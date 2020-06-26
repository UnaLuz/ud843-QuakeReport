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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        final ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        // Add a click event to each list item
        // This will send an intent for the user to open the earthquake info page
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Earthquake current = adapter.getItem(position);

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
                    Log.e(LOG_TAG, "An error occurred, please read the stack trace", e);

                    Toast.makeText(parent.getContext(), "Error opening url", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }


}
