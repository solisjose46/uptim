package com.example.uptime.ui;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.uptime.network.AnnouncementRepository;
import com.example.uptime.pojo.Betteruptime;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * This viewModel is shared with MainActivity and AnnouncementFragment
 * 11/20/21 Jose Salazar
 **/

public class AnnouncementViewModel extends ViewModel {
    // for the bugs
    private final String TAG = AnnouncementViewModel.class.getSimpleName();
    AnnouncementRepository announcementRepository;
    // observed in 'AnnouncementFragment' for updating ui with announcement
    private LiveData<Betteruptime> betteruptimeLiveData;
    // observed in 'MainActivity' for tracking if the fragment should be visible
    private MutableLiveData<Boolean> visible;
    // updates the status of the fragment: true = should be visible to user, false = should be removed

    boolean first = true;

    CountDownTimer announcementTimer = new CountDownTimer(30000,1000) {
        public void onTick ( long millisUntilFinished){
            System.out.println("new call in " + millisUntilFinished / 1000);
        }

        public void onFinish () {
            System.out.println(TAG + "get new call");
            announcementRepository.makeCall(); // checks for announcement
        }
    };

    // only need btnClick now for fragment
    public void handleBtnClick() {
        System.out.println(TAG + ": handleClickBtn");
        announcementRepository.userSeen(); // marks the currently displayed announcement has seen by user
        visible.postValue(false);
        if(first){
            announcementTimer.start();
            first = false;
        }
    }



    // phase this out for timer
//    public void handleBtnClick(){
//        System.out.println(TAG + ": handleClickBtn");
//        if(visible.getValue()){
//            announcementRepository.userSeen(); // marks the currently displayed announcement has seen by user
//            visible.postValue(false);
//        }
//        else{
//            announcementRepository.makeCall(); // make new call to check for any new announcements
//            // TODO: determine here to display announcement based on if seen or not
//            visible.postValue(true); // if new announcement then new fragment will contain the new announcement
//        }
//    }


    // ----- for the observers -----
    // observed in Main activity: decides if AnnouncementFragment should be visible or not
    public LiveData<Boolean> isVisible() {
        if (visible == null) {
            visible = new MutableLiveData<Boolean>(true); // instantiated with true = will display fragment at MainActivity startup
        }
        return visible;
    }

    // observed in AnnouncementFragment: updates its text with the announcement
    public LiveData<Betteruptime> getBetteruptimeLiveData() {
        if (betteruptimeLiveData == null) {
            if (announcementRepository == null) {
                announcementRepository = new AnnouncementRepository();
            }
            betteruptimeLiveData = announcementRepository.getBetteruptimeLiveData(); // propagate the observable to AnnouncementFragment
        }
        return betteruptimeLiveData;
    }
}
