package com.example.uptime.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Table definition for Caching betteruptime api calls
 * 11/22/21 Jose Salazar
 * **/

@Entity(tableName = "betteruptime_table")
public class Better {
    @NonNull
    @PrimaryKey
    private String createdAtTime; // We will differentiate any announcements by their creation timestamp
    private String announcement;
    private boolean hasSeen; // false default?

    public Better(String createdAtTime, String announcement){
        System.out.println(Better.class.getSimpleName() + " new better");
        this.createdAtTime = createdAtTime;
        this.announcement = announcement;
        this.hasSeen = false; // new announcement has not been seen
    }

    public String getCreatedAtTime(){
        return createdAtTime;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public boolean getHasSeen(){
        return hasSeen;
    }

    public void setHasSeen(boolean seen){
        hasSeen = seen;
    }
}
