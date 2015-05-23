package com.apps.kawaii.helpme.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.apps.kawaii.helpme.Adapters.HelpListAdapter;
import com.apps.kawaii.helpme.Models.Help;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.net.AjaxClient;
import com.apps.kawaii.helpme.net.AjaxFactory;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HelpHistoryFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.HelpList)
    ListView HelpList;


    public static HelpHistoryFragment newInstance() {
        HelpHistoryFragment fragment = new HelpHistoryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public HelpHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help_history, container, false);
        ButterKnife.inject(this, view);
        AjaxFactory ajaxFactory=AjaxFactory.getHelpsAround("31.98820428172846", "35.90435028076172");

        AjaxClient.sendRequest(getActivity(), ajaxFactory, Help[].class, new AjaxCallback<Help[]>() {
            @Override
            public void callback(String url, Help[] object, AjaxStatus status) {
            HelpList.setAdapter(new HelpListAdapter(getActivity(), Arrays.asList(object)));
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
