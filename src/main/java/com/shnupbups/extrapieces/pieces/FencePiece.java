package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.FencePieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

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

	public void addBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		super.addBlockModels(pack, pb);
		addBlockModel(pack, pb, "side");
		addBlockModel(pack, pb, "inventory");
	}

	public void addItemModel(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addItemModel(Registry.BLOCK.getId(pb.getBlock()), model -> {
			model.parent(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_inventory"), "block/"));
		});
	}

	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			state.multipartCase(caze -> {
				caze.apply(var -> {
					var.model(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "block/"));
				});
			});
			for (Direction d : Direction.values()) {
				if (d != Direction.UP && d != Direction.DOWN) {
					state.multipartCase(caze -> {
						caze.when(d.asString(), "true");
						caze.apply(var -> {
							var.model(ExtraPieces.prependToPath(ExtraPieces.appendToPath(Registry.BLOCK.getId(pb.getBlock()), "_side"), "block/"));
							var.uvlock(true);
							switch (d) {
								case EAST:
									var.rotationY(90);
									break;
								case WEST:
									var.rotationY(270);
									break;
								case SOUTH:
									var.rotationY(180);
									break;
							}
						});
					});
				}
			}
		});
	}
}
