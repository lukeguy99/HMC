package com.hmc.onegoodday.ui.popup.inventory;

import android.view.View;

import com.hmc.onegoodday.R;
import com.hmc.onegoodday.ui.popup.ScrollableViewLayout;

public abstract class InventoryViewLayout extends ScrollableViewLayout {

	public InventoryViewLayout(View view) {
		super(view);

		scrollViewId = R.id.inventoryItemsScroller;
		containerId = R.id.inventoryItemsContainer;
		maxNumberOfItems = 4;
		itemMaxHeightDimenId = R.dimen.popup_inventory_items_max_height;

		initialize();
	}
}
