package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemConvertible;

public interface PieceBlock extends ItemConvertible {
	PieceType getType();

	Block getBlock();

	PieceSet getSet();
	
	default Block getBase() {
		return getSet().getBase();
	}

	default BlockState getBaseState() {
		return getBase().getDefaultState();
	}

	default String getPieceString() {
		return getSet().getName()+" "+getType().getId();
	}
}
