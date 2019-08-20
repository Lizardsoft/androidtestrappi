package com.example.androidtestrappi.negocio;

import java.util.Date;
import java.util.List;

public class TvResponse {
    private List<TvItem> results;
    private int page;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<TvItem> getResults() {
        return results;
    }

    public TvResponse(List<TvItem> results, int page, int total_pages) {
        this.results = results;
        this.page = page;
        this.total_pages = total_pages;
    }

    public class TvItem {

        private String name;
        private Date first_air_date;
        private String backdrop_path;
        private int id;
        private double vote_average;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        Date getFirst_air_date() {
            return first_air_date;
        }

        String getBackdrop_path() {
            return backdrop_path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        double getVote_average() {
            return vote_average;
        }

        public TvItem(String name, Date first_air_date, String backdrop_path, int id, double vote_average) {
            this.name = name;
            this.first_air_date = first_air_date;
            this.backdrop_path = backdrop_path;
            this.id = id;
            this.vote_average = vote_average;
        }
    }

}
