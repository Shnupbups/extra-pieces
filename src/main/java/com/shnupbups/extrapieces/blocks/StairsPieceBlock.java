package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Random;

@SuppressWarnings("deprecation")
public class StairsPieceBlock extends StairsBlock implements PieceBlock {
	private final PieceSet set;

	public StairsPieceBlock(PieceSet set) {
		super(set.getBase().getDefaultState(), Settings.copy(set.getBase()));
		this.set = set;
	}

	public Block getBlock() {
		return this;
	}

	public PieceSet getSet() {
		return set;
	}

	public PieceType getType() {
		return PieceTypes.STAIRS;
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
		return getSet().isTransparent() ? (blockState_2.getBlock() == this || super.isSideInvisible(blockState_1, blockState_2, direction_1)) : super.isSideInvisible(blockState_1, blockState_2, direction_1);
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		try {
			this.getBase().randomDisplayTick(blockState_1, world_1, blockPos_1, random_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onBlockBreakStart(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1) {
		try {
			this.getBase().getDefaultState().onBlockBreakStart(world_1, blockPos_1, playerEntity_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onBroken(IWorld iWorld_1, BlockPos blockPos_1, BlockState blockState_1) {
		try {
			this.getBase().onBroken(iWorld_1, blockPos_1, blockState_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public float getBlastResistance() {
		try {
			return this.getBase().getBlastResistance();
		} catch (IllegalArgumentException ignored) {
			return this.resistance;
		}
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		try {
			return this.getBase().getRenderLayer();
		} catch (IllegalArgumentException ignored) {
			return BlockRenderLayer.SOLID;
		}
	}
	
	@Override
	public int getTickRate(ViewableWorld viewableWorld_1) {
		try {
			return this.getBase().getTickRate(viewableWorld_1);
		} catch (IllegalArgumentException ignored) {
			return 10;
		}
	}
	
	@Override
	public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		try {
			if (blockState_1.getBlock() != blockState_1.getBlock()) {
				this.getBase().getDefaultState().neighborUpdate(world_1, blockPos_1, Blocks.AIR, blockPos_1, false);
				this.getBase().onBlockAdded(this.set.getBase().getDefaultState(), world_1, blockPos_1, blockState_2, false);
			}
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		try {
			if (blockState_1.getBlock() != blockState_2.getBlock()) {
				this.getBase().getDefaultState().onBlockRemoved(world_1, blockPos_1, blockState_2, boolean_1);
			}
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onSteppedOn(World world_1, BlockPos blockPos_1, Entity entity_1) {
		try {
			this.getBase().onSteppedOn(world_1, blockPos_1, entity_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onScheduledTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		try {
			this.getBase().onScheduledTick(blockState_1, world_1, blockPos_1, random_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
		try {
			return this.getBase().getDefaultState().activate(world_1, playerEntity_1, hand_1, blockHitResult_1);
		} catch (IllegalArgumentException ignored) {
			return false;
		}
	}
	
	@Override
	public void onDestroyedByExplosion(World world_1, BlockPos blockPos_1, Explosion explosion_1) {
		try {
			this.getBase().onDestroyedByExplosion(world_1, blockPos_1, explosion_1);
		} catch (IllegalArgumentException ignored) {}
	}
}
