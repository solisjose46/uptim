package com.example.uptime.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * This db is for caching betteruptime announcements
 * Schema below
 * ------------
 * | String createdAt (primary key) | String announcement | boolean hasSeen |
 * **/

@Database(entities = {Better.class}, version = 1)
public abstract class BetterDatabase extends RoomDatabase {
    private static final String TAG = BetterDatabase.class.getSimpleName();
    private static final String DATABASE = "BetterDatabase.db";

    private static BetterDatabase Instance;

    public abstract BetterDao betterDao();

    public static synchronized BetterDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context.getApplicationContext(), BetterDatabase.class, DATABASE)
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            System.out.println(TAG + "db created!");
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return Instance;
    }
}
