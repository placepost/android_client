package com.placepostbeta.placepostbeta.apicaller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.placepost.placepostbeta.OnApiTaskCompleted;
import com.placepost.placepostbeta.SessionManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by user on 2/15/15.
 */
public class ApiCaller extends AsyncTask<String, String, String> {

    String TAG = ApiCaller.class.toString();

    private static final String API_KEY = "8b17ec42b09a558ca2f03f7d43c23d49";
    private static final String API_SECRET = "bff9e784a449b8b8e090a42dd68d099b";
    private static final String PASSPORT_ENDPOINT = "https://passport.qor.io/v1/";
    private static final String POSTBOARD_ENDPOINT = "https://postboard.qor.io/postboard/v1/";

    private static final String AUTH_ENDPOINT = PASSPORT_ENDPOINT + "auth";
    private static final String GET_CONTEXTS_ENDPOINT = POSTBOARD_ENDPOINT + "contexts";

    private static final String REGISTER_ENDPOINT = PASSPORT_ENDPOINT + "auth";


    private static final String USERNAME_PARAM = "username";
    private static final String PASSWORD_PARAM = "password";

    public static final String LOGIN_CALL = "login";
    public static final String REGISTER_CALL = "register";
    public static final String GET_CONTEXTS_CALL = "contexts";

    // authorization contexts https://postboard.qor.io/postboard/v1/contexts


    public static final String ERROR_JSON_EXCEPTION = "json_exception";
    public static final String ERROR_INVALID_PARAMS = "invalid_params";
    public static final String ERROR_CLIENT_EXCEPTION = "client_exception";
    public static final String ERROR_IO_EXCEPTION = "io_exception";

    private OnApiTaskCompleted mOnApiTaskCompleted;
    private String mAuthToken;

    public ApiCaller(OnApiTaskCompleted onApiTaskCompleted, String token) {
        mOnApiTaskCompleted = onApiTaskCompleted;
        mAuthToken = token;
    }

    /**
     * Api Calls:
     *
     * How do you auth an existing user?
     * https://passport.qor.io/v1/auth
     * Params:
     * - username: the username
     * - password: password for user
     *
     * Returns:
     * - token: auth token on success
     * - error: an error message on error eg "error-bad-credentials"
     *
     * How do you register a new user?
     * https://passport.qor.io/v1/register
     */

    @Override
    protected String doInBackground(String... params) {
        if (params.length == 0) {
            return ERROR_INVALID_PARAMS;
        }

        // method type should be first param
        String methodType = params[0];

        switch (methodType) {
            case ApiCaller.LOGIN_CALL:
                return doLogin(params);
            case ApiCaller.REGISTER_CALL:
                return doRegister(params);
            case ApiCaller.GET_CONTEXTS_CALL:
                return doGetContextsCall(params);
            default:
                return "not valid";
        }
    }

    private String doRegister(String... params) {
        /**
         * todo - need to implement this
         *
         * How do you register a new user?
         * https://passport.qor.io/v1/register
         */

        return "NOK";
    }

    private String doGetContextsCall(String... params) {
        String identifier1 = params[1];
        String identifier2 = params[1];

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(GET_CONTEXTS_ENDPOINT);
        String response = null;
        JSONObject responseObject = null;
        JSONArray responseArray = null;

        try {
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            httpGet.setHeader("Authorization", "Bearer " + mAuthToken);
            httpGet.addHeader("X-Postboard-Context-Identifier", identifier1);
            httpGet.addHeader("X-Postboard-Context-Identifier", identifier2);

            // eg X-Postboard-Context-Identifier qG31Py0b3LbOjOmXs6v8Rw==
            // eg X-Postboard-Context-Identifier RdFhbaskX1jXV/x8H5h6mQ==


            ResponseHandler<String> handler=new BasicResponseHandler();
            response = httpclient.execute(httpGet, handler);
//            responseObject = new JSONObject(response);
            responseArray = new JSONArray(response);
            Log.v(TAG, response);
        } catch (JSONException e) {
            Log.v(TAG, "Json Exception occurred" + e.getMessage());
            return ERROR_JSON_EXCEPTION;
        } catch (ClientProtocolException e) {
            Log.v(TAG, "Client Exception: " + e.getMessage());
            // tododavid : ask about this
            // e.getMessage = Unauthorized means the login failed... do we want exception for this?
            return ERROR_CLIENT_EXCEPTION;
        } catch (IOException e) {
            Log.v(TAG, "IO Exception: " + e.getMessage());
            return ERROR_IO_EXCEPTION;
        }
        Log.v(TAG, "OK");
        if (responseObject != null) {
            return responseObject.toString();
        } else {
            return responseArray.toString();
        }
    }

    private String doLogin(String... params) {
        String username = params[1];
        String password = params[2];

        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(AUTH_ENDPOINT);
        String response = null;
        JSONObject responseObject = null;

        try {
            JSONObject holder = new JSONObject();
            holder.put(USERNAME_PARAM, username);
            holder.put(PASSWORD_PARAM, password);

            StringEntity se = new StringEntity(holder.toString());
            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            ResponseHandler<String> handler=new BasicResponseHandler();
            response = httpclient.execute(httppost, handler);
            responseObject = new JSONObject(response);
            responseObject.put("username", username);
            Log.v(TAG, response);
        } catch (JSONException e) {
            Log.v(TAG, "Json Exception occurred" + e.getMessage());
            return ERROR_JSON_EXCEPTION;
        } catch (ClientProtocolException e) {
            Log.v(TAG, "Client Exception: " + e.getMessage());
            // tododavid : ask about this
            // e.getMessage = Unauthorized means the login failed... do we want exception for this?
            return ERROR_CLIENT_EXCEPTION;
        } catch (IOException e) {
            Log.v(TAG, "IO Exception: " + e.getMessage());
            return ERROR_IO_EXCEPTION;
        }
        Log.v(TAG, "OK");
        if (responseObject != null) {
            return responseObject.toString();
        } else {
            return response;
        }
    }

    protected void onPostExecute(String result) {
        mOnApiTaskCompleted.onApiTaskCompleted(result);
    }

}
