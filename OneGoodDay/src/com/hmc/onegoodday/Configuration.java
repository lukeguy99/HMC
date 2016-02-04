package com.hmc.onegoodday;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.location.Location;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.models.RandomEvent;
import com.hmc.onegoodday.models.inventory.InventoryItem;
import com.hmc.onegoodday.models.inventory.InventoryResetTime;
import com.hmc.onegoodday.models.inventory.ItemType;
import com.hmc.onegoodday.models.locations.OgdLocation;
import com.hmc.onegoodday.models.locations.QuestItemLocation;
import com.hmc.onegoodday.models.locations.QuestLocation;
import com.hmc.onegoodday.models.locations.ShopLocation;
import com.hmc.onegoodday.models.quests.Quest;
import com.hmc.onegoodday.models.quests.QuestEndingCondition;
import com.hmc.onegoodday.models.quickActions.QuickAction;

public abstract class Configuration {

	// TODO: add begging locations: for every minute spent +money (5-15), up to 10 mins

	// TODO: move toast to upper right corner

	// TODO: quest items required for quest show on disabled location

	// TODO: quest finished popup - show: You have finished quest 'x' + current reward text

	public static final boolean DEBUG = false;

	public static final String CustomLocationProviderName = "ONE_GOOD_DAY";

	public static final int InitialZoom = 16;

	public static final int LocationTriggerThreshold = 20; // meters

	public static final LocationRequest LocationUpdateRequest = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(1000) // 1 second
			.setSmallestDisplacement(1) // 1 meter
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	public static final int TickInterval = (DEBUG ? 1 : 60) * 1000;

	public static final int AgingInterval = 1; // in ticks

	public static final int InitialAge = 18;

	public static final int InitialHappiness = 100;

	public static final int InitialMoney = 100;

	public static final int MaxAge = 73;

	public static final int ItemRestockInterval = 20; // ticks

	public static final List<InventoryItem> InitialPlayerInventory = new ArrayList<InventoryItem>();

	public static final List<QuestLocation> QuestLocations = new ArrayList<QuestLocation>();

	public static final Map<ItemType, QuestItemLocation> QuestItemLocations = new HashMap<ItemType, QuestItemLocation>();

	@SuppressLint("UseSparseArrays")
	public static final Map<Integer, ShopLocation> ShopLocations = new HashMap<Integer, ShopLocation>();

	public static final List<OgdLocation> AllLocations = new ArrayList<OgdLocation>();

	public static List<ItemType> VisibleRandomItemTypes;

	@SuppressLint("UseSparseArrays")
	public static final Map<Integer, RandomEvent> RandomEvents = new HashMap<Integer, RandomEvent>();

	static {
		initializePlayerInventory();

		initializeQuestLocations();
		initializeQuestItemLocations();
		initializeShopLocations();

		AllLocations.addAll(QuestLocations);
		AllLocations.addAll(QuestItemLocations.values());
		AllLocations.addAll(ShopLocations.values());

		initializeRandomEvents();

		VisibleRandomItemTypes = new ArrayList<ItemType>();
		VisibleRandomItemTypes.add(ItemType.StolenBike);
		VisibleRandomItemTypes.add(ItemType.StolenCable);
		VisibleRandomItemTypes.add(ItemType.StolenManhole);
	}

	private static void initializePlayerInventory() {
		InitialPlayerInventory.add(new InventoryItem(ItemType.Car, 1));
		InitialPlayerInventory.add(new InventoryItem(ItemType.Kidney, 1));
		InitialPlayerInventory.add(new InventoryItem(ItemType.Money, InitialMoney));
	}

