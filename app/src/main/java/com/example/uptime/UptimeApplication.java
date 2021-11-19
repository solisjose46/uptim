package com.example.uptime;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class UptimeApplication extends Application {
    private final String TAG = UptimeApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
