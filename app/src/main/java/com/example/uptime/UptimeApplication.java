package com.example.uptime;

import android.app.Application;
import android.content.Context;

import dagger.hilt.android.HiltAndroidApp;

public class UptimeApplication extends Application {
    private final String TAG = UptimeApplication.class.getSimpleName();
    // TODO: eww, refactor this later with hilt
    private static UptimeApplication applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
    }

    public static Context getStaticContext(){
        return applicationContext.getApplicationContext();
    }
}