	private static void initializeQuestLocations() {
		// bus station
		QuestLocations.add(new QuestLocation() {
			{
				positions.add(new LatLng(45.27287427579737, 19.82904594631262));
				placemarker = R.drawable.location_bus_station;
				placemarkerVisited = R.drawable.location_bus_station_visited;
				name = R.string.bus_station_location_name;
				actor = R.drawable.actor_bus_station;
				description = R.string.bus_station_location_description;
				quickAction = new QuickAction() {
					{
						description = R.string.bus_station_quick_action_description;
						image = R.drawable.item_ticket_large;
						moneyCost = 0;
						name = R.string.bus_station_quick_action_name;
						doneImage = R.drawable.bus_station_closed;
					}
				};
				quest = new Quest(this) {
					{
						image = R.drawable.quest_bus_station;
						name = R.string.bus_station_quest_name;
						description = R.string.bus_station_quest_description;
						doneDescription = R.string.bus_station_quest_done;
						rewards.put(ItemType.Happiness, 30);
						rewards.put(ItemType.Money, 0);
						endingConditions.add(new QuestEndingCondition(ItemType.Money, 1300));
					}
				};
			}

			private boolean isHintShown;

			@Override
			protected void onQuickActionDone() {
				super.onQuickActionDone();
				App.UIListener.showQuickActionDonePopup(this);
			}

			@Override
			public void tryEndQuest() {
				if (canQuestEnd()) {
					if (!isHintShown) {
						App.UIListener.showToast(R.string.bus_station_quest_done_hint);
						isHintShown = true;
					}
				}
			}
		});

		// church
		QuestLocations.add(new QuestLocation() {
			{
				positions.add(new LatLng(45.25693882650348, 19.84781245924985));
				placemarker = R.drawable.location_church;
				placemarkerVisited = R.drawable.location_church_visited;
				name = R.string.church_location_name;
				actor = R.drawable.actor_church;
				description = R.string.church_location_description;
				quickAction = new QuickAction() {
					{
						description = R.string.church_quick_action_description;
						image = R.drawable.item_blessing_large;
						moneyCost = 500;
						name = R.string.church_quick_action_name;
					}
				};
				quest = new Quest(this) {
					{
						image = R.drawable.quest_church;
						name = R.string.church_quest_name;
						description = R.string.church_quest_description;
						doneDescription = R.string.church_quest_done;
						rewards.put(ItemType.Happiness, 20);
						rewards.put(ItemType.Money, 0);
						endingConditions.add(new QuestEndingCondition(ItemType.Hammer, 1));
						endingConditions.add(new QuestEndingCondition(ItemType.Saw, 1));
					}
				};
			}

			@Override
			protected void onQuickActionDone() {
				super.onQuickActionDone();

				App.PlayerState.modifyMoney(-500);
				App.PlayerState.modifyHappiness(10);
				App.PlayerState.addToInventory(ItemType.Blessing, 1);
			}

			@Override
			protected void onQuestStarted() {
				super.onQuestStarted();

				ShopLocations.get(ShopLocation.Fence).addToInventory(ItemType.Hammer, 1, 1);
				ShopLocations.get(ShopLocation.Fence).addToInventory(ItemType.Saw, 1, 1);
				ShopLocations.get(ShopLocation.Regular).addToInventory(ItemType.Hammer, 1, 1);
				ShopLocations.get(ShopLocation.Regular).addToInventory(ItemType.Saw, 1, 1);
			}

			@Override
			public boolean canQuestEnd() {
				if (getMarkerOnLocation(App.LocationState.getPlayerLocation()) == null) {
					return false;
				}

				if (super.canQuestEnd()) {
					return true;
				}

				App.UIListener.showToast(R.string.church_quest_missing_prerequisites);
				return false;
			}
		});

		// hospital
		QuestLocations.add(new QuestLocation() {
			{
				positions.add(new LatLng(45.25088194534524, 19.82415974508284));
				placemarker = R.drawable.location_hospital;
				placemarkerVisited = R.drawable.location_hospital_visited;
				name = R.string.hospital_location_name;
				actor = R.drawable.actor_hospital;
				description = R.string.hospital_location_description;
				quickAction = new QuickAction() {
					{
						description = R.string.hospital_quick_action_description;
						image = R.drawable.item_kidney_large;
						moneyCost = 0;
						name = R.string.hospital_quick_action_name;
					}
				};
				quest = new Quest(this) {
					{
						image = R.drawable.quest_hospital;
						name = R.string.hospital_quest_name;
						description = R.string.hospital_quest_description;
						doneDescription = R.string.hospital_quest_done;
						rewards.put(ItemType.Happiness, 20);
						rewards.put(ItemType.Money, 0);
						endingConditions.add(new QuestEndingCondition(ItemType.Document, 4));
					}
				};
			}

			@Override
			protected void onQuickActionDone() {
				super.onQuickActionDone();

				App.PlayerState.modifyMoney(1500);
				if (OgdRandom.instance().toss(50)) {
					if (App.PlayerState.modifyHappiness(-30)) {
						App.UIListener.showToast(R.string.hospital_quick_action_infection_hint);
					}
				}

				App.PlayerState.removeFromInventory(ItemType.Kidney, 1);
			}

			@Override
			protected void onQuestStarted() {
				super.onQuestStarted();

				App.UIListener.showQuestItems(ItemType.Document);
			}

			@Override
			protected void onQuestDone() {
				super.onQuestDone();
				QuestItemLocations.get(ItemType.Document).removeMarkers();
			}
		});

		// parking
		QuestLocations.add(new QuestLocation() {
			{
				positions.add(new LatLng(45.26482963798485, 19.83079810947642));
				positions.add(new LatLng(45.25694644269606, 19.84404022300477));
				positions.add(new LatLng(45.24683278326162, 19.84721506207512));
				positions.add(new LatLng(45.258741, 19.82382499999994));
				placemarker = R.drawable.location_parking;
				placemarkerVisited = R.drawable.location_parking_visited;
				name = R.string.parking_location_name;
				actor = R.drawable.actor_parking;
				description = R.string.parking_location_description;
				quickAction = new QuickAction() {
					{
						description = R.string.parking_quick_action_description;
						image = R.drawable.item_car_large;
						moneyCost = 0;
						name = R.string.parking_quick_action_name;
					}
				};
				quest = new Quest(this) {
					{
						image = R.drawable.quest_parking;
						name = R.string.parking_quest_name;
						description = R.string.parking_quest_description;
						doneDescription = R.string.parking_quest_done;
						rewards.put(ItemType.Happiness, 0);
						rewards.put(ItemType.Money, 1000);
						endingConditions.add(new QuestEndingCondition(ItemType.Hammer, 1));
						endingConditions.add(new QuestEndingCondition(ItemType.Screwdriver, 1));
						endingConditions.add(new QuestEndingCondition(ItemType.LootedCar, 10));
					}
				};
			}

			@Override
			protected void onQuickActionDone() {
				super.onQuickActionDone();

				App.PlayerState.modifyMoney(500);
				App.PlayerState.removeFromInventory(ItemType.Car, 1);
			}

			@Override
			protected void onQuestStarted() {
				super.onQuestStarted();

				App.UIListener.showQuestItems(ItemType.LootedCar);

				ShopLocations.get(ShopLocation.Fence).addToInventory(ItemType.Hammer, 1, 1);
				ShopLocations.get(ShopLocation.Fence).addToInventory(ItemType.Screwdriver, 1, 1);
				ShopLocations.get(ShopLocation.Regular).addToInventory(ItemType.Hammer, 1, 1);
				ShopLocations.get(ShopLocation.Regular).addToInventory(ItemType.Screwdriver, 1, 1);

				RandomEvents.get(RandomEvent.Police).setChance(2);
			}

			@Override
			protected void onQuestDone() {
				super.onQuestDone();
				QuestItemLocations.get(ItemType.LootedCar).removeMarkers();
			}
		});

		// police station
		QuestLocations.add(new QuestLocation() {
			{
				positions.add(new LatLng(45.26118058242006, 19.83485634485685));
				positions.add(new LatLng(45.24065653269686, 19.84370876239801));
				positions.add(new LatLng(45.25061296988378, 19.85406213191341));
				placemarker = R.drawable.location_police_station;
				placemarkerVisited = R.drawable.location_police_station_visited;
				name = R.string.police_station_location_name;
				actor = R.drawable.actor_police_station;
				description = R.string.police_station_location_description;
				quickAction = new QuickAction() {
					{
						description = R.string.police_station_quick_action_description;
						image = R.drawable.item_id_large;
						moneyCost = 500;
						name = R.string.police_station_quick_action_name;
					}
				};
				quest = new Quest(this) {
					{
						image = R.drawable.quest_police_station;
						name = R.string.police_station_quest_name;
						description = R.string.police_station_quest_description;
						doneDescription = R.string.police_station_quest_done;
					}
				};
			}

			private static final long updateInterval = 1; // ticks

			private Location startingLocation;

			private int moneyReward;

			@Override
			protected void onQuickActionDone() {
				super.onQuickActionDone();

				App.PlayerState.addToInventory(ItemType.Id, 1);
				App.PlayerState.modifyMoney(-500);
			}

			@Override
			protected void onQuestStarted() {
				super.onQuestStarted();

				startingLocation = App.LocationState.getPlayerLocation();

				App.GameState.addTickListener(this);
			}

			@Override
			public boolean canQuestEnd() {
				return startingLocation.distanceTo(App.LocationState.getPlayerLocation()) > 100;
			}

			@Override
			protected void onQuestDone() {
				super.onQuestDone();

				quest.setReward(ItemType.Money, moneyReward);

				App.GameState.removeTickListener(this);
			}

			@Override
			public void onTick(int count) {
				super.onTick(count);

				if (count % updateInterval == 0) {
					if (App.PlayerState.modifyHappiness(-5)) {
						App.PlayerState.modifyMoney(50);
						moneyReward += 50;
						App.UIListener.showToast(R.string.police_station_quest_reward_text);
					}
				}
			}
		});

		// school
		QuestLocations.add(new QuestLocation() {
			{
				positions.add(new LatLng(45.25857455349606, 19.84423291085287));
				positions.add(new LatLng(45.24669554901577, 19.85244847396926));
				placemarker = R.drawable.location_school;
				placemarkerVisited = R.drawable.location_school_visited;
				name = R.string.school_location_name;
				actor = R.drawable.actor_school;
				description = R.string.school_location_description;
				quickAction = new QuickAction() {
					{
						description = R.string.school_quick_action_description;
						image = R.drawable.item_certificate_large;
						moneyCost = 1500;
						name = R.string.school_quick_action_name;
					}
				};
				quest = new Quest(this) {
					{
						image = R.drawable.quest_school;
						name = R.string.school_quest_name;
						description = R.string.school_quest_description;
						doneDescription = R.string.school_quest_done;
						rewards.put(ItemType.Happiness, 20);
						rewards.put(ItemType.Money, 0);
						endingConditions.add(new QuestEndingCondition(ItemType.Bottle, 10));
						endingConditions.add(new QuestEndingCondition(ItemType.Brick, 10));
					}
				};
			}

			@Override
			protected void onQuickActionDone() {
				super.onQuickActionDone();

				App.PlayerState.modifyMoney(-1500);
				if (OgdRandom.instance().toss(30)) {
					App.PlayerState.modifyMoney(200);
				}
				if (OgdRandom.instance().toss(20)) {
					App.PlayerState.modifyHappiness(10);
				}
				QuestItemLocations.get(ItemType.Certificate).removeMarkers();
				App.PlayerState.addToInventory(ItemType.Certificate, 1);
			}

			@Override
			protected void onQuestStarted() {
				super.onQuestStarted();

				App.UIListener.showQuestItems(ItemType.Bottle);
				App.UIListener.showQuestItems(ItemType.Brick);
			}

			@Override
			protected void onQuestDone() {
				super.onQuestDone();
				QuestItemLocations.get(ItemType.Bottle).removeMarkers();
				QuestItemLocations.get(ItemType.Brick).removeMarkers();
				RandomEvents.get(RandomEvent.Thugs).activate();
			}
		});

		// theatre
		QuestLocations.add(new QuestLocation() {
			{
				positions.add(new LatLng(45.25486401431286, 19.84309435204724));
				positions.add(new LatLng(45.25512386799397, 19.8489543096673));
				positions.add(new LatLng(45.25769955913628, 19.8417884408716));
				placemarker = R.drawable.location_theatre;
				placemarkerVisited = R.drawable.location_theatre_visited;
				name = R.string.theatre_location_name;
				actor = R.drawable.actor_theatre;
				description = R.string.theatre_location_description;
				quickAction = new QuickAction() {
					{
						description = R.string.theatre_quick_action_description;
						image = R.drawable.item_ticket_large;
						moneyCost = 500;
						name = R.string.theatre_quick_action_name;
					}
				};
				quest = new Quest(this) {
					{
						image = R.drawable.quest_theatre;
						name = R.string.theatre_quest_name;
						description = R.string.theatre_quest_description;
						doneDescription = R.string.theatre_quest_done;
						rewards.put(ItemType.Happiness, 20);
						rewards.put(ItemType.Money, 0);
						endingConditions.add(new QuestEndingCondition(ItemType.Certificate, 1));
						endingConditions.add(new QuestEndingCondition(ItemType.PartyMembership, 1));
						endingConditions.add(new QuestEndingCondition(ItemType.Fame, 1));
					}
				};
			}

			@Override
			protected void onQuickActionDone() {
				super.onQuickActionDone();

				App.PlayerState.modifyMoney(-500);
				App.PlayerState.modifyHappiness(10);
				App.PlayerState.addToInventory(ItemType.Ticket, 1);
			}

			@Override
			protected void onQuestStarted() {
				super.onQuestStarted();

				if (!App.PlayerState.getInventory().contains(ItemType.Certificate, 1)) {
					App.UIListener.showQuestItems(ItemType.Certificate);
				}
				if (!App.PlayerState.getInventory().contains(ItemType.Fame, 1)) {
					App.UIListener.showQuestItems(ItemType.Fame);
				}
				if (!App.PlayerState.getInventory().contains(ItemType.PartyMembership, 1)) {
					App.UIListener.showQuestItems(ItemType.PartyMembership);
				}
			}

			@Override
			protected void onQuestDone() {
				super.onQuestDone();
				QuestItemLocations.get(ItemType.Certificate).removeMarkers();
				QuestItemLocations.get(ItemType.Fame).removeMarkers();
				QuestItemLocations.get(ItemType.PartyMembership).removeMarkers();
			}
		});

		// town hall
		QuestLocations.add(new QuestLocation() {
			{
				positions.add(new LatLng(45.25324860873409, 19.84738502828024));
				placemarker = R.drawable.location_town_hall;
				placemarkerVisited = R.drawable.location_town_hall_visited;
				name = R.string.town_hall_location_name;
				actor = R.drawable.actor_town_hall;
				description = R.string.town_hall_location_description;
				quickAction = new QuickAction() {
					{
						description = R.string.town_hall_quick_action_description;
						image = R.drawable.item_party_membership_large;
						moneyCost = 1000;
						name = R.string.town_hall_quick_action_name;
					}
				};
				quest = new Quest(this) {
					{
						image = R.drawable.quest_town_hall;
						name = R.string.town_hall_quest_name;
						description = R.string.town_hall_quest_description;
						doneDescription = R.string.town_hall_quest_done;
						rewards.put(ItemType.Happiness, 0);
						rewards.put(ItemType.Money, 1000);
						endingConditions.add(new QuestEndingCondition(ItemType.Vote, 5));
					}
				};
			}

			@Override
			protected void onQuickActionDone() {
				super.onQuickActionDone();

				App.PlayerState.modifyMoney(-1000);
				if (OgdRandom.instance().toss(30)) {
					App.PlayerState.modifyMoney(200);
				}
				if (OgdRandom.instance().toss(20)) {
					App.PlayerState.modifyHappiness(10);
				}
				QuestItemLocations.get(ItemType.PartyMembership).removeMarkers();
				App.PlayerState.addToInventory(ItemType.PartyMembership, 1);
			}

			@Override
			protected void onQuestStarted() {
				super.onQuestStarted();

				App.UIListener.showQuestItems(ItemType.Vote);
			}

			@Override
			protected void onQuestDone() {
				super.onQuestDone();
				QuestItemLocations.get(ItemType.Vote).removeMarkers();
			}
		});

		// TV station
		QuestLocations.add(new QuestLocation() {
			{
				positions.add(new LatLng(45.2495669860552, 19.8454059341638));
				placemarker = R.drawable.location_tv;
				placemarkerVisited = R.drawable.location_tv_visited;
				name = R.string.tv_station_location_name;
				actor = R.drawable.actor_tv;
				description = R.string.tv_station_location_description;
				quickAction = new QuickAction() {
					{
						description = R.string.tv_station_quick_action_description;
						image = R.drawable.item_fame_large;
						moneyCost = 1500;
						name = R.string.tv_station_quick_action_name;
					}
				};
				quest = new Quest(this) {
					{
						image = R.drawable.quest_tv;
						name = R.string.tv_station_quest_name;
						description = R.string.tv_station_quest_description;
						doneDescription = R.string.tv_station_quest_done;
						rewards.put(ItemType.Happiness, 20);
						rewards.put(ItemType.Money, 0);
						endingConditions.add(new QuestEndingCondition(ItemType.Video, 3));
					}
				};
			}

			@Override
			protected void onQuickActionDone() {
				super.onQuickActionDone();

				App.PlayerState.modifyMoney(-1500);
				if (OgdRandom.instance().toss(30)) {
					App.PlayerState.modifyMoney(200);
				}
				if (OgdRandom.instance().toss(20)) {
					App.PlayerState.modifyHappiness(10);
				}
				QuestItemLocations.get(ItemType.Fame).removeMarkers();
				App.PlayerState.addToInventory(ItemType.Fame, 1);
			}

			@Override
			protected void onQuestStarted() {
				super.onQuestStarted();

				App.UIListener.showQuestItems(ItemType.Video);
			}

			@Override
			protected void onQuestDone() {
				super.onQuestDone();
				QuestItemLocations.get(ItemType.Video).removeMarkers();
			}
		});
	}

