package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.FenceGatePieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;

public class FenceGatePiece extends PieceType {
	public FenceGatePiece() {
		super("fence_gate");
	}

	public FenceGatePieceBlock getNew(PieceSet set) {
		return new FenceGatePieceBlock(set);
	}
}
