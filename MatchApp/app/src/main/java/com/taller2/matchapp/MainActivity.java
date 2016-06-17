package com.taller2.matchapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.andtinder.model.CardModel;
import com.andtinder.view.CardContainer;
import com.andtinder.view.SimpleCardStackAdapter;
import com.taller2.matchapp.api.MatchAPI;
import com.taller2.matchapp.http.MathAppJsonRequest;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private CardContainer mCardContainer;
    private SimpleCardStackAdapter adapter;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);

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

        adapter = new SimpleCardStackAdapter(this);
        adapter.setShouldFillCardBackground(true);
        mCardContainer.setAdapter(adapter);
        fetchCandidates();
    }


    void fetchCandidates() {

        final MathAppJsonRequest registerRequest = new MathAppJsonRequest(this, MatchAPI.getCandidatesEndpoint()) {

            @Override
            public void onSuccess(JSONObject data) {
                Log.d("Response: ", data.toString());

                //Sacar esto cuando lo resolvamos en el appServer
                String dataString = data.toString().replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}");
                try {
                    data = new JSONObject(dataString);


                    JSONObject profile = data.optJSONObject("profile");

                    String name = profile.optString("name");
                    String age = profile.optString("age");

                    String description = String.format("%s (%s)", name, age);

                    String imageData = profile.optString("image");
                    byte[] decodedBytes = Base64.decode(imageData, 0);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);


                    CardModel cardModel = new CardModel("New candidate!", description, bitmap);
                    cardModel.setOnClickListener(new CardModel.OnClickListener() {
                        @Override
                        public void OnClickListener() {
                            Log.i("Swipeable Cards", "I am pressing the card");
                        }
                    });

                    cardModel.setOnCardDismissedListener(new CardModel.OnCardDismissedListener() {
                        @Override
                        public void onLike() {
                            Log.i("Swipeable Cards", "I like the card");
                        }

                        @Override
                        public void onDislike() {
                            Log.i("Swipeable Cards", "I dislike the card");
                        }
                    });

                    adapter.add(cardModel);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    getActivityIndicator().hide();
                    Toast.makeText(MainActivity.this, getString(R.string.unknown_error), Toast.LENGTH_LONG);
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
                params.put("Authorization", MatchAPI.getToken(MainActivity.this));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(registerRequest);
    }
}