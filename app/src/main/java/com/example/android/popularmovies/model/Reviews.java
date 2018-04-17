package com.example.android.popularmovies.model;

public class Reviews {
    public Reviews(String author, String content) {
        this.author = author;
        this.content = content;
    }

    private String author;
    private String content;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
