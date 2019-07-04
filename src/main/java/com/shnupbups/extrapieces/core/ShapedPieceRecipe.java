package com.shnupbups.extrapieces.core;

import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class ShapedPieceRecipe {
	public PieceType output;
	public int count;
	public HashMap<Character,PieceType> key = new HashMap<>();
	public String[] pattern;

	public ShapedPieceRecipe(PieceType output, int count, String... pattern) {
		this.output=output;
		this.count=count;
		this.pattern=pattern;
	}

	public ShapedPieceRecipe addToKey(char c, PieceType type) {
		key.put(c,type);
		return this;
	}

	public HashMap<Character,PieceType> getKey() {
		return key;
	}

	public String[] getPattern() {
		return pattern;
	}

	public PieceType getOutput() {
		return output;
	}

	public int getCount() {
		return count;
	}

	public PieceType getFromKey(char c) {
		return key.get(c);
	}

	public Block getOutput(PieceSet set) {
		return set.getPiece(getOutput());
	}

	public HashMap<Character,Block> getKey(PieceSet set) {
		HashMap<Character,Block> setKey = new HashMap<>();
		for(char c:getKey().keySet()) {
			setKey.put(c,set.getPiece(getKey().get(c)));
		}
		return setKey;
	}

	public Block getFromKey(PieceSet set, char c) {
		return set.getPiece(getFromKey(c));
	}

	public ShapedPieceRecipe add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set) {
		data.addShapedRecipe(id, recipe -> {
			recipe.result(Registry.BLOCK.getId(this.getOutput(set)), this.getCount());
			recipe.pattern(this.getPattern());
			for (char c : this.getKey(set).keySet()) {
				recipe.ingredientItem(c, Registry.BLOCK.getId(this.getFromKey(set, c)));
			}
		});
		return this;
	}
}
