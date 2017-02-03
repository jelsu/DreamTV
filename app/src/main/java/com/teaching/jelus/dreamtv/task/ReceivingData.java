package com.teaching.jelus.dreamtv.task;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ReceivingData {
    public static final String TAG = ReceivingData.class.getSimpleName();

    public static URL getUrl() throws MalformedURLException {
        final String BEGINNING_URL = "https://api.themoviedb.org/3/";
        final String APP_KEY = "3e8a943eab2f635a03f49729a887d467";
        String requestType = "discover/tv";
        StringBuilder compositeUrl = new StringBuilder(BEGINNING_URL + requestType);
        compositeUrl.append("?api_key=" + APP_KEY);
        compositeUrl.append("&sort_by=popularity.desc");
        compositeUrl.append("&language=ru");
        Log.d(TAG, "Composite URL: " + compositeUrl.toString());
        return new URL(compositeUrl.toString());
    }

    public static String urlToStr(URL url) throws IOException {
        HttpURLConnection urlConnection;
        BufferedReader reader;
        InputStream inputStream;
        StringBuffer buffer;
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
        inputStream = urlConnection.getInputStream();
        buffer = new StringBuffer();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null){
            buffer.append(line);
        }
        return buffer.toString();
    }

    public static JSONObject strToJson(String str) throws JSONException {
        return new JSONObject(str);
    }
}
