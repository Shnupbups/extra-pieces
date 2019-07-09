package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.block.Block;

public final class BasePiece extends PieceType {
	public BasePiece() {
		super("base");
	}

	public String getBlockId(String baseName) {
		return baseName;
	}

	public Block getNew(PieceSet set) {
		return set.getBase();
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
	}
}
