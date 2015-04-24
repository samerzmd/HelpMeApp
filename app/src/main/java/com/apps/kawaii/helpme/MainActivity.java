package com.apps.kawaii.helpme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Contacts;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.neokree.googlenavigationdrawer.GAccount;
import it.neokree.googlenavigationdrawer.GAccountListener;
import it.neokree.googlenavigationdrawer.GoogleNavigationDrawer;


public class MainActivity  extends GoogleNavigationDrawer implements GAccountListener {

    @Override
    public void init(Bundle savedInstanceState) {

        // add first account
        GAccount account = new GAccount("Samer Zuhair","samerzmd@gmail.com",this.getResources().getDrawable(R.drawable.photo),this.getResources().getDrawable(R.drawable.bamboo));
        
        this.addAccount(account);
        // set listener
        this.setAccountListener(this);

        // add your sections
        this.addSection(this.newSection("Map",this.getResources().getDrawable(R.drawable.ic_map_grey),new MapFragment()));
        this.addSection(this.newSection("Help Center",this.getResources().getDrawable(R.drawable.ic_help_center),new BlankFragment()));
        this.addDivisor();
        this.addSection(this.newSection("Notifications",this.getResources().getDrawable(R.drawable.ic_notifications),new BlankFragment()).setNotifications(10));

        Intent i = new Intent(this,Contacts.Settings.class);
        this.addSection(this.newSection("Settings",this.getResources().getDrawable(R.drawable.ic_settings_black_24dp),i));

    }

    @Override
    public void onAccountOpening(GAccount gAccount) {
        getFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new BlankFragment()).commit();
    }
}