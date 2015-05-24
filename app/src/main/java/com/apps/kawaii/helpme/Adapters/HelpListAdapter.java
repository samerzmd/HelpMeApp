package com.apps.kawaii.helpme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.apps.kawaii.helpme.Models.Help;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.net.AjaxClient;
import com.apps.kawaii.helpme.net.AjaxFactory;
import com.gc.materialdesign.views.ButtonRectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Samer on 23/05/2015.
 */
public class HelpListAdapter extends BaseAdapter {
    Context mContext;
    List<Help> mHelps;

    public HelpListAdapter(Context context, List<Help> helps) {
        mContext = context;
        mHelps = helps;
    }

    @Override
    public int getCount() {
        return mHelps.size();
    }

    @Override
    public Help getItem(int position) {
        return mHelps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mHelps.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.help_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Help currentHelp=getItem(position);
        viewHolder.helpTitle.setText(currentHelp.title==null?"":currentHelp.title);
        viewHolder.helpDescription.setText(currentHelp.description==null?"":currentHelp.description);
        viewHolder.checkUserAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo open user profile
            }
        });
        viewHolder.responeToHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AjaxFactory factory=AjaxFactory.acceptHelp(String.valueOf(currentHelp.id),"1");
                AjaxClient.sendRequest(mContext,factory,String.class,new AjaxCallback<String>(){
                    @Override
                    public void callback(String url, String object, AjaxStatus status) {
                        super.callback(url, object, status);
                        object= object.contains("true")?"done":"failed";
                        Toast.makeText(mContext,object,Toast.LENGTH_SHORT).show();
                        AjaxFactory ajaxFactory=AjaxFactory.getHelpsAround("31.98820428172846", "35.90435028076172");

                        AjaxClient.sendRequest(mContext, ajaxFactory, Help[].class, new AjaxCallback<Help[]>() {
                            @Override
                            public void callback(String url, Help[] object, AjaxStatus status) {
                                mHelps = Arrays.asList(object);
                                notifyDataSetChanged();
                            }
                        });

                    }
                });
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.helpTitle)
        TextView helpTitle;
        @InjectView(R.id.helpDescription)
        TextView helpDescription;
        @InjectView(R.id.responeToHelpBtn)
        ButtonRectangle responeToHelpBtn;
        @InjectView(R.id.checkUserAccountButton)
        ButtonRectangle checkUserAccountButton;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
