package com.icemetalpunk.redplusplus.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRedstoneWrench extends ItemRedPlusPlus {

	public ItemRedstoneWrench() {
		super("redstone_wrench");
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState stateHit = worldIn.getBlockState(pos);

		// Extended pistons can't be rotated.
		if (stateHit.getBlock() instanceof BlockPistonBase) {
			if (stateHit.getValue(BlockPistonBase.EXTENDED)) {
				return EnumActionResult.FAIL;
			}
		}

		// Some modified copypasta of the Forge rotation method, to rotate to
		// the face rather than around it; and a bit of optimization of it.
		Block block = stateHit.getBlock();
		IBlockState newState;
		if (block instanceof BlockBed || block instanceof BlockPistonExtension) {
			return EnumActionResult.PASS;
		}

		for (IProperty<?> prop : stateHit.getProperties().keySet()) {
			if ((prop.getName().equals("facing") || prop.getName().equals("rotation"))
					&& prop.getValueClass() == EnumFacing.class) {
				IProperty<EnumFacing> facingProperty = (IProperty<EnumFacing>) prop;
				java.util.Collection<EnumFacing> validFacings = facingProperty.getAllowedValues();

				if (!validFacings.contains(facing)) {
					return EnumActionResult.PASS;
				}

				newState = stateHit.withProperty(facingProperty, facing);

				worldIn.setBlockState(pos, newState);
				return EnumActionResult.SUCCESS;
			}
		}

		return EnumActionResult.PASS;
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(this.getRegistryName(), new ResourceLocation("redplusplus:redstone_wrench"),
				new ItemStack(this, 1), "I I", " R ", " I ", 'I', new ItemStack(Items.IRON_INGOT), 'R',
				new ItemStack(Items.REDSTONE));
	}

}
