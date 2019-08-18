package com.example.androidtestrappi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.androidtestrappi.ActivityDetailsMovie;
import com.example.androidtestrappi.ActivityDetailsTv;
import com.example.androidtestrappi.R;
import com.example.androidtestrappi.negocio.MovieItem;
import com.example.androidtestrappi.negocio.MovieItemAdapter;
import com.example.androidtestrappi.negocio.MovieResponse;
import com.example.androidtestrappi.negocio.TvItemAdapter;
import com.example.androidtestrappi.negocio.TvResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.androidtestrappi.persistencia.ApiRequest;

import java.util.List;

public class PlaceholderFragment extends Fragment {

    private static final String TAG = "TAG_FRAGMENT";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final CallbackResponse callbackResponse = new CallbackResponse();
    private static String navigationTypePath = "movie";
    private String categoryPath = "popular";
    private MovieItemAdapter movieItemAdapter;
    private TvItemAdapter tvItemAdapter;
    private boolean loading = false;

    private TextView textView;
    private ProgressBar progressBar;
    private ListView listView;

    private ApiRequest apiRequest;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        Log.d(TAG, "newInstance");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        switch (index) {
            case 1:
                categoryPath = "popular";
                break;
            case 2:
                categoryPath = "top_rated";
                break;
            default:
                categoryPath = "upcoming";
        }
        if (getContext() != null) {
            apiRequest = new ApiRequest(getContext());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        textView = root.findViewById(R.id.section_label);
        progressBar = root.findViewById(R.id.progressBar);
        listView = root.findViewById(R.id.listmovies);

        movieItemAdapter = null;
        tvItemAdapter = null;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent activityDetails;
                Bundle b = new Bundle();
                if (navigationTypePath.equalsIgnoreCase("movie")) {
                    activityDetails = new Intent(getActivity(), ActivityDetailsMovie.class);
                    b.putInt("MovieId", movieItemAdapter.getItem(position).getId());
                } else {
                    activityDetails = new Intent(getActivity(), ActivityDetailsTv.class);
                    b.putInt("TvId", tvItemAdapter.getItem(position).getId());
                }
                activityDetails.putExtras(b);
                startActivity(activityDetails);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (visibleItemCount != 0 && ((firstVisibleItem + visibleItemCount) >= (totalItemCount)) && !loading) {

                    if (navigationTypePath.equalsIgnoreCase("movie")) {
                        if (movieItemAdapter.getPage() < movieItemAdapter.getTotalPages() && !movieItemAdapter.isFiltered())
                            getPost(movieItemAdapter.getPage() + 1);
                    } else {
                        if (tvItemAdapter.getPage() < tvItemAdapter.getTotalPages() && !tvItemAdapter.isFiltered())
                            getPost(tvItemAdapter.getPage() + 1);
                    }

                }
            }
        });

        getPost(1);
        return root;
    }

    private class CallbackResponse implements Callback {
        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) {
            Log.d(TAG, "onResponse");
            if (response.body() instanceof MovieResponse) {

                MovieResponse data = (MovieResponse) response.body();
                if (movieItemAdapter != null) {
                    List<MovieItem> res=( List<MovieItem>)data.getResults();
                    movieItemAdapter.getFilter().filter("");
                    movieItemAdapter.setPage(data.getPage());
                    movieItemAdapter.addItems(res);
                    movieItemAdapter.notifyDataSetChanged();
                } else {
                    movieItemAdapter = new MovieItemAdapter(getActivity(), data.getResults(), data.getTotal_pages());
                    listView.setAdapter(movieItemAdapter);
                }
            } else if (response.body() instanceof TvResponse) {
                TvResponse data = (TvResponse) response.body();
                if (tvItemAdapter != null) {
                    tvItemAdapter.getFilter().filter("");
                    tvItemAdapter.setPage(data.getPage());
                    tvItemAdapter.addItems(data.getResults());
                    tvItemAdapter.notifyDataSetChanged();
                } else {
                    tvItemAdapter = new TvItemAdapter(getActivity(), data.getResults(), data.getTotal_pages());
                    listView.setAdapter(tvItemAdapter);
                }
            } else {
                listView.setAdapter(null);
                tvItemAdapter = null;
                movieItemAdapter = null;
                textView.setVisibility(View.VISIBLE);
                textView.setText(R.string.Info_not_available);
            }
            progressBar.setVisibility(View.GONE);
            loading = false;
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull Throwable t) {
            Log.d(TAG, "onFailure");
            listView.setSelection(0);
            listView.smoothScrollBy(1, 0);
            progressBar.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(R.string.connection_fails);
            loading = false;
        }

    }

    private void getPost(int page) {
        Log.d(TAG, "getPosts");

        if (apiRequest != null) {
            loading = true;
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            apiRequest.getData(callbackResponse, navigationTypePath, categoryPath, page);
        }
    }

    public void filterListview(String s) {
        if (navigationTypePath.equalsIgnoreCase("movie")) {
            if (movieItemAdapter != null) movieItemAdapter.getFilter().filter(s);
        } else {
            if (tvItemAdapter != null) tvItemAdapter.getFilter().filter(s);
        }
    }

    public void setNavigationTypePath(String value) {
        navigationTypePath = value;
    }

    public int getNavigationTypePath() {
        if (navigationTypePath.equalsIgnoreCase("movie")) return R.id.nav_movies;
        return R.id.nav_series;
    }
}
