package com.samerzmd.testthismap;

import java.util.ArrayList;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import serverReqeusts.Help;
import serverReqeusts.Result;
import serverReqeusts.ServerTransaction;
import serverReqeusts.UserProfile;
import android.R.integer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
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

public class AnswerhelpActivity extends Activity {
	UserProfile userProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answerhelp);
		userProfile = (UserProfile) getIntent().getParcelableExtra("Profile");
		final ServerTransaction s = new ServerTransaction();
		final TextView title = (TextView) findViewById(R.id.Title);
		final TextView desc = (TextView) findViewById(R.id.descra);
		s.getSpecificHelp(getApplicationContext(), Integer.parseInt((this
				.getIntent().getExtras().getString("helpId"))),
				new AjaxCallback<Help>() {
					@Override
					public void callback(String url, Help object,
							AjaxStatus status) {
						title.setText(object.title);
						desc.setText(object.details);
					}
				});
		title.setText(getIntent().getExtras().getString("helpId"));

		Button button = (Button) findViewById(R.id.answerHelp);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				s.answerForHelp(
						getApplicationContext(),
						userProfile.id,
						Integer.parseInt(getIntent().getExtras().getString(
								"helpId")), new AjaxCallback<Result>() {

							@Override
							public void callback(String url, Result object,
									AjaxStatus status) {
								Toast.makeText(getApplicationContext(),
										String.valueOf(status.getCode()),
										Toast.LENGTH_SHORT).show();
								if (status.getCode() == 200
										&& object.code.equals(Result.OK)) {
									Toast.makeText(getApplicationContext(),
											"your request is waiting approval",
											Toast.LENGTH_SHORT).show();
									finish();
								}
							}
						});
			}
		});
	}
}