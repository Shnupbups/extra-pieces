package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.core.PieceType;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ModItemGroups {

	public static Map<PieceType, ItemGroup> groups = new HashMap<PieceType, ItemGroup>();

	public static void init() {
		for (PieceType p : ModBlocks.blocks.keySet()) {
			groups.put(p, FabricItemGroupBuilder.create(p.getId()).icon(() -> new ItemStack(ModBlocks.blocks.get(p).get(0))).build());
		}
	}
}
