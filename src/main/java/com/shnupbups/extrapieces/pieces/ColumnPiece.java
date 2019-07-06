package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.ColumnPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;

public class ColumnPiece extends PieceType {
	public ColumnPiece() {
		super("column");
	}

	public ColumnPieceBlock getNew(PieceSet set) {
		return new ColumnPieceBlock(set);
	}
}
