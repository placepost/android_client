package com.placepost.placepostbeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.placepostbeta.placepostbeta.apicaller.ApiCaller;

public class LandingPageFragment extends Fragment {

    private Button registerButton;

    private int mSlideNumber;

    private final int DEFAULT_SLIDE_NUMBER = 1;
    public static final String SLIDE_PARAM_NAME = "slideNumber";

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.landing_fragment, container, false);

        registerButton = (Button) rootView.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(new RegisterFragment());
            }
        });

        // set slide number if we have it in arguments
        if (getArguments() != null) {
            mSlideNumber = getArguments().getInt(SLIDE_PARAM_NAME, DEFAULT_SLIDE_NUMBER);
            View mainContainer = rootView.findViewById(R.id.landing_main_container);
            TextView sloganTextView = (TextView) rootView.findViewById(R.id.slogan_text);
            if (mainContainer != null) {
                int backgroundId = R.drawable.landing_1;
                int sloganStringId = R.string.slide_1_text;
                if (mSlideNumber == 1) {
                    backgroundId = R.drawable.landing_2;
                    sloganStringId = R.string.slide_2_text;
                } else if (mSlideNumber == 2) {
                    backgroundId = R.drawable.landing_3;
                    sloganStringId = R.string.slide_3_text;
                }
                // set drawable to the right id
                mainContainer.setBackgroundDrawable(
                        getResources().getDrawable(backgroundId));

                // set the slogan to the right text resource
                if (sloganTextView != null) {
                    sloganTextView.setText(sloganStringId);
                }
            }
        }

        View loginLink = (View) rootView.findViewById(R.id.login_link);
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(new LoginFragment());
            }
        });

        return rootView;
    }

    public void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction
                = getActivity().getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }



}
