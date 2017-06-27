package com.icemetalpunk.redplusplus.items;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRedstoneWrench extends ItemRedPlusPlus {

	public ItemRedstoneWrench() {
		super("redstone_wrench");
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState stateHit = worldIn.getBlockState(pos);

		for (IProperty prop : stateHit.getPropertyKeys()) {
			if (prop == BlockDirectional.FACING) {
				// EnumFacing current = (EnumFacing) stateHit.getValue(prop);
				// worldIn.setBlockState(pos, stateHit.withProperty(prop,
				// current.rotateY()));
				worldIn.setBlockState(pos, stateHit.withProperty(prop, facing));
				return EnumActionResult.SUCCESS;
			}
		}
		return EnumActionResult.PASS;
	}

	@Override
	public void registerRecipes() {

	}

}
