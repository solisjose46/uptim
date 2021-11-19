package com.example.uptime.pojo;

/**
 * Pojo for mapping Betteruptime return json via Gson/retrofit
 * thus doesn't need setters
 * data{
 *     id: ...
 *     attributes:{
 *         announcement: ...
 *     }
 *     ...
 * }
 *
 * 11/18 JS
 */

public class Betteruptime {
    private Data data;

    public Data getData(){
        return data;
    }
}
