package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryUtils {
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(String response) {
        // If the query response is null or empty there is nothing to do so finish early
        if (TextUtils.isEmpty(response))
            return null;
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the response. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Convert the query response into a JSONObject
            JSONObject jsonResponse = new JSONObject(response);

            // Get the array of features or earthquake info
            JSONArray featuresArray = jsonResponse.getJSONArray("features");

            for (int i = 0; i < featuresArray.length(); i++){
                // Get the feature (or earthquake) at index i
                JSONObject currentFeature = featuresArray.getJSONObject(i);
                // Get the properties of the feature or earthquake
                JSONObject currentProperties = currentFeature.getJSONObject("properties");

                // Get all the info of the earthquake at index i
                float mag = (float) currentProperties.getDouble("mag");
                String location = currentProperties.getString("place");
                long timeInMilliseconds = currentProperties.getLong("time");
                String earthquakeUrl = currentProperties.getString("url");

                // Create an earthquake object with the data gathered
                // and add it to the list
                earthquakes.add(new Earthquake(mag, location, timeInMilliseconds, earthquakeUrl));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
}
