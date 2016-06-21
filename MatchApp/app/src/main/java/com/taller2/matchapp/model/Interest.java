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
    public static final String ID = "id";

    public int id;
    public String value;
    public String category;

    public Interest() {

    }

    public String getCategory() {
        return category;
    }

    public String getValue() {
        return value;
    }

    public Object toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(ID, id);
            jsonObject.putOpt(VALUE, value);
            jsonObject.putOpt(CATEGORY, category);
        } catch (JSONException e) {
            //Never will happend
        }
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        id = jsonObject.optInt(ID);
        value = jsonObject.optString(VALUE);
        category = jsonObject.optString(CATEGORY);
    }

    @Override
    public String toString() {
        return String.format("%s-%s", category, value);
    }
}
