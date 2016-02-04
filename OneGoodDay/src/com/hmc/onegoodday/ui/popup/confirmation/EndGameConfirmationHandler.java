package com.hmc.onegoodday.ui.popup.confirmation;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.Gender;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

// TODO: make confirmation handlers generic
public class EndGameConfirmationHandler extends PopupHandler {

	public EndGameConfirmationHandler(Popup popup) {
		super(popup);
	}

	@Override
	public void onShow() {
		popup.setTitle(R.string.quit_title);
		popup.setLocationDescription(R.string.quit_prompt);
		popup.setLeftButtonCaption(R.string.yes_button_text);
		popup.setRightButtonCaption(R.string.no_button_text);

		int actor;
		if (App.PlayerState.getGender().equals(Gender.Male)) {
			actor = R.drawable.player_male;
		}
		else {
			actor = R.drawable.player_female;
		}
		popup.setActor(actor);

		setCurrentView(PopupView.SpeechBubble);
	}

	@Override
	public void onLeftButtonClick() {
		popup.dismiss();
		if (null != onDismissedListener) {
			onDismissedListener.onClick(null);
		}
	}

	@Override
	public void onRightButtonClick() {
		popup.dismiss();
	}

}
