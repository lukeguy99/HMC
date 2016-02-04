package com.hmc.onegoodday.models.locations;

import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.App;
import com.hmc.onegoodday.Configuration;
import com.hmc.onegoodday.OgdRandom;
import com.hmc.onegoodday.listeners.MarkerClickedEvent;
import com.hmc.onegoodday.models.inventory.ItemType;

public abstract class QuestItemLocation extends OgdLocation {

	protected ItemType itemType;

	protected int numberOfVisibleItems;

	public ItemType getItemType() {
		return itemType;
	}

	@Override
	public List<LatLng> getPositions() {
		return numberOfVisibleItems == 0 ? super.getPositions() : OgdRandom.instance().getSubset(positions,
				numberOfVisibleItems);
	}

	@Override
	public void onMarkerClicked(MarkerClickedEvent event) {
		if (Configuration.DEBUG) {
			if (containsMarker(event.getMarker())) {
				event.setHandled(true);
				event.setResult(true);
				onVisited(event.getMarker());
				return;
			}
		}

		event.setHandled(containsMarker(event.getMarker()));
	}

	@Override
	public void onVisited(Marker marker) {
		App.PlayerState.addToInventory(itemType, 1);
		removeMarker(marker);

		App.UIListener.showToast(description);
	}
}
