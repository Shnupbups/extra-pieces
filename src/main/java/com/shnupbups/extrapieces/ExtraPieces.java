package com.shnupbups.extrapieces;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ExtraPieces implements ModInitializer {
	public static Map<PiecesSet.BlockPiece, ItemGroup> groups = new HashMap<PiecesSet.BlockPiece, ItemGroup>();

	@Override
	public void onInitialize() {
		for(PiecesSet.BlockPiece p : PiecesSet.BlockPiece.values()) {
			groups.put(p, FabricItemGroupBuilder.create(new Identifier("extrapieces",p.getName())).icon(() -> new ItemStack(ModBlocks.TERRACOTTA_PIECES.getPiece(p))).build());
		}
		ModBlocks.init();
	}

	/**
	 * Returns a new instance of SidingBlock based upon any {@link Block}
	 * @param block The block on which to base the SidingBlock
	 * @return A SidingBlock with all the {@link Block.Settings} of the input block
	 */
	public static SidingBlock getSiding(Block block) {
		return new SidingBlock(Block.Settings.copy(block));
	}
}
