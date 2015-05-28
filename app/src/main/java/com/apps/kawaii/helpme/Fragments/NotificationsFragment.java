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
import com.apps.kawaii.helpme.InternalSystemClasses.HelpApplication;
import com.apps.kawaii.helpme.Models.Help;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.net.AjaxClient;
import com.apps.kawaii.helpme.net.AjaxFactory;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NotificationsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @InjectView(R.id.HelpList)
    ListView HelpList;




    public static NotificationsFragment newInstance() {
        NotificationsFragment fragment = new NotificationsFragment();
        return fragment;
    }

    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.inject(this, view);
        AjaxFactory ajaxFactory = AjaxFactory.getAskedHelps(String.valueOf(HelpApplication.appUser.id));

        AjaxClient.sendRequest(getActivity(), ajaxFactory, Help[].class, new AjaxCallback<Help[]>() {
            @Override
            public void callback(String url, Help[] object, AjaxStatus status) {
                ArrayList<Help>helps=new ArrayList<Help>();
                for (Help help:object){
                    if (help.respondent_id!=null || help.status==3)
                        helps.add(help);
                }

                HelpList.setAdapter(new HelpListAdapter(getActivity(), helps));
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
