package com.hmc.onegoodday.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hmc.onegoodday.R;

public class IntroActivity extends FragmentActivityBase {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkGpsState();
	}

	public void onNewGameClick(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		finish();
	}
}
