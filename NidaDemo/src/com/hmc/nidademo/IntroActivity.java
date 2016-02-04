package com.hmc.nidademo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class IntroActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
	}

	@Override
	protected void onResume() {
		super.onResume();
		checkGooglePlayServices();
		checkGpsState();
	}

	private void checkGooglePlayServices() {
		int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (result != ConnectionResult.SUCCESS) {
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(result, this, 1, new OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			});
			dialog.show();
			return;
		}
	}

	private void checkGpsState() {
		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

			final AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setTitle(R.string.enable_gps_title)
					.setMessage(R.string.enable_gps_prompt)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialogInterface, int id) {
									startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
									dialogInterface.dismiss();
								}
							})
					.setNegativeButton("Continue",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialogInterface, int id) {
									dialogInterface.cancel();
								}
							});
			builder.create().show();
		}
	}

	public void showMainActivity(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
