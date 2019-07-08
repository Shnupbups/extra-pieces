package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;

public class ModTags {

	public static void init(ArtificeResourcePack.ServerResourcePackBuilder data) {
		HashMap<PieceType, ArrayList<Identifier>> map = new HashMap<>();
		for (PieceSet ps : PieceSets.registry.values()) {
			for (PieceBlock pb : ps.getPieceBlocks()) {
				if (!map.containsKey(pb.getType())) map.put(pb.getType(), new ArrayList<>());
				if (pb.getBlock() instanceof PieceBlock) map.get(pb.getType()).add(Registry.BLOCK.getId(pb.getBlock()));
			}
		}
		for (PieceType pt : map.keySet()) {
			data.addBlockTag(pt.getTagId(), tag -> {
				tag.replace(false);
				tag.values(map.get(pt).toArray(new Identifier[map.get(pt).size()]));
			});
			data.addItemTag(pt.getTagId(), tag -> {
				tag.replace(false);
				tag.values(map.get(pt).toArray(new Identifier[map.get(pt).size()]));
			});
			ExtraPieces.log("Added block and item tags for " + pt.toString() + ", " + map.get(pt).size() + " entries.");
		}
	}
}
