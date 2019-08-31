package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.LayerPieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class LayerPiece extends PieceType {
	public LayerPiece() {
		super("layer");
	}

	public LayerPieceBlock getNew(PieceSet set) {
		return new LayerPieceBlock(set);
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 12, "bbb").addToKey('b', PieceTypes.SLAB));
		return recipes;
	}

	public void addBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		addBlockModel(pack, pb, "height_2");
		addBlockModel(pack, pb, "height_4");
		addBlockModel(pack, pb, "height_6");
		addBlockModel(pack, pb, "height_8");
		addBlockModel(pack, pb, "height_10");
		addBlockModel(pack, pb, "height_12");
		addBlockModel(pack, pb, "height_14");
		addBlockModel(pack, pb, "height_16");
	}

	public void addItemModel(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addItemModel(Registry.BLOCK.getId(pb.getBlock()), model -> {
			model.parent(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_height_2"), "block/"));
		});
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for(Direction dir : Direction.values()) {
				for (int i = 1; i <= 8; i++) {
					final int j = i * 2;
					state.variant("facing="+dir.asString()+",layers=" + i, var -> {
						var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_height_" + j), "block/"));
						var.uvlock(true);
						switch(dir) {
							case DOWN:
								var.rotationX(180);
								return;
							case NORTH:
								var.rotationX(90);
								return;
							case SOUTH:
								var.rotationX(90);
								var.rotationY(180);
								return;
							case EAST:
								var.rotationX(90);
								var.rotationY(90);
								return;
							case WEST:
								var.rotationX(90);
								var.rotationY(270);
						}
					});
				}
			}
		});
	}

	public int getStonecuttingCount() {
		return 8;
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
					for (int i = 1; i <= 8; i++) {
						final int j = i;
						entry.function(new Identifier("set_count"), func -> {
							func.add("count", j);
							func.condition(new Identifier("block_state_property"), cond -> {
								cond.add("block", Registry.BLOCK.getId(pb.getBlock()).toString());
								cond.addObject("properties", prop -> {
									prop.add("layers", j);
								});
							});
						});
					}
					entry.function(new Identifier("explosion_decay"), cond -> {
					});
				});
			});
		});
	}
}
