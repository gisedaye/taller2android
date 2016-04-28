package com.taller2.matchapp.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Configuration parameters saved into the app shared preferences
 */
public class Configuration {

    private static final String CONNECTION_CONFIG  = "connection_config";
    private static final String APP_SERVER_IP  = "ip";
    private static final String APP_SERVER_PORT  = "port";
    /**
     * Returns the app server URL
     * @param context
     * @return
     */
    public static String getURL(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(CONNECTION_CONFIG, Context.MODE_PRIVATE);
        String ip = sharedPref.getString(APP_SERVER_IP, "192.168.56.1");
        String port = sharedPref.getString(APP_SERVER_PORT, "8083");
        return "http://"+ip+":"+port;
    }
}