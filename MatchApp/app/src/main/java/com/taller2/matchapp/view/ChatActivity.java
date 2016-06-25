package com.taller2.matchapp.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.taller2.matchapp.R;
import com.taller2.matchapp.api.MatchAPI;
import com.taller2.matchapp.http.MathAppJsonRequest;
import com.taller2.matchapp.http.VolleyClient;
import com.taller2.matchapp.model.Message;
import com.taller2.matchapp.util.KeyboardUtils;
import com.taller2.matchapp.view.adapters.MessagesAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by federicofarina on 6/22/16.
 */
public class ChatActivity extends BaseActivity {

    public static final String UPDATE = "update";
    public static String CHAT_ID_EXTRA = "CHAT_ID_EXTRA";

    private String chatID;
    private MessagesAdapter messagesAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        chatID = getIntent().getStringExtra(CHAT_ID_EXTRA);

        RecyclerView chatRv = (RecyclerView) findViewById(R.id.chatRv);
        //noinspection ConstantConditions
        layoutManager = new LinearLayoutManager(getApplicationContext());

        //noinspection ConstantConditions
        chatRv.setLayoutManager(layoutManager);

        messagesAdapter = new MessagesAdapter(getApplicationContext());
        chatRv.setAdapter(messagesAdapter);

        fetchMessages();


        final EditText postEt = (EditText) findViewById(R.id.sendAMessageTv);
        //noinspection ConstantConditions
        postEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (!TextUtils.isEmpty(v.getText())) {
                        KeyboardUtils.hideKeyboard(ChatActivity.this, v);
                        postNewMessage(v.getText().toString());
                        v.setText("");
                    } else {
                        v.clearFocus();
                        Toast.makeText(ChatActivity.this, getString(R.string.try_to_send_a_message_without_text), Toast.LENGTH_LONG).show();
                    }
                    return true;
                }

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void fetchMessages() {

        final String messagesEndpoint = MatchAPI.getMessagesEndpoint(chatID);
        final MathAppJsonRequest getMessagesRequest = new MathAppJsonRequest(this, messagesEndpoint) {
            @Override
            public void onSuccess(JSONObject data) {
                List<Message> messages = new ArrayList<>();
                JSONArray messagesJSONArray = data.optJSONArray("messages");
                if (messagesJSONArray != null) {
                    for (int index = 0; index < messagesJSONArray.length(); index++) {
                        JSONObject messageJSONObject = messagesJSONArray.optJSONObject(index);
                        if (messageJSONObject != null) {
                            Message message = new Message();
                            message.fromJson(messageJSONObject);
                            messages.add(message);
                        }
                    }

                    final int messagesCountBeforeUpdate = messagesAdapter.getItemCount();

                    messagesAdapter.setMessages(messages);

                    //Go to last message
                    if (messagesCountBeforeUpdate < messages.size()) {
                        layoutManager.scrollToPosition(messagesAdapter.getItemCount() - 1);
                    }
                }

                final MathAppJsonRequest updateRequest = this;

                if (!isFinishing() && !isDestroyed()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            VolleyClient.getInstance(getContext()).addToRequestQueue(updateRequest);
                        }
                    }, 5000);
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

        getMessagesRequest.setTag(UPDATE);
    }

    void postNewMessage(final String messageText) {

        final JSONObject requestBody = new JSONObject();

        try {
            requestBody.put("message", messageText);
        } catch (JSONException e) {
            //Never will happen
        }

        final MathAppJsonRequest sendMessageRequest = new MathAppJsonRequest(this, Request.Method.POST, MatchAPI.postAMessageEndpint(chatID), requestBody) {
            @Override
            public void onSuccess(JSONObject data) {
                String username = Session.getInstance(getContext()).getProfile().getAlias();
                Message message = new Message();
                message.sender = username;
                message.time = "";
                message.message = messageText;
                messagesAdapter.addItem(message);
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

        VolleyClient.getInstance(getApplicationContext()).addToRequestQueue(sendMessageRequest);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        final RequestQueue requestQueue = VolleyClient.getInstance(getApplicationContext()).getRequestQueue();
        requestQueue.cancelAll(UPDATE);
    }
}