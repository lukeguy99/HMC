package com.hmc.nidademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public class EndGameActivity extends FragmentActivity {

	public final static String IsSuccess = "com.hmc.nidademo.is_success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);

		Intent intent = getIntent();
		boolean isSuccess = intent.getBooleanExtra(IsSuccess, false);

		TextView content = (TextView) findViewById(R.id.end_game_text);
		content.setText(isSuccess ? R.string.game_end_win : R.string.game_end_lose);
	}

	public void restart(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public void exit(View view) {
		finish();
	}

}
