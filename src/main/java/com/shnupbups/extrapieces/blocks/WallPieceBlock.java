package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.block.WallBlock;

public class WallPieceBlock extends WallBlock implements PieceBlock {
	public final Block baseBlock;

	public WallPieceBlock(Block base) {
		super(Block.Settings.copy(base));
		this.baseBlock = base;
	}

	public Block getBlock() { return this; }

	public Block getBase() {
		return baseBlock;
	}

	public PieceType getType() {return PieceType.WALL;}
}
