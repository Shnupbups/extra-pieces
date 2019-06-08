package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.PieceSet;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

public class StackUtils {
	public static Block getBase(ItemStack stack) {
		if(stack.getItem() instanceof BlockItem) {
			if(((BlockItem) stack.getItem()).getBlock() instanceof PieceBlock) {
				return ((PieceBlock) ((BlockItem) stack.getItem()).getBlock()).getBase();
			} else if(PieceSet.hasSet(((BlockItem) stack.getItem()).getBlock())) {
				return ((BlockItem) stack.getItem()).getBlock();
			}
		}
		return null;
	}
}
