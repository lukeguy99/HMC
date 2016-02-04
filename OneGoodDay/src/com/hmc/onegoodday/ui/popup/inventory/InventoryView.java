package com.hmc.onegoodday.ui.popup.inventory;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.hmc.onegoodday.models.inventory.InventoryItem;

public class InventoryView extends InventoryViewLayout {

	private final List<InventoryItemView> inventoryItemViews;

	public InventoryView(View view) {
		super(view);

		inventoryItemViews = new ArrayList<InventoryItemView>();
	}

	public void setInventory(List<InventoryItem> inventory) {
		adjustNumberOfItems(inventory.size());

		for (int i = 0; i < inventory.size(); i++) {
			InventoryItemView view = inventoryItemViews.get(i);
			view.setItem(inventory.get(i));
		}
	}

	private void adjustNumberOfItems(int numberOfItems) {

		setLayoutParams(numberOfItems);

		if (inventoryItemViews.size() < numberOfItems) {
			while ((inventoryItemViews.size() != numberOfItems)) {
				InventoryItemView inventoryItemView = new InventoryItemView(view.getContext());
				inventoryItemViews.add(inventoryItemView);
				addView(inventoryItemView.getView(), inventoryItemViews.size() == 1);
			}
		} else if (inventoryItemViews.size() > numberOfItems) {
			while ((inventoryItemViews.size() != numberOfItems)) {
				InventoryItemView removedView = inventoryItemViews.remove(inventoryItemViews.size() - 1);
				removeView(removedView.getView());
			}
		}
	}
}
