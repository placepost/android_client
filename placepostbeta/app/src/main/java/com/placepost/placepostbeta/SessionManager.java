package com.placepost.placepostbeta;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private Context mContext;

    public SessionManager(Context context) {
        mContext = context;
    }

    public boolean isLoggedIn() {
        SharedPreferences prefs = mContext.getSharedPreferences(
                Constants.MAIN_PLACEPOST_PREFS,
                mContext.MODE_PRIVATE);
        String sessionToken = prefs.getString(Constants.SESSION_TOKEN, null);
        // todo need to make sure token hasn't expired
        if (sessionToken != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getLoggedInUsername() {
        SharedPreferences prefs = mContext.getSharedPreferences(
                Constants.MAIN_PLACEPOST_PREFS,
                mContext.MODE_PRIVATE);
        String username = prefs.getString(Constants.SESSION_USERNAME, null);
        return username;
    }

    public String getSessionToken() {
        SharedPreferences prefs = mContext.getSharedPreferences(
                Constants.MAIN_PLACEPOST_PREFS,
                mContext.MODE_PRIVATE);
        String sessionToken = prefs.getString(Constants.SESSION_TOKEN, null);
        return sessionToken;
    }

    /**
     * Logout will clear the session.
     */
    public void logout() {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(
                Constants.MAIN_PLACEPOST_PREFS,
                mContext.MODE_PRIVATE).edit();
        editor.remove(Constants.SESSION_USERNAME);
        editor.remove(Constants.SESSION_TOKEN);
        editor.commit();
    }

    public void logUserIn(String username, String token) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(
                Constants.MAIN_PLACEPOST_PREFS,
                mContext.MODE_PRIVATE).edit();
        editor.putString(Constants.SESSION_TOKEN, token);
        editor.putString(Constants.SESSION_USERNAME, username);
        editor.commit();
    }

}
