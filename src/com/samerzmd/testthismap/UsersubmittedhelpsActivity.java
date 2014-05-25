package com.samerzmd.testthismap;

import java.util.ArrayList;

import com.androidquery.callback.AjaxCallback;

import serverReqeusts.Help;
import serverReqeusts.ServerTransaction;
import serverReqeusts.UserProfile;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.Build;

public class UsersubmittedhelpsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usersubmittedhelps);
		final UserProfile userProfile = getIntent().getExtras().getParcelable(
				"userProfile");
		ServerTransaction s = new ServerTransaction();

		s.getAllHelps(getApplicationContext(), new AjaxCallback<Help[]>() {
			public void callback(String url, Help[] object,
					com.androidquery.callback.AjaxStatus status) {
				ArrayList<Help> helps = new ArrayList<Help>();
				for (Help help : object) {

					if (help.whoRequestsId == userProfile.id) {
						helps.add(help);
					}
				}
				ListView listView = (ListView) findViewById(R.id.HelpsList);
				listView.setAdapter(new ListHelpsAdapter(
						UsersubmittedhelpsActivity.this, helps));

			};
		});

	}
}
