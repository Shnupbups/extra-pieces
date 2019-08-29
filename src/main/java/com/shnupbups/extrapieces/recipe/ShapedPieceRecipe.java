package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class ShapedPieceRecipe extends PieceRecipe {
	private HashMap<Character, PieceIngredient> key = new HashMap<>();
	private String[] pattern;

	public ShapedPieceRecipe(PieceType output, int count, String... pattern) {
		super(output, count);
		this.pattern = pattern;
	}

	public ShapedPieceRecipe addToKey(char c, PieceType type) {
		return addToKey(c, new PieceIngredient(type));
	}

	public ShapedPieceRecipe addToKey(char c, Item item) {
		return addToKey(c, new PieceIngredient(item));
	}

	public ShapedPieceRecipe addToKey(char c, PieceIngredient ingredient) {
		key.put(c, ingredient);
		return this;
	}

	public HashMap<Character, PieceIngredient> getKey() {
		return key;
	}

	public String[] getPattern() {
		return pattern;
	}

	public PieceIngredient getFromKey(char c) {
		return key.get(c);
	}

	public HashMap<Character, ItemConvertible> getKey(PieceSet set) {
		HashMap<Character, ItemConvertible> setKey = new HashMap<>();
		for (char c : getKey().keySet()) {
			setKey.put(c, getFromKey(c).asItem(set));
		}
		return setKey;
	}

	public ItemConvertible getFromKey(PieceSet set, char c) {
		return getFromKey(c).asItem(set);
	}

	public void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addShapedRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(this.getOutput(set)), this.getCount());
			recipe.group(Registry.BLOCK.getId(getOutput(set)));
			recipe.pattern(this.getPattern());
			for (char c : this.getKey(set).keySet()) {
				recipe.ingredientItem(c, Registry.ITEM.getId(this.getFromKey(set, c).asItem()));
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
