package com.shnupbups.extrapieces;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ExtraPieces implements ModInitializer {
	public static ItemGroup group;

	@Override
	public void onInitialize() {
		ModBlocks.init();
		group = FabricItemGroupBuilder.create(new Identifier("extrapieces","blocks")).icon(() -> new ItemStack(ModBlocks.OAK_SIDING)).build();
		ModItems.init();
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
