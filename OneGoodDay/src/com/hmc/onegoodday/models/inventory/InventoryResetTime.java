package com.hmc.onegoodday.models.inventory;


public class InventoryResetTime {

	public final ItemType itemType;

	public final int resetTickCount;

	public InventoryResetTime(ItemType itemType, int resetTickCount) {
		this.itemType = itemType;
		this.resetTickCount = resetTickCount;
	}
}
