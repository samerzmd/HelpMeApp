package com.apps.kawaii.helpme.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.apps.kawaii.helpme.Activities.HelpDetailsActivity;
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
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o=new Intent(mContext, HelpDetailsActivity.class);
                o.putExtra(HelpDetailsActivity.KEY_HELP,currentHelp);
                mContext.startActivity(o);
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.helpTitle)
        TextView helpTitle;
        @InjectView(R.id.helpDescription)
        TextView helpDescription;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
