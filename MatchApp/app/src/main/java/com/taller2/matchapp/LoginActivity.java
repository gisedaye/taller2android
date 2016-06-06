package com.taller2.matchapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.taller2.matchapp.config.Configuration;
import com.taller2.matchapp.http.MathAppJsonRequest;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends BaseActivity {

    private static final String LOGIN_URL = "/api/accounts/login";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private View mProgressView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private TextView mRegisterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupLoginForm();
    }

    private void setupLoginForm() {
        mUsernameView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mRegisterView = (TextView) findViewById(R.id.link_register);
        mRegisterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterPage();
            }
        });
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

       /* // Check for a valid username
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }
*/
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            userLogin(username, password);
        }
    }

    private void goToRegisterPage() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    private void userLogin(String mUsername, String mPassword) {

        JSONObject requestBody = buildLoginBody(mUsername, mPassword);
        if (requestBody != null) {
            getActivityIndicator().show();

            final MathAppJsonRequest mathAppJsonRequest = new MathAppJsonRequest(this, Configuration.getURL(this) + LOGIN_URL, requestBody) {
                @Override
                public int expectedCode() {
                    return HttpsURLConnection.HTTP_ACCEPTED;
                }

                @Override
                public void onSuccess(JSONObject data) {
                    String token = data.optString("token");
                    //Configuration.setToken(LoginActivity.this, token);
                    Intent i = new Intent(LoginActivity.this, MatchActivity.class);
                    finish();
                    startActivity(i);
                }

                @Override
                public void onError(int statusCode) {
                    getActivityIndicator().hide();
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(mathAppJsonRequest);
        }
    }

    private JSONObject buildLoginBody(String mUsername, String mPassword) {
        JSONObject requestBody = null;
        try {
            requestBody = new JSONObject();
            requestBody.putOpt(KEY_USERNAME, mUsername);
            requestBody.putOpt(KEY_PASSWORD, mPassword);
        } catch (JSONException e) {
            Toast.makeText(this, "Missing username or password", Toast.LENGTH_LONG).show();
        }

        return requestBody;
    }
}

