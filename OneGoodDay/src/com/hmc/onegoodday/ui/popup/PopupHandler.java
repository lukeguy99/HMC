package com.hmc.onegoodday.ui.popup;

import android.view.View.OnClickListener;

public abstract class PopupHandler {

	protected final Popup popup;

	protected OnClickListener onDismissedListener;

	private PopupView currentView;

	protected PopupHandler(Popup popup) {
		this.popup = popup;
	}

	public abstract void onShow();

	public abstract void onLeftButtonClick();

	public abstract void onRightButtonClick();

	public void setOnDismissedListener(OnClickListener onDismissedListener) {
		this.onDismissedListener = onDismissedListener;
	}

	public void onBackPressed() {
		if (currentView == PopupView.SpeechBubble) {
			popup.dismiss();
		}
		else {
			onLeftButtonClick();
		}
	}

	protected void setCurrentView(PopupView view) {
		currentView = view;
		popup.navigateToView(view);
	}

	protected PopupView getCurrentView() {
		return currentView;
	}

}
