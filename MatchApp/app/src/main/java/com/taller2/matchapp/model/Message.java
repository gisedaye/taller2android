package com.taller2.matchapp.model;

import com.taller2.matchapp.util.Serializable;
import org.json.JSONObject;

/**
 * Created by federicofarina on 6/21/16.
 */
public class Message implements Serializable{
    public String time;
    public String sender;
    public String message;


    @Override
    public JSONObject toJson() {
        return null;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {

    }

    public String getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
