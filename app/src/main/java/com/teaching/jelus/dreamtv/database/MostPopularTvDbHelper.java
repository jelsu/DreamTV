package com.teaching.jelus.dreamtv.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.teaching.jelus.dreamtv.pojo.Tv;
import com.teaching.jelus.dreamtv.pojo.TvList;

import java.util.List;

public class MostPopularTvDbHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String TAG = MostPopularTvDbHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "DreamTV_database";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "MostPopularTv";
    public static final String ID_COLUMN = BaseColumns._ID;
    public static final String ORIGINAL_NAME_COLUMN = "city";

    public MostPopularTvDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = ""
                + "create table " + TABLE_NAME
                + " (" + ID_COLUMN + " integer primary key, "
                + ORIGINAL_NAME_COLUMN + " text not null "
                + ");";
        db.execSQL(CREATE_TABLE);
        Log.d(TAG, "Create new table database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
        Log.d(TAG, "Update " + oldVersion + " version to " + newVersion + " version");
    }

    public void recordDataToDb(TvList list) {
        List<Tv> results = list.getResults();
        for (int i = 0; i < results.size(); i++)
        {
            Tv tv = results.get(i);
            updateTableRow(i, tv.getOriginalName());
        }
    }

    private void updateTableRow(int id, String originalName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_COLUMN, id);
        values.put(ORIGINAL_NAME_COLUMN, originalName);
        if (isRecordExists(id)){
            db.update(TABLE_NAME, values, ID_COLUMN + " = " + id, null);
        } else {
            db.insert(TABLE_NAME, null, values);
        }
    }

    public boolean isRecordExists(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        final String QUERY = "Select * from " + TABLE_NAME + " where " + ID_COLUMN + " = " + id;
        Cursor cursor = db.rawQuery(QUERY, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Cursor getData(){
        final String QUERY = "Select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery(QUERY, null);
    }

    public void showDataInLog(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex(ID_COLUMN);
            int originalNameIndex = cursor.getColumnIndex(ORIGINAL_NAME_COLUMN);
            do {
                Log.d(TAG, "id = " + cursor.getInt(idColIndex)
                        + "; Original name = " + cursor.getString(originalNameIndex));
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "Database is null");
        }
        cursor.close();
        db.close();
    }
}
