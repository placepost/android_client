package com.placepost.placepostbeta;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfilePageFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView clickCountText = (TextView) rootView.findViewById(R.id.click_count);

        SharedPreferences prefs = getActivity().getSharedPreferences(
                Constants.TEST_PREFS,
                getActivity().MODE_PRIVATE);
        Integer optionClickCount = new Integer(prefs.getInt(Constants.OPTION_CLICK_COUNT, 0));
        clickCountText.setText(optionClickCount.toString());
        return rootView;
    }
}
