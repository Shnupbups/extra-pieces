package com.shnupbups.extrapieces.core;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import com.shnupbups.extrapieces.pieces.*;
import com.shnupbups.extrapieces.register.ModConfigs;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

import java.util.HashSet;
import java.util.Optional;

public class PieceTypes {
	public static final PieceType BASE = new BasePiece();
	public static final PieceType STAIRS = new StairsPiece();
	public static final PieceType SLAB = new SlabPiece();
	public static final PieceType SIDING = new SidingPiece();
	public static final PieceType WALL = new WallPiece();
	public static final PieceType FENCE = new FencePiece();
	public static final PieceType FENCE_GATE = new FenceGatePiece();
	public static final PieceType POST = new PostPiece();
	public static final PieceType COLUMN = new ColumnPiece();
	public static final PieceType CORNER = new CornerPiece();
	public static final PieceType LAYER = new LayerPiece();
	
	public static final PieceType TRIM = new TrimPiece();

	private static HashSet<PieceType> types = new HashSet<PieceType>();

	public static void init() {
		register(PieceTypes.BASE);
		if(ModConfigs.stairs) register(PieceTypes.STAIRS);
		if(ModConfigs.slabs) register(PieceTypes.SLAB);
		if(ModConfigs.sidings) register(PieceTypes.SIDING);
		if(ModConfigs.walls) register(PieceTypes.WALL);
		if(ModConfigs.fences) register(PieceTypes.FENCE);
		if(ModConfigs.fenceGates) register(PieceTypes.FENCE_GATE);
		if(ModConfigs.posts) register(PieceTypes.POST);
		if(ModConfigs.columns) register(PieceTypes.COLUMN);
		if(ModConfigs.corners) register(PieceTypes.CORNER);
		if(ModConfigs.layers) register(PieceTypes.LAYER);
		register(PieceTypes.TRIM);
	}

	public static PieceType register(PieceType type) {
		if (types.add(type)) return type;
		return null;
	}

	public static HashSet<PieceType> getTypes() {
		return types;
	}

	public static HashSet<PieceType> getTypesNoBase() {
		HashSet<PieceType> nobase = new HashSet<>(getTypes());
		nobase.remove(PieceTypes.BASE);
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
				return PieceTypes.BASE;
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

	public static Optional<PieceType> getTypeOrEmpty(String id) {
		return Optional.ofNullable(getType(id));
	}

	public static PieceType readPieceType(PacketByteBuf buf) {
		return getType(buf.readString(buf.readInt()));
	}
}
