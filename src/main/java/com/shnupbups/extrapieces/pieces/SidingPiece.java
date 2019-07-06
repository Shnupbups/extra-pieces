package com.shnupbups.extrapieces.pieces;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.blocks.SidingPieceBlock;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class SidingPiece extends PieceType {
	public SidingPiece() {
		super("siding");
	}

	public SidingPieceBlock getNew(PieceSet set) {
		return new SidingPieceBlock(set);
	}

	public ArrayList<ShapedPieceRecipe> getRecipes() {
		ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
		recipes.add(new ShapedPieceRecipe(this,6,"b", "b", "b").addToKey('b', PieceTypes.BASE));
		return recipes;
	}

	public int getStonecuttingCount() {
		return 2;
	}

	@Override
	public void addLootTable(ArtificeResourcePack.ServerResourcePackBuilder data, PieceBlock pb) {
		data.addLootTable(ExtraPieces.prependToPath(Registry.BLOCK.getId(pb.getBlock()),"blocks/"), loot -> {
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
								prop.add("type", "double");
							});
						});
					});
					entry.function(new Identifier("explosion_decay"), cond -> {});
				});
			});
		});
	}
}
