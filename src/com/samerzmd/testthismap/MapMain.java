package com.samerzmd.testthismap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileCreator;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileOverlayOptionsCreator;
import com.google.android.gms.maps.model.TileProvider;

import android.R.integer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;
import android.provider.ContactsContract.Profile;
import serverReqeusts.Help;
import serverReqeusts.ServerTransaction;
import serverReqeusts.UserProfile;

public class MapMain extends Activity implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener,
		OnMyLocationButtonClickListener {
	ArrayList<helpMarker> helpMarkers;
	GoogleMap mMap;
	UserProfile userProfile;
	private LocationClient mLocationClient;
	protected int helpIcon = R.drawable.ic_launcher;
	protected int REQUEST_CODE;
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_main);
		TextView userName = (TextView) findViewById(R.id.Name);
		userProfile = (UserProfile) getIntent().getParcelableExtra(
				"userInfo");
		userName.setText(userProfile.name);
		userName.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent o= new Intent(getApplicationContext(), UsersubmittedhelpsActivity.class);
				o.putExtra("userProfile", userProfile);
				startActivity(o);
				
			}
		});
		drawAllHelps();
	}

	private void drawAllHelps() {
		try {
			mMap.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		ServerTransaction transaction = new ServerTransaction();
		transaction.getAllHelps(getApplicationContext(),
				new AjaxCallback<Help[]>() {
					
					@Override
					public void callback(String url, Help[] object,
							AjaxStatus status) {
						helpMarkers=new ArrayList<helpMarker>();
						int counter=0;
						for (Help help : object) {
							if(help.whoAnswersId==0 && help.status==0)
							{
							helpMarkers.add(new helpMarker(help));
							mMap.addMarker(
									helpMarkers.get(counter).getHelpOnMap()).setIcon(
									(BitmapDescriptorFactory
											.fromResource(helpIcon)));
							mMap.setOnMarkerClickListener(markerClickListener);
							counter++;
							}
						}

					}
				});
	}

	 OnMarkerClickListener markerClickListener = new OnMarkerClickListener() {

		@Override
		public boolean onMarkerClick(Marker marker) {
			
			Intent o = new Intent(getApplicationContext(),
					AnswerhelpActivity.class);
			o.putExtra("Profile", userProfile);
			o.putExtra("helpId", marker.getSnippet());
			o.putParcelableArrayListExtra("helps", (ArrayList<? extends Parcelable>) helpMarkers);	
			startActivity(o);
			return false;
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		setUpMapIfNeeded();
		setUpLocationClientIfNeeded();
		mLocationClient.connect();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mLocationClient != null) {
			mLocationClient.disconnect();
		}
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.myMap)).getMap();
			// Check if we were successful in obtaining the map.
			if (mMap != null) {

				mMap.setMyLocationEnabled(true);
				mMap.setOnMyLocationButtonClickListener(this);

			}
		}
	}

	private void setUpLocationClientIfNeeded() {
		if (mLocationClient == null) {
			try {
				mLocationClient = new LocationClient(getApplicationContext(),
						this, // ConnectionCallbacks
						this); // OnConnectionFailedListener
			} catch (Exception e) {

			}

		}
	}

	/**
	 * Button to get current Location. This demonstrates how to get the current
	 * Location as required without needing to register a LocationListener.
	 */
	public void showMyLocation(View view) {
		if (mLocationClient != null && mLocationClient.isConnected()) {
			String msg = "Location = " + mLocationClient.getLastLocation();
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * Implementation of {@link LocationListener}.
	 */
	@Override
	public void onLocationChanged(Location location) {
		try {
			LatLng newlatLng = new LatLng(mLocationClient.getLastLocation()
					.getLatitude(), mLocationClient.getLastLocation()
					.getLongitude());

			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newlatLng, 16));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/**
	 * Callback called when connected to GCore. Implementation of
	 * {@link ConnectionCallbacks}.
	 */
	@Override
	public void onConnected(Bundle connectionHint) {

		mLocationClient.requestLocationUpdates(REQUEST, this); // LocationListener
		try {
			mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					mLocationClient.getLastLocation().getLatitude(),
					mLocationClient.getLastLocation().getLongitude()), 16F));
		} catch (Exception e) {
			Toast.makeText(this, "it seems we are unable to locate ur pos",
					Toast.LENGTH_SHORT).show();
		}

		final Button askForHelpbutton = (Button) findViewById(R.id.AskForHelp);
		try {
			askForHelpbutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						
						Intent helper = new Intent(MapMain.this,
								HelpAsker.class);
						helper.putExtra("userProfile", userProfile);
						helper.putExtra("myLocLong", String
								.valueOf(mLocationClient.getLastLocation()
										.getLongitude()));
						helper.putExtra("myLocLat", String
								.valueOf(mLocationClient.getLastLocation()
										.getLatitude()));
						startActivityForResult(helper, REQUEST_CODE);
					} catch (Exception e) {
						Toast.makeText(
								getApplicationContext(),
								"it seems we are unable to locate your loc plz check your gps or wait a littel longer",
								Toast.LENGTH_SHORT).show();
					}

				}
			});
		} catch (Exception e) {

		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CODE) {
				/*
				 * Toast.makeText(MainActivity.this, "You picked: " +
				 * data.getStringExtra("car"), Toast.LENGTH_SHORT).show();
				 */
				drawAllHelps();
				

			}
		}
	}

	/**
	 * Callback called when disconnected from GCore. Implementation of
	 * {@link ConnectionCallbacks}.
	 */
	@Override
	public void onDisconnected() {

	}

	/**
	 * Implementation of {@link OnConnectionFailedListener}.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult result) {

	}

	@Override
	public boolean onMyLocationButtonClick() {
		Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
				.show();
		// Return false so that we don't consume the event and the default
		// behavior still occurs
		// (the camera animates to the user's current position).
		return false;
	}
}
