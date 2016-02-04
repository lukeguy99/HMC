package com.hmc.onegoodday.ui.popup.randomEvent;

import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.RandomEvent;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

public class RandomEventHandler extends PopupHandler {

	private final RandomEvent event;

	public RandomEventHandler(Popup popup, RandomEvent event) {
		super(popup);

		this.event = event;
	}

	@Override
	public void onShow() {
		popup.setTitle(event.getTitle());
		popup.setLeftButtonCaption(R.string.ok_button_text);
		popup.hideRightButton();

		if (event.getActor() != 0) {
			popup.setActor(event.getActor());
			popup.setLocationDescription(event.getDescription());
			setCurrentView(PopupView.SpeechBubble);
		}
		else if (event.getImage() != 0) {
			popup.setQuickActionImage(event.getImage());
			popup.setQuickActionDescription(event.getDescription());
			setCurrentView(PopupView.QuickAction);
		}
		else {
			throw new IllegalStateException("Both actor and image are 0 for random event popup");
		}
	}

	@Override
	public void onLeftButtonClick() {
		popup.dismiss();
		event.onEventCompleted();
	}

	@Override
	public void onRightButtonClick() {
		throw new IllegalStateException("Can't click on right button in random event popup");
	}
}
