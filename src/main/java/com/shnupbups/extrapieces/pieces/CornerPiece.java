package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.CornerPieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class CornerPiece extends PieceType {
	public CornerPiece() {
		super("corner");
	}

	public CornerPieceBlock getNew(PieceSet set) {
		return new CornerPieceBlock(set);
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 4, "bbb", "bb ", "b  ").addToKey('b', PieceTypes.BASE));
		return recipes;
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for (Direction d : Direction.values()) {
				if (d != Direction.UP && d != Direction.DOWN) {
					state.variant("facing=" + d.asString(), var -> {
						var.model(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "block/"));
						var.uvlock(true);
						switch (d) {
							case SOUTH:
								var.rotationY(180);
								break;
							case EAST:
								var.rotationY(90);
								break;
							case WEST:
								var.rotationY(270);
								break;
						}
					});
				}
			}
		});
	}
}
