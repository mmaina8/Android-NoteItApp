package com.example.solution.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.example.solution.database.Note;
//import com.facebook.stetho.server.http.HttpHandler;
import com.example.solution.util.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class TestAsync extends AsyncTask<Void, Void, String> {
    //private Tab1Fragment.FragmentCallback mFragmentCallback;
    private String url;
    private ProgressDialog pDialog;

    private ArrayList<Note> noteList = new ArrayList<Note>();
    String rage;
    JSONObject json;
    SharedPreferences user_sess;

    Context context;

    private Activity activity;
    private String activityName;

    private final static String TAG = TestAsync.class.getSimpleName();

    /*public TestAsyncTask(Tab1Fragment.FragmentCallback fragmentCallback, String url) {
        mFragmentCallback = fragmentCallback;
        url_deal = url;
    }*/

    public TestAsync(Activity activity, String url) {
        //super();
        this.activity = activity;
        this.activityName = activity.getLocalClassName();
        context = activity;
        this.url = url;

    }

    @Override
    protected String doInBackground(Void... params) {

        rage = loadJSON(url).toString();
        return rage;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //@Override
    protected void onPostExecute(String result) {

        //  ((MenuActivity) activity).jsonLatestDeal(result);
        Log.e(TAG, "Response from url: " + result);
    }




    public JSONObject loadJSON(String url) {
        // Creating JSON Parser instance
        JSONGetter jParser = new JSONGetter();

        // getting JSON string from URL
        json = jParser.getJSONFromUrl(url);

        return json;
    }


    private class JSONGetter {

        private InputStream is = null;
        private JSONObject jObj = null;
        private String json = "";

        // constructor
        public JSONGetter() {

        }

        public JSONObject getJSONFromUrl(String url) {


            HttpHandler sh = new HttpHandler();
            JSONObject jsonObj = null;
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    jsonObj = new JSONObject(jsonStr);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }



            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }


            return jsonObj;


        }
    }


}

