package com.hmc.onegoodday.models.quickActions;

import com.hmc.onegoodday.App;

public abstract class QuickAction {

	protected int image;

	protected int name;

	protected int description;

	protected int moneyCost;

	protected int doneImage;

	private boolean isDone;

	public int getImage() {
		return image;
	}

	public int getName() {
		return name;
	}

	public int getDescription() {
		return description;
	}

	public int getDoneImage() {
		return doneImage;
	}

	public boolean isDone() {
		return isDone;
	}

	public boolean tryEnd() {
		return App.PlayerState.getMoney() >= moneyCost;
	};

	public void setDone() {
		isDone = true;
	}
}
