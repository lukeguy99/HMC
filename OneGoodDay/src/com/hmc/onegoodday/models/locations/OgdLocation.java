package com.hmc.onegoodday.models.locations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.App;
import com.hmc.onegoodday.Configuration;
import com.hmc.onegoodday.listeners.MarkerClickedEvent;
import com.hmc.onegoodday.listeners.TickListener;
import com.hmc.onegoodday.models.infoWindows.SimpleInfoWindow;

public abstract class OgdLocation implements LocationListener, TickListener {

	protected int name;

	protected int description;

	protected int placemarker;

	protected final List<LatLng> positions;

	protected final Map<Marker, Boolean> markers;

	protected TextView nameView;

	private SimpleInfoWindow infoWindow;

	protected OgdLocation() {
		positions = new ArrayList<LatLng>();
		markers = new HashMap<Marker, Boolean>();
	}

	public int getName() {
		return name;
	}

	public int getDescription() {
		return description;
	}

	public int getPlacemarker() {
		return placemarker;
	}

	public abstract void onVisited(Marker marker);

	@Override
	public void onLocationChanged(Location playerLocation) {
		Marker marker = getMarkerOnLocation(playerLocation);
		if (null != marker && !markers.get(marker)) {
			onVisited(marker);
		}
	}

	public List<LatLng> getPositions() {
		return positions;
	}

	public void addMarker(Marker marker) {
		markers.put(marker, false);
	}

	public boolean containsMarker(Marker marker) {
		return markers.get(marker) != null;
	}

	public Marker getMarkerOnLocation(Location location) {
		if (null == location) {
			return null;
		}

		float[] results = new float[1];
		for (Marker marker : markers.keySet()) {
			Location.distanceBetween(marker.getPosition().latitude, marker.getPosition().longitude,
					location.getLatitude(), location.getLongitude(), results);
			if (results[0] < Configuration.LocationTriggerThreshold) {
				return marker;
			}
		}

		return null;
	}

	public void removeMarkers() {
		for (Marker marker : markers.keySet()) {
			marker.remove();
		}
		markers.clear();
	}

	public void removeMarker(Marker marker) {
		marker.remove();
		markers.remove(marker);
	}

	@SuppressWarnings("unused")
	public void onMarkerClicked(MarkerClickedEvent event) {
		Marker marker = event.getMarker();

		if (containsMarker(marker)) {
			event.setHandled(true);

			if (Configuration.DEBUG || marker.equals(getMarkerOnLocation(App.LocationState.getPlayerLocation()))) {
				onVisited(marker);
				event.setResult(true);
			}
		}
	}

	public View getInfoWindow(Context context) {
		if (null == infoWindow) {
			infoWindow = new SimpleInfoWindow(context);
		}

		return infoWindow.getInfoWindow(name);
	}

	@Override
	public void onTick(int count) {
	}
}
