package com.hmc.onegoodday.ui.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.Configuration;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.listeners.PlayerStatusUpdateListener;
import com.hmc.onegoodday.models.RandomEvent;
import com.hmc.onegoodday.ui.popup.EndGamePopup;

public class MainActivity extends MainActivityLayout implements PlayerStatusUpdateListener {

	private boolean isInitialized;

	@Override
	protected void onResume() {
		super.onResume();

		if (!isInitialized) {
			isInitialized = true;

			App.PlayerState.addPlayerStatusUpdateListener(MainActivity.this);

			popup.showGameIntroduction(getSupportFragmentManager());
			popup.setOnDismissedListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Configuration.RandomEvents.get(RandomEvent.Inflation).activate();
					Configuration.RandomEvents.get(RandomEvent.Police).activate();
					App.GameState.startTime();
				}
			});

			// TODO: should we wait for GPS to connect and then start the game and the time?
		}
		else {
			App.GameState.startTime();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();

		App.GameState.pauseTime();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		toggleMenu();
		return false;
	}

	public void onMenuButtonClick(View view) {
		toggleMenu();

		switch (view.getId()) {
		case R.id.menuButtonInventory:
			App.UIListener.showInventoryPopup();
			break;
		case R.id.menuButtonMyLocation:
			zoomToPlayerLocation();
			break;
		case R.id.menuButtonNewGame:
			confirmNewGame();
			break;
		case R.id.menuButtonQuestLog:
			App.UIListener.showQuestLogPopup();
			break;
		case R.id.menuButtonAbout:
			App.UIListener.showAboutPopup();
			break;
		default:
			throw new IllegalStateException("Illegal menu button " + view.getId());
		}
	}

	// TODO: na engleskom, the representative je predugaèko za mission log

	// TODO: mission log: bolnica je samo jedan red, pa je sitno, stavi min-size

	@Override
	public void onPlayerStatusUpdated(int age, int happiness, int money) {
		setAge(age);
		setHappiness(happiness);
		setMoney(money);
	}

	@Override
	public void onEndGame() {
		EndGamePopup endGamePopup = new EndGamePopup();
		endGamePopup.show(getSupportFragmentManager());
		endGamePopup.setOnDismissedListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				restartGame();
			}
		});
	}

	private void confirmNewGame() {
		popup.showNewGameConfirmation(getSupportFragmentManager());
		popup.setOnDismissedListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				restartGame();
			}
		});
	}

	private void restartGame() {
		Context context = getApplicationContext();
		Intent intent = new Intent(context, IntroActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,
				PendingIntent.FLAG_CANCEL_CURRENT);
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		manager.set(AlarmManager.RTC, System.currentTimeMillis() + 250, pendingIntent);
		System.exit(0);
	}
}
