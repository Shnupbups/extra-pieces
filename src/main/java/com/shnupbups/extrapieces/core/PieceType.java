package com.shnupbups.extrapieces.core;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.blocks.*;
import com.shnupbups.extrapieces.recipe.ShapedPieceRecipe;
import com.shnupbups.extrapieces.recipe.StonecuttingPieceRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.ArrayList;
import java.util.Optional;

public abstract class PieceType {
	public static final PieceType BASE = new BasePiece();
	public static final PieceType STAIRS = new StairsPiece();
	public static final PieceType SLAB = new SlabPiece();
	public static final PieceType SIDING = new SidingPiece();
	public static final PieceType WALL = new WallPiece();
	public static final PieceType FENCE = new FencePiece();
	public static final PieceType FENCE_GATE = new FenceGatePiece();
	public static final PieceType POST = new PostPiece();
	//public static final PieceType COLUMN = new ColumnPiece();
	public static final PieceType CORNER = new CornerPiece();

	private static ArrayList<PieceType> types = new ArrayList<PieceType>();

	static {
		register(PieceType.BASE);
		register(PieceType.STAIRS);
		register(PieceType.SLAB);
		register(PieceType.SIDING);
		register(PieceType.WALL);
		register(PieceType.FENCE);
		register(PieceType.FENCE_GATE);
		register(PieceType.POST);
		register(PieceType.CORNER);
	}

	private final Identifier id;

	public PieceType(String id) {
		this(ExtraPieces.getID(id));
	}

	public PieceType(Identifier id) {
		this.id = id;
	}

	public static PieceType register(PieceType type) {
		if (types.add(type)) return type;
		return null;
	}

	public static ArrayList<PieceType> getTypes() {
		return types;
	}

	public static ArrayList<PieceType> getTypesNoBase() {
		ArrayList<PieceType> nobase = new ArrayList<>(getTypes());
		nobase.remove(PieceType.BASE);
		return nobase;
	}

