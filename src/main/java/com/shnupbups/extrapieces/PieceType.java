package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.blocks.*;
import net.minecraft.block.*;
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

	public static Optional<PieceType> getTypeOrEmpty(Identifier id) {
		return Optional.ofNullable(getType(id));
	}

	public static PieceType getType(String id) {
		Identifier idt = new Identifier(id);
		return getType(idt);
	}

	public abstract Block getNew(Block base);

	public static PieceType readPieceType(PacketByteBuf buf) {
		return getType(buf.readString(buf.readInt()));
	}

	public PacketByteBuf writePieceType(PacketByteBuf buf) {
		buf.writeInt(getId().toString().length());
		buf.writeString(getId().toString());
		return buf;
	}

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

		public StairsPieceBlock getNew(Block base) {
			return new StairsPieceBlock(base);
		}
	}

	public static class SlabPiece extends PieceType {
		public SlabPiece() {
			super("slab");
		}

		public SlabPieceBlock getNew(Block base) {
			return new SlabPieceBlock(base);
		}
	}

	public static class SidingPiece extends PieceType {
		public SidingPiece() {
			super("siding");
		}

		public SidingPieceBlock getNew(Block base) {
			return new SidingPieceBlock(base);
		}
	}

	public static class WallPiece extends PieceType {
		public WallPiece() {
			super("wall");
		}

		public WallPieceBlock getNew(Block base) {
			return new WallPieceBlock(base);
		}
	}

	public static class FencePiece extends PieceType {
		public FencePiece() {
			super("fence");
		}

		public FencePieceBlock getNew(Block base) {
			return new FencePieceBlock(base);
		}
	}

	public static class FenceGatePiece extends PieceType {
		public FenceGatePiece() {
			super("fence_gate");
		}

		public FenceGatePieceBlock getNew(Block base) {
			return new FenceGatePieceBlock(base);
		}
	}

	public static class PostPiece extends PieceType {
		public PostPiece() {
			super("post");
		}

		public PostPieceBlock getNew(Block base) {
			return new PostPieceBlock(base);
		}
	}

	public static class CornerPiece extends PieceType {
		public CornerPiece() {
			super("corner");
		}

		public CornerPieceBlock getNew(Block base) {
			return new CornerPieceBlock(base);
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
