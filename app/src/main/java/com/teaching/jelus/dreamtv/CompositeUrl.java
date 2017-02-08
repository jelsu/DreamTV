package com.teaching.jelus.dreamtv;

import java.net.MalformedURLException;
import java.net.URL;

public class CompositeUrl {
    private final static String BEGINNING_URL = "https://api.themoviedb.org/3/";
    private final static String APP_KEY = "3e8a943eab2f635a03f49729a887d467";
    private final static String KEY_PREFIX = "?api_key=";
    private final static String MOST_POPULAR_TV_REQUEST = "discover/tv";
    private final static String BY_POPULARITY_SORT = "&sort_by=popularity.desc";
    private final static String RU_LANGUAGE = "&language=ru";

    private String mRequestType;
    private String mSortType;
    private String mLanguage;
    private StringBuilder mBuilder;

    private CompositeUrl(String requestType, String sortType, String language) {
        this.mRequestType = requestType;
        this.mSortType = sortType;
        this.mLanguage = language;
        mBuilder = new StringBuilder(BEGINNING_URL);
    }

    public URL getCompositeURL() throws MalformedURLException {
        mBuilder.append(mRequestType);
        mBuilder.append(KEY_PREFIX + APP_KEY);
        mBuilder.append(mSortType);
        mBuilder.append(mLanguage);
        return new URL(mBuilder.toString());
    }

    public static class CompositeUrlBuilder {
        private String mRequestType;
        private String mSortType;
        private String mLanguage;

        public CompositeUrlBuilder selectMostPopularRequestType() {
            mRequestType = MOST_POPULAR_TV_REQUEST;
            return this;
        }

        public CompositeUrlBuilder selectByPopularitySortType() {
            mSortType = BY_POPULARITY_SORT;
            return this;
        }

        public CompositeUrlBuilder selectRuLanguage() {
            mLanguage = RU_LANGUAGE;
            return this;
        }

        public CompositeUrl build() {
            return new CompositeUrl(mRequestType, mSortType, mLanguage);
        }
    }
}
