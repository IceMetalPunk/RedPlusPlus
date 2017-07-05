package com.icemetalpunk.redplusplus.blocks;

import java.util.HashMap;
import java.util.Map;

import com.icemetalpunk.redplusplus.RedPlusPlus;
import com.icemetalpunk.redplusplus.tileentities.TETrappedShulkerBox;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
			int openCount = ReflectionHelper.getPrivateValue(TileEntityShulkerBox.class, shulkerTE, "openCount");
			return MathHelper.clamp(openCount, 0, 15);
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

		ModelLoader.setCustomStateMapper(this, new IStateMapper() {

			@Override
			public Map<IBlockState, ModelResourceLocation> putStateModelLocations(Block blockIn) {
				Map<IBlockState, ModelResourceLocation> theMap = new HashMap<IBlockState, ModelResourceLocation>();
				theMap.put(BlockTrappedShulkerBox.this.getDefaultState(), new ModelResourceLocation(
						RedPlusPlus.MODID + ":trapped_shulker_box", BlockTrappedShulkerBox.this.getColor().getName()));
				return theMap;
			}

		});

		ModelResourceLocation model = new ModelResourceLocation(
				new ResourceLocation("minecraft", this.getColor().getName() + "_shulker_box"), "inventory");
		ModelLoader.registerItemVariants(this.itemBlock, model);
		ModelLoader.setCustomModelResourceLocation(this.itemBlock, 0, model);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void registerRecipes() {
		Block shulker = Block.REGISTRY.getObject(new ResourceLocation(this.getColor().getName() + "_shulker_box"));
		GameRegistry.addShapelessRecipe(this.getRegistryName(), new ResourceLocation("redplusplus:trapped_shulker_box"),
				new ItemStack(this, 1), Ingredient.fromStacks(new ItemStack(shulker, 1)),
				Ingredient.fromStacks(new ItemStack(Blocks.TRIPWIRE_HOOK, 1)));
	}
}
