package com.apps.kawaii.helpme.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.apps.kawaii.helpme.Models.UserInfo;
import com.apps.kawaii.helpme.R;

import java.util.ArrayList;

/**
 * Created by Samer on 30/04/2015.
 */
public class UserInfoListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<UserInfo> mUserInfos;
    public UserInfoListAdapter(Context context,ArrayList<UserInfo> userInfos){
        mContext=context;
        mUserInfos=userInfos;
    }
    @Override
    public int getCount() {
        return mUserInfos.size();
    }

    @Override
    public UserInfo getItem(int position) {
        return mUserInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(R.layout.user_info_list_item,parent,false);

        return view;
    }
}
