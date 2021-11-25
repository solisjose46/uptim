package com.example.uptime.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * For use with retrofit/gson mapping. See Data.java
 * 11/18/21 Jose Salazar
 * **/

public class Attributes {
    private String announcement;
    @SerializedName("updated_at") // renamed. Use this to tell retrofit/gson to map accordingly
    private String createdAtTime;

    public String getAnnouncement(){
        return announcement;
    }
    public String getCreatedAtTime(){
        return createdAtTime;
    }
}
