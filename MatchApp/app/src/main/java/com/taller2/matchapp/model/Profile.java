package com.taller2.matchapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.taller2.matchapp.util.Serializable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fedefarina on 20/06/16.
 */
public class Profile implements Serializable, Parcelable {

    private static final String NAME = "name";
    private static final String ALIAS = "alias";
    private static final String EMAIL = "email";
    private static final String GENDER = "gender";
    private static final String AGE = "age";
    private static final String PHOTO_PROFILE = "photo_profile";
    public static final String INTERESTS = "interests";

    private int age;
    private String name;
    private String alias;
    private String email;
    private String gender;
    private String photo_profile;

    private List<Interest> interests = new ArrayList<>();

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
    public JSONObject toJson() {
        JSONObject profile = new JSONObject();
        try {
            profile.putOpt(NAME, name);
            profile.putOpt(AGE, age);
            profile.putOpt(EMAIL, email);
            profile.putOpt(GENDER, gender);
            profile.putOpt(PHOTO_PROFILE, photo_profile);
            profile.putOpt(ALIAS, alias);

            if (!interests.isEmpty()) {
                JSONArray interestsJSONArray = new JSONArray();
                for (Interest interest : interests) {
                    interestsJSONArray.put(interest.toJson());
                }
                profile.putOpt(INTERESTS, interestsJSONArray);
            }

        } catch (JSONException e) {
            //Will be OK
        }
        return profile;
    }

    @Override
    public void fromJson(JSONObject jsonObject) {
        age = jsonObject.optInt(AGE);
        name = jsonObject.optString(NAME);
        alias = jsonObject.optString(ALIAS);
        email = jsonObject.optString(EMAIL);
        gender = jsonObject.optString(GENDER);
        photo_profile = jsonObject.optString(PHOTO_PROFILE);

        JSONArray interestsJSONArray = jsonObject.optJSONArray(INTERESTS);
        if (interestsJSONArray != null) {
            for (int index = 0; index < interestsJSONArray.length(); index++) {
                JSONObject interestJSON = interestsJSONArray.optJSONObject(index);
                if (interestJSON != null) {
                    Interest interest = new Interest();
                    interest.fromJson(interestJSON);
                    interests.add(interest);
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getProfilePhoto() {
        return photo_profile;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public List<Interest> getInterests() {
        return interests;
    }
}
