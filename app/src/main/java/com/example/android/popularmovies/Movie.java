package com.example.android.popularmovies;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben.mason on 01/02/2018.
 */

public class Movie {

    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";

    private String title;
    @SerializedName("poster_path")
    private String poster;

    @SerializedName("overview")
    private String description;

    @SerializedName("backdrop_path")
    private String backdrop;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return TMDB_IMAGE_PATH + poster;
    }

//    public String getPoster() {
//        return "http://t2.gstatic.com/images?q=tbn:ANd9GcQW3LbpT94mtUG1PZIIzJNxmFX399wr_NcvoppJ82k7z99Hx6in";
//    }

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
        return TMDB_IMAGE_PATH + backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

        public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}

// original
//public class Movie {
//
//    // added the @SerializedName to I can use my own variable names that aren't exactly the same as the JSON key names
//    @SerializedName("id")
//    private long mId;
//    @SerializedName("original_title")
//    private String mTitle;
//    @SerializedName("poster_path")
//    private String mPoster;
//    @SerializedName("overview")
//    private String mOverview;
//    @SerializedName("vote_average")
//    private String mUserRating;
//    @SerializedName("release_date")
//    private String mReleaseDate;
//    @SerializedName("backdrop_path")
//    private String mBackdrop;
//
//
//    public Movie(long id, String title, String poster, String overview, String userRating, String releaseDate, String backdrop) {
//        mId = id;
//        mTitle = title;
//        mPoster = poster;
//        mOverview = overview;
//        mUserRating = userRating;
//        mReleaseDate = releaseDate;
//        mBackdrop = backdrop;
//    }
//
//    public long getmId() {
//        return mId;
//    }
//
//    public String getmTitle() {
//        return mTitle;
//    }
//
////    public String getmPoster() {
////        return mPoster;
////    }
//public String getmPoster() {
//    return "http://t2.gstatic.com/images?q=tbn:ANd9GcQW3LbpT94mtUG1PZIIzJNxmFX399wr_NcvoppJ82k7z99Hx6in";
//}
//
//    public String getmOverview() {
//        return mOverview;
//    }
//
//    public String getmUserRating() {
//        return mUserRating;
//    }
//
//    public String getmReleaseDate() {
//        return mReleaseDate;
//    }
//
//    public String getmBackdrop() {
//        return mBackdrop;
//    }
//
//    public static class MovieResult {
//        private List<Movie> results;
//
//        public List<Movie> getResults() {
//            return results;
//        }
//    }
//
//}



