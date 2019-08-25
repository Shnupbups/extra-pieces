package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.api.EPInitializer;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.BiConsumer;

public class ModBlocks {

	public static HashMap<Identifier, PieceSet.Builder> setBuilders = new HashMap<>();
	public static ArrayList<PieceSet.Builder> primedBuilders = new ArrayList<>();

	public static HashMap<Pair<Identifier, Identifier>, Identifier> vanillaPieces = new HashMap<>();

	public static boolean finished = false;

	public static PieceSet PRISMARINE_PIECES;
	public static PieceSet PRISMARINE_BRICK_PIECES;
	public static PieceSet DARK_PRISMARINE_PIECES;
	public static PieceSet OAK_PIECES;
	public static PieceSet SPRUCE_PIECES;
	public static PieceSet BIRCH_PIECES;
	public static PieceSet JUNGLE_PIECES;
	public static PieceSet ACACIA_PIECES;
	public static PieceSet DARK_OAK_PIECES;
	public static PieceSet STONE_PIECES;
	public static PieceSet SMOOTH_STONE_PIECES;
	public static PieceSet SANDSTONE_PIECES;
	public static PieceSet CUT_SANDSTONE_PIECES;
	public static PieceSet PETRIFIED_OAK_PIECES;
	public static PieceSet COBBLESTONE_PIECES;
	public static PieceSet BRICK_PIECES;
	public static PieceSet STONE_BRICK_PIECES;
	public static PieceSet NETHER_BRICK_PIECES;
	public static PieceSet QUARTZ_PIECES;
	public static PieceSet RED_SANDSTONE_PIECES;
	public static PieceSet CUT_RED_SANDSTONE_PIECES;
	public static PieceSet PURPUR_PIECES;
	public static PieceSet POLISHED_GRANITE_PIECES;
	public static PieceSet SMOOTH_RED_SANDSTONE_PIECES;
	public static PieceSet MOSSY_STONE_BRICK_PIECES;
	public static PieceSet POLISHED_DIORITE_PIECES;
	public static PieceSet MOSSY_COBBLESTONE_PIECES;
	public static PieceSet END_STONE_BRICK_PIECES;
	public static PieceSet SMOOTH_SANDSTONE_PIECES;
	public static PieceSet SMOOTH_QUARTZ_PIECES;
	public static PieceSet GRANITE_PIECES;
	public static PieceSet ANDESITE_PIECES;
	public static PieceSet RED_NETHER_BRICK_PIECES;
	public static PieceSet POLISHED_ANDESITE_PIECES;
	public static PieceSet DIORITE_PIECES;
	public static PieceSet NETHERRACK_PIECES;
	public static PieceSet END_STONE_PIECES;
	public static PieceSet QUARTZ_PILLAR_PIECES;
	public static PieceSet CHISELED_QUARTZ_PIECES;
	public static PieceSet CHISELED_SANDSTONE_PIECES;
	public static PieceSet CHISELED_RED_SANDSTONE_PIECES;
	public static PieceSet CRACKED_STONE_BRICK_PIECES;
	public static PieceSet CHISELED_STONE_BRICK_PIECES;
	public static PieceSet PURPUR_PILLAR_PIECES;
	public static PieceSet TERRACOTTA_PIECES;
	public static PieceSet WHITE_TERRACOTTA_PIECES;
	public static PieceSet ORANGE_TERRACOTTA_PIECES;
	public static PieceSet MAGENTA_TERRACOTTA_PIECES;
	public static PieceSet LIGHT_BLUE_TERRACOTTA_PIECES;
	public static PieceSet YELLOW_TERRACOTTA_PIECES;
	public static PieceSet LIME_TERRACOTTA_PIECES;
	public static PieceSet PINK_TERRACOTTA_PIECES;
	public static PieceSet GRAY_TERRACOTTA_PIECES;
	public static PieceSet LIGHT_GRAY_TERRACOTTA_PIECES;
	public static PieceSet CYAN_TERRACOTTA_PIECES;
	public static PieceSet PURPLE_TERRACOTTA_PIECES;
	public static PieceSet BLUE_TERRACOTTA_PIECES;
	public static PieceSet BROWN_TERRACOTTA_PIECES;
	public static PieceSet GREEN_TERRACOTTA_PIECES;
	public static PieceSet RED_TERRACOTTA_PIECES;
	public static PieceSet BLACK_TERRACOTTA_PIECES;
	public static PieceSet WHITE_CONCRETE_PIECES;
	public static PieceSet ORANGE_CONCRETE_PIECES;
	public static PieceSet MAGENTA_CONCRETE_PIECES;
	public static PieceSet LIGHT_BLUE_CONCRETE_PIECES;
	public static PieceSet YELLOW_CONCRETE_PIECES;
	public static PieceSet LIME_CONCRETE_PIECES;
	public static PieceSet PINK_CONCRETE_PIECES;
	public static PieceSet GRAY_CONCRETE_PIECES;
	public static PieceSet LIGHT_GRAY_CONCRETE_PIECES;
	public static PieceSet CYAN_CONCRETE_PIECES;
	public static PieceSet PURPLE_CONCRETE_PIECES;
	public static PieceSet BLUE_CONCRETE_PIECES;
	public static PieceSet BROWN_CONCRETE_PIECES;
	public static PieceSet GREEN_CONCRETE_PIECES;
	public static PieceSet RED_CONCRETE_PIECES;
	public static PieceSet BLACK_CONCRETE_PIECES;
	public static PieceSet GOLD_PIECES;
	public static PieceSet IRON_PIECES;
	public static PieceSet DIAMOND_PIECES;
	public static PieceSet EMERALD_PIECES;
	public static PieceSet REDSTONE_PIECES;
	public static PieceSet COAL_PIECES;
	public static PieceSet LAPIS_LAZULI_PIECES;
	public static PieceSet PACKED_ICE_PIECES;
	public static PieceSet WHITE_WOOL_PIECES;
	public static PieceSet ORANGE_WOOL_PIECES;
	public static PieceSet MAGENTA_WOOL_PIECES;
	public static PieceSet LIGHT_BLUE_WOOL_PIECES;
	public static PieceSet YELLOW_WOOL_PIECES;
	public static PieceSet LIME_WOOL_PIECES;
	public static PieceSet PINK_WOOL_PIECES;
	public static PieceSet GRAY_WOOL_PIECES;
	public static PieceSet LIGHT_GRAY_WOOL_PIECES;
	public static PieceSet CYAN_WOOL_PIECES;
	public static PieceSet PURPLE_WOOL_PIECES;
	public static PieceSet BLUE_WOOL_PIECES;
	public static PieceSet BROWN_WOOL_PIECES;
	public static PieceSet GREEN_WOOL_PIECES;
	public static PieceSet RED_WOOL_PIECES;
	public static PieceSet BLACK_WOOL_PIECES;
	public static PieceSet OBSIDIAN_PIECES;
	public static PieceSet BONE_PIECES;
	public static PieceSet SNOW_PIECES;
	public static PieceSet MAGMA_PIECES;
	public static PieceSet GLOWSTONE_PIECES;
	public static PieceSet NETHER_WART_PIECES;
	public static PieceSet TUBE_CORAL_PIECES;
	public static PieceSet BRAIN_CORAL_PIECES;
	public static PieceSet BUBBLE_CORAL_PIECES;
	public static PieceSet FIRE_CORAL_PIECES;
	public static PieceSet HORN_CORAL_PIECES;
	public static PieceSet OAK_LOG_PIECES;
	public static PieceSet SPRUCE_LOG_PIECES;
	public static PieceSet BIRCH_LOG_PIECES;
	public static PieceSet JUNGLE_LOG_PIECES;
	public static PieceSet ACACIA_LOG_PIECES;
	public static PieceSet DARK_OAK_LOG_PIECES;
	public static PieceSet STRIPPED_OAK_LOG_PIECES;
	public static PieceSet STRIPPED_SPRUCE_LOG_PIECES;
	public static PieceSet STRIPPED_BIRCH_LOG_PIECES;
	public static PieceSet STRIPPED_JUNGLE_LOG_PIECES;
	public static PieceSet STRIPPED_ACACIA_LOG_PIECES;
	public static PieceSet STRIPPED_DARK_OAK_LOG_PIECES;
	public static PieceSet OAK_WOOD_PIECES;
	public static PieceSet SPRUCE_WOOD_PIECES;
	public static PieceSet BIRCH_WOOD_PIECES;
	public static PieceSet JUNGLE_WOOD_PIECES;
	public static PieceSet ACACIA_WOOD_PIECES;
	public static PieceSet DARK_OAK_WOOD_PIECES;
	public static PieceSet STRIPPED_OAK_WOOD_PIECES;
	public static PieceSet STRIPPED_SPRUCE_WOOD_PIECES;
	public static PieceSet STRIPPED_BIRCH_WOOD_PIECES;
	public static PieceSet STRIPPED_JUNGLE_WOOD_PIECES;
	public static PieceSet STRIPPED_ACACIA_WOOD_PIECES;
	public static PieceSet STRIPPED_DARK_OAK_WOOD_PIECES;
	static int built = 0;

