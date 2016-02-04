package com.hmc.onegoodday.ui.popup.quest;

import java.security.InvalidParameterException;

import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.locations.QuestLocation;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

public class QuestHandler extends PopupHandler {

	private final QuestLocation location;

	private final Marker marker;

	public QuestHandler(Popup popup, QuestLocation location, Marker marker) {
		super(popup);
		this.location = location;
		this.marker = marker;
	}

	@Override
	public void onShow() {
		popup.setActor(location.getActor());
		popup.setLocationDescription(location.getDescription());
		popup.setQuestDescription(location.getQuest().getDescription());
		popup.setQuickActionDescription(location.getQuickAction().getDescription());
		popup.setQuickActionImage(location.getQuickAction().getImage());

		setCurrentView(switchToLocationOverviewView());
	}

	@Override
	public void onLeftButtonClick() {
		switch (getCurrentView()) {
		case SpeechBubble:
			setCurrentView(switchToQuickActionView());
			break;
		case Quest:
		case QuickAction:
			setCurrentView(switchToLocationOverviewView());
			break;
		default:
			throw new InvalidParameterException("Invalid value " + getCurrentView()
					+ " for left button click in quest handler");
		}
	}

	@Override
	public void onRightButtonClick() {
		switch (getCurrentView()) {
		case SpeechBubble:
			setCurrentView(switchToQuestView());
			break;
		case Quest:
			popup.dismiss();
			location.setQuestStarted(marker);
			break;
		case QuickAction:
			popup.dismiss();
			location.setQuickActionDone(marker);
			break;
		default:
			throw new InvalidParameterException("Invalid value " + getCurrentView()
					+ " for right button click in quest handler");
		}
	}

	private void setCommonElements(int title, int leftButtonCaption, int rightButtonCaption) {
		popup.setTitle(title);
		popup.setLeftButtonCaption(leftButtonCaption);
		popup.setRightButtonCaption(rightButtonCaption);
	}

	private PopupView switchToLocationOverviewView() {
		setCommonElements(location.getName(), R.string.quick_action_button_text, R.string.misson_button_text);
		popup.showRightButton();

		return PopupView.SpeechBubble;
	}

	private PopupView switchToQuickActionView() {
		setCommonElements(location.getQuickAction().getName(), R.string.decline_button_text,
				R.string.accept_button_text);

		if (location.getQuickAction().isDone()) {
			popup.setQuickActionDescription(R.string.quick_action_done);
			popup.hideRightButton();
		}

		return PopupView.QuickAction;
	}

	private PopupView switchToQuestView() {
		setCommonElements(location.getQuest().getName(), R.string.decline_button_text, R.string.accept_button_text);

		if (location.getQuest().isStarted()) {
			popup.setQuestDescription(R.string.quest_in_progress);
			popup.hideRightButton();
		}
		else if (location.getQuest().isDone()) {
			popup.setQuestDescription(R.string.quest_done);
			popup.hideRightButton();
		}

		return PopupView.Quest;
	}
}
