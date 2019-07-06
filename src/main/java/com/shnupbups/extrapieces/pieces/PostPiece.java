package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.PostPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;

public class PostPiece extends PieceType {
	public PostPiece() {
		super("post");
	}

	public PostPieceBlock getNew(PieceSet set) {
		return new PostPieceBlock(set);
	}
}
