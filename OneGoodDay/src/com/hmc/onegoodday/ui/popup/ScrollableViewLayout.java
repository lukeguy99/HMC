package com.hmc.onegoodday.ui.popup;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;

import com.hmc.onegoodday.R;

public class ScrollableViewLayout {

	protected final View view;

	protected ScrollView scrollView;

	protected int scrollViewId;

	protected LinearLayout container;

	protected int containerId;

	protected int maxNumberOfItems;

	protected int itemMaxHeightDimenId;

	public ScrollableViewLayout(View view) {
		this.view = view;
	}

	protected void initialize() {
		scrollView = (ScrollView) view.findViewById(scrollViewId);
		container = (LinearLayout) view.findViewById(containerId);
	}

	protected void setLayoutParams(int numberOfItems) {
		LayoutParams layoutParams = scrollView.getLayoutParams();
		layoutParams.height = numberOfItems > maxNumberOfItems ? (int) view.getResources()
				.getDimension(itemMaxHeightDimenId)
				: LayoutParams.WRAP_CONTENT;
	}

	protected void addView(View view, boolean isFirstItem) {
		TableLayout.LayoutParams params = new TableLayout.LayoutParams(view.getLayoutParams());
		int margin = (int) view.getResources().getDimension(R.dimen.popup_item_spacing);
		if (isFirstItem) {
			params.setMargins(0, margin, 0, margin);
		} else {
			params.setMargins(0, 0, 0, margin);
		}
		container.addView(view, params);
	}

	protected void removeView(View view) {
		container.removeView(view);
	}
}
