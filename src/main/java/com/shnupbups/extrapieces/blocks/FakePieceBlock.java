package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

public class FakePieceBlock implements PieceBlock {
	public final PieceType type;
	public final Block block;
	public final PieceSet set;

	public FakePieceBlock(Block block, PieceType type, PieceSet set) {
		this.type = type;
		this.block = block;
		this.set = set;
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

	public Item asItem() {
		return getBlock().asItem();
	}
}
