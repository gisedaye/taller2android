package com.taller2.matchapp.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.taller2.matchapp.R;
import com.taller2.matchapp.api.MatchAPI;
import com.taller2.matchapp.http.MathAppJsonRequest;
import com.taller2.matchapp.http.VolleyClient;
import com.taller2.matchapp.model.Profile;
import com.taller2.matchapp.util.LocationManager;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivity {

    public static final String LIKE = "/like";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private CardContainer mCardContainer;
    private SimpleCardStackAdapter adapter;
    private ActionBarDrawerToggle drawerToggle;

    private View progressBar;
    private TextView candidatesTv;
    private boolean fetching = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);

        progressBar = findViewById(R.id.progress);
        candidatesTv = (TextView) findViewById(R.id.candidatesStateTv);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }


        };
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        ImageView profileIv = (ImageView) findViewById(R.id.profile_image);
        Profile profile = Session.getInstance(this).getProfile();

        try {
            String imageData = profile.getProfilePhoto();
            byte[] decodedBytes = Base64.decode(imageData, Base64.DEFAULT);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;


            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            if (bitmap == null) {
                //noinspection ConstantConditions
                profileIv.setImageResource(R.mipmap.ic_person);
            } else {
                //noinspection ConstantConditions
                profileIv.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            Log.e("Bad base 64", profile == null ? "" : profile.getProfilePhoto());
            //noinspection ConstantConditions
            profileIv.setImageResource(R.mipmap.ic_person);
        }

        adapter = new SimpleCardStackAdapter(this);
        adapter.setShouldFillCardBackground(false);


        final View refreshIv = findViewById(R.id.refreshIv);
        //noinspection ConstantConditions
        refreshIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fetching) {
                    adapter.pop();
                    fetchCandidates();
                    DrawerFragment drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                    if (drawerFragment != null) {
                        drawerFragment.fetchMatches();
                    }
                }
            }
        });


        final View likeIv = findViewById(R.id.likeIv);
        //noinspection ConstantConditions
        likeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CardModel model = adapter.pop();
                if (model != null) {
                    Profile profile = (Profile) model.getData();
                    String username = profile.getAlias();
                    performAction(MatchAPI.getLikeEndpoint(username), getString(R.string.liked));
                }
            }
        });


        final View dislikeIv = findViewById(R.id.dislikeIv);
        //noinspection ConstantConditions
        dislikeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CardModel model = adapter.pop();
                if (model != null) {
                    Profile profile = (Profile) model.getData();
                    String username = profile.getAlias();
                    performAction(MatchAPI.getDislikeEndpoint(username), getString(R.string.liked));
                }
            }
        });


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        DrawerFragment drawerFragment = new DrawerFragment();
        fragmentTransaction.replace(R.id.container, drawerFragment);
        fragmentTransaction.commit();

        //Fetch candidates when init
        fetchCandidates();
    }


    void fetchCandidates() {

        fetching = true;
        progressBar.setVisibility(View.VISIBLE);

        Location lastLocation = LocationManager.getInstance(getApplicationContext()).fetchLastLocation();

        if (lastLocation != null) {

            String radius = Session.getInstance(this).getSearchDistance() + "";
            String latitude = lastLocation.getLatitude() + "";
            String longitude = lastLocation.getLongitude() + "";

            final String candidatesEndpoint = MatchAPI.getCandidatesEndpoint(latitude, longitude, radius);
            final MathAppJsonRequest getCandidatesRequest = new MathAppJsonRequest(this, candidatesEndpoint) {

                @Override
                public void onSuccess(JSONObject data) {
                    fetching = false;
                    progressBar.setVisibility(View.GONE);
                    candidatesTv.setText(R.string.refresh_to_get_more_candidates);

                    try {
                        JSONObject profileJSON = data.optJSONObject("profile");

                        if (profileJSON != null && !profileJSON.isNull("alias")) {


                            final Profile profile = new Profile();

                            profile.fromJson(profileJSON);
                            String age = profile.getAge() + "";
                            String name = profile.getName();
                            final String username = profile.getAlias();

                            String description = String.format("%s (%s)", name, age);

                            Bitmap bitmap = null;
                            String imageData = profile.getProfilePhoto();

                            try {
                                byte[] decodedBytes = Base64.decode(imageData, 0);
                                bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                            } catch (Exception e) {
                                Log.e("Bad base 64", imageData);
                            }

                            CardModel cardModel;

                            if (bitmap != null) {
                                cardModel = new CardModel(description, bitmap);
                            } else {
                                cardModel = new CardModel(description, getDrawable(R.mipmap.ic_user));
                            }

                            cardModel.setData(profile);

                            cardModel.setOnFooterClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    goToProfile(profile);
                                }
                            });

                            cardModel.setOnClickListener(new CardModel.OnClickListener() {
                                @Override
                                public void OnClickListener() {
                                    goToProfile(profile);
                                }
                            });

                            cardModel.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
                                @Override
                                public void onLike() {
                                    adapter.pop();
                                    performAction(MatchAPI.getLikeEndpoint(username), getString(R.string.liked));
                                }

                                @Override
                                public void onDislike() {
                                    adapter.pop();
                                    performAction(MatchAPI.getDislikeEndpoint(username), getString(R.string.disliked));
                                }
                            });


                            adapter.add(cardModel);
                            mCardContainer.setAdapter(adapter);
                        } else {
                            candidatesTv.setText(R.string.no_more_candidates);
                        }
                    } catch (Exception e) {
                        getActivityIndicator().hide();
                        Toast.makeText(MainActivity.this, getString(R.string.unknown_error), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public int expectedCode() {
                    return HttpsURLConnection.HTTP_OK;
                }

                @Override
                public void onError(int statusCode) {
                    fetching = false;
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onNoConnection() {
                    fetching = false;
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", Session.getInstance(MainActivity.this).getToken());
                    return params;
                }
            };
            VolleyClient.getInstance(MainActivity.this).addToRequestQueue(getCandidatesRequest);
        } else {
            fetching = false;
            progressBar.setVisibility(View.GONE);
            candidatesTv.setText(R.string.cant_fetch_location);
        }
    }

    private void goToProfile(Profile profile) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ProfileDetailActivity.class);
        intent.putExtra(ProfileDetailActivity.PROFILE_EXTRA, profile);
        startActivity(intent);
    }

    private void performAction(final String endpoint, final String message) {


        final MathAppJsonRequest likeOrDislikeRequest = new MathAppJsonRequest(this, Request.Method.PUT, endpoint) {

            @Override
            public void onSuccess(JSONObject data) {
                if (endpoint.contains(LIKE)) {
                    boolean isAMatch = data.optBoolean("match", false);
                    if (isAMatch) {
                        Toast.makeText(MainActivity.this, R.string.that_was_a_match, Toast.LENGTH_SHORT).show();
                        DrawerFragment drawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                        if (drawerFragment != null) {
                            drawerFragment.fetchMatches();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public int expectedCode() {
                return HttpsURLConnection.HTTP_OK;
            }

            @Override
            public void onError(int statusCode) {
                getActivityIndicator().hide();
            }

            @Override
            public void onNoConnection() {
                getActivityIndicator().hide();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", Session.getInstance(MainActivity.this).getToken());
                return params;
            }
        };

        VolleyClient.getInstance(MainActivity.this).addToRequestQueue(likeOrDislikeRequest);
    }
}