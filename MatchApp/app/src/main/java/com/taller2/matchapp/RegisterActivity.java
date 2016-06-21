package com.taller2.matchapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.soundcloud.android.crop.Crop;
import com.taller2.matchapp.api.MatchAPI;
import com.taller2.matchapp.http.MathAppJsonRequest;
import com.taller2.matchapp.model.Interest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends BaseActivity {

    private static final String KEY_AGE = "age";
    private static final String KEY_NAME = "name";
    public static final String KEY_GENDER = "gender";
    private static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    private static final String KEY_INTERESTS = "interests";
    private static final String KEY_PHOTO_PROFILE = "photo_profile";

    private static final char SEX_MALE = 'H';
    private static final char SEX_FEMALE = 'M';

    private static final int PICK_IMAGE_REQUEST_CODE = 1;


    // UI references.
    private EditText ageEt;
    private EditText nameEt;
    private EditText usernameEt;
    private EditText passwordEt;
    private RadioButton sexFemaleRb;

    private TextView interestTv;
    private Toolbar myToolbar;

    private List<Interest> interests;
    private Integer[] selectedInterestIndices = new Integer[]{};

    //Profile photo
    private EditText emailEt;
    private Uri currentPhotoUri;
    private ImageView profileIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Set up the register form.
        usernameEt = (EditText) findViewById(R.id.username);
        passwordEt = (EditText) findViewById(R.id.password);
        sexFemaleRb = (RadioButton) findViewById(R.id.sex_female);
        ageEt = (EditText) findViewById(R.id.age);
        emailEt = (EditText) findViewById(R.id.emailEt);
        nameEt = (EditText) findViewById(R.id.nameEt);

        Button mUserRegisterButton = (Button) findViewById(R.id.user_register_button);
        //noinspection ConstantConditions
        mUserRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempRegister();
            }
        });

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        interestTv = (TextView) findViewById(R.id.interests);
        //noinspection ConstantConditions
        interestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditInterests();
            }
        });

        profileIv = (ImageView) findViewById(R.id.profile_image);
        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage(PICK_IMAGE_REQUEST_CODE);
            }
        });

        //Fetch interests from appServer
        fetchInterests();
    }

    private void pickImage(int requestCode) {
        Crop.pickImage(this, requestCode);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
        if (requestCode == PICK_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                beginCrop(result.getData());
            } else {
                profileIv.setImageDrawable(null);
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                Glide.with(this)
                        .load(Crop.getOutput(result))
                        .into(profileIv);
            } else if (resultCode == Crop.RESULT_ERROR) {
                Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private void attempRegister() {

        // Reset errors.
        usernameEt.setError(null);
        passwordEt.setError(null);
        ageEt.setError(null);

        // Store values at the time of the login attempt.
        String username = usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        Character gender = sexFemaleRb.isChecked() ? SEX_FEMALE : SEX_MALE;
        String age = ageEt.getText().toString();
        String email = emailEt.getText().toString();
        String name = nameEt.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            usernameEt.setError(getString(R.string.error_field_required));
            focusView = usernameEt;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordEt.setError(getString(R.string.error_field_required));
            focusView = passwordEt;
            cancel = true;
        }

        if (TextUtils.isEmpty(age)) {
            ageEt.setError(getString(R.string.error_field_required));
            focusView = ageEt;
            cancel = true;
        }

        String currentEncodedImage = null;

        if (currentPhotoUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), currentPhotoUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] image = stream.toByteArray();
                currentEncodedImage = Base64.encodeToString(image, Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            getActivityIndicator().show();
            userRegister(username, password, gender, age, email, currentEncodedImage, name);
        }
    }

    private void userRegister(String username, String password, Character mSex, String age, String email, String encodedImage, String name) {

        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put(KEY_USERNAME, username);
            requestBody.put(KEY_PASSWORD, password);
            requestBody.put(KEY_GENDER, mSex);
            requestBody.put(KEY_AGE, Integer.valueOf(age));
            requestBody.put(KEY_EMAIL, email);
            requestBody.put(KEY_NAME, name);

            JSONArray jsonArray = new JSONArray();
            for (Interest interest : interests) {
                jsonArray.put(interest.id);
            }

            requestBody.put(KEY_INTERESTS, jsonArray);
            requestBody.putOpt(KEY_PHOTO_PROFILE, encodedImage);
        } catch (JSONException e) {
            //Never will happen
        }

        final MathAppJsonRequest registerRequest = new MathAppJsonRequest(this, Request.Method.POST, MatchAPI.getRegisterEndpoint(), requestBody) {

            @Override
            public void onSuccess(JSONObject data) {
                String message = data.optString("message");
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public int expectedCode() {
                return HttpsURLConnection.HTTP_OK;
            }

            @Override
            public void onError(int statusCode) {
                getActivityIndicator().hide();
            }

            @Override
            public void onNoConnection() {
                getActivityIndicator().hide();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(registerRequest);
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

    private void fetchInterests() {

        final MathAppJsonRequest registerRequest = new MathAppJsonRequest(this, MatchAPI.getInterestsEndpoint()) {

            @Override
            public void onSuccess(JSONObject data) {
                activityIndicator.hide();
                interests = new ArrayList<>();
                JSONArray interestsArray = data.optJSONArray("interests");
                for (int index = 0; index < interestsArray.length(); index++) {
                    JSONObject interestJO = interestsArray.optJSONObject(index);
                    Interest interest = new Interest();
                    interest.fromJson(interestJO);
                    interests.add(interest);
                }
            }

            @Override
            public int expectedCode() {
                return HttpsURLConnection.HTTP_OK;
            }

            @Override
            public void onError(int statusCode) {
                activityIndicator.hide();
            }

            @Override
            public void onNoConnection() {
                activityIndicator.hide();
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(registerRequest);
    }

    private void onEditInterests() {
        if (interests == null) {
            activityIndicator.show();
            fetchInterests();
        } else {
            showInterestsDialog();
        }
    }

    private void showInterestsDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.interests_dialog_title)
                .theme(Theme.LIGHT)
                .items(interests)
                .itemsCallbackMultiChoice(selectedInterestIndices, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        selectedInterestIndices = which;
                        if (which.length > 0) {
                            StringBuilder labelBuilder = new StringBuilder();
                            for (int index = 0; index < which.length; index++) {
                                final String selectedInterest = interests.get(index).toString();
                                labelBuilder.append(selectedInterest);
                                if (index != which.length - 1) {
                                    labelBuilder.append(", ");
                                }
                            }
                            interestTv.setText(labelBuilder.toString());
                        } else {
                            interestTv.setHint(R.string.interests_dialog_title);
                        }

                        return true;
                    }
                })
                .positiveText(R.string.choose)
                .show();
    }

    private void beginCrop(Uri source) {
        currentPhotoUri = Uri.fromFile(new File(getCacheDir(), "cropped" + System.currentTimeMillis()));
        Crop.of(source, currentPhotoUri).asSquare().start(this);
    }
}