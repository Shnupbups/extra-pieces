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
				if(pi.isTag()) recipe.ingredientTag(pi.getId(set));
				else recipe.ingredientItem(pi.getId(set));
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
