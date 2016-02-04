package com.hmc.onegoodday.listeners;

public interface PlayerStatusUpdateListener {

	void onPlayerStatusUpdated(int age, int happiness, int money);

	void onEndGame();

}
