package com.icemetalpunk.redplusplus.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockAnalogLamp extends BlockRedPlusPlus {

	public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);

	public BlockAnalogLamp() {
		super("block_analog_lamp", Material.GLASS, MapColor.SNOW);
		this.setHardness(0.3F);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getValue(POWER);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		worldIn.setBlockState(pos,
				this.getDefaultState().withProperty(POWER, worldIn.isBlockIndirectlyGettingPowered(pos)));
	}

	@Override
	public void observedNeighborChange(IBlockState thisState, World world, BlockPos thisPos, Block changedBlock,
			BlockPos changedBlockPos) {
		super.observedNeighborChange(thisState, world, thisPos, changedBlock, changedBlockPos);
		world.setBlockState(thisPos,
				this.getDefaultState().withProperty(POWER, world.isBlockIndirectlyGettingPowered(thisPos)));
		// world.checkLightFor(EnumSkyBlock.BLOCK, thisPos);
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapelessRecipe(this.getRegistryName(), new ResourceLocation("minecraft:redstone"),
				new ItemStack(this, 1), Ingredient.fromItem(Items.COMPARATOR),
				Ingredient.fromItem(Item.getItemFromBlock(Blocks.REDSTONE_LAMP)));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(POWER, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(POWER);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { POWER });
	}

}
