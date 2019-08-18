package com.example.androidtestrappi.negocio;

public class JsonResponse {
    private int page;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    JsonResponse(int page, int total_pages) {
        this.page = page;
        this.total_pages = total_pages;
    }
}
