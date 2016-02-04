package com.hmc.onegoodday.ui.popup.questLog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.listeners.QuestItemClickListener;
import com.hmc.onegoodday.models.quests.Quest;
import com.hmc.onegoodday.models.quests.QuestEndingCondition;

public class QuestLogItemView2 extends QuestLogItemView {

	protected final ImageView endingConditions1Image;

	protected final TextView endingConditions1ActualQuantity;

	protected final TextView endingConditions1RequiredQuantity;

	protected final ImageView endingConditions2Image;

	protected final TextView endingConditions2ActualQuantity;

	protected final TextView endingConditions2RequiredQuantity;

	public QuestLogItemView2(Context context, QuestItemClickListener listener) {
		super(View.inflate(context, R.layout.popup_quest_log_item2_template, null), listener);

		endingConditions1Image = (ImageView) view.findViewById(R.id.endingConditions1Image);
		endingConditions1ActualQuantity = (TextView) view.findViewById(R.id.endingConditions1ActualQuantity);
		endingConditions1RequiredQuantity = (TextView) view.findViewById(R.id.endingConditions1RequiredQuantity);
		endingConditions2Image = (ImageView) view.findViewById(R.id.endingConditions2Image);
		endingConditions2ActualQuantity = (TextView) view.findViewById(R.id.endingConditions2ActualQuantity);
		endingConditions2RequiredQuantity = (TextView) view.findViewById(R.id.endingConditions2RequiredQuantity);
	}

	@Override
	public void setQuest(Quest quest) {
		super.setQuest(quest);

		QuestEndingCondition questEndingCondition = quest.getEndingConditions().get(0);
		endingConditions1Image.setImageResource(questEndingCondition.itemType.image);
		int actualCount = App.PlayerState.getInventory().getQuantity(questEndingCondition.itemType);
		endingConditions1ActualQuantity.setText("" + actualCount);
		endingConditions1RequiredQuantity.setText("" + questEndingCondition.quantity);
		questEndingCondition = quest.getEndingConditions().get(1);
		endingConditions2Image.setImageResource(questEndingCondition.itemType.image);
		actualCount = App.PlayerState.getInventory().getQuantity(questEndingCondition.itemType);
		endingConditions2ActualQuantity.setText("" + actualCount);
		endingConditions2RequiredQuantity.setText("" + questEndingCondition.quantity);
	}
}
