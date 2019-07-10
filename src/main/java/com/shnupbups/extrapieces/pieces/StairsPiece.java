package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.StairsPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class StairsPiece extends PieceType {
	public StairsPiece() {
		super("stairs");
	}

	public StairsPieceBlock getNew(PieceSet set) {
		return new StairsPieceBlock(set);
	}

	public Identifier getTagId() {
		return new Identifier("minecraft", "stairs");
	}

	public ArrayList<ShapedPieceRecipe> getRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
		recipes.add(new ShapedPieceRecipe(getId(), this, 4, "b  ", "bb ", "bbb").addToKey('b', PieceTypes.BASE));
		return recipes;
	}

	public void addBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "inner");
		addBlockModel(pack, pb, "outer");
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for (Direction d : Direction.values()) {
				if (!(d.equals(Direction.DOWN) || d.equals(Direction.UP))) {
					for (BlockHalf h : BlockHalf.values()) {
						for (StairShape s : StairShape.values()) {
							String varname = "facing=" + d.asString() + ",half=" + h.asString() + ",shape=" + s.asString();
							state.variant(varname, var -> {
								var.uvlock(true);
								int y = 0;
								switch (s) {
									case STRAIGHT:
										var.model(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "block/"));
										switch (d) {
											case EAST:
												y = 0;
												break;
											case WEST:
												y = 180;
												break;
											case NORTH:
												y = 270;
												break;
											case SOUTH:
												y = 90;
												break;
										}
										break;
									case OUTER_RIGHT:
										var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_outer"), "block/"));
										switch (h) {
											case BOTTOM:
												switch (d) {
													case EAST:
														y = 0;
														break;
													case WEST:
														y = 180;
														break;
													case NORTH:
														y = 270;
														break;
													case SOUTH:
														y = 90;
														break;
												}
												break;
											case TOP:
												switch (d) {
													case EAST:
														y = 90;
														break;
													case WEST:
														y = 270;
														break;
													case NORTH:
														y = 180;
														break;
													case SOUTH:
														y = 0;
														break;
												}
												break;
										}
										break;
									case OUTER_LEFT:
										var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_outer"), "block/"));
										switch (h) {
											case BOTTOM:
												switch (d) {
													case EAST:
														y = 270;
														break;
													case WEST:
														y = 90;
														break;
													case NORTH:
														y = 0;
														break;
													case SOUTH:
														y = 180;
														break;
												}
												break;
											case TOP:
												switch (d) {
													case EAST:
														y = 0;
														break;
													case WEST:
														y = 180;
														break;
													case NORTH:
														y = 90;
														break;
													case SOUTH:
														y = 270;
														break;
												}
												break;
										}
										break;
									case INNER_RIGHT:
										var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_inner"), "block/"));
										switch (h) {
											case BOTTOM:
												switch (d) {
													case EAST:
														y = 0;
														break;
													case WEST:
														y = 180;
														break;
													case NORTH:
														y = 90;
														break;
													case SOUTH:
														y = 270;
														break;
												}
												break;
											case TOP:
												switch (d) {
													case EAST:
														y = 90;
														break;
													case WEST:
														y = 270;
														break;
													case NORTH:
														y = 180;
														break;
													case SOUTH:
														y = 0;
														break;
												}
												break;
										}
										break;
									case INNER_LEFT:
										var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_inner"), "block/"));
										switch (h) {
											case BOTTOM:
												switch (d) {
													case EAST:
														y = 270;
														break;
													case WEST:
														y = 90;
														break;
													case NORTH:
														y = 0;
														break;
													case SOUTH:
														y = 180;
														break;
												}
												break;
											case TOP:
												switch (d) {
													case EAST:
														y = 0;
														break;
													case WEST:
														y = 180;
														break;
													case NORTH:
														y = 90;
														break;
													case SOUTH:
														y = 270;
														break;
												}
												break;
										}
										break;
								}
								if (h.equals(BlockHalf.TOP)) var.rotationX(180);
								var.rotationY(y);
							});
						}
					}
				}
			}
			state.variant("facing=east,half=bottom,shape=straight", var -> {
				var.model(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "block/"));
			});
			state.variant("facing=west,half=bottom,shape=straight", var -> {
				var.model(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "block/"));
				var.rotationY(180);
				var.uvlock(true);
			});
		});
	}
}
