package com.hmc.nidademo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;

import com.google.android.gms.maps.model.LatLng;

public final class GameState {

	public static final Random Random = new Random();

	private static long gameTime;

	private static long lastGameTimeUpdate;

	private static int totalOil;

	private static int collectedOil;

	private static boolean isGameInProgress;

	private static final Handler timeHandler;

	private static List<NidaLocation> locations;

	private static IStatusUpdatable statusUpdateListener;

	private static Runnable updateTime = new Runnable() {

		@Override
		public void run() {
			if (!isGameInProgress) {
				return;
			}

			long currentTime = System.currentTimeMillis();
			modifyGameTime(lastGameTimeUpdate - currentTime);
			lastGameTimeUpdate = currentTime;
			resetLocations();
			timeHandler.postDelayed(this, Configuration.TickInterval);
		}
	};

	static {
		timeHandler = new Handler();

		locations = new ArrayList<NidaLocation>();
		for (LatLng position : Configuration.Locations) {
			locations.add(new NidaLocation(position));
		}
	}

	private GameState() {
	}

	public static void setStatusUpdateListener(IStatusUpdatable listener) {
		statusUpdateListener = listener;
	}

	public static void startGame() {
		if (isGameInProgress) {
			throw new IllegalStateException();
		}

		isGameInProgress = true;

		gameTime = Configuration.GameDuration;
		lastGameTimeUpdate = System.currentTimeMillis();
		statusUpdateListener.updateTime(gameTime);

		totalOil = locations.size() * Configuration.InitialOilPerLocation;
		statusUpdateListener.updateTotalOil(totalOil);

		collectedOil = Configuration.InitialCollectedOil;
		statusUpdateListener.updateCollectedOil(collectedOil);

		timeHandler.postDelayed(updateTime, Configuration.TickInterval);
	}

	public static void endGame() {
		isGameInProgress = false;
		timeHandler.removeCallbacks(updateTime);
	}

	private static void endGame(boolean isSuccess) {
		isGameInProgress = false;
		timeHandler.removeCallbacks(updateTime);
		statusUpdateListener.endGame(isSuccess);
	}

	public static boolean isGameInProgress() {
		return isGameInProgress;
	}

	public static List<NidaLocation> getLocations() {
		return locations;
	}

	public static void modifyGameTime(long value) {
		if (!isGameInProgress) {
			return;
		}

		gameTime += value;
		statusUpdateListener.updateTime(gameTime);

		if (gameTime <= 0) {
			endGame(false);
		}
	}

	public static void modifyTotalOil(int value) {
		if (!isGameInProgress) {
			return;
		}

		totalOil += value;
		statusUpdateListener.updateTotalOil(totalOil);

		if (collectedOil >= totalOil) {
			endGame(true);
		}
	}

	public static void modifyCollectedOil(int value) {
		if (!isGameInProgress) {
			return;
		}

		collectedOil += value;
		statusUpdateListener.updateCollectedOil(collectedOil);

		if (collectedOil >= totalOil) {
			endGame(true);
		}
	}

	public static void updatePlayerLocation(Location playerLocation) {
		float minDistance = Float.MAX_VALUE;
		for (NidaLocation location : locations) {
			if (location.isVisited()) {
				continue;
			}

			float distance = Math.abs(playerLocation.distanceTo(location));
			if (distance < minDistance) {
				minDistance = distance;
			}

			if (distance < Configuration.LocationTriggerThreshold) {
				statusUpdateListener.onLocationReached(location);
			}
		}

		// in meters
		statusUpdateListener.updateNearestDistance((int) minDistance);
	}

	private static void resetLocations() {
		for (NidaLocation location : locations) {
			if (location.isVisited() && location.getResetTime() < SystemClock.uptimeMillis()) {
				location.reset();
				statusUpdateListener.resetLocation(location);
			}
		}
	}
}
