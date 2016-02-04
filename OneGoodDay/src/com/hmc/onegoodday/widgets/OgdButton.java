package com.hmc.onegoodday.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class OgdButton extends Button {

	public OgdButton(Context context) {
		super(context);
		setFont(context);
		setTransformationMethod(OgdWidgetUtils.getUpperCaseTransformation(context));
	}

	public OgdButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont(context);
		setTransformationMethod(OgdWidgetUtils.getUpperCaseTransformation(context));
	}

	public OgdButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont(context);
		setTransformationMethod(OgdWidgetUtils.getUpperCaseTransformation(context));
	}

	protected void setFont(Context context) {
		if (isInEditMode()) {
			return;
		}

		setTypeface(OgdWidgetUtils.getTypeface(context));
	}
}
