package com.example.uptime.ui;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uptime.network.AnnouncementRepository;
import com.example.uptime.pojo.Betteruptime;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

public class AnnouncementViewModel extends ViewModel {
    private final String TAG = AnnouncementViewModel.class.getSimpleName();

    // Api call is called when repo is created
    private MutableLiveData<AnnouncementRepository> announcementRepositoryMutableLiveData = new MutableLiveData<AnnouncementRepository>(new AnnouncementRepository());
    // AnnouncementRepository announcementRepository
    private MutableLiveData<Boolean> visible = new MutableLiveData<Boolean>(true);

    public void handleBtnClick(){
        System.out.println(TAG + ": handleClickBtn");
        if(visible.getValue()){
            visible.postValue(false);
        }
        else{
            announcementRepositoryMutableLiveData.postValue(new AnnouncementRepository());
            visible.postValue(true);
        }
    }

    public LiveData<AnnouncementRepository> observerRepository(){
        return announcementRepositoryMutableLiveData;
    }

    public LiveData<Boolean> isVisible(){
        return visible;
    }
}
