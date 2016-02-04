package com.hmc.onegoodday.ui.popup;

import java.util.List;
import java.util.Map;

import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.hmc.onegoodday.R;
import com.hmc.onegoodday.listeners.QuestItemClickListener;
import com.hmc.onegoodday.models.inventory.InventoryItem;
import com.hmc.onegoodday.models.inventory.ItemType;
import com.hmc.onegoodday.models.quests.Quest;
import com.hmc.onegoodday.ui.popup.inventory.InventoryView;
import com.hmc.onegoodday.ui.popup.questLog.QuestLogView;
import com.hmc.onegoodday.ui.popup.shop.ShopView;

public abstract class PopupLayout extends DialogFragment implements OnKeyListener {

	private View view;

	private ViewAnimator viewAnimator;

	private TextView title;

	private Button leftButton;

	private Button rightButton;

	private TextView locationDescription;

	private ImageView actor;

	private TextView questDescription;

	private TextView quickActionDescription;

	private ImageView quickActionImage;

	private ShopView shopView;

	private InventoryView inventoryView;

	private QuestLogView questLogView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		super.onCreateView(inflater, container, savedInstanceState);

		setCancelable(false);
		getDialog().setOnKeyListener(this);

		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		view = inflater.inflate(R.layout.popup, container);
		viewAnimator = (ViewAnimator) view.findViewById(R.id.popupViewAnimator);
		title = (TextView) view.findViewById(R.id.title);

		leftButton = (Button) view.findViewById(R.id.leftButton);
		leftButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onLeftButtonClick();
			}
		});

		rightButton = (Button) view.findViewById(R.id.rightButton);
		rightButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onRightButtonClick();
			}
		});

		locationDescription = (TextView) view.findViewById(R.id.locationDescription);
		actor = (ImageView) view.findViewById(R.id.actorImage);

		questDescription = (TextView) view.findViewById(R.id.questDescription);

		quickActionDescription = (TextView) view.findViewById(R.id.quickActionDescription);
		quickActionImage = (ImageView) view.findViewById(R.id.quickActionLargeImage);

		shopView = new ShopView(view);

		inventoryView = new InventoryView(view);

		questLogView = new QuestLogView(view);

		return view;
	}

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if (event.getAction() != KeyEvent.ACTION_UP) {
			return false;
		}

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBackPressed();
			return true;
		}

		return false;
	}

	protected abstract void onBackPressed();

	protected abstract void onLeftButtonClick();

	protected abstract void onRightButtonClick();

	protected void navigateToView(PopupView view) {
		viewAnimator.setDisplayedChild(view.viewId);
	}

	public void setTitle(int resid) {
		title.setText(resid);
	}

	public void setLeftButtonCaption(int resid) {
		leftButton.setText(resid);
	}

	public void showLeftButton() {
		leftButton.setVisibility(View.VISIBLE);
	}

	public void hideLeftButton() {
		leftButton.setVisibility(View.GONE);
	}

	public void setRightButtonCaption(int resid) {
		rightButton.setText(resid);
	}

	public void showRightButton() {
		rightButton.setVisibility(View.VISIBLE);
	}

	public void hideRightButton() {
		rightButton.setVisibility(View.GONE);
	}

	public void setLocationDescription(int resid) {
		locationDescription.setText(resid);
	}

	public void setActor(int resid) {
		actor.setImageResource(resid);
	}

	public void setQuestDescription(int resid) {
		questDescription.setText(resid);
	}

	public void setQuestDescription(String text) {
		questDescription.setText(Html.fromHtml(text));
	}

	public void setQuickActionDescription(int resid) {
		if (0 == resid) {
			quickActionDescription.setText(null);

		} else {
			quickActionDescription.setText(resid);
		}
	}

	public void setQuickActionImage(int resid) {
		quickActionImage.setImageResource(resid);
	}

	public void setInventory(List<InventoryItem> inventory, Map<ItemType, Integer> prices) {
		shopView.setInventory(inventory, prices);
	}

	public int getTotal() {
		return shopView.getTotal();
	}

	public List<InventoryItem> getItems() {
		return shopView.getItems();
	}

	public void setInventory(List<InventoryItem> inventory) {
		inventoryView.setInventory(inventory);
	}

	public void setQuests(List<Quest> quests, QuestItemClickListener listener) {
		questLogView.setQuests(quests, listener);
	}
}
