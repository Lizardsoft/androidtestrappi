package com.example.androidtestrappi.negocio;

import java.util.Date;
import java.util.List;

public class MovieDetailsResponse {
    private String backdrop_path;
    private double budget;
    private List<Genres> genres;
    private String name;
    private int id;
    private String original_language;
    private String original_title;
    private String overview;
    private String poster_path;
    private Date release_date;
    private double revenue;
    private int runtime;
    private String status;
    private String title;
    private float vote_average;
    private Results videos;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getBudget() {
        return budget;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public double getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public float getVote_average() {
        return vote_average;
    }

    public Results getVideos() {
        return videos;
    }

    public MovieDetailsResponse(String backdrop_path, double budget, List<Genres> genres, String name, int id, String original_language, String original_title, String overview, String poster_path, Date release_date, double revenue, int runtime, String status, String title, float vote_average, Results videos) {
        this.backdrop_path = backdrop_path;
        this.budget = budget;
        this.genres = genres;
        this.name = name;
        this.id = id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.title = title;
        this.vote_average = vote_average;
        this.videos = videos;
    }

    public class Genres {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class Results {
        private List<Videos> results;

        public List<Videos> getResults() {
            return results;
        }

        public Results(List<Videos> results) {
            this.results = results;
        }
    }

    public class Videos {
        private String key;
        private String site;

        public String getKey() {
            return key;
        }

        public String getSite() {
            return site;
        }

        public Videos(String key, String site) {
            this.key = key;
            this.site = site;
        }
    }
}

