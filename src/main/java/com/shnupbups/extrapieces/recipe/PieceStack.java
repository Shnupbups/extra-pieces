package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.PieceSet;
import com.shnupbups.extrapieces.PieceType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.PacketByteBuf;

public class PieceStack {
	public PieceType type;
	public int count;

	public PieceStack(PieceType type) {
		this(type,1);
	}

	public PieceStack(PieceType type, int count) {
		this.type=type;
		this.count=count;
	}

	public PieceType getType() {
		return type;
	}

	public int getCount() {
		return count;
	}

	public ItemStack toItemStack(Block base) {
		return new ItemStack(PieceSet.getPiece(base, type),count);
	}

	public static PieceStack readPieceStack(PacketByteBuf buf) {
		PieceType type = PieceType.readPieceType(buf);
		int count = buf.readInt();
		return new PieceStack(type, count);
	}
}
