package com.hmc.onegoodday.models.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Inventory {

	protected final Map<ItemType, Integer> items;

	public Inventory() {
		items = new HashMap<ItemType, Integer>();
	}

	public Inventory(List<InventoryItem> initialInventory) {
		this();
		for (InventoryItem item : initialInventory) {
			items.put(item.itemType, item.quantity);
		}
	}

	public void add(ItemType itemType, int quantity) {
		add(itemType, quantity, Integer.MAX_VALUE);
	}

	public void add(ItemType itemType, int quantity, int maxQuantity) {
		Integer existingQuantity = items.get(itemType);

		if (null == existingQuantity) {
			existingQuantity = 0;
		}

		existingQuantity += quantity;

		if (existingQuantity > maxQuantity) {
			existingQuantity = maxQuantity;
		}

		if (existingQuantity < 0) {
			throw new IllegalStateException("Cannot set item " + itemType + " to " + existingQuantity);
		}

		if (existingQuantity == 0) {
			items.remove(itemType);
		}
		else {
			items.put(itemType, existingQuantity);
		}
	}

	public boolean contains(ItemType itemType, int quantity) {
		Integer existingQuantity = items.get(itemType);
		return existingQuantity != null && existingQuantity >= quantity;
	}

	public void remove(ItemType itemType, int quantity) {
		Integer existingQuantity = items.get(itemType);
		existingQuantity -= quantity;
		items.put(itemType, existingQuantity);
	}

	public List<InventoryItem> getItems() {
		List<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
		for (Entry<ItemType, Integer> item : items.entrySet()) {
			inventoryItems.add(new InventoryItem(item.getKey(), item.getValue()));
		}
		return inventoryItems;
	}

	public int size() {
		return items.size();
	}

	public int getQuantity(ItemType itemType) {
		Integer quantity = items.get(itemType);
		return null == quantity ? 0 : quantity;
	}
}
