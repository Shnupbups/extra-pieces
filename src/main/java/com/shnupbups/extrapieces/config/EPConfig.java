package com.shnupbups.extrapieces.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.impl.SyntaxError;
import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.register.ModBlocks;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EPConfig {

	public static boolean generateDefaultPack = true;
	public static boolean forceUpdateDefaultPack = false;
	public static boolean everythingStonecuttable = false;
	private static int setsNum = 0;
	private static int ppSetsNum = 0;

	public static void init() {
		File config = new File(ExtraPieces.getConfigDirectory(), "config.json");
		try (FileReader reader = new FileReader(config)) {
			ExtraPieces.log("Loading config");
			JsonObject cfg = Jankson.builder().build().load(config);
			if (isConfigOutdated(cfg)) {
				updateConfig(cfg, config);
			}
			generateDefaultPack = cfg.get("generateDefaultPack").equals(JsonPrimitive.TRUE);
			forceUpdateDefaultPack = cfg.get("forceUpdateDefaultPack").equals(JsonPrimitive.TRUE);
			everythingStonecuttable = cfg.get("everythingStonecuttable").equals(JsonPrimitive.TRUE);
		} catch (IOException e) {
			generateConfig(config);
		} catch (SyntaxError e) {
			ExtraPieces.log("SyntaxError loading config");
		}
		initPiecePacks();
	}

	public static void initPiecePacks() {
		File ppDir = ExtraPieces.getPiecePackDirectory();
		File defaultPack = new File(ppDir, "default.json");
		if (ppDir.listFiles().length == 0) {
			if (generateDefaultPack) {
				ExtraPieces.log("No piece packs found, generating default!");
				generateDefaultPack(new File(ppDir, "default.json"));
			} else ExtraPieces.log("No piece packs found! Why bother having Extra Pieces installed then?");
		} else {
			if (generateDefaultPack && (!defaultPack.exists() || forceUpdateDefaultPack)) {
				generateDefaultPack(defaultPack);
			}
		}
		for (File f : ppDir.listFiles()) {
			try (FileReader reader = new FileReader(f)) {
				ExtraPieces.log("Loading piece pack " + f.getName());
				JsonObject pp = Jankson.builder().build().load(f);
				for (String s : pp.keySet()) {
					JsonObject jsonSet = (JsonObject) pp.get(s);
					PieceSet.Builder pieceSet = new PieceSet.Builder(s, jsonSet);
					setsNum++;
					ppSetsNum++;
					ModBlocks.registerSet(pieceSet);
				}
				ExtraPieces.log("Generated " + ppSetsNum + " PieceSets from piece pack " + f.getName());
			} catch (IOException e) {
				ExtraPieces.log("IOException loading piece pack " + f.getName());
				ExtraPieces.log(e.getMessage());
			} catch (SyntaxError e) {
				ExtraPieces.log("SyntaxError loading piece pack " + f.getName());
				ExtraPieces.log(e.getCompleteMessage());
			}
			ppSetsNum = 0;
		}
		ExtraPieces.log("Generated " + setsNum + " PieceSets!");
	}

	public static void generateConfig(File config) {
		updateConfig(new JsonObject(), config);
	}

	public static void generateDefaultPack(File pack) {
		if (pack.exists()) pack.delete();
		ModBlocks.generateDefaultSets();
		try (FileWriter writer = new FileWriter(pack)) {
			JsonObject pp = new JsonObject();
			for (PieceSet set : PieceSets.defaults.values()) {
				pp.put(set.getName(), set.toJson());
			}
			writer.write(pp.toJson(false, true));
		} catch (IOException e) {
			ExtraPieces.log("Failed to write to default piece pack!");
			ExtraPieces.log(e.getMessage());
		}
	}

	public static boolean isConfigOutdated(JsonObject cfg) {
		if (!cfg.containsKey("generateDefaultPack")) return true;
		if (!cfg.containsKey("forceUpdateDefaultPack")) return true;
		if (!cfg.containsKey("everythingStonecuttable")) return true;
		return false;
	}

	public static void updateConfig(JsonObject cfg, File config) {
		if (!cfg.containsKey("generateDefaultPack"))
			cfg.put("generateDefaultPack", new JsonPrimitive(generateDefaultPack));
		if (!cfg.containsKey("forceUpdateDefaultPack"))
			cfg.put("forceUpdateDefaultPack", new JsonPrimitive(forceUpdateDefaultPack));
		if (!cfg.containsKey("everythingStonecuttable"))
			cfg.put("everythingStonecuttable", new JsonPrimitive(everythingStonecuttable));
		if (config.exists()) config.delete();
		try (FileWriter writer = new FileWriter(config)) {
			writer.write(cfg.toJson(false, true));
		} catch (IOException e) {
			ExtraPieces.log("Failed to write to config!");
			ExtraPieces.log(e.getMessage());
		}
	}

}
