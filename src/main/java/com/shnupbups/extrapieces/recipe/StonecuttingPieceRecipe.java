package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StonecuttingPieceRecipe extends PieceRecipe {
	private PieceType input;

	public StonecuttingPieceRecipe(PieceType output, int count, PieceType input) {
		super(output, count);
		this.input = input;
	}

	public PieceType getInput() {
		return input;
	}

	public Block getInput(PieceSet set) {
		return set.getPiece(getInput());
	}

	public void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addStonecuttingRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(getOutput(set)));
			recipe.count(getCount());
			recipe.ingredientItem(Registry.BLOCK.getId(getInput(set)));
		});
	}
}
