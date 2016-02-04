package com.hmc.onegoodday.widgets;

import java.util.Locale;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.method.TransformationMethod;
import android.view.View;

public abstract class OgdWidgetUtils {

	private static Typeface typeface;

	public static Typeface getTypeface(Context context) {
		if (null == typeface) {
			typeface = Typeface.createFromAsset(context.getAssets(), "fonts/mecha.ttf");
		}

		return typeface;
	}

	private static TransformationMethod upperCaseTransformation;

	public static TransformationMethod getUpperCaseTransformation(final Context context) {
		if (null == upperCaseTransformation) {
			upperCaseTransformation = new TransformationMethod() {

				private final Locale locale = context.getResources().getConfiguration().locale;

				@Override
				public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction,
						Rect previouslyFocusedRect) {
				}

				@Override
				public CharSequence getTransformation(CharSequence source, View view) {
					return source != null ? source.toString().toUpperCase(locale) : null;
				}
			};
		}
		return upperCaseTransformation;
	}
}