	public static PieceType getType(Identifier id) {
		for (PieceType p : types) {
			if (p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

	public static PieceType getType(ItemStack stack) {
		if (stack.getItem() instanceof BlockItem) {
			Block block = ((BlockItem) stack.getItem()).getBlock();
			if (block instanceof PieceBlock) {
				return ((PieceBlock) block).getType();
			} else if (PieceSets.hasSet(block)) {
				return PieceType.BASE;
			}
		}
		return null;
	}

	public static Optional<PieceType> getTypeOrEmpty(Identifier id) {
		return Optional.ofNullable(getType(id));
	}

	public static PieceType getType(String id) {
		Identifier idt = new Identifier(id);
		return getType(idt);
	}

	public static PieceType readPieceType(PacketByteBuf buf) {
		return getType(buf.readString(buf.readInt()));
	}

	/**
	 * Gets the name of this {@link PieceType} with {@code baseName_} appended to the front, in all lowercase.<br>
	 * Used for registry.
	 *
	 * @return The name of this {@link PieceType}, in all lowercase.
	 */
	public String getBlockId(String baseName) {
		return baseName.toLowerCase() + "_" + getId().getPath();
	}

	/**
	 * Gets the id of this {@link PieceType}<br>
	 * Used for registry.
	 *
	 * @return The id of this {@link PieceType}
	 */
	public Identifier getId() {
		return this.id;
	}

	public String getTranslationKey() {
		return "piece." + id.getNamespace() + "." + id.getPath();
	}

	/**
	 * Gets the id of the block and item tag of this {@link PieceType}<br>
	 * Used for registry.<br>
	 * Defaults to {@link #getId()} wth an 's' appended
	 *
	 * @return The id of this {@link PieceType}'s tag
	 */
	public Identifier getTagId() {
		return new Identifier(this.id.toString() + "s");
	}

	public abstract Block getNew(PieceSet set);

	public PacketByteBuf writePieceType(PacketByteBuf buf) {
		buf.writeInt(getId().toString().length());
		buf.writeString(getId().toString());
		return buf;
	}

	public String toString() {
		return getId().toString();
	}

	public ArrayList<ShapedPieceRecipe> getRecipes() {
		return new ArrayList<>();
	}

	public StonecuttingPieceRecipe getStonecuttingRecipe() {
		return new StonecuttingPieceRecipe(this,getStonecuttingCount(),PieceType.BASE);
	}

	public int getStonecuttingCount() {
		return 1;
	}

	public static final class BasePiece extends PieceType {
		public BasePiece() {
			super("base");
		}

		public String getBlockId(String baseName) {
			return baseName;
		}

		public Block getNew(PieceSet set) {
			return set.getBase();
		}
	}

	public static class StairsPiece extends PieceType {
		public StairsPiece() {
			super("stairs");
		}

		public StairsPieceBlock getNew(PieceSet set) {
			return new StairsPieceBlock(set);
		}

		public Identifier getTagId() {
			return new Identifier("minecraft", "stairs");
		}

		public ArrayList<ShapedPieceRecipe> getRecipes() {
			ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
			recipes.add(new ShapedPieceRecipe(this,4,"b  ", "bb ", "bbb").addToKey('b',BASE));
			return recipes;
		}
	}

	public static class SlabPiece extends PieceType {
		public SlabPiece() {
			super("slab");
		}

		public SlabPieceBlock getNew(PieceSet set) {
			return new SlabPieceBlock(set);
		}

		public Identifier getTagId() {
			return new Identifier("minecraft", "slabs");
		}

		public ArrayList<ShapedPieceRecipe> getRecipes() {
			ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
			recipes.add(new ShapedPieceRecipe(this,6,"bbb").addToKey('b',BASE));
			return recipes;
		}

		public int getStonecuttingCount() {
			return 2;
		}
	}

	public static class SidingPiece extends PieceType {
		public SidingPiece() {
			super("siding");
		}

		public SidingPieceBlock getNew(PieceSet set) {
			return new SidingPieceBlock(set);
		}

		public ArrayList<ShapedPieceRecipe> getRecipes() {
			ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
			recipes.add(new ShapedPieceRecipe(this,6,"b", "b", "b").addToKey('b',BASE));
			return recipes;
		}

		public int getStonecuttingCount() {
			return 2;
		}
	}

	public static class WallPiece extends PieceType {
		public WallPiece() {
			super("wall");
		}

		public WallPieceBlock getNew(PieceSet set) {
			return new WallPieceBlock(set);
		}

		public Identifier getTagId() {
			return new Identifier("minecraft", "walls");
		}

		public ArrayList<ShapedPieceRecipe> getRecipes() {
			ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
			recipes.add(new ShapedPieceRecipe(this,6,"bbb", "bbb").addToKey('b',BASE));
			return recipes;
		}
	}

	public static class FencePiece extends PieceType {
		public FencePiece() {
			super("fence");
		}

		public FencePieceBlock getNew(PieceSet set) {
			return new FencePieceBlock(set);
		}

		public Identifier getTagId() {
			return new Identifier("minecraft", "fences");
		}
	}

	public static class FenceGatePiece extends PieceType {
		public FenceGatePiece() {
			super("fence_gate");
		}

		public FenceGatePieceBlock getNew(PieceSet set) {
			return new FenceGatePieceBlock(set);
		}
	}

	public static class PostPiece extends PieceType {
		public PostPiece() {
			super("post");
		}

		public PostPieceBlock getNew(PieceSet set) {
			return new PostPieceBlock(set);
		}
	}

	public static class CornerPiece extends PieceType {
		public CornerPiece() {
			super("corner");
		}

		public CornerPieceBlock getNew(PieceSet set) {
			return new CornerPieceBlock(set);
		}

		public ArrayList<ShapedPieceRecipe> getRecipes() {
			ArrayList<ShapedPieceRecipe> recipes = super.getRecipes();
			recipes.add(new ShapedPieceRecipe(this,4,"bbb", "bb ", "b  ").addToKey('b',BASE));
			return recipes;
		}
	}
}
