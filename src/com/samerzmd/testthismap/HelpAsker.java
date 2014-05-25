package com.samerzmd.testthismap;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import serverReqeusts.Help;
import serverReqeusts.Result;
import serverReqeusts.ServerTransaction;
import serverReqeusts.UserProfile;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class HelpAsker extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_asker);
			final UserProfile userProfile=getIntent().getExtras().getParcelable("userProfile");
			final EditText hint = (EditText) findViewById(R.id.helpType);
			TextView locTextView = (TextView) findViewById(R.id.locText);
			locTextView.setText("long "
					+ getIntent().getExtras().getString("myLocLong") + " lat "
					+ getIntent().getExtras().getString("myLocLat"));
			EditText desC = (EditText) findViewById(R.id.desC);
			Button submit = (Button) findViewById(R.id.done);
			submit.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ServerTransaction transaction = new ServerTransaction();
					transaction.submitHelp(getApplicationContext(), hint
							.getText().toString(),
							((EditText) findViewById(R.id.desC)).getText()
									.toString(),userProfile.id , Double
									.parseDouble(getIntent().getExtras()
											.getString("myLocLong")), Double
									.parseDouble(getIntent().getExtras()
											.getString("myLocLat")),
							new AjaxCallback<Result>() {
								@Override
								public void callback(String url, Result object,
										AjaxStatus status) {
									Toast.makeText(getApplicationContext(), object.code.toString()+" "+status.getCode(), Toast.LENGTH_SHORT).show();
									if (object.code.equals(Result.OK)
											&& status.getCode() == 200) {
										Intent o = new Intent(HelpAsker.this,
												MapMain.class);
										o.putExtra("myLocLong", getIntent()
												.getStringExtra("myLocLong"));
										o.putExtra("myLocLat", getIntent()
												.getStringExtra("myLocLat"));
										o.putExtra("HintMessage", hint
												.getText().toString());
										setResult(Activity.RESULT_OK, o);
										finish();
									}
								}
							});

				}
			});
		}
	}
