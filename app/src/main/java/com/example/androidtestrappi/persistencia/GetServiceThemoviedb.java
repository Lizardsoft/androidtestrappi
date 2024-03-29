package com.example.androidtestrappi.persistencia;

import com.example.androidtestrappi.negocio.MovieDetailsResponse;
import com.example.androidtestrappi.negocio.MovieResponse;
import com.example.androidtestrappi.negocio.TvDetailsResponse;
import com.example.androidtestrappi.negocio.TvResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetServiceThemoviedb {
    public static final String URL_IMG="https://image.tmdb.org/";
    public static final String URL_BASE="https://api.themoviedb.org/3/";
    String API_ID = "7de2655e2eab1bbf079f571eefe27729";

    String LANGUAGE = "es";

    String API_ROUTE = "{type}/{category}?api_key=" + API_ID + "&language=" + LANGUAGE;
    String API_ROUTE_DETAILS = "{type}/{id}?api_key=" + API_ID + "&language=" + LANGUAGE + "&append_to_response=videos";

    @GET(API_ROUTE)
    Call<MovieResponse> getDataMovie(@Path("type") String type, @Path("category") String category, @Query("page") int page);

    @GET(API_ROUTE)
    Call<TvResponse> getDataTv(@Path("type") String type, @Path("category") String category, @Query("page") int page);

    @GET(API_ROUTE_DETAILS)
    Call<MovieDetailsResponse> getDetailsMovie(@Path("type") String type, @Path("id") int movie_id);

    @GET(API_ROUTE_DETAILS)
    Call<TvDetailsResponse> getDetailsTv(@Path("type") String type, @Path("id") int tv_id);
}

