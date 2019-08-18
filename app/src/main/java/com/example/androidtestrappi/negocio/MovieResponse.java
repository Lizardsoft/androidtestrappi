package com.example.androidtestrappi.negocio;

import java.util.List;

public class MovieResponse extends JsonResponse {
    private List<MovieItem> results;

    public List<MovieItem> getResults() {
        return results;
    }

    public MovieResponse(int page,  int total_pages, List<MovieItem> results) {
        super(page, total_pages);
        this.results = results;
    }
}
