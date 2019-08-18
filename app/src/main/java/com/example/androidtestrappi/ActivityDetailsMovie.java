package com.example.androidtestrappi;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.androidtestrappi.negocio.MovieDetailsResponse;
import com.example.androidtestrappi.persistencia.ApiRequest;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityDetailsMovie extends AppCompatActivity {
    private static final String YOUTUBE_API_KEY = "AIzaSyBsOoXmr--RxNv0v8o7aFOsOkcehN--s4U";
    private final CallbackResponse callbackResponse = new CallbackResponse();
    private String idVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        ApiRequest apiRequest = new ApiRequest(getApplicationContext());
        int movieId = 0;

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            movieId = bundle.getInt("MovieId");
        }

        if (movieId != 0) {
            apiRequest.getDataDetails(callbackResponse, "movie", movieId);

            Button button_play_trailer = findViewById(R.id.button_play_trailer);
            button_play_trailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (idVideo != null) {
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent(ActivityDetailsMovie.this, YOUTUBE_API_KEY, idVideo, 0, true, true);
                        startActivity(intent);
                    }
                }
            });

        } else {
            finish();
        }
    }

    private class CallbackResponse implements Callback {
        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) {
            MovieDetailsResponse data = (MovieDetailsResponse) response.body();
            final String MOVIE_BASE_URL = "http://image.tmdb.org/t/p/w780";

            if (data != null) {
                ImageView image_backdrop = findViewById(R.id.image_backdrop);
                ImageView image_Poster = findViewById(R.id.image_Poster);
                TextView textView_title = findViewById(R.id.textView_title);
                ProgressBar progressBar_user_score = findViewById(R.id.progressBar_user_score);
                TextView textView_user_score_value = findViewById(R.id.textView_user_score_value);
                Button button_play_trailer = findViewById(R.id.button_play_trailer);
                View view=findViewById(R.id.view);
                ProgressBar progressBarData = findViewById(R.id.progressBarData);

                ConstraintLayout layout_description = findViewById(R.id.layout_description);
                TextView textView_description = findViewById(R.id.textView_description);

                LinearLayout layout_Info = findViewById(R.id.layout_Info);
                TextView textView_original_title = findViewById(R.id.textView_original_title);
                TextView textView_state = findViewById(R.id.textView_state);
                TextView textView_release_date = findViewById(R.id.textView_release_date);
                TextView textView_original_language = findViewById(R.id.textView_original_language);
                TextView textView_runtime = findViewById(R.id.textView_runtime);
                TextView textView_budget = findViewById(R.id.textView_budget);
                TextView textView_revenue = findViewById(R.id.textView_revenue);
                TextView textView_genres = findViewById(R.id.textView_genres);

                if (data.getBackdrop_path() != null) {
                    Glide.with(getApplicationContext())
                            .load(MOVIE_BASE_URL + data.getBackdrop_path())
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.placeholder)
                            .thumbnail(0.5f)
                            .into(image_backdrop);
                }

                if (data.getPoster_path() != null) {
                    Glide.with(getApplicationContext())
                            .load(MOVIE_BASE_URL + data.getPoster_path())
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(R.drawable.placeholder)
                            .thumbnail(0.5f)
                            .into(image_Poster);
                }

                textView_title.setText(data.getTitle());
                progressBar_user_score.setProgress((int) (data.getVote_average() * 10));
                textView_user_score_value.setText(getString(R.string.score_value, progressBar_user_score.getProgress()));
                if (data.getVideos().getResults() != null && data.getVideos().getResults().size() > 0) {
                    if (data.getVideos().getResults().get(0).getSite().equalsIgnoreCase("YouTube")) {
                        button_play_trailer.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE);
                        idVideo = data.getVideos().getResults().get(0).getKey();
                    }
                }

                textView_description.setText(data.getOverview());
                textView_original_title.setText(data.getOriginal_title());
                textView_state.setText(data.getStatus());
                textView_release_date.setText(new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(data.getRelease_date().getTime()));
                textView_original_language.setText(data.getOriginal_language());
                textView_runtime.setText(String.format(Locale.getDefault(), "%dh %02dm", data.getRuntime() / 60, data.getRuntime() % 60));
                textView_budget.setText(new DecimalFormat("$#,###", new DecimalFormatSymbols()).format(data.getBudget()));
                textView_revenue.setText(new DecimalFormat("$#,###", new DecimalFormatSymbols()).format(data.getRevenue()));
                String genres = "";
                for (int i = 0; i < data.getGenres().size(); i++) {
                    genres = genres.concat(data.getGenres().get(i).getName().concat(", "));
                }
                textView_genres.setText(genres.substring(0, genres.length() - 2));

                layout_description.setVisibility(View.VISIBLE);
                layout_Info.setVisibility(View.VISIBLE);
                progressBarData.setVisibility(View.GONE);
            } else {
                Toast.makeText(getApplicationContext(), R.string.Info_not_available, Toast.LENGTH_LONG).show();
                finish();
            }
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull Throwable t) {
            t.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.connection_fails, Toast.LENGTH_LONG).show();
            finish();
        }

    }

}
