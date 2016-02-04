package com.hmc.onegoodday.ui.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.ui.dialog.OgdDialog;

public abstract class FragmentActivityBase extends FragmentActivity {

	private boolean isGpsPromptVisible;

	@Override
	protected void onResume() {
		super.onResume();

		checkGooglePlayServices();
	}

	protected void checkGooglePlayServices() {
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

	protected void checkGpsState() {
		if (isGpsPromptVisible) {
			return;
		}

		LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

			final OgdDialog dialog = new OgdDialog();
			dialog.setTitle(R.string.enable_gps_title);
			dialog.setMessage(R.string.enable_gps_prompt);
			dialog.setPositiveButton(R.string.yes_button_text, new OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					dialog.dismiss();
					isGpsPromptVisible = false;
				}
			});
			dialog.setNegativeButton(R.string.no_button_text, new OnClickListener() {
				@Override
				public void onClick(View view) {
					dialog.dismiss();
					isGpsPromptVisible = false;
				}
			});

			dialog.show(getSupportFragmentManager());
			isGpsPromptVisible = true;
		}
	}
}
