package com.example.uptime.misc;

/**
 * All sensitive info for Betteruptime api call here
 * included in .gitignore for obvious reasons
 * 11/18/21 Jose Salazar
 * **/

public final class BetteruptimeConstants {
    private BetteruptimeConstants(){}
    public final static String BASE_URL = "https://betteruptime.com/api/v2/status-pages/";
    public final static String PAGE_NUMBER = "132524";
    public final static String KEY = "Authorization";
    public final static String API_TOKEN = "Bearer VTkNV9QiNcmFjHR5PjAFm6o3";
    public final static  String KEY_AND_TOKEN = KEY + ": " + API_TOKEN;
}
