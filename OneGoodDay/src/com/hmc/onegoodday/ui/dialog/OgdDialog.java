package com.hmc.onegoodday.ui.dialog;

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

import com.hmc.onegoodday.R;

public class OgdDialog extends DialogFragment {

	private static final String tag = "OgdDialogFragment";

	private int title;

	private int message;

	private int positiveButton;

	private int negativeButton;

	private OnClickListener positiveButtonListener;

	private OnClickListener negativeButtonListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		View view = inflater.inflate(R.layout.dialog, container);
		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.questDescription)).setText(message);
		Button pb = (Button) view.findViewById(R.id.leftButton);
		pb.setText(positiveButton);
		pb.setOnClickListener(positiveButtonListener);
		Button nb = (Button) view.findViewById(R.id.rightButton);
		nb.setText(negativeButton);
		nb.setOnClickListener(negativeButtonListener);

		return view;
	}

	public void setTitle(int resid) {
		title = resid;
	}

	public void setMessage(int resid) {
		message = resid;
	}

	public void setPositiveButton(int resid, OnClickListener listener) {
		positiveButton = resid;
		positiveButtonListener = listener;
	}

	public void setNegativeButton(int resid, OnClickListener listener) {
		negativeButton = resid;
		negativeButtonListener = listener;
	}

	public void show(FragmentManager manager) {
		show(manager, tag);
	}
}
