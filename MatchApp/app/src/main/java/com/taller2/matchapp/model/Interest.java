package com.taller2.matchapp.model;

import com.taller2.matchapp.util.Serializable;
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

    public String getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public Object toJson() {
        return id;
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
