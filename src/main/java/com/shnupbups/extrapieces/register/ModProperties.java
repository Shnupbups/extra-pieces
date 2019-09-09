package com.shnupbups.extrapieces.register;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.StringIdentifiable;

public class ModProperties {
	public static final EnumProperty<SidingType> SIDING_TYPE;
	public static final BooleanProperty ONE;
	public static final BooleanProperty TWO;
	public static final BooleanProperty THREE;
	public static final BooleanProperty FOUR;
	public static final IntProperty TRIM_COUNT;

	static {
		SIDING_TYPE = EnumProperty.of("type", SidingType.class);
		ONE = BooleanProperty.of("one");
		TWO = BooleanProperty.of("two");
		THREE = BooleanProperty.of("three");
		FOUR = BooleanProperty.of("four");
		TRIM_COUNT = IntProperty.of("count", 1,  4);
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
