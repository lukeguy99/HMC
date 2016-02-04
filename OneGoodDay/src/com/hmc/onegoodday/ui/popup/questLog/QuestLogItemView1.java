package com.hmc.onegoodday.ui.popup.questLog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.listeners.QuestItemClickListener;
import com.hmc.onegoodday.models.quests.Quest;
import com.hmc.onegoodday.models.quests.QuestEndingCondition;

public class QuestLogItemView1 extends QuestLogItemView {

	protected final TableRow endingConditions;

	// TODO: extract ending conditions as a separate layout / widget
	protected final ImageView endingConditionsImage;

	protected final TextView endingConditionsActualQuantity;

	protected final TextView endingConditionsRequiredQuantity;

	public QuestLogItemView1(Context context, QuestItemClickListener listener) {
		super(View.inflate(context, R.layout.popup_quest_log_item1_template, null), listener);

		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, R.dimen.popup_table_row_height));
		endingConditions = (TableRow) view.findViewById(R.id.endingConditions);
		endingConditionsImage = (ImageView) view.findViewById(R.id.endingConditionsImage);
		endingConditionsActualQuantity = (TextView) view.findViewById(R.id.endingConditionsActualQuantity);
		endingConditionsRequiredQuantity = (TextView) view.findViewById(R.id.endingConditionsRequiredQuantity);
	}

	@Override
	public void setQuest(Quest quest) {
		super.setQuest(quest);

		if (quest.getEndingConditions().size() == 0) {
			// police station
			endingConditions.setVisibility(View.INVISIBLE);
		}
		else {
			QuestEndingCondition questEndingCondition = quest.getEndingConditions().get(0);
			endingConditionsImage.setImageResource(questEndingCondition.itemType.image);
			int actualCount = App.PlayerState.getInventory().getQuantity(questEndingCondition.itemType);
			endingConditionsActualQuantity.setText("" + actualCount);
			endingConditionsRequiredQuantity.setText("" + questEndingCondition.quantity);
		}
	}
}
