package com.example.android.popularmovies.utilities;

import android.util.Log;
import android.widget.Toast;

import com.example.android.popularmovies.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ben.mason on 01/02/2018.
 */

public interface ApiInterface {

    String MOVIE_DB_BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/";

    @GET("top_rated")
    Call<Movie.MovieResult> getMovieResults(@Query("api_key")String apiKey);
}
