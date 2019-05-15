package com.shnupbups.extrapieces;

import net.minecraft.util.StringRepresentable;

public enum SidingType implements StringRepresentable {
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
