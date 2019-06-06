package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.SidingType;
import net.minecraft.state.property.EnumProperty;

public class ModProperties {
	public static final EnumProperty<SidingType> SIDING_TYPE;

	static {
		SIDING_TYPE = EnumProperty.of("type", SidingType.class);
	}
}
