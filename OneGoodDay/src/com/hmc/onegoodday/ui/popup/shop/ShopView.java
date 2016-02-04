package com.hmc.onegoodday.ui.popup.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.view.View;

import com.hmc.onegoodday.models.inventory.InventoryItem;
import com.hmc.onegoodday.models.inventory.ItemType;

public class ShopView extends ShopViewLayout {

	private final List<ShopItemView> shopItemViews;

	private int total;

	public ShopView(View view) {
		super(view);

		shopItemViews = new ArrayList<ShopItemView>();
	}

	public void setInventory(List<InventoryItem> inventory, Map<ItemType, Integer> prices) {
		adjustNumberOfItems(inventory.size());

		for (int i = 0; i < inventory.size(); i++) {
			ShopItemView view = shopItemViews.get(i);
			InventoryItem item = inventory.get(i);
			view.setItem(item, prices.get(item.itemType));
		}

		updateTotal();
	}

	public List<InventoryItem> getItems() {
		List<InventoryItem> items = new ArrayList<InventoryItem>();

		for (ShopItemView view : shopItemViews) {
			int selectedQuantity = view.getSelectedQuantity();
			if (selectedQuantity > 0) {
				items.add(new InventoryItem(view.getItemType(), selectedQuantity));
			}
		}

		return items;
	}

	private void adjustNumberOfItems(int numberOfItems) {

		setLayoutParams(numberOfItems);

		if (shopItemViews.size() < numberOfItems) {
			while ((shopItemViews.size() != numberOfItems)) {
				ShopItemView shopItemView = new ShopItemView(view.getContext(), this);
				shopItemViews.add(shopItemView);
				addView(shopItemView.getView(), shopItemViews.size() == 1);
			}
		} else if (shopItemViews.size() > numberOfItems) {
			while ((shopItemViews.size() != numberOfItems)) {
				ShopItemView removedView = shopItemViews.remove(shopItemViews.size() - 1);
				removeView(removedView.getView());
			}
		}
	}

	public void updateTotal() {
		int total = 0;
		for (ShopItemView view : shopItemViews) {
			total += view.getTotal();
		}
		setTotal(total);

		this.total = total;
	}

	public int getTotal() {
		return total;
	}
}
