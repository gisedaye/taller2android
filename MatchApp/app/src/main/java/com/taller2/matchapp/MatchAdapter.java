package com.taller2.matchapp;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by gisedaye on 02/06/16.
 */

public class MatchAdapter extends FragmentStatePagerAdapter {
    public MatchAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new MatchFragment();
    }

    @Override
    public int getCount() {
        return 20;
    }

}
