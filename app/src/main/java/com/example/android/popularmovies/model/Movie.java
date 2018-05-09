package com.example.android.popularmovies.model;

// Helpful Resources...
// http://mateoj.com/2015/10/06/creating-movies-app-retrofit-picasso-android/
// http://mateoj.com/2015/10/07/creating-movies-app-retrofit-picass-android-part2/

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo
    private String title;

    @ColumnInfo(name = "posterImage")
    @SerializedName("poster_path")
    private String poster;

    @ColumnInfo
    @SerializedName("overview")
    private String description;

    @ColumnInfo(name = "backdrop")
    @SerializedName("backdrop_path")
    private String backdrop;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private String userRating;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String releaseDate;

    @ColumnInfo(name = "isFavourite")
    private boolean isFavourite;

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    private String trailers;

    //Getter and Setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
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

    public String getTrailers() {
        return trailers;
    }

    public void setTrailers(String trailers) {
        this.trailers = trailers;
    }

//    public boolean isFavourite() {
//        return isFavourite;
//    }
//
//    public void setFavourite(boolean favourite) {
//        isFavourite = favourite;
//    }

    public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}

