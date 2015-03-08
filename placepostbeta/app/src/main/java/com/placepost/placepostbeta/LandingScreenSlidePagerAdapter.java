package com.placepost.placepostbeta;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class LandingScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 3;

    private Context mContext;

    public LandingScreenSlidePagerAdapter(Context context, FragmentManager mgr) {
        super(mgr);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        LandingPageFragment fragment = new LandingPageFragment();
        Bundle args = new Bundle();
        args.putInt(LandingPageFragment.SLIDE_PARAM_NAME, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}
