package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.SlabPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class SlabPiece extends PieceType {
	public SlabPiece() {
		super("slab");
	}

	public SlabPieceBlock getNew(PieceSet set) {
		return new SlabPieceBlock(set);
	}

	public Identifier getTagId() {
		return new Identifier("minecraft", "slabs");
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 6, "bbb").addToKey('b', PieceTypes.BASE));
		return recipes;
	}

	public int getStonecuttingCount() {
		return 2;
	}

	@Override
	public void addLootTable(ArtificeResourcePack.ServerResourcePackBuilder data, PieceBlock pb) {
		data.addLootTable(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "blocks/"), loot -> {
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
					entry.function(new Identifier("explosion_decay"), cond -> {
					});
				});
			});
		});
	}

	public void addBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "top");
		addBlockModel(pack, pb, "double");
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for (SlabType t : SlabType.values()) {
				state.variant("type=" + t.asString(), var -> {
					switch (t) {
						case BOTTOM:
							var.model(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "block/"));
							break;
						case TOP:
							var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_top"), "block/"));
							break;
						case DOUBLE:
							var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_double"), "block/"));
							break;
					}
				});
			}
		});
	}
}
