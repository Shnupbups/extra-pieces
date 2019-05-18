package com.shnupbups.extrapieces.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class WallPieceBlock extends WallBlock implements ExtraPiece {
	public final Block baseBlock;

	public WallPieceBlock(Block base) {
		super(Block.Settings.copy(base));
		this.baseBlock = base;
	}

	public Block getBase() {
		return baseBlock;
	}
}
