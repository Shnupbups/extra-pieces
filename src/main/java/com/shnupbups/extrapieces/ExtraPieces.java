package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.config.EPConfig;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.debug.DebugItem;
import com.shnupbups.extrapieces.register.ModBlocks;
import com.shnupbups.extrapieces.register.ModLootTables;
import com.shnupbups.extrapieces.register.ModRecipes;
import com.shnupbups.extrapieces.register.ModTags;
import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ExtraPieces implements ModInitializer {
	public static final String mod_id = "extrapieces";
	public static final String mod_name = "Extra Pieces";
	public static final Logger logger = LogManager.getFormatterLogger(mod_name);

	public static Map<PieceType, ItemGroup> groups = new HashMap<PieceType, ItemGroup>();

	@Override
	public void onInitialize() {
		for (PieceType p : PieceType.getTypesNoBase()) {
			groups.put(p, FabricItemGroupBuilder.create(p.getId()).icon(() -> new ItemStack(PieceSets.registry.getOrDefault(Blocks.TERRACOTTA, ModBlocks.DUMMY_PIECES).getPiece(p))).build());
		}
		ModBlocks.init();
		EPConfig.init();
		Registry.register(Registry.ITEM, getID("debug_item"), new DebugItem());
		Artifice.registerData(getID("ep_data"), data -> {
			ModTags.init(data);
			ModRecipes.init(data);
			ModLootTables.init(data);
		});
	}

	public static Identifier getID(String path) {
		return new Identifier(mod_id, path);
	}

	public static void log(String out) {
		logger.info("["+mod_name+"] "+out);
	}
}
