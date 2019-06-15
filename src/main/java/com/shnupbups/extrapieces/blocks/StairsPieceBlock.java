package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class StairsPieceBlock extends StairsBlock implements PieceBlock {
	private final PieceSet set;

	public StairsPieceBlock(PieceSet set) {
		super(set.getBase().getDefaultState(), Settings.copy(set.getBase()));
		this.set = set;
	}

	public Block getBlock() { return this; }

	public PieceSet getSet() {
		return set;
	}

	public PieceType getType() {return PieceType.STAIRS;}
}
