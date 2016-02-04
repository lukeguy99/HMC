package com.hmc.onegoodday.states;

import java.util.ArrayList;
import java.util.List;

import com.hmc.onegoodday.Configuration;
import com.hmc.onegoodday.listeners.InventoryUpdateListener;
import com.hmc.onegoodday.listeners.PlayerStatusUpdateListener;
import com.hmc.onegoodday.listeners.TickListener;
import com.hmc.onegoodday.models.Gender;
import com.hmc.onegoodday.models.Language;
import com.hmc.onegoodday.models.inventory.Inventory;
import com.hmc.onegoodday.models.inventory.ItemType;
import com.hmc.onegoodday.models.quests.Quest;

public class PlayerState implements TickListener {

	private boolean isInitialized;

	private int age;

	private Gender gender;

	private int happiness;

	private Inventory inventory;

	private Language language;

	private List<Quest> quests;

	private List<InventoryUpdateListener> inventoryUpdateListeners;

	private List<PlayerStatusUpdateListener> playerStatusUpdateListeners;

	public PlayerState() {
		if (isInitialized) {
			throw new IllegalStateException("PlayerState is already initialized");
		}
		isInitialized = true;

		initialize();
	}

	public void initialize() {
		age = Configuration.InitialAge;
		happiness = Configuration.InitialHappiness;
		inventory = new Inventory(Configuration.InitialPlayerInventory);
		quests = new ArrayList<Quest>();

		inventoryUpdateListeners = new ArrayList<InventoryUpdateListener>();
		playerStatusUpdateListeners = new ArrayList<PlayerStatusUpdateListener>();
	}

	public void addInventoryUpdateListener(InventoryUpdateListener listener) {
		inventoryUpdateListeners.add(listener);
	}

	public void addPlayerStatusUpdateListener(PlayerStatusUpdateListener listener) {
		playerStatusUpdateListeners.add(listener);

		listener.onPlayerStatusUpdated(age, happiness, getMoney());
	}

	public int getAge() {
		return age;
	}

	public void increaseAge() {
		age++;
		if (age > Configuration.MaxAge) {
			endGame();
			return;
		}

		for (PlayerStatusUpdateListener listener : playerStatusUpdateListeners) {
			listener.onPlayerStatusUpdated(age, happiness, getMoney());
		}
	}

	public Gender getGender() {
		return this.gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getHappiness() {
		return happiness;
	}

	public boolean modifyHappiness(Integer value) {
		if (null == value || 0 == value) {
			return true;
		}

		int result = happiness + value;

		if (result < 0) {
			endGame();
			return false;
		}

		happiness = result > 100 ? 100 : result;

		for (PlayerStatusUpdateListener listener : playerStatusUpdateListeners) {
			listener.onPlayerStatusUpdated(age, happiness, getMoney());
		}

		return true;
	}

	// TODO: this should not be here as add/remove shouldn't be accessible directly
	public Inventory getInventory() {
		return inventory;
	}

	public void addToInventory(ItemType itemType, int quantity) {
		if (0 == quantity) {
			return;
		}

		inventory.add(itemType, quantity);

		for (InventoryUpdateListener listener : inventoryUpdateListeners) {
			listener.onInventoryUpdated();
		}
	}

	public void removeFromInventory(ItemType itemType, int quantity) {
		inventory.remove(itemType, quantity);

		for (InventoryUpdateListener listener : inventoryUpdateListeners) {
			listener.onInventoryUpdated();
		}
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public int getMoney() {
		return inventory.getQuantity(ItemType.Money);
	}

	public boolean modifyMoney(Integer value) {
		if (null == value || 0 == value) {
			return true;
		}

		if (getMoney() + value < 0) {
			endGame();
			return false;
		}

		inventory.add(ItemType.Money, value);

		for (PlayerStatusUpdateListener listener : playerStatusUpdateListeners) {
			listener.onPlayerStatusUpdated(age, happiness, getMoney());
		}

		return true;
	}

	public List<Quest> getQuests() {
		return quests;
	}

	public void addQuest(Quest quest) {
		quests.add(quest);
	}

	@Override
	public void onTick(int count) {
		if (count % Configuration.AgingInterval == 0) {
			increaseAge();
		}
	}

	private void endGame() {
		for (PlayerStatusUpdateListener listener : playerStatusUpdateListeners) {
			listener.onEndGame();
		}
	}
}
