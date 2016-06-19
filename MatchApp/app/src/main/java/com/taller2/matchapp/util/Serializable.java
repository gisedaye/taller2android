package com.taller2.matchapp.util;

import org.json.JSONObject;

/**
 * Created by federicofarina on 6/17/16.
 */
public interface Serializable {
    Object toJson();

    void fromJson(JSONObject jsonObject);
}
