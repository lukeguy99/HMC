package com.hmc.nidademo;

public class LocationType {

	public final int totalOilChange;

	public final int collectedOilChange;

	public final int timeChange;

	public final int popupTextId;

	public final int glyphId;

	public LocationType(int totalOilChange, int collectedOilChange, int timeChange, int popupTextId, int glyphId) {
		this.totalOilChange = totalOilChange;
		this.collectedOilChange = collectedOilChange;
		this.timeChange = timeChange;
		this.popupTextId = popupTextId;
		this.glyphId = glyphId;
	}
}