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
    private List<Message> messages = new ArrayList<>();

    public MessagesAdapter(Context context) {
        username = Session.getInstance(context).getProfile().getAlias();
    }

    @Override
    public MessageVH onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = viewType == MINE_MESSAGE ? R.layout.mine_message_row : R.layout.other_message_row;
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MessageVH(view);
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
        Message message = messages.get(position);
        holder.messageTv.setText(message.getMessage());
        holder.timeStampTv.setText(message.getTime());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public class MessageVH extends RecyclerView.ViewHolder {

        public TextView messageTv;
        public TextView timeStampTv;

        public MessageVH(View itemView) {
            super(itemView);
            messageTv = (TextView) itemView.findViewById(R.id.message);
            timeStampTv = (TextView) itemView.findViewById(R.id.messageTime);
        }
    }
}
