package com.hmc.onegoodday.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.hmc.onegoodday.R;

public class NumericTextView extends OgdTextView {

	private String format = "%d";

	private int[] valueScale = new int[] { 0, Integer.MAX_VALUE };

	private int value;

	public NumericTextView(Context context) {
		super(context);
	}

	public NumericTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		getCustomAttributes(context, attrs);
	}

	public NumericTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		getCustomAttributes(context, attrs);
	}

	private void getCustomAttributes(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumericTextView, 0, 0);
		String format = a.getString(R.styleable.NumericTextView_format);
		if (null != format) {
			this.format = format;
		}
		String valueScale = a.getString(R.styleable.NumericTextView_valueScale);
		if (null != valueScale) {
			setValueScale(valueScale);
		}
	}

	public void setFormat(String format) {
		this.format = format;
		redraw();
	}

	public String getFormat() {
		return format;
	}

	private void setValueScale(String valueScale) {
		String[] values = valueScale.split(",");
		this.valueScale = new int[values.length + 1];
		for (int i = 0; i < values.length; i++) {
			this.valueScale[i] = Integer.parseInt(values[i]);
		}
		this.valueScale[values.length] = Integer.MAX_VALUE;
	}

	public void setValueScale(int[] valueScale) {
		this.valueScale = valueScale;
		redraw();
	}

	public int[] getValueScale() {
		return valueScale;
	}

	public void setValue(int value) {
		this.value = value;
		redraw();
	}

	private void redraw() {
		for (int i = 0; i < valueScale.length; i++) {
			if (value <= valueScale[i]) {
				getCompoundDrawables()[0].setLevel(i);
				break;
			}
		}

		setText(String.format(format, value));
	}
}
