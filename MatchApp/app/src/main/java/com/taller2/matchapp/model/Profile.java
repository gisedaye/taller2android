package com.taller2.matchapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.taller2.matchapp.util.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fedefarina on 20/06/16.
 */
public class Profile implements Serializable, Parcelable {

    private static final String NAME = "name";
    private static final String ALIAS = "alias";
    private static final String EMAIL = "email";
    private static final String AGE = "age";
    private static final String PHOTO_PROFILE = "photo_profile";

    private int age;
    private String name;
    private String alias;
    private String email;
    private String photo_profile;

    public static final Parcelable.Creator<Profile> CREATOR
            = new Parcelable.Creator<Profile>() {
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public Profile() {

    }

    public Profile(Parcel in) {
        String jsonString = in.readString();
        try {
            fromJson(new JSONObject(jsonString));
        } catch (JSONException e) {
            //Always a valid JSON here
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String jsonString = toJson().toString();
        dest.writeString(jsonString);
    }

    @Override
    public Object toJson() {
        JSONObject profile = new JSONObject();
        try {
            profile.putOpt(NAME, name);
            profile.putOpt(AGE, age);
            profile.putOpt(EMAIL, email);
            profile.putOpt(PHOTO_PROFILE, photo_profile);
            profile.putOpt(ALIAS, alias);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return profile;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        age = jsonObject.optInt(AGE);
        name = jsonObject.optString(NAME);
        alias = jsonObject.optString(ALIAS);
        email = jsonObject.optString(EMAIL);
        photo_profile = jsonObject.optString(PHOTO_PROFILE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getProilePhoto() {
        return photo_profile;
    }
}
