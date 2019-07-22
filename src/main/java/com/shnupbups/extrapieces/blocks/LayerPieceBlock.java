package com.shnupbups.extrapieces.blocks;

import com.shnupbups.extrapieces.core.PieceSet;
import com.shnupbups.extrapieces.core.PieceType;
import com.shnupbups.extrapieces.core.PieceTypes;
import net.minecraft.block.*;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;

import java.util.Random;

public class LayerPieceBlock extends Block implements Waterloggable,PieceBlock {
	public static final IntProperty LAYERS;
	public static final BooleanProperty WATERLOGGED;
	protected static final VoxelShape[] LAYERS_TO_SHAPE;

	private final PieceSet set;

	public LayerPieceBlock(PieceSet set) {
		super(Settings.copy(set.getBase()));
		this.set = set;
		this.setDefaultState(this.stateFactory.getDefaultState().with(LAYERS, 1));
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

	public boolean canPlaceAtSide(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, BlockPlacementEnvironment blockPlacementEnvironment_1) {
		switch(blockPlacementEnvironment_1) {
			case LAND:
				return (Integer)blockState_1.get(LAYERS) < 5;
			case WATER:
				return false;
			case AIR:
				return false;
			default:
				return false;
		}
	}

	public VoxelShape getOutlineShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext entityContext_1) {
		return LAYERS_TO_SHAPE[(Integer)blockState_1.get(LAYERS)];
	}

	public boolean hasSidedTransparency(BlockState blockState_1) {
		return true;
	}

	public boolean canPlaceAt(BlockState blockState_1, ViewableWorld viewableWorld_1, BlockPos blockPos_1) {
		BlockState blockState_2 = viewableWorld_1.getBlockState(blockPos_1.down());
		Block block_1 = blockState_2.getBlock();
		return Block.isFaceFullSquare(blockState_2.getCollisionShape(viewableWorld_1, blockPos_1.down()), Direction.UP) || block_1 == this && (Integer)blockState_2.get(LAYERS) == 8;
	}

	public BlockState getStateForNeighborUpdate(BlockState blockState_1, Direction direction_1, BlockState blockState_2, IWorld iWorld_1, BlockPos blockPos_1, BlockPos blockPos_2) {
		return !blockState_1.canPlaceAt(iWorld_1, blockPos_1) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(blockState_1, direction_1, blockState_2, iWorld_1, blockPos_1, blockPos_2);
	}

	public void onScheduledTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
		if (world_1.getLightLevel(LightType.BLOCK, blockPos_1) > 11) {
			dropStacks(blockState_1, world_1, blockPos_1);
			world_1.clearBlockState(blockPos_1, false);
		}

	}

	public boolean canReplace(BlockState blockState_1, ItemPlacementContext itemPlacementContext_1) {
		int int_1 = blockState_1.get(LAYERS);
		if (itemPlacementContext_1.getStack().getItem() == this.asItem() && int_1 < 8) {
			if (itemPlacementContext_1.canReplaceExisting()) {
				return itemPlacementContext_1.getSide() == Direction.UP;
			} else {
				return true;
			}
		}
		return false;
	}

	public BlockState getPlacementState(ItemPlacementContext itemPlacementContext_1) {
		BlockState blockState_1 = itemPlacementContext_1.getWorld().getBlockState(itemPlacementContext_1.getBlockPos());
		if (blockState_1.getBlock() == this) {
			int int_1 = blockState_1.get(LAYERS);
			return blockState_1.with(LAYERS, Math.min(8, int_1 + 1));
		} else {
			return super.getPlacementState(itemPlacementContext_1);
		}
	}

	protected void appendProperties(StateFactory.Builder<Block, BlockState> stateFactory$Builder_1) {
		stateFactory$Builder_1.add(LAYERS);
		stateFactory$Builder_1.add(WATERLOGGED);
	}

	static {
		LAYERS = Properties.LAYERS;
		WATERLOGGED = Properties.WATERLOGGED;
		LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
	}
}
