package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ShapelessPieceRecipe extends PieceRecipe {
	private PieceIngredient[] inputs;

	public ShapelessPieceRecipe(PieceType output, int count, PieceIngredient... inputs) {
		super(output, count);
		this.inputs = inputs;
	}

	public PieceIngredient[] getInputs() {
		return inputs;
	}

	public void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addShapelessRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(this.getOutput(set)), this.getCount());
			recipe.group(Registry.BLOCK.getId(getOutput(set)));
			for (PieceIngredient pi : getInputs()) {
				recipe.ingredientItem(Registry.ITEM.getId(pi.asItem(set).asItem()));
			}
		});
	}

	@Override
	public boolean canAddForSet(PieceSet set) {
		for (PieceIngredient pi : inputs) {
			if (!pi.hasIngredientInSet(set)) return false;
		}
		return true;
	}
}
