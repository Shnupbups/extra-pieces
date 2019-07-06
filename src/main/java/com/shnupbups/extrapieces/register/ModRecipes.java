package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.*;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.shnupbups.extrapieces.recipe.ShapelessPieceRecipe;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {

	public static final ShapedPieceRecipe STAIRS_TO_BASE = new ShapedPieceRecipe(PieceTypes.BASE,4,"ss","ss").addToKey('s',PieceTypes.STAIRS);
	public static final ShapedPieceRecipe CORNERS_TO_BASE = new ShapedPieceRecipe(PieceTypes.BASE,4,"ss","ss").addToKey('s',PieceTypes.CORNER);
	public static final ShapedPieceRecipe WALL_TO_BASE = new ShapedPieceRecipe(PieceTypes.BASE,4,"ss","ss").addToKey('s',PieceTypes.WALL);

	public static final ShapelessPieceRecipe STAIRS_TO_CORNER = new ShapelessPieceRecipe(PieceTypes.CORNER,1,PieceTypes.STAIRS);
	public static final ShapelessPieceRecipe CORNER_TO_STAIRS = new ShapelessPieceRecipe(PieceTypes.STAIRS,1,PieceTypes.CORNER);
	public static final ShapelessPieceRecipe SLAB_TO_SIDING = new ShapelessPieceRecipe(PieceTypes.SIDING,1,PieceTypes.SLAB);
	public static final ShapelessPieceRecipe SIDING_TO_SLAB = new ShapelessPieceRecipe(PieceTypes.SLAB,1,PieceTypes.SIDING);
	public static final ShapelessPieceRecipe SLABS_TO_BASE = new ShapelessPieceRecipe(PieceTypes.BASE,1,PieceTypes.SLAB,PieceTypes.SLAB);
	public static final ShapelessPieceRecipe SIDINGS_TO_BASE = new ShapelessPieceRecipe(PieceTypes.BASE,1,PieceTypes.SIDING,PieceTypes.SIDING);
	public static final ShapelessPieceRecipe FENCE_TO_POST = new ShapelessPieceRecipe(PieceTypes.POST,1,PieceTypes.FENCE);
	public static final ShapelessPieceRecipe POST_TO_FENCE = new ShapelessPieceRecipe(PieceTypes.FENCE,1,PieceTypes.POST);
	public static final ShapelessPieceRecipe WALL_TO_COLUMN = new ShapelessPieceRecipe(PieceTypes.COLUMN,1,PieceTypes.WALL);
	public static final ShapelessPieceRecipe COLUMN_TO_WALL = new ShapelessPieceRecipe(PieceTypes.WALL,1,PieceTypes.COLUMN);

	public static void init(ArtificeResourcePack.ServerResourcePackBuilder data) {
		int r = 0;
		int s = 0;
		for (PieceSet ps : PieceSets.registry.values()) {
			if(ps!=ModBlocks.DUMMY_PIECES) {
				for (PieceBlock pb : ps.getPieceBlocks()) {
					if (ps.isCraftable(pb.getType())) {
						int i = 0;
						for (ShapedPieceRecipe pr : pb.getType().getRecipes()) {
							Identifier bid = Registry.BLOCK.getId(pb.getBlock());
							Identifier id = ExtraPieces.getID(bid.getPath() + "_" + (i++));
							pr.add(data, id, ps);
							r++;
						}
						if(pb.getType()!=PieceTypes.BASE&&ps.isStonecuttable()) {
							Identifier bid = Registry.BLOCK.getId(pb.getBlock());
							Identifier id = ExtraPieces.getID(bid.getPath() + "_stonecutting");
							pb.getType().getStonecuttingRecipe().add(data,id,ps);
							s++;
						}
					}
				}
				if(ps.hasPiece(PieceTypes.STAIRS)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.STAIRS));
					Identifier id = ExtraPieces.getID( bid.getPath() + "_to_base");
					STAIRS_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceTypes.CORNER)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.CORNER));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
					CORNERS_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceTypes.STAIRS) && ps.hasPiece(PieceTypes.CORNER)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.CORNER));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_stairs");
					CORNER_TO_STAIRS.add(data, id, ps);
					r++;
					Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.STAIRS));
					Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_corner");
					STAIRS_TO_CORNER.add(data, id2, ps);
					r++;
				}
				if(ps.hasPiece(PieceTypes.WALL)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.WALL));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
					WALL_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceTypes.SLAB)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SLAB));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
					SLABS_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceTypes.SIDING)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SIDING));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
					SIDINGS_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceTypes.SLAB) && ps.hasPiece(PieceTypes.SIDING)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SLAB));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_siding");
					SLAB_TO_SIDING.add(data, id, ps);
					r++;
					Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.SIDING));
					Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_slab");
					SIDING_TO_SLAB.add(data, id2, ps);
					r++;
				}
				if(ps.hasPiece(PieceTypes.FENCE) && ps.hasPiece(PieceTypes.POST)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceTypes.FENCE));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_post");
					FENCE_TO_POST.add(data, id, ps);
					r++;
					Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceTypes.POST));
					Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_fence");
					POST_TO_FENCE.add(data, id2, ps);
					r++;
				}
				if(ps.hasPiece(PieceTypes.WALL) && ps.hasPiece(PieceTypes.COLUMN)) {
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
		}
		ExtraPieces.log("Added "+r+" crafting recipes!");
		ExtraPieces.log("Added "+s+" stonecutting recipes!");
	}

}
