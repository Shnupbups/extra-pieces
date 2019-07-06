package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.FencePieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.minecraft.util.Identifier;

public class FencePiece extends PieceType {
	public FencePiece() {
		super("fence");
	}

	public FencePieceBlock getNew(PieceSet set) {
		return new FencePieceBlock(set);
	}

	public Identifier getTagId() {
		return new Identifier("minecraft", "fences");
	}
}
