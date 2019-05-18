package com.shnupbups.extrapieces.blocks;

import net.minecraft.block.Block;

public class SlabPieceBlock extends net.minecraft.block.SlabBlock implements ExtraPiece {

	public final Block baseBlock;

	public SlabPieceBlock(Block base) {
		super(Block.Settings.copy(base));
		this.baseBlock=base;
	}

	public Block getBase() {
		return baseBlock;
	}
}
