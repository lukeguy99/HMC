package com.hmc.onegoodday.states;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.content.Context;
import android.os.Handler;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.Configuration;
import com.hmc.onegoodday.listeners.TickListener;

public final class GameState {

	public final Context context;

	private boolean isInitialized;

	private final Handler tickHandler;

	private final List<TickListener> tickListeners;

	private boolean isTimeRunning;

	private int tickCount;

	public GameState(Context context) {
		if (isInitialized) {
			throw new IllegalStateException("GameState is already initialized");
		}
		isInitialized = true;

		this.context = context;

		tickHandler = new Handler();
		tickListeners = new CopyOnWriteArrayList<TickListener>();
	}

	public int getTickCount() {
		return tickCount;
	}

	public void startTime() {
		App.LocationState.connect();
		tickHandler.postDelayed(updateTickCount, Configuration.TickInterval);
		isTimeRunning = true;
	}

	public void pauseTime() {
		App.LocationState.disconnect();
		tickHandler.removeCallbacks(updateTickCount);
		isTimeRunning = false;
	}

	private Runnable updateTickCount = new Runnable() {

		@Override
		public void run() {
			tickCount++;

			for (TickListener listener : tickListeners) {
				listener.onTick(tickCount);
			}

			if (isTimeRunning) {
				tickHandler.postDelayed(this, Configuration.TickInterval);
			}
		}
	};

	public void addTickListener(TickListener listener) {
		tickListeners.add(listener);
	}

	public void removeTickListener(TickListener listener) {
		tickListeners.remove(listener);
	}
}