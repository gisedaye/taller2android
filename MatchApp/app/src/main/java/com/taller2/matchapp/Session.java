package com.taller2.matchapp;

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

    public static Session instance = null;
    private static SharedPreferencesEditor sharedPreferencesEditor;

    private String token;
    private Profile profile;

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
        sharedPreferencesEditor.clear();
    }

    public void setToken(String token) {
        sharedPreferencesEditor.valueForKey(TOKEN, token);
    }

    public void setProfile(Profile profile) {
        try {
            sharedPreferencesEditor.setJSONObjectForKey(PROFILE, (JSONObject) profile.toJson());
        } catch (JSONException e) {
            //Will not happen
        }
    }

    public String getToken() {
        return token;
    }

    public boolean isLoggedIn() {
        return token != null;
    }
}
