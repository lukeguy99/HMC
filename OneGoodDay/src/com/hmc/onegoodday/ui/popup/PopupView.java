package com.hmc.onegoodday.ui.popup;

public enum PopupView {
	SpeechBubble(0),
	Quest(1),
	QuickAction(2),
	ShopSell(3),
	ShopBuy(3),
	Inventory(4),
	QuestLog(5);

	public final int viewId;

	private PopupView(int viewId) {
		this.viewId = viewId;
	}
}
