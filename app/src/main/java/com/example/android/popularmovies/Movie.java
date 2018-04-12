package com.example.android.popularmovies;

import com.google.gson.annotations.SerializedName;

import java.util.List;

// Helpful Resources...
// http://mateoj.com/2015/10/06/creating-movies-app-retrofit-picasso-android/
// http://mateoj.com/2015/10/07/creating-movies-app-retrofit-picass-android-part2/

public class Movie {

    // The instructions recommended using w185 for the image size but that seems too small
    private static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w342";

    private String title;

    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String description;

    @SerializedName("backdrop_path")
    private String backdrop;

    @SerializedName("vote_average")
    private String userRating;

    @SerializedName("release_date")
    private String releaseDate;

    private String reviews;

    private String trailers;


    //Getter and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return TMDB_IMAGE_PATH + poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public String getTrailers() {
        return trailers;
    }

    public void setTrailers(String trailers) {
        this.trailers = trailers;
    }

        public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}


