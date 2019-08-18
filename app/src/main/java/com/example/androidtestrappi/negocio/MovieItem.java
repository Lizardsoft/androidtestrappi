package com.example.androidtestrappi.negocio;

import java.util.Date;

public class MovieItem {

    private int id;
    private double vote_average;
    private String title;
    private String backdrop_path;
    private Date release_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    double getVote_average() {
        return vote_average;
    }

    String getTitle() {
        return title;
    }

    String getBackdrop_path() {
        return backdrop_path;
    }

    Date getRelease_date() {
        return release_date;
    }

    public MovieItem(int id, double vote_average, String title, String backdrop_path, Date release_date) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.release_date = release_date;
    }
}
