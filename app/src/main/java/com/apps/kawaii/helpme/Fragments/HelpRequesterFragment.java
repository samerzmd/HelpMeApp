package com.apps.kawaii.helpme.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.kawaii.helpme.Models.Help;
import com.apps.kawaii.helpme.R;

import lombok.val;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpRequesterFragment extends Fragment {


    public HelpRequesterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_help_requester, container, false);
    }


}
