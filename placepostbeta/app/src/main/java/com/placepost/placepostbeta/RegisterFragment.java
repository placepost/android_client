package com.placepost.placepostbeta;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.placepostbeta.placepostbeta.apicaller.ApiCaller;

public class RegisterFragment extends Fragment implements OnApiTaskCompleted {

    private EditText username = null;
    private EditText email = null;
    private EditText password = null;
    private Button registerButton;

    // minimum length for a password
    private final int MIN_PASSWORD_LENGTH = 3;
    // minimum length for a username
    private final int MIN_USERNAME_LENGTH = 3;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.register_fragment, container, false);

        username = (EditText) rootView.findViewById(R.id.editText1);
        password = (EditText) rootView.findViewById(R.id.editText2);
        registerButton = (Button) rootView.findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });

        View backToLandingLink = (View) rootView.findViewById(R.id.backToLandingLink);
        backToLandingLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        View exitingUserLink = (View) rootView.findViewById(R.id.existingUserLink);
        exitingUserLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLoginFragment(v);
            }
        });

        return rootView;
    }

    public void switchToLoginFragment(View v) {
        switchToFragment(new LoginFragment());
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

    public void register(View view){
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
        // at this point we have pre-verified fields and will delegate to the api caller
        new ApiCaller(this, null).execute(ApiCaller.REGISTER_CALL, usernameString, passwordString);
    }

    @Override
    public void onApiTaskCompleted(String result) {
        Toast.makeText(
                getActivity().getApplicationContext(),
                result,
                Toast.LENGTH_SHORT).show();
    }

}
