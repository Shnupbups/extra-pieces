package com.shnupbups.extrapieces.blocks.options;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;

public class Harvesting {

	private final static HashMap<Material, Tag<Item>> HARVEST_TOOLS = new HashMap<Material, Tag<Item>>();
	private final static HashMap<String, Integer> HARVEST_LEVELS = new HashMap<String, Integer>();

	static {
		HARVEST_TOOLS.put(Material.WOOD, FabricToolTags.AXES);
		HARVEST_TOOLS.put(Material.NETHER_WOOD, FabricToolTags.AXES);

		HARVEST_TOOLS.put(Material.AGGREGATE, FabricToolTags.SHOVELS);
		HARVEST_TOOLS.put(Material.SOIL, FabricToolTags.SHOVELS);
		HARVEST_TOOLS.put(Material.SNOW_BLOCK, FabricToolTags.SHOVELS);

		HARVEST_TOOLS.put(Material.LEAVES, FabricToolTags.HOES);
		HARVEST_TOOLS.put(Material.SOLID_ORGANIC, FabricToolTags.HOES);
		HARVEST_TOOLS.put(Material.SPONGE, FabricToolTags.HOES);

		HARVEST_LEVELS.put("Obsidian", 3);
		HARVEST_LEVELS.put("Crying Obsidian", 3);
		HARVEST_LEVELS.put("Ancient Debris", 3);
	}

	public static Tag<Item> getTool(final Block b) {

		final Tag<Item> result = HARVEST_TOOLS.get(b.getDefaultState().getMaterial());
		return (result == null) ? FabricToolTags.PICKAXES : result;
	}

	public static int getLevel(final Block b) {

		final Integer result = HARVEST_LEVELS.get(b.getName().getString());
		return (result == null) ? 0 : result;
	}
}
