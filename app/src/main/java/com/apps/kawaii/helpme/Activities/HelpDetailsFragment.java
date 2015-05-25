package com.apps.kawaii.helpme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.apps.kawaii.helpme.InternalSystemClasses.HelpApplication;
import com.apps.kawaii.helpme.Models.Help;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.net.AjaxClient;
import com.apps.kawaii.helpme.net.AjaxFactory;
import com.gc.materialdesign.views.ButtonRectangle;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A placeholder fragment containing a simple view.
 */
public class HelpDetailsFragment extends Fragment {
    private static String ARGS_HELP = "ARGS_HELP";
    @InjectView(R.id.responeToHelpBtn)
    ButtonRectangle responeToHelpBtn;
    @InjectView(R.id.checkUserAccountButton)
    ButtonRectangle checkUserAccountButton;
    @InjectView(R.id.helpTitleTxv)
    TextView helpTitleTxv;
    @InjectView(R.id.helpDescription)
    TextView helpDescription;
    @InjectView(R.id.div)
    View div;
    @InjectView(R.id.categoryTitle)
    TextView categoryTitle;
    @InjectView(R.id.categoryTxv)
    TextView categoryTxv;
    @InjectView(R.id.approveHelpBtn)
    ButtonRectangle approveHelpBtn;
    @InjectView(R.id.helpOnWayTxv)
    TextView helpOnWayTxv;

    public static Fragment newInstance(Help help) {
        HelpDetailsFragment fragment = new HelpDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_HELP, help);
        fragment.setArguments(bundle);
        return fragment;
    }

    public HelpDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Help help = getArguments().getParcelable(ARGS_HELP);
        View view = inflater.inflate(R.layout.fragment_help_detalis, container, false);
        ButterKnife.inject(this, view);
        helpTitleTxv.setText(help.title == null ? "" : help.title);
        helpDescription.setText(help.description == null ? "" : help.description);
        categoryTxv.setText(help.category == null ? "" : help.category);

        if (help.respondent_id == null) {
            responeToHelpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AjaxFactory factory = AjaxFactory.acceptHelp(String.valueOf(help.id), String.valueOf(HelpApplication.appUser.id));
                    AjaxClient.sendRequest(getActivity(), factory, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String object, AjaxStatus status) {
                            Toast.makeText(getActivity(), object.contains("true") ? "done" : "plz try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            checkUserAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent o = new Intent(getActivity(), UserActivity.class);
                    o.putExtra(UserActivity.KEY_USER_ID, help.asker_id);
                    getActivity().startActivity(o);
                }
            });
        } else if (help.asker_id == HelpApplication.appUser.id) {
            responeToHelpBtn.setVisibility(View.GONE);
            approveHelpBtn.setVisibility(View.VISIBLE);
            approveHelpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AjaxFactory factory = AjaxFactory.approveHelp(String.valueOf(help.id));
                    AjaxClient.sendRequest(getActivity(), factory, String.class, new AjaxCallback<String>() {
                        @Override
                        public void callback(String url, String object, AjaxStatus status) {
                            Toast.makeText(getActivity(), object.contains("true") ? "done" : "plz try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            checkUserAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent o = new Intent(getActivity(), UserActivity.class);
                    o.putExtra(UserActivity.KEY_USER_ID, help.respondent_id);
                    getActivity().startActivity(o);
                }
            });
        }
        if (help.status == 3) {
            checkUserAccountButton.setVisibility(View.GONE);
            approveHelpBtn.setVisibility(View.GONE);
            responeToHelpBtn.setVisibility(View.GONE);
            helpOnWayTxv.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
