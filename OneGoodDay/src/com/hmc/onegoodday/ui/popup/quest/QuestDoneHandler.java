package com.hmc.onegoodday.ui.popup.quest;

import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.locations.QuestLocation;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

public class QuestDoneHandler extends PopupHandler {

	private final QuestLocation location;

	public QuestDoneHandler(Popup popup, QuestLocation location) {
		super(popup);
		this.location = location;
	}

	@Override
	public void onShow() {
		popup.setActor(location.getActor());
		popup.setTitle(location.getName());
		popup.setLocationDescription(location.getQuest().getDoneDescription());
		popup.setLeftButtonCaption(R.string.ok_button_text);
		popup.hideRightButton();
		setCurrentView(PopupView.SpeechBubble);
	}

	@Override
	public void onLeftButtonClick() {
		popup.dismiss();
	}

	@Override
	public void onRightButtonClick() {
		throw new IllegalStateException("Can't click on right button in quest done popup");
	}
}
