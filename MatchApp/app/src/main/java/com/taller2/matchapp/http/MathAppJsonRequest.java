package com.taller2.matchapp.http;

import android.content.Context;
import android.widget.Toast;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.taller2.matchapp.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

/**
 * Created by fedefarina on 28/04/16.
 */
public abstract class MathAppJsonRequest extends JsonObjectRequest {

    private int statusCode;
    WeakReference<Context> contextWr;

    public MathAppJsonRequest(Context context, String url, JSONObject jsonRequest) {
        super(url, jsonRequest, null, null);
        contextWr = new WeakReference<>(context);
    }

    public MathAppJsonRequest(Context context, int method, String url, JSONObject jsonRequest) {
        super(method, url, jsonRequest, null, null);
        contextWr = new WeakReference<>(context);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        statusCode = response.statusCode;
        return super.parseNetworkResponse(response);
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        try {
            JSONObject data = response.getJSONObject("data");
            if (statusCode == expectedCode()) {
                onSuccess(data);
            } else {
                showFirstError(response);
                onError(statusCode);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        NetworkResponse response = error.networkResponse;
        if (response != null) {
            try {
                String body = new String(error.networkResponse.data, "UTF-8");
                JSONObject responseJSON = new JSONObject(body);

                if (expectedCode() == response.statusCode) {
                    JSONObject data = responseJSON.getJSONObject("data");
                    onSuccess(data);
                } else {
                    showFirstError(responseJSON);
                    onError(response.statusCode);
                }
            } catch (JSONException | UnsupportedEncodingException e) {
                Toast.makeText(getContext(), getContext().getString(R.string.unknow_error), Toast.LENGTH_LONG).show();
                onError(response.statusCode);
            }
        } else {
            Toast.makeText(getContext(), getContext().getString(R.string.unknow_error), Toast.LENGTH_LONG).show();
        }

    }


    private void showFirstError(JSONObject responseJSON) throws JSONException {
        JSONArray jsonArray = responseJSON.optJSONArray("errors");
        String message = jsonArray.getJSONObject(0).getString("message");
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    public Context getContext() {
        return contextWr.get();
    }

    public void onError(int statusCode) {

    }

    public void onSuccess(JSONObject data) {

    }

    public abstract int expectedCode();
}
