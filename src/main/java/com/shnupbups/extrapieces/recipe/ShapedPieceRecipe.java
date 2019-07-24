package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class ShapedPieceRecipe extends PieceRecipe {
	private HashMap<Character, PieceType> key = new HashMap<>();
	private String[] pattern;

	public ShapedPieceRecipe(Identifier id, PieceType output, int count, String... pattern) {
		super(id, output, count);
		this.pattern = pattern;
	}

	public ShapedPieceRecipe(String id, PieceType output, int count, String... pattern) {
		this(ExtraPieces.getID(id), output, count, pattern);
	}

	public ShapedPieceRecipe addToKey(char c, PieceType type) {
		key.put(c, type);
		return this;
	}

	public HashMap<Character, PieceType> getKey() {
		return key;
	}

	public String[] getPattern() {
		return pattern;
	}

	public PieceType getFromKey(char c) {
		return key.get(c);
	}

	public HashMap<Character, Block> getKey(PieceSet set) {
		HashMap<Character, Block> setKey = new HashMap<>();
		for (char c : getKey().keySet()) {
			setKey.put(c, set.getPiece(getKey().get(c)));
		}
		return setKey;
	}

	public Block getFromKey(PieceSet set, char c) {
		return set.getPiece(getFromKey(c));
	}

	public void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addShapedRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(this.getOutput(set)), this.getCount());
			recipe.group(getID());
			recipe.pattern(this.getPattern());
			for (char c : this.getKey(set).keySet()) {
				recipe.ingredientItem(c, Registry.BLOCK.getId(this.getFromKey(set, c)));
			}
		});
	}
}
