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
    // Columns for this table
    @NonNull
    @PrimaryKey
    private String createdAtTime; // We will differentiate any announcements by their creation timestamp
    private String announcement;
    private boolean hasSeen;

    // If making new announcement then it has not been seen so default hasSeen is false
    public Better(String createdAtTime, String announcement){
        System.out.println(Better.class.getSimpleName() + " new better");
        this.createdAtTime = createdAtTime;
        this.announcement = announcement;
        this.hasSeen = false; // new announcement has not been seen
    }
    // These getters/setter are used by Room
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
