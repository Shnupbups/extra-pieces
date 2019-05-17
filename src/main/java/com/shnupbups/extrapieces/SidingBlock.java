package com.shnupbups.extrapieces;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlacementEnvironment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.EntityContext;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;

public class SidingBlock extends Block implements Waterloggable {
	public static final EnumProperty<SidingType> TYPE;
	public static final BooleanProperty WATERLOGGED;
	public static final DirectionProperty FACING_HORIZONTAL;
	protected static final VoxelShape SINGLE_SHAPE_SOUTH;
	protected static final VoxelShape SINGLE_SHAPE_NORTH;
	protected static final VoxelShape SINGLE_SHAPE_EAST;
	protected static final VoxelShape SINGLE_SHAPE_WEST;

	public SidingBlock(Block.Settings block$Settings_1) {
		super(block$Settings_1);
		this.setDefaultState(this.getDefaultState().with(TYPE, SidingType.SINGLE).with(FACING_HORIZONTAL, Direction.NORTH).with(WATERLOGGED, false));
	}

	public boolean hasSidedTransparency(BlockState blockState_1) {
		return blockState_1.get(TYPE) != SidingType.DOUBLE;
	}

	protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.add(TYPE, FACING_HORIZONTAL, WATERLOGGED);
	}

	public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext verticalEntityPosition_1) {
		SidingType slabType_1 = blockState_1.get(TYPE);
		Direction facing = blockState_1.get(FACING_HORIZONTAL);
		switch(slabType_1) {
			case DOUBLE:
				return VoxelShapes.fullCube();
			default:
				switch(facing) {
					case SOUTH:
						return SINGLE_SHAPE_SOUTH;
					case EAST:
						return SINGLE_SHAPE_EAST;
					case WEST:
						return SINGLE_SHAPE_WEST;
					default:
						return SINGLE_SHAPE_NORTH;
				}
		}
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
		BlockState blockState_1 = itemPlacementContext_1.getWorld().getBlockState(blockPos_1);
		if (blockState_1.getBlock() == this) {
			return blockState_1.with(TYPE, SidingType.DOUBLE).with(FACING_HORIZONTAL,blockState_1.get(FACING_HORIZONTAL)).with(WATERLOGGED, false);
		} else {
			FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(blockPos_1);
			Direction playerHorizontalFacing = itemPlacementContext_1.getPlayerHorizontalFacing();
			Direction facing = itemPlacementContext_1.getFacing();
			double xPos = itemPlacementContext_1.getPos().getX() - blockPos_1.getX();
			double zPos = itemPlacementContext_1.getPos().getZ() - blockPos_1.getZ();
			Direction direction_1 = playerHorizontalFacing.getOpposite();
			if(facing.getAxis().isVertical()) {
				if(direction_1==Direction.EAST||direction_1==Direction.WEST) {
					if (xPos > 0.5) direction_1 = Direction.WEST;
					else direction_1 = Direction.EAST;
				} else {
					if(zPos>0.5) direction_1 = Direction.NORTH;
					else direction_1 = Direction.SOUTH;
				}
			}
			BlockState blockState_2 = this.getDefaultState().with(TYPE, SidingType.SINGLE).with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER).with(FACING_HORIZONTAL,direction_1);
			return blockState_2;
		}
	}

	public boolean canReplace(BlockState blockState_1, ItemPlacementContext itemPlacementContext_1) {
		if(itemPlacementContext_1.getPlayer().isSneaking()) return false;
		ItemStack itemStack_1 = itemPlacementContext_1.getItemStack();
		SidingType slabType_1 = blockState_1.get(TYPE);
		Direction facing = blockState_1.get(FACING_HORIZONTAL);
		if (slabType_1 != SidingType.DOUBLE && itemStack_1.getItem() == this.asItem()) {
			if (itemPlacementContext_1.canReplaceHitBlock()) {
				boolean boolean_1;
				switch(facing) {
					case EAST:
						boolean_1 = itemPlacementContext_1.getPos().x - (double)itemPlacementContext_1.getBlockPos().getX() > 0.5D;
						break;
					case WEST:
						boolean_1 = itemPlacementContext_1.getPos().x - (double)itemPlacementContext_1.getBlockPos().getX() < 0.5D;
						break;
					case SOUTH:
						boolean_1 = itemPlacementContext_1.getPos().z - (double)itemPlacementContext_1.getBlockPos().getZ() > 0.5D;
						break;
					default:
						boolean_1 = itemPlacementContext_1.getPos().z - (double)itemPlacementContext_1.getBlockPos().getZ() < 0.5D;
				}
				Direction direction_1 = itemPlacementContext_1.getFacing();
				return direction_1 == facing || boolean_1 && direction_1.getAxis().isVertical();
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public FluidState getFluidState(BlockState blockState_1) {
		return (Boolean)blockState_1.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(blockState_1);
	}

	public boolean tryFillWithFluid(IWorld iWorld_1, BlockPos blockPos_1, BlockState blockState_1, FluidState fluidState_1) {
		return blockState_1.get(TYPE) != SidingType.DOUBLE ? Waterloggable.super.tryFillWithFluid(iWorld_1, blockPos_1, blockState_1, fluidState_1) : false;
	}

	public boolean canFillWithFluid(BlockView blockView_1, BlockPos blockPos_1, BlockState blockState_1, Fluid fluid_1) {
		return blockState_1.get(TYPE) != SidingType.DOUBLE ? Waterloggable.super.canFillWithFluid(blockView_1, blockPos_1, blockState_1, fluid_1) : false;
	}

	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		if (blockState_1.get(WATERLOGGED) && direction_1 != blockState_1.get(FACING_HORIZONTAL).getOpposite()) {
			iWorld_1.getFluidTickScheduler().schedule(blockPos_1, Fluids.WATER, Fluids.WATER.getTickRate(iWorld_1));
		}

		return super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, iWorld_1, blockPos_1, blockPos_2);
	}

	public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, BlockPlacementEnvironment blockPlacementEnvironment_1) {
		switch(blockPlacementEnvironment_1) {
			case LAND:
				return blockState_1.get(TYPE) == SidingType.SINGLE;
			case WATER:
				return blockView_1.getFluidState(blockPos_1).matches(FluidTags.WATER);
			case AIR:
				return false;
			default:
				return false;
		}
	}

	static {
		TYPE = ModProperties.SIDING_TYPE;
		WATERLOGGED = Properties.WATERLOGGED;
		FACING_HORIZONTAL = Properties.FACING_HORIZONTAL;
		SINGLE_SHAPE_NORTH = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
		SINGLE_SHAPE_SOUTH = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
		SINGLE_SHAPE_EAST = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
		SINGLE_SHAPE_WEST = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	}
}
