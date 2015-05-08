package com.apps.kawaii.helpme.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.apps.kawaii.helpme.Adapters.UserInfoListAdapter;
import com.apps.kawaii.helpme.Models.UserInfo;
import com.apps.kawaii.helpme.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.val;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {


    @InjectView(R.id.userImage)
    ImageView userImage;
    @InjectView(R.id.userInfoList)
    ListView userInfoList;

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.inject(this, view);
        val a=new ArrayList<UserInfo>();

        //for the sake of testing
        //region test
        UserInfo u= new UserInfo();
        u.image= getResources().getDrawable( R.drawable.ic_launcher );
        u.info="sdfjsfkj";
        u.subInfo="sssss";
        a.add(u);
        a.add(u);
        a.add(u);
        a.add(u);
        a.add(u);
        a.add(u);
        a.add(u);
        a.add(u);
        //endregion

        userInfoList.setAdapter(new UserInfoListAdapter(getActivity(),a));
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
