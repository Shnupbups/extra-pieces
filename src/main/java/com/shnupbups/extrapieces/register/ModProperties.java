package com.shnupbups.extrapieces.register;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

public class ModProperties {
	public static final EnumProperty<SidingType> SIDING_TYPE;

	static {
		SIDING_TYPE = EnumProperty.of("type", SidingType.class);
	}

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
}
