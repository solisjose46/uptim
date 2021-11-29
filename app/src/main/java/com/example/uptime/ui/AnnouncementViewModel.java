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
    // observed in 'MainActivity' for updating UI with announcement
    private LiveData<Betteruptime> betteruptimeLiveData;

    CountDownTimer announcementTimer = new CountDownTimer(10000,1000) {
        public void onTick ( long millisUntilFinished){
            System.out.println("new call in " + millisUntilFinished / 1000);
        }

        public void onFinish () {
            System.out.println(TAG + "get new call");
            announcementRepository.makeCall(); // checks for announcement
            // start the timer again
            announcementTimer.start();
        }
    };

    // expose this to dialog method
    public void setSeen(){
        announcementRepository.userSeen();
        announcementTimer.start();
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
