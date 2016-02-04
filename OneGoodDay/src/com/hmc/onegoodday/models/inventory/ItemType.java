package com.hmc.onegoodday.models.inventory;

import com.hmc.onegoodday.R;

public enum ItemType {
	Antibiotic(R.drawable.item_antibiotic, R.string.item_name_antibiotic),
	Blessing(R.drawable.item_blessing, R.string.item_name_blessing),
	Bottle(R.drawable.item_bottle, R.string.item_name_bottle),
	Brick(R.drawable.item_brick, R.string.item_name_brick),
	Car(R.drawable.item_car, R.string.item_name_car),
	Certificate(R.drawable.item_certificate, R.string.item_name_certificate),
	Document(R.drawable.item_document, R.string.item_name_document),
	Fame(R.drawable.item_fame, R.string.item_name_fame),
	Hammer(R.drawable.item_hammer, R.string.item_name_hammer),
	Happiness(R.drawable.item_happiness, R.string.do_not_show, true),
	Id(R.drawable.item_id, R.string.item_name_id),
	Kidney(R.drawable.item_kidney, R.string.item_name_kidney),
	LootedCar(R.drawable.item_car, R.string.item_name_looted_car),
	Money(R.drawable.item_money, R.string.do_not_show, true),
	PartyMembership(R.drawable.item_party_membership, R.string.item_name_party_membership),
	Saw(R.drawable.item_saw, R.string.item_name_saw),
	Screwdriver(R.drawable.item_screwdriver, R.string.item_name_screwdriver),
	Sedative(R.drawable.item_sedative, R.string.item_name_sedative),
	StolenBike(R.drawable.item_stolen_bike, R.string.item_name_stolen_bike),
	StolenCable(R.drawable.item_stolen_cable, R.string.item_name_stolen_cable),
	StolenManhole(R.drawable.item_stolen_manhole, R.string.item_name_stolen_manhole),
	Ticket(R.drawable.item_ticket, R.string.item_name_ticket),
	Video(R.drawable.item_video, R.string.item_name_video),
	Vote(R.drawable.item_vote, R.string.item_name_vote);

	public final int image;

	public final int name;

	public final boolean isHidden;

	// TODO: stolen flag and image

	private ItemType(int image, int name) {
		this(image, name, false);
	}

	private ItemType(int image, int name, boolean isHidden) {
		this.image = image;
		this.name = name;
		this.isHidden = isHidden;
	}
}
