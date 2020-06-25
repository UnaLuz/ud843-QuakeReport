package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<Earthquake> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // If the view doesn't exist inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_item, parent, false);
        }

        // Get the current earthquake item
        Earthquake currentEarthquake = getItem(position);
        if (currentEarthquake != null) {
            // Get the text view with ID magnitude_text_view
            TextView magTextView = listItemView.findViewById(R.id.magnitude_text_view);
            // Set it's text to show the magnitude of the earthquake
            magTextView.setText(currentEarthquake.getMag());

            // Get the text view with ID area_text_view
            TextView areaTextView = listItemView.findViewById(R.id.area_text_view);
            // Get the text view with ID city_text_view
            TextView cityTextView = listItemView.findViewById(R.id.city_text_view);
            // Get the location String
            String location = currentEarthquake.getLocation();

            // Find where the word 'of' is
            int separationIndex = location.indexOf(" of ");
            // If the word 'of' is in the location means that there is a relative location
            // so use that for the areaTextView including the word 'of'
            // and the rest to the cityTextView
            if (separationIndex != -1) {
                areaTextView.setText(location.substring(0, separationIndex + 3));
                cityTextView.setText(location.substring(separationIndex + 4));
            } else {
                areaTextView.setText(R.string.near_the);
                cityTextView.setText(location);
            }

            // Create a new Date object from the time in milliseconds of the earthquake
            Date dateObject = new Date(currentEarthquake.getTimeInMillis());

            // Find the TextView with view ID date
            TextView dateView = listItemView.findViewById(R.id.date_text_view);
            // Format the date string (i.e. "Mar 3, 1984")
            String formattedDate = formatDate(dateObject);
            // Capitalize the first letter of the month
            formattedDate = formattedDate.substring(0, 1).toUpperCase() + formattedDate.substring(1);
            // Display the date of the current earthquake in that TextView
            dateView.setText(formattedDate);

            // Find the TextView with view ID time
            TextView timeView = listItemView.findViewById(R.id.time_text_view);
            // Format the time string (i.e. "4:30PM")
            String formattedTime = formatTime(dateObject);
            // Display the time of the current earthquake in that TextView
            timeView.setText(formattedTime);
        }

        // Return the item view with the correct information
        return listItemView;
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy", Locale.getDefault());
        return dateFormat.format(date);
    }

    private String formatTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        return timeFormat.format(date);
    }
}
