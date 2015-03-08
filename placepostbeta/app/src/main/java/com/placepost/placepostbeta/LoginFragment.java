package com.placepost.placepostbeta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.placepostbeta.placepostbeta.apicaller.ApiCaller;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment implements OnApiTaskCompleted {

    private EditText username = null;
    private EditText password = null;
    private Button login;

    private SessionManager mSessionManager;

    ProgressDialog mProgressDialog;

    // minimum length for a password
    private final int MIN_PASSWORD_LENGTH = 3;
    // minimum length for a username
    private final int MIN_USERNAME_LENGTH = 3;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_fragment, container, false);

        mSessionManager = new SessionManager(getActivity().getApplicationContext());

        username = (EditText) rootView.findViewById(R.id.editText1);
        password = (EditText) rootView.findViewById(R.id.editText2);
        login = (Button) rootView.findViewById(R.id.button1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        View registerLink = (View) rootView.findViewById(R.id.registerLink);
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToRegisterFragment(v);
            }
        });

        return rootView;
    }

    public void switchToRegisterFragment(View v) {
        switchToFragment(new RegisterFragment());
    }

    public void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction
                = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Note test login:
     *
     *
     * @param view
     */

    public void login(View view){
        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();

        // do some validation on the username and password
        if (usernameString.length() < MIN_USERNAME_LENGTH) {
            // handle error case
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Username must be at least " + MIN_USERNAME_LENGTH + " long",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (passwordString.length() < MIN_PASSWORD_LENGTH) {
            // handle error case
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Password must be at least " + MIN_PASSWORD_LENGTH + " long",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        showProgress("Logging in...");
        // at this point we have pre-verified fields and will delegate to the api caller
        new ApiCaller(this, null).execute(ApiCaller.LOGIN_CALL, usernameString, passwordString);
    }

    private void showProgress(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    @Override
    public void onApiTaskCompleted(String result) {
        mProgressDialog.hide();
        boolean loginSuccessful = false;
        String token = null;
        String username = null;
        try {
            JSONObject resultObj = new JSONObject(result);
            if (resultObj.has("token")) {
                token = resultObj.getString("token");
                loginSuccessful = true;
            }
            if (resultObj.has("username")) {
                username = resultObj.getString("username");
            }
        } catch(JSONException jsonEx) {
            loginSuccessful = false;
        }

        // if the login is successful, store the login token in shared prefs and login
        if (loginSuccessful) {
            mSessionManager.logUserIn(username, token);
            // if successful load activity for main app home
            Intent intent = new Intent(getActivity(), MainAppActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Login failed",
                    Toast.LENGTH_SHORT).show();
        }
/*
        if (result == result.LOGIN_SUCCESS) {
            Toast.makeText(
                getApplicationContext(),
                "Redirecting...",
                Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                    Toast.LENGTH_SHORT).show();
        }
*/
    }

}
