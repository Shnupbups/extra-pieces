package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WoodmillingPieceRecipe extends PieceRecipe {
	private PieceIngredient input;

	public WoodmillingPieceRecipe(PieceType output, int count, PieceIngredient input) {
		super(output, count);
		this.input = input;
	}

	public WoodmillingPieceRecipe(PieceType output, int count, PieceType input) {
		this(output, count, new PieceIngredient(input));
	}
	
	public WoodmillingPieceRecipe(PieceType output, int count, ItemConvertible input) {
		this(output, count, new PieceIngredient(input));
	}
	
	public WoodmillingPieceRecipe(PieceType output, int count, Tag<Item> input) {
		this(output, count, new PieceIngredient(input));
	}

	public PieceIngredient getInput() {
		return input;
	}

	public void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addStonecuttingRecipe(id, recipe -> {
			recipe.type(new Identifier("woodmill", "woodmilling"));
			recipe.result(Registry.BLOCK.getId(getOutput(set)));
			recipe.group(Registry.BLOCK.getId(getOutput(set)));
			recipe.count(getCount());
			PieceIngredient pi = getInput();
			if(pi.isTag()) recipe.ingredientTag(pi.getId(set));
			else recipe.ingredientItem(pi.getId(set));
		});
	}

	@Override
	public boolean canAddForSet(PieceSet set) {
		return input.hasIngredientInSet(set);
	}
}
