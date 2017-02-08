package com.teaching.jelus.dreamtv.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teaching.jelus.dreamtv.MyApp;
import com.teaching.jelus.dreamtv.R;
import com.teaching.jelus.dreamtv.adapter.RecyclerAdapter;
import com.teaching.jelus.dreamtv.database.MostPopularTvDbHelper;
import com.teaching.jelus.dreamtv.pojo.TvList;
import com.teaching.jelus.dreamtv.task.ReceivingData;

import java.net.URL;
import java.util.concurrent.ExecutorService;

public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = MainActivityFragment.class.getSimpleName();
    private static final int LOADER_ID = 1;

    private RecyclerAdapter mRecyclerAdapter;
    private ExecutorService mPool;
    private MostPopularTvDbHelper mMostPopularTvDbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mPool = MyApp.getPool();
        mMostPopularTvDbHelper = MyApp.getMostPopularTvDbHelper();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_tv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerAdapter = new RecyclerAdapter(null);
        recyclerView.setAdapter(mRecyclerAdapter);
        receiveData();
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(getContext(), mMostPopularTvDbHelper);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mRecyclerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerAdapter.swapCursor(null);
    }

    //TODO find correct solution
    private void receiveData() {
        mPool.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = ReceivingData.getUrl();
                    Log.d(TAG, "Composite URL: " + url.toString());
                    TvList TvList = ReceivingData.getJsonStrViaJackson();
                    mMostPopularTvDbHelper.recordDataToDb(TvList);
                    mMostPopularTvDbHelper.showDataInLog();
                    getLoaderManager().getLoader(LOADER_ID).forceLoad();
                    //Log.d(TAG, "Total Pages: " + result);
                    /*String str = ReceivingData.obtainStrDataOnUrl(url);
                    JSONObject jsonObject = ReceivingData.strToJson(str);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    static class MyCursorLoader extends CursorLoader {
        MostPopularTvDbHelper db;

        public MyCursorLoader(Context context, MostPopularTvDbHelper db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getData();
            return cursor;
        }
    }
}
