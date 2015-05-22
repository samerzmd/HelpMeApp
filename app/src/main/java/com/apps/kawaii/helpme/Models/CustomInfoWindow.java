package com.apps.kawaii.helpme.Models;

import android.view.View;

import com.apps.kawaii.helpme.R;
import com.mapbox.mapboxsdk.views.InfoWindow;
import com.mapbox.mapboxsdk.views.MapView;

/**
 * Created by Samer on 22/05/2015.
 */
public class CustomInfoWindow extends InfoWindow {
    public CustomInfoWindow(MapView mapView) {
        super(R.layout.custom_info_window, mapView);
    }
}
