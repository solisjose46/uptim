package com.example.uptime.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.uptime.network.AnnouncementRepository;
import com.example.uptime.pojo.Betteruptime;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

// @HiltViewModel
public class AnnouncementViewModel extends ViewModel {
    // @Inject
    AnnouncementRepository announcementRepository = new AnnouncementRepository();
    private LiveData<Betteruptime> betteruptimeLiveData = announcementRepository.getBetteruptimeLiveData();

//    // @Inject
//    AnnouncementViewModel(){
//        // super();
//    }

    public String getAnnouncement(){
        return betteruptimeLiveData.getValue().getData().getAttributes().getAnnouncement();
    }

    public LiveData<Betteruptime> getBetteruptimeLiveData() {
        return betteruptimeLiveData;
    }
}
