package com.example.android.popularmovies.utilities;

import com.example.android.popularmovies.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ben.mason on 01/02/2018.
 */

public class MovieResponse {

    private String page;
    private String total_results;
    private String total_pages;
    private List<Movie> results;


    public MovieResponse(String page, String total_results, String total_pages, List<Movie> results) {
        this.page = page;
        this.total_results = total_results;
        this.total_pages = total_pages;
        this.results = results;
    }

    public String getPage() {
        return page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

}
