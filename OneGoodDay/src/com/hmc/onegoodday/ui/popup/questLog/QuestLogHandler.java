package com.hmc.onegoodday.ui.popup.questLog;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.listeners.QuestItemClickListener;
import com.hmc.onegoodday.models.quests.Quest;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

public class QuestLogHandler extends PopupHandler implements QuestItemClickListener {

	private Quest selectedQuest;

	public QuestLogHandler(Popup popup) {
		super(popup);
	}

	@Override
	public void onShow() {
		setCurrentView(switchToQuestLogView());
	}

	@Override
	public void onLeftButtonClick() {
		switch (getCurrentView()) {
		case Quest:
			if (null == selectedQuest) {
				popup.dismiss();
				return;
			}

			if (selectedQuest.getLocation().canQuestEnd()) {
				popup.dismiss();
				selectedQuest.getLocation().setQuestDone();
			}
			else {
				setCurrentView(switchToQuestLogView());
			}
			break;
		case QuestLog:
			popup.dismiss();
			break;
		default:
			throw new InvalidParameterException("Invalid value " + getCurrentView()
					+ " for left button click in quest log handler");
		}
	}

	@Override
	public void onRightButtonClick() {
		throw new IllegalStateException("Can't click on right button in quest end popup");
	}

	@Override
	public void onQuestItemClick(Quest quest) {
		setCurrentView(switchToQuestDetailsView(quest));
	}

	private PopupView switchToQuestLogView() {
		this.selectedQuest = null;

		popup.setTitle(R.string.popup_quest_log_title);
		popup.setLeftButtonCaption(R.string.ok_button_text);
		popup.hideRightButton();

		List<Quest> quests = getQuests();

		if (quests.size() > 0) {
			popup.setQuests(quests, this);
			return PopupView.QuestLog;
		}
		else {
			popup.setQuestDescription(R.string.no_quests_in_log);
			return PopupView.Quest;
		}
	}

	private PopupView switchToQuestDetailsView(Quest quest) {
		this.selectedQuest = quest;

		popup.setTitle(quest.getName());
		popup.setQuestDescription(quest.getDescription());
		popup.hideRightButton();

		if (quest.getLocation().canQuestEnd()) {
			popup.setLeftButtonCaption(R.string.end_quest_text);
		}
		else {
			popup.setLeftButtonCaption(R.string.ok_button_text);
		}

		return PopupView.Quest;
	}

	private List<Quest> getQuests() {
		List<Quest> finishedQuests = new ArrayList<Quest>();
		List<Quest> unfinishedQuests = new ArrayList<Quest>();

		for (Quest quest : App.PlayerState.getQuests()) {
			if (quest.isDone()) {
				finishedQuests.add(quest);
			}
			else {
				unfinishedQuests.add(quest);
			}
		}

		unfinishedQuests.addAll(finishedQuests);
		return unfinishedQuests;
	}
}
