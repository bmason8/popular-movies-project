package com.example.android.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.utilities.MovieDao;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
}
