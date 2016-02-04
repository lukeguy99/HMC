package com.hmc.onegoodday.ui.popup.inventory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.inventory.InventoryItem;

public class InventoryItemView {

	protected final View view;

	protected final ImageView image;

	protected final TextView name;

	protected final TextView quantity;

	public InventoryItemView(Context context) {
		view = View.inflate(context, R.layout.popup_inventory_item_template, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, R.dimen.popup_table_row_height));
		image = (ImageView) view.findViewById(R.id.itemImage);
		name = (TextView) view.findViewById(R.id.itemName);
		quantity = (TextView) view.findViewById(R.id.itemQuantity);
	}

	public View getView() {
		return view;
	}

	public void setItem(InventoryItem item) {
		image.setImageResource(item.itemType.image);
		name.setText(item.itemType.name);
		this.quantity.setText(String.valueOf(item.quantity));
	}
}
