package com.example.android.popularmovies.utilities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.popularmovies.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies")
    List<Movie> getMovies();

    @Query("SELECT * FROM movies WHERE id =:movie_id")
    int getSingleMovie(int movie_id);

    @Insert
    void insertSingleMovie(Movie movie);

    @Delete
    void deleteSingleMovie(Movie movie);


}
