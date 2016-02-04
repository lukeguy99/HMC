package com.hmc.onegoodday.ui.activities;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hmc.onegoodday.App;
import com.hmc.onegoodday.Configuration;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.listeners.UiListener;
import com.hmc.onegoodday.models.RandomEvent;
import com.hmc.onegoodday.models.inventory.ItemType;
import com.hmc.onegoodday.models.locations.OgdLocation;
import com.hmc.onegoodday.models.locations.QuestItemLocation;
import com.hmc.onegoodday.models.locations.QuestLocation;
import com.hmc.onegoodday.models.locations.ShopLocation;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.widgets.NumericTextView;

public abstract class MainActivityLayout extends FragmentActivityBase implements UiListener, LocationListener {

	protected Popup popup;

	private Toast toast;

	private TextView toastText;

	private NumericTextView age;

	private NumericTextView happiness;

	private NumericTextView money;

	private LinearLayout menuBar;

	private GoogleMap map;

	private Marker playerLocationMarker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		App.UIListener = this;

		setContentView(R.layout.activity_main);
		age = (NumericTextView) findViewById(R.id.playerAge);
		happiness = (NumericTextView) findViewById(R.id.playerHappiness);
		money = (NumericTextView) findViewById(R.id.playerMoney);
		menuBar = (LinearLayout) findViewById(R.id.menuBar);
	}

	@Override
	protected void onResume() {
		super.onResume();

		setupMap();

		if (null == popup) {
			popup = new Popup();
		}

		if (null == toast) {
			LayoutInflater inflater = getLayoutInflater();
			View toastLayout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toastLayoutRoot));
			toastText = (TextView) toastLayout.findViewById(R.id.toastText);
			toast = new Toast(getApplicationContext());
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(toastLayout);
		}
	}

	@Override
	public void onBackPressed() {
		popup.showEndGameConfirmation(getSupportFragmentManager());
		popup.setOnDismissedListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivityLayout.super.onBackPressed();
				System.exit(0);
			}
		});
	}

	protected void setupMap() {
		if (map != null) {
			return;
		}

		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		if (map == null) {
			return;
		}

		Builder builder = LatLngBounds.builder();
		showQuestLocations(builder);
		showRandomItems(builder);
		showShopLocations(builder);
		LatLngBounds bounds = builder.build();

		panToAll(bounds);

		App.LocationState.addLocationUpdateListener(this);
		map.setMyLocationEnabled(false);
		map.setOnMarkerClickListener(App.LocationState);
		map.setInfoWindowAdapter(App.LocationState);
		map.setOnInfoWindowClickListener(App.LocationState);
		UiSettings mapUiSettings = map.getUiSettings();
		mapUiSettings.setCompassEnabled(false);
		mapUiSettings.setRotateGesturesEnabled(false);
		mapUiSettings.setTiltGesturesEnabled(false);
		mapUiSettings.setMyLocationButtonEnabled(false);
		mapUiSettings.setZoomControlsEnabled(false);
	}

	private void showQuestLocations(Builder builder) {
		for (QuestLocation location : Configuration.QuestLocations) {
			App.PlayerState.addInventoryUpdateListener(location);
			App.PlayerState.addPlayerStatusUpdateListener(location);
			showLocationMarkers(builder, location);
		}
	}

	private void showRandomItems(Builder builder) {
		for (ItemType itemType : Configuration.VisibleRandomItemTypes) {
			QuestItemLocation location = Configuration.QuestItemLocations.get(itemType);
			showLocationMarkers(builder, location);
		}
	}

	private void showShopLocations(Builder builder) {
		for (ShopLocation location : Configuration.ShopLocations.values()) {
			showLocationMarkers(builder, location);
		}
	}

	private void showLocationMarkers(Builder builder, OgdLocation location) {
		App.LocationState.addLocationUpdateListener(location);

		for (LatLng latLng : location.getPositions()) {
			Marker marker = map.addMarker(new MarkerOptions().position(latLng).icon(
					BitmapDescriptorFactory.fromResource(location.getPlacemarker())));
			builder.include(latLng);
			location.addMarker(marker);
		}
	}

	private void panToAll(final LatLngBounds viewport) {
		final View mapView = getSupportFragmentManager().findFragmentById(R.id.map).getView();
		mapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressLint("NewApi")
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					mapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					mapView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				map.moveCamera(CameraUpdateFactory.newLatLngBounds(viewport, 50));
			}
		});
	}

	protected void setAge(int value) {
		age.setValue(value);
	}

	protected void setHappiness(int value) {
		happiness.setValue(value);
	}

	protected void setMoney(int value) {
		money.setValue(value);
	}

	@Override
	public void showAboutPopup() {
		popup.showAbout(getSupportFragmentManager());
	}

	@Override
	public void showInventoryPopup() {
		popup.showInventory(getSupportFragmentManager());
	}

	@Override
	public void showQuestItems(ItemType itemType) {
		QuestItemLocation location = Configuration.QuestItemLocations.get(itemType);
		for (LatLng latLng : location.getPositions()) {
			Marker marker = map.addMarker(new MarkerOptions().position(latLng).icon(
					BitmapDescriptorFactory.fromResource(location.getPlacemarker())));
			App.LocationState.addLocationUpdateListener(location);
			location.addMarker(marker);
		}
	}

	@Override
	public void showQuestLogPopup() {
		popup.showQuestLog(getSupportFragmentManager());
	}

	@Override
	public void showQuestPopup(QuestLocation location, Marker marker) {
		popup.showQuest(location, marker, getSupportFragmentManager());
	}

	@Override
	public void showQuestDonePopup(QuestLocation location) {
		popup.showQuestDone(location, getSupportFragmentManager());
	}

	@Override
	public void showQuickActionDonePopup(QuestLocation location) {
		popup.showQuickActionDone(location, getSupportFragmentManager());
	}

	@Override
	public void showRandomEventPopup(RandomEvent event) {
		popup.showRandomEvent(event, getSupportFragmentManager());
	}

	@Override
	public void showShopPopup(ShopLocation location, Marker marker) {
		popup.showShop(location, marker, getSupportFragmentManager());
	}

	@Override
	public void onLocationChanged(Location location) {
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

		if (null == playerLocationMarker) {
			showPlayerLocation(latLng);
			zoomToPlayerLocation(latLng);
		}
		else {
			playerLocationMarker.setPosition(latLng);
		}
	}

	private void showPlayerLocation(LatLng latLng) {
		BitmapDescriptor image;
		switch (App.PlayerState.getGender()) {
		case Female:
			image = BitmapDescriptorFactory.fromResource(R.drawable.player_female_small);
			break;
		case Male:
			image = BitmapDescriptorFactory.fromResource(R.drawable.player_male_small);
			break;
		default:
			throw new IllegalStateException("Illegal value " + App.PlayerState.getGender() + " for player gender");
		}

		playerLocationMarker = map.addMarker(new MarkerOptions().position(latLng).icon(image));
	}

	protected void zoomToPlayerLocation() {
		if (null == playerLocationMarker) {
			showToast(R.string.location_not_available);
			return;
		}

		Location location = App.LocationState.getPlayerLocation();
		LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
		zoomToPlayerLocation(latLng);
	}

	private void zoomToPlayerLocation(LatLng latLng) {
		map.animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng,
				Configuration.InitialZoom)));
	}

	@Override
	public void showToast(int message) {
		toastText.setText(message);
		toast.show();
	}

	protected void toggleMenu() {
		menuBar.setVisibility(menuBar.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
	}
}