	private static void initializeQuestItemLocations() {
		// bottles
		QuestItemLocations.put(ItemType.Bottle, new QuestItemLocation() {
			{
				description = R.string.item_added_bottle;
				itemType = ItemType.Bottle;
				name = R.string.item_info_window_bottle;
				numberOfVisibleItems = 5;
				placemarker = R.drawable.quest_item_bottle;
				positions.add(new LatLng(45.24187053387026, 19.83010347858911));
				positions.add(new LatLng(45.24684114428113, 19.83597664109003));
				positions.add(new LatLng(45.26259987579385, 19.8354547868569));
				positions.add(new LatLng(45.24837198576766, 19.8539596598402));
				positions.add(new LatLng(45.25017900133484, 19.82609371083918));
				positions.add(new LatLng(45.25587985907407, 19.83182394357837));
				positions.add(new LatLng(45.24161664395729, 19.85054986220102));
				positions.add(new LatLng(45.24624908401065, 19.84761348270332));
				positions.add(new LatLng(45.23892497707038, 19.84071938285475));
				positions.add(new LatLng(45.24961815380163, 19.84103573262051));
			}
		});

		// bricks
		QuestItemLocations.put(ItemType.Brick, new QuestItemLocation() {
			{
				description = R.string.item_added_brick;
				itemType = ItemType.Brick;
				name = R.string.item_info_window_brick;
				numberOfVisibleItems = 5;
				placemarker = R.drawable.quest_item_brick;
				positions.add(new LatLng(45.25508553372929, 19.85146476300619));
				positions.add(new LatLng(45.26389215125523, 19.82536963884301));
				positions.add(new LatLng(45.2502431343315, 19.83395723047378));
				positions.add(new LatLng(45.25888098604934, 19.84259130209812));
				positions.add(new LatLng(45.23714085070252, 19.84711670740237));
				positions.add(new LatLng(45.248663087907, 19.84658845971921));
				positions.add(new LatLng(45.24776239106963, 19.83051511148336));
				positions.add(new LatLng(45.24139843752406, 19.8334920899284));
				positions.add(new LatLng(45.25395084740298, 19.84216145346429));
				positions.add(new LatLng(45.24431945367819, 19.83580478066176));
			}
		});

		// certificate
		QuestItemLocations.put(ItemType.Certificate, new QuestItemLocation() {
			{
				placemarker = R.drawable.quest_item_certificate;
				positions.add(new LatLng(45.25833275867948, 19.84501131093888));
				positions.add(new LatLng(45.24624715815204, 19.85211658787066));
			}

			@Override
			public void onVisited(Marker marker) {
				// do nothing
			}
		});

		// documents
		QuestItemLocations.put(ItemType.Document, new QuestItemLocation() {
			{
				description = R.string.item_added_document;
				itemType = ItemType.Document;
				name = R.string.item_info_window_document;
				placemarker = R.drawable.quest_item_document;
				positions.add(new LatLng(45.24891461621505, 19.84565743910465));
				positions.add(new LatLng(45.25201989095501, 19.84240729992762));
				positions.add(new LatLng(45.24686811990539, 19.84085736519772));
				positions.add(new LatLng(45.25486387760262, 19.84465813064627));
			}
		});

		// fame
		QuestItemLocations.put(ItemType.Fame, new QuestItemLocation() {
			{
				placemarker = R.drawable.quest_item_fame;
				positions.add(new LatLng(45.25004450031597, 19.84602409346848));
			}

			@Override
			public void onVisited(Marker marker) {
				// do nothing
			}
		});

		// looted car
		QuestItemLocations.put(ItemType.LootedCar, new QuestItemLocation() {
			{
				description = R.string.item_added_looted_car;
				itemType = ItemType.LootedCar;
				name = R.string.item_info_window_looted_car;
				numberOfVisibleItems = 20;
				placemarker = R.drawable.quest_item_looted_car;
				positions.add(new LatLng(45.24985423408065, 19.85339150343098));
				positions.add(new LatLng(45.2506730595508, 19.85557056548508));
				positions.add(new LatLng(45.24794129805186, 19.85308862139405));
				positions.add(new LatLng(45.24497196961125, 19.85066897666981));
				positions.add(new LatLng(45.245834141237, 19.84973469671736));
				positions.add(new LatLng(45.24460877317356, 19.84933081790227));
				positions.add(new LatLng(45.24214162511471, 19.84978362744447));
				positions.add(new LatLng(45.239799921745, 19.85109260234848));
				positions.add(new LatLng(45.24335609433521, 19.84595073713769));
				positions.add(new LatLng(45.24483173952014, 19.84432443142487));
				positions.add(new LatLng(45.23955796334059, 19.84437044259101));
				positions.add(new LatLng(45.24720850375958, 19.84134935026884));
				positions.add(new LatLng(45.2488586939343, 19.84215802869514));
				positions.add(new LatLng(45.2483271325812, 19.84034354285211));
				positions.add(new LatLng(45.2520074113994, 19.84734857212629));
				positions.add(new LatLng(45.25308034532938, 19.84691436785785));
				positions.add(new LatLng(45.25284378083403, 19.84551494928479));
				positions.add(new LatLng(45.25274450511386, 19.84370907650803));
				positions.add(new LatLng(45.2559595990472, 19.84246198257233));
				positions.add(new LatLng(45.2541953780767, 19.84098024718754));
				positions.add(new LatLng(45.25475221108459, 19.83988632473152));
				positions.add(new LatLng(45.25615096197334, 19.84177339936787));
				positions.add(new LatLng(45.25829157432766, 19.83508928343209));
				positions.add(new LatLng(45.25977516917437, 19.83441974853981));
				positions.add(new LatLng(45.25836065022855, 19.83815797186654));
				positions.add(new LatLng(45.24397618970653, 19.83496744401826));
				positions.add(new LatLng(45.25828478247576, 19.83321359727734));
				positions.add(new LatLng(45.24620652001128, 19.83297875278647));
				positions.add(new LatLng(45.2507414148141, 19.8373185662965));
				positions.add(new LatLng(45.25674349979542, 19.84389987063521));
				positions.add(new LatLng(45.25761294907235, 19.84921021668684));
				positions.add(new LatLng(45.25425203251124, 19.84734489152353));
				positions.add(new LatLng(45.25392083632216, 19.84923856462511));
				positions.add(new LatLng(45.25238787841285, 19.83273689447173));
				positions.add(new LatLng(45.25524638016309, 19.84915994478472));
				positions.add(new LatLng(45.25429933143784, 19.85335657528139));
				positions.add(new LatLng(45.25825410213876, 19.85001717867663));
				positions.add(new LatLng(45.26170047868353, 19.83661561266612));
				positions.add(new LatLng(45.26248004523994, 19.83850347031777));
				positions.add(new LatLng(45.2546385418091, 19.82966944712203));
				positions.add(new LatLng(45.26086938589587, 19.84987198069873));
				positions.add(new LatLng(45.25582092623456, 19.83500767955436));
				positions.add(new LatLng(45.25388926028983, 19.83600237523294));
				positions.add(new LatLng(45.24910714091502, 19.83866720695885));
				positions.add(new LatLng(45.24364119593062, 19.84033596464904));
				positions.add(new LatLng(45.24296039381455, 19.83779604791217));
				positions.add(new LatLng(45.26509479856787, 19.83059460881722));
				positions.add(new LatLng(45.25669283085848, 19.85464109323409));
				positions.add(new LatLng(45.26124738604946, 19.82899569915353));
				positions.add(new LatLng(45.26080352070662, 19.8290814745465));
				positions.add(new LatLng(45.26309258619232, 19.83100356693437));
				positions.add(new LatLng(45.26263405227524, 19.83079948275755));
				positions.add(new LatLng(45.26300617643246, 19.82928069468926));
				positions.add(new LatLng(45.26256470306308, 19.82514837151713));
				positions.add(new LatLng(45.26328327743313, 19.82404471290829));
				positions.add(new LatLng(45.26176623896632, 19.81943348086029));
				positions.add(new LatLng(45.26080086918112, 19.82097752869324));
				positions.add(new LatLng(45.25841520115829, 19.82395688740944));
				positions.add(new LatLng(45.25723280917401, 19.82454632463768));
				positions.add(new LatLng(45.25523362314599, 19.82641631330526));
				positions.add(new LatLng(45.25447748301208, 19.82396977786428));
				positions.add(new LatLng(45.25379781902663, 19.82107605303253));
				positions.add(new LatLng(45.25131095282035, 19.82424821460948));
				positions.add(new LatLng(45.25228727849247, 19.82451521356582));
				positions.add(new LatLng(45.24013755169314, 19.82965357504089));
				positions.add(new LatLng(45.23824543815962, 19.82873732714224));
				positions.add(new LatLng(45.23657212087718, 19.83152592457611));
				positions.add(new LatLng(45.23572836554444, 19.83174859441002));
				positions.add(new LatLng(45.23869537444418, 19.83555341935723));
				positions.add(new LatLng(45.2398841485259, 19.83622197655823));
				positions.add(new LatLng(45.23767832024398, 19.83859715526686));
				positions.add(new LatLng(45.23875386097586, 19.84845527327193));
				positions.add(new LatLng(45.24126495745961, 19.84510670781802));
				positions.add(new LatLng(45.24657787944391, 19.84749700530712));
				positions.add(new LatLng(45.24644691917244, 19.8540984130289));
				positions.add(new LatLng(45.24655468412414, 19.8495291230224));
				positions.add(new LatLng(45.24434004982702, 19.85167180272642));
				positions.add(new LatLng(45.25161298663356, 19.85189942983548));
				positions.add(new LatLng(45.25272476701714, 19.85037158282508));
			}

			@Override
			public void onVisited(Marker marker) {
				if (App.PlayerState.getInventory().contains(ItemType.Hammer, 1)
						&& App.PlayerState.getInventory().contains(ItemType.Screwdriver, 1))
				{
					super.onVisited(marker);
				}
				else {
					App.UIListener.showToast(R.string.parking_quest_missing_prerequisites);
				}
			}

		});

		// party membership
		QuestItemLocations.put(ItemType.PartyMembership, new QuestItemLocation() {
			{
				placemarker = R.drawable.quest_item_party_membership;
				positions.add(new LatLng(45.25352736330595, 19.84839788534059));
			}

			@Override
			public void onVisited(Marker marker) {
				// do nothing
			}
		});

		// stolen bikes
		QuestItemLocations.put(ItemType.StolenBike, new QuestItemLocation() {
			{
				description = R.string.item_added_stolen_bike;
				itemType = ItemType.StolenBike;
				name = R.string.item_info_window_stolen_bike;
				numberOfVisibleItems = 5;
				placemarker = R.drawable.quest_item_stolen_bike;
				positions.add(new LatLng(45.25125305655315, 19.8282494396945));
				positions.add(new LatLng(45.25798227172751, 19.83329746111705));
				positions.add(new LatLng(45.26148908927479, 19.84321526659767));
				positions.add(new LatLng(45.25920004253455, 19.84863184757483));
				positions.add(new LatLng(45.2445959091857, 19.8292568907946));
				positions.add(new LatLng(45.2402645313409, 19.83086507790075));
				positions.add(new LatLng(45.25117139774173, 19.83888950117404));
				positions.add(new LatLng(45.2443585436272, 19.83833178130398));
				positions.add(new LatLng(45.24342282811425, 19.84907253246987));
				positions.add(new LatLng(45.24927980812912, 19.84862873995615));
			}
		});

		// stolen cables
		QuestItemLocations.put(ItemType.StolenCable, new QuestItemLocation() {
			{
				description = R.string.item_added_stolen_cable;
				itemType = ItemType.StolenCable;
				name = R.string.item_info_window_stolen_cable;
				numberOfVisibleItems = 5;
				placemarker = R.drawable.quest_item_stolen_cable;
				positions.add(new LatLng(45.25728248410993, 19.83793046169937));
				positions.add(new LatLng(45.25478241704499, 19.82848687701139));
				positions.add(new LatLng(45.24844441525507, 19.83681009426454));
				positions.add(new LatLng(45.24298614733148, 19.83427526719518));
				positions.add(new LatLng(45.24560702278407, 19.8447772315067));
				positions.add(new LatLng(45.25981441924108, 19.846269384557));
				positions.add(new LatLng(45.2371464967726, 19.84060049477317));
				positions.add(new LatLng(45.24040451587481, 19.84640889878367));
				positions.add(new LatLng(45.24720728198518, 19.82542505590494));
				positions.add(new LatLng(45.2532990425975, 19.85173246524085));

			}
		});

		// stolen manholes
		QuestItemLocations.put(ItemType.StolenManhole, new QuestItemLocation() {
			{
				description = R.string.item_added_stolen_manhole;
				itemType = ItemType.StolenManhole;
				name = R.string.item_info_window_stolen_manhole;
				numberOfVisibleItems = 5;
				placemarker = R.drawable.quest_item_stolen_manhole;
				positions.add(new LatLng(45.25237547722485, 19.83180956452378));
				positions.add(new LatLng(45.25780389820621, 19.82571203424748));
				positions.add(new LatLng(45.26052109124998, 19.83873910058021));
				positions.add(new LatLng(45.24588280097838, 19.83266015559044));
				positions.add(new LatLng(45.2503909516681, 19.8415101006556));
				positions.add(new LatLng(45.2419763200899, 19.85106671576335));
				positions.add(new LatLng(45.2366181787595, 19.83813655420569));
				positions.add(new LatLng(45.24152648007591, 19.82875465002543));
				positions.add(new LatLng(45.25532524535813, 19.8451720351442));
				positions.add(new LatLng(45.2409182572792, 19.84211852657129));

			}
		});

		// videos
		QuestItemLocations.put(ItemType.Video, new QuestItemLocation() {
			{
				description = R.string.item_added_video;
				itemType = ItemType.Video;
				name = R.string.item_info_window_video;
				placemarker = R.drawable.quest_item_video;
				positions.add(new LatLng(45.25974374139546, 19.82823001702563));
				positions.add(new LatLng(45.25214169429746, 19.83716219275739));
				positions.add(new LatLng(45.25340695195019, 19.84225692940756));
			}
		});

		// votes
		QuestItemLocations.put(ItemType.Vote, new QuestItemLocation() {
			{
				description = R.string.item_added_vote;
				itemType = ItemType.Vote;
				name = R.string.item_info_window_vote;
				placemarker = R.drawable.quest_item_vote;
				positions.add(new LatLng(45.25466559179968, 19.83796261365806));
				positions.add(new LatLng(45.25428527119144, 19.84462086998431));
				positions.add(new LatLng(45.25290650546238, 19.84302697923582));
				positions.add(new LatLng(45.25292756329854, 19.85519830958833));
				positions.add(new LatLng(45.24059033075731, 19.84867551712981));
				positions.add(new LatLng(45.2433990494621, 19.84680480830404));
				positions.add(new LatLng(45.24019317047214, 19.83409374853122));
				positions.add(new LatLng(45.23752270470815, 19.83057179220338));
				positions.add(new LatLng(45.23789573932091, 19.8287479538481));
				positions.add(new LatLng(45.24515216495599, 19.81716969211155));
				positions.add(new LatLng(45.24536666695125, 19.83628725599003));
				positions.add(new LatLng(45.25891462859691, 19.82082618222215));
				positions.add(new LatLng(45.24167141021508, 19.82632917216262));
				positions.add(new LatLng(45.25844276026041, 19.8547521318914));
				positions.add(new LatLng(45.25871955007477, 19.8297446643384));
				positions.add(new LatLng(45.26419707982751, 19.83208374919149));
				positions.add(new LatLng(45.26261032998779, 19.82280146951428));
				positions.add(new LatLng(45.26288956020682, 19.85010226502219));
			}
		});
	}

