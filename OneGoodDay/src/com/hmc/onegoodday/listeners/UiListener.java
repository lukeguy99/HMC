package com.hmc.onegoodday.listeners;

import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.models.RandomEvent;
import com.hmc.onegoodday.models.inventory.ItemType;
import com.hmc.onegoodday.models.locations.QuestLocation;
import com.hmc.onegoodday.models.locations.ShopLocation;

public interface UiListener {

	void showAboutPopup();

	void showInventoryPopup();

	void showQuestItems(ItemType itemType);

	void showQuestLogPopup();

	void showQuestPopup(QuestLocation location, Marker marker);

	void showQuestDonePopup(QuestLocation location);

	void showQuickActionDonePopup(QuestLocation location);

	void showRandomEventPopup(RandomEvent event);

	void showShopPopup(ShopLocation location, Marker marker);

	void showToast(int message);
}
