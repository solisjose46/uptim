package com.example.uptime.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uptime.network.AnnouncementRepository;
import com.example.uptime.pojo.Betteruptime;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
/**
 * This viewModel is shared with MainActivity and AnnouncementFragment
 * 11/20/21 Jose Salazar
 * **/
public class AnnouncementViewModel extends ViewModel {
    private final String TAG = AnnouncementViewModel.class.getSimpleName();
    AnnouncementRepository announcementRepository;
    // from the repository
    private LiveData<Betteruptime> betteruptimeLiveData;
    // tracks if announcement should be visible
    private MutableLiveData<Boolean> visible;

    public void handleBtnClick(){
        System.out.println(TAG + ": handleClickBtn");
        if(visible.getValue()){
            visible.postValue(false);
        }
        else{
            announcementRepository.makeCall();
            visible.postValue(true);
        }
    }
    // for the observers
    public LiveData<Boolean> isVisible(){
        if (visible == null){
            visible = new MutableLiveData<Boolean>(true);
        }
        return visible;
    }
    public LiveData<Betteruptime> getBetteruptimeLiveData(){
        if(betteruptimeLiveData == null){
            if(announcementRepository == null){
                announcementRepository = new AnnouncementRepository();
            }
            betteruptimeLiveData = announcementRepository.getBetteruptimeLiveData();
        }
        return betteruptimeLiveData;
    }
}
