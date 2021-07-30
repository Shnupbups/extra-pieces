package com.shnupbups.extrapieces.recipe;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ShapedPieceRecipe extends PieceRecipe {
	private ListMultimap<Character, PieceIngredient> key = MultimapBuilder.hashKeys().arrayListValues().build();
	private String[] pattern;

	public ShapedPieceRecipe(PieceType output, int count, String... pattern) {
		super(output, count);
		this.pattern = pattern;
	}

	public ShapedPieceRecipe addToKey(char c, PieceType type) {
		return addToKey(c, new PieceIngredient(type));
	}

	public ShapedPieceRecipe addToKey(char c, ItemConvertible item) {
		return addToKey(c, new PieceIngredient(item));
	}
	
	public ShapedPieceRecipe addToKey(char c, Tag.Identified<Item> tag) {
		return addToKey(c, new PieceIngredient(tag));
	}

	public ShapedPieceRecipe addToKey(char c, PieceIngredient ingredient) {
		key.put(c, ingredient);
		return this;
	}

	public Multimap<Character, PieceIngredient> getKey() {
		return key;
	}

	public String[] getPattern() {
		return pattern;
	}

	public List<PieceIngredient> getFromKey(char c) {
		return key.get(c);
	}

	public void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addShapedRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(this.getOutput(set)), this.getCount());
			recipe.group(Registry.BLOCK.getId(getOutput(set)));
			recipe.pattern(this.getPattern());
			for (Map.Entry<Character, Collection<PieceIngredient>> ingredients : this.getKey().asMap().entrySet()) {
				recipe.multiIngredient(ingredients.getKey(), ingredient -> {
					for (PieceIngredient pi : ingredients.getValue()) {
						if (pi.isTag()) ingredient.tag(pi.getId(set));
						else ingredient.item(pi.getId(set));
					}
				});
			}
		});
	}

	@Override
	public boolean canAddForSet(PieceSet set) {
		for (PieceIngredient pi : key.values()) {
			if (!pi.hasIngredientInSet(set)) return false;
		}
		return true;
	}
}
