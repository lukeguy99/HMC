package com.hmc.onegoodday.models.infoWindows;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hmc.onegoodday.R;

public class InfoWindow extends SimpleInfoWindow {

	private final TextView happinessView;

	private final TextView moneyView;

	private final String happinessFormat;

	private final String moneyFormat;

	public InfoWindow(Context context) {
		super(context, R.layout.info_window);

		happinessView = (TextView) infoWindow.findViewById(R.id.happinessReward);
		moneyView = (TextView) infoWindow.findViewById(R.id.moneyReward);

		happinessFormat = getHappinessRewardFormat(context);
		moneyFormat = getMoneyRewardFormat(context);
	}

	// TODO: show info window explaining why marker is disabled

	// TODO: show info window for quest items (fame, certificate) that cannot be picked up
	public View getInfoWindow(int name, Integer happinessReward, Integer moneyReward) {
		nameView.setText(name);
		adjustReward(happinessView, happinessReward, happinessFormat, R.string.happiness_reward_zero,
				R.drawable.item_happiness, R.drawable.item_happiness_dim);
		adjustReward(moneyView, moneyReward, moneyFormat, R.string.money_reward_zero,
				R.drawable.item_money, R.drawable.item_money_dim);

		return infoWindow;
	}

	private void adjustReward(TextView view, Integer reward, String format, int noRewardText, int drawable,
			int drawableNoReward) {
		if (reward == null) {
			view.setText(R.string.reward_unknown);
			view.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
		}
		else if (reward > 0) {
			view.setText(String.format(format, reward));
			view.setCompoundDrawablesWithIntrinsicBounds(drawable, 0, 0, 0);
		}
		else {
			view.setText(noRewardText);
			view.setCompoundDrawablesWithIntrinsicBounds(drawableNoReward, 0, 0, 0);
		}
	}

	public static String getHappinessRewardFormat(Context context) {
		return context.getResources().getString(R.string.happiness_reward_format);
	}

	public static String getMoneyRewardFormat(Context context) {
		return context.getResources().getString(R.string.money_reward_format);
	}
}
