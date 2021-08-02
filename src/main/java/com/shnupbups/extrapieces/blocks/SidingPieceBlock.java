package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.ExtraPieces;
import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import com.shnupbups.extrapieces.register.ModProperties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Random;

@SuppressWarnings("deprecation")
public class SidingPieceBlock extends Block implements Waterloggable, PieceBlock {
	public static final EnumProperty<ModProperties.SidingType> TYPE;
	public static final BooleanProperty WATERLOGGED;
	public static final DirectionProperty FACING_HORIZONTAL;
	protected static final VoxelShape SINGLE_SHAPE_SOUTH;
	protected static final VoxelShape SINGLE_SHAPE_NORTH;
	protected static final VoxelShape SINGLE_SHAPE_EAST;
	protected static final VoxelShape SINGLE_SHAPE_WEST;

	static {
		TYPE = ModProperties.SIDING_TYPE;
		WATERLOGGED = Properties.WATERLOGGED;
		FACING_HORIZONTAL = Properties.HORIZONTAL_FACING;
		SINGLE_SHAPE_NORTH = Block.createCuboidShape(0.0D, 0.0D, 8.0D, 16.0D, 16.0D, 16.0D);
		SINGLE_SHAPE_SOUTH = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 8.0D);
		SINGLE_SHAPE_EAST = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
		SINGLE_SHAPE_WEST = Block.createCuboidShape(8.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	}

	private final PieceSet set;

	public SidingPieceBlock(PieceSet set) {
		super(FabricBlockSettings.copyOf(set.getBase()).materialColor(set.getDefaultMapColor()).breakByTool(set.getHarvestTool(), set.getHarvestLevel()));
		this.set = set;
		this.setDefaultState(this.getDefaultState().with(TYPE, ModProperties.SidingType.SINGLE).with(FACING_HORIZONTAL, Direction.NORTH).with(WATERLOGGED, false));
	}

	public Block getBlock() {
		return this;
	}

	public PieceSet getSet() {
		return set;
	}

	public PieceType getType() {
		return PieceTypes.SIDING;
	}

	public boolean hasSidedTransparency(BlockState blockState_1) {
		return blockState_1.get(TYPE) != ModProperties.SidingType.DOUBLE;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.add(TYPE, FACING_HORIZONTAL, WATERLOGGED);
	}

