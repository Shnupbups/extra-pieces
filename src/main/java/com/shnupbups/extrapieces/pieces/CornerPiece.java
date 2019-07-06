package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.CornerPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;

import java.util.ArrayList;

public class CornerPiece extends PieceType {
	public CornerPiece() {
		super("corner");
	}

	public CornerPieceBlock getNew(PieceSet set) {
		return new CornerPieceBlock(set);
	}

	public ArrayList<ShapedPieceRecipe> getRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
		recipes.add(new ShapedPieceRecipe(this,4,"bbb", "bb ", "b  ").addToKey('b', PieceTypes.BASE));
		return recipes;
	}
}
