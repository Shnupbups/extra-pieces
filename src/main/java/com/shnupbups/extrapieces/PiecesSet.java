package com.shnupbups.extrapieces;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.block.BlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PiecesSet {
	public static BlockPiece[] NO_SLAB = {BlockPiece.STAIRS,BlockPiece.SIDING,BlockPiece.WALL,BlockPiece.FENCE,BlockPiece.FENCE_GATE,BlockPiece.POST,/*BlockPiece.COLUMN,*/BlockPiece.CORNER};
	public static BlockPiece[] NO_SLAB_OR_STAIRS = {BlockPiece.SIDING,BlockPiece.WALL,BlockPiece.FENCE,BlockPiece.FENCE_GATE,BlockPiece.POST,/*BlockPiece.COLUMN,*/BlockPiece.CORNER};
	public static BlockPiece[] NO_SLAB_STAIRS_OR_WALL = {BlockPiece.SIDING,BlockPiece.FENCE,BlockPiece.FENCE_GATE,BlockPiece.POST,/*BlockPiece.COLUMN,*/BlockPiece.CORNER};
	public static BlockPiece[] JUST_EXTRAS_AND_WALL = {BlockPiece.WALL,BlockPiece.SIDING,BlockPiece.POST,/*BlockPiece.COLUMN,*/BlockPiece.CORNER};
	public static BlockPiece[] JUST_EXTRAS_AND_FENCE_GATE = {BlockPiece.FENCE_GATE,BlockPiece.SIDING,BlockPiece.POST,/*BlockPiece.COLUMN,*/BlockPiece.CORNER};

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

	public static Block getPiece(Block base, BlockPiece piece) {
		if(getSet(base)!=null && getSet(base).hasPiece(piece)) return getSet(base).getPiece(piece);
		return null;
	}

	public static StairsBlock getStairs(Block base) {
		return (StairsBlock)getPiece(base, BlockPiece.STAIRS);
	}

	public static SlabBlock getSlab(Block base) {
		return (SlabBlock)getPiece(base, BlockPiece.SLAB);
	}

	public static SidingBlock getSiding(Block base) {
		return (SidingBlock)getPiece(base, BlockPiece.SIDING);
	}

	public static WallBlock getWall(Block base) {
		return (WallBlock)getPiece(base, BlockPiece.WALL);
	}

	public static FenceBlock getFence(Block base) {
		return (FenceBlock)getPiece(base, BlockPiece.FENCE);
	}

	public static FenceGateBlock getFenceGate(Block base) {
		return (FenceGateBlock)getPiece(base, BlockPiece.FENCE_GATE);
	}

	public static PostBlock getPost(Block base) {
		return (PostBlock)getPiece(base, BlockPiece.POST);
	}

	/*public static ColumnBlock getColumn(Block base) {
		return (ColumnBlock)getPiece(base, BlockPiece.COLUMN);
	}*/

	public static CornerBlock getCorner(Block base) {
		return (CornerBlock)getPiece(base, BlockPiece.CORNER);
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
			pieces.put(BlockPiece.STAIRS, new StairsBlock(base.getDefaultState(),Block.Settings.copy(getBase())));
		}
		if(shouldHavePiece(BlockPiece.SLAB)) {
			pieces.put(BlockPiece.SLAB, new SlabBlock(Block.Settings.copy(getBase())));
		}
		if(shouldHavePiece(BlockPiece.SIDING)) {
			pieces.put(BlockPiece.SIDING, new SidingBlock(Block.Settings.copy(getBase())));
		}
		if(shouldHavePiece(BlockPiece.WALL)) {
			pieces.put(BlockPiece.WALL, new WallBlock(Block.Settings.copy(getBase())));
		}
		if(shouldHavePiece(BlockPiece.FENCE)) {
			pieces.put(BlockPiece.FENCE, new FenceBlock(Block.Settings.copy(getBase())));
		}
		if(shouldHavePiece(BlockPiece.FENCE_GATE)) {
			pieces.put(BlockPiece.FENCE_GATE, new FenceGateBlock(Block.Settings.copy(getBase())));
		}
		if(shouldHavePiece(BlockPiece.CORNER)) {
			pieces.put(BlockPiece.CORNER, new CornerBlock(base.getDefaultState(),Block.Settings.copy(getBase())));
		}
		if(shouldHavePiece(BlockPiece.POST)) {
			pieces.put(BlockPiece.POST, new PostBlock(Block.Settings.copy(getBase())));
		}
		return this;
	}

	public PiecesSet register() {
		if(isRegistered()) throw new IllegalStateException("Base block "+base.getTranslationKey()+" already has PiecesSet registered! Cannot register again!");
		if(!isGenerated()) generate();
		for(BlockPiece b : pieces.keySet()) {
			Registry.register(Registry.BLOCK, new Identifier("extrapieces",b.getName(getName())), pieces.get(b));
			Item item = new BlockItem(pieces.get(b), (new Item.Settings()).itemGroup(ExtraPieces.groups.get(b)));
			((BlockItem)item).registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
			Registry.register(Registry.ITEM, Registry.BLOCK.getId(pieces.get(b)), item);
		}
		registered = true;
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

	public PostBlock getPost() {
		return (PostBlock)getPiece(BlockPiece.POST);
	}

	/*public ColumnBlock getColumn() {
		return (ColumnBlock)getPiece(BlockPiece.COLUMN);
	}*/

	public CornerBlock getCorner() {
		return (CornerBlock)getPiece(BlockPiece.CORNER);
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
		FENCE_GATE,
		POST,
		//COLUMN,
		CORNER;

		public String getName(String baseName) {
			return baseName+"_"+this.name().toLowerCase();
		}
		public String getName() {
			return this.name().toLowerCase();
		}
	}
}
