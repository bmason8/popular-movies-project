package com.example.android.popularmovies.utilities;

// Helpful Resources...
// https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/

import com.example.android.popularmovies.model.Movie.MovieResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    String MOVIE_DB_BASE_MOVIE_URL = "https://api.themoviedb.org/3/movie/";


//    @GET("{getParameter}")
//    Call<Movie.MovieResult> getMovieResults(@Path("getParameter") String getParameter,
//                                            @Query("api_key")String apiKey,
//                                            @Query("appendReviewsAndVideos") String appendReviewsAndVideos);

    @GET("{getParameter}")
    Call<MovieResult> getMovieResults(@Path("getParameter") String getParameter,
                                      @Query("api_key")String apiKey);


//    @GET("{id}/videos")
//    Call<MovieVideosResult> getMovieVideos(@Path("id") String movieId);
//
//    @GET("{id}/reviews")
//    Call<MovieReviewsResult> getMovieReviews(@Path("id") String movieId);

}
