package com.taller2.matchapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class MatchFragment extends Fragment {

    private FloatingActionButton mLikeActionButton;
    private FloatingActionButton mDislikeActionButton;

    private static final String LIKE_URL = "/api/accounts/";
    private static final String LIKE = "like";
    private static final String DISLIKE = "dislike";

    public static final String AUTHORIZATION = "Authorization";
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_match, container, false);

        mLikeActionButton = (FloatingActionButton) getActivity().findViewById(R.id.like);
        /*mLikeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeAction();
            }
        });*/
        mDislikeActionButton = (FloatingActionButton) getActivity().findViewById(R.id.dislike);
        /*mDislikeActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dislikeAction();
            }
        });*/
        context = this.getContext();
        return rootView;
    }


    private void likeAction() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(AUTHORIZATION, Configuration.getToken(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Configuration.getURL(this.getContext()) + LIKE_URL + 1 + "/" + LIKE, obj,
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
                            //TODO
                        } else {
                            Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(context, getString(R.string.unknow_error), Toast.LENGTH_LONG).show();
                        }

                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    private void dislikeAction() {
        JSONObject obj = new JSONObject();
        try {
            obj.put(AUTHORIZATION, Configuration.getToken(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Configuration.getURL(context) + LIKE_URL + 1 + "/" + DISLIKE, obj,
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
                            //TODO
                        } else {
                            Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(context, getString(R.string.unknow_error), Toast.LENGTH_LONG).show();
                        }

                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

}
