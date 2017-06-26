package com.icemetalpunk.redplusplus.blocks;

import java.util.EnumSet;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRedstoneCounter extends BlockRedPlusPlus {

	public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
	protected static final AxisAlignedBB HALF_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D);

	public BlockRedstoneCounter() {
		super("block_redstone_counter", Material.IRON, MapColor.IRON);
		this.setHardness(5.0F).setResistance(10.0F);
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		if (side == EnumFacing.DOWN) {
			return 0;
		} else {
			return blockState.getValue(POWER);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
		return false;
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return blockState.getWeakPower(blockAccess, pos, side);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!playerIn.capabilities.allowEdit) {
			return false;
		} else {
			worldIn.setBlockState(pos, state.cycleProperty(POWER), 3);
			this.notifyNeighbors(worldIn, pos, state);
			return true;
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		this.notifyNeighbors(worldIn, pos, state);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		this.notifyNeighbors(worldIn, pos, state);
	}

	// Needed to notify redstone through a block of state change.
	protected void notifyNeighbors(World worldIn, BlockPos pos, IBlockState state) {
		for (EnumFacing enumfacing : EnumFacing.values()) {
			BlockPos blockpos = pos.offset(enumfacing.getOpposite());
			if (ForgeEventFactory.onNeighborNotify(worldIn, pos, worldIn.getBlockState(pos),
					EnumSet.of(enumfacing.getOpposite()), false).isCanceled())
				return;
			worldIn.neighborChanged(blockpos, this, pos);
			worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
		}
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

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return HALF_AABB;
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(this.getRegistryName(), new ResourceLocation("minecraft:redstone"),
				new ItemStack(this, 1), " R ", "CLC", " R ", 'R', Items.REDSTONE, 'C', Items.COMPARATOR, 'L',
				Blocks.LEVER);
	}

}
