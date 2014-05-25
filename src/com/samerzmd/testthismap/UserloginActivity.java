package com.samerzmd.testthismap;

import java.util.regex.Pattern;

import serverReqeusts.ServerTransaction;
import serverReqeusts.UserProfile;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class UserloginActivity extends Activity {
	ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userlogin);
		progress = new ProgressDialog(UserloginActivity.this);

		Button loginButton = (Button) findViewById(R.id.loginButton);
		final TextView Email=(TextView) findViewById(R.id.email);
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(UserloginActivity.this).getAccounts();
		String possibleEmail;
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        possibleEmail = account.name;
		        Email.setText(possibleEmail);
		    }
		   }
		
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				progress.setTitle("fetching");
				progress.show();
				ServerTransaction serverTransaction = new ServerTransaction();
				serverTransaction.getProfile(getApplicationContext(),
						Email.getText().toString(),Email.getText().toString(), new AjaxCallback<UserProfile>() {
							@Override
							public void callback(String url,
									UserProfile object, AjaxStatus status) {
								if (status.getCode() == 200 && object!=null) {
									Intent o = new Intent(
											UserloginActivity.this,
											MapMain.class);
									o.putExtra("userInfo", object);
									startActivity(o);
									progress.dismiss();
								} else {
									progress.dismiss();
									Toast.makeText(getApplicationContext(),
											"ERR FETCHING", Toast.LENGTH_SHORT)
											.show();
								}
							}
						});
			}
		});
	}
}
