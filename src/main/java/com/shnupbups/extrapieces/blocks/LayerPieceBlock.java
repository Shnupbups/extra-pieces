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
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
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
public class LayerPieceBlock extends Block implements Waterloggable, PieceBlock {
	public static final IntProperty LAYERS;
	public static final BooleanProperty WATERLOGGED;
	public static final EnumProperty<Direction> FACING;

	static {
		LAYERS = Properties.LAYERS;
		WATERLOGGED = Properties.WATERLOGGED;
		FACING = Properties.FACING;
	}

	private final PieceSet set;

	public LayerPieceBlock(PieceSet set) {
		super(FabricBlockSettings.copyOf(set.getBase()).materialColor(set.getBase().getDefaultMaterialColor()));
		this.set = set;
		this.setDefaultState(this.stateManager.getDefaultState().with(LAYERS, 1).with(FACING, Direction.UP).with(WATERLOGGED, false));
	}

	public PieceSet getSet() {
		return set;
	}

	public Block getBlock() {
		return this;
	}

	public PieceType getType() {
		return PieceTypes.LAYER;
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, ShapeContext entityContext_1) {
		Direction dir = state.get(FACING);
		int layers = state.get(LAYERS);
		if(layers == 8) return VoxelShapes.fullCube();
		switch(dir) {
			case UP:
				return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D*layers, 16.0D);
			case DOWN:
				return Block.createCuboidShape(0.0D, 16.0D-(layers*2.0D), 0.0D, 16.0D, 16.0D, 16.0D);
			case EAST:
				return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 2.0D*layers, 16.0D, 16.0D);
			case SOUTH:
				return Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D*layers);
			case WEST:
				return Block.createCuboidShape(16.0D-(layers*2.0D), 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
			case NORTH:
				return Block.createCuboidShape(0.0D, 0.0D, 16.0D-(layers*2.0D), 16.0D, 16.0D, 16.0D);
		} return VoxelShapes.empty();
	}

	public boolean hasSidedTransparency(BlockState blockState_1) {
		return true;
	}

	public boolean canReplace(BlockState state, ItemPlacementContext itemPlacementContext_1) {
		int int_1 = state.get(LAYERS);
		if (itemPlacementContext_1.getStack().getItem() == this.asItem() && int_1 < 8) {
			if (itemPlacementContext_1.canReplaceExisting()) {
				return itemPlacementContext_1.getSide() == state.get(FACING);
			} else {
				return true;
			}
		}
		return false;
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		BlockPos blockPos_1 = itemPlacementContext_1.getBlockPos();
		BlockState blockState_1 = itemPlacementContext_1.getWorld().getBlockState(blockPos_1);
		if (blockState_1.getBlock() == this) {
			int int_1 = blockState_1.get(LAYERS);
			BlockState newState = blockState_1.with(LAYERS, Math.min(8, int_1 + 1));
			if (int_1 + 1 >= 8) {
				newState = newState.with(WATERLOGGED, false);
			}
			return newState;
		} else {
			FluidState fluidState_1 = itemPlacementContext_1.getWorld().getFluidState(blockPos_1);
			return this.getDefaultState().with(WATERLOGGED, fluidState_1.getFluid() == Fluids.WATER).with(FACING, itemPlacementContext_1.getSide());
		}
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.add(LAYERS);
		stateFactory$Builder_1.add(WATERLOGGED);
		stateFactory$Builder_1.add(FACING);
	}

	public FluidState getFluidState(BlockState blockState_1) {
		return blockState_1.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(blockState_1);
	}

	public boolean tryFillWithFluid(WorldAccess worldAccess_1, BlockPos blockPos_1, BlockState blockState_1, FluidState fluidState_1) {
		return blockState_1.get(LAYERS) < 8 && Waterloggable.super.tryFillWithFluid(worldAccess_1, blockPos_1, blockState_1, fluidState_1);
	}

	public boolean canFillWithFluid(BlockView blockView_1, BlockPos blockPos_1, BlockState blockState_1, Fluid fluid_1) {
		return blockState_1.get(LAYERS) < 8 && Waterloggable.super.canFillWithFluid(blockView_1, blockPos_1, blockState_1, fluid_1);
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
	public void onSteppedOn(World world_1, BlockPos blockPos_1, Entity entity_1) {
		super.onSteppedOn(world_1, blockPos_1, entity_1);
		try {
			this.getBase().onSteppedOn(world_1, blockPos_1, entity_1);
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
		power = (power / 8) * blockState_1.get(LAYERS);
		return Math.round(power);
	}
}
