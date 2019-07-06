package com.shnupbups.extrapieces.core;

import com.shnupbups.extrapieces.blocks.FakePieceBlock;
import com.shnupbups.extrapieces.blocks.PieceBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieceSets {
	public static Map<Block, PieceSet> registry = new HashMap<>();
	public static Map<Block, FakePieceBlock> vanillaPieceRegistry = new HashMap<>();

	public static boolean isVanillaPiece(Block block) {
		return vanillaPieceRegistry.containsKey(block);
	}

	public static FakePieceBlock registerVanillaPiece(Block b, FakePieceBlock fpb) {
		vanillaPieceRegistry.put(b, fpb);
		return fpb;
	}

	static PieceSet registerSet(Block base, PieceSet ps) {
		registry.put(base, ps);
		return ps;
	}

	public static boolean isPiece(Block block) {
		return (block instanceof PieceBlock || hasSet(block) || isVanillaPiece(block));
	}

	public static PieceType getType(Block block) {
		if (isPiece(block)) {
			if (hasSet(block)) return PieceTypes.BASE;
			else if (block instanceof PieceBlock) return ((PieceBlock) block).getType();
			else if (vanillaPieceRegistry.containsKey(block)) return vanillaPieceRegistry.get(block).getType();
		}
		return null;
	}

	/**
	 * Gets a blcok's {@link PieceSet}
	 *
	 * @param block The {@link Block} to find the set of.
	 * @return The {@link PieceSet} based on the {@link Block} {@code base}, or null if none exists.
	 */
	public static PieceSet getSet(Block block) {
		if (hasSet(block)) return registry.get(block);
		else if (block instanceof PieceBlock) return ((PieceBlock) block).getSet();
		return vanillaPieceRegistry.getOrDefault(block, null).getSet();
	}

	public static PieceBlock asPieceBlock(Block block) {
		if (block instanceof PieceBlock) return (PieceBlock) block;
		else if (vanillaPieceRegistry.containsKey(block)) {
			return vanillaPieceRegistry.get(block);
		}
		return null;
	}

	/**
	 * Gets a {@link PieceType} based on the {@link Block} {@code base}, if it exists.
	 *
	 * @param base  The {@link Block} that the {@link PieceType} should be based upon.
	 * @param piece The {@link PieceType} to get.
	 * @return The {@link PieceType} based on the {@link Block} {@code base}, or null if none exists.
	 */
	public static Block getPiece(Block base, PieceType piece) {
		//System.out.println("GETTING PIECE FOR PIECESET: "+base.getTranslationKey()+" piece: "+piece.toString()+" ");
		if (hasSet(base)) {
			if (getSet(base).hasPiece(piece)) {
				//System.out.println("EXISTS! "+getSet(base).toString());
				return getSet(base).getPiece(piece);
			} else if (piece == PieceTypes.BASE) {
				return getSet(base).getBase();
			}
		}
		return null;
	}

	public static PieceSet createSet(Block base, String name, List<PieceType> types) {
		if (hasSet(base))
			throw new IllegalStateException("Block " + base.getTranslationKey() + " already has PieceSet in registry! Use getSet!");
		else {
			if (types.contains(PieceTypes.BASE)) types.remove(PieceTypes.BASE);
			return new PieceSet(base, name, types);
		}
	}

	/**
	 * Creates a new {@link PieceSet} based on {@code base} with each of the {@link PieceType}s specified.
	 *
	 * @param base  The {@link Block} on which the {@link PieceType}s will be based upon.
	 * @param name  A string used to identify this {@link PieceSet}. Used in registry names.
	 * @param types A list of {@link PieceType}s that this {@link PieceSet} will have.
	 * @return A new {@link PieceSet}
	 * @throws IllegalStateException If a {@link PieceSet} already exists with an identical base {@link Block}
	 */
	public static PieceSet createSet(Block base, String name, PieceType... types) {
		return createSet(base, name, Arrays.asList(types));
	}

	/**
	 * Creates a new {@link PieceSet} based on {@code base} with every {@link PieceType}.
	 *
	 * @param base The {@link Block} on which the {@link PieceType}s will be based upon.
	 * @param name A string used to identify this {@link PieceSet}. Used in registry names.
	 * @return A new {@link PieceSet}
	 * @throws IllegalStateException If a {@link PieceSet} already exists with an identical base {@link Block}
	 */
	public static PieceSet createSet(Block base, String name) {
		return createSet(base, name, PieceType.getTypesNoBase());
	}

	/**
	 * Gets whether a {@link PieceSet} exists based upon the {@link Block} {@code base}.
	 *
	 * @param base The {@link Block} that the {@link PieceSet} should be based upon.
	 * @return Whether such a {@link PieceSet} exists.
	 */
	public static boolean hasSet(Block base) {
		return registry.containsKey(base);
	}

	public static Block getBase(ItemStack stack) {
		if (stack.getItem() instanceof BlockItem) {
			Block b = ((BlockItem) stack.getItem()).getBlock();
			if (hasSet(b)) return b;
			else if (isPiece(b)) {
				return asPieceBlock(((BlockItem) stack.getItem()).getBlock()).getSet().getBase();
			}
		}
		return null;
	}

}
