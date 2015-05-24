package com.apps.kawaii.helpme.Fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.apps.kawaii.helpme.Adapters.UserInfoListAdapter;
import com.apps.kawaii.helpme.Models.User;
import com.apps.kawaii.helpme.Models.UserInfo;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.net.AjaxClient;
import com.apps.kawaii.helpme.net.AjaxFactory;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import lombok.val;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {
    public  static String KEY_USER_ID="KEYUSERID";

    @InjectView(R.id.userImage)
    ImageView userImage;
    @InjectView(R.id.userInfoList)
    ListView userInfoList;

    public static UserProfileFragment newInstance(String userId){
        UserProfileFragment fragment=new UserProfileFragment();
        Bundle bundle=new Bundle();
        bundle.putString(KEY_USER_ID, userId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public UserProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ButterKnife.inject(this, view);
        AjaxFactory factory=AjaxFactory.getUser(getArguments().getString(KEY_USER_ID));
        AjaxClient.sendRequest(getActivity(),factory, User.class,new AjaxCallback<User>(){
            @Override
            public void callback(String url, User object, AjaxStatus status) {
                if (object!=null){
                    Glide.with(getActivity()).load(object.avatar).into(userImage);
                    ArrayList<UserInfo>userInfos=new ArrayList<UserInfo>();
                    UserInfo userInfo1=new UserInfo(R.drawable.ic_action_user,"Name",object.username);
                    userInfos.add(userInfo1);
                    UserInfo userInfo2=new UserInfo(R.drawable.ic_action_call,"Mobile",object.mobile);
                    userInfos.add(userInfo2);
                    UserInfo userInfo3=new UserInfo(R.drawable.ic_action_mail,"Email",object.email);
                    userInfos.add(userInfo3);
                    UserInfo userInfo4=new UserInfo(R.drawable.ic_action_user_add,"Gender",object.gender);
                    userInfos.add(userInfo4);
                    UserInfo userInfo5=new UserInfo(R.drawable.abc_btn_rating_star_on_mtrl_alpha,"Rate",object.rate);
                    userInfos.add(userInfo5);
                    UserInfo userInfo6=new UserInfo(R.drawable.abc_ic_menu_selectall_mtrl_alpha,"About",object.about);
                    userInfos.add(userInfo6);

                    userInfoList.setAdapter(new UserInfoListAdapter(getActivity(),userInfos));
                }
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
