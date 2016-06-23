package com.taller2.matchapp.model;

import com.taller2.matchapp.util.DateUtils;
import com.taller2.matchapp.util.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by federicofarina on 6/21/16.
 */
public class Message implements Serializable {

    public static final String TIME = "time";
    public static final String SENDER = "sender";
    public static final String MESSAGE = "message";

    public String time;
    public String sender;
    public String message;


    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(TIME, time);
            jsonObject.putOpt(SENDER, sender);
            jsonObject.putOpt(MESSAGE, message);
        } catch (JSONException e) {
            //Will never be happen
        }
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        message = jsonObject.optString(MESSAGE);
        sender = jsonObject.optString(SENDER);
        String timeText = jsonObject.optString(TIME);
        timeText = timeText.replace("\\n", "");
        final String convertedDate = DateUtils.convertDate(timeText);
        time = convertedDate != null ? convertedDate : "";
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
