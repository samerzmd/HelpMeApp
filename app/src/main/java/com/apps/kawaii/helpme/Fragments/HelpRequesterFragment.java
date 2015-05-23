package com.apps.kawaii.helpme.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
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
        ButterKnife.inject(this,view);

        helpSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AjaxFactory factory = AjaxFactory.askForHelp(helpTitle.getText().toString(),"31.988616","35.905881","..1..",helpDescription.getText().toString(),"1");
                AjaxClient.sendRequest(getActivity(),factory,String.class,new AjaxCallback<String>(){
                    @Override
                    public void callback(String url, String object, AjaxStatus status) {
                        super.callback(url, object, status);
                    }
                });
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
