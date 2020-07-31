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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class StairsPieceBlock extends StairsBlock implements PieceBlock {
	private final PieceSet set;

	public StairsPieceBlock(PieceSet set) {
		super(set.getBase().getDefaultState(), FabricBlockSettings.copyOf(set.getBase()).materialColor(set.getBase().getDefaultMaterialColor()));
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

	@Override
	public void onSteppedOn(World world_1, BlockPos blockPos_1, Entity entity_1) {
		try {
			this.getBase().onSteppedOn(world_1, blockPos_1, entity_1);
		} catch (IllegalArgumentException ignored) {
			ExtraPieces.debugLog("Caught an exception in onSteppedOn for "+this.getPieceString());
		}
	}
	
	@Override
	public boolean emitsRedstonePower(BlockState blockState_1) {
		return super.emitsRedstonePower(blockState_1) || this.getBaseState().emitsRedstonePower();
	}
	
	@Override
	public int getWeakRedstonePower(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, Direction direction_1) {
		float power = (float)this.getBaseState().getWeakRedstonePower(blockView_1, blockPos_1, direction_1);
		power = (power / 4) * 3;
		return Math.round(power);
	}
}
