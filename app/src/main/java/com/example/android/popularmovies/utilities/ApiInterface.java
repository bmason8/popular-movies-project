package com.example.android.popularmovies.utilities;

// Helpful Resources...
// https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/

import com.example.android.popularmovies.model.Movie.MovieResult;
import com.example.android.popularmovies.model.ReviewListResponse;
import com.example.android.popularmovies.model.VideoListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    String MOVIE_DB_BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/";

    @GET("{getParameter}")
    Call<MovieResult> getMovieResults(@Path("getParameter") String getParameter, @Query("api_key")String apiKey);

    @GET("{movie_id}/videos")
    Call<VideoListResponse> getMovieVideos(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("{movie_id}/reviews")
    Call<ReviewListResponse> getMovieReviews(@Path("movie_id") int id, @Query("api_key") String apiKey);

}
