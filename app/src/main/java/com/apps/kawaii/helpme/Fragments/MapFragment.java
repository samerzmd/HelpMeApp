package com.apps.kawaii.helpme.Fragments;

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
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.views.InfoWindow;
import com.mapbox.mapboxsdk.views.MapView;

import lombok.val;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {


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
}
