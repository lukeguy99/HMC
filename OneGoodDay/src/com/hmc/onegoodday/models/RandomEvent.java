package com.hmc.onegoodday.models;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.OgdRandom;
import com.hmc.onegoodday.listeners.TickListener;

public abstract class RandomEvent implements TickListener {

	public static final int Inflation = 1;
	public static final int Police = 2;
	public static final int Thugs = 3;

	// prevents multiple events happening at the same time
	private static boolean eventTriggered;

	protected int chance;

	protected int actor;

	protected int description;

	protected int image;

	protected int title;

	protected int interval = 1; // ticks

	public void activate() {
		App.GameState.addTickListener(this);
	}

	public void deactivate() {
		App.GameState.removeTickListener(this);
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	@Override
	public void onTick(int count) {
		if (eventTriggered) {
			return;
		}

		if (count % interval == 0) {
			if (OgdRandom.instance().toss(chance)) {
				eventTriggered = true;
				onEventTriggered();
				deactivate();
			}
		}
	}

	protected void onEventTriggered() {
		App.UIListener.showRandomEventPopup(this);
	}

	public void onEventCompleted() {
		eventTriggered = false;
	}

	public int getActor() {
		return actor;
	}

	public int getDescription() {
		return description;
	}

	public int getImage() {
		return image;
	}

	public int getTitle() {
		return title;
	}
}
