package com.hmc.onegoodday.models.quests;

import com.hmc.onegoodday.models.inventory.ItemType;

public class QuestEndingCondition {

	public final ItemType itemType;

	public final int quantity;

	public QuestEndingCondition(ItemType itemType, int quantity) {
		this.itemType = itemType;
		this.quantity = quantity;
	}
}
