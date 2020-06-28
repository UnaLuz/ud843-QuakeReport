/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/" +
            "query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=10";
    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static EarthquakeAdapter mAdapter;
    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes but initialize it with empty data
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Add a click event to each list item
        // This will send an intent for the user to open the earthquake info page
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Earthquake current = mAdapter.getItem(position);

                // If the current item is somehow null, finish early
                if (current == null) return;

                // Try to parse the the earthquake url and send an intent
                // Catch the exception so the app doesn't crash
                // print the error message to the logs and Show the user that an error occurred
                try {
                    // Create a new intent to view the earthquake URI
                    Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(current.getUrl()));
                    // Send the intent to launch a new activity
                    startActivity(openBrowser);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Error parsing the url or opening it in a browser", e);

                    Toast.makeText(parent.getContext(), "Error opening url", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = LoaderManager.getInstance(this);

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        // Create a new loader for the given URL
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> data) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a list with actual data, add that to the adapter
        if (data != null && !data.isEmpty()) mAdapter.addAll(data);
            // Else log it and show a message to the user informing that an error occurred
        else {
            Log.e(LOG_TAG, "Error getting the data");
            Toast.makeText(this, "Error loading earthquakes\n" +
                    "Please try restarting the app", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /**
     * AsyncTaskLoader class that replaces the AsyncTask class
     */
    public static class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
        /**
         * Tag for log messages
         */
        private static final String LOG_TAG = EarthquakeLoader.class.getName();

        /**
         * Query URL
         */
        private String mUrl;

        /**
         * Constructs a new {@link EarthquakeLoader}.
         *
         * @param context of the activity
         * @param url     to load data from
         */
        public EarthquakeLoader(Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<Earthquake> loadInBackground() {
            // Return early if there is nothing to do
            if (mUrl == null) return null;

            // Perform the network request, parse the response, and extract a list of earthquakes.
            return QueryUtils.fetchEarthquakeData(mUrl);
        }
    }
}
