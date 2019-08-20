package com.example.androidtestrappi.negocio;

import java.util.Date;
import java.util.List;

public class TvDetailsResponse {

    private int id;
    private String name;
    private String backdrop_path;
    private String poster_path;
    private float vote_average;
    private String overview;
    private String original_name;
    private String status;
    private int[] episode_run_time;
    private Date first_air_date;
    private NextEpisodeToAir next_episode_to_air;
    private String original_language;
    private int number_of_episodes;
    private int number_of_seasons;
    private List<Genres> genres;
    private Results videos;

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int[] getEpisode_run_time() {
        return episode_run_time;
    }

    public Date getFirst_air_date() {
        return first_air_date;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getStatus() {
        return status;
    }

    public float getVote_average() {
        return vote_average;
    }

    public int getNumber_of_episodes() {
        return number_of_episodes;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public NextEpisodeToAir getNextEpisodeToAir() {
        return next_episode_to_air;
    }

    public Results getVideos() {
        return videos;
    }

    public class NextEpisodeToAir {
        private Date air_date;

        public Date getAir_date() {
            return air_date;
        }

        public NextEpisodeToAir(Date air_date) {
            this.air_date = air_date;
        }
    }

    public TvDetailsResponse(int id, String name, String backdrop_path, String poster_path, float vote_average, String overview, String original_name, String status, int[] episode_run_time, Date first_air_date, NextEpisodeToAir next_episode_to_air, String original_language, int number_of_episodes, int number_of_seasons, List<Genres> genres, Results videos) {
        this.id = id;
        this.name = name;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.overview = overview;
        this.original_name = original_name;
        this.status = status;
        this.episode_run_time = episode_run_time;
        this.first_air_date = first_air_date;
        this.next_episode_to_air = next_episode_to_air;
        this.original_language = original_language;
        this.number_of_episodes = number_of_episodes;
        this.number_of_seasons = number_of_seasons;
        this.genres = genres;
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
