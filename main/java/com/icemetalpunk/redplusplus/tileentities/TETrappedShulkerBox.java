package com.icemetalpunk.redplusplus.tileentities;

import javax.annotation.Nullable;

import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntityShulkerBox;

public class TETrappedShulkerBox extends TileEntityShulkerBox {

	public TETrappedShulkerBox() {
		super();
	}

	public TETrappedShulkerBox(@Nullable EnumDyeColor colorIn) {
		super(colorIn);
	}

	@Override
	public void openInventory(EntityPlayer player) {
		super.openInventory(player);
		this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		IBlockState state = this.world.getBlockState(pos);
		this.world.notifyNeighborsOfStateChange(this.pos.offset(state.getValue(BlockShulkerBox.FACING).getOpposite()),
				this.getBlockType(), false);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		super.closeInventory(player);
		this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		IBlockState state = this.world.getBlockState(pos);
		this.world.notifyNeighborsOfStateChange(this.pos.offset(state.getValue(BlockShulkerBox.FACING).getOpposite()),
				this.getBlockType(), false);
	}

}
