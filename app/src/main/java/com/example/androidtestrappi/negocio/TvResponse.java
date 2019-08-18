package com.example.androidtestrappi.negocio;

import java.util.List;

public class TvResponse extends JsonResponse {
    private List<TvItem> results;

    public List<TvItem> getResults() {
        return results;
    }

    public TvResponse(int page, int total_pages, List<TvItem> results) {
        super(page, total_pages);
        this.results = results;
    }
}
