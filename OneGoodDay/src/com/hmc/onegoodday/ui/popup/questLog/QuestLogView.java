package com.hmc.onegoodday.ui.popup.questLog;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.hmc.onegoodday.listeners.QuestItemClickListener;
import com.hmc.onegoodday.models.quests.Quest;

public class QuestLogView extends QuestLogViewLayout {

	private final List<QuestLogItemView> questLogItemViews;

	public QuestLogView(View view) {
		super(view);

		questLogItemViews = new ArrayList<QuestLogItemView>();
	}

	public void setQuests(List<Quest> quests, QuestItemClickListener listener) {
		int numberOfItems = quests.size();

		removeAllViews();

		for (int i = 0; i < numberOfItems; i++) {
			Quest quest = quests.get(i);
			QuestLogItemView questLogItemView = createView(quest, listener);
			questLogItemViews.add(questLogItemView);
			addView(questLogItemView.getView(), i == 0);
			questLogItemView.setQuest(quest);
		}

		setLayoutParams(numberOfItems);
	}

	private void removeAllViews() {
		while ((questLogItemViews.size() != 0)) {
			QuestLogItemView removedView = questLogItemViews.remove(0);
			removeView(removedView.getView());
		}
	}

	private QuestLogItemView createView(Quest quest, QuestItemClickListener listener) {
		int count = quest.getEndingConditions().size();

		switch (count) {
		case 0:
		case 1:
			return new QuestLogItemView1(view.getContext(), listener);
		case 2:
			return new QuestLogItemView2(view.getContext(), listener);
		case 3:
			return new QuestLogItemView3(view.getContext(), listener);
		default:
			throw new InvalidParameterException("No quest log view for ending conditions of size: " + count);
		}
	}
}
