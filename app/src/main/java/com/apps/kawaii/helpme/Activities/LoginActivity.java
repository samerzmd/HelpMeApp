package com.apps.kawaii.helpme.Activities;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.apps.kawaii.helpme.InternalSystemClasses.HelpApplication;
import com.apps.kawaii.helpme.Models.User;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.net.AjaxClient;
import com.apps.kawaii.helpme.net.AjaxFactory;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;

import java.net.URLEncoder;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends ActionBarActivity {

    @InjectView(R.id.statusBar)
    View statusBar;
    @InjectView(R.id.loggingTxv)
    TextView loggingTxv;
    @InjectView(R.id.progressBarCircularIndeterminate)
    ProgressBarCircularIndeterminate progressBarCircularIndeterminate;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();

        AjaxFactory factory = AjaxFactory.login(URLEncoder.encode(accounts[0].name));
        AjaxClient.sendRequest(this, factory, User.class, new AjaxCallback<User>() {
            @Override
            public void callback(String url, User object, AjaxStatus status) {
                if (object != null) {
                    HelpApplication.appUser = object;
                    Intent o=new Intent(LoginActivity.this,MainActivity.class);
                    LoginActivity.this.startActivity(o);
                    finish();
                } else {
                    User staticUser=new User();
                    staticUser.id=2;
                    staticUser.about="i lov helping";
                    staticUser.email="nhr.r@hotmail.com";
                    staticUser.gender="1";
                    staticUser.mobile="0795726778";
                    staticUser.avatar=null;
                    HelpApplication.appUser = staticUser;
                    Intent o=new Intent(LoginActivity.this,MainActivity.class);
                    LoginActivity.this.startActivity(o);
                    finish();
                }
            }
        });
    }

}
