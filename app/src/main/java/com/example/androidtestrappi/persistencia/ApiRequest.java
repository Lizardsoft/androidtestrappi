package com.example.androidtestrappi.persistencia;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequest {
    private GetServiceThemoviedb postService;

    public ApiRequest(Context context) {
        final long cacheSize = (5 * 1024 * 1024);
        final Cache myCache = new Cache(context.getCacheDir(), cacheSize);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(myCache)
                .addInterceptor(new AddInterceptorCache(context))
                .addNetworkInterceptor(new AddNetworkInterceptorCache()).build();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        final Gson gson = gsonBuilder.create();

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();

        postService = retrofit.create(GetServiceThemoviedb.class);
    }

    @SuppressWarnings("unchecked")
    public void getData(Callback callback, String navigationTypePath, String categoryPath, int page) {
        Call call;
        if (navigationTypePath.equalsIgnoreCase("movie")) {
            call = postService.getDataMovie(navigationTypePath, categoryPath, page);
        } else {
            call = postService.getDataTv(navigationTypePath, categoryPath, page);
        }

        call.enqueue(callback);
    }

    @SuppressWarnings("unchecked")
    public void getDataDetails(Callback callback, String navigationTypePath, int Id) {
        Call call;
        if (navigationTypePath.equalsIgnoreCase("movie")) {
            call = postService.getDetailsMovie(navigationTypePath, Id);
        } else {
            call = postService.getDetailsTv(navigationTypePath, Id);
        }

        call.enqueue(callback);
    }

    private class AddNetworkInterceptorCache implements Interceptor {
        @Override
        public @NonNull
        Response intercept(@NonNull Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());

            //Guarda en cache la petición por 1 hora antes de volver hacerla al servidor
            return response.newBuilder()
                    .removeHeader("Cache-Control")
                    .addHeader("Cache-Control", "public, max-age=3600")
                    .build();
        }
    }

    private class AddInterceptorCache implements Interceptor {
        private Context context;

        AddInterceptorCache(Context context) {
            this.context = context;
        }

        @Override
        public @NonNull
        Response intercept(@NonNull Chain chain) throws IOException {
            Request request = chain.request();

            //Trae la petición de cache cuando no hay conexión
            if (!hasNetwork(context)) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                        .build();
            }

            return chain.proceed(request);
        }
    }

    private boolean hasNetwork(Context context) {
        boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
            isConnected = true;
        return isConnected;
    }
}
