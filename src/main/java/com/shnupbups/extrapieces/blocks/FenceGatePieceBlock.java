package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.block.FenceGateBlock;

public class FenceGatePieceBlock extends FenceGateBlock implements PieceBlock {

	private final PieceSet set;

	public FenceGatePieceBlock(PieceSet set) {
		super(Settings.copy(set.getBase()));
		this.set = set;
	}

	public Block getBlock() { return this; }

	public PieceSet getSet() {
		return set;
	}

	public PieceType getType() {return PieceType.FENCE_GATE;}
}