	public static void generateDefaultSets() {
		PRISMARINE_PIECES = PieceSets.createDefaultSet(Blocks.PRISMARINE, "prismarine", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.PRISMARINE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.PRISMARINE_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.PRISMARINE_WALL);
		PRISMARINE_BRICK_PIECES = PieceSets.createDefaultSet(Blocks.PRISMARINE_BRICKS, "prismarine_brick", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.PRISMARINE_BRICK_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.PRISMARINE_BRICK_STAIRS);
		DARK_PRISMARINE_PIECES = PieceSets.createDefaultSet(Blocks.DARK_PRISMARINE, "dark_prismarine", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.DARK_PRISMARINE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.DARK_PRISMARINE_STAIRS);
		OAK_PIECES = PieceSets.createDefaultSet(Blocks.OAK_PLANKS, "oak", PieceSet.JUST_EXTRAS_AND_WALL).setUncraftable(PieceTypes.WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.OAK_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.OAK_STAIRS).addVanillaPiece(PieceTypes.FENCE, Blocks.OAK_FENCE).addVanillaPiece(PieceTypes.FENCE_GATE, Blocks.OAK_FENCE_GATE);
		SPRUCE_PIECES = PieceSets.createDefaultSet(Blocks.SPRUCE_PLANKS, "spruce", PieceSet.JUST_EXTRAS_AND_WALL).setUncraftable(PieceTypes.WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.SPRUCE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.SPRUCE_STAIRS).addVanillaPiece(PieceTypes.FENCE, Blocks.SPRUCE_FENCE).addVanillaPiece(PieceTypes.FENCE_GATE, Blocks.SPRUCE_FENCE_GATE);
		BIRCH_PIECES = PieceSets.createDefaultSet(Blocks.BIRCH_PLANKS, "birch", PieceSet.JUST_EXTRAS_AND_WALL).setUncraftable(PieceTypes.WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.BIRCH_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.BIRCH_STAIRS).addVanillaPiece(PieceTypes.FENCE, Blocks.BIRCH_FENCE).addVanillaPiece(PieceTypes.FENCE_GATE, Blocks.BIRCH_FENCE_GATE);
		JUNGLE_PIECES = PieceSets.createDefaultSet(Blocks.JUNGLE_PLANKS, "jungle", PieceSet.JUST_EXTRAS_AND_WALL).setUncraftable(PieceTypes.WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.JUNGLE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.JUNGLE_STAIRS).addVanillaPiece(PieceTypes.FENCE, Blocks.JUNGLE_FENCE).addVanillaPiece(PieceTypes.FENCE_GATE, Blocks.JUNGLE_FENCE_GATE);
		ACACIA_PIECES = PieceSets.createDefaultSet(Blocks.ACACIA_PLANKS, "acacia", PieceSet.JUST_EXTRAS_AND_WALL).setUncraftable(PieceTypes.WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.ACACIA_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.ACACIA_STAIRS).addVanillaPiece(PieceTypes.FENCE, Blocks.ACACIA_FENCE).addVanillaPiece(PieceTypes.FENCE_GATE, Blocks.ACACIA_FENCE_GATE);
		DARK_OAK_PIECES = PieceSets.createDefaultSet(Blocks.DARK_OAK_PLANKS, "dark_oak", PieceSet.JUST_EXTRAS_AND_WALL).setUncraftable(PieceTypes.WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.DARK_OAK_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.DARK_OAK_STAIRS).addVanillaPiece(PieceTypes.FENCE, Blocks.DARK_OAK_FENCE).addVanillaPiece(PieceTypes.FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE);
		STONE_PIECES = PieceSets.createDefaultSet(Blocks.STONE, "stone", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.STONE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.STONE_STAIRS);
		SMOOTH_STONE_PIECES = PieceSets.createDefaultSet(Blocks.SMOOTH_STONE, "smooth_stone", PieceSet.NO_SLAB).addVanillaPiece(PieceTypes.SLAB, Blocks.SMOOTH_STONE_SLAB);
		SANDSTONE_PIECES = PieceSets.createDefaultSet(Blocks.SANDSTONE, "sandstone", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.SANDSTONE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.SANDSTONE_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.SANDSTONE_WALL).setTopTexture("sandstone_top").setBottomTexture("sandstone_bottom");
		CUT_SANDSTONE_PIECES = PieceSets.createDefaultSet(Blocks.CUT_SANDSTONE, "cut_sandstone", PieceSet.NO_SLAB).addVanillaPiece(PieceTypes.SLAB, Blocks.CUT_SANDSTONE_SLAB).setTopTexture("sandstone_top").setBottomTexture("sandstone_bottom");
		PETRIFIED_OAK_PIECES = PieceSets.createDefaultSet(Blocks.PETRIFIED_OAK_SLAB, "petrified_oak", PieceTypes.SIDING).addVanillaPiece(PieceTypes.SLAB, Blocks.PETRIFIED_OAK_SLAB).setTexture("oak_planks").setUncraftable(PieceTypes.SIDING).setInclude();
		COBBLESTONE_PIECES = PieceSets.createDefaultSet(Blocks.COBBLESTONE, "cobblestone", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.COBBLESTONE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.COBBLESTONE_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.COBBLESTONE_WALL);
		BRICK_PIECES = PieceSets.createDefaultSet(Blocks.BRICKS, "brick", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.BRICK_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.BRICK_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.BRICK_WALL);
		STONE_BRICK_PIECES = PieceSets.createDefaultSet(Blocks.STONE_BRICKS, "stone_brick", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.STONE_BRICK_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.STONE_BRICK_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.STONE_BRICK_WALL);
		NETHER_BRICK_PIECES = PieceSets.createDefaultSet(Blocks.NETHER_BRICKS, "nether_brick", PieceSet.JUST_EXTRAS_AND_FENCE_GATE).addVanillaPiece(PieceTypes.SLAB, Blocks.NETHER_BRICK_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.NETHER_BRICK_STAIRS).addVanillaPiece(PieceTypes.FENCE, Blocks.NETHER_BRICK_FENCE).addVanillaPiece(PieceTypes.WALL, Blocks.NETHER_BRICK_WALL);
		QUARTZ_PIECES = PieceSets.createDefaultSet(Blocks.QUARTZ_BLOCK, "quartz", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.QUARTZ_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.QUARTZ_STAIRS).setTexture("quartz_block_side").setTopTexture("quartz_block_top");
		RED_SANDSTONE_PIECES = PieceSets.createDefaultSet(Blocks.RED_SANDSTONE, "red_sandstone", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.RED_SANDSTONE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.RED_SANDSTONE_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.RED_SANDSTONE_WALL).setTopTexture("red_sandstone_top").setBottomTexture("red_sandstone_bottom");
		CUT_RED_SANDSTONE_PIECES = PieceSets.createDefaultSet(Blocks.CUT_RED_SANDSTONE, "cut_red_sandstone", PieceSet.NO_SLAB).addVanillaPiece(PieceTypes.SLAB, Blocks.CUT_RED_SANDSTONE_SLAB).setTopTexture("red_sandstone_top").setBottomTexture("red_sandstone_bottom");
		PURPUR_PIECES = PieceSets.createDefaultSet(Blocks.PURPUR_BLOCK, "purpur", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.PURPUR_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.PURPUR_STAIRS);
		POLISHED_GRANITE_PIECES = PieceSets.createDefaultSet(Blocks.POLISHED_GRANITE, "polished_granite", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.POLISHED_GRANITE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.POLISHED_GRANITE_STAIRS);
		SMOOTH_RED_SANDSTONE_PIECES = PieceSets.createDefaultSet(Blocks.SMOOTH_RED_SANDSTONE, "smooth_red_sandstone", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.SMOOTH_RED_SANDSTONE_STAIRS).setTexture("red_sandstone_top");
		MOSSY_STONE_BRICK_PIECES = PieceSets.createDefaultSet(Blocks.MOSSY_STONE_BRICKS, "mossy_stone_brick", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.MOSSY_STONE_BRICK_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.MOSSY_STONE_BRICK_WALL);
		POLISHED_DIORITE_PIECES = PieceSets.createDefaultSet(Blocks.POLISHED_DIORITE, "polished_diorite", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.POLISHED_DIORITE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.POLISHED_DIORITE_STAIRS);
		MOSSY_COBBLESTONE_PIECES = PieceSets.createDefaultSet(Blocks.MOSSY_COBBLESTONE, "mossy_cobblestone", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.MOSSY_COBBLESTONE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.MOSSY_COBBLESTONE_WALL);
		END_STONE_BRICK_PIECES = PieceSets.createDefaultSet(Blocks.END_STONE_BRICKS, "end_stone_brick", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.END_STONE_BRICK_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.END_STONE_BRICK_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.END_STONE_BRICK_WALL);
		SMOOTH_SANDSTONE_PIECES = PieceSets.createDefaultSet(Blocks.SMOOTH_SANDSTONE, "smooth_sandstone", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.SMOOTH_SANDSTONE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.SMOOTH_SANDSTONE_STAIRS).setTexture("sandstone_top");
		SMOOTH_QUARTZ_PIECES = PieceSets.createDefaultSet(Blocks.SMOOTH_QUARTZ, "smooth_quartz", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.SMOOTH_QUARTZ_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.SMOOTH_QUARTZ_STAIRS).setTexture("quartz_block_bottom");
		GRANITE_PIECES = PieceSets.createDefaultSet(Blocks.GRANITE, "granite", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.GRANITE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.GRANITE_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.GRANITE_WALL);
		ANDESITE_PIECES = PieceSets.createDefaultSet(Blocks.ANDESITE, "andesite", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.ANDESITE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.ANDESITE_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.ANDESITE_WALL);
		RED_NETHER_BRICK_PIECES = PieceSets.createDefaultSet(Blocks.RED_NETHER_BRICKS, "red_nether_brick", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.RED_NETHER_BRICK_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.RED_NETHER_BRICK_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.RED_NETHER_BRICK_WALL);
		POLISHED_ANDESITE_PIECES = PieceSets.createDefaultSet(Blocks.POLISHED_ANDESITE, "polished_andesite", PieceSet.NO_SLAB_OR_STAIRS).addVanillaPiece(PieceTypes.SLAB, Blocks.POLISHED_ANDESITE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.POLISHED_ANDESITE_STAIRS);
		DIORITE_PIECES = PieceSets.createDefaultSet(Blocks.DIORITE, "diorite", PieceSet.NO_SLAB_STAIRS_OR_WALL).addVanillaPiece(PieceTypes.SLAB, Blocks.DIORITE_SLAB).addVanillaPiece(PieceTypes.STAIRS, Blocks.DIORITE_STAIRS).addVanillaPiece(PieceTypes.WALL, Blocks.DIORITE_WALL);
		NETHERRACK_PIECES = PieceSets.createDefaultSet(Blocks.NETHERRACK, "netherrack");
		END_STONE_PIECES = PieceSets.createDefaultSet(Blocks.END_STONE, "end_stone");
		QUARTZ_PILLAR_PIECES = PieceSets.createDefaultSet(Blocks.QUARTZ_PILLAR, "quartz_pillar").setTopTexture("quartz_pillar_top");
		CHISELED_QUARTZ_PIECES = PieceSets.createDefaultSet(Blocks.CHISELED_QUARTZ_BLOCK, "chiseled_quartz").setTopTexture("chiseled_quartz_block_top");
		CHISELED_SANDSTONE_PIECES = PieceSets.createDefaultSet(Blocks.CHISELED_SANDSTONE, "chiseled_sandstone").setTopTexture("sandstone_top").setBottomTexture("sandstone_bottom");
		CHISELED_RED_SANDSTONE_PIECES = PieceSets.createDefaultSet(Blocks.CHISELED_RED_SANDSTONE, "chiseled_red_sandstone").setTopTexture("red_sandstone_top").setBottomTexture("red_sandstone_bottom");
		CRACKED_STONE_BRICK_PIECES = PieceSets.createDefaultSet(Blocks.CRACKED_STONE_BRICKS, "cracked_stone_brick");
		CHISELED_STONE_BRICK_PIECES = PieceSets.createDefaultSet(Blocks.CHISELED_STONE_BRICKS, "chiseled_stone_brick");
		PURPUR_PILLAR_PIECES = PieceSets.createDefaultSet(Blocks.PURPUR_PILLAR, "purpur_pillar").setTopTexture("purpur_pillar_top");
		TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.TERRACOTTA, "terracotta");
		WHITE_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.WHITE_TERRACOTTA, "white_terracotta");
		ORANGE_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.ORANGE_TERRACOTTA, "orange_terracotta");
		MAGENTA_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.MAGENTA_TERRACOTTA, "magenta_terracotta");
		LIGHT_BLUE_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.LIGHT_BLUE_TERRACOTTA, "light_blue_terracotta");
		YELLOW_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.YELLOW_TERRACOTTA, "yellow_terracotta");
		LIME_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.LIME_TERRACOTTA, "lime_terracotta");
		PINK_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.PINK_TERRACOTTA, "pink_terracotta");
		GRAY_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.GRAY_TERRACOTTA, "gray_terracotta");
		LIGHT_GRAY_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.LIGHT_GRAY_TERRACOTTA, "light_gray_terracotta");
		CYAN_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.CYAN_TERRACOTTA, "cyan_terracotta");
		PURPLE_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.PURPLE_TERRACOTTA, "purple_terracotta");
		BLUE_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.BLUE_TERRACOTTA, "blue_terracotta");
		BROWN_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.BROWN_TERRACOTTA, "brown_terracotta");
		GREEN_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.GREEN_TERRACOTTA, "green_terracotta");
		RED_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.RED_TERRACOTTA, "red_terracotta");
		BLACK_TERRACOTTA_PIECES = PieceSets.createDefaultSet(Blocks.BLACK_TERRACOTTA, "black_terracotta");
		WHITE_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.WHITE_CONCRETE, "white_concrete");
		ORANGE_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.ORANGE_CONCRETE, "orange_concrete");
		MAGENTA_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.MAGENTA_CONCRETE, "magenta_concrete");
		LIGHT_BLUE_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.LIGHT_BLUE_CONCRETE, "light_blue_concrete");
		YELLOW_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.YELLOW_CONCRETE, "yellow_concrete");
		LIME_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.LIME_CONCRETE, "lime_concrete");
		PINK_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.PINK_CONCRETE, "pink_concrete");
		GRAY_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.GRAY_CONCRETE, "gray_concrete");
		LIGHT_GRAY_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.LIGHT_GRAY_CONCRETE, "light_gray_concrete");
		CYAN_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.CYAN_CONCRETE, "cyan_concrete");
		PURPLE_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.PURPLE_CONCRETE, "purple_concrete");
		BLUE_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.BLUE_CONCRETE, "blue_concrete");
		BROWN_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.BROWN_CONCRETE, "brown_concrete");
		GREEN_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.GREEN_CONCRETE, "green_concrete");
		RED_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.RED_CONCRETE, "red_concrete");
		BLACK_CONCRETE_PIECES = PieceSets.createDefaultSet(Blocks.BLACK_CONCRETE, "black_concrete");
		GOLD_PIECES = PieceSets.createDefaultSet(Blocks.GOLD_BLOCK, "gold");
		IRON_PIECES = PieceSets.createDefaultSet(Blocks.IRON_BLOCK, "iron");
		DIAMOND_PIECES = PieceSets.createDefaultSet(Blocks.DIAMOND_BLOCK, "diamond");
		EMERALD_PIECES = PieceSets.createDefaultSet(Blocks.EMERALD_BLOCK, "emerald");
		REDSTONE_PIECES = PieceSets.createDefaultSet(Blocks.REDSTONE_BLOCK, "redstone");
		COAL_PIECES = PieceSets.createDefaultSet(Blocks.COAL_BLOCK, "coal");
		LAPIS_LAZULI_PIECES = PieceSets.createDefaultSet(Blocks.LAPIS_BLOCK, "lapis_lazuli");
		PACKED_ICE_PIECES = PieceSets.createDefaultSet(Blocks.PACKED_ICE, "packed_ice");
		WHITE_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.WHITE_WOOL, "white_wool");
		ORANGE_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.ORANGE_WOOL, "orange_wool");
		MAGENTA_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.MAGENTA_WOOL, "magenta_wool");
		LIGHT_BLUE_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.LIGHT_BLUE_WOOL, "light_blue_wool");
		YELLOW_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.YELLOW_WOOL, "yellow_wool");
		LIME_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.LIME_WOOL, "lime_wool");
		PINK_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.PINK_WOOL, "pink_wool");
		GRAY_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.GRAY_WOOL, "gray_wool");
		LIGHT_GRAY_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.LIGHT_GRAY_WOOL, "light_gray_wool");
		CYAN_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.CYAN_WOOL, "cyan_wool");
		PURPLE_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.PURPLE_WOOL, "purple_wool");
		BLUE_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.BLUE_WOOL, "blue_wool");
		BROWN_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.BROWN_WOOL, "brown_wool");
		GREEN_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.GREEN_WOOL, "green_wool");
		RED_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.RED_WOOL, "red_wool");
		BLACK_WOOL_PIECES = PieceSets.createDefaultSet(Blocks.BLACK_WOOL, "black_wool");
		OBSIDIAN_PIECES = PieceSets.createDefaultSet(Blocks.OBSIDIAN, "obsidian");
		BONE_PIECES = PieceSets.createDefaultSet(Blocks.BONE_BLOCK, "bone").setTexture("bone_block_side").setTopTexture("bone_block_top");
		SNOW_PIECES = PieceSets.createDefaultSet(Blocks.SNOW_BLOCK, "snow", PieceSet.NO_LAYER).setTexture("snow").setUncraftable(PieceTypes.SLAB).addVanillaPiece(PieceTypes.LAYER, Blocks.SNOW);
		MAGMA_PIECES = PieceSets.createDefaultSet(Blocks.MAGMA_BLOCK, "magma").setTexture("magma");
		GLOWSTONE_PIECES = PieceSets.createDefaultSet(Blocks.GLOWSTONE, "glowstone");
		NETHER_WART_PIECES = PieceSets.createDefaultSet(Blocks.NETHER_WART_BLOCK, "nether_wart");
		TUBE_CORAL_PIECES = PieceSets.createDefaultSet(Blocks.TUBE_CORAL_BLOCK, "tube_coral");
		BRAIN_CORAL_PIECES = PieceSets.createDefaultSet(Blocks.BRAIN_CORAL_BLOCK, "brain_coral");
		BUBBLE_CORAL_PIECES = PieceSets.createDefaultSet(Blocks.BUBBLE_CORAL_BLOCK, "bubble_coral");
		FIRE_CORAL_PIECES = PieceSets.createDefaultSet(Blocks.FIRE_CORAL_BLOCK, "fire_coral");
		HORN_CORAL_PIECES = PieceSets.createDefaultSet(Blocks.HORN_CORAL_BLOCK, "horn_coral");
		OAK_LOG_PIECES = PieceSets.createDefaultSet(Blocks.OAK_LOG, "oak_log").setTopTexture("oak_log_top");
		SPRUCE_LOG_PIECES = PieceSets.createDefaultSet(Blocks.SPRUCE_LOG, "spruce_log").setTopTexture("spruce_log_top");
		BIRCH_LOG_PIECES = PieceSets.createDefaultSet(Blocks.BIRCH_LOG, "birch_log").setTopTexture("birch_log_top");
		JUNGLE_LOG_PIECES = PieceSets.createDefaultSet(Blocks.JUNGLE_LOG, "jungle_log").setTopTexture("jungle_log_top");
		ACACIA_LOG_PIECES = PieceSets.createDefaultSet(Blocks.ACACIA_LOG, "acacia_log").setTopTexture("acacia_log_top");
		DARK_OAK_LOG_PIECES = PieceSets.createDefaultSet(Blocks.DARK_OAK_LOG, "dark_oak_log").setTopTexture("dark_oak_log_top");
		STRIPPED_OAK_LOG_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_OAK_LOG, "stripped_oak_log").setTopTexture("stripped_oak_log_top");
		STRIPPED_SPRUCE_LOG_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_SPRUCE_LOG, "stripped_spruce_log").setTopTexture("stripped_spruce_log_top");
		STRIPPED_BIRCH_LOG_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_BIRCH_LOG, "stripped_birch_log").setTopTexture("stripped_birch_log_top");
		STRIPPED_JUNGLE_LOG_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_JUNGLE_LOG, "stripped_jungle_log").setTopTexture("stripped_jungle_log_top");
		STRIPPED_ACACIA_LOG_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_ACACIA_LOG, "stripped_acacia_log").setTopTexture("stripped_acacia_log_top");
		STRIPPED_DARK_OAK_LOG_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_DARK_OAK_LOG, "stripped_dark_oak_log").setTopTexture("stripped_dark_oak_log_top");
		OAK_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.OAK_WOOD, "oak_wood").setTexture("oak_log");
		SPRUCE_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.SPRUCE_WOOD, "spruce_wood").setTexture("spruce_log");
		BIRCH_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.BIRCH_WOOD, "birch_wood").setTexture("birch_log");
		JUNGLE_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.JUNGLE_WOOD, "jungle_wood").setTexture("jungle_log");
		ACACIA_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.ACACIA_WOOD, "acacia_wood").setTexture("acacia_log");
		DARK_OAK_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.DARK_OAK_WOOD, "dark_oak_wood").setTexture("dark_oak_log");
		STRIPPED_OAK_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_OAK_WOOD, "stripped_oak_wood").setTexture("stripped_oak_log");
		STRIPPED_SPRUCE_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_SPRUCE_WOOD, "stripped_spruce_wood").setTexture("stripped_spruce_log");
		STRIPPED_BIRCH_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_BIRCH_WOOD, "stripped_birch_wood").setTexture("stripped_birch_log");
		STRIPPED_JUNGLE_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_JUNGLE_WOOD, "stripped_jungle_wood").setTexture("stripped_jungle_log");
		STRIPPED_ACACIA_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_ACACIA_WOOD, "stripped_acacia_wood").setTexture("stripped_acacia_log");
		STRIPPED_DARK_OAK_WOOD_PIECES = PieceSets.createDefaultSet(Blocks.STRIPPED_DARK_OAK_WOOD, "stripped_dark_oak_wood").setTexture("stripped_dark_oak_log");
		ExtraPieces.log("Generated Default Sets");
	}

	public static void registerSet(PieceSet.Builder psb) {
		if (!setBuilders.containsKey(psb.getBaseID())) {
			for (Pair<Identifier, Identifier> pair : vanillaPieces.keySet()) {
				if (pair.getLeft().equals(psb.getBaseID())) {
					psb.addVanillaPiece(pair.getRight(), vanillaPieces.get(pair));
				}
			}
			setBuilders.put(psb.getBaseID(), psb);
		} else {
			ExtraPieces.log("Piece Pack " + psb.packName + " tried to register a set for " + psb.getBaseID() + " when one already exists! Skipping...");
		}
	}

	public static void registerVanillaPiece(Identifier base, Identifier type, Identifier piece) {
		if (setBuilders.containsKey(base)) {
			setBuilders.get(base).addVanillaPiece(type, piece);
		} else {
			vanillaPieces.put(new Pair<>(base, type), piece);
		}
	}

	public static void init(ArtificeResourcePack.ServerResourcePackBuilder data) {
		visitRegistry(Registry.BLOCK, (id, block) -> {
			Iterator<PieceSet.Builder> primed = primedBuilders.iterator();
			PieceSet.Builder builder;

			while (primed.hasNext()) {
				builder = primed.next();

				if (!builder.isBuilt() && builder.isReady()) {
					builder.build().register(data);
					primed.remove();
				}
			}

			PieceSet.Builder current = setBuilders.get(id);

			if (current != null && !current.isBuilt()) {
				if (current.isReady()) {
					current.build().register(data);
				} else {
					ExtraPieces.log("Piece Set Builder" + current + " not yet ready! Deferring to delayed build...");
					primedBuilders.add(setBuilders.get(id));
				}
			}

			if (!finished && isDone()) {
				ExtraPieces.log("Done! All sets built!");
				finish(data);
				ExtraPieces.log("Registered all PieceSets!");
			}
		});
	}

	public static void finish(ArtificeResourcePack.ServerResourcePackBuilder data) {
		ModRecipes.init(data);
		ModLootTables.init(data);
		ModTags.init(data);
		FabricLoader.getInstance().getEntrypoints("extrapieces", EPInitializer.class).forEach(api -> {
			api.addData(data);
		});

		ModBlocks.finished = true;
	}

	public static boolean isDone() {
		if (ModBlocks.finished) return true;
		boolean done = primedBuilders.isEmpty();

		if (done) {
			int built = 0;
			for (PieceSet.Builder psb : setBuilders.values()) {
				if (!psb.isBuilt()) {
					done = false;
				} else built++;
			}
			if (built != ModBlocks.built) {
				//ExtraPieces.log(built+"/"+setBuilders.size());
				ModBlocks.built = built;
			}
		}

		return done;
	}

	public static <T> void visitRegistry(Registry<T> registry, BiConsumer<Identifier, T> visitor) {
		registry.getIds().forEach(id -> visitor.accept(id, registry.get(id)));
		RegistryEntryAddedCallback.event(registry).register((index, identifier, entry) -> visitor.accept(identifier, entry));
	}
}
