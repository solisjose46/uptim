package com.example.uptime.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.uptime.R;

import dagger.hilt.android.AndroidEntryPoint;
/**
 * Disable Hilt DI for now
 * **/
// @AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    // get AnnouncementFragment in this fragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(TAG + ": making frag..");
        getSupportFragmentManager().beginTransaction().add(R.id.announcement_fragment, new AnnouncementFragment()).commit();
    }
}