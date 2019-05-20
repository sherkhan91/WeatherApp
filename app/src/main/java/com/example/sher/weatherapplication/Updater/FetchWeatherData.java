package com.example.sher.weatherapplication.Updater;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sher.weatherapplication.Constants;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class FetchWeatherData {
    //api.openweathermap.org/data/2.5/weather?lat=24.8607&lon=67.0011&appid=fc7890922373d80b3ca8b09b998e37ee
    private static String baseurl = "https://api.openweathermap.org/data/2.5/weather?";
    private static String url;
    private static ProgressDialog progressDialog;


    public static boolean fetchWeather() {

        // if there is no location retrieved Default co-ordinates for Dubai has been put
        if(Constants.latitude==null)
            Constants.latitude = "25.2048";
        if(Constants.longitude==null)
            Constants.longitude = "55.2708";
        url = baseurl+"lat="+Constants.latitude+"&lon="+Constants.longitude+"&appid=fc7890922373d80b3ca8b09b998e37ee";
       //progressDialog = new ProgressDialog(Constants.context);
        new FetchWeatherData.WaetherDataRequester().execute();
        return false;
    }

    private static class WaetherDataRequester extends AsyncTask<Void,  Void, JSONObject>
    {

        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("response","In preExecutre");
            //progressDialog.setMessage("Please wait while we fetch weather..");
           // progressDialog.show();
            //progressDialog.setCancelable(false);

        }

        protected JSONObject doInBackground(Void... params) {
            Log.d("response","background work started to fetch data");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Log.d("url",url);
            Response response = null;
            JSONObject reader =null;
            try {
                Log.d("response","first stage");
                response = client.newCall(request).execute();
                reader = new JSONObject(response.body().string());
                //JSONArray resultArray= reader.getJSONArray("results");



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return reader;
        }

        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            Constants.weatherJSONObject = result;
           // progressDialog.dismiss();

        }
    }
}
