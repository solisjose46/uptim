package com.example.uptime.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.example.uptime.R;
import com.example.uptime.databinding.ActivityMainBinding;
import com.example.uptime.pojo.Betteruptime;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * This represents the base activity for which the announcement fragment will sit in
 * **/

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding binding;
    AnnouncementViewModel viewModel;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(AnnouncementViewModel.class);
        binding.setViewModel(viewModel); // remember to bind your ui with viewmodel if using databinding!
        // for creating/removing announcement fragments
        fragmentManager = getSupportFragmentManager();
        viewModel.getBetteruptimeLiveData().observe(this, new Observer<Betteruptime>() {
            @Override
            public void onChanged(Betteruptime betteruptime) {
                if(betteruptime != null && fragmentManager.findFragmentByTag(AnnouncementFragment.TAG) == null){
                    // create the fragment and set its text
                    System.out.println(TAG + " creating dialog fragment");
                    AnnouncementFragment announcementFragment = AnnouncementFragment.newInstance(betteruptime.getAnnouncement());
                    announcementFragment.show(fragmentManager, AnnouncementFragment.TAG);
                }
            }
        });
    }
}