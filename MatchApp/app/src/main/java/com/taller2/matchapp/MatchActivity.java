package com.taller2.matchapp;

import java.util.Locale;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
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

public class MatchActivity extends FragmentActivity {
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;
    private static final String LIKE_URL = "/api/accounts/";
    private static final String LIKE = "like";
    private static final String DISLIKE = "dislike";

    public static final String AUTHORIZATION = "Authorization";

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    private FloatingActionButton mLikeActionButton;
    private FloatingActionButton mDislikeActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new MatchAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mLikeActionButton = (FloatingActionButton) findViewById(R.id.like);
        mLikeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeAction();
            }
        });
        mDislikeActionButton = (FloatingActionButton) findViewById(R.id.dislike);
        mDislikeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dislikeAction();
            }
        });
    }

    private void likeAction() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(AUTHORIZATION, Configuration.getToken(MatchActivity.this));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Configuration.getURL(this) + LIKE_URL + mPager.getCurrentItem() + "/" + LIKE, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String message = null;
                        try {
                            message = response.getJSONObject("data").getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (message.equals("Like successful")) {
                            //Go to next candidate
                            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                        } else {
                            Toast.makeText(MatchActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject jsonObject = new JSONObject(responseBody);
                            JSONArray jsonArray = jsonObject.optJSONArray("errors");
                            String message = jsonArray.getJSONObject(0).getString("message");
                            Toast.makeText(MatchActivity.this, message, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(MatchActivity.this, getString(R.string.unknow_error), Toast.LENGTH_LONG).show();
                        }

                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(MatchActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void dislikeAction() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(AUTHORIZATION, Configuration.getToken(MatchActivity.this));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Configuration.getURL(this) + LIKE_URL + mPager.getCurrentItem() + "/" + DISLIKE, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String message = null;
                        try {
                            message = response.getJSONObject("data").getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (message.equals("Dislike successful")) {
                            //Go to next candidate
                            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                        } else {
                            Toast.makeText(MatchActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject jsonObject = new JSONObject(responseBody);
                            JSONArray jsonArray = jsonObject.optJSONArray("errors");
                            String message = jsonArray.getJSONObject(0).getString("message");
                            Toast.makeText(MatchActivity.this, message, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(MatchActivity.this, getString(R.string.unknow_error), Toast.LENGTH_LONG).show();
                        }

                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(MatchActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class MatchAdapter extends FragmentStatePagerAdapter {
        public MatchAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MatchFragment();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
