package com.example.androidtestrappi;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class SearchableActivity extends AppCompatActivity {
    private static final String TAG = "TAG_SearchableAc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            /*doMySearch(query);*/
            Log.d(TAG, "Query: " + query);
        }

    }
}
