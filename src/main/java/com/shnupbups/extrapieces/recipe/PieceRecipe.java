package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public abstract class PieceRecipe {
	private Identifier id;
	private PieceType output;
	private int count;

	public PieceRecipe(Identifier id, PieceType output, int count) {
		this.id = id;
		this.output = output;
		this.count = count;
	}

	public PieceRecipe(String id, PieceType output, int count) {
		this(ExtraPieces.getID(id),output,count);
	}

	public Identifier getID() {
		return id;
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

	public abstract void add(ArtificeResourcePack.ServerResourcePackBuilder data, Identifier id, PieceSet set);
}
