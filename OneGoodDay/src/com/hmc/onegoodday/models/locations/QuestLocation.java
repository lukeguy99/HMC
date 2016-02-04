package com.hmc.onegoodday.models.locations;

import android.content.Context;
import android.location.Location;
import android.view.View;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.listeners.InventoryUpdateListener;
import com.hmc.onegoodday.listeners.PlayerStatusUpdateListener;
import com.hmc.onegoodday.models.infoWindows.InfoWindow;
import com.hmc.onegoodday.models.inventory.ItemType;
import com.hmc.onegoodday.models.quests.Quest;
import com.hmc.onegoodday.models.quickActions.QuickAction;

public abstract class QuestLocation extends OgdLocation implements InventoryUpdateListener, PlayerStatusUpdateListener {

	protected int placemarkerVisited;

	protected int actor;

	protected QuickAction quickAction;

	protected Quest quest;

	protected InfoWindow infoWindow;

	public int getActor() {
		return actor;
	}

	public QuickAction getQuickAction() {
		return quickAction;
	}

	public void setQuickActionDone(Marker marker) {
		if (!quickAction.tryEnd()) {
			App.UIListener.showToast(R.string.not_enough_money);
			return;
		}

		if (quest.isStarted()) {
			// mark all markers as visited
			for (Marker m : markers.keySet()) {
				m.setIcon(BitmapDescriptorFactory.fromResource(placemarkerVisited));
				markers.put(m, true);
			}
		}
		else if (markers.size() > 1) {
			marker.setIcon(BitmapDescriptorFactory.fromResource(placemarkerVisited));
			markers.put(marker, true);
		}

		onQuickActionDone();
	}

	protected void onQuickActionDone() {
		quickAction.setDone();
	};

	public Quest getQuest() {
		return quest;
	}

	protected void onQuestStarted() {
		App.PlayerState.addQuest(quest);
	}

	public void setQuestStarted(Marker marker) {
		if (quickAction.isDone()) {
			// mark all markers as visited
			for (Marker m : markers.keySet()) {
				m.setIcon(BitmapDescriptorFactory.fromResource(placemarkerVisited));
				markers.put(m, true);
			}
		}
		else if (markers.size() > 1) {
			marker.setIcon(BitmapDescriptorFactory.fromResource(placemarkerVisited));
			markers.put(marker, true);
		}

		quest.setStarted();
		onQuestStarted();
	}

	public void tryEndQuest() {
		if (canQuestEnd()) {
			setQuestDone();
		}
	}

	public boolean canQuestEnd() {
		return quest.canEnd();
	}

	public void setQuestDone() {
		quest.setDone();
		onQuestDone();
	}

	protected void onQuestDone() {
		App.PlayerState.modifyHappiness(quest.getReward(ItemType.Happiness));
		App.PlayerState.modifyMoney(quest.getReward(ItemType.Money));
		App.UIListener.showQuestDonePopup(this);
	}

	@Override
	public void onVisited(Marker marker) {
		if (!markers.get(marker)) {
			App.UIListener.showQuestPopup(this, marker);
		}
	}

	@Override
	public void onLocationChanged(Location playerLocation) {
		super.onLocationChanged(playerLocation);

		if (quest.isStarted()) {
			tryEndQuest();
		}
	}

	@Override
	public void onInventoryUpdated() {
		if (quest.isStarted()) {
			tryEndQuest();
		}
	}

	@Override
	public void onPlayerStatusUpdated(int age, int happiness, int money) {
		if (quest.isStarted()) {
			tryEndQuest();
		}
	}

	@Override
	public View getInfoWindow(Context context) {
		if (null == infoWindow) {
			infoWindow = new InfoWindow(context);
		}

		return infoWindow.getInfoWindow(name, quest.getReward(ItemType.Happiness), quest.getReward(ItemType.Money));
	}

	@Override
	public void onEndGame() {
	}
}
