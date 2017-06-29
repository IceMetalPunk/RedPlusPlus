package com.icemetalpunk.redplusplus.dispensebehaviors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BehaviorFallblockDispense extends BehaviorDefaultDispenseItem {

	@Override
	/**
	 * Dispense the specified stack, play the dispense sound and spawn
	 * particles.
	 */
	public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
		World world = source.getWorld();
		IPosition iposition = BlockDispenser.getDispensePosition(source);

		BlockPos pos = new BlockPos(iposition.getX(), iposition.getY(), iposition.getZ());
		EnumFacing enumfacing = (EnumFacing) source.getBlockState().getValue(BlockDispenser.FACING);
		if (world.isAirBlock(pos)) {
			world.setBlockState(pos, Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getMetadata()));
			stack.shrink(1);
		}
		return stack;
	}

}
