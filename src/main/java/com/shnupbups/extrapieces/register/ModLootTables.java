package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

public class ModLootTables {

	static int l;

	public static void init(ArtificeResourcePack.ServerResourcePackBuilder data) {
		for (PieceSet ps : PieceSets.registry.values()) {
			ps.addLootTables(data);
		}
		ExtraPieces.debugLog("Registered " + l + " loot tables!");
	}

	public static void incrementLootTables() {
		l++;
	}
}