	private static void initializeShopLocations() {

		ShopLocations.put(ShopLocation.Fence, new ShopLocation() {
			{
				type = ShopLocation.Fence;
				actor = R.drawable.actor_fence;
				positions.add(new LatLng(45.25349181292791, 19.83542602692319));
				positions.add(new LatLng(45.23884496467124, 19.83342798747653));
				positions.add(new LatLng(45.24340221370247, 19.84123061432764));
				positions.add(new LatLng(45.25353650288785, 19.84468532476894));
				positions.add(new LatLng(45.24987520739421, 19.85306221024932));

				name = R.string.location_fence_name;
				description = R.string.location_fence_description;
				placemarker = R.drawable.location_fence;

				buyPrices.put(ItemType.Bottle, 100);
				buyPrices.put(ItemType.Brick, 100);
				buyPrices.put(ItemType.Hammer, 200);
				buyPrices.put(ItemType.StolenBike, 400);
				buyPrices.put(ItemType.StolenCable, 200);
				buyPrices.put(ItemType.StolenManhole, 300);
				buyPrices.put(ItemType.Saw, 200);
				buyPrices.put(ItemType.Screwdriver, 200);

				sellPrices.put(ItemType.Bottle, 50);
				sellPrices.put(ItemType.Brick, 50);
				sellPrices.put(ItemType.Hammer, 100);
				sellPrices.put(ItemType.StolenBike, 200);
				sellPrices.put(ItemType.StolenCable, 100);
				sellPrices.put(ItemType.StolenManhole, 150);
				sellPrices.put(ItemType.Saw, 100);
				sellPrices.put(ItemType.Screwdriver, 100);

				inventory.add(ItemType.Bottle, 4);
				inventory.add(ItemType.Brick, 3);
			}
		});

		ShopLocations.put(ShopLocation.Pharmacy, new ShopLocation() {
			{
				type = ShopLocation.Pharmacy;
				actor = R.drawable.actor_pharmacy;
				positions.add(new LatLng(45.25409444444445, 19.84826666666666));
				positions.add(new LatLng(45.25199722222222, 19.85234722222223));
				positions.add(new LatLng(45.26403061416207, 19.83076660040843));
				positions.add(new LatLng(45.24816456164296, 19.83440443063778));
				positions.add(new LatLng(45.26126667210135, 19.81993818295547));

				name = R.string.location_pharmacy_name;
				description = R.string.location_pharmacy_description;
				placemarker = R.drawable.location_pharmacy;

				buyPrices.put(ItemType.Antibiotic, 200);
				buyPrices.put(ItemType.Sedative, 200);

				inventory.add(ItemType.Antibiotic, 1);
				inventory.add(ItemType.Sedative, 1);
			}

			private List<InventoryResetTime> inventoryResetTimes = new ArrayList<InventoryResetTime>();

			@Override
			public void addToInventory(ItemType itemType, int quantity) {
				super.addToInventory(itemType, quantity);

				if (quantity < 0) {
					inventoryResetTimes.add(new InventoryResetTime(itemType, App.GameState.getTickCount()
							+ ItemRestockInterval));
				}
			}

			@Override
			public void onTick(int count) {
				super.onTick(count);

				// restock inventory after configurable interval
				Iterator<InventoryResetTime> iterator = inventoryResetTimes.iterator();

				while (iterator.hasNext()) {
					InventoryResetTime entry = iterator.next();
					if (entry.resetTickCount <= count) {
						inventory.add(entry.itemType, 1);
						iterator.remove();
					}
				}
			}
		});

		ShopLocations.put(ShopLocation.Regular, new ShopLocation() {
			{
				type = ShopLocation.Regular;
				actor = R.drawable.actor_shop;
				positions.add(new LatLng(45.24888055684482, 19.82973807539435));
				positions.add(new LatLng(45.26095871588231, 19.83157083714464));
				positions.add(new LatLng(45.25759199523607, 19.84995285241241));
				positions.add(new LatLng(45.24274967563962, 19.83667662212358));
				positions.add(new LatLng(45.24797631907828, 19.84387802192207));

				name = R.string.location_shop_name;
				description = R.string.location_shop_description;
				placemarker = R.drawable.location_shop;

				buyPrices.put(ItemType.Bottle, 100);
				buyPrices.put(ItemType.Brick, 100);
				buyPrices.put(ItemType.Hammer, 200);
				buyPrices.put(ItemType.Saw, 200);
				buyPrices.put(ItemType.Screwdriver, 200);

				sellPrices.put(ItemType.Bottle, 50);
				sellPrices.put(ItemType.Brick, 50);
				sellPrices.put(ItemType.Hammer, 100);
				sellPrices.put(ItemType.Saw, 100);
				sellPrices.put(ItemType.Screwdriver, 100);

				inventory.add(ItemType.Bottle, 4);
				inventory.add(ItemType.Brick, 3);
			}
		});
	}

