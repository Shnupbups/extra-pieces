package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;

public interface PieceBlock extends ItemConvertible {
	PieceType getType();

	Block getBlock();

	PieceSet getSet();
}
