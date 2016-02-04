package com.hmc.nidademo;

import java.util.Arrays;
import java.util.List;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;

public final class Configuration {

	public static final String CustomLocationProviderName = "NIDA_DEMO";

	public static final int TickInterval = 1000;

	public static final int LocationResetInterval = 20 * 60 * 1000;

	public static final int GameDuration = 2 * 60 * 60 * 1000;

	public static final int InitialCollectedOil = 0;

	public static final int InitialOilPerLocation = 10;

	public static final int LocationTriggerThreshold = 15;

	public static final LocationRequest LocationUpdateRequest = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	public static final List<LocationType> LocationTypes = Arrays.asList(
			new LocationType(-10, 10, 0, R.string.location_description_collected_oil,
					R.drawable.collected_oil),
			new LocationType(20, 10, 0, R.string.location_description_wind_change, R.drawable.wind_change),
			new LocationType(0, 10, -10 * 60 * 1000, R.string.location_description_helping_seals,
					R.drawable.helping_seals),
			new LocationType(0, 10, -20 * 60 * 1000, R.string.location_description_new_sponge, R.drawable.new_sponge));

	public static final List<LatLng> Locations = Arrays.asList(
			new LatLng(55.299129, 20.985130),
			new LatLng(55.303135, 20.974833),
			new LatLng(55.287132, 20.960840),
			new LatLng(55.293732, 20.966892),
			new LatLng(55.302910, 21.008263),
			new LatLng(55.301834, 21.007330),
			new LatLng(55.300735, 21.005190),
			new LatLng(55.301617, 21.010965),
			new LatLng(55.301498, 20.996075),
			new LatLng(55.308781, 20.979595));
}
