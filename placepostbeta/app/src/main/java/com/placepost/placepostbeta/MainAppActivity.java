package com.placepost.placepostbeta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

public class MainAppActivity extends SingleActionBarFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new MainAppHomeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        int optionClickCount = 0;
        SharedPreferences prefs = getSharedPreferences(Constants.TEST_PREFS, MODE_PRIVATE);
        optionClickCount = prefs.getInt(Constants.OPTION_CLICK_COUNT, 0) + 1;

        SharedPreferences.Editor editor = getSharedPreferences(Constants.TEST_PREFS, MODE_PRIVATE).edit();
        editor.putInt(Constants.OPTION_CLICK_COUNT, optionClickCount);
        editor.commit();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            getSupportActionBar().setTitle("Settings");
            switchToFragment(new SettingsPageFragment());
            return true;
        } else if (id == R.id.action_profile) {
            getSupportActionBar().setTitle("Profile");
            switchToFragment(new ProfilePageFragment());
            return true;
        } else if (id == R.id.action_admin) {
            getSupportActionBar().setTitle("Admin");
            switchToFragment(new AdminPageFragment());
            return true;
        } else if (id == R.id.action_home) {
            getSupportActionBar().setTitle("PlacePosts");
            switchToFragment(new MainAppHomeFragment());
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction
                = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }



}
