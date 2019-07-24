package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.SidingPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.shnupbups.extrapieces.register.ModProperties;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class SidingPiece extends PieceType {
	public SidingPiece() {
		super("siding");
	}

	public SidingPieceBlock getNew(PieceSet set) {
		return new SidingPieceBlock(set);
	}

	public ArrayList<ShapedPieceRecipe> getRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
		recipes.add(new ShapedPieceRecipe(getId(), this, 6, "b", "b", "b").addToKey('b', PieceTypes.BASE));
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
		addBlockModel(pack, pb, "double");
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for (ModProperties.SidingType t : ModProperties.SidingType.values()) {
				switch (t) {
					case SINGLE:
						for (Direction d : Direction.values()) {
							if (!(d.equals(Direction.DOWN) || d.equals(Direction.UP))) {
								state.variant("type=" + t.asString() + ",facing=" + d.asString(), var -> {
									var.uvlock(true);
									var.model(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "block/"));
									switch (d) {
										case EAST:
											var.rotationY(90);
											break;
										case WEST:
											var.rotationY(270);
											break;
										case SOUTH:
											var.rotationY(180);
											break;
									}
								});
							}
						}
						break;
					case DOUBLE:
						state.variant("type=" + t.asString(), var -> {
							var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_double"), "block/"));
						});
						break;
				}

			}
		});
	}
}
