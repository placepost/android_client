package com.placepost.placepostbeta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.placepostbeta.placepostbeta.apicaller.ApiCaller;

import org.json.JSONException;
import org.json.JSONObject;

public class MainAppHomeFragment extends Fragment implements OnApiTaskCompleted {

    private SessionManager mSessionManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_app_home_fragment, container, false);
        mSessionManager = new SessionManager(getActivity().getApplicationContext());
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        // at this point we have pre-verified fields and will delegate to the api caller
        String identifier1 = "qG31Py0b3LbOjOmXs6v8Rw==";
        String identifier2 = "RdFhbaskX1jXV/x8H5h6mQ==";

        // authorization contexts https://postboard.qor.io/postboard/v1/contexts
        // need to pass in list of beacons encoded base64UrlEncode(bytes(uuid) + bytes(major) + bytes(minor))
        // eg X-Postboard-Context-Identifier qG31Py0b3LbOjOmXs6v8Rw==
        // eg X-Postboard-Context-Identifier RdFhbaskX1jXV/x8H5h6mQ==

        String userAuthToken = mSessionManager.getSessionToken();
        ApiCaller apiCaller = new ApiCaller(this, userAuthToken);
        Log.v("foo", mSessionManager.getSessionToken());
        apiCaller.execute(ApiCaller.GET_CONTEXTS_CALL, identifier1, identifier2);
    }

    @Override
    public void onApiTaskCompleted(String result) {
        Toast.makeText(
                getActivity().getApplicationContext(),
                "Redirecting...",
                Toast.LENGTH_SHORT).show();
    }



    // authorization me  https://postboard.qor.io/postboard/v1/me Bearer (token)
    /*
        {
            "id": "4a0c73ab-a00b-11e4-8051-0242ac110062",
            "name": "poster34",
            "is_admin": false,
            "is_api": false
        }
     */

    // authorization contexts https://postboard.qor.io/postboard/v1/contexts
    // need to pass in list of beacons encoded base64Encode(bytes(uuid) + bytes(major) + bytes(minor))
    // eg X-Postboard-Context-Identifier qG31Py0b3LbOjOmXs6v8Rw==
    // eg X-Postboard-Context-Identifier RdFhbaskX1jXV/x8H5h6mQ==
    /*
    [
    {
        "id": "109b2eab-9636-11e4-8ed6-42010af04554",
        "url": "http://beacons.placepost.io/v1/instance/12345",
        "type_url": "http://beacons.placepost.io/metadata/ibeacon",
        "identifier": "qG31Py0b3LbOjOmXs6v8Rw==",
        "tags": [
            "kitchen",
            "cooking",
            "home",
            "2645 polk",
            "fridge",
            "meals"
        ]
    },
    {
        "id": "9b247896-963e-11e4-b435-42010af04554",
        "url": "http://beacons.placepost.io/v1/instance/12345",
        "type_url": "http://beacons.placepost.io/metadata/ibeacon",
        "identifier": "RdFhbaskX1jXV/x8H5h6mQ==",
        "tags": [
            "office",
            "work",
            "650 townsend"
        ]
    }
]
     */



}
