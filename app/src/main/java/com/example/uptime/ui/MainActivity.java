package com.example.uptime.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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
        boolean atStart = true;

        viewModel.isVisible().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visible) {
                // if fragment should not be visible and the fragment exists, remove it
                if(!visible && fragmentManager.findFragmentByTag(AnnouncementFragment.class.getSimpleName()) != null){
                    // remove the announcement
                    System.out.println(TAG + "remove fragment!");
                    fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag(AnnouncementFragment.class.getSimpleName())).commit();
                } // if fragment should be visible and does not exisit already, create it
                else if(visible && fragmentManager.findFragmentByTag(AnnouncementFragment.class.getSimpleName()) == null){
                    // create the announcement!
                    System.out.println(TAG + "make new fragment");
                    fragmentManager.beginTransaction().add(R.id.announcement_fragment, new AnnouncementFragment(), AnnouncementFragment.class.getSimpleName()).commit();
                }
            }
        });
    }
}