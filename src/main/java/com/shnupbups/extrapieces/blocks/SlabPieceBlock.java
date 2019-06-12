package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class SlabPieceBlock extends SlabBlock implements PieceBlock {

	public final Block baseBlock;

	public SlabPieceBlock(Block base) {
		super(Block.Settings.copy(base));
		this.baseBlock=base;
	}

	public Block getBlock() { return this; }

	public Block getBase() {
		return baseBlock;
	}

	public PieceType getType() {return PieceType.SLAB;}
}
