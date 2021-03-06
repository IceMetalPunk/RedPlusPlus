package com.icemetalpunk.redplusplus.blocks;

import com.icemetalpunk.redplusplus.RedPlusPlus;
import com.icemetalpunk.redplusplus.tileentities.TETrappedShulkerBox;

import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class BlockTrappedShulkerBox extends BlockShulkerBox implements IRedPlusPlusBlock {

	private ItemBlock itemBlock = new ItemBlock(this);
	public static final PropertyEnum COLOR = PropertyEnum.create("color", EnumDyeColor.class);

	public BlockTrappedShulkerBox(EnumDyeColor colorIn) {
		super(colorIn);
		this.setRegistryName(RedPlusPlus.MODID, "trapped_" + colorIn.getName() + "_shulker_box")
				.setUnlocalizedName("trapped_" + colorIn.getName() + "_shulker_box").setCreativeTab(RedPlusPlus.tab);
		this.setHardness(2.0f);
		this.setDefaultState(this.getDefaultState().withProperty(COLOR, colorIn));
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
	public void registerModel() {

		ModelLoader.setCustomStateMapper(this, new StateMapperBase() {

			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(RedPlusPlus.MODID + ":trapped_shulker_box",
						"color=" + BlockTrappedShulkerBox.this.getColor().getName());
			}

		});

		ModelResourceLocation model = new ModelResourceLocation(
				new ResourceLocation("minecraft", this.getColor().getName() + "_shulker_box"), "inventory");
		ModelLoader.registerItemVariants(this.itemBlock, model);
		ModelLoader.setCustomModelResourceLocation(this.itemBlock, 0, model);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(COLOR, this.getColor()).withProperty(FACING,
				EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING, COLOR });
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}
}
