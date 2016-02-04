package com.hmc.onegoodday;

import java.util.Locale;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.hmc.onegoodday.listeners.UiListener;
import com.hmc.onegoodday.models.Gender;
import com.hmc.onegoodday.models.Language;
import com.hmc.onegoodday.states.GameState;
import com.hmc.onegoodday.states.LocationState;
import com.hmc.onegoodday.states.PlayerState;

public class App extends Application {

	public static GameState GameState;

	public static LocationState LocationState;

	public static PlayerState PlayerState;

	public static UiListener UIListener;

	@Override
	public void onCreate() {
		super.onCreate();

		GameState = new GameState(this);
		LocationState = new LocationState(this);
		PlayerState = new PlayerState();

		GameState.addTickListener(PlayerState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		setLanguage(getBaseContext());
	}

	public static void setLanguage(Context context) {
		String languageToLoad;
		if (null != App.PlayerState.getLanguage() && App.PlayerState.getLanguage().equals(Language.English)) {
			languageToLoad = "en";
		}
		else if (null != App.PlayerState.getGender() && App.PlayerState.getGender().equals(Gender.Female)) {
			languageToLoad = "rs";
		}
		else {
			languageToLoad = "xx";
		}

		Locale locale = new Locale(languageToLoad);
		Locale.setDefault(locale);
		Resources resources = context.getResources();
		android.content.res.Configuration configuration = resources.getConfiguration();
		configuration.locale = locale;
		resources.updateConfiguration(configuration, resources.getDisplayMetrics());
	}
}
