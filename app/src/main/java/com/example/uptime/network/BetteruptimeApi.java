package com.example.uptime.network;

import static com.example.uptime.misc.BetteruptimeConstants.KEY_AND_TOKEN;
import static com.example.uptime.misc.BetteruptimeConstants.PAGE_NUMBER;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import com.example.uptime.pojo.Betteruptime;

/**
 * Interface for Retrofit implementation
 * 11/18/21 Jose Salazar
 * **/

public interface BetteruptimeApi {
    @Headers(KEY_AND_TOKEN)
    @GET(PAGE_NUMBER)
    Call<Betteruptime> getAnnouncement();
}
