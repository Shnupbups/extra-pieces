package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Random;

@SuppressWarnings("deprecation")
public class WallPieceBlock extends WallBlock implements PieceBlock {
	private final PieceSet set;

	public WallPieceBlock(PieceSet set) {
		super(FabricBlockSettings.copyOf(set.getBase()).materialColor(set.getBase().getDefaultMapColor()));
		this.set = set;
	}

	public Block getBlock() {
		return this;
	}

	public PieceSet getSet() {
		return set;
	}

	public PieceType getType() {
		return PieceTypes.WALL;
	}

	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		super.randomDisplayTick(blockState_1, world_1, blockPos_1, random_1);
		this.getBase().randomDisplayTick(this.getBaseState(), world_1, blockPos_1, random_1);
	}

	@Override
	public void onBlockBreakStart(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1) {
		super.onBlockBreakStart(blockState_1, world_1, blockPos_1, playerEntity_1);
		this.getBaseState().onBlockBreakStart(world_1, blockPos_1, playerEntity_1);
	}

	@Override
	public void onBroken(WorldAccess worldAccess_1, BlockPos blockPos_1, BlockState blockState_1) {
		super.onBroken(worldAccess_1, blockPos_1, blockState_1);
		this.getBase().onBroken(worldAccess_1, blockPos_1, blockState_1);
	}

	@Override
	public float getBlastResistance() {
		return this.getBase().getBlastResistance();
	}

	@Override
	public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		super.onBlockAdded(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
		if (blockState_1.getBlock() != blockState_2.getBlock()) {
			this.getBase().getDefaultState().neighborUpdate(world_1, blockPos_1, Blocks.AIR, blockPos_1, false);
			this.getBase().getDefaultState().onBlockAdded(world_1, blockPos_1, blockState_2, false);
		}
	}

	@Override
	public void onStateReplaced(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		super.onStateReplaced(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
		if (blockState_1.getBlock() != blockState_2.getBlock()) {
			this.getBaseState().onStateReplaced(world_1, blockPos_1, blockState_2, boolean_1);
		}
	}

	@Override
	public void onSteppedOn(World world_1, BlockPos blockPos_1, BlockState blockState_1, Entity entity_1) {
		super.onSteppedOn(world_1, blockPos_1, blockState_1, entity_1);
		try {
			this.getBase().onSteppedOn(world_1, blockPos_1, blockState_1, entity_1);
		} catch (IllegalArgumentException ignored) {
			ExtraPieces.debugLog("Caught an exception in onSteppedOn for "+this.getPieceString());
		}
	}

	@Override
	public void scheduledTick(BlockState blockState_1, ServerWorld world_1, BlockPos blockPos_1, Random random_1) {
		super.scheduledTick(blockState_1, world_1, blockPos_1, random_1);
		this.getBase().scheduledTick(this.getBaseState(), world_1, blockPos_1, random_1);
	}

	@Override
	public ActionResult onUse(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
		ActionResult a = super.onUse(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
		if(a.isAccepted() || this.getBaseState().onUse(world_1, playerEntity_1, hand_1, blockHitResult_1).isAccepted()) {
			return ActionResult.SUCCESS;
		} else {
			return ActionResult.PASS;
		}
	}

	@Override
	public void onDestroyedByExplosion(World world_1, BlockPos blockPos_1, Explosion explosion_1) {
		super.onDestroyedByExplosion(world_1, blockPos_1, explosion_1);
		this.getBase().onDestroyedByExplosion(world_1, blockPos_1, explosion_1);
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
		return getSet().isTransparent() ? (blockState_2.getBlock() == this || super.isSideInvisible(blockState_1, blockState_2, direction_1)) : super.isSideInvisible(blockState_1, blockState_2, direction_1);
	}
	
	@Override
	public boolean emitsRedstonePower(BlockState blockState_1) {
		return super.emitsRedstonePower(blockState_1) || this.getBaseState().emitsRedstonePower();
	}
	
	@Override
	public int getWeakRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		return this.getBaseState().getWeakRedstonePower(blockView_1, blockPos_1, direction_1);
	}
}