	public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, ShapeContext shapeContext_1) {
		ModProperties.SidingType slabType_1 = blockState_1.get(TYPE);
		Direction facing = blockState_1.get(FACING_HORIZONTAL);
		if (slabType_1 == ModProperties.SidingType.DOUBLE) {
			return VoxelShapes.fullCube();
		} else {
			switch (facing) {
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
			return blockState_1.with(TYPE, ModProperties.SidingType.DOUBLE).with(FACING_HORIZONTAL, blockState_1.get(FACING_HORIZONTAL)).with(WATERLOGGED, false);
		} else {
			FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(blockPos_1);
			Direction playerHorizontalFacing = itemPlacementContext_1.getPlayerFacing();
			Direction facing = itemPlacementContext_1.getSide();
			double xPos = itemPlacementContext_1.getHitPos().getX() - blockPos_1.getX();
			double zPos = itemPlacementContext_1.getHitPos().getZ() - blockPos_1.getZ();
			Direction direction_1 = playerHorizontalFacing.getOpposite();
			if (facing.getAxis().isVertical()) {
				if (direction_1 == Direction.EAST || direction_1 == Direction.WEST) {
					if (xPos > 0.5) direction_1 = Direction.WEST;
					else direction_1 = Direction.EAST;
				} else {
					if (zPos > 0.5) direction_1 = Direction.NORTH;
					else direction_1 = Direction.SOUTH;
				}
			}
			return this.getDefaultState().with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER).with(FACING_HORIZONTAL, direction_1);
		}
	}

	public boolean canReplace(BlockState blockState_1, ItemPlacementContext itemPlacementContext_1) {
		ItemStack itemStack_1 = itemPlacementContext_1.getStack();
		ModProperties.SidingType slabType_1 = blockState_1.get(TYPE);
		Direction facing = blockState_1.get(FACING_HORIZONTAL);
		if (slabType_1 != ModProperties.SidingType.DOUBLE && itemStack_1.getItem() == this.asItem()) {
			if (itemPlacementContext_1.canReplaceExisting()) {
				boolean boolean_1;
				switch (facing) {
					case EAST:
						boolean_1 = itemPlacementContext_1.getHitPos().getX() - (double) itemPlacementContext_1.getBlockPos().getX() > 0.5D;
						break;
					case WEST:
						boolean_1 = itemPlacementContext_1.getHitPos().getX() - (double) itemPlacementContext_1.getBlockPos().getX() < 0.5D;
						break;
					case SOUTH:
						boolean_1 = itemPlacementContext_1.getHitPos().getZ() - (double) itemPlacementContext_1.getBlockPos().getZ() > 0.5D;
						break;
					default:
						boolean_1 = itemPlacementContext_1.getHitPos().getZ() - (double) itemPlacementContext_1.getBlockPos().getZ() < 0.5D;
				}
				Direction direction_1 = itemPlacementContext_1.getSide();
				return direction_1 == facing || boolean_1 && direction_1.getAxis().isVertical();
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public FluidState getFluidState(BlockState blockState_1) {
		return blockState_1.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(blockState_1);
	}

	public boolean tryFillWithFluid(WorldAccess worldAccess_1, BlockPos blockPos_1, BlockState blockState_1, FluidState fluidState_1) {
		return blockState_1.get(TYPE) != ModProperties.SidingType.DOUBLE && Waterloggable.super.tryFillWithFluid(worldAccess_1, blockPos_1, blockState_1, fluidState_1);
	}

	public boolean canFillWithFluid(BlockView blockView_1, BlockPos blockPos_1, BlockState blockState_1, Fluid fluid_1) {
		return blockState_1.get(TYPE) != ModProperties.SidingType.DOUBLE && Waterloggable.super.canFillWithFluid(blockView_1, blockPos_1, blockState_1, fluid_1);
	}

	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, WorldAccess worldAccess_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		if (blockState_1.get(WATERLOGGED) && direction_1 != blockState_1.get(FACING_HORIZONTAL).getOpposite()) {
			worldAccess_1.getFluidTickScheduler().schedule(blockPos_1, Fluids.WATER, Fluids.WATER.getTickRate(worldAccess_1));
		}

		return super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, worldAccess_1, blockPos_1, blockPos_2);
	}

	public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, NavigationType navigationType_1) {
		switch (navigationType_1) {
			case LAND:
				return blockState_1.get(TYPE) == ModProperties.SidingType.SINGLE;
			case WATER:
				return blockView_1.getFluidState(blockPos_1).isIn(FluidTags.WATER);
			default:
				return false;
		}
	}

	@Environment(EnvType.CLIENT)
	public boolean isSideInvisible(BlockState blockState_1, BlockState blockState_2, Direction direction_1) {
		return getSet().isTransparent() ? (blockState_2.getBlock() == this || super.isSideInvisible(blockState_1, blockState_2, direction_1)) : super.isSideInvisible(blockState_1, blockState_2, direction_1);
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
	
	@Override
	public boolean emitsRedstonePower(BlockState blockState_1) {
		return super.emitsRedstonePower(blockState_1) || this.getBaseState().emitsRedstonePower();
	}
	
	@Override
	public int getWeakRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		float power = (float)this.getBaseState().getWeakRedstonePower(blockView_1, blockPos_1, direction_1);
		if(blockState_1.get(TYPE).equals(ModProperties.SidingType.SINGLE)) power /= 2;
		return Math.round(power);
	}
}
