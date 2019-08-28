package com.shnupbups.extrapieces.recipe;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;

import net.minecraft.item.Item;

public class PieceIngredient {
	public final PIType type;
	private Item item;
	private PieceType pt;
	
	public PieceIngredient(PieceType type) {
		this.type = PIType.PIECE;
		this.pt = type;
	}
	
	public PieceIngredient(Item item) {
		this.type = PIType.ITEM;
		this.item = item;
	}
	
	public Item asItem(PieceSet set) {
		switch(type) {
			case ITEM:
				return item;
			case PIECE:
				return set.getPiece(pt).asItem();
			default:
				return null;
		}
	}
	
	public boolean hasIngredientInSet(PieceSet set) {
		return (type.equals(PIType.ITEM) || set.hasPiece(pt));
	}
	
	enum PIType {
		ITEM,
		PIECE
	}
}
