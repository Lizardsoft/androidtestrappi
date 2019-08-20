package com.example.androidtestrappi.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.androidtestrappi.ActivityDetailsMovie;
import com.example.androidtestrappi.ActivityDetailsTv;
import com.example.androidtestrappi.R;

import com.example.androidtestrappi.negocio.MovieItemAdapter;
import com.example.androidtestrappi.negocio.MovieResponse;
import com.example.androidtestrappi.negocio.TvItemAdapter;
import com.example.androidtestrappi.negocio.TvResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.androidtestrappi.persistencia.ApiRequest;

public class PlaceholderFragment extends Fragment {
    private static final String TAG = "TAG_FRAGMENT";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private final CallbackResponse callbackResponse = new CallbackResponse();
    private static String navigationTypePath = "movie";
    private String categoryPath = "popular";
    private MovieItemAdapter movieItemAdapter;
    private TvItemAdapter tvItemAdapter;
    private boolean loading = false;

    private View footerView;
    private FrameLayout frameLayout;
    private ConstraintLayout retry_Layout;

    private TextView textView_not_Info;
    private ProgressBar progressBar;
    private ListView listView;

    private ApiRequest apiRequest;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        navigationTypePath = "movie";
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
        textView_not_Info = root.findViewById(R.id.textView_not_Info);
        progressBar = root.findViewById(R.id.progressBar);
        listView = root.findViewById(R.id.listmovies);
        retry_Layout = root.findViewById(R.id.retry_Layout);

        movieItemAdapter = null;
        tvItemAdapter = null;
        loading = false;

        footerView = inflater.inflate(R.layout.footer_layout, container, false);
        if (getContext() != null) {
            frameLayout = new FrameLayout(getContext());
            listView.addFooterView(frameLayout);
        }

        footerView.findViewById(R.id.button_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });

        retry_Layout.findViewById(R.id.button_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPost(1);
            }
        });

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
                    if (frameLayout.getChildCount() == 0) {
                        loadMore();
                    }
                }
            }
        });

        getPost(1);
        return root;
    }

    private void loadMore() {
        if (navigationTypePath.equalsIgnoreCase("movie")) {
            if (movieItemAdapter != null && movieItemAdapter.getPage() < movieItemAdapter.getTotalPages() && !movieItemAdapter.isFiltered())
                getPost(movieItemAdapter.getPage() + 1);
        } else {
            if (tvItemAdapter != null && tvItemAdapter.getPage() < tvItemAdapter.getTotalPages() && !tvItemAdapter.isFiltered())
                getPost(tvItemAdapter.getPage() + 1);
        }
    }

    private void setVisibilityFooter(int visibility) {
        if (visibility == View.VISIBLE) {
            frameLayout.removeAllViews();
            frameLayout.addView(footerView);
        } else {
            frameLayout.removeAllViews();
        }
    }

    private class CallbackResponse implements Callback {
        @Override
        public void onResponse(@NonNull Call call, @NonNull Response response) {
            Log.d(TAG, "onResponse");
            if (response.body() instanceof MovieResponse) {
                MovieResponse data = (MovieResponse) response.body();
                if (navigationTypePath.equalsIgnoreCase("movie")) {
                    if (movieItemAdapter != null) {
                        movieItemAdapter.getFilter().filter("");
                        movieItemAdapter.setPage(data.getPage());
                        movieItemAdapter.addItems(data.getResults());
                        movieItemAdapter.notifyDataSetChanged();
                    } else {
                        movieItemAdapter = new MovieItemAdapter(getActivity(), data.getResults(), data.getTotal_pages());
                        listView.setAdapter(movieItemAdapter);
                    }
                }
            } else if (response.body() instanceof TvResponse) {
                TvResponse data = (TvResponse) response.body();
                if (navigationTypePath.equalsIgnoreCase("tv")) {
                    if (tvItemAdapter != null) {
                        tvItemAdapter.getFilter().filter("");
                        tvItemAdapter.setPage(data.getPage());
                        tvItemAdapter.addItems(data.getResults());
                        tvItemAdapter.notifyDataSetChanged();
                    } else {
                        tvItemAdapter = new TvItemAdapter(getActivity(), data.getResults(), data.getTotal_pages());
                        listView.setAdapter(tvItemAdapter);
                    }
                }
            } else {
                if (apiRequest.hasNetwork(getContext())) {
                    tvItemAdapter = null;
                    movieItemAdapter = null;
                    textView_not_Info.setVisibility(View.VISIBLE);
                } else {
                    if (listView.getAdapter() == null) {
                        retry_Layout.setVisibility(View.VISIBLE);
                    } else {
                        setVisibilityFooter(View.VISIBLE);
                    }
                }
            }
            progressBar.setVisibility(View.GONE);
            loading = false;
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull Throwable t) {
            Log.d(TAG, "onFailure");
            progressBar.setVisibility(View.GONE);
            if (listView.getAdapter() == null) {
                retry_Layout.setVisibility(View.VISIBLE);
            } else {
                setVisibilityFooter(View.VISIBLE);
            }
            loading = false;
        }
    }

    private void getPost(int page) {
        Log.d(TAG, "getPosts");

        if (apiRequest != null) {
            loading = true;
            progressBar.setVisibility(View.VISIBLE);
            textView_not_Info.setVisibility(View.GONE);
            setVisibilityFooter(View.GONE);
            retry_Layout.setVisibility(View.GONE);
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
