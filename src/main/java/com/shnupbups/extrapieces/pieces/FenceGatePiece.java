package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.FenceGatePieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class FenceGatePiece extends PieceType {
	public FenceGatePiece() {
		super("fence_gate");
	}

	public FenceGatePieceBlock getNew(PieceSet set) {
		return new FenceGatePieceBlock(set);
	}

	public void addBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "wall");
		addBlockModel(pack, pb, "open");
		addBlockModel(pack, pb, "wall_open");
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for (Direction d : Direction.values()) {
				if (d != Direction.UP && d != Direction.DOWN) {
					state.variant("facing=" + d.asString() + ",in_wall=false,open=false", var -> {
						var.model(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "block/"));
						var.uvlock(true);
						switch (d) {
							case NORTH:
								var.rotationY(180);
								break;
							case WEST:
								var.rotationY(90);
								break;
							case EAST:
								var.rotationY(270);
								break;
						}
					});
					state.variant("facing=" + d.asString() + ",in_wall=true,open=false", var -> {
						var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_wall"), "block/"));
						var.uvlock(true);
						switch (d) {
							case NORTH:
								var.rotationY(180);
								break;
							case WEST:
								var.rotationY(90);
								break;
							case EAST:
								var.rotationY(270);
								break;
						}
					});
					state.variant("facing=" + d.asString() + ",in_wall=false,open=true", var -> {
						var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_open"), "block/"));
						var.uvlock(true);
						switch (d) {
							case NORTH:
								var.rotationY(180);
								break;
							case WEST:
								var.rotationY(90);
								break;
							case EAST:
								var.rotationY(270);
								break;
						}
					});
					state.variant("facing=" + d.asString() + ",in_wall=true,open=true", var -> {
						var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_wall_open"), "block/"));
						var.uvlock(true);
						switch (d) {
							case NORTH:
								var.rotationY(180);
								break;
							case WEST:
								var.rotationY(90);
								break;
							case EAST:
								var.rotationY(270);
								break;
						}
					});
				}
			}
		});
	}
}
