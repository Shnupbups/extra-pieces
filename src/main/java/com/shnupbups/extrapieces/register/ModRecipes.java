package com.shnupbups.extrapieces.register;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.core.*;
import com.swordglowsblue.artifice.api.ArtificeResourcePack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipes {

	public static final ShapedPieceRecipe STAIRS_TO_BASE = new ShapedPieceRecipe(PieceType.BASE,4,"ss","ss").addToKey('s',PieceType.STAIRS);
	public static final ShapedPieceRecipe CORNERS_TO_BASE = new ShapedPieceRecipe(PieceType.BASE,4,"ss","ss").addToKey('s',PieceType.CORNER);
	public static final ShapedPieceRecipe WALL_TO_BASE = new ShapedPieceRecipe(PieceType.BASE,4,"ss","ss").addToKey('s',PieceType.WALL);

	public static final ShapelessPieceRecipe STAIRS_TO_CORNER = new ShapelessPieceRecipe(PieceType.CORNER,1,PieceType.STAIRS);
	public static final ShapelessPieceRecipe CORNER_TO_STAIRS = new ShapelessPieceRecipe(PieceType.STAIRS,1,PieceType.CORNER);
	public static final ShapelessPieceRecipe SLAB_TO_SIDING = new ShapelessPieceRecipe(PieceType.SIDING,1,PieceType.SLAB);
	public static final ShapelessPieceRecipe SIDING_TO_SLAB = new ShapelessPieceRecipe(PieceType.SLAB,1,PieceType.SIDING);
	public static final ShapelessPieceRecipe SLABS_TO_BASE = new ShapelessPieceRecipe(PieceType.BASE,1,PieceType.SLAB,PieceType.SLAB);
	public static final ShapelessPieceRecipe SIDINGS_TO_BASE = new ShapelessPieceRecipe(PieceType.BASE,1,PieceType.SIDING,PieceType.SIDING);
	public static final ShapelessPieceRecipe FENCE_TO_POST = new ShapelessPieceRecipe(PieceType.POST,1,PieceType.FENCE);
	public static final ShapelessPieceRecipe POST_TO_FENCE = new ShapelessPieceRecipe(PieceType.FENCE,1,PieceType.POST);

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
						if(pb.getType()!=PieceType.BASE&&ps.isStonecuttable()) {
							Identifier bid = Registry.BLOCK.getId(pb.getBlock());
							Identifier id = ExtraPieces.getID(bid.getPath() + "_stonecutting");
							pb.getType().getStonecuttingRecipe().add(data,id,ps);
							s++;
						}
					}
				}
				if(ps.hasPiece(PieceType.STAIRS)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceType.STAIRS));
					Identifier id = ExtraPieces.getID( bid.getPath() + "_to_base");
					STAIRS_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceType.CORNER)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceType.CORNER));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
					CORNERS_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceType.STAIRS) && ps.hasPiece(PieceType.CORNER)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceType.CORNER));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_stairs");
					CORNER_TO_STAIRS.add(data, id, ps);
					r++;
					Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceType.STAIRS));
					Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_corner");
					STAIRS_TO_CORNER.add(data, id2, ps);
					r++;
				}
				if(ps.hasPiece(PieceType.WALL)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceType.WALL));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
					WALL_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceType.SLAB)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceType.SLAB));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
					SLABS_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceType.SIDING)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceType.SIDING));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_base");
					SIDINGS_TO_BASE.add(data, id, ps);
					r++;
				}
				if(ps.hasPiece(PieceType.SLAB) && ps.hasPiece(PieceType.SIDING)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceType.SLAB));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_siding");
					SLAB_TO_SIDING.add(data, id, ps);
					r++;
					Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceType.SIDING));
					Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_slab");
					SIDING_TO_SLAB.add(data, id2, ps);
					r++;
				}
				if(ps.hasPiece(PieceType.FENCE) && ps.hasPiece(PieceType.POST)) {
					Identifier bid = Registry.BLOCK.getId(ps.getPiece(PieceType.FENCE));
					Identifier id = ExtraPieces.getID(bid.getPath() + "_to_post");
					FENCE_TO_POST.add(data, id, ps);
					r++;
					Identifier bid2 = Registry.BLOCK.getId(ps.getPiece(PieceType.POST));
					Identifier id2 = ExtraPieces.getID(bid2.getPath() + "_to_fence");
					POST_TO_FENCE.add(data, id2, ps);
					r++;
				}
			}
		}
		ExtraPieces.log("Added "+r+" crafting recipes!");
		ExtraPieces.log("Added "+s+" stonecutting recipes!");
	}

}
