package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.api.EPInitializer;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.debug.DebugItem;
import com.shnupbups.extrapieces.register.ModBlocks;
import com.shnupbups.extrapieces.register.ModConfigs;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ExtraPieces implements ModInitializer {
	public static final String mod_id = "extrapieces";
	public static final String mod_name = "Extra Pieces";
	public static final String piece_pack_version = "2.8.1";
	public static final Logger logger = LogManager.getFormatterLogger(mod_name);

	public static ArtificeResourcePack datapack;

	public static File configDir;
	public static File ppDir;

	public static Identifier getID(String path) {
		return new Identifier(mod_id, path);
	}

	public static void log(String out) {
		logger.info("[" + mod_name + "] " + out);
	}
	
	public static void debugLog(String out) {
		if(ModConfigs.debugOutput) log("[DEBUG] "+out);
	}
	
	public static void moreDebugLog(String out) {
		if(ModConfigs.moreDebugOutput) debugLog(out);
	}

	public static Identifier prependToPath(Identifier id, String prep) {
		return new Identifier(id.getNamespace(), prep + id.getPath());
	}

	public static Identifier appendToPath(Identifier id, String app) {
		return new Identifier(id.getNamespace(), id.getPath() + app);
	}

	public static File getConfigDirectory() {
		if (configDir == null) {
			configDir = new File(FabricLoader.getInstance().getConfigDirectory(), mod_id);
			configDir.mkdirs();
		}
		return configDir;
	}

	public static File getPiecePackDirectory() {
		if (ppDir == null) {
			ppDir = new File(getConfigDirectory(), "piecepacks");
			ppDir.mkdirs();
		}
		return ppDir;
	}

	public static boolean isWoodmillInstalled() {
		return FabricLoader.getInstance().isModLoaded("woodmill");
	}

	public static void dump() {
		if (ModConfigs.dumpData) {
			try {
				datapack.dumpResources(FabricLoader.getInstance().getConfigDirectory().getParent() + "/dump");
			} catch (Exception e) {
				ExtraPieces.debugLog("BIG OOF: " + e.getMessage());
			}
		}
	}

	@Override
	public void onInitialize() {
		ModConfigs.init();
		PieceTypes.init();
		FabricLoader.getInstance().getEntrypoints("extrapieces", EPInitializer.class).forEach(api -> {
			debugLog("EPInitializer " + api.toString());
			api.onInitialize();
		});
		ModConfigs.initPiecePacks();
		datapack = Artifice.registerData(getID("ep_data"), ModBlocks::init);
		Registry.register(Registry.ITEM, getID("debug_item"), new DebugItem());

		ServerStartCallback.EVENT.register(server -> {
			if (ModBlocks.setBuilders.size() != PieceSets.registry.size()) {
				for (PieceSet.Builder psb : ModBlocks.setBuilders.values()) {
					if (!psb.isBuilt())
						server.sendMessage(new LiteralText("Piece Set " + psb.toString() + " could not be built, make sure the base and any vanilla pieces actually exist!"));
				}
			}
		});
	}
}
