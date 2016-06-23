package com.taller2.matchapp.view;

import android.content.Context;
import com.taller2.matchapp.model.Profile;
import com.taller2.matchapp.util.SharedPreferencesEditor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fedefarina on 20/06/16.
 */
public class Session {

    private static final String TOKEN = "TOKEN";
    private static final String PROFILE = "PROFILE";
    private static final String SEARCH_DISTANCE = "SEARCH_DISTANCE";

    public static Session instance = null;
    private static SharedPreferencesEditor sharedPreferencesEditor;

    // Used as memory cache
    private String token;
    private Profile profile;
    private int searchDistance;

    public static Session getInstance(Context context) {
        // double checked locking implementation to prevent synchronization blocking
        if (instance == null) {
            synchronized (Session.class) {
                if (instance == null) {
                    instance = new Session(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private Session(Context context) {
        sharedPreferencesEditor = new SharedPreferencesEditor(context, "session");
        token = sharedPreferencesEditor.valueForKey(TOKEN);
        searchDistance = sharedPreferencesEditor.valueForKey(SEARCH_DISTANCE, 5);
        try {
            JSONObject profileJSON = sharedPreferencesEditor.jsonObjectForKey(PROFILE);
            if (profileJSON != null) {
                Profile profile = new Profile();
                profile.fromJson(profileJSON);
                this.profile = profile;
            }
        } catch (JSONException ignored) {
            //Always valid JSON here
        }
    }

    public void logout() {
        token = null;
        profile = null;
        sharedPreferencesEditor.clear();
    }

    public void setToken(String token) {
        this.token = token;
        sharedPreferencesEditor.setValueForKey(TOKEN, token);
    }

    public void setSearchDistance(int searchDistance) {
        this.searchDistance = searchDistance;
        sharedPreferencesEditor.setValueForKey(SEARCH_DISTANCE, searchDistance);
    }

    public void setProfile(Profile profile) {
        try {
            this.profile = profile;
            sharedPreferencesEditor.setJSONObjectForKey(PROFILE, (JSONObject) profile.toJson());
        } catch (JSONException e) {
            //Will not happen
        }
    }

    public Profile getProfile() {
        return profile;
    }

    public String getToken() {
        return token;
    }

    public int getSearchDistance() {
        return searchDistance;
    }

    public boolean isLoggedIn() {
        return token != null;
    }
}
