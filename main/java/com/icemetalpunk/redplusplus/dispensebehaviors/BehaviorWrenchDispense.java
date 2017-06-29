package com.icemetalpunk.redplusplus.dispensebehaviors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BehaviorWrenchDispense extends BehaviorDefaultDispenseItem {

	@Override
	/**
	 * Dispense the specified stack, play the dispense sound and spawn particles.
	 */
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {

		World worldIn = source.getWorld();
		IPosition iposition = BlockDispenser.getDispensePosition(source);
		BlockPos pos = new BlockPos(iposition.getX(), iposition.getY(), iposition.getZ());
		IBlockState stateHit = worldIn.getBlockState(pos);
		IBlockState sourceState = source.getBlockState();

		if (!(sourceState.getBlock() instanceof BlockDispenser)) {
			return stack;
		}

		// Extended pistons can't be rotated.
		if (stateHit.getBlock() instanceof BlockPistonBase) {
			if (stateHit.getValue(BlockPistonBase.EXTENDED)) {
				return stack;
			}
		}

		EnumFacing axis = sourceState.getValue(BlockDispenser.FACING).getOpposite();

		// Some modified copypasta of the Forge rotation method, to rotate to
		// the face rather than around it; and a bit of optimization of it.
		Block block = stateHit.getBlock();
		IBlockState newState;
		if (block instanceof BlockBed || block instanceof BlockPistonExtension) {
			return stack;
		}

		for (IProperty<?> prop : stateHit.getProperties().keySet()) {
			if ((prop.getName().equals("facing") || prop.getName().equals("rotation"))
					&& prop.getValueClass() == EnumFacing.class) {
				IProperty<EnumFacing> facingProperty = (IProperty<EnumFacing>) prop;
				EnumFacing facing = stateHit.getValue(facingProperty);
				java.util.Collection<EnumFacing> validFacings = facingProperty.getAllowedValues();

				if (validFacings.size() == 4 && !validFacings.contains(EnumFacing.UP)
						&& !validFacings.contains(EnumFacing.DOWN)) {
					newState = stateHit.withProperty(facingProperty, facing.rotateY());
				} else {
					// rotate other facings about the axis
					EnumFacing rotatedFacing = facing.rotateAround(axis.getAxis());

					if (!validFacings.contains(rotatedFacing)) {
						return stack;
					}

					newState = stateHit.withProperty(facingProperty, rotatedFacing);
				}
				worldIn.setBlockState(pos, newState);
				return stack;

			}
		}
		return stack;
	}
}
