package com.hmc.onegoodday.models.infoWindows;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hmc.onegoodday.R;

public class SimpleInfoWindow {

	protected final View infoWindow;

	protected final TextView nameView;

	public SimpleInfoWindow(Context context) {
		this(context, R.layout.info_window_simple);
	}

	protected SimpleInfoWindow(Context context, int layoutResid) {
		infoWindow = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layoutResid,
				null);
		nameView = (TextView) infoWindow.findViewById(R.id.name);
	}

	public View getInfoWindow(int name) {
		nameView.setText(name);

		return infoWindow;
	}
}
