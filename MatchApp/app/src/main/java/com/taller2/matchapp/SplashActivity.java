package com.taller2.matchapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.taller2.matchapp.util.LocationManager;

/**
 * Created by federicofarina on 6/7/16.
 */
public class SplashActivity extends BaseActivity {

    public static final int START_DELAY = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        //Fetch last location
        LocationManager.getInstance(getApplicationContext()).fetchLastLocation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                //If user is logged in
                if (Session.getInstance(SplashActivity.this).isLoggedIn()) {
                    intent.setClass(SplashActivity.this, MainActivity.class);
                } else {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                }
                    startActivity(intent);
                finish();
            }
        }, START_DELAY);
    }
}
