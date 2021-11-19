package com.example.uptime;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;
/**
 * Disable Hilt DI for now
 * **/
// @HiltAndroidApp
public class UptimeApplication extends Application {
    private final String TAG = UptimeApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
