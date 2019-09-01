package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.ViewableWorld;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Random;

@SuppressWarnings("deprecation")
public class CornerPieceBlock extends Block implements Waterloggable, PieceBlock {
	public static final DirectionProperty FACING;
	public static final BooleanProperty WATERLOGGED;
	protected static final VoxelShape NORTH_SIDING_SHAPE;
	protected static final VoxelShape EAST_SIDING_SHAPE;
	protected static final VoxelShape SOUTH_SIDING_SHAPE;
	protected static final VoxelShape WEST_SIDING_SHAPE;
	protected static final VoxelShape NORTH_EXTRA_SHAPE;
	protected static final VoxelShape EAST_EXTRA_SHAPE;
	protected static final VoxelShape SOUTH_EXTRA_SHAPE;
	protected static final VoxelShape WEST_EXTRA_SHAPE;
	protected static final VoxelShape NORTH_SHAPE;
	protected static final VoxelShape EAST_SHAPE;
	protected static final VoxelShape SOUTH_SHAPE;
	protected static final VoxelShape WEST_SHAPE;

	static {
		FACING = Properties.HORIZONTAL_FACING;
		WATERLOGGED = Properties.WATERLOGGED;
		NORTH_SIDING_SHAPE = SidingPieceBlock.SINGLE_SHAPE_NORTH;
		EAST_SIDING_SHAPE = SidingPieceBlock.SINGLE_SHAPE_EAST;
		SOUTH_SIDING_SHAPE = SidingPieceBlock.SINGLE_SHAPE_SOUTH;
		WEST_SIDING_SHAPE = SidingPieceBlock.SINGLE_SHAPE_WEST;
		NORTH_EXTRA_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
		EAST_EXTRA_SHAPE = Block.createCuboidShape(8.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
		SOUTH_EXTRA_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 8.0D, 16.0D, 16.0D);
		WEST_EXTRA_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 8.0D);
		NORTH_SHAPE = VoxelShapes.union(NORTH_SIDING_SHAPE, NORTH_EXTRA_SHAPE);
		EAST_SHAPE = VoxelShapes.union(EAST_SIDING_SHAPE, EAST_EXTRA_SHAPE);
		SOUTH_SHAPE = VoxelShapes.union(SOUTH_SIDING_SHAPE, SOUTH_EXTRA_SHAPE);
		WEST_SHAPE = VoxelShapes.union(WEST_SIDING_SHAPE, WEST_EXTRA_SHAPE);
	}

	private final PieceSet set;

	public CornerPieceBlock(PieceSet set) {
		super(Block.Settings.copy(set.getBase()));
		this.set = set;
		this.setDefaultState(this.stateFactory.getDefaultState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
	}

	public PieceSet getSet() {
		return set;
	}

	public Block getBlock() {
		return this;
	}

	public PieceType getType() {
		return PieceTypes.CORNER;
	}

	public boolean hasSidedTransparency(BlockState blockState_1) {
		return true;
	}

	public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext verticalEntityPosition_1) {
		switch (blockState_1.get(FACING)) {
			case EAST:
				return EAST_SHAPE;
			case SOUTH:
				return SOUTH_SHAPE;
			case WEST:
				return WEST_SHAPE;
			default:
				return NORTH_SHAPE;
		}
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		super.randomDisplayTick(blockState_1, world_1, blockPos_1, random_1);
		try {
			this.getBase().randomDisplayTick(blockState_1, world_1, blockPos_1, random_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onBlockBreakStart(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1) {
		super.onBlockBreakStart(blockState_1, world_1, blockPos_1, playerEntity_1);
		try {
			this.getBase().getDefaultState().onBlockBreakStart(world_1, blockPos_1, playerEntity_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onBroken(IWorld iWorld_1, BlockPos blockPos_1, BlockState blockState_1) {
		super.onBroken(iWorld_1, blockPos_1, blockState_1);
		try {
			this.getBase().onBroken(iWorld_1, blockPos_1, blockState_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public float getBlastResistance() {
		try {
			return this.getBase().getBlastResistance();
		} catch (IllegalArgumentException ignored) {
			return super.getBlastResistance();
		}
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		try {
			return this.getBase().getRenderLayer();
		} catch (IllegalArgumentException ignored) {
			return super.getRenderLayer();
		}
	}
	
	@Override
	public int getTickRate(ViewableWorld viewableWorld_1) {
		try {
			return this.getBase().getTickRate(viewableWorld_1);
		} catch (IllegalArgumentException ignored) {
			return super.getTickRate(viewableWorld_1);
		}
	}
	
	@Override
	public void onBlockAdded(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		super.onBlockAdded(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
		try {
			if (blockState_1.getBlock() != blockState_1.getBlock()) {
				this.getBase().getDefaultState().neighborUpdate(world_1, blockPos_1, Blocks.AIR, blockPos_1, false);
				this.getBase().onBlockAdded(this.set.getBase().getDefaultState(), world_1, blockPos_1, blockState_2, false);
			}
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onBlockRemoved(BlockState blockState_1, World world_1, BlockPos blockPos_1, BlockState blockState_2, boolean boolean_1) {
		super.onBlockRemoved(blockState_1, world_1, blockPos_1, blockState_2, boolean_1);
		try {
			if (blockState_1.getBlock() != blockState_2.getBlock()) {
				this.getBase().getDefaultState().onBlockRemoved(world_1, blockPos_1, blockState_2, boolean_1);
			}
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onSteppedOn(World world_1, BlockPos blockPos_1, Entity entity_1) {
		super.onSteppedOn(world_1, blockPos_1, entity_1);
		try {
			this.getBase().onSteppedOn(world_1, blockPos_1, entity_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public void onScheduledTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		super.onScheduledTick(blockState_1, world_1, blockPos_1, random_1);
		try {
			this.getBase().onScheduledTick(blockState_1, world_1, blockPos_1, random_1);
		} catch (IllegalArgumentException ignored) {}
	}
	
	@Override
	public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
		try {
			boolean a = super.activate(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
			return a || this.getBase().getDefaultState().activate(world_1, playerEntity_1, hand_1, blockHitResult_1);
		} catch (IllegalArgumentException ignored) {
			return super.activate(blockState_1, world_1, blockPos_1, playerEntity_1, hand_1, blockHitResult_1);
		}
	}
	
	@Override
	public void onDestroyedByExplosion(World world_1, BlockPos blockPos_1, Explosion explosion_1) {
		super.onDestroyedByExplosion(world_1, blockPos_1, explosion_1);
		try {
			this.getBase().onDestroyedByExplosion(world_1, blockPos_1, explosion_1);
		} catch (IllegalArgumentException ignored) {}
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
		FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(blockPos_1);
		double xPos = itemPlacementContext_1.getHitPos().getX() - blockPos_1.getX();
		double zPos = itemPlacementContext_1.getHitPos().getZ() - blockPos_1.getZ();
		Direction direction_1 = itemPlacementContext_1.getPlayerFacing().getOpposite();
		if (direction_1 == Direction.EAST) {
			if (zPos < 0.5) direction_1 = direction_1.rotateYClockwise();
		} else if (direction_1 == Direction.WEST) {
			if (zPos > 0.5) direction_1 = direction_1.rotateYClockwise();
		} else if (direction_1 == Direction.SOUTH) {
			if (xPos > 0.5) direction_1 = direction_1.rotateYClockwise();
		} else {
			if (xPos < 0.5) direction_1 = direction_1.rotateYClockwise();
		}
		return this.getDefaultState().with(FACING, direction_1).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER);
	}

	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		if (blockState_1.get(WATERLOGGED)) {
			iWorld_1.getFluidTickScheduler().schedule(blockPos_1, Fluids.WATER, Fluids.WATER.getTickRate(iWorld_1));
		}

		return super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, iWorld_1, blockPos_1, blockPos_2);
	}

	protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.add(FACING, WATERLOGGED);
	}

	public FluidState getFluidState(BlockState blockState_1) {
		return blockState_1.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(blockState_1);
	}

	public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, BlockPlacementEnvironment blockPlacementEnvironment_1) {
		return false;
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
		return getSet().isTransparent() ? (blockState_2.getBlock() == this || super.isSideInvisible(blockState_1, blockState_2, direction_1)) : super.isSideInvisible(blockState_1, blockState_2, direction_1);
	}
}
