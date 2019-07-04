package com.shnupbups.extrapieces.core;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class StonecuttingPieceRecipe {
	public PieceType output;
	public int count;
	public PieceType input;

	public StonecuttingPieceRecipe(PieceType output, int count, PieceType input) {
		this.output=output;
		this.count=count;
		this.input=input;
	}

	public PieceType getInput() {
		return input;
	}

	public PieceType getOutput() {
		return output;
	}

	public int getCount() {
		return count;
	}

	public Block getOutput(PieceSet set) {
		return set.getPiece(getOutput());
	}

	public Block getInput(PieceSet set) {
		return set.getPiece(getInput());
	}

	public StonecuttingPieceRecipe add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addStonecuttingRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(getOutput(set)));
			recipe.count(getCount());
			recipe.ingredientItem(Registry.BLOCK.getId(getInput(set)));
		});
		return this;
	}
}
