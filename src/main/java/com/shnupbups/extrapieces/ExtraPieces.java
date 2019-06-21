package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.config.EPConfig;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.debug.DebugItem;
import com.shnupbups.extrapieces.register.ModBlocks;
import com.shnupbups.extrapieces.register.ModRecipes;
import com.shnupbups.extrapieces.register.ModTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class ExtraPieces implements ModInitializer {
	public static Map<PieceType, ItemGroup> groups = new HashMap<PieceType, ItemGroup>();

	@Override
	public void onInitialize() {
		ModRecipes.init();
		for (PieceType p : PieceType.getTypesNoBase()) {
			groups.put(p, FabricItemGroupBuilder.create(p.getId()).icon(() -> new ItemStack(PieceSets.registry.getOrDefault(Blocks.TERRACOTTA, ModBlocks.DUMMY_PIECES).getPiece(p))).build());
		}
		ModBlocks.init();
		EPConfig.init();
		Registry.register(Registry.ITEM, new Identifier("extrapieces", "debug_item"), new DebugItem());
		ModTags.init();
	}
}
