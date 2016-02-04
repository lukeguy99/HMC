package com.hmc.onegoodday.models.quests;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.models.inventory.ItemType;
import com.hmc.onegoodday.models.locations.QuestLocation;

public abstract class Quest {

	private final QuestLocation location;

	protected final Map<ItemType, Integer> rewards;

	protected final List<QuestEndingCondition> endingConditions;

	protected int image;

	protected int name;

	protected int description;

	protected int doneDescription;

	private boolean isStarted;

	private boolean isDone;

	private Date doneOn;

	protected Quest(QuestLocation location) {
		this.location = location;
		rewards = new HashMap<ItemType, Integer>();
		endingConditions = new ArrayList<QuestEndingCondition>();
	}

	public QuestLocation getLocation() {
		return location;
	}

	public int getImage() {
		return image;
	}

	public int getName() {
		return name;
	}

	public int getDescription() {
		return description;
	}

	public int getDoneDescription() {
		return doneDescription;
	}

	public Integer getReward(ItemType itemType) {
		return rewards.get(itemType);
	}

	public void setReward(ItemType itemType, Integer amount) {
		if (null == amount) {
			rewards.remove(itemType);
		}
		else {
			rewards.put(itemType, amount);
		}
	}

	public List<QuestEndingCondition> getEndingConditions() {
		return endingConditions;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted() {
		isStarted = true;
	}

	public boolean canEnd() {
		for (QuestEndingCondition condition : endingConditions) {
			if (!App.PlayerState.getInventory().contains(condition.itemType, condition.quantity)) {
				return false;
			}
		}

		return true;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone() {
		isStarted = false;
		isDone = true;
		doneOn = new Date();
	}

	public Date getDoneOn() {
		return doneOn;
	}
}
