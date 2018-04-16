package com.example.android.popularmovies.model;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Video implements android.os.Parcelable {
    @SerializedName("results")
    @Expose
    private List<VideoResults> results = null;

    public List<VideoResults> getVideoResults() {
        return results;
    }

    public void setVideoResults(List<VideoResults> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.results);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.results = new ArrayList<VideoResults>();
        in.readList(this.results, VideoResults.class.getClassLoader());
    }

    public static final Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
