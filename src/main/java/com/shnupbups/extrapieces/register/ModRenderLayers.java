package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;

public class ModRenderLayers {
	public static void init() {
		for (PieceSet ps : PieceSets.registry.values()) {
			ps.addRenderLayers();
		}

		ExtraPieces.debugLog("Added render layers!");
	}
}
