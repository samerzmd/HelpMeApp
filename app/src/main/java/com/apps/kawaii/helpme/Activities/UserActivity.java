package com.apps.kawaii.helpme.Activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.apps.kawaii.helpme.Fragments.UserProfileFragment;
import com.apps.kawaii.helpme.R;

public class UserActivity extends ActionBarActivity {

    public static final String KEY_USER_ID="USER_ID" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int UserId= getIntent().getExtras().getInt(KEY_USER_ID);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, UserProfileFragment.newInstance(String.valueOf(UserId))).commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
