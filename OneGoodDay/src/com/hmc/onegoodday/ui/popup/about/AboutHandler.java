package com.hmc.onegoodday.ui.popup.about;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.ui.popup.Popup;
import com.hmc.onegoodday.ui.popup.PopupHandler;
import com.hmc.onegoodday.ui.popup.PopupView;

public class AboutHandler extends PopupHandler {

	public AboutHandler(Popup popup) {
		super(popup);
	}

	@Override
	public void onShow() {
		popup.setTitle(R.string.app_name);
		popup.setLeftButtonCaption(R.string.ok_button_text);
		popup.hideRightButton();

		String aboutText = popup.getResources().getString(R.string.about);
		aboutText += GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(popup.getActivity()
				.getApplicationContext());

		popup.setQuestDescription(aboutText);

		setCurrentView(PopupView.Quest);
	}

	@Override
	public void onLeftButtonClick() {
		popup.dismiss();
	}

	@Override
	public void onRightButtonClick() {
		throw new IllegalStateException("Can't click on right button in about popup");
	}
}
