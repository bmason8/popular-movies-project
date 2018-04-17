package com.example.android.popularmovies.model;

import java.util.List;

public class VideoResults {

    private int trailerId;
    private List<Video> results;

    public int getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(int trailerId) {
        this.trailerId = trailerId;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }

    public VideoResults(int trailerId, List<Video> results) {
        this.trailerId = trailerId;
        this.results = results;
    }
}
