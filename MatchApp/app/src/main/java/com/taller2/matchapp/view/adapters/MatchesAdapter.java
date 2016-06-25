package com.taller2.matchapp.view.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.taller2.matchapp.R;
import com.taller2.matchapp.model.Match;
import com.taller2.matchapp.model.Profile;
import com.taller2.matchapp.util.OnRowClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by federicofarina on 6/21/16.
 */
public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchVH> {

    private List<Match> matches = new ArrayList<>();
    private final OnRowClickListener onClickListener;


    public MatchesAdapter(OnRowClickListener onRowClickListener) {
        onClickListener = onRowClickListener;
    }

    @Override
    public MatchVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_row, parent, false);
        return new MatchVH(view);
    }

    @Override
    public void onBindViewHolder(MatchVH holder, int position) {

        Match match = matches.get(position);

        final Profile profile = match.getProfile();

        String imageData = profile.getProfilePhoto();

        try {
            byte[] decodedBytes = Base64.decode(imageData, 0);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            if (bitmap == null) {
                holder.profileIv.setImageResource(R.mipmap.ic_person);
            } else {
                holder.profileIv.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            Log.e("Bad base 64", imageData);
            holder.profileIv.setImageResource(R.mipmap.ic_person);
        }

        holder.usernameTv.setText(profile.getAlias());
    }

    public Match getMatch(int position) {
        return matches.get(position);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    public class MatchVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profileIv;
        TextView usernameTv;

        public MatchVH(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            usernameTv = (TextView) itemView.findViewById(R.id.username);
            profileIv = (ImageView) itemView.findViewById(R.id.profile_image);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onRowClick(getAdapterPosition());
            }
        }
    }
}
