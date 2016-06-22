package com.taller2.matchapp.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.taller2.matchapp.R;
import com.taller2.matchapp.model.Interest;
import com.taller2.matchapp.model.Profile;

import java.util.List;

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

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //noinspection ConstantConditions
        collapsingToolbar.setTitle(name);

        String email = currentProfile.getEmail();
        int age = currentProfile.getAge();
        String gender = currentProfile.getGender();

        if (!TextUtils.isEmpty(name)) {
            TextView nameTv = (TextView) findViewById(R.id.nameTv);
            //noinspection ConstantConditions
            nameTv.setText(name);
        } else {
            //noinspection ConstantConditions
            findViewById(R.id.nameLayout).setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(email)) {
            TextView emailTv = (TextView) findViewById(R.id.emailTv);
            //noinspection ConstantConditions
            emailTv.setText(email);
        } else {
            //noinspection ConstantConditions
            findViewById(R.id.emailLayout).setVisibility(View.GONE);
        }


        if (age != 0) {
            TextView ageTv = (TextView) findViewById(R.id.ageTv);
            //noinspection ConstantConditions
            ageTv.setText(age + "");
        } else {
            //noinspection ConstantConditions
            findViewById(R.id.ageLayout).setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(gender)) {
            TextView genderTv = (TextView) findViewById(R.id.genderTv);
            //noinspection ConstantConditions
            genderTv.setText(gender);
        } else {
            //noinspection ConstantConditions
            findViewById(R.id.genderLayout).setVisibility(View.GONE);
        }

        String profilePhoto = currentProfile.getProfilePhoto();


        List<Interest> interests = currentProfile.getInterests();

        if (!interests.isEmpty()) {
            StringBuilder interestsBuilder = new StringBuilder();
            for (Interest interest : interests) {
                interestsBuilder.append(interest).append("\n");
            }
            TextView interestsTv = (TextView) findViewById(R.id.interestTv);
            //noinspection ConstantConditions
            interestsTv.setText(interestsBuilder.toString());
        } else {
            //noinspection ConstantConditions
            findViewById(R.id.interestsLayout).setVisibility(View.GONE);
        }

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
