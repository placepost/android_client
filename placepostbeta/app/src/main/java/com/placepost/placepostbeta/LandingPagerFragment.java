package com.placepost.placepostbeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LandingPagerFragment extends Fragment {
    public SessionManager mSessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.landing_page_pager, container, false);
        ViewPager pager = (ViewPager)result.findViewById(R.id.landing_slides_pager);
        pager.setAdapter(
                new LandingScreenSlidePagerAdapter(getActivity(), getChildFragmentManager()));
        mSessionManager = new SessionManager(getActivity().getApplicationContext());
        return(result);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mSessionManager.isLoggedIn()) {
            // if successful load activity for main app home
            Intent intent = new Intent(getActivity(), MainAppActivity.class);
            startActivity(intent);
        }
    }


}

