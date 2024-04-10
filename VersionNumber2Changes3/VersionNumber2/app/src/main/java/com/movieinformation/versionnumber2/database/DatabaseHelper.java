package com.movieinformation.versionnumber2.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.movieinformation.versionnumber2.model.ResponseData;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "url_database";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME2 = "Movies";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_ACTOR = "actors";


    private static final String TABLE_NAME1 = "match_details";
    private static final String COLUMN_TITLE1 = "title";
    private static final String COLUMN_DATE = "date";
    private static final String TEAM_NAME1 = "teamName1";
    private static final String TEAM_NAME2 = "teamName2";
    private static final String TEAM_URL1 = "teamUrl1";
    private static final String TEAM_URL2 = "teamUrl2";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE " + TABLE_NAME2 + "("
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_YEAR + " TEXT,"
                + COLUMN_ACTOR + " TEXT,"
                + COLUMN_URL + " TEXT"
                + ")";
        db.execSQL(createTableQuery);
        String matchDetail =  "CREATE TABLE " + TABLE_NAME1 + "("
                + COLUMN_TITLE1 + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + TEAM_NAME1 + " TEXT,"
                + TEAM_NAME2 + " TEXT,"
                + TEAM_URL1 + " TEXT,"
                + TEAM_URL2 + " TEXT"
                + ")";
        db.execSQL(matchDetail);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);


    }

    //    public boolean insertUrl(String url) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_URL, url);
//        db.insert(TABLE_NAME, null, values);
//        db.close();
//        return true;
//    }
//    public boolean insertUrl(String url) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_URL, url);
//        long result = db.insert(TABLE_NAME2, null, values);
//        db.close();
//        return result != -1; // Returns true if insertion was successful
//    }
    public boolean insertMovies(ResponseData responseData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, responseData.getTitle());
        values.put(COLUMN_YEAR, responseData.getYear());
        values.put(COLUMN_ACTOR, responseData.getActors());
        values.put(COLUMN_URL, responseData.getPoster());
        long result = db.insert(TABLE_NAME2, null, values);
        db.close();
        return result != -1;
    }
}