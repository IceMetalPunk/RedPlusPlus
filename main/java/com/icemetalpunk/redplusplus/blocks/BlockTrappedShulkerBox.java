package com.icemetalpunk.redplusplus.blocks;

import java.lang.reflect.Field;

import com.icemetalpunk.redplusplus.RedPlusPlus;
import com.icemetalpunk.redplusplus.tileentities.TETrappedShulkerBox;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockTrappedShulkerBox extends BlockShulkerBox implements IRedPlusPlusBlock {

	private ItemBlock itemBlock = new ItemBlock(this);

	public BlockTrappedShulkerBox(EnumDyeColor colorIn) {
		super(colorIn);
		this.setRegistryName(RedPlusPlus.MODID, "trapped_" + colorIn.getName() + "_shulker_box")
				.setUnlocalizedName("trapped_" + colorIn.getName() + "_shulker_box").setCreativeTab(RedPlusPlus.tab);
		this.setHardness(2.0f);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TETrappedShulkerBox(this.getColor());
	}

	@Override
	public boolean canProvidePower(IBlockState state) {
		return true;
	}

	@Override
	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		TileEntity te = blockAccess.getTileEntity(pos);
		if (te != null && te instanceof TETrappedShulkerBox) {
			TileEntityShulkerBox shulkerTE = (TETrappedShulkerBox) te;
			int output = 0;
			try {
				Field openCountField = TileEntityShulkerBox.class.getDeclaredField("openCount");
				openCountField.setAccessible(true);
				output = MathHelper.clamp(openCountField.getInt(shulkerTE), 0, 15);
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return output;
		} else {
			return 0;
		}
	}

	@Override
	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return side == blockState.getValue(BlockShulkerBox.FACING) ? blockState.getWeakPower(blockAccess, pos, side)
				: 0;
	}

	@Override
	public Item getItemBlock() {
		return this.itemBlock;
	}

	@Override
	public void register() {
		this.itemBlock.setRegistryName(this.getRegistryName());
		RedPlusPlus.FMLBlockRegistry.register(this);
		RedPlusPlus.FMLItemRegistry.register(this.itemBlock);
	}

	@Override
	public void registerModel() {

		// FIXME: Using TESR outputs a non-fatal, unnoticed error of Model Not Found into log.

		ModelResourceLocation model = new ModelResourceLocation(
				new ResourceLocation("minecraft", this.getColor().getName() + "_shulker_box"), "inventory");
		ModelLoader.registerItemVariants(this.itemBlock, model);
		ModelLoader.setCustomModelResourceLocation(this.itemBlock, 0, model);
	}

	@Override
	public void registerRecipes() {
		Block shulker = Block.REGISTRY.getObject(new ResourceLocation(this.getColor().getName() + "_shulker_box"));
		GameRegistry.addShapelessRecipe(this.getRegistryName(), new ResourceLocation("redplusplus:trapped_shulker_box"),
				new ItemStack(this, 1), Ingredient.fromStacks(new ItemStack(shulker, 1)),
				Ingredient.fromStacks(new ItemStack(Blocks.TRIPWIRE_HOOK, 1)));
	}
}
