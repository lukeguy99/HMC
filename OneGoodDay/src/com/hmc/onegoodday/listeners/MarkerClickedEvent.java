package com.hmc.onegoodday.listeners;

import com.google.android.gms.maps.model.Marker;

public class MarkerClickedEvent {

	private final Marker marker;

	private boolean isHandled;

	private boolean result;

	public MarkerClickedEvent(Marker marker) {
		this.marker = marker;
	}

	public Marker getMarker() {
		return marker;
	}

	public boolean isHandled() {
		return isHandled;
	}

	public void setHandled(boolean isHandled) {
		this.isHandled = isHandled;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
}
