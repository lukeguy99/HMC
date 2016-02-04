package com.hmc.onegoodday.ui.popup.shop;

import android.view.View;
import android.widget.TextView;

import com.hmc.onegoodday.R;
import com.hmc.onegoodday.ui.popup.ScrollableViewLayout;

public abstract class ShopViewLayout extends ScrollableViewLayout {

	protected final TextView itemsTotal;

	protected final String itemsTotalFormat;

	public ShopViewLayout(View view) {
		super(view);

		scrollViewId = R.id.shopItemsScroller;
		containerId = R.id.shopItemsContainer;
		maxNumberOfItems = 4;
		itemMaxHeightDimenId = R.dimen.popup_shop_items_max_height;

		initialize();

		itemsTotal = (TextView) view.findViewById(R.id.shopItemsTotal);
		itemsTotalFormat = view.getResources().getString(R.string.item_total_format);
	}

	protected void setTotal(int amount) {
		itemsTotal.setText(String.format(itemsTotalFormat, amount));
	}
}
