package com.hmc.nidademo;

import android.location.Location;
import android.os.SystemClock;

import com.google.android.gms.maps.model.LatLng;

public class NidaLocation extends Location {

	private boolean isVisited;

	private long resetTime;

	public NidaLocation(LatLng position) {
		super(Configuration.CustomLocationProviderName);
		setLatitude(position.latitude);
		setLongitude(position.longitude);
	}

	public boolean isVisited() {
		return isVisited;
	}

	public void reset() {
		isVisited = false;
	}

	public long getResetTime() {
		return resetTime;
	}

	public LocationType onVisited() {
		isVisited = true;

		resetTime = SystemClock.uptimeMillis() + Configuration.LocationResetInterval;

		int eventType = GameState.Random.nextInt(Integer.MAX_VALUE) % Configuration.LocationTypes.size();
		LocationType locationType = Configuration.LocationTypes.get(eventType);

		GameState.modifyCollectedOil(locationType.collectedOilChange);
		GameState.modifyTotalOil(locationType.totalOilChange);
		GameState.modifyGameTime(locationType.timeChange);

		return locationType;
	}
}
