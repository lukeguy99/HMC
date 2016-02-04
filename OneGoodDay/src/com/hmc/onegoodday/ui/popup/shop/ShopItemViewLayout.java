package com.hmc.onegoodday.ui.popup.shop;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmc.onegoodday.R;

public abstract class ShopItemViewLayout {

	protected final View view;

	protected final ImageView image;

	protected final TextView name;

	protected final TextView quantity;

	protected final TextView availableQuantity;

	protected final TextView price;

	protected final Button addButton;

	protected final Button removeButton;

	public ShopItemViewLayout(Context context) {
		view = View.inflate(context, R.layout.popup_shop_item_template, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, R.dimen.popup_table_row_height));
		image = (ImageView) view.findViewById(R.id.itemImage);
		name = (TextView) view.findViewById(R.id.itemName);
		quantity = (TextView) view.findViewById(R.id.itemQuantity);
		availableQuantity = (TextView) view.findViewById(R.id.itemMaxQuantity);
		price = (TextView) view.findViewById(R.id.itemPrice);
		addButton = (Button) view.findViewById(R.id.itemAddButton);
		removeButton = (Button) view.findViewById(R.id.itemRemoveButton);
	}

	protected void setOnClickListeners(OnClickListener listener) {
		addButton.setOnClickListener(listener);
		removeButton.setOnClickListener(listener);
	}

	public View getView() {
		return view;
	}

	protected void setImage(int resid) {
		image.setImageResource(resid);
	}

	protected void setName(int resid) {
		name.setText(resid);
	}

	protected void setQuantity(int value) {
		quantity.setText(String.valueOf(value));
	}

	protected void setAvailableQuantity(int value) {
		availableQuantity.setText(String.valueOf(value));
	}

	protected void setPrice(int value) {
		price.setText(String.valueOf(value));
	}
}
