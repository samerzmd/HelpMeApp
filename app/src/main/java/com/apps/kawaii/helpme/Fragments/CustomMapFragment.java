package com.apps.kawaii.helpme.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.apps.kawaii.helpme.Activities.HelpDetailsActivity;
import com.apps.kawaii.helpme.Adapters.HelpInfoWindowAdapter;
import com.apps.kawaii.helpme.Models.Help;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.Utils.MapPoint;
import com.apps.kawaii.helpme.net.AjaxClient;
import com.apps.kawaii.helpme.net.AjaxFactory;
import com.gc.materialdesign.views.ButtonFloat;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

    public HashMap<Marker, Help> pointsMap;
    private Location mCurrentLocation;

        public static CustomMapFragment newInstance() {
        CustomMapFragment customMapFragment = new CustomMapFragment();
        return customMapFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= super.onCreateView(inflater, container, savedInstanceState);
        Button buttonFloat=new Button(getActivity());
        buttonFloat.setText("ssss");
        buttonFloat.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        container.addView(buttonFloat);
        return view;
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
//region ss
           /* if (mapMarkerHelper != null) {
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
                });*/
//endregion
            }

            // Enable MyLocation Button in the Map
            mGoogleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Activity.LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the username of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location From GPS
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                locationListener.onLocationChanged(location);
            }

            locationManager.requestLocationUpdates(provider, 8000, 0, locationListener);
            mCurrentLocation = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (mCurrentLocation != null) {
            locationListener.onLocationChanged(mCurrentLocation);

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

    public void moveCameraToPoint(Help help) {
        isFirstTime=false;
        Set<Marker> Markers = pointsMap.keySet();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(help.latitude, help.logitude)));
        for(Marker marker : Markers) {
            Help p = pointsMap.get(marker);
            if (help.equals(p))
            {
                marker.showInfoWindow();
            }
        }
    }

    private boolean isFirstLoad=true;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            mCurrentLocation = location;
            if (isFirstTime) {
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mLatitude,mLongitude)));
                mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                isFirstTime = false;
            }
            AjaxFactory factory=AjaxFactory.getHelpsAround(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
            AjaxClient.sendRequest(getActivity(),factory, Help[].class,new AjaxCallback<Help[]>(){
                @Override
                public void callback(String url, Help[] object, AjaxStatus status) {
                    if (pointsMap!=null)
                    for (Marker marker:pointsMap.keySet()){
                        if (!Arrays.asList(object).contains(pointsMap.get(marker))){
                            marker.remove();
                        }
                    }
                    if (isFirstLoad) {
                        pointsMap=new HashMap<Marker, Help>();
                        for (Help help : object) {

                            isFirstLoad = false;
                            Log.d(TAG, "Point: " + help.latitude + "," + help.logitude);
                            LatLng ll = new LatLng(help.latitude, help.logitude);
                            MarkerOptions markerOptions = new MarkerOptions().position(ll);
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.service_pin));
                            Marker marker = mGoogleMap.addMarker(markerOptions);

                            pointsMap.put(marker, help);
                        }
                    }
                     else {
                        for (Help help : object) {
                            if (!pointsMap.containsValue(help)) {
                                Log.d(TAG, "Point: " + help.latitude + "," + help.logitude);
                                LatLng ll = new LatLng(help.latitude, help.logitude);
                                MarkerOptions markerOptions = new MarkerOptions()
                                        .position(ll);
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.service_pin));
                                Marker marker = mGoogleMap.addMarker(markerOptions);
                                pointsMap.put(marker, help);
                            }
                        }
                    }

                    HelpInfoWindowAdapter adapter=new HelpInfoWindowAdapter(getActivity().getLayoutInflater(),pointsMap);
                    mGoogleMap.setInfoWindowAdapter(adapter);
                    mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Intent o = new Intent(getActivity(), HelpDetailsActivity.class);
                            Help help1=pointsMap.get(marker);
                            o.putExtra(HelpDetailsActivity.KEY_HELP, help1);
                            getActivity().startActivity(o);
                        }
                    });
                }
            });
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

    @Override
    public void onPause() {
        super.onPause();
        isFirstTime=true;
    }
}
