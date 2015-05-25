package com.apps.kawaii.helpme.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.kawaii.helpme.Activities.NewHelpActivity;
import com.apps.kawaii.helpme.R;
import com.gc.materialdesign.views.ButtonFloat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.neokree.googlenavigationdrawer.GoogleNavigationDrawer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapContainerFragment extends Fragment {


    @InjectView(R.id.buttonFloat)
    ButtonFloat buttonFloat;

    public MapContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_container, container, false);
        ButterKnife.inject(this, view);
        getChildFragmentManager().beginTransaction().add(R.id.container, CustomMapFragment.newInstance()).commit();

        buttonFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o=new Intent(getActivity(), NewHelpActivity.class);
                getActivity().startActivity(o);
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
