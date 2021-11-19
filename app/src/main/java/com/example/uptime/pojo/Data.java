package com.example.uptime.pojo;

/**
 * For use with retrofit/gson mappiong. See Data.java
 * 11/18/21 Jose Salazar
 * **/

public class Data {
    private String id;
    private Attributes attributes;

    public String getId(){
        return id;
    }

    public Attributes getAttributes(){
        return attributes;
    }
}
