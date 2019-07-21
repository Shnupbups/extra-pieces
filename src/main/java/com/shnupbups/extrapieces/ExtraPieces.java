package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.config.EPConfig;
import com.shnupbups.extrapieces.debug.DebugItem;
import com.shnupbups.extrapieces.register.*;
import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class ExtraPieces implements ModInitializer {
	public static final String mod_id = "extrapieces";
	public static final String mod_name = "Extra Pieces";
	public static final Logger logger = LogManager.getFormatterLogger(mod_name);

	public static File configDir;
	public static File ppDir;

	public static Identifier getID(String path) {
		return new Identifier(mod_id, path);
	}

	public static void log(String out) {
		logger.info("[" + mod_name + "] " + out);
	}

	public static Identifier prependToPath(Identifier id, String prep) {
		return new Identifier(id.getNamespace(), prep + id.getPath());
	}

	public static Identifier appendToPath(Identifier id, String app) {
		return new Identifier(id.getNamespace(), id.getPath() + app);
	}

	@Override
	public void onInitialize() {
		EPConfig.init();
		ModItemGroups.init();
		ModBlocks.init();
		Registry.register(Registry.ITEM, getID("debug_item"), new DebugItem());
		//try {
		Artifice.registerData(getID("ep_data"), data -> {
			ModTags.init(data);
			ModRecipes.init(data);
			ModLootTables.init(data);
		})/*.dumpResources(FabricLoader.getInstance().getConfigDirectory().getParent()+"/dump")*/;
		/*} catch(Exception e) {
			ExtraPieces.log("BIG OOF: "+e.getMessage());
		}*/
	}

	public static File getConfigDirectory() {
		if(configDir==null) {
			configDir = new File(FabricLoader.getInstance().getConfigDirectory(),mod_id);
			configDir.mkdirs();
		}
		return configDir;
	}

	public static File getPiecePackDirectory() {
		if(ppDir==null) {
			ppDir = new File(getConfigDirectory(),"piecepacks");
			ppDir.mkdirs();
		}
		return ppDir;
	}
}
