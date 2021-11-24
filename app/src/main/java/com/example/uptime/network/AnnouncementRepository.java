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
 * This object is tied to the lifecycle of MainActivity
 * 11/18/21 Jose Salazar
 * **/

public class AnnouncementRepository {
    // for the bugs
    private final String TAG = AnnouncementRepository.class.getSimpleName();
    // responsible for making the call and getting our betteruptime json object
    private BetteruptimeApi betteruptimeApi; // remote data source
    // This live data value will hold our return json object and share to our observers
    // This observable object contains our announcement
    private MutableLiveData<Betteruptime> betteruptimeMutableLiveData;
    // For database operations
    private BetterDao betterDao;

    // on creation of repo, make api call to set our mutable live data

    public AnnouncementRepository(){
        System.out.println(TAG + "new repo");
        // make betteruptimeApi object here with retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Instantiate our dependencies
        betteruptimeApi = retrofit.create(BetteruptimeApi.class);
        betteruptimeMutableLiveData = new MutableLiveData<Betteruptime>(null);
        betterDao = BetterDatabase.getInstance(getStaticContext()).betterDao();
        // nuke table just for testing
        new NukeTableTask(betterDao).execute();
        makeCall(); // our initial call
    }

    // API call which fetches announcement
    public void makeCall(){
        // call method implemented by retrofit
        Call<Betteruptime> call = betteruptimeApi.getAnnouncement();
        // make the api call
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
                // Great Succ
                // betteruptimeMutableLiveData.postValue(response.body()); // extract announcement in viewmodel
                displayAnnouncement(response.body()); // only called when response is 200 so this betteruptime object is never null
                System.out.println(TAG + ": announcement : " + response.body().getData().getAttributes().getAnnouncement());
            }

            @Override
            public void onFailure(Call<Betteruptime> call, Throwable t) {
                // Network issues
                System.out.println(TAG + ": call fail");
            }
        });
    }
    // ! ----- Async task classes for Dao operations here ----- !
    // TODO: make these async classes

    // 1. check if the entry exists
    // 2. If true then update betteruptimeMutableLiveData if false do nothing
    // 3. If 2 true then insert the betteruptimeMutableLiveData



    // ----- helper functions ----- TODO: move these tasks to the async task
    private void displayAnnouncement(Betteruptime betteruptime){
        System.out.println(TAG + " displayAnnouncement");
        // TODO: crashses here.... create the asyctask
        new FetchAnnouncementTask(betterDao).execute(betteruptime); // runs in background on separate thread
//        if(!entryExists(betteruptime)){
//            betteruptimeMutableLiveData.postValue(betteruptime);
//            // Move this to onPost of FetchAnnouncementTask
//            insertDB(betteruptime.getCreatedAtDate(), betteruptime.getAnnouncement());
//        }
    }
    // call this to onPost of FetchAnnouncementTask
//    private void insertDB(String timeStamp, String announcement){
//        System.out.println(TAG + " insertDB");
//        Better better = new Better(timeStamp, announcement);
//        betterDao.insert(better);
//    }
//    private boolean entryExists(Betteruptime betteruptime){
//        System.out.println(TAG + " entryExists");
//        String key = betteruptime.getCreatedAtDate();
//        if(betterDao.getAnnouncement(key) != null){
//            return true;
//        }
//        return false;
//    }




    // ----- share to our observers -----
    // For Announcement fragment
    public LiveData<Betteruptime> getBetteruptimeLiveData(){
        // be careful betteruptime variable can be null, handle this in observers
        return betteruptimeMutableLiveData;
    }
    public void userSeen(){
        System.out.println(TAG + "userSeen");
        new UpdateSeenTask(betterDao).execute(betteruptimeMutableLiveData.getValue().getCreatedAtDate());
        // updates the current announcement as has been seen so as to not display the same message again
        System.out.println(TAG + "userSeen");
//        String key = betteruptimeMutableLiveData.getValue().getCreatedAtDate();
//        betterDao.setSeen(true, key);
    }
    // ----- Async classes here -----
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
            // TODO: refactor pojo for created at for updated at because it uses the same created at time...
            if(betterDao.getAnnouncement(key) == null){
                System.out.println(TAG + " " + FetchAnnouncementTask.class.getSimpleName() + " doing and posting");
                System.out.println(TAG + " this key is " + key);
                Better better = new Better(betteruptime.getCreatedAtDate(), betteruptime.getAnnouncement());
                betteruptimeMutableLiveData.postValue(betteruptime);
                betterDao.insert(better);
            }
            else{
                // TODO: need to return something
                System.out.println(TAG + " this key exists " + key);
            }
            return null;
        }
    }

    private class UpdateSeenTask extends AsyncTask<String, Void, Void>{
        private BetterDao betterDao;

        private UpdateSeenTask(BetterDao betterDao){
            this.betterDao = betterDao;
        }


        @Override
        protected Void doInBackground(String... strings) {
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
