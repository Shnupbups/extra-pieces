package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.FenceGatePieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class FenceGatePiece extends PieceType {
	public FenceGatePiece() {
		super("fence_gate");
	}

	public FenceGatePieceBlock getNew(PieceSet set) {
		return new FenceGatePieceBlock(set);
	}

	public ArrayList<ShapedPieceRecipe> getShapedRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getShapedRecipes();
		recipes.add(new ShapedPieceRecipe(this, 1, "sbs", "sbs").addToKey('b', PieceTypes.BASE).addToKey('s', Items.STICK));
		return recipes;
	}

	public void addBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "wall");
		addBlockModel(pack, pb, "open");
		addBlockModel(pack, pb, "wall_open");
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for (Direction d : Direction.values()) {
				if (d != Direction.UP && d != Direction.DOWN) {
					state.variant("facing=" + d.asString() + ",in_wall=false,open=false", var -> {
						var.model(getModelPath(pb));
						var.uvlock(true);
						switch (d) {
							case NORTH:
								var.rotationY(180);
								break;
							case WEST:
								var.rotationY(90);
								break;
							case EAST:
								var.rotationY(270);
								break;
						}
					});
					state.variant("facing=" + d.asString() + ",in_wall=true,open=false", var -> {
						var.model(getModelPath(pb, "wall"));
						var.uvlock(true);
						switch (d) {
							case NORTH:
								var.rotationY(180);
								break;
							case WEST:
								var.rotationY(90);
								break;
							case EAST:
								var.rotationY(270);
								break;
						}
					});
					state.variant("facing=" + d.asString() + ",in_wall=false,open=true", var -> {
						var.model(getModelPath(pb, "open"));
						var.uvlock(true);
						switch (d) {
							case NORTH:
								var.rotationY(180);
								break;
							case WEST:
								var.rotationY(90);
								break;
							case EAST:
								var.rotationY(270);
								break;
						}
					});
					state.variant("facing=" + d.asString() + ",in_wall=true,open=true", var -> {
						var.model(getModelPath(pb, "wall_open"));
						var.uvlock(true);
						switch (d) {
							case NORTH:
								var.rotationY(180);
								break;
							case WEST:
								var.rotationY(90);
								break;
							case EAST:
								var.rotationY(270);
								break;
						}
					});
				}
			}
		});
	}
}
