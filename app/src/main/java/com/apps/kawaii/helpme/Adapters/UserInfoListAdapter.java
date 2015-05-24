package com.apps.kawaii.helpme.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.kawaii.helpme.Models.UserInfo;
import com.apps.kawaii.helpme.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Samer on 30/04/2015.
 */
public class UserInfoListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<UserInfo> mUserInfos;

    public UserInfoListAdapter(Context context, ArrayList<UserInfo> userInfos) {
        mContext = context;
        mUserInfos = userInfos;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_info_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final UserInfo currentUserInfo = getItem(position);

        viewHolder.image.setImageResource(currentUserInfo.image);
        viewHolder.info.setText(currentUserInfo.info);
        viewHolder.subInfo.setText(currentUserInfo.subInfo==null || currentUserInfo.subInfo.equals("") ?"not available":currentUserInfo.subInfo);
        if (currentUserInfo.info.equals("Mobile") && (!(currentUserInfo.subInfo==null || currentUserInfo.subInfo.equals("")))){
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + currentUserInfo.subInfo));
                    mContext.startActivity(callIntent);
                }
            });
        }
        return convertView;
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'user_info_list_item.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @InjectView(R.id.image)
        ImageView image;
        @InjectView(R.id.info)
        TextView info;
        @InjectView(R.id.subInfo)
        TextView subInfo;
        @InjectView(R.id.infoContainer)
        LinearLayout infoContainer;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
