package com.taller2.matchapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.taller2.matchapp.model.Profile;

/**
 * Created by federicofarina on 6/20/16.
 */
public class ProfileDetailActivity extends BaseActivity {

    public static final String PROFILE_EXTRA = "PROFILE";

    private Profile currentProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //noinspection Duplicates
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar supportActionBar = getSupportActionBar();
            supportActionBar.setHomeButtonEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        currentProfile = getIntent().getParcelableExtra(PROFILE_EXTRA);
        showProfileData();
    }


    private void showProfileData() {
        String name = currentProfile.getName();
        String email = currentProfile.getAlias();

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //noinspection ConstantConditions
        collapsingToolbar.setTitle(name);

        TextView description = (TextView) findViewById(R.id.description);
        //noinspection ConstantConditions
        description.setText(email);

        String profilePhoto = currentProfile.getProilePhoto();

        if (!TextUtils.isEmpty(profilePhoto)) {
            loadBackdrop(profilePhoto);
        }
    }

    private void loadBackdrop(String imageData) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        byte[] decodedBytes = Base64.decode(imageData, 0);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        //noinspection ConstantConditions
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
