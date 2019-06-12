package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FakePieceBlock implements PieceBlock {
	public final PieceType type;
	public final Block block;
	public final Block base;

	public FakePieceBlock(Block block, PieceType type, Block base) {
		this.type=type;
		this.block=block;
		this.base=base;
	}

	public PieceType getType() {
		return type;
	}

	public Block getBase() {
		return base;
	}

	public Block getBlock() {
		return block;
	}
}
