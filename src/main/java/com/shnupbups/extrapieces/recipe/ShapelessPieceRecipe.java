package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ShapelessPieceRecipe extends PieceRecipe {
	private PieceType[] inputs;

	public ShapelessPieceRecipe(PieceType output, int count, PieceType... inputs) {
		super(output, count);
		this.inputs=inputs;
	}

	public PieceType[] getInputs() {
		return inputs;
	}

	public void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addShapelessRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(this.getOutput(set)), this.getCount());
			for(PieceType pt:getInputs()) {
				recipe.ingredientItem(Registry.BLOCK.getId(set.getPiece(pt)));
			}
		});
	}
}
