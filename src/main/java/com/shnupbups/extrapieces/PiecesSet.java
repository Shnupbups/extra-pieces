package com.shnupbups.extrapieces;

import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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

	/**
	 * Gets a {@link PiecesSet} based on the {@link Block} {@code base}, if it exists.
	 * @param base The {@link Block} that the {@link PiecesSet} should be based upon.
	 * @return The {@link PiecesSet} based on the {@link Block} {@code base}, or null if none exists.
	 */
	public static PiecesSet getSet(Block base) {
		if(hasSet(base)) return registry.get(base);
		else return null;
	}

	/**
	 * Gets a {@link BlockPiece} based on the {@link Block} {@code base}, if it exists.
	 * @param base The {@link Block} that the {@link BlockPiece} should be based upon.
	 * @param piece The {@link BlockPiece} type to get.
	 * @return The {@link BlockPiece} based on the {@link Block} {@code base}, or null if none exists.
	 */
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

	/**
	 * Creates a new {@link PiecesSet} based on {@code base} with each of the {@link BlockPiece} types specified.
	 * @throws IllegalStateException If a {@link PiecesSet} already exists with an identical base {@link Block}
	 * @param base The {@link Block} on which the {@link BlockPiece}s will be based upon.
	 * @param name A string used to identify this {@link PiecesSet}. Used in registry names.
	 * @param types A list of {@link BlockPiece}s that this {@link PiecesSet} will have.
	 * @return A new {@link PiecesSet}
	 */
	public static PiecesSet createSet(Block base, String name, BlockPiece... types) {
		if(hasSet(base)) throw new IllegalStateException("Base block "+base.getTranslationKey()+" already has PiecesSet in registry! Use getSet!");
		else return new PiecesSet(base, name, types);
	}

	/**
	 * Creates a new {@link PiecesSet} based on {@code base} with every {@link BlockPiece} type.
	 * @throws IllegalStateException If a {@link PiecesSet} already exists with an identical base {@link Block}
	 * @param base The {@link Block} on which the {@link BlockPiece}s will be based upon.
	 * @param name A string used to identify this {@link PiecesSet}. Used in registry names.
	 * @return A new {@link PiecesSet}
	 */
	public static PiecesSet createSet(Block base, String name) {
		return createSet(base, name, BlockPiece.values());
	}

	/**
	 * Gets whether a {@link PiecesSet} exists based upon the {@link Block} {@code base}.
	 * @param base The {@link Block} that the {@link PiecesSet} should be based upon.
	 * @return Whether such a {@link PiecesSet} exists.
	 */
	public static boolean hasSet(Block base) {
		return registry.containsKey(base);
	}

	/**
	 * Creates the instances of each {@link BlockPiece} in this {@link PiecesSet}.<br>
	 * Clears any already generated.
	 * @return This {@link PiecesSet} with all {@link BlockPiece}s generated.
	 */
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

	/**
	 * Registers each {@link BlockPiece} in this {@link PiecesSet} to the {@link Registry}.<br>
	 * If {@link #isGenerated()} returns {@code false}, runs {@link #generate()}.
	 * @throws IllegalStateException If a {@link PiecesSet} has already been registered with the same base {@link Block}
	 * @return This {@link PiecesSet}
	 */
	public PiecesSet register() {
		if(isRegistered()) throw new IllegalStateException("Base block "+base.getTranslationKey()+" already has PiecesSet registered! Cannot register again!");
		if(!isGenerated()) generate();
		for(BlockPiece b : pieces.keySet()) {
			Registry.register(Registry.BLOCK, new Identifier("extrapieces",b.getName(getName())), pieces.get(b));
			BlockItem item = new BlockItem(pieces.get(b), (new Item.Settings()).itemGroup(ExtraPieces.groups.get(b)));
			item.registerBlockItemMap(Item.BLOCK_ITEM_MAP, item);
			Registry.register(Registry.ITEM, Registry.BLOCK.getId(pieces.get(b)), item);
		}
		registered = true;
		return this;
	}

	/**
	 * Gets the name of this {@link PiecesSet}.<br>
	 * Used for registry.
	 * @return The name of this {@link PiecesSet}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets a {@link BlockPiece} from this {@link PiecesSet}, if it exists.<br>
	 * If {@link #isGenerated()} returns {@code false}, runs {@link #generate()} first.
	 * @param piece The {@link BlockPiece} type to get.
	 * @return The {@link BlockPiece} from this {@link PiecesSet}, or null if no such BlockPiece exists.
	 */
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

	/**
	 * Gets the {@link Block} which this {@link PiecesSet} is based upon.
	 * @return The {@link Block} which this {@link PiecesSet} is based upon.
	 */
	public Block getBase() {
		return base;
	}

	private boolean shouldHavePiece(BlockPiece piece) {
		return Arrays.asList(types).contains(piece);
	}

	/**
	 * Gets whether this {@link PiecesSet} has a {@link BlockPiece} of type {@code piece}.
	 * @param piece The {@link BlockPiece} type to query for.
	 * @return Whether this {@link PiecesSet} has a {@link BlockPiece} of type {@code piece}.
	 */
	public boolean hasPiece(BlockPiece piece) {
		return (shouldHavePiece(piece) && pieces.containsKey(piece));
	}

	/**
	 * Gets whether each {@link BlockPiece} for this {@link PiecesSet} has been generated.<br>
	 * Generation is done with {@link #generate()}.
	 * @return Whether each {@link BlockPiece} for this {@link PiecesSet} has been generated.
	 */
	public boolean isGenerated() {
		for(BlockPiece p : types) {
			if(!pieces.containsKey(p)) return false;
		}
		return true;
	}

	/**
	 * Gets whether this {@link PiecesSet}'s {@link BlockPiece}s have been added to the {@link Registry}.<br>
	 * Registration is done with {@link #register()}.
	 * @return Whether this {@link PiecesSet}'s {@link BlockPiece}s have been added to the {@link Registry}.
	 */
	public boolean isRegistered() {
		return registered;
	}

	/**
	 * The different 'pieces' or shapes of blocks available in a {@link PiecesSet}.
	 */
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

		/**
		 * Gets the name of this {@link BlockPiece} type with {@code baseName_} appended to the front, in all lowercase.<br>
		 * Used for registry.
		 * @return The name of this {@link BlockPiece} type, in all lowercase.
		 */
		public String getName(String baseName) {
			return baseName.toLowerCase()+"_"+getName();
		}

		/**
		 * Gets the name of this {@link BlockPiece} type, in all lowercase<br>
		 * Used for registry.
		 * @return The name of this {@link BlockPiece} type, in all lowercase.
		 */
		public String getName() {
			return this.name().toLowerCase();
		}
	}
}
