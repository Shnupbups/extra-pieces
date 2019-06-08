package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.PieceType;
import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;

public class FenceGatePieceBlock extends FenceGateBlock implements PieceBlock {
	public final Block baseBlock;

	public FenceGatePieceBlock(Block base) {
		super(Settings.copy(base));
		this.baseBlock = base;
	}

	public Block getBlock() { return this; }

	public Block getBase() {
		return baseBlock;
	}

	public PieceType getType() {return PieceType.FENCE_GATE;}
}
