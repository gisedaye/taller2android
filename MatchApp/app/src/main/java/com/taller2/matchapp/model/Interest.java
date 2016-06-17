package com.taller2.matchapp.model;

/**
 * Created by federicofarina on 6/17/16.
 */
public class Interest {

    public final String value;
    public final String category;


    public Interest(String value, String category) {
        this.value = value;
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }
}
