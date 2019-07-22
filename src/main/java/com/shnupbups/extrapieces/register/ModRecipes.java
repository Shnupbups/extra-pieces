package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.config.EPConfig;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.shnupbups.extrapieces.recipe.ShapelessPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {

	public static final ShapedPieceRecipe STAIRS_TO_BASE = new ShapedPieceRecipe("stairs_to_base", PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.STAIRS);
	public static final ShapedPieceRecipe CORNERS_TO_BASE = new ShapedPieceRecipe("corners_to_base", PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.CORNER);
	public static final ShapedPieceRecipe WALLS_TO_BASE = new ShapedPieceRecipe("walls_to_base", PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.WALL);
	public static final ShapedPieceRecipe FENCES_TO_BASE = new ShapedPieceRecipe("fences_to_base", PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.FENCE);
	public static final ShapedPieceRecipe POSTS_TO_BASE = new ShapedPieceRecipe("posts_to_base", PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.POST);
	public static final ShapedPieceRecipe COLUMNS_TO_BASE = new ShapedPieceRecipe("columns_to_base", PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.COLUMN);

	public static final ShapelessPieceRecipe STAIRS_TO_CORNER = new ShapelessPieceRecipe("stairs_to_corner", PieceTypes.CORNER, 1, PieceTypes.STAIRS);
	public static final ShapelessPieceRecipe CORNER_TO_STAIRS = new ShapelessPieceRecipe("corner_to_stairs", PieceTypes.STAIRS, 1, PieceTypes.CORNER);
	public static final ShapelessPieceRecipe SLAB_TO_SIDING = new ShapelessPieceRecipe("slab_to_siding", PieceTypes.SIDING, 1, PieceTypes.SLAB);
	public static final ShapelessPieceRecipe SIDING_TO_SLAB = new ShapelessPieceRecipe("siding_to_slab", PieceTypes.SLAB, 1, PieceTypes.SIDING);
	public static final ShapelessPieceRecipe SLABS_TO_BASE = new ShapelessPieceRecipe("slabs_to_base", PieceTypes.BASE, 1, PieceTypes.SLAB, PieceTypes.SLAB);
	public static final ShapelessPieceRecipe SIDINGS_TO_BASE = new ShapelessPieceRecipe("sidings_to_base", PieceTypes.BASE, 1, PieceTypes.SIDING, PieceTypes.SIDING);
	public static final ShapelessPieceRecipe FENCE_TO_POST = new ShapelessPieceRecipe("fence_to_post", PieceTypes.POST, 1, PieceTypes.FENCE);
	public static final ShapelessPieceRecipe POST_TO_FENCE = new ShapelessPieceRecipe("post_to_fence", PieceTypes.FENCE, 1, PieceTypes.POST);
	public static final ShapelessPieceRecipe WALL_TO_COLUMN = new ShapelessPieceRecipe("wall_to_column", PieceTypes.COLUMN, 1, PieceTypes.WALL);
	public static final ShapelessPieceRecipe COLUMN_TO_WALL = new ShapelessPieceRecipe("column_to_wall", PieceTypes.WALL, 1, PieceTypes.COLUMN);
	public static final ShapelessPieceRecipe LAYERS_TO_BASE = new ShapelessPieceRecipe("layers_to_base", PieceTypes.BASE, 1, PieceTypes.LAYER, PieceTypes.LAYER, PieceTypes.LAYER, PieceTypes.LAYER, PieceTypes.LAYER, PieceTypes.LAYER, PieceTypes.LAYER, PieceTypes.LAYER);
	public static final ShapelessPieceRecipe LAYERS_TO_SLAB = new ShapelessPieceRecipe("layers_to_slab", PieceTypes.SLAB, 1, PieceTypes.LAYER, PieceTypes.LAYER, PieceTypes.LAYER, PieceTypes.LAYER);

	public static void init(ArtificeResourcePack.ServerResourcePackBuilder data) {
		int r = 0;
		int s = 0;
		for (PieceSet ps : PieceSets.registry.values()) {
			for (PieceBlock pb : ps.getPieceBlocks()) {
				if (ps.isCraftable(pb.getType())) {
					int i = 0;
					for (ShapedPieceRecipe pr : pb.getType().getRecipes()) {
						Identifier bid = Registry.BLOCK.getId(pb.getBlock());
						Identifier id = ExtraPieces.getID(bid.getPath() + "_" + (i++));
						pr.add(data, id, ps);
						r++;
					}

				}
				if (!ps.isVanillaPiece(pb.getType()) && pb.getType() != PieceTypes.BASE && (ps.isStonecuttable() || EPConfig.everythingStonecuttable)) {
					Identifier bid = Registry.BLOCK.getId(pb.getBlock());
					Identifier id = ExtraPieces.getID(bid.getPath() + "_stonecutting");
					pb.getType().getStonecuttingRecipe().add(data, id, ps);
					s++;
				}
			}
			if (ps.hasPiece(PieceTypes.STAIRS)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.STAIRS));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				STAIRS_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.CORNER)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.CORNER));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				CORNERS_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.STAIRS) && ps.hasPiece(PieceTypes.CORNER)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.CORNER));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_stairs");
				CORNER_TO_STAIRS.add(data, id, ps);
				r++;
				Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.STAIRS));
				Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_corner");
				STAIRS_TO_CORNER.add(data, id2, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.WALL)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.WALL));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				WALLS_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.FENCE)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.FENCE));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				FENCES_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.COLUMN)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.COLUMN));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				COLUMNS_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.POST)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.POST));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				POSTS_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.SLAB)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SLAB));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				SLABS_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.SIDING)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SIDING));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				SIDINGS_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.WALL)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.WALL));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				WALLS_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.LAYER)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.LAYER));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				LAYERS_TO_BASE.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.LAYER) && ps.hasPiece(PieceTypes.SLAB)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.LAYER));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_slab");
				LAYERS_TO_SLAB.add(data, id, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.SLAB) && ps.hasPiece(PieceTypes.SIDING)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SLAB));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_siding");
				SLAB_TO_SIDING.add(data, id, ps);
				r++;
				Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SIDING));
				Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_slab");
				SIDING_TO_SLAB.add(data, id2, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.FENCE) && ps.hasPiece(PieceTypes.POST)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.FENCE));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_post");
				FENCE_TO_POST.add(data, id, ps);
				r++;
				Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.POST));
				Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_fence");
				POST_TO_FENCE.add(data, id2, ps);
				r++;
			}
			if (ps.hasPiece(PieceTypes.WALL) && ps.hasPiece(PieceTypes.COLUMN)) {
				Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.WALL));
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_column");
				WALL_TO_COLUMN.add(data, id, ps);
				r++;
				Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.COLUMN));
				Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_wall");
				COLUMN_TO_WALL.add(data, id2, ps);
				r++;
			}
		}
		ExtraPieces.log("Added " + r + " crafting recipes!");
		ExtraPieces.log("Added " + s + " stonecutting recipes!");
	}

}
