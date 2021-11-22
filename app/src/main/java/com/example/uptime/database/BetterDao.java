package com.example.uptime.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * These are the database operations available for the Better entity (table)
 * 11/22/21 Jose Salazar
 * **/

@Dao
public interface BetterDao {

    /**
     * Note: plain old Convenience methods such as Insert, Update, Delete run synchronously
     * Query methods that return a value run asynchronously so handle appropriately
     * **/

    // generic insert into table
    @Insert
    void insert(Better better);

    // get announcement by its key
    @Query("SELECT announcement FROM BETTERUPTIME_TABLE WHERE createdAtTime = :createdAt")
    String getAnnouncement(String createdAt);

    // Set a row's hasSeen value by its createdAt (primary key)
    @Query("UPDATE betteruptime_table SET hasSeen = :seen WHERE createdAtTime = :createdAt")
    void setSeen(boolean seen, String createdAt);

    // empties the table
    @Query("DELETE FROM betteruptime_table")
    void dropBetterTable();

}
