package com.hmc.onegoodday.models.locations;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.App;
import com.hmc.onegoodday.models.inventory.Inventory;
import com.hmc.onegoodday.models.inventory.ItemType;

public abstract class ShopLocation extends OgdLocation {

	public static final int Fence = 1;
	public static final int Pharmacy = 2;
	public static final int Regular = 3;

	protected int type;

	protected int actor;

	protected final Inventory inventory;

	// prices when a player is buying items
	protected final Map<ItemType, Integer> buyPrices;

	// prices when a player is selling items
	protected final Map<ItemType, Integer> sellPrices;

	protected ShopLocation() {
		inventory = new Inventory();
		buyPrices = new HashMap<ItemType, Integer>();
		sellPrices = new HashMap<ItemType, Integer>();
	}

	public int getType() {
		return type;
	}

	public int getActor() {
		return actor;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void addToInventory(ItemType itemType, int quantity) {
		inventory.add(itemType, quantity, Integer.MAX_VALUE);
	}

	public void addToInventory(ItemType itemType, int quantity, int maxQuantity) {
		inventory.add(itemType, quantity, maxQuantity);
	}

	public Map<ItemType, Integer> getBuyPrices() {
		return buyPrices;
	}

	public Map<ItemType, Integer> getSellPrices() {
		return sellPrices;
	}

	@Override
	public void onVisited(Marker marker) {
		App.UIListener.showShopPopup(this, marker);
	}
}
