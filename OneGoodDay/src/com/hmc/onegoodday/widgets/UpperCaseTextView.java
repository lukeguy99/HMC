package com.hmc.onegoodday.widgets;

import android.content.Context;
import android.util.AttributeSet;

public class UpperCaseTextView extends OgdTextView {

	public UpperCaseTextView(Context context) {
		super(context);
		setTransformationMethod(OgdWidgetUtils.getUpperCaseTransformation(context));
	}

	public UpperCaseTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTransformationMethod(OgdWidgetUtils.getUpperCaseTransformation(context));
	}

	public UpperCaseTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setTransformationMethod(OgdWidgetUtils.getUpperCaseTransformation(context));
	}
}
