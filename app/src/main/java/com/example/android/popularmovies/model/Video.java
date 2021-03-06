package com.example.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    private String id;
    @SerializedName("key")
    private String key;

    private String name;

    private String type;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}

