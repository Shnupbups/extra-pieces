package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FakePieceBlock implements PieceBlock {
	public final PieceType type;
	public final Block block;
	public final PieceSet set;

	public FakePieceBlock(Block block, PieceType type, PieceSet set) {
		this.type=type;
		this.block=block;
		this.set=set;
	}

	public PieceType getType() {
		return type;
	}

	public PieceSet getSet() {
		return set;
	}

	public Block getBlock() {
		return block;
	}
}
