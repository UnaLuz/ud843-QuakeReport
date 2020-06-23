package com.example.android.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

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
            // Get the text view with ID magnitud_text_view
            TextView magTextView = listItemView.findViewById(R.id.magnitud_text_view);
            // Set it's text to show the magnitude of the earthquake
            magTextView.setText(currentEarthquake.getMag());

            // Get the text view with ID location_text_view
            TextView locationTextView = listItemView.findViewById(R.id.location_text_view);
            // Set it's text to show the location of the earthquake
            locationTextView.setText(currentEarthquake.getLocation());

            // Get the text view with ID date_text_view
            TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
            // Set it's text to show the date of the earthquake
            dateTextView.setText(currentEarthquake.getDate());
        }

        // Return the item view with the correct information
        return listItemView;
    }
}
