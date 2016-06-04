package com.taller2.matchapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.taller2.matchapp.config.Configuration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private static final String REGISTER_URL = "/api/accounts/signup";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_SEX = "sex";
    public static final String KEY_ALIAS = "alias";
    public static final String KEY_EMAIL = "email";


    private static final char SEX_FEMALE = 'M';
    private static final char SEX_MALE = 'H';

    // UI references.
    private EditText mUsernameView;
    private EditText mAliasView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private RadioButton mSexFemale;
    private RadioButton mSexMale;
    private View mProgressView;
    private View mRegisterFormView;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the register form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mAliasView = (EditText) findViewById(R.id.alias);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSexFemale = (RadioButton) findViewById(R.id.sex_female);
        mSexMale = (RadioButton) findViewById(R.id.sex_male);


        Button mUserRegisterButton = (Button) findViewById(R.id.user_register_button);
        mUserRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);

        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {

        // Reset errors.
        mUsernameView.setError(null);
        mAliasView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String alias = mAliasView.getText().toString();
        String email = mEmailView.getText().toString();
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

        // Check for a valid alias.
        if (TextUtils.isEmpty(alias)) {
            mAliasView.setError(getString(R.string.error_field_required));
            focusView = mAliasView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            userRegister(username, sexo, alias, email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    private void userRegister(String mUsername, Character mSex, String mAlias, String mEmail, String mPassword) {
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put(KEY_USERNAME, mUsername);
            requestBody.put(KEY_PASSWORD, mPassword);

//          Commented until we have all fields on the svc
//          requestBody.put(KEY_SEX,mSex);
//          requestBody.put(KEY_ALIAS,mAlias);
//          requestBody.put(KEY_EMAIL,mEmail);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Configuration.getURL(this) + REGISTER_URL, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject data = response.optJSONObject("data");
                        String message = data.optString("message");
                        if (TextUtils.isEmpty(message) && message.equals("Successful signup")) {
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }, 2000);
                        } else if (TextUtils.isEmpty(message)) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.unknow_error), Toast.LENGTH_LONG).show();
                            showProgress(false);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false);
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject jsonObject = new JSONObject(responseBody);
                            JSONArray jsonArray = jsonObject.optJSONArray("errors");
                            String message = jsonArray.getJSONObject(0).getString("message");
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(RegisterActivity.this, getString(R.string.unknow_error), Toast.LENGTH_LONG).show();
                        }
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Shows the progress UI and hides the register form.
     */
    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
