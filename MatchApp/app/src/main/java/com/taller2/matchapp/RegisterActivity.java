package com.taller2.matchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.taller2.matchapp.api.MatchAPI;
import com.taller2.matchapp.http.MathAppJsonRequest;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity extends BaseActivity {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_SEX = "sex";


    private static final char SEX_FEMALE = 'M';
    private static final char SEX_MALE = 'H';

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private RadioButton mSexFemale;

    private TextView interestTv;
    private Toolbar myToolbar;
    private RadioButton mSexMale;

    private Integer[] selectedInterestIndices = new Integer[]{};
    private String[] interests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the register form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSexFemale = (RadioButton) findViewById(R.id.sex_female);
        mSexMale = (RadioButton) findViewById(R.id.sex_male);


        Button mUserRegisterButton = (Button) findViewById(R.id.user_register_button);
        //noinspection ConstantConditions
        mUserRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
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

        //Fetch interests from appServer
        getInterests();
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        Character sexo = mSexFemale.isChecked() ? SEX_FEMALE : SEX_MALE;

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            getActivityIndicator().show();
            userRegister(username, sexo, password);
        }
    }

    private void userRegister(String mUsername, Character mSex, String mPassword) {

        JSONObject requestBody = new JSONObject();

        try {
            requestBody.put(KEY_USERNAME, mUsername);
            requestBody.put(KEY_PASSWORD, mPassword);
            requestBody.put(KEY_SEX, mSex);
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
        getMenuInflater().inflate(R.menu.menu_register, menu);
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

    private void getInterests() {
        interests = new String[]{"music-band/aerosmith", "music-band/miranda", "music-band/La Portuaria"};
    }

    private void onEditInterests() {
        new MaterialDialog.Builder(this)
                .title(R.string.interests_dialog_title)
                .items(interests)
                .itemsCallbackMultiChoice(selectedInterestIndices, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        selectedInterestIndices = which;
                        if (which.length > 0) {
                            StringBuilder labelBuilder = new StringBuilder();
                            for (int index = 0; index < which.length; index++) {
                                labelBuilder.append(interests[index]);
                                if (index != which.length - 1) {
                                    labelBuilder.append(" , ");
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
}