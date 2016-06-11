package com.taller2.matchapp.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * MatchAPI parameters saved into the app shared preferences
 */
public class MatchAPI {

    private static final String LOGIN_ENDPOINT = "/api/accounts/login";
    private static final String REGISTER_ENDPOINT = "/api/accounts/signup";
    private static final String CANDIDATES_ENDPOINT = "/api/matches/candidate";


    private static final String CONNECTION_CONFIG = "connection_config";
    private static final String TOKEN = "SESSION_TOKEN";

    private static final String APP_SERVER_IP = "192.168.1.47";
    private static final String APP_SERVER_PORT = "8083";

    private static String getAppServerURL() {
        return "http://" + APP_SERVER_IP + ":" + APP_SERVER_PORT;
    }

    public static String getLoginEndpoint() {
        return getAppServerURL() + LOGIN_ENDPOINT;
    }

    public static String getRegisterEndpoint() {
        return getAppServerURL() + REGISTER_ENDPOINT;
    }

    public static String getCandidatesEndpoint() {
        return getAppServerURL() + CANDIDATES_ENDPOINT;
    }


    public static String getToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(CONNECTION_CONFIG, Context.MODE_PRIVATE);
        return sharedPref.getString(TOKEN, null);
    }

    public static void setToken(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences(CONNECTION_CONFIG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TOKEN, token);
        editor.commit();
    }

}