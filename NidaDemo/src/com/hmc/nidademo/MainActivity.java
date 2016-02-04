package com.hmc.nidademo;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements ConnectionCallbacks,
		OnConnectionFailedListener, LocationListener, IStatusUpdatable, OnMarkerClickListener {

	private final HashMap<NidaLocation, Marker> locationMarkerMap = new HashMap<NidaLocation, Marker>();
	private final HashMap<Marker, NidaLocation> markerLocationMap = new HashMap<Marker, NidaLocation>();

	private GoogleMap map;

	private UiSettings mapUiSettings;

	private LocationClient locationClient;

	private View popup;

	private TextView locationDescription;

	private ImageView locationGlyph;

	private TextView totalOil;

	private TextView collectedOil;

	private TextView gameTime;

	private TextView nearestDistance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		popup = findViewById(R.id.popupLayout);
		locationDescription = (TextView) findViewById(R.id.locationDescription);
		locationGlyph = (ImageView) findViewById(R.id.locationGlyph);
		totalOil = (TextView) findViewById(R.id.totalOil);
		collectedOil = (TextView) findViewById(R.id.collectedOil);
		gameTime = (TextView) findViewById(R.id.time);
		nearestDistance = (TextView) findViewById(R.id.nearest_distance);

		GameState.setStatusUpdateListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (map == null) {
			map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			if (map != null) {
				LatLngBounds bounds = setupMarkers();
				panToAll(bounds);
				map.setMyLocationEnabled(true);
				map.setOnMarkerClickListener(this);
				mapUiSettings = map.getUiSettings();
				mapUiSettings.setZoomControlsEnabled(false);
				mapUiSettings.setMyLocationButtonEnabled(false);
			}
		}

		if (locationClient == null) {
			locationClient = new LocationClient(this, this, this);
		}
		locationClient.connect();

		if (!GameState.isGameInProgress())
		{
			showPopup();
		}
	}

	private void panToAll(final LatLngBounds bounds) {
		// Pan to see all markers in view.
		final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
		mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressLint("NewApi")
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));

				// int zoomLevel = calculateZoomLevel();
				// map.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel));
			}
		});
	}

	private int calculateZoomLevel() {
		final double equatorLength = 40075004; // in meters
		int screenWidth = findViewById(R.id.rootLayout).getWidth();
		final double widthInPixels = screenWidth;
		double metersPerPixel = equatorLength / 256;
		int zoomLevel = 1;
		while ((metersPerPixel * widthInPixels) > 2000) {
			metersPerPixel /= 2;
			++zoomLevel;
		}
		return zoomLevel;
	}

	private LatLngBounds setupMarkers() {
		Builder builder = LatLngBounds.builder();

		for (NidaLocation location : GameState.getLocations()) {
			LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
			Marker marker = map.addMarker(new MarkerOptions().position(position));
			builder.include(position);
			locationMarkerMap.put(location, marker);
			markerLocationMap.put(marker, location);
		}

		return builder.build();
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (locationClient != null) {
			locationClient.disconnect();
		}
	}

	@Override
	public void onBackPressed() {
		if (popup.getVisibility() == View.VISIBLE) {
			hidePopup();
			return;
		}

		new AlertDialog.Builder(this).setTitle(R.string.quit_prompt).setMessage(R.string.quit_message)
				.setCancelable(false).setPositiveButton(R.string.yes, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						MainActivity.super.onBackPressed();
						GameState.endGame();
						finish();
					}
				}).setNegativeButton(R.string.no, null).show();

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO: start game play now?
		locationClient.requestLocationUpdates(Configuration.LocationUpdateRequest, this);
	}

	@Override
	public void onDisconnected() {
		// TODO: stop game play now?
		// Do nothing
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			try {
				result.startResolutionForResult(this, 1);
			} catch (SendIntentException e) {
				e.printStackTrace();
			}
		}
		else {
			// TODO: showErrorDialog(connectionResult.getErrorCode());
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		GameState.updatePlayerLocation(location);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		NidaLocation location = markerLocationMap.get(marker);
		if (!location.isVisited()) {
			onLocationReached(location);
		}
		return true;
	}

	public void closePopup(View view) {
		hidePopup();
	}

	@Override
	public void updateTime(long v) {
		long time = v / 1000;

		long hours = time / 3600;
		long minutes = (time - hours * 3600) / 60;
		long seconds = time - hours * 3600 - minutes * 60;

		String text = String.format(getResources().getString(R.string.time_format), hours, minutes, seconds);
		gameTime.setText(text);
	}

	@Override
	public void updateTotalOil(int value) {
		String text = String.format(getResources().getString(R.string.total_oil_format), value);
		totalOil.setText(text);
	}

	@Override
	public void updateCollectedOil(int value) {
		String text = String.format(getResources().getString(R.string.collected_oil_format), value);
		collectedOil.setText(text);
	}

	@Override
	public void updateNearestDistance(int value) {
		String text;
		if (value > 1000) {
			text = String.format(getResources().getString(R.string.nearest_distance_max_format), 1000);
		}
		else {
			text = String.format(getResources().getString(R.string.nearest_distance_format), value);
		}
		nearestDistance.setText(text);
	}

	@Override
	public void resetLocation(NidaLocation location) {
		Marker marker = locationMarkerMap.get(location);
		marker.setIcon(BitmapDescriptorFactory.defaultMarker());
	}

	@Override
	public void onLocationReached(NidaLocation location) {
		LocationType locationType = location.onVisited();

		if (locationType.popupTextId != R.string.location_description_placeholder) {
			locationDescription.setText(locationType.popupTextId);
			locationGlyph.setImageResource(locationType.glyphId);
			showPopup();
		}

		Marker marker = locationMarkerMap.get(location);
		marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
	}

	@Override
	public void endGame(boolean isSuccess) {
		Intent intent = new Intent(this, EndGameActivity.class);
		intent.putExtra(EndGameActivity.IsSuccess, isSuccess);
		startActivity(intent);
		finish();
	}

	private void showPopup() {
		popup.setVisibility(View.VISIBLE);
	}

	private void hidePopup() {
		popup.setVisibility(View.GONE);
		if (!GameState.isGameInProgress()) {
			GameState.startGame();
		}
	}
}
