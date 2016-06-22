package com.taller2.matchapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.taller2.matchapp.R;
import com.taller2.matchapp.model.Message;
import com.taller2.matchapp.view.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by federicofarina on 6/21/16.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageVH> {

    private final int MINE_MESSAGE = 1;
    private final int OTHER_MESSAGE = 2;

    private final String username;
    List<Message> messages = new ArrayList<>();

    public MessagesAdapter(Context context) {
        username = Session.getInstance(context).getProfile().getAlias();
    }

    @Override
    public MessageVH onCreateViewHolder(ViewGroup parent, int viewType) {

        View message = LayoutInflater.from(parent.getContext()).inflate(R.layout.other_message_row, parent, false);
        return new MessageVH(message);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if (username.equals(message.sender)) {
            return MINE_MESSAGE;
        } else {
            return OTHER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(MessageVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageVH extends RecyclerView.ViewHolder {

        public TextView messageTv;

        public MessageVH(View itemView) {
            super(itemView);
            messageTv= (TextView) itemView.findViewById(R.id.text);
        }
    }

}
