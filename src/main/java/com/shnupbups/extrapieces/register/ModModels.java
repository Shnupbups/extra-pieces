package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import grondag.jmx.api.JmxInitializer;
import grondag.jmx.api.RetexturedModelBuilder;
import net.minecraft.util.registry.Registry;

public class ModModels implements JmxInitializer {

	@Override
	public void onInitalizeJmx() {
		for (PieceSet set : PieceSets.registry.values()) {
			if (set != ModBlocks.DUMMY_PIECES)
				for (PieceBlock b : set.getPieceBlocks()) {
					if (!set.isVanillaPiece(b.getType())) {
						//System.out.println(Registry.BLOCK.getId(ModBlocks.DUMMY_PIECES.getPiece(b.getType()))+" "+Registry.BLOCK.getId(b.getBlock()));
						RetexturedModelBuilder.builder(Registry.BLOCK.getId(ModBlocks.DUMMY_PIECES.getPiece(b.getType())), Registry.BLOCK.getId(b.getBlock()))
								.mapSprite(ExtraPieces.getID("block/dummy_main"), set.getMainTexture())
								.mapSprite(ExtraPieces.getID("block/dummy_top"), set.getTopTexture())
								.mapSprite(ExtraPieces.getID("block/dummy_bottom"), set.getBottomTexture())
								.completeBlockWithItem();
					}
				}
		}
	}
}
