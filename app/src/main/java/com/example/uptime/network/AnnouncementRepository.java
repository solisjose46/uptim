package com.example.uptime.network;

import static com.example.uptime.misc.BetteruptimeConstants.BASE_URL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.uptime.database.Better;
import com.example.uptime.database.BetterDao;
import com.example.uptime.database.BetterDatabase;
import com.example.uptime.pojo.Betteruptime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.uptime.UptimeApplication.getStaticContext;

import android.os.AsyncTask;

/**
 * Repository for AnnouncementViewModel
 * Remote data source: BetteruptimeApi
 * Local data source: BetterDao
 * 11/18/21 Jose Salazar
 * **/

public class AnnouncementRepository {
    // for the bugs
    private final String TAG = AnnouncementRepository.class.getSimpleName();
    // Remote data source
    private BetteruptimeApi betteruptimeApi;
    // This live data value will hold our return json object and share to our observers
    // This observable object contains our announcement
    private MutableLiveData<Betteruptime> betteruptimeMutableLiveData;
    // Remote data source
    private BetterDao betterDao;

    // on creation of repo, make api call to set our mutable live data
    public AnnouncementRepository(){
        System.out.println(TAG + "new repo");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        betteruptimeApi = retrofit.create(BetteruptimeApi.class);
        betteruptimeMutableLiveData = new MutableLiveData<Betteruptime>(null);
        betterDao = BetterDatabase.getInstance(getStaticContext()).betterDao(); // TODO: get application context in a better way

        // ----- nuke table just for testing -----
        System.out.println(TAG + " table Nuked just for testing");
        new NukeTableTask(betterDao).execute();
        // ----- nuke table just for testing -----

        makeCall(); // our initial call
    }

    // API call which fetches announcement
    public void makeCall(){
        Call<Betteruptime> call = betteruptimeApi.getAnnouncement();
        call.enqueue(new Callback<Betteruptime>() {
            @Override
            public void onResponse(Call<Betteruptime> call, Response<Betteruptime> response) {
                // Response received check if 200
                if(!response.isSuccessful()){
                    // not 200, check code
                    // either, bad url, bad api token, etc
                    System.out.println(TAG + ": " + response.code());
                    return;
                }
                // Great Succ, process response for updating UI
                System.out.println(TAG + ": announcement : " + response.body().getData().getAttributes().getAnnouncement());
                displayAnnouncement(response.body()); // only called when response is 200 so this betteruptime object is never null
            }

            @Override
            public void onFailure(Call<Betteruptime> call, Throwable t) {
                // Network issues
                System.out.println(TAG + ": call fail");
            }
        });
    }

    // ----- helper functions -----

    private void displayAnnouncement(Betteruptime betteruptime){
        System.out.println(TAG + " displayAnnouncement");
        new FetchAnnouncementTask(betterDao).execute(betteruptime); // runs in background on separate thread
    }

    // ----- share to our observers -----

    public LiveData<Betteruptime> getBetteruptimeLiveData(){
        // be careful betteruptimeMutableLiveData can be null, handle this in observers
        return betteruptimeMutableLiveData;
    }

    public void userSeen(){
        System.out.println(TAG + " userSeen");
        new UpdateSeenTask(betterDao).execute(betteruptimeMutableLiveData.getValue().getCreatedAtDate());
    }

    // ! ----- Async task classes for Dao operations here ----- !

    private class FetchAnnouncementTask extends AsyncTask<Betteruptime, Void, Void>{
        private BetterDao betterDao;

        private FetchAnnouncementTask(BetterDao betterDao){
            System.out.println(TAG + " " + FetchAnnouncementTask.class.getSimpleName() + " created");
            this.betterDao = betterDao;
        }

        @Override
        protected Void doInBackground(Betteruptime... betteruptimes) {
            String key = betteruptimes[0].getCreatedAtDate();
            Betteruptime betteruptime = betteruptimes[0];
            System.out.println(TAG + " " + FetchAnnouncementTask.class.getSimpleName() + " doing");
            if(betterDao.getAnnouncement(key) == null){
                System.out.println(TAG + " " + FetchAnnouncementTask.class.getSimpleName() + " doing and posting");
                System.out.println(TAG + " this key is " + key);
                Better better = new Better(betteruptime.getCreatedAtDate(), betteruptime.getAnnouncement());
                betteruptimeMutableLiveData.postValue(betteruptime);
                betterDao.insert(better);
            }
            else{
                // for debugging
                System.out.println(TAG + " this key exists " + key);
            }
            return null;
        }
    }

    private class UpdateSeenTask extends AsyncTask<String, Void, Void>{
        private BetterDao betterDao;

        private UpdateSeenTask(BetterDao betterDao){
            System.out.println(UpdateSeenTask.class.getSimpleName() + "created");
            this.betterDao = betterDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            System.out.println(UpdateSeenTask.class.getSimpleName() + " update task in background");
            String key = strings[0];
            betterDao.setSeen(true, key);
            return null;
        }
    }

    private class NukeTableTask extends AsyncTask<Void, Void, Void>{
        private BetterDao betterDao;

        private NukeTableTask(BetterDao betterDao){
            this.betterDao = betterDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            betterDao.dropBetterTable();
            return null;
        }
    }
}