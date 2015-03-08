package com.placepost.placepostbeta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsPageFragment extends Fragment {

    private SessionManager mSessionManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        mSessionManager = new SessionManager(getActivity().getApplicationContext());

        TextView userInfo = (TextView) rootView.findViewById(R.id.user_info);
        TextView userToken = (TextView) rootView.findViewById(R.id.user_token);
        if (mSessionManager.isLoggedIn()) {
            String usernameString = mSessionManager.getLoggedInUsername();
            String userTokenString = mSessionManager.getSessionToken();
            userInfo.setText("User logged in -- " + usernameString);
            userToken.setText("Token: " + userTokenString);
        } else {
            userInfo.setText("User not logged in");
        }

        View logoutLink = rootView.findViewById(R.id.logout_link);
        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        getActivity().getApplicationContext(),
                        "logging out user",
                        Toast.LENGTH_SHORT).show();
/* */

//                ProgressDialog progress = new ProgressDialog(getActivity());
                //progress.setTitle("Loading");
//                progress.setMessage("Logging out...");
//                progress.show();

                // log out user
                mSessionManager.logout();
/*
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {

                }
                /* */

                // To dismiss the dialog
                //progress.dismiss();

                // if successful load activity for main app home
                Intent intent = new Intent(getActivity(), LandingPageActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
