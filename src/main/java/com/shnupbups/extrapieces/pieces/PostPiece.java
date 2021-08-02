package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.PostPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class PostPiece extends PieceType {
	public PostPiece() {
		super("post");
	}

	public PostPieceBlock getNew(PieceSet set) {
		return new PostPieceBlock(set);
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for (Direction.Axis a : Direction.Axis.values()) {
				state.variant("axis=" + a.asString(), var -> {
					var.uvlock(true);
					var.model(getModelPath(pb));
					if (a != Direction.Axis.Y) {
						var.rotationX(90);
						if (a == Direction.Axis.X) {
							var.rotationY(90);
						}
					}
				});
			}
		});
	}
}
