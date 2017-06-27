package com.icemetalpunk.redplusplus.dispensebehaviors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class BehaviorPlantableDispense extends BehaviorDefaultDispenseItem {

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
		IBlockState blockInto = world.getBlockState(pos);

		Item item = stack.getItem();
		Block block = Block.getBlockFromItem(item);
		IBlockState plantBlock = null;
		IPlantable plant = null;
		if (item instanceof IPlantable) {
			plant = (IPlantable) item;
			plantBlock = plant.getPlant(world, pos);
		} else if (block instanceof IPlantable) {
			plant = (IPlantable) block;
			plantBlock = block.getStateFromMeta(stack.getMetadata());
		} else {
			return stack;
		}

		if (blockInto.getBlock().canSustainPlant(blockInto, world, pos, EnumFacing.UP, plant)
				&& world.isAirBlock(pos.up())) {
			IBlockState state = plant.getPlant(world, pos);

			world.setBlockState(pos.up(), plantBlock);
			stack.shrink(1);
		}
		return stack;
	}

}
