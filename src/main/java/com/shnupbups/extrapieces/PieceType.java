package com.shnupbups.extrapieces;

import net.minecraft.block.*;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

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

	private final Identifier id;

	public PieceType(String id) {
		this(new Identifier("extrapieces",id));
	}

	public PieceType(Identifier id) {
		this.id = id;
	}

	/**
	 * Gets the name of this {@link PieceType} with {@code baseName_} appended to the front, in all lowercase.<br>
	 * Used for registry.
	 * @return The name of this {@link PieceType}, in all lowercase.
	 */
	public String getBlockId(String baseName) {
		return baseName.toLowerCase()+"_"+getId().getPath();
	}

	/**
	 * Gets the id of this {@link PieceType}<br>
	 * Used for registry.
	 * @return The id of this {@link PieceType}
	 */
	public Identifier getId() {
		return this.id;
	}

	public static PieceType register(PieceType type) {
		if(types.add(type))	return type;
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
		for(PieceType p:types) {
			if(p.getId().equals(id)) {
				return p;
			}
		}
		return null;
	}

	public static PieceType getType(String id) {
		Identifier idt = new Identifier(id);
		return getType(idt);
	}

	public abstract Block getNew(Block base);

	public static class BasePiece extends PieceType {
		public BasePiece() {
			super("base");
		}

		public String getBlockId(String baseName) {
			return baseName;
		}

		public Block getNew(Block base) {
			return base;
		}
	}

	public static class StairsPiece extends PieceType {
		public StairsPiece() {
			super("stairs");
		}

		public StairsBlock getNew(Block base) {
			return new StairsBlock(base.getDefaultState(), Block.Settings.copy(base));
		}
	}

	public static class SlabPiece extends PieceType {
		public SlabPiece() {
			super("slab");
		}

		public SlabBlock getNew(Block base) {
			return new SlabBlock(Block.Settings.copy(base));
		}
	}

	public static class SidingPiece extends PieceType {
		public SidingPiece() {
			super("siding");
		}

		public SidingBlock getNew(Block base) {
			return new SidingBlock(Block.Settings.copy(base));
		}
	}

	public static class WallPiece extends PieceType {
		public WallPiece() {
			super("wall");
		}

		public WallBlock getNew(Block base) {
			return new WallBlock(Block.Settings.copy(base));
		}
	}

	public static class FencePiece extends PieceType {
		public FencePiece() {
			super("fence");
		}

		public FenceBlock getNew(Block base) {
			return new FenceBlock(Block.Settings.copy(base));
		}
	}

	public static class FenceGatePiece extends PieceType {
		public FenceGatePiece() {
			super("fence_gate");
		}

		public FenceGateBlock getNew(Block base) {
			return new FenceGateBlock(Block.Settings.copy(base));
		}
	}

	public static class PostPiece extends PieceType {
		public PostPiece() {
			super("post");
		}

		public PostBlock getNew(Block base) {
			return new PostBlock(Block.Settings.copy(base));
		}
	}

	public static class CornerPiece extends PieceType {
		public CornerPiece() {
			super("corner");
		}

		public CornerBlock getNew(Block base) {
			return new CornerBlock(base.getDefaultState(), Block.Settings.copy(base));
		}
	}

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
}
