package com.hmc.onegoodday.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class OgdTextView extends TextView {

	public OgdTextView(Context context) {
		super(context);
		setFont(context);
	}

	public OgdTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont(context);
	}

	public OgdTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont(context);
	}

	protected void setFont(Context context) {
		if (isInEditMode()) {
			return;
		}

		setTypeface(OgdWidgetUtils.getTypeface(context));
	}
}
