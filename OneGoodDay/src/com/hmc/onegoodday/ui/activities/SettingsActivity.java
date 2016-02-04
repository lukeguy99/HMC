package com.hmc.onegoodday.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hmc.onegoodday.App;
import com.hmc.onegoodday.R;
import com.hmc.onegoodday.models.Gender;
import com.hmc.onegoodday.models.Language;

public class SettingsActivity extends FragmentActivityBase {

	private boolean isGenderSelected;
	private Gender selectedGender;
	private boolean isLanguageSelected;
	private Language selectedLanguage;

	private Button maleButton;
	private ImageView maleImage;
	private Button femaleButton;
	private ImageView femaleImage;
	private Button englishButton;
	private ImageView englishImage;
	private Button serbianButton;
	private ImageView serbianImage;

	private Button okButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		maleButton = (Button) findViewById(R.id.playerMaleButton);
		maleImage = (ImageView) findViewById(R.id.playerMaleImage);
		femaleButton = (Button) findViewById(R.id.playerFemaleButton);
		femaleImage = (ImageView) findViewById(R.id.playerFemaleImage);
		englishButton = (Button) findViewById(R.id.languageEnglishButton);
		englishImage = (ImageView) findViewById(R.id.languageEnglishImage);
		serbianButton = (Button) findViewById(R.id.languageSerbianButton);
		serbianImage = (ImageView) findViewById(R.id.languageSerbianImage);
		okButton = (Button) findViewById(R.id.okButton);
	}

	public void onGenderClick(View view) {
		if (view.equals(maleButton) || view.equals(maleImage)) {
			femaleButton.setText(null);
			maleButton.setText(R.string.checkboxText);
			selectedGender = Gender.Male;
			isGenderSelected = true;
		} else if (view.equals(femaleButton) || view.equals(femaleImage)) {
			maleButton.setText(null);
			femaleButton.setText(R.string.checkboxText);
			selectedGender = Gender.Female;
			isGenderSelected = true;
		}
		else {
			throw new IllegalStateException("Unknown view in onGenderClick");
		}

		if (isLanguageSelected) {
			okButton.setEnabled(true);
		}
	}

	public void onLanguageClick(View view) {
		if (view.equals(serbianButton) || view.equals(serbianImage)) {
			englishButton.setText(null);
			serbianButton.setText(R.string.checkboxText);
			selectedLanguage = Language.Serbian;
			isLanguageSelected = true;
		} else if (view.equals(englishButton) || view.equals(englishImage)) {
			serbianButton.setText(null);
			englishButton.setText(R.string.checkboxText);
			selectedLanguage = Language.English;
			isLanguageSelected = true;
		}
		else {
			throw new IllegalStateException("Unknown view in onLanguageClick");
		}

		if (isGenderSelected) {
			okButton.setEnabled(true);
		}
	}

	public void onOkClick(View view) {
		App.PlayerState.setGender(selectedGender);
		App.PlayerState.setLanguage(selectedLanguage);
		App.setLanguage(getApplicationContext());

		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
