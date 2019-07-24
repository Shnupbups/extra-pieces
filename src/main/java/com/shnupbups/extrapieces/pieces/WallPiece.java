package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.WallPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class WallPiece extends PieceType {
	public WallPiece() {
		super("wall");
	}

	public WallPieceBlock getNew(PieceSet set) {
		return new WallPieceBlock(set);
	}

	public Identifier getTagId() {
		return new Identifier("minecraft", "walls");
	}

	public ArrayList<ShapedPieceRecipe> getRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
		recipes.add(new ShapedPieceRecipe(getId(), this, 6, "bbb", "bbb").addToKey('b', PieceTypes.BASE));
		return recipes;
	}

	public void addBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "side");
		addBlockModel(pack, pb, "inventory");
	}

	public void addItemModel(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addItemModel(Registry.BLOCK.getId(pb.getBlock()), model -> {
			model.parent(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_inventory"), "block/"));
		});
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			state.multipartCase(caze -> {
				caze.when(Direction.UP.asString(),"true");
				caze.apply(var -> {
					var.model(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "block/"));
				});
			});
			for (Direction d : Direction.values()) {
				if (d != Direction.UP && d != Direction.DOWN) {
					state.multipartCase(caze -> {
						caze.when(d.asString(), "true");
						caze.apply(var -> {
							var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_side"), "block/"));
							var.uvlock(true);
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
					});
				}
			}
		});
	}
}
