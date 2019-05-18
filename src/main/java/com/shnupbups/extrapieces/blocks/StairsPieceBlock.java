package com.shnupbups.extrapieces.blocks;

import net.minecraft.block.Block;

public class StairsPieceBlock extends net.minecraft.block.StairsBlock implements ExtraPiece {

	public final Block baseBlock;

	public StairsPieceBlock(Block base) {
		super(base.getDefaultState(), Block.Settings.copy(base));
		this.baseBlock=base;
	}

	public Block getBase() {
		return baseBlock;
	}
}
