package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.PieceType;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;

public class StairsPieceBlock extends StairsBlock implements ExtraPiece {

	public final Block baseBlock;

	public StairsPieceBlock(Block base) {
		super(base.getDefaultState(), Block.Settings.copy(base));
		this.baseBlock=base;
	}

	public Block getBase() {
		return baseBlock;
	}

	public PieceType getType() {return PieceType.STAIRS;}
}
