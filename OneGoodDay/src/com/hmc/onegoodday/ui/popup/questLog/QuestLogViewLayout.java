package com.hmc.onegoodday.ui.popup.questLog;

import android.view.View;

import com.hmc.onegoodday.R;
import com.hmc.onegoodday.ui.popup.ScrollableViewLayout;

public abstract class QuestLogViewLayout extends ScrollableViewLayout {

	protected QuestLogViewLayout(View view) {
		super(view);

		scrollViewId = R.id.questLogQuestsScroller;
		containerId = R.id.questLogQuestsContainer;
		maxNumberOfItems = 3;
		itemMaxHeightDimenId = R.dimen.popup_quest_log_quests_max_height;

		initialize();
	}
}
