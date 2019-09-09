package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.TrimPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.register.ModProperties;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class TrimPiece extends PieceType {
	public TrimPiece() {
		super("trim");
	}
	
	@Override
	public PieceBlock getNew(PieceSet set) {
		return new TrimPieceBlock(set);
	}
	
	@Override
	public int getStonecuttingCount() {
		return 4;
	}
	
	@Override
	public void addBlockModels(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		addBlockModel(pack, pb, "1");
		addBlockModel(pack, pb, "2");
		addBlockModel(pack, pb, "3");
		addBlockModel(pack, pb, "4");
	}
	
	public void addItemModel(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addItemModel(Registry.BLOCK.getId(pb.getBlock()), model -> {
			model.parent(getModelPath(pb, "1"));
		});
	}
	
	@Override
	public void addLootTable(ArtificeResourcePack.ServerResourcePackBuilder data, PieceBlock pb) {
		data.addLootTable(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()), "blocks/"), loot -> {
			loot.type(new Identifier("block"));
			loot.pool(pool -> {
				pool.rolls(1);
				pool.entry(entry -> {
					entry.type(new Identifier("item"));
					entry.name(Registry.BLOCK.getId(pb.getBlock()));
					entry.function(new Identifier("set_count"), func -> {
						func.add("count", 2);
						func.condition(new Identifier("block_state_property"), cond -> {
							cond.add("block", Registry.BLOCK.getId(pb.getBlock()).toString());
							cond.addObject("properties", prop -> {
								prop.add("count", "2");
							});
						});
					});
					entry.function(new Identifier("set_count"), func -> {
						func.add("count", 3);
						func.condition(new Identifier("block_state_property"), cond -> {
							cond.add("block", Registry.BLOCK.getId(pb.getBlock()).toString());
							cond.addObject("properties", prop -> {
								prop.add("count", "3");
							});
						});
					});
					entry.function(new Identifier("set_count"), func -> {
						func.add("count", 4);
						func.condition(new Identifier("block_state_property"), cond -> {
							cond.add("block", Registry.BLOCK.getId(pb.getBlock()).toString());
							cond.addObject("properties", prop -> {
								prop.add("count", "4");
							});
						});
					});
					entry.function(new Identifier("explosion_decay"), cond -> {
					});
				});
			});
		});
	}
	
	@Override
	public void addBlockstate(ArtificeResourcePack.ClientResourcePackBuilder pack, PieceBlock pb) {
		pack.addBlockState(Registry.BLOCK.getId(pb.getBlock()), state -> {
			for(Direction.Axis axis: Direction.Axis.values()) {
				state.multipartCase(caze -> {
					caze.when("one", "true");
					caze.when("axis", axis.asString());
					caze.apply(var -> {
						var.uvlock(true);
						var.model(getModelPath(pb, "1"));
						switch(axis) {
							case Y:
								var.rotationX(90);
								break;
							case X:
								var.rotationY(-90);
						}
					});
				});
				state.multipartCase(caze -> {
					caze.when("two", "true");
					caze.when("axis", axis.asString());
					caze.apply(var -> {
						var.uvlock(true);
						var.model(getModelPath(pb, "2"));
						switch(axis) {
							case Y:
								var.rotationX(90);
								break;
							case X:
								var.rotationY(-90);
						}
					});
				});
				state.multipartCase(caze -> {
					caze.when("three", "true");
					caze.when("axis", axis.asString());
					caze.apply(var -> {
						var.uvlock(true);
						var.model(getModelPath(pb, "3"));
						switch(axis) {
							case Y:
								var.rotationX(90);
								break;
							case X:
								var.rotationY(-90);
						}
					});
				});
				state.multipartCase(caze -> {
					caze.when("four", "true");
					caze.when("axis", axis.asString());
					caze.apply(var -> {
						var.uvlock(true);
						var.model(getModelPath(pb, "4"));
						switch(axis) {
							case Y:
								var.rotationX(90);
								break;
							case X:
								var.rotationY(-90);
						}
					});
				});
			}
		});
	}
}
