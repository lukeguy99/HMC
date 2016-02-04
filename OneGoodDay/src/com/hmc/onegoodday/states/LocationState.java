package com.hmc.onegoodday.states;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.Configuration;
import com.hmc.onegoodday.listeners.MarkerClickedEvent;
import com.hmc.onegoodday.models.locations.OgdLocation;

public final class LocationState implements ConnectionCallbacks, InfoWindowAdapter, LocationListener,
		OnConnectionFailedListener,
		OnInfoWindowClickListener, OnMarkerClickListener {

	private boolean isInitialized;

	private final Context context;

	private final LocationClient locationClient;

	private final List<LocationListener> locationListeners;

	private Location playerLocation;

	private float travelDistance;

	public LocationState(Context context) {
		if (isInitialized) {
			throw new IllegalStateException("LocationState is already initialized");
		}
		isInitialized = true;

		this.context = context;

		locationListeners = new ArrayList<LocationListener>();

		locationClient = new LocationClient(context, this, this);
		locationClient.connect();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		locationClient.requestLocationUpdates(Configuration.LocationUpdateRequest, this);
	}

	public void connect() {
		if (!locationClient.isConnecting() && !locationClient.isConnected()) {
			locationClient.connect();
		}
	}

	public void disconnect() {
		locationClient.disconnect();
	}

	@Override
	public void onDisconnected() {
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (result.hasResolution()) {
			// TODO: implement onConnectionFailed handling code
			// try {
			// result.startResolutionForResult(this, 1);
			// } catch (SendIntentException e) {
			// e.printStackTrace();
			// }
		}
		else {
			// TODO: showErrorDialog(connectionResult.getErrorCode());
		}
	}

	@Override
	public void onLocationChanged(Location newLocation) {
		if (null != playerLocation) {
			travelDistance += playerLocation.distanceTo(newLocation);
		}

		playerLocation = newLocation;

		for (LocationListener listener : locationListeners) {
			listener.onLocationChanged(newLocation);
		}
	}

	public Location getPlayerLocation() {
		return playerLocation;
	}

	public float getTravelDistance() {
		return travelDistance;
	}

	public void addLocationUpdateListener(LocationListener listener) {
		locationListeners.add(listener);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		MarkerClickedEvent event = new MarkerClickedEvent(marker);

		for (OgdLocation location : Configuration.AllLocations) {
			location.onMarkerClicked(event);
			if (event.isHandled()) {
				return event.getResult();
			}
		}

		return true;
	}

	@Override
	public View getInfoContents(Marker marker) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		for (OgdLocation location : Configuration.AllLocations) {
			if (location.containsMarker(marker)) {
				return location.getInfoWindow(context);
			}
		}

		throw new IllegalStateException("No info window for marker.");
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		marker.hideInfoWindow();
	}
}
