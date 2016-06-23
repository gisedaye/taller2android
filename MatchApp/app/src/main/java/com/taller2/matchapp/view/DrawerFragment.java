package com.taller2.matchapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.taller2.matchapp.R;
import com.taller2.matchapp.api.MatchAPI;
import com.taller2.matchapp.http.MathAppJsonRequest;
import com.taller2.matchapp.model.Match;
import com.taller2.matchapp.util.OnRowClickListener;
import com.taller2.matchapp.view.adapters.MatchesAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by federicofarina on 6/21/16.
 */
public class DrawerFragment extends Fragment {

    private MatchesAdapter matchesAdapter;

    public DrawerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View logoutView = view.findViewById(R.id.logout);

        //noinspection ConstantConditions
        logoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getInstance(getContext()).logout();
                Intent intent = new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        final View settingsView = view.findViewById(R.id.settings);
        //noinspection ConstantConditions
        settingsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });


        RecyclerView rv = (RecyclerView) view.findViewById(R.id.matchesRv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        matchesAdapter = new MatchesAdapter(new OnRowClickListener() {
            @Override
            public void onRowClick(int position) {
                Intent intent = new Intent();
                intent.setClass(getContext(), ChatActivity.class);
                intent.putExtra(ChatActivity.CHAT_ID_EXTRA, matchesAdapter.getMatch(position).getChatID());
                startActivity(intent);
            }
        });


        rv.setAdapter(matchesAdapter);

        fetchMatches();
    }

    public void fetchMatches() {

        final MathAppJsonRequest getMatchesRequest = new MathAppJsonRequest(getContext(), MatchAPI.getMatchesEndpoint()) {

            @Override
            public void onSuccess(JSONObject data) {
                List<Match> matches = new ArrayList<>();
                JSONArray matchesJSONArray = data.optJSONArray("matches");
                if (matchesJSONArray != null) {
                    for (int index = 0; index < matchesJSONArray.length(); index++) {
                        JSONObject matchJSONObject = matchesJSONArray.optJSONObject(index);
                        if (matchJSONObject != null) {
                            Match match = new Match();
                            match.fromJson(matchJSONObject);
                            matches.add(match);
                        }
                    }
                    matchesAdapter.setMatches(matches);
                }
            }

            @Override
            public int expectedCode() {
                return HttpsURLConnection.HTTP_OK;
            }

            @Override
            public void onError(int statusCode) {
            }

            @Override
            public void onNoConnection() {
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", Session.getInstance(getContext()).getToken());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(getMatchesRequest);
    }

}