	private static void initializeRandomEvents() {
		RandomEvents.put(RandomEvent.Inflation, new RandomEvent() {
			{
				chance = 2;
				description = R.string.random_event_inflation_description;
				image = R.drawable.inflation;
				title = R.string.random_event_inflation_title;
			}

			private static final int inflationRate = 5; // percent

			@Override
			public void onEventCompleted() {
				super.onEventCompleted();
				int amount = App.PlayerState.getMoney() * inflationRate / 100;
				App.PlayerState.modifyMoney(-amount);
			}
		});

		RandomEvents.put(RandomEvent.Police, new RandomEvent() {
			{
				chance = 1; // DEBUG ? 99 : 1;
				actor = R.drawable.actor_police_station;
				title = R.string.random_event_police_title;
			}

			@Override
			public int getDescription() {
				return App.PlayerState.getInventory().contains(ItemType.Id, 1) ?
						R.string.random_event_police_description_has_id :
						R.string.random_event_police_description_no_id;

			}

			@Override
			public void onEventCompleted() {
				super.onEventCompleted();
				if (!App.PlayerState.getInventory().contains(ItemType.Id, 1)) {
					App.PlayerState.modifyMoney(-1000);
				}
			}
		});

		RandomEvents.put(RandomEvent.Thugs, new RandomEvent() {
			{
				chance = 3;
				actor = R.drawable.actor_thug;
				description = R.string.random_event_thugs_description;
				title = R.string.random_event_thugs_title;
			}

			@Override
			public void onEventCompleted() {
				super.onEventCompleted();
				App.PlayerState.modifyHappiness(-30);
			}
		});
	}
}
