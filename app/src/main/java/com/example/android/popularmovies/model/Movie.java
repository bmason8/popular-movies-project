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

//    private ArrayList<ReviewsResults> reviews;
//
//    public ArrayList<ReviewsResults> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(ArrayList<ReviewsResults> reviews) {
//        this.reviews = reviews;
//    }

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

    public static class MovieResult {
        private List<Movie> results;

        public List<Movie> getResults() {
            return results;
        }
    }
}



//public class Movie implements android.os.Parcelable {
//
//    // The instructions recommended using w185 for the image size but that seems too small
//    private static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w342";
//
//    private String title;
//
//    @SerializedName("poster_path")
//    private String poster;
//
//    @SerializedName("overview")
//    private String description;
//
//    @SerializedName("backdrop_path")
//    private String backdrop;
//
//    @SerializedName("vote_average")
//    private String userRating;
//
//    @SerializedName("release_date")
//    private String releaseDate;
//
//    @SerializedName("video")
//    @Expose
//    private Video video;
//    @SerializedName("reviews")
//    @Expose
//    private Review reviews;
//
//    // Getters
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getPoster() {
//        return TMDB_IMAGE_PATH + poster;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getBackdrop() {
//        return backdrop;
//    }
//
//    public String getUserRating() {
//        return userRating;
//    }
//
//    public String getReleaseDate() {
//        return releaseDate;
//    }
//
//    public Video getVideos() {
//        return video;
//    }
//
//    public void setVideos(Video video) {
//        this.video = video;
//    }
//
//    public Review getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(Review reviews) {
//        this.reviews = reviews;
//    }
//
//        public static class MovieResult {
//        private List<Movie> results;
//
//        public List<Movie> getResults() {
//            return results;
//        }
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.title);
//        dest.writeString(this.poster);
//        dest.writeString(this.description);
//        dest.writeString(this.backdrop);
//        dest.writeString(this.userRating);
//        dest.writeString(this.releaseDate);
////        dest.writeParcelable(this.video, flags);
////        dest.writeParcelable(this.reviews, flags);
//    }
//
//    public Movie() {
//    }
//
//    protected Movie(Parcel in) {
//        this.title = in.readString();
//        this.poster = in.readString();
//        this.description = in.readString();
//        this.backdrop = in.readString();
//        this.userRating = in.readString();
//        this.releaseDate = in.readString();
////        this.video = in.readParcelable(Video.class.getClassLoader());
////        this.reviews = in.readParcelable(Review.class.getClassLoader());
//    }
//
//    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
//        @Override
//        public Movie createFromParcel(Parcel source) {
//            return new Movie(source);
//        }
//
//        @Override
//        public Movie[] newArray(int size) {
//            return new Movie[size];
//        }
//    };
//}


