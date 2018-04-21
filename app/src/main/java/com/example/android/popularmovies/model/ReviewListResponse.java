package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;


public class ReviewListResponse {
    @SerializedName("results")
    private List<Review> mReviewList = new ArrayList<>();

    public List<Review> getmReviewList() {
        return mReviewList;
    }
}
