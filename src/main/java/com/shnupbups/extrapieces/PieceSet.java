package com.shnupbups.extrapieces;

import com.shnupbups.extrapieces.blocks.PieceBlock;
import net.minecraft.ChatFormat;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class PieceSet {
	public static final ArrayList<PieceType> NO_SLAB;
	public static final ArrayList<PieceType> NO_SLAB_OR_STAIRS;
	public static final ArrayList<PieceType> NO_SLAB_STAIRS_OR_WALL;
	public static final ArrayList<PieceType> JUST_EXTRAS_AND_WALL;
	public static final ArrayList<PieceType> JUST_EXTRAS_AND_FENCE_GATE;

	private static Map<Block, PieceSet> registry = new HashMap<>();
	private static Map<Block, FakePieceBlock> vanillaPieceRegistry = new HashMap<>();

	private final Block base;
	private final String name;
	private PieceType[] genTypes;
	private Map<PieceType, PieceBlock> pieces = new HashMap<>();
	private boolean registered = false;
	private boolean stonecuttable;
	private ArrayList<PieceType> uncraftable = new ArrayList<>();

	private PieceSet(Block base, String name, List<PieceType> types) {
		this.base = base;
		this.name = name.toLowerCase();
		this.genTypes = types.toArray(new PieceType[types.size()]);
		registry.put(base, this);
		stonecuttable = (base.getMaterial(base.getDefaultState()).equals(Material.STONE) || base.getMaterial(base.getDefaultState()).equals(Material.METAL));
	}

	/**
	 * Adds a vanilla (or just not-generated) block as a piece to this set.
	 * @param type The piece type of the block
	 * @param block The block to add
	 * @return This set
	 */
	public PieceSet addVanillaPiece(PieceType type, Block block) {
		FakePieceBlock fpb = new FakePieceBlock(block, type, getBase());
		vanillaPieceRegistry.put(block, fpb);
		pieces.put(type, fpb);
		setUncraftable(type);
		//System.out.println("ADDING VANILLA PIECE! "+block+" as type "+type+" to set "+getName()+" with base "+getBase());
		return this;
	}

	public static boolean isVanillaPiece(Block block) { return vanillaPieceRegistry.containsKey(block); }

	public boolean isVanillaPiece(PieceType type) { return pieces.get(type) instanceof FakePieceBlock && vanillaPieceRegistry.containsValue(pieces.get(type)); }

	public static boolean isPiece(Block block) {
		return (block instanceof PieceBlock || hasSet(block) || isVanillaPiece(block));
	}

	public static PieceType getType(Block block) {
		if(isPiece(block)) {
			if(hasSet(block)) return PieceType.BASE;
			else if(block instanceof PieceBlock) return ((PieceBlock)block).getType();
			else if(vanillaPieceRegistry.containsKey(block)) return vanillaPieceRegistry.get(block).getType();
		}
		return null;
	}

	/**
	 * Gets a blcok's PieceSet
	 * @param block The {@link Block} to find the set of.
	 * @return The {@link PieceSet} based on the {@link Block} {@code base}, or null if none exists.
	 */
	public static PieceSet getSet(Block block) {
		if(hasSet(block)) return registry.get(block);
		else if(block instanceof PieceBlock) return registry.get(((PieceBlock)block).getBase());
		return registry.get(vanillaPieceRegistry.getOrDefault(block, null).getBase());
	}

	public static PieceBlock asPieceBlock(Block block) {
		if(block instanceof PieceBlock) return (PieceBlock)block;
		else if(vanillaPieceRegistry.containsKey(block)) {
			return vanillaPieceRegistry.get(block);
		} return null;
	}

	/**
	 * Gets a {@link PieceType} based on the {@link Block} {@code base}, if it exists.
	 * @param base The {@link Block} that the {@link PieceType} should be based upon.
	 * @param piece The {@link PieceType} to get.
	 * @return The {@link PieceType} based on the {@link Block} {@code base}, or null if none exists.
	 */
	public static Block getPiece(Block base, PieceType piece) {
		//System.out.println("GETTING PIECE FOR PIECESET: "+base.getTranslationKey()+" piece: "+piece.toString()+" ");
		if(hasSet(base)) {
			if(getSet(base).hasPiece(piece)) {
				//System.out.println("EXISTS! "+getSet(base).toString());
				return getSet(base).getPiece(piece);
			} else if(piece==PieceType.BASE) {
				return getSet(base).getBase();
			}
		}
		return null;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PieceSet{ base: ").append(getBase()).append(", pieces: [");
		for(PieceType p:pieces.keySet()) {
			sb.append("{").append(p.toString()).append(" = ").append(pieces.get(p).getBlock());
			if(isVanillaPiece(p)) sb.append(", Vanilla Piece!");
			else if(!isCraftable(p)) sb.append(", Uncraftable!");
			sb.append("} , ");
		}
		sb.delete(sb.length()-2,sb.length());
		sb.append("] , stonecuttable: ").append(isStonecuttable()).append(" }");
		return sb.toString();
	}

	public static PieceSet createSet(Block base, String name, List<PieceType> types) {
		if(hasSet(base)) throw new IllegalStateException("Block "+base.getTranslationKey()+" already has PiecesSet in registry! Use getSet!");
		else {
			if(types.contains(PieceType.BASE)) types.remove(PieceType.BASE);
			return new PieceSet(base, name, types);
		}
	}

	public boolean isStonecuttable() {
		return stonecuttable;
	}

	public PieceSet setStonecuttable(boolean stonecuttable) {
		this.stonecuttable=stonecuttable;
		return this;
	}

	public boolean isCraftable(PieceType type) {
		return !uncraftable.contains(type);
	}

	public PieceSet setUncraftable(PieceType type) {
		uncraftable.add(type);
		return this;
	}

	/**
	 * Creates a new {@link PieceSet} based on {@code base} with each of the {@link PieceType}s specified.
	 * @throws IllegalStateException If a {@link PieceSet} already exists with an identical base {@link Block}
	 * @param base The {@link Block} on which the {@link PieceType}s will be based upon.
	 * @param name A string used to identify this {@link PieceSet}. Used in registry names.
	 * @param types A list of {@link PieceType}s that this {@link PieceSet} will have.
	 * @return A new {@link PieceSet}
	 */
	public static PieceSet createSet(Block base, String name, PieceType... types) {
		return createSet(base, name, Arrays.asList(types));
	}

	/**
	 * Creates a new {@link PieceSet} based on {@code base} with every {@link PieceType}.
	 * @throws IllegalStateException If a {@link PieceSet} already exists with an identical base {@link Block}
	 * @param base The {@link Block} on which the {@link PieceType}s will be based upon.
	 * @param name A string used to identify this {@link PieceSet}. Used in registry names.
	 * @return A new {@link PieceSet}
	 */
	public static PieceSet createSet(Block base, String name) {
		return createSet(base, name, PieceType.getTypesNoBase());
	}

	/**
	 * Gets whether a {@link PieceSet} exists based upon the {@link Block} {@code base}.
	 * @param base The {@link Block} that the {@link PieceSet} should be based upon.
	 * @return Whether such a {@link PieceSet} exists.
	 */
	public static boolean hasSet(Block base) {
		return registry.containsKey(base);
	}

	/**
	 * Creates the instances of each {@link PieceType} in this {@link PieceSet}.
	 * @return This {@link PieceSet} with all {@link PieceType}s generated.
	 */
	public PieceSet generate() {
		for (PieceType p: PieceType.getTypes()) {
			if(shouldGenPiece(p) && !hasPiece(p)) {
				pieces.put(p, (PieceBlock)p.getNew(base));
			}
		}
		return this;
	}

	/**
	 * Registers each {@link PieceType} in this {@link PieceSet} to the {@link Registry}.<br>
	 * If {@link #isGenerated()} returns {@code false}, runs {@link #generate()}.
	 * @throws IllegalStateException If a {@link PieceSet} has already been registered with the same base {@link Block}
	 * @return This {@link PieceSet}
	 */
	public PieceSet register() {
		if(isRegistered()) throw new IllegalStateException("Base block "+base.getTranslationKey()+" already has PiecesSet registered! Cannot register again!");
		if(!isGenerated()) generate();
		for(PieceType b : genTypes) {
			Registry.register(Registry.BLOCK, new Identifier(b.getId().getNamespace(),b.getBlockId(getName())), pieces.get(b).getBlock());
			BlockItem item = new BlockItem(pieces.get(b).getBlock(), (new Item.Settings()).group(ExtraPieces.groups.get(b)));
			item.appendBlocks(Item.BLOCK_ITEMS, item);
			Registry.register(Registry.ITEM, Registry.BLOCK.getId(pieces.get(b).getBlock()), item);
		}
		registered = true;
		//System.out.println("DEBUG! PieceSet register: "+this.toString());
		return this;
	}

	/**
	 * Gets the name of this {@link PieceSet}.<br>
	 * Used for registry.
	 * @return The name of this {@link PieceSet}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets a {@link PieceType} from this {@link PieceSet}, if it exists.<br>
	 * If {@link #isGenerated()} returns {@code false}, runs {@link #generate()} first.
	 * @param piece The {@link PieceType} to get.
	 * @return The {@link PieceType} from this {@link PieceSet}, or null if no such PieceType exists.
	 */
	public Block getPiece(PieceType piece) {
		if(!isGenerated()) generate();
		if(hasPiece(piece)) return pieces.get(piece).getBlock();
		return null;
	}

	/**
	 * Gets the {@link Block} which this {@link PieceSet} is based upon.
	 * @return The {@link Block} which this {@link PieceSet} is based upon.
	 */
	public Block getBase() {
		return base;
	}

	private boolean shouldGenPiece(PieceType piece) {
		return Arrays.asList(genTypes).contains(piece);
	}

	/**
	 * Gets whether this {@link PieceSet} has a {@link PieceType} of type {@code piece}.
	 * @param piece The {@link PieceType} to query for.
	 * @return Whether this {@link PieceSet} has a {@link PieceType} of type {@code piece}.
	 */
	public boolean hasPiece(PieceType piece) {
		return (pieces.containsKey(piece));
	}

	public ArrayList<PieceType> getPieceTypes() {
		return new ArrayList<>(pieces.keySet());
	}

	public ArrayList<PieceBlock> getPieceBlocks() {
		return new ArrayList<>(pieces.values());
	}

	/**
	 * Gets whether each {@link PieceType} for this {@link PieceSet} has been generated.<br>
	 * Generation is done with {@link #generate()}.
	 * @return Whether each {@link PieceType} for this {@link PieceSet} has been generated.
	 */
	public boolean isGenerated() {
		for(PieceType p : genTypes) {
			if(!pieces.containsKey(p)) return false;
		}
		return true;
	}

	/**
	 * Gets whether this {@link PieceSet}'s {@link PieceType}s have been added to the {@link Registry}.<br>
	 * Registration is done with {@link #register()}.
	 * @return Whether this {@link PieceSet}'s {@link PieceType}s have been added to the {@link Registry}.
	 */
	public boolean isRegistered() {
		return registered;
	}

	static {
		NO_SLAB = new ArrayList<>(PieceType.getTypesNoBase());
		NO_SLAB.remove(PieceType.SLAB);

		NO_SLAB_OR_STAIRS = new ArrayList<>(NO_SLAB);
		NO_SLAB_OR_STAIRS.remove(PieceType.STAIRS);

		NO_SLAB_STAIRS_OR_WALL = new ArrayList<>(NO_SLAB_OR_STAIRS);
		NO_SLAB_STAIRS_OR_WALL.remove(PieceType.WALL);

		JUST_EXTRAS_AND_WALL = new ArrayList<>(NO_SLAB_OR_STAIRS);
		JUST_EXTRAS_AND_WALL.remove(PieceType.FENCE);
		JUST_EXTRAS_AND_WALL.remove(PieceType.FENCE_GATE);

		JUST_EXTRAS_AND_FENCE_GATE = new ArrayList<>(NO_SLAB_STAIRS_OR_WALL);
		JUST_EXTRAS_AND_FENCE_GATE.remove(PieceType.FENCE);
	}
}
