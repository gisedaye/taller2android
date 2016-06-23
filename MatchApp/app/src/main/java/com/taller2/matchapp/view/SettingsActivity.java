package com.taller2.matchapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.taller2.matchapp.R;

/**
 * Created by federicofarina on 6/21/16.
 */
public class SettingsActivity extends BaseActivity {

    private Toolbar myToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final View logoutView = findViewById(R.id.logout);

        //noinspection ConstantConditions
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getInstance(SettingsActivity.this).logout();
                Intent intent = new Intent();
                intent.setClass(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                SettingsActivity.this.finish();
            }
        });


        final View myProfileView = findViewById(R.id.my_profile);
        //noinspection ConstantConditions
        myProfileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingsActivity.this, ProfileDetailActivity.class);
                intent.putExtra(ProfileDetailActivity.PROFILE_EXTRA, Session.getInstance(getApplicationContext()).getProfile());
                startActivity(intent);
            }
        });

        final Session session = Session.getInstance(getApplicationContext());
        final EditText radiusEt = (EditText) findViewById(R.id.radiusEt);

        //noinspection ConstantConditions
        radiusEt.setText(session.getSearchDistance() + "");
        radiusEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    final int searchDistance = Integer.parseInt(s.toString());
                    session.setSearchDistance(searchDistance);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
