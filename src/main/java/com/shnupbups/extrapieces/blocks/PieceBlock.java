package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.PieceType;
import net.minecraft.block.Block;

public interface PieceBlock {
	Block getBase();
	PieceType getType();
	Block getBlock();
}
