package com.shnupbups.extrapieces;

import com.google.common.collect.UnmodifiableIterator;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;

public class ModBlocks {

	public static Block PRISMARINE_SIDING;
	public static Block PRISMARINE_BRICK_SIDING;
	public static Block DARK_PRISMARINE_SIDING;
	public static Block OAK_SIDING;
	public static Block SPRUCE_SIDING;
	public static Block BIRCH_SIDING;
	public static Block JUNGLE_SIDING;
	public static Block ACACIA_SIDING;
	public static Block DARK_OAK_SIDING;
	public static Block STONE_SIDING;
	public static Block SMOOTH_STONE_SIDING;
	public static Block SANDSTONE_SIDING;
	public static Block CUT_SANDSTONE_SIDING;
	public static Block PETRIFIED_OAK_SIDING;
	public static Block COBBLESTONE_SIDING;
	public static Block BRICK_SIDING;
	public static Block STONE_BRICK_SIDING;
	public static Block NETHER_BRICK_SIDING;
	public static Block QUARTZ_SIDING;
	public static Block RED_SANDSTONE_SIDING;
	public static Block CUT_RED_SANDSTONE_SIDING;
	public static Block PURPUR_SIDING;
	public static Block POLISHED_GRANITE_SIDING;
	public static Block SMOOTH_RED_SANDSTONE_SIDING;
	public static Block MOSSY_STONE_BRICK_SIDING;
	public static Block POLISHED_DIORITE_SIDING;
	public static Block MOSSY_COBBLESTONE_SIDING;
	public static Block END_STONE_BRICK_SIDING;
	public static Block SMOOTH_SANDSTONE_SIDING;
	public static Block SMOOTH_QUARTZ_SIDING;
	public static Block GRANITE_SIDING;
	public static Block ANDESITE_SIDING;
	public static Block RED_NETHER_BRICK_SIDING;
	public static Block POLISHED_ANDESITE_SIDING;
	public static Block DIORITE_SIDING;

	private static Block register(String string_1, Block block_1) {
		return (Block) Registry.register(Registry.BLOCK, new Identifier("extrapieces",(String)string_1), block_1);
	}

	public static void init() {
		PRISMARINE_SIDING = register("prismarine_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.CYAN).strength(1.5F, 6.0F)));
		PRISMARINE_BRICK_SIDING = register("prismarine_brick_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.DIAMOND).strength(1.5F, 6.0F)));
		DARK_PRISMARINE_SIDING = register("dark_prismarine_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.DIAMOND).strength(1.5F, 6.0F)));
		OAK_SIDING = register("oak_siding", new SidingBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.WOOD).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()));
		SPRUCE_SIDING = register("spruce_siding", new SidingBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.SPRUCE).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()));
		BIRCH_SIDING = register("birch_siding", new SidingBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.SAND).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()));
		JUNGLE_SIDING = register("jungle_siding", new SidingBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.DIRT).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()));
		ACACIA_SIDING = register("acacia_siding", new SidingBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.ORANGE).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()));
		DARK_OAK_SIDING = register("dark_oak_siding", new SidingBlock(FabricBlockSettings.of(Material.WOOD, MaterialColor.BROWN).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD).build()));
		STONE_SIDING = register("stone_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.STONE).strength(2.0F, 6.0F)));
		SMOOTH_STONE_SIDING = register("smooth_stone_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.STONE).strength(2.0F, 6.0F)));
		SANDSTONE_SIDING = register("sandstone_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.SAND).strength(2.0F, 6.0F)));
		CUT_SANDSTONE_SIDING = register("cut_sandstone_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.SAND).strength(2.0F, 6.0F)));
		PETRIFIED_OAK_SIDING = register("petrified_oak_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.WOOD).strength(2.0F, 6.0F)));
		COBBLESTONE_SIDING = register("cobblestone_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.STONE).strength(2.0F, 6.0F)));
		BRICK_SIDING = register("brick_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.RED).strength(2.0F, 6.0F)));
		STONE_BRICK_SIDING = register("stone_brick_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.STONE).strength(2.0F, 6.0F)));
		NETHER_BRICK_SIDING = register("nether_brick_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.NETHER).strength(2.0F, 6.0F)));
		QUARTZ_SIDING = register("quartz_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.QUARTZ).strength(2.0F, 6.0F)));
		RED_SANDSTONE_SIDING = register("red_sandstone_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.ORANGE).strength(2.0F, 6.0F)));
		CUT_RED_SANDSTONE_SIDING = register("cut_red_sandstone_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.ORANGE).strength(2.0F, 6.0F)));
		PURPUR_SIDING = register("purpur_siding", new SidingBlock(Block.Settings.of(Material.STONE, MaterialColor.MAGENTA).strength(2.0F, 6.0F)));
		POLISHED_GRANITE_SIDING = register("polished_granite_siding", new SidingBlock(Block.Settings.copy(Blocks.POLISHED_GRANITE)));
		SMOOTH_RED_SANDSTONE_SIDING = register("smooth_red_sandstone_siding", new SidingBlock(Block.Settings.copy(Blocks.SMOOTH_RED_SANDSTONE)));
		MOSSY_STONE_BRICK_SIDING = register("mossy_stone_brick_siding", new SidingBlock(Block.Settings.copy(Blocks.MOSSY_STONE_BRICKS)));
		POLISHED_DIORITE_SIDING = register("polished_diorite_siding", new SidingBlock(Block.Settings.copy(Blocks.POLISHED_DIORITE)));
		MOSSY_COBBLESTONE_SIDING = register("mossy_cobblestone_siding", new SidingBlock(Block.Settings.copy(Blocks.MOSSY_COBBLESTONE)));
		END_STONE_BRICK_SIDING = register("end_stone_brick_siding", new SidingBlock(Block.Settings.copy(Blocks.END_STONE_BRICKS)));
		SMOOTH_SANDSTONE_SIDING = register("smooth_sandstone_siding", new SidingBlock(Block.Settings.copy(Blocks.SMOOTH_SANDSTONE)));
		SMOOTH_QUARTZ_SIDING = register("smooth_quartz_siding", new SidingBlock(Block.Settings.copy(Blocks.SMOOTH_QUARTZ)));
		GRANITE_SIDING = register("granite_siding", new SidingBlock(Block.Settings.copy(Blocks.GRANITE)));
		ANDESITE_SIDING = register("andesite_siding", new SidingBlock(Block.Settings.copy(Blocks.ANDESITE)));
		RED_NETHER_BRICK_SIDING = register("red_nether_brick_siding", new SidingBlock(Block.Settings.copy(Blocks.RED_NETHER_BRICKS)));
		POLISHED_ANDESITE_SIDING = register("polished_andesite_siding", new SidingBlock(Block.Settings.copy(Blocks.POLISHED_ANDESITE)));
		DIORITE_SIDING = register("diorite_siding", new SidingBlock(Block.Settings.copy(Blocks.DIORITE)));

		Iterator var0 = Registry.BLOCK.iterator();

		while(var0.hasNext()) {
			Block block_1 = (Block)var0.next();
			UnmodifiableIterator var2 = block_1.getStateFactory().getStates().iterator();

			while(var2.hasNext()) {
				BlockState blockState_1 = (BlockState)var2.next();
				blockState_1.initShapeCache();
				Block.STATE_IDS.add(blockState_1);
			}

			block_1.getDropTableId();
		}
	}
}
