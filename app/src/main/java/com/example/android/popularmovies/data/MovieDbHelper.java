package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.popularmovies.data.MovieContract.MovieDbEntry;

public class MovieDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "favouriteMovies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
                "CREATE TABLE " + MovieDbEntry.TABLE_NAME + " (" +
                        MovieDbEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        MovieDbEntry.COLUMN_ID_TMDB + " INTEGER NOT NULL, " +
                        MovieDbEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                        MovieDbEntry.COLUMN_POSTER_PATH + " TEXT, " +
                        MovieDbEntry.COLUMN_OVERVIEW + " TEXT, " +
                        MovieDbEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                        MovieDbEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
                        MovieDbEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                        MovieDbEntry.COLUMN_FAVORITE + " INTEGER DEFAULT 0 );";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieDbEntry.TABLE_NAME);
        onCreate(db);
    }
}
