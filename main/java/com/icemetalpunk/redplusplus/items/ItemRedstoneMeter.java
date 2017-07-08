package com.icemetalpunk.redplusplus.items;

import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRedstoneMeter extends ItemRedPlusPlus {

	public ItemRedstoneMeter() {
		super("redstone_meter");
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return EnumActionResult.PASS;
		}

		// Calculate max strength
		int strength = calculateRedstoneStrength(worldIn, pos);
		TextComponentTranslation output = new TextComponentTranslation("item.redstone_meter.output",
				new Object[] { strength });
		output.getStyle().setColor(TextFormatting.RED);
		player.sendMessage(output);
		return EnumActionResult.SUCCESS;
	}

	// Adapted from BlockRedstoneDiode code.
	protected int calculateRedstoneStrength(World worldIn, BlockPos pos) {
		int ret = 0;
		IBlockState iblockstate = worldIn.getBlockState(pos);
		if (iblockstate.getBlock() == Blocks.REDSTONE_WIRE) {
			return ((Integer) iblockstate.getValue(BlockRedstoneWire.POWER)).intValue();
		}
		for (EnumFacing facing : EnumFacing.values()) {
			BlockPos blockpos = pos.offset(facing);
			int i = worldIn.getRedstonePower(blockpos, facing);

			if (i >= 15) {
				return i;
			} else {
				iblockstate = worldIn.getBlockState(blockpos);
				ret = Math.max(ret, Math.max(i, iblockstate.getBlock() == Blocks.REDSTONE_WIRE
						? ((Integer) iblockstate.getValue(BlockRedstoneWire.POWER)).intValue() : 0));
			}
		}
		return ret;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BLOCK;
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(this.getRegistryName(), new ResourceLocation("redplusplus:redstone_meter"),
				new ItemStack(this, 1), "C  ", " W ", "  T", 'C', new ItemStack(Items.COMPARATOR), 'T',
				new ItemStack(Blocks.REDSTONE_TORCH), 'W', new ItemStack(ItemRegistry.get("REDSTONE_WRENCH")));
	}

}
