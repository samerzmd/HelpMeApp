package com.apps.kawaii.helpme.Fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.kawaii.helpme.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpCenterFragment extends Fragment {


    public HelpCenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_center, container, false);
    }


}
