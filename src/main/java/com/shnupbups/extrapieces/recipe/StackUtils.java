package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.PieceSet;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class StackUtils {
	public static Block getBase(ItemStack stack) {
		if(stack.getItem() instanceof BlockItem) {
			Block b = ((BlockItem) stack.getItem()).getBlock();
			if(PieceSet.hasSet(b)) return b;
			else if(PieceSet.isPiece(b)) {
				return PieceSet.asPieceBlock(((BlockItem) stack.getItem()).getBlock()).getBase();
			}
		}
		return null;
	}
}
