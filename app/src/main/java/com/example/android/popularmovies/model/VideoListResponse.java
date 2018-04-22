package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VideoListResponse {

    @SerializedName("results")
    private List<Video> mVideoList = new ArrayList<>();

    public List<Video> getVideoList() {
        return mVideoList;
    }
}
