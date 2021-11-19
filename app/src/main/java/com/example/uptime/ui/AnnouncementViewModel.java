package com.example.uptime.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.uptime.network.AnnouncementRepository;
import com.example.uptime.pojo.Betteruptime;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

// @HiltViewModel
public class AnnouncementViewModel extends ViewModel {
    private final String TAG = AnnouncementViewModel.class.getSimpleName();

    AnnouncementRepository announcementRepository;
    private LiveData<Betteruptime> betteruptimeLiveData;

//    AnnouncementViewModel(){
//        announcementRepository  = new AnnouncementRepository();
//        betteruptimeLiveData = announcementRepository.getBetteruptimeLiveData();
//    }

    public String getAnnouncement(){
        String announce = "bad";
        try{
            return getBetteruptimeLiveData().getValue().getData().getAttributes().getAnnouncement();
        }
        catch (Exception e){
            System.out.println(TAG + ":" + e.getMessage());
        }
        return announce;
        // return betteruptimeLiveData.getValue().getData().getAttributes().getAnnouncement();
    }

    public LiveData<Betteruptime> getBetteruptimeLiveData() {
        if(betteruptimeLiveData == null){
            if(announcementRepository == null){
                announcementRepository = new AnnouncementRepository();
            }
            betteruptimeLiveData = announcementRepository.getBetteruptimeLiveData();
        }
        return betteruptimeLiveData;
    }
}
