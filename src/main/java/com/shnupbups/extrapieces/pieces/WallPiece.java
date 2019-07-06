package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.WallPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import net.minecraft.util.Identifier;

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
		recipes.add(new ShapedPieceRecipe(this,6,"bbb", "bbb").addToKey('b', PieceTypes.BASE));
		return recipes;
	}
}
