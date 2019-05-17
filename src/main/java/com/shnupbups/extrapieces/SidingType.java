package com.shnupbups.extrapieces;

import net.minecraft.util.StringIdentifiable;

public enum SidingType implements StringIdentifiable {
	SINGLE("single"),
	DOUBLE("double");

	private final String name;

	SidingType(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public String asString() {
		return this.name;
	}
}
