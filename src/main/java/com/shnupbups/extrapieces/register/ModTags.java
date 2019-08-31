package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ModTags {

	public static void init(ArtificeResourcePack.ServerResourcePackBuilder data) {
		HashMap<PieceType, HashSet<Identifier>> map = new HashMap<>();

		for (PieceSet set : PieceSets.registry.values()) {
			for (PieceBlock pieceBlock : set.getPieceBlocks()) {
				HashSet<Identifier> identifiers = map.computeIfAbsent(pieceBlock.getType(), ty -> new HashSet<>());
				Block block = pieceBlock.getBlock();

				if (block instanceof PieceBlock) {
					identifiers.add(Registry.BLOCK.getId(block));
				}
			}
		}

		for (Map.Entry<PieceType, HashSet<Identifier>> entry : map.entrySet()) {
			PieceType type = entry.getKey();
			HashSet<Identifier> identifiers = entry.getValue();

			data.addBlockTag(entry.getKey().getTagId(), tag -> {
				tag.replace(false);
				tag.values(identifiers.toArray(new Identifier[0]));
			});

			data.addItemTag(entry.getKey().getTagId(), tag -> {
				tag.replace(false);
				tag.values(identifiers.toArray(new Identifier[0]));
			});

			ExtraPieces.debugLog("Added block and item tags for " + type.toString() + ", " + identifiers.size() + " entries.");
		}
	}
}
