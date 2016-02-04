package com.hmc.onegoodday.ui.popup.shop;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.inventory.Inventory;
import com.hmc.onegoodday.models.inventory.InventoryItem;
import com.hmc.onegoodday.models.locations.ShopLocation;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

public class ShopHandler extends PopupHandler {

	private final ShopLocation location;

	public ShopHandler(Popup popup, ShopLocation location, Marker marker) {
		super(popup);
		this.location = location;
	}

	@Override
	public void onShow() {
		popup.setActor(location.getActor());
		popup.setLocationDescription(location.getDescription());

		setCurrentView(switchToLocationOverviewView());
	}

	@Override
	public void onLeftButtonClick() {
		switch (getCurrentView()) {
		case SpeechBubble:
			setCurrentView(switchToSellView());
			break;
		case Quest:
		case ShopBuy:
		case ShopSell:
			setCurrentView(switchToLocationOverviewView());
			break;
		default:
			throw new InvalidParameterException("Invalid value " + getCurrentView()
					+ " for left button click in shop handler");
		}
	}

	@Override
	public void onRightButtonClick() {
		switch (getCurrentView()) {
		case SpeechBubble:
			setCurrentView(switchToBuyView());
			break;
		case ShopBuy:
			if (confirmBuy()) {
				popup.dismiss();
			}
			break;
		case ShopSell:
			if (confirmSell()) {
				popup.dismiss();
			}
			break;
		default:
			throw new InvalidParameterException("Invalid value " + getCurrentView()
					+ " for right button click in shop handler");
		}
	}

	private void setCommonElements(int title, int leftButtonCaption, int rightButtonCaption) {
		popup.setTitle(title);
		popup.setLeftButtonCaption(leftButtonCaption);
		popup.setRightButtonCaption(rightButtonCaption);
	}

	private PopupView switchToLocationOverviewView() {
		setCommonElements(location.getName(), R.string.sell_button_text, R.string.buy_button_text);
		if (location.getType() == ShopLocation.Pharmacy) {
			popup.hideLeftButton();
		} else {
			popup.showLeftButton();
		}
		popup.showRightButton();

		return PopupView.SpeechBubble;
	}

	private PopupView switchToBuyView() {
		List<InventoryItem> inventory = location.getInventory().getItems();

		popup.setLeftButtonCaption(R.string.decline_button_text);
		popup.showLeftButton();

		if (inventory.size() > 0) {
			popup.setInventory(inventory, location.getBuyPrices());
			popup.showRightButton();
			popup.setRightButtonCaption(R.string.buy_button_text);
			return PopupView.ShopBuy;
		}

		popup.setQuestDescription(R.string.no_items_to_buy);
		popup.hideRightButton();
		return PopupView.Quest;
	}

	private PopupView switchToSellView() {
		List<InventoryItem> inventory = getSellInventory(App.PlayerState.getInventory());

		popup.setLeftButtonCaption(R.string.decline_button_text);
		popup.showLeftButton();

		if (inventory.size() > 0) {
			popup.setInventory(inventory, location.getSellPrices());
			popup.showRightButton();
			popup.setRightButtonCaption(R.string.sell_button_text);
			return PopupView.ShopSell;
		}

		popup.setQuestDescription(R.string.no_items_to_sell);
		popup.hideRightButton();
		return PopupView.Quest;
	}

	private List<InventoryItem> getSellInventory(Inventory inventory) {
		List<InventoryItem> sellInventory = new ArrayList<InventoryItem>();

		for (InventoryItem item : inventory.getItems()) {
			if (item.itemType.isHidden || null == location.getSellPrices().get(item.itemType)) {
				continue;
			}

			sellInventory.add(item);
		}

		return sellInventory;
	}

	private boolean confirmBuy() {
		int total = popup.getTotal();

		if (total > App.PlayerState.getMoney()) {
			App.UIListener.showToast(R.string.not_enough_money);
			return false;
		}

		for (InventoryItem item : popup.getItems()) {
			App.PlayerState.addToInventory(item.itemType, item.quantity);
			location.addToInventory(item.itemType, -item.quantity);
		}

		App.PlayerState.modifyMoney(-total);

		return true;
	}

	private boolean confirmSell() {

		for (InventoryItem item : popup.getItems()) {
			App.PlayerState.addToInventory(item.itemType, -item.quantity);
			location.addToInventory(item.itemType, item.quantity);
		}

		App.PlayerState.modifyMoney(popup.getTotal());

		return true;
	}
}
