package com.apps.kawaii.helpme.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.Utils.MapPoint;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by MHijazi on 6/19/2014.
 */
public class CustomMapFragment extends SupportMapFragment {
    private static final String TAG = "CustomMapFragment";

    GoogleMap mGoogleMap;
    ArrayList<LatLng> mMarkerPoints;
    double mLatitude = 0;
    double mLongitude = 0;
    private boolean isFirstTime = true;
    MapMarkerHelper mapMarkerHelper;

    public static final String EXTRA_POINTS = "com.apps.kawaii.helpme.extra_point";
    public HashMap<Marker, MapPoint> pointsMap;
    private LatLng mCurrentLocation;

    public interface MapMarkerHelper {
        public ArrayList<MapPoint> parsePoints(Bundle bundle);

        public GoogleMap.InfoWindowAdapter getInfoWindowAdapter(HashMap<Marker, MapPoint> points);

        public void onInfoWindowClick(Marker marker, MapPoint mapPoint, LatLng currentLocation);

        public void onLocationChanged(Location location);
    }

    public static CustomMapFragment newInstance(ArrayList points) {
        CustomMapFragment customMapFragment = new CustomMapFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(CustomMapFragment.EXTRA_POINTS, points);
        customMapFragment.setArguments(args);

        return customMapFragment;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MapMarkerHelper) {
            mapMarkerHelper = (MapMarkerHelper) activity;
        } else {
            Log.w(TAG, "Parent Activity must implements MapMarkerHelper");
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initGoogleMaps(this);

    }


    private void initGoogleMaps(SupportMapFragment fm) {
        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        if (status != ConnectionResult.SUCCESS) { // Google Play Services are not available
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getActivity(), requestCode);
            dialog.show();

        } else { // Google Play Services are available

            // Initializing
            mMarkerPoints = new ArrayList<LatLng>();

            // Getting Map for the SupportMapFragment
            mGoogleMap = fm.getMap();

            if (mapMarkerHelper != null) {
                pointsMap = new HashMap<Marker, MapPoint>();
                ArrayList<MapPoint> points = mapMarkerHelper.parsePoints(getArguments());
                Iterator<MapPoint> iterator = points.iterator();
                while (iterator.hasNext()) {
                    MapPoint point = iterator.next();
                    Log.d(TAG, "Point: " + point.getLatitude() + "," + point.getLongitude());
                    LatLng ll = new LatLng(point.getLatitude(), point.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(ll);
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.service_pin));
                    Marker marker = mGoogleMap.addMarker(markerOptions);
                    pointsMap.put(marker, point);

                }

                mGoogleMap.setInfoWindowAdapter(mapMarkerHelper.getInfoWindowAdapter(pointsMap));
                mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {

                        mapMarkerHelper.onInfoWindowClick(marker, pointsMap.get(marker), mCurrentLocation);
                    }
                });

            }

            // Enable MyLocation Button in the Map
            mGoogleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Activity.LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location From GPS
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                locationListener.onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(provider, 20000, 0, locationListener);
        }

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void drawMarker(LatLng point) {
        mMarkerPoints.add(point);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        // Setting the position of the marker
        options.position(point);

        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        // Add new marker to the Google Map Android API V2
        mGoogleMap.addMarker(options);

    }

    public void moveCameraToPoint(MapPoint mapPoint) {
        isFirstTime=false;
        Set<Marker> Markers = pointsMap.keySet();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mapPoint.getLatitude(),mapPoint.getLongitude())));
        for(Marker marker : Markers) {
            MapPoint p = pointsMap.get(marker);
            if (mapPoint.equals(p))
            {
                marker.showInfoWindow();
            }
        }
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            mCurrentLocation = new LatLng(mLatitude, mLongitude);
            if (isFirstTime) {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(mCurrentLocation));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                isFirstTime = false;
            }

            if (mapMarkerHelper != null) {
                mapMarkerHelper.onLocationChanged(location);
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
