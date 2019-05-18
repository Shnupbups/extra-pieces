package com.shnupbups.extrapieces;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ExtraPieces implements ModInitializer {
	public static Map<PieceType, ItemGroup> groups = new HashMap<PieceType, ItemGroup>();

	@Override
	public void onInitialize() {
		for(PieceType p : PieceType.getTypesNoBase()) {
			groups.put(p, FabricItemGroupBuilder.create(p.getId()).icon(() -> new ItemStack(ModBlocks.TERRACOTTA_PIECES.getPiece(p))).build());
		}
		ModBlocks.init();
	}
}
