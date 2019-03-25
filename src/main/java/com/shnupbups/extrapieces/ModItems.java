package com.shnupbups.extrapieces;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

	public static Item OAK_SIDING;
	public static Item SPRUCE_SIDING;
	public static Item BIRCH_SIDING;
	public static Item JUNGLE_SIDING;
	public static Item ACACIA_SIDING;
	public static Item DARK_OAK_SIDING;
	public static Item STONE_SIDING;
	public static Item SMOOTH_STONE_SIDING;
	public static Item SANDSTONE_SIDING;
	public static Item CUT_SANDSTONE_SIDING;
	public static Item PETRIFIED_OAK_SIDING;
	public static Item COBBLESTONE_SIDING;
	public static Item BRICK_SIDING;
	public static Item STONE_BRICK_SIDING;
	public static Item NETHER_BRICK_SIDING;
	public static Item QUARTZ_SIDING;
	public static Item RED_SANDSTONE_SIDING;
	public static Item CUT_RED_SANDSTONE_SIDING;
	public static Item PURPUR_SIDING;
	public static Item PRISMARINE_SIDING;
	public static Item PRISMARINE_BRICK_SIDING;
	public static Item DARK_PRISMARINE_SIDING;
	public static Item POLISHED_GRANITE_SIDING;
	public static Item SMOOTH_RED_SANDSTONE_SIDING;
	public static Item MOSSY_STONE_BRICK_SIDING;
	public static Item POLISHED_DIORITE_SIDING;
	public static Item MOSSY_COBBLESTONE_SIDING;
	public static Item END_STONE_BRICK_SIDING;
	public static Item SMOOTH_SANDSTONE_SIDING;
	public static Item SMOOTH_QUARTZ_SIDING;
	public static Item GRANITE_SIDING;
	public static Item ANDESITE_SIDING;
	public static Item RED_NETHER_BRICK_SIDING;
	public static Item POLISHED_ANDESITE_SIDING;
	public static Item DIORITE_SIDING;

	private static Item registerBlock(Block block_1) {
		return register(new BlockItem(block_1, new Item.Settings()));
	}

	private static Item registerBlock(Block block_1, ItemGroup itemGroup_1) {
		return register(new BlockItem(block_1, (new Item.Settings()).itemGroup(itemGroup_1)));
	}

	private static Item register(BlockItem blockItem_1) {
		return register((Block)blockItem_1.getBlock(), blockItem_1);
	}

	protected static Item register(Block block_1, Item item_1) {
		return register(Registry.BLOCK.getId(block_1), item_1);
	}

	private static Item register(Identifier identifier_1, Item item_1) {
		if (item_1 instanceof BlockItem) {
			((BlockItem)item_1).registerBlockItemMap(Item.BLOCK_ITEM_MAP, item_1);
		}

		return (Item)Registry.register(Registry.ITEM, (Identifier)identifier_1, item_1);
	}
	
	public static void init() {
		OAK_SIDING = registerBlock(ModBlocks.OAK_SIDING, ExtraPieces.group);
		SPRUCE_SIDING = registerBlock(ModBlocks.SPRUCE_SIDING, ExtraPieces.group);
		BIRCH_SIDING = registerBlock(ModBlocks.BIRCH_SIDING, ExtraPieces.group);
		JUNGLE_SIDING = registerBlock(ModBlocks.JUNGLE_SIDING, ExtraPieces.group);
		ACACIA_SIDING = registerBlock(ModBlocks.ACACIA_SIDING, ExtraPieces.group);
		DARK_OAK_SIDING = registerBlock(ModBlocks.DARK_OAK_SIDING, ExtraPieces.group);
		STONE_SIDING = registerBlock(ModBlocks.STONE_SIDING, ExtraPieces.group);
		SMOOTH_STONE_SIDING = registerBlock(ModBlocks.SMOOTH_STONE_SIDING, ExtraPieces.group);
		SANDSTONE_SIDING = registerBlock(ModBlocks.SANDSTONE_SIDING, ExtraPieces.group);
		CUT_SANDSTONE_SIDING = registerBlock(ModBlocks.CUT_SANDSTONE_SIDING, ExtraPieces.group);
		PETRIFIED_OAK_SIDING = registerBlock(ModBlocks.PETRIFIED_OAK_SIDING, ExtraPieces.group);
		COBBLESTONE_SIDING = registerBlock(ModBlocks.COBBLESTONE_SIDING, ExtraPieces.group);
		BRICK_SIDING = registerBlock(ModBlocks.BRICK_SIDING, ExtraPieces.group);
		STONE_BRICK_SIDING = registerBlock(ModBlocks.STONE_BRICK_SIDING, ExtraPieces.group);
		NETHER_BRICK_SIDING = registerBlock(ModBlocks.NETHER_BRICK_SIDING, ExtraPieces.group);
		QUARTZ_SIDING = registerBlock(ModBlocks.QUARTZ_SIDING, ExtraPieces.group);
		RED_SANDSTONE_SIDING = registerBlock(ModBlocks.RED_SANDSTONE_SIDING, ExtraPieces.group);
		CUT_RED_SANDSTONE_SIDING = registerBlock(ModBlocks.CUT_RED_SANDSTONE_SIDING, ExtraPieces.group);
		PURPUR_SIDING = registerBlock(ModBlocks.PURPUR_SIDING, ExtraPieces.group);
		PRISMARINE_SIDING = registerBlock(ModBlocks.PRISMARINE_SIDING, ExtraPieces.group);
		PRISMARINE_BRICK_SIDING = registerBlock(ModBlocks.PRISMARINE_BRICK_SIDING, ExtraPieces.group);
		DARK_PRISMARINE_SIDING = registerBlock(ModBlocks.DARK_PRISMARINE_SIDING, ExtraPieces.group);
		POLISHED_GRANITE_SIDING = registerBlock(ModBlocks.POLISHED_GRANITE_SIDING, ExtraPieces.group);
		SMOOTH_RED_SANDSTONE_SIDING = registerBlock(ModBlocks.SMOOTH_RED_SANDSTONE_SIDING, ExtraPieces.group);
		MOSSY_STONE_BRICK_SIDING = registerBlock(ModBlocks.MOSSY_STONE_BRICK_SIDING, ExtraPieces.group);
		POLISHED_DIORITE_SIDING = registerBlock(ModBlocks.POLISHED_DIORITE_SIDING, ExtraPieces.group);
		MOSSY_COBBLESTONE_SIDING = registerBlock(ModBlocks.MOSSY_COBBLESTONE_SIDING, ExtraPieces.group);
		END_STONE_BRICK_SIDING = registerBlock(ModBlocks.END_STONE_BRICK_SIDING, ExtraPieces.group);
		SMOOTH_SANDSTONE_SIDING = registerBlock(ModBlocks.SMOOTH_SANDSTONE_SIDING, ExtraPieces.group);
		SMOOTH_QUARTZ_SIDING = registerBlock(ModBlocks.SMOOTH_QUARTZ_SIDING, ExtraPieces.group);
		GRANITE_SIDING = registerBlock(ModBlocks.GRANITE_SIDING, ExtraPieces.group);
		ANDESITE_SIDING = registerBlock(ModBlocks.ANDESITE_SIDING, ExtraPieces.group);
		RED_NETHER_BRICK_SIDING = registerBlock(ModBlocks.RED_NETHER_BRICK_SIDING, ExtraPieces.group);
		POLISHED_ANDESITE_SIDING = registerBlock(ModBlocks.POLISHED_ANDESITE_SIDING, ExtraPieces.group);
		DIORITE_SIDING = registerBlock(ModBlocks.DIORITE_SIDING, ExtraPieces.group);
	}
}
