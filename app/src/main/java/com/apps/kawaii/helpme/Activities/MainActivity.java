package com.apps.kawaii.helpme.Activities;

import android.os.Bundle;

import com.apps.kawaii.helpme.Fragments.BlankFragment;
import com.apps.kawaii.helpme.Fragments.MapFragment;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.Fragments.UserProfileFragment;

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
        this.addSection(this.newSection("Help Center", this.getResources().getDrawable(R.drawable.ic_help_center), new BlankFragment()));
        this.addDivisor();
        this.addSection(this.newSection("Notifications",this.getResources().getDrawable(R.drawable.ic_notifications),new BlankFragment()).setNotifications(10));
        this.addSection(this.newSection("Settings",this.getResources().getDrawable(R.drawable.ic_settings_black_24dp),new BlankFragment()));

    }

    @Override
    public void onAccountOpening(GAccount gAccount) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new UserProfileFragment()).commit();
    }
}