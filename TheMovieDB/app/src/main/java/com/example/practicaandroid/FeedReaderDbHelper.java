package com.example.practicaandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.CompoundButton;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader2.db";
    private static final String MOVIE_TABLE_CREATE = "CREATE TABLE favIds(_id INTEGER PRIMARY KEY AUTOINCREMENT, idFav TEXT)";
    private static final String SERIE_TABLE_CREATE = "CREATE TABLE series(_id INTEGER PRIMARY KEY AUTOINCREMENT, idFav TEXT)";

    private static final String SQL_DELETE_TABLA1 =
            "DROP TABLE IF EXISTS favIds";
    private static final String SQL_DELETE_TABLA2 =
            "DROP TABLE IF EXISTS series";


    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SERIE_TABLE_CREATE);
        Log.i("Create table", "yeye");
        db.execSQL(MOVIE_TABLE_CREATE);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLA1);
        db.execSQL(SQL_DELETE_TABLA2);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
