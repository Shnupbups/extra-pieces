package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public interface PieceBlock {
	Block getBase();
	PieceType getType();
	Block getBlock();
}
