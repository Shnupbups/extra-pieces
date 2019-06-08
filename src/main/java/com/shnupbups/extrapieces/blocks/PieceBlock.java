package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.PieceType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface PieceBlock {
	Block getBase();
	PieceType getType();
	Block getBlock();
}
