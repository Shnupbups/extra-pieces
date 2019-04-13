package com.shnupbups.extrapieces;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.Direction;

public class ModProperties {
	public static final EnumProperty<SidingType> SIDING_TYPE;
	public static final EnumProperty<Direction.Axis> AXIS;

	static {
		SIDING_TYPE = EnumProperty.create("type", SidingType.class);
		AXIS = EnumProperty.create("axis", Direction.Axis.class);
	}
}
