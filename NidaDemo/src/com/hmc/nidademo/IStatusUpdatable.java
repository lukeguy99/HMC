package com.hmc.nidademo;

public interface IStatusUpdatable {

	void endGame(boolean isSuccess);

	void resetLocation(NidaLocation location);

	void onLocationReached(NidaLocation location);

	void updateTime(long time);

	void updateTotalOil(int value);

	void updateCollectedOil(int value);

	void updateNearestDistance(int value);
}
