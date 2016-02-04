package com.hmc.onegoodday.ui.popup.shop;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.hmc.onegoodday.models.inventory.InventoryItem;
import com.hmc.onegoodday.models.inventory.ItemType;

public class ShopItemView extends ShopItemViewLayout implements OnClickListener {

	private final ShopView parentView;

	private ItemType itemType;

	private int selectedQuantity;

	private int availableQuantity;

	private int price;

	public ShopItemView(Context context, ShopView parentView) {
		super(context);
		this.parentView = parentView;
		setOnClickListeners(this);
	}

	public void setItem(InventoryItem item, int price) {
		this.itemType = item.itemType;
		this.selectedQuantity = 0;
		this.availableQuantity = item.quantity;
		this.price = price;

		setName(itemType.name);
		setImage(itemType.image);
		setQuantity(0);
		setAvailableQuantity(availableQuantity);
		setPrice(price);
	}

	public ItemType getItemType() {
		return itemType;
	}

	public int getSelectedQuantity() {
		return selectedQuantity;
	}

	@Override
	public void onClick(View view) {
		if (view.equals(addButton)) {
			selectedQuantity++;
			if (selectedQuantity > availableQuantity) {
				selectedQuantity = availableQuantity;
			}
		} else if (view.equals(removeButton)) {
			selectedQuantity--;
			if (selectedQuantity < 0) {
				selectedQuantity = 0;
			}
		}

		setQuantity(selectedQuantity);
		parentView.updateTotal();
	}

	public int getTotal() {
		return selectedQuantity * price;
	}
}
