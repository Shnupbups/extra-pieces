package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;

import java.util.Random;

public class FenceGatePieceBlock extends FenceGateBlock implements PieceBlock {

	private final PieceSet set;

	public FenceGatePieceBlock(PieceSet set) {
		super(Settings.copy(set.getBase()));
		this.set = set;
	}

	public Block getBlock() {
		return this;
	}

	public PieceSet getSet() {
		return set;
	}

	public PieceType getType() {
		return PieceType.FENCE_GATE;
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		this.set.getBase().randomDisplayTick(blockState_1, world_1, blockPos_1, random_1);
	}

	public void onBlockBreakStart(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1) {
		this.set.getBase().getDefaultState().onBlockBreakStart(world_1, blockPos_1, playerEntity_1);
	}

	public void onBroken(IWorld iWorld_1, BlockPos blockPos_1, BlockState blockState_1) {
		this.set.getBase().onBroken(iWorld_1, blockPos_1, blockState_1);
	}

	public float getBlastResistance() {
		return this.set.getBase().getBlastResistance();
	}

	public BlockRenderLayer getRenderLayer() {
		return this.set.getBase().getRenderLayer();
	}

	public int getTickRate(ViewableWorld viewableWorld_1) {
		return this.set.getBase().getTickRate(viewableWorld_1);
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
		return getSet().isTransparent() ? (blockState_2.getBlock() == this ? true : super.isSideInvisible(blockState_1, blockState_2, direction_1)) : super.isSideInvisible(blockState_1, blockState_2, direction_1);
	}
}
