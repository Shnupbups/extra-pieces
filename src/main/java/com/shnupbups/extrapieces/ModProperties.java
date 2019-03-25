package com.shnupbups.extrapieces;

import net.minecraft.state.property.EnumProperty;

public class ModProperties {
	public static final EnumProperty<SidingType> SIDING_TYPE;

	static {
		SIDING_TYPE = EnumProperty.create("type", SidingType.class);
	}
}
