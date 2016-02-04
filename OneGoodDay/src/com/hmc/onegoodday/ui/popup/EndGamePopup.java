package com.hmc.onegoodday.ui.popup;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;

public class EndGamePopup extends DialogFragment {

	private static final String tag = "EndGameDialogFragment";

	private OnClickListener onDismissedListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		setCancelable(false);

		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		View view = inflater.inflate(R.layout.popup_end_game, container);
		setEndGameText(view);

		Button newGameButton = (Button) view.findViewById(R.id.newGameButton);
		newGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				onDismissedListener.onClick(view);
			}
		});

		Button exitGameButton = (Button) view.findViewById(R.id.exitGameButton);
		exitGameButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO: move to main activity
				App.GameState.pauseTime();
				App.LocationState.disconnect();
				System.exit(0);
			}
		});

		return view;
	}

	public void setOnDismissedListener(OnClickListener onDismissedListener) {
		this.onDismissedListener = onDismissedListener;
	}

	public void show(FragmentManager manager) {
		App.GameState.pauseTime();
		App.LocationState.disconnect();
		super.show(manager, tag);
	}

	private void setEndGameText(View view) {
		TextView endGameText = (TextView) view.findViewById(R.id.endGameText);

		// TODO: add end game scenarios to end game screen

		endGameText.setText(R.string.end_game_text);
	}
}
