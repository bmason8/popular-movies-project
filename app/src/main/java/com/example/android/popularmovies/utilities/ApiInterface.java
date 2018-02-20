package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Helpful Resources...
// https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/

public interface ApiInterface {

    String MOVIE_DB_BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/";


    @GET("{getParameter}")
    Call<Movie.MovieResult> getMovieResults(@Path("getParameter") String getParameter, @Query("api_key")String apiKey);

}
