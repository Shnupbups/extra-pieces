package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceType;
import io.github.cottonmc.cotton.datapack.tags.TagEntryManager;
import io.github.cottonmc.cotton.datapack.tags.TagType;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.HashMap;

public class ModTags {

	public static void init() {
		HashMap<PieceType, ArrayList<String>> map = new HashMap<>();
		for (PieceSet ps : PieceSets.registry.values()) {
			for (PieceBlock pb : ps.getPieceBlocks()) {
				if (!map.containsKey(pb.getType())) map.put(pb.getType(), new ArrayList<>());
				map.get(pb.getType()).add(Registry.BLOCK.getId(pb.getBlock()).toString());
			}
		}
		for (PieceType pt : map.keySet()) {
			TagEntryManager.getTagLocation(pt.getTagId()).delete();
			TagEntryManager.registerToTag(TagType.BLOCK, pt.getTagId(), map.get(pt).toArray(new String[map.get(pt).size()]));
			TagEntryManager.registerToTag(TagType.ITEM, pt.getTagId(), map.get(pt).toArray(new String[map.get(pt).size()]));
		}
	}
}
