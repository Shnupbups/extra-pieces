package com.shnupbups.extrapieces.debug;

import com.shnupbups.extrapieces.PieceSet;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class DebugItem extends Item {
	public DebugItem() {
		super(new Item.Settings());
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		BlockPos pos = context.getBlockPos();
		BlockState state = context.getWorld().getBlockState(pos);
		if(state.getBlock() instanceof PieceBlock) {
			PieceBlock pb = (PieceBlock)state.getBlock();
			context.getPlayer().addChatMessage(new TextComponent(state.getBlock()+" is piece! Type: "+pb.getType()+" Base: "+pb.getBase()), false);
			return ActionResult.SUCCESS;
		} else if(PieceSet.hasSet(state.getBlock())) {
			context.getPlayer().addChatMessage(new TextComponent(state.getBlock()+" is base! PieceSet "+PieceSet.getSet(state.getBlock())), false);
			return ActionResult.SUCCESS;
		} else if(PieceSet.isPiece(state.getBlock())) {
			PieceBlock pb = PieceSet.asPieceBlock(state.getBlock());
			context.getPlayer().addChatMessage(new TextComponent(state.getBlock()+" is vanilla piece! Type: "+pb.getType()+" Base: "+pb.getBase()), false);
			return ActionResult.SUCCESS;
		} else {
			context.getPlayer().addChatMessage(new TextComponent(state.getBlock()+" nope"), false);
			return ActionResult.PASS;
		}
	}
}
