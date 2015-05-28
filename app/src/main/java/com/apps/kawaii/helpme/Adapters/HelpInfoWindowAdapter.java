package com.apps.kawaii.helpme.Adapters;

/**
 * Created by Samer on 24/05/2015.
 */
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.kawaii.helpme.Activities.HelpDetailsActivity;
import com.apps.kawaii.helpme.Models.Help;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.Utils.MapPoint;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

public class HelpInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "HelpInfoWindowAdapter";
    LayoutInflater inflater = null;
    private HashMap<Marker, Help> points;
    private LatLng mCurrentLocation;

    public HelpInfoWindowAdapter(LayoutInflater inflater, HashMap<Marker, Help> points) {
        this.points = points;
        this.inflater = inflater;

    }

    @Override
    public View getInfoWindow(final Marker marker) {
        View popup = inflater.inflate(R.layout.popup, null);

        popup.setClickable(true);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "popup clicked");

            }
        });

        TextView titleTextView = (TextView) popup.findViewById(R.id.title);
        TextView regionTextView = (TextView) popup.findViewById(R.id.address);
        TextView distanceTextView = (TextView) popup.findViewById(R.id.distance);

        Help help = (Help) points.get(marker);

        if (help != null) {
            titleTextView.setText(help.description);
            regionTextView.setText(help.category);

            if(mCurrentLocation != null) {
                float[] results = new float[1];
                Location.distanceBetween(mCurrentLocation.latitude, mCurrentLocation.longitude,
                        help.latitude, help.logitude, results);
                distanceTextView.setText(String.valueOf(results[0] / 1000) + " KM");
            }

        }


        ImageView callImageView  = (ImageView) popup.findViewById(R.id.callButton);
        callImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "call clicked");

            }
        });
        return (popup);
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;

    }

    public void setCurrentLocation(LatLng currentLocation) {
        this.mCurrentLocation = currentLocation;

    }
}
