package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceType;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ModItemGroups {

	public static final Map<PieceType, ItemGroup> groups = new HashMap<PieceType, ItemGroup>();

	public static ItemGroup getItemGroup(PieceType type) {
		return groups.get(type);
	}

	public static ItemGroup getItemGroup(PieceType type, Block icon) {
		if (hasItemGroup(type)) return getItemGroup(type);
		return createItemGroup(type, icon);
	}

	public static ItemGroup getItemGroup(PieceBlock pb) {
		return getItemGroup(pb.getType(), pb.getBlock());
	}

	public static boolean hasItemGroup(PieceType type) {
		return groups.containsKey(type);
	}

	public static ItemGroup createItemGroup(PieceType type, Block icon) {
		return groups.put(type, FabricItemGroupBuilder.create(type.getId()).icon(() -> new ItemStack(icon)).build());
	}
}
