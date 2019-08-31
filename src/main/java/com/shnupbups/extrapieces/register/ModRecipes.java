package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceSets;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.recipe.PieceIngredient;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.shnupbups.extrapieces.recipe.ShapelessPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {

	public static final ShapedPieceRecipe STAIRS_TO_BASE = new ShapedPieceRecipe(PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.STAIRS);
	public static final ShapedPieceRecipe CORNERS_TO_BASE = new ShapedPieceRecipe(PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.CORNER);
	public static final ShapedPieceRecipe WALLS_TO_BASE = new ShapedPieceRecipe(PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.WALL);
	public static final ShapedPieceRecipe FENCES_TO_BASE = new ShapedPieceRecipe(PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.FENCE);
	public static final ShapedPieceRecipe POSTS_TO_BASE = new ShapedPieceRecipe(PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.POST);
	public static final ShapedPieceRecipe COLUMNS_TO_BASE = new ShapedPieceRecipe(PieceTypes.BASE, 4, "ss", "ss").addToKey('s', PieceTypes.COLUMN);

	public static final ShapelessPieceRecipe STAIRS_TO_CORNER = new ShapelessPieceRecipe(PieceTypes.CORNER, 1, new PieceIngredient(PieceTypes.STAIRS));
	public static final ShapelessPieceRecipe CORNER_TO_STAIRS = new ShapelessPieceRecipe(PieceTypes.STAIRS, 1, new PieceIngredient(PieceTypes.CORNER));
	public static final ShapelessPieceRecipe SLAB_TO_SIDING = new ShapelessPieceRecipe(PieceTypes.SIDING, 1, new PieceIngredient(PieceTypes.SLAB));
	public static final ShapelessPieceRecipe SIDING_TO_SLAB = new ShapelessPieceRecipe(PieceTypes.SLAB, 1, new PieceIngredient(PieceTypes.SIDING));
	public static final ShapelessPieceRecipe SLABS_TO_BASE = new ShapelessPieceRecipe(PieceTypes.BASE, 1, new PieceIngredient(PieceTypes.SLAB), new PieceIngredient(PieceTypes.SLAB));
	public static final ShapelessPieceRecipe SIDINGS_TO_BASE = new ShapelessPieceRecipe(PieceTypes.BASE, 1, new PieceIngredient(PieceTypes.SIDING), new PieceIngredient(PieceTypes.SIDING));
	public static final ShapelessPieceRecipe FENCE_TO_POST = new ShapelessPieceRecipe(PieceTypes.POST, 1, new PieceIngredient(PieceTypes.FENCE));
	public static final ShapelessPieceRecipe POST_TO_FENCE = new ShapelessPieceRecipe(PieceTypes.FENCE, 1, new PieceIngredient(PieceTypes.POST));
	public static final ShapelessPieceRecipe WALL_TO_COLUMN = new ShapelessPieceRecipe(PieceTypes.COLUMN, 1, new PieceIngredient(PieceTypes.WALL));
	public static final ShapelessPieceRecipe COLUMN_TO_WALL = new ShapelessPieceRecipe(PieceTypes.WALL, 1, new PieceIngredient(PieceTypes.COLUMN));
	public static final ShapelessPieceRecipe LAYERS_TO_BASE = new ShapelessPieceRecipe(PieceTypes.BASE, 1, new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER));
	public static final ShapelessPieceRecipe LAYERS_TO_SLAB = new ShapelessPieceRecipe(PieceTypes.SLAB, 1, new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER), new PieceIngredient(PieceTypes.LAYER));

	public static int r = 0, s = 0, w = 0;

	public static void init(ArtificeResourcePack.ServerResourcePackBuilder data) {
		for (PieceSet ps : PieceSets.registry.values()) {
			ps.addRecipes(data);
		}
		ExtraPieces.debugLog("Registered " + r + " crafting recipes!");
		ExtraPieces.debugLog("Registered " + s + " stonecutting recipes!");
		if (ExtraPieces.isWoodmillInstalled()) {
			ExtraPieces.debugLog("Registered " + w + " woodmilling recipes!");
		}
	}

	public static void addMiscRecipes(ArtificeResourcePack.ServerResourcePackBuilder data, PieceSet ps) {
		if (ps.hasPiece(PieceTypes.STAIRS)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.STAIRS));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				STAIRS_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.CORNER)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.CORNER));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				CORNERS_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.STAIRS) && ps.hasPiece(PieceTypes.CORNER)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.CORNER));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_stairs");
				CORNER_TO_STAIRS.add(data, id, ps);
				r++;
			}
			Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.STAIRS));
			if (!checkIsAir(bid2, ps)) {
				Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_corner");
				STAIRS_TO_CORNER.add(data, id2, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.WALL)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.WALL));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				WALLS_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.FENCE)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.FENCE));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				FENCES_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.COLUMN)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.COLUMN));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				COLUMNS_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.POST)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.POST));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				POSTS_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.SLAB)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SLAB));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				SLABS_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.SIDING)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SIDING));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				SIDINGS_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.WALL)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.WALL));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				WALLS_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.LAYER)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.LAYER));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
				LAYERS_TO_BASE.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.LAYER) && ps.hasPiece(PieceTypes.SLAB)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.LAYER));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_slab");
				LAYERS_TO_SLAB.add(data, id, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.SLAB) && ps.hasPiece(PieceTypes.SIDING)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SLAB));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_siding");
				SLAB_TO_SIDING.add(data, id, ps);
				r++;
			}
			Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SIDING));
			if (!checkIsAir(bid2, ps)) {
				Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_slab");
				SIDING_TO_SLAB.add(data, id2, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.FENCE) && ps.hasPiece(PieceTypes.POST)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.FENCE));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_post");
				FENCE_TO_POST.add(data, id, ps);
				r++;
			}
			Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.POST));
			if (!checkIsAir(bid2, ps)) {
				Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_fence");
				POST_TO_FENCE.add(data, id2, ps);
				r++;
			}
		}
		if (ps.hasPiece(PieceTypes.WALL) && ps.hasPiece(PieceTypes.COLUMN)) {
			Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.WALL));
			if (!checkIsAir(bid, ps)) {
				Identifier id = ExtraPieces.getID(bid.getPath() + "_to_column");
				WALL_TO_COLUMN.add(data, id, ps);
				r++;
			}
			Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.COLUMN));
			if (!checkIsAir(bid2, ps)) {
				Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_wall");
				COLUMN_TO_WALL.add(data, id2, ps);
				r++;
			}
		}
	}

	public static boolean checkIsAir(Identifier bid, PieceSet ps) {
		if (bid.getPath().equals("air")) {
			ExtraPieces.debugLog("OH NO - " + ps);
			return true;
		}
		return false;
	}

	public static void incrementRecipes() {
		r++;
	}

	public static void incrementStonecuttingRecipes() {
		s++;
	}

	public static void incrementWoodmillingRecipes() {
		w++;
	}

}
