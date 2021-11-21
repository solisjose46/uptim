package com.example.uptime.network;

import static com.example.uptime.misc.BetteruptimeConstants.BASE_URL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.uptime.pojo.Betteruptime;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Repository for AnnouncementViewModel
 * Make api call here
 * TODO: implement Room and make api call there. Api call stays here for now.
 * 11/18/21 Jose Salazar
 * **/

public class AnnouncementRepository {
    private final String TAG = AnnouncementRepository.class.getSimpleName();
    // responsible for making the call and getting our betteruptime json object
    private BetteruptimeApi betteruptimeApi;
    // This live data value will hold our return json object and share to our observers
    private MutableLiveData<Betteruptime> betteruptimeMutableLiveData;

    // on creation of repo, make api call to set our mutable live data
    public AnnouncementRepository(){
        // make betteruptimeApi object here with retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        betteruptimeMutableLiveData = new MutableLiveData<Betteruptime>(null);
        betteruptimeApi = retrofit.create(BetteruptimeApi.class);
        makeCall();
    }

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
                    System.out.println(TAG + ": " + response.code());
                    return;
                }
                // Great Succ
                betteruptimeMutableLiveData.postValue(response.body()); // extract in viewmodel
                System.out.println(TAG + " : announcement : " + response.body().getData().getAttributes().getAnnouncement());
            }

            @Override
            public void onFailure(Call<Betteruptime> call, Throwable t) {
                // TODO: need to handle failure and return something like a Betteruptime object with its announcement saying announcement failed or something like that
                System.out.println(TAG + ": call fail");
            }
        });
    }
    // share to our observers
    public LiveData<Betteruptime> getBetteruptimeLiveData(){
        // be careful betteruptime variables can be null, handle this in observers
        return betteruptimeMutableLiveData;
    }
}
