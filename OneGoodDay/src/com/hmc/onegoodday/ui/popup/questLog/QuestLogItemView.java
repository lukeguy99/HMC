package com.hmc.onegoodday.ui.popup.questLog;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.hmc.onegoodday.R;
import com.hmc.onegoodday.listeners.QuestItemClickListener;
import com.hmc.onegoodday.models.infoWindows.InfoWindow;
import com.hmc.onegoodday.models.inventory.ItemType;
import com.hmc.onegoodday.models.quests.Quest;

public abstract class QuestLogItemView {

	protected final TableRow container;

	protected final View view;

	protected Quest quest;

	protected final ImageView image;

	protected final TextView name;

	protected final ImageView rewardImage;

	protected final TextView reward;

	protected QuestLogItemView(View view, final QuestItemClickListener listener) {
		this.view = view;
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, R.dimen.popup_table_row_height));
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if (!quest.isDone()) {
					listener.onQuestItemClick(quest);
				}
			}
		});

		container = (TableRow) view.findViewById(R.id.container);
		image = (ImageView) view.findViewById(R.id.image);
		name = (TextView) view.findViewById(R.id.name);
		rewardImage = (ImageView) view.findViewById(R.id.rewardImage);
		reward = (TextView) view.findViewById(R.id.reward);
	}

	public View getView() {
		return view;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;

		if (quest.isDone()) {
			container.setBackgroundResource(R.drawable.item_quest_done_background);
		}
		else {
			container.setBackgroundResource(R.drawable.item_background);
		}

		image.setImageResource(quest.getImage());
		name.setText(quest.getName());

		Integer questRewardHappiness = quest.getReward(ItemType.Happiness);
		Integer questRewardMoney = quest.getReward(ItemType.Money);
		if (null == questRewardHappiness && null == questRewardMoney) {
			rewardImage.setVisibility(View.INVISIBLE);
			reward.setText(R.string.reward_unknown);
		}
		else {
			rewardImage.setVisibility(View.VISIBLE);
			if (null != questRewardHappiness) {
				rewardImage.setImageResource(R.drawable.item_happiness);
				reward.setText(String.format(InfoWindow.getHappinessRewardFormat(view.getContext()),
						questRewardHappiness));
			}
			else if (null != questRewardMoney) {
				rewardImage.setImageResource(R.drawable.item_money);
				reward.setText(String.format(InfoWindow.getMoneyRewardFormat(view.getContext()), questRewardMoney));
			}
		}
	}
}
