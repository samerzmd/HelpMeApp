package com.apps.kawaii.helpme.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.apps.kawaii.helpme.R;

public class LoginActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setSupportActionBar(((Toolbar)findViewById(R.id.toolbar)));
        setTitle("");
    }

}
