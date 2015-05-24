package com.apps.kawaii.helpme.Fragments;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import com.apps.kawaii.helpme.Models.Help;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.net.AjaxClient;
import com.apps.kawaii.helpme.net.AjaxFactory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.MapView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment  {


    private GoogleApiClient mGoogleApiClient;

    public MapFragment() {
        // Required empty public constructor
    }
    MapView mMapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getHelpsAround();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_map, container, false);
        mMapView= (MapView) view.findViewById(R.id.mapview);
        mMapView.setCenter(new LatLng(31.98820428172846, 35.90435028076172));
        mMapView.setZoom(18);
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
        return view;
    }

    public void getHelpsAround()
    {
        AjaxFactory ajaxFactory=AjaxFactory.getHelpsAround("31.98820428172846","35.90435028076172");

        AjaxClient.sendRequest(getActivity(),ajaxFactory, Help[].class,new AjaxCallback<Help[]>(){
            @Override
            public void callback(String url, Help[] object, AjaxStatus status) {
                drawNeededHelpsAround(object);
            }
        });

    }

    public void drawNeededHelpsAround(Help[] neededHelps){
        for (Help currentHelp : neededHelps){
            Marker marker=new Marker(currentHelp.title,currentHelp.description,new LatLng(currentHelp.latitude,currentHelp.logitude));
            mMapView.addMarker(marker);
        }

    }

    private double mLatitude;
    private double mLongitude;
    private com.google.android.gms.maps.model.LatLng mCurrentLocation;
    private boolean isFirstTime=true;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            mCurrentLocation = new com.google.android.gms.maps.model.LatLng(mLatitude, mLongitude);
            if (isFirstTime) {
                mMapView.setCenter(new LatLng(mLatitude,mLongitude));

                isFirstTime = false;
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
