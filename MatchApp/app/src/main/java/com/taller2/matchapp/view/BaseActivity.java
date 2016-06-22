package com.taller2.matchapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.taller2.matchapp.util.ActivityIndicator;

/**
 * Created by fedefarina on 05/06/16.
 */
public class BaseActivity extends AppCompatActivity {

    ActivityIndicator activityIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityIndicator = new ActivityIndicator(this);
    }

    public ActivityIndicator getActivityIndicator() {
        return activityIndicator;
    }
}
