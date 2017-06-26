package com.icemetalpunk.redplusplus.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockAnalogLamp extends BlockRedPlusPlus {

	public BlockAnalogLamp() {
		super("block_analog_lamp", Material.GLASS, MapColor.SNOW);
		this.setHardness(0.3F);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (world instanceof World) {
			World theWorld = (World) world;
			return theWorld.isBlockIndirectlyGettingPowered(pos);
		}
		return 0;
	}

	@Override
	public void observedNeighborChange(IBlockState thisState, World world, BlockPos thisPos, Block changedBlock,
			BlockPos changedBlockPos) {
		world.setLightFor(EnumSkyBlock.BLOCK, thisPos, world.isBlockIndirectlyGettingPowered(thisPos));
		world.checkLight(thisPos);
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapelessRecipe(this.getRegistryName(), new ResourceLocation("minecraft:redstone"),
				new ItemStack(this, 1), Ingredient.fromItem(Items.COMPARATOR),
				Ingredient.fromItem(Item.getItemFromBlock(Blocks.REDSTONE_LAMP)));
	}

}
