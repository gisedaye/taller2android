package com.taller2.matchapp.model;

import com.taller2.matchapp.util.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by federicofarina on 6/17/16.
 */
public class Interest implements Serializable {

    public static final String VALUE = "value";
    public static final String CATEGORY = "category";

    public String value;
    public String category;

    public Interest() {

    }

    public Interest(String value, String category) {
        this.value = value;
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject();
            jsonObject.putOpt(VALUE, value);
            jsonObject.putOpt(CATEGORY, category);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        value = jsonObject.optString(VALUE);
        category = jsonObject.optString(CATEGORY);
    }

    @Override
    public String toString() {
        return String.format("%s-%s", category, value);
    }
}
