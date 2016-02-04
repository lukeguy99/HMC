package com.hmc.onegoodday.ui.popup.inventory;

import java.util.ArrayList;
import java.util.List;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.inventory.InventoryItem;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

public class InventoryHandler extends PopupHandler {

	public InventoryHandler(Popup popup) {
		super(popup);
	}

	@Override
	public void onShow() {
		popup.setTitle(R.string.popup_inventory_title);
		popup.setLeftButtonCaption(R.string.ok_button_text);
		popup.hideRightButton();

		List<InventoryItem> inventory = getVisibleItems();

		if (inventory.size() > 0) {
			popup.setInventory(inventory);
			setCurrentView(PopupView.Inventory);
		}
		else {
			popup.setQuestDescription(R.string.no_items_in_inventory);
			setCurrentView(PopupView.Quest);
		}
	}

	@Override
	public void onLeftButtonClick() {
		popup.dismiss();
	}

	@Override
	public void onRightButtonClick() {
		throw new IllegalStateException("Can't click on right button in inventory popup");
	}

	private List<InventoryItem> getVisibleItems() {
		List<InventoryItem> newInventory = new ArrayList<InventoryItem>();

		for (InventoryItem item : App.PlayerState.getInventory().getItems()) {
			if (!item.itemType.isHidden || item.quantity == 0) {
				newInventory.add(item);
			}
		}

		return newInventory;
	}
}
