package com.taller2.matchapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.taller2.matchapp.config.MatchAPI;

/**
 * Created by federicofarina on 6/7/16.
 */
public class SplashActivity extends BaseActivity {

    public static final int START_DELAY = 2000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //If user is logged in
                if (MatchAPI.getToken(SplashActivity.this) == null) {
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, START_DELAY);
    }
}
