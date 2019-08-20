package com.example.androidtestrappi.negocio;

import java.util.Date;
import java.util.List;

public class MovieResponse {
    private List<MovieItem> results;
    private int page;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<MovieItem> getResults() {
        return results;
    }

    public MovieResponse(List<MovieItem> results, int page, int total_pages) {
        this.results = results;
        this.page = page;
        this.total_pages = total_pages;
    }

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
}
