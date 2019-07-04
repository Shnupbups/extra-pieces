package com.shnupbups.extrapieces.core;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class ShapelessPieceRecipe {
	public PieceType output;
	public int count;
	public PieceType[] inputs;

	public ShapelessPieceRecipe(PieceType output, int count, PieceType... inputs) {
		this.output=output;
		this.count=count;
		this.inputs=inputs;
	}

	public PieceType[] getInputs() {
		return inputs;
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

	public ShapelessPieceRecipe add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addShapelessRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(this.getOutput(set)), this.getCount());
			for(PieceType pt:getInputs()) {
				recipe.ingredientItem(Registry.BLOCK.getId(set.getPiece(pt)));
			}
		});
		return this;
	}
}
