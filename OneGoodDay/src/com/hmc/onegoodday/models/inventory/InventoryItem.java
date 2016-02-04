package com.hmc.onegoodday.models.inventory;


public class InventoryItem {

	public final ItemType itemType;

	public final int quantity;

	public InventoryItem(ItemType itemType, int quantity) {
		this.itemType = itemType;
		this.quantity = quantity;
	}
}
