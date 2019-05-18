package com.shnupbups.extrapieces.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.FenceBlock;

public class FencePieceBlock extends FenceBlock implements ExtraPiece {
	public final Block baseBlock;

	public FencePieceBlock(Block base) {
		super(Settings.copy(base));
		this.baseBlock = base;
	}

	public Block getBase() {
		return baseBlock;
	}
}
