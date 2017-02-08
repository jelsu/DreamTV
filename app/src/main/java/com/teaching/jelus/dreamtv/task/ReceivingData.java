package com.teaching.jelus.dreamtv.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teaching.jelus.dreamtv.CompositeUrl;
import com.teaching.jelus.dreamtv.pojo.TvList;

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
        CompositeUrl mostPopularTvUrl = new CompositeUrl.CompositeUrlBuilder()
                .selectMostPopularRequestType()
                .selectByPopularitySortType()
                .selectRuLanguage()
                .build();
        return mostPopularTvUrl.getCompositeURL();
    }

    public static TvList getJsonStrViaJackson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TvList tvList = new TvList();
        tvList = objectMapper.readValue(getUrl(), TvList.class);
        return tvList;
    }

    public static String obtainStrDataOnUrl(URL url) throws IOException {
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
