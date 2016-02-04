package com.hmc.onegoodday.ui.popup;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.Marker;
import com.hmc.onegoodday.App;
import com.hmc.onegoodday.models.RandomEvent;
import com.hmc.onegoodday.models.locations.QuestLocation;
import com.hmc.onegoodday.models.locations.ShopLocation;
import com.hmc.onegoodday.ui.popup.about.AboutHandler;
import com.hmc.onegoodday.ui.popup.confirmation.EndGameConfirmationHandler;
import com.hmc.onegoodday.ui.popup.confirmation.NewGameConfirmationHandler;
import com.hmc.onegoodday.ui.popup.gameIntroduction.GameIntroductionHandler;
import com.hmc.onegoodday.ui.popup.inventory.InventoryHandler;
import com.hmc.onegoodday.ui.popup.quest.QuestDoneHandler;
import com.hmc.onegoodday.ui.popup.quest.QuestHandler;
import com.hmc.onegoodday.ui.popup.questLog.QuestLogHandler;
import com.hmc.onegoodday.ui.popup.quickAction.QuickActionDoneHandler;
import com.hmc.onegoodday.ui.popup.randomEvent.RandomEventHandler;
import com.hmc.onegoodday.ui.popup.shop.ShopHandler;

public final class Popup extends PopupLayout {

	private static final String tag = "PopupDialogFragment";

	private PopupHandler handler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		handler.onShow();
		return view;
	}

	public void setOnDismissedListener(OnClickListener onDismissedListener) {
		handler.setOnDismissedListener(onDismissedListener);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		App.GameState.startTime();
	}

	private void show(FragmentManager manager) {
		App.GameState.pauseTime();
		super.show(manager, tag);
	}

	public void showAbout(FragmentManager manager) {
		show(manager);
		handler = new AboutHandler(this);
	}

	public void showGameIntroduction(FragmentManager manager) {
		show(manager);
		handler = new GameIntroductionHandler(this);
	}

	public void showEndGameConfirmation(FragmentManager manager) {
		show(manager);
		handler = new EndGameConfirmationHandler(this);
	}

	public void showInventory(FragmentManager manager) {
		show(manager);
		handler = new InventoryHandler(this);
	}

	public void showNewGameConfirmation(FragmentManager manager) {
		show(manager);
		handler = new NewGameConfirmationHandler(this);
	}

	public void showQuest(QuestLocation location, Marker marker, FragmentManager manager) {
		show(manager);
		handler = new QuestHandler(this, location, marker);
	}

	public void showQuestDone(QuestLocation location, FragmentManager manager) {
		show(manager);
		handler = new QuestDoneHandler(this, location);
	}

	public void showQuestLog(FragmentManager manager) {
		show(manager);
		handler = new QuestLogHandler(this);
	}

	public void showQuickActionDone(QuestLocation location, FragmentManager manager) {
		show(manager);
		handler = new QuickActionDoneHandler(this, location);
	}

	public void showRandomEvent(RandomEvent event, FragmentManager manager) {
		show(manager);
		handler = new RandomEventHandler(this, event);
	}

	public void showShop(ShopLocation location, Marker marker, FragmentManager manager) {
		show(manager);
		handler = new ShopHandler(this, location, marker);
	}

	@Override
	public void onBackPressed() {
		handler.onBackPressed();
	}

	@Override
	protected void onLeftButtonClick() {
		handler.onLeftButtonClick();
	}

	@Override
	protected void onRightButtonClick() {
		handler.onRightButtonClick();
	}
}
