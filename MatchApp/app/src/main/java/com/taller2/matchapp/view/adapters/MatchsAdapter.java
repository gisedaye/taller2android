package com.taller2.matchapp.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.taller2.matchapp.model.Match;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by federicofarina on 6/21/16.
 */
public class MatchsAdapter extends RecyclerView.Adapter<MatchsAdapter.MatchVH> {

    private List<Match> matches = new ArrayList<>();

    @Override
    public MatchVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MatchVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public class MatchVH extends RecyclerView.ViewHolder {

        public MatchVH(View itemView) {
            super(itemView);
        }
    }
}
