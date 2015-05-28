package com.apps.kawaii.helpme.Fragments;


import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.apps.kawaii.helpme.InternalSystemClasses.HelpApplication;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.net.AjaxClient;
import com.apps.kawaii.helpme.net.AjaxFactory;
import com.gc.materialdesign.views.ButtonRectangle;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpRequesterFragment extends Fragment {


    @InjectView(R.id.helpTitle)
    EditText helpTitle;
    @InjectView(R.id.helpDescription)
    EditText helpDescription;
    @InjectView(R.id.helpSubmitBtn)
    ButtonRectangle helpSubmitBtn;

    public HelpRequesterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_help_requester, container, false);
        ButterKnife.inject(this, view);

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Activity.LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the username of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location From GPS
        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }


        final Location finalLocation = location;
        helpSubmitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalLocation !=null){
                    AjaxFactory factory = AjaxFactory.askForHelp(helpTitle.getText().toString(), String.valueOf(finalLocation.getLatitude()),String.valueOf(finalLocation.getLongitude()), "I need help", helpDescription.getText().toString(),String.valueOf(HelpApplication.appUser.id));
                    AjaxClient.sendRequest(getActivity(), factory, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String object, AjaxStatus status) {
                            super.callback(url, object, status);
                            
                            Toast.makeText(getActivity(), "Help has been submitted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    }
                    else Toast.makeText(getActivity(), "Sorry we are unable to locate you, please check your location service", Toast.LENGTH_LONG).show();
                }
            });




        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
