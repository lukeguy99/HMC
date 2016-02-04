package com.hmc.onegoodday.ui.popup.quickAction;

import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.locations.QuestLocation;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

public class QuickActionDoneHandler extends PopupHandler {

	private final QuestLocation location;

	public QuickActionDoneHandler(Popup popup, QuestLocation location) {
		super(popup);
		this.location = location;
	}

	@Override
	public void onShow() {
		popup.setTitle(location.getQuickAction().getName());
		popup.setQuickActionImage(location.getQuickAction().getDoneImage());
		// TODO: quick action done image is not centered
		popup.setQuickActionDescription(0);
		popup.setLeftButtonCaption(R.string.decline_button_text);
		popup.hideRightButton();
		setCurrentView(PopupView.QuickAction);
	}

	@Override
	public void onLeftButtonClick() {
		popup.dismiss();
	}

	@Override
	public void onRightButtonClick() {
		throw new IllegalStateException("Can't click on right button in quick action done popup");
	}
}
