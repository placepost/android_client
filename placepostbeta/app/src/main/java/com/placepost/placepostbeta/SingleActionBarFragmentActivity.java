package com.placepost.placepostbeta;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;

public abstract class SingleActionBarFragmentActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, createFragment())
                    .commit();
        }
    }

    /**
     * Abstract method used to get the fragment that is used here.
     *
     * @return
     */
    protected abstract Fragment createFragment();

}

