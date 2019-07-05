package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModLootTables {

	public enum LootTableType {
		STANDARD,SLAB_OR_SIDING;

		public void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceBlock pb) {
			switch(this) {
				case SLAB_OR_SIDING:
					data.addLootTable(id, loot -> {
						loot.type(new Identifier("block"));
						loot.pool(pool -> {
							pool.rolls(1);
							pool.entry(entry -> {
								entry.type(new Identifier("item"));
								entry.name(Registry.BLOCK.getId(pb.getBlock()));
								entry.function(new Identifier("set_count"), func -> {
									func.add("count", 2);
									func.condition(new Identifier("block_state_property"), cond -> {
										cond.add("block", Registry.BLOCK.getId(pb.getBlock()).toString());
										cond.addObject("properties", prop -> {
											prop.add("type", "double");
										});
									});
								});
								entry.function(new Identifier("explosion_decay"), cond -> {});
							});
						});
					});
					return;
				case STANDARD:
				default:
					data.addLootTable(id, loot -> {
						loot.type(new Identifier("block"));
						loot.pool(pool -> {
							pool.rolls(1);
							pool.entry(entry -> {
								entry.type(new Identifier("item"));
								entry.name(Registry.BLOCK.getId(pb.getBlock()));
							});
							pool.condition(new Identifier("survives_explosion"), cond -> {});
						});
					});
					return;
			}
		}
	}

	public static void init(ArtificeResourcePack.ServerResourcePackBuilder data) {
		int l = 0;
		for (PieceSet ps : PieceSets.registry.values()) {
			if (ps != ModBlocks.DUMMY_PIECES) {
				for (PieceBlock pb : ps.getPieceBlocks()) {
					if(!ps.isVanillaPiece(pb.getType())) {
						Identifier bid = Registry.BLOCK.getId(pb.getBlock());
						Identifier id = ExtraPieces.getID("blocks/"+bid.getPath());
						pb.getType().getLootTableType().add(data, id, pb);
						l++;
					}
				}
			}
		}
		ExtraPieces.log("Added "+l+" loot tables!");
	}
}
