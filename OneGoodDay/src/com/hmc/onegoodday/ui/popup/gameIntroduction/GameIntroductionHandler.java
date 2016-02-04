package com.hmc.onegoodday.ui.popup.gameIntroduction;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.Gender;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

public class GameIntroductionHandler extends PopupHandler {

	private int page;

	public GameIntroductionHandler(Popup popup) {
		super(popup);
	}

	@Override
	public void onShow() {
		popup.setTitle(R.string.app_name);
		popup.hideRightButton();
		popup.setLocationDescription(R.string.game_introduction_text1);
		popup.setLeftButtonCaption(R.string.next_button_text);

		int actor;
		if (App.PlayerState.getGender().equals(Gender.Male)) {
			actor = R.drawable.player_male;
		}
		else {
			actor = R.drawable.player_female;
		}
		popup.setActor(actor);

		page = 0;

		setCurrentView(PopupView.SpeechBubble);
	}

	@Override
	public void onLeftButtonClick() {
		page++;

		switch (page) {
		case 1:
			popup.setLocationDescription(R.string.game_introduction_text2);
			break;
		case 2:
			popup.setLocationDescription(R.string.game_introduction_text3);
			break;
		case 3:
			popup.setLocationDescription(R.string.game_introduction_text4);
			break;
		case 4:
			popup.setLocationDescription(R.string.game_introduction_text5);
			popup.setLeftButtonCaption(R.string.ok_button_text);
			break;
		case 5:
			if (null != onDismissedListener) {
				onDismissedListener.onClick(null);
			}
			popup.dismiss();
			break;
		}
	}

	@Override
	public void onRightButtonClick() {
		throw new IllegalStateException("Can't click on right button in new game popup");
	}
}
