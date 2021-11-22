package com.example.uptime.network;

import static com.example.uptime.misc.BetteruptimeConstants.BASE_URL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.uptime.database.Better;
import com.example.uptime.database.BetterDao;
import com.example.uptime.database.BetterDatabase;
import com.example.uptime.pojo.Betteruptime;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.uptime.UptimeApplication.getStaticContext;

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
                System.out.println(TAG + " : announcement : " + response.body().getData().getAttributes().getAnnouncement());
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

    // ----- helper functions -----
    private void displayAnnouncement(Betteruptime betteruptime){
        System.out.println(TAG + "displayAnnouncement");
        if(!entryExists(betteruptime)){
            betteruptimeMutableLiveData.postValue(betteruptime);
            insertDB(betteruptime.getCreatedAtDate(), betteruptime.getAnnouncement());
        }
    }
    private void insertDB(String timeStamp, String announcement){
        System.out.println(TAG + "insertDB");
        Better better = new Better(timeStamp, announcement);
        betterDao.insert(better);
    }
    private boolean entryExists(Betteruptime betteruptime){
        System.out.println(TAG + "entryExists");
        String key = betteruptime.getCreatedAtDate();
        if(betterDao.getAnnouncement(key) != null){
            return true;
        }
        return false;
    }
    // ----- share to our observers -----
    // For Announcement fragment
    public LiveData<Betteruptime> getBetteruptimeLiveData(){
        // be careful betteruptime variable can be null, handle this in observers
        return betteruptimeMutableLiveData;
    }
    public void userSeen(){
        // updates the current announcement as has been seen so as to not display the same message again
        System.out.println(TAG + "userSeen");
        String key = betteruptimeMutableLiveData.getValue().getCreatedAtDate();
        betterDao.setSeen(true, key);
    }
}
