package com.teaching.jelus.dreamtv;

import android.app.Application;

import com.teaching.jelus.dreamtv.database.MostPopularTvDbHelper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {
    private static ExecutorService sPool;
    private static MostPopularTvDbHelper sMostPopularTvDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        sPool = Executors.newCachedThreadPool();
        sMostPopularTvDbHelper = new MostPopularTvDbHelper(getApplicationContext());
    }

    public static ExecutorService getPool() {
        return sPool;
    }

    public static MostPopularTvDbHelper getMostPopularTvDbHelper() {
        return sMostPopularTvDbHelper;
    }
}
