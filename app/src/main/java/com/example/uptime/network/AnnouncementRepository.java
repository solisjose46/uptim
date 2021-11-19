package com.example.uptime.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.uptime.pojo.Betteruptime;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository for AnnouncementViewModel
 * Make api call here
 * TODO: implement Room and make api call there. Api call stays here for now.
 * 11/18/21 Jose Salazar
 * **/

public class AnnouncementRepository {
    private final String TAG = AnnouncementRepository.class.getSimpleName();
    @Inject
    BetteruptimeApi betteruptimeApi;
    private MutableLiveData<Betteruptime> betteruptimeMutableLiveData;

    public LiveData<Betteruptime> getBetteruptimeLiveData(){
        return betteruptimeMutableLiveData;
    }

    // @Inject
    AnnouncementRepository(){
        makeCall();
    }

    private void makeCall(){
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
                betteruptimeMutableLiveData.postValue(response.body());
                System.out.println(TAG + " : announcement : " + response.body().getData().getAttributes().getAnnouncement());
            }

            @Override
            public void onFailure(Call<Betteruptime> call, Throwable t) {
                System.out.println(TAG + ": call fail");
            }
        });
    }

}
