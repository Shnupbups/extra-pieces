package com.shnupbups.extrapieces;

import net.minecraft.block.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PiecesSet {
	public static BlockPiece[] NO_SLAB = {BlockPiece.STAIRS,BlockPiece.SIDING,BlockPiece.WALL,BlockPiece.FENCE,BlockPiece.FENCE_GATE};
	public static BlockPiece[] NO_SLAB_OR_STAIRS = {BlockPiece.SIDING,BlockPiece.WALL,BlockPiece.FENCE,BlockPiece.FENCE_GATE};
	public static BlockPiece[] NO_SLAB_STAIRS_OR_WALL = {BlockPiece.SIDING,BlockPiece.FENCE,BlockPiece.FENCE_GATE};

	private static Map<Block, PiecesSet> registry = new HashMap<Block, PiecesSet>();

	private final Block base;
	private final String name;
	private BlockPiece[] types;
	private Map<BlockPiece, Block> pieces = new HashMap<BlockPiece, Block>();
	private boolean registered = false;

	private PiecesSet(Block base, String name, BlockPiece... types) {
		this.base = base;
		this.name = name.toLowerCase();
		this.types = types;
		registry.put(base, this);
	}

	public static PiecesSet getSet(Block base) {
		if(hasSet(base)) return registry.get(base);
		else return null;
	}

	public static PiecesSet createSet(Block base, String name, BlockPiece... types) {
		if(hasSet(base)) throw new IllegalStateException("Base block "+base.getTranslationKey()+" already has PiecesSet in registry! Use getSet!");
		else return new PiecesSet(base, name, types);
	}

	public static PiecesSet createSet(Block base, String name) {
		return createSet(base, name, BlockPiece.values());
	}

	public static boolean hasSet(Block base) {
		return registry.containsKey(base);
	}

	public PiecesSet generate() {
		pieces.clear();
		if(shouldHavePiece(BlockPiece.STAIRS)) {
			pieces.put(BlockPiece.STAIRS, new StairsBlock(base.getDefaultState(),Block.Settings.copy(base)));
		}
		if(shouldHavePiece(BlockPiece.SLAB)) {
			pieces.put(BlockPiece.SLAB, new SlabBlock(Block.Settings.copy(base)));
		}
		if(shouldHavePiece(BlockPiece.SIDING)) {
			pieces.put(BlockPiece.SIDING, new SidingBlock(Block.Settings.copy(base)));
		}
		if(shouldHavePiece(BlockPiece.WALL)) {
			pieces.put(BlockPiece.WALL, new WallBlock(Block.Settings.copy(base)));
		}
		if(shouldHavePiece(BlockPiece.FENCE)) {
			pieces.put(BlockPiece.FENCE, new FenceBlock(Block.Settings.copy(base)));
		}
		if(shouldHavePiece(BlockPiece.FENCE_GATE)) {
			pieces.put(BlockPiece.FENCE_GATE, new FenceGateBlock(Block.Settings.copy(base)));
		}
		registry.put(base, this);
		return this;
	}

	public PiecesSet register() {
		if(isRegistered()) throw new IllegalStateException("Base block "+base.getTranslationKey()+" already has PiecesSet registered! Cannot register again!");
		if(!isGenerated()) generate();
		for(BlockPiece b : pieces.keySet()) {
			ModBlocks.register(b.getName(getName()), pieces.get(b));
		}
		registered = true;
		registry.put(base, this);
		return this;
	}

	public String getName() {
		return name;
	}

	public Block getPiece(BlockPiece piece) {
		if(!isGenerated()) generate();
		if(hasPiece(piece)) return pieces.get(piece);
		return null;
	}

	public StairsBlock getStairs() {
		return (StairsBlock)getPiece(BlockPiece.STAIRS);
	}

	public SlabBlock getSlab() {
		return (SlabBlock)getPiece(BlockPiece.SLAB);
	}

	public SidingBlock getSiding() {
		return (SidingBlock)getPiece(BlockPiece.SIDING);
	}

	public WallBlock getWall() {
		return (WallBlock)getPiece(BlockPiece.WALL);
	}

	public FenceBlock getFence() {
		return (FenceBlock)getPiece(BlockPiece.FENCE);
	}

	public FenceGateBlock getFenceGate() {
		return (FenceGateBlock)getPiece(BlockPiece.FENCE_GATE);
	}

	public Block getBase() {
		return base;
	}

	private boolean shouldHavePiece(BlockPiece piece) {
		return Arrays.asList(types).contains(piece);
	}

	public boolean hasPiece(BlockPiece piece) {
		return (shouldHavePiece(piece) && pieces.containsKey(piece));
	}

	public boolean isGenerated() {
		for(BlockPiece p : types) {
			if(!pieces.containsKey(p)) return false;
		}
		return true;
	}

	public boolean isRegistered() {
		return registered;
	}

	public enum BlockPiece {
		STAIRS,
		SLAB,
		SIDING,
		WALL,
		FENCE,
		FENCE_GATE;

		public String getName(String baseName) {
			return baseName+"_"+this.name().toLowerCase();
		}
	}
}
