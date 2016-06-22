package com.taller2.matchapp.model;

import com.taller2.matchapp.util.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by federicofarina on 6/21/16.
 */
public class Match implements Serializable {

    public static final String CHAT_ID = "chatId";
    public static final String PROFILE = "profile";

    String chatID;
    Profile profile;

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(CHAT_ID, chatID);
            jsonObject.putOpt(PROFILE, profile.toJson());
        } catch (JSONException e) {
            //Will never be happen
        }
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        chatID = jsonObject.optString(CHAT_ID);
        Profile profile = new Profile();
        profile.fromJson(jsonObject.optJSONObject(PROFILE));
        this.profile = profile;
    }

    public String getChatID() {
        return chatID;
    }

    public Profile getProfile() {
        return profile;
    }
}
