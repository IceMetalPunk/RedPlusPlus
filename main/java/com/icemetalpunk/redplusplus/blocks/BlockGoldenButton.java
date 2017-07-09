package com.icemetalpunk.redplusplus.blocks;

import javax.annotation.Nullable;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockGoldenButton extends BlockButton implements IRedPlusPlusBlock {

	private ItemBlock itemBlock = new ItemBlock(this);

	protected BlockGoldenButton() {
		super(false);
		this.setRegistryName(RedPlusPlus.MODID, "golden_button").setUnlocalizedName("golden_button")
				.setCreativeTab(RedPlusPlus.tab);
	}

	@Override
	public int tickRate(World worldIn) {
		return 2;
	}

	@Override
	protected void playClickSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos) {
		worldIn.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
	}

	@Override
	protected void playReleaseSound(World worldIn, BlockPos pos) {
		worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS,
				0.3F, 0.5F);
	}

	@Override
	public Item getItemBlock() {
		return itemBlock;
	}

	@Override
	public void registerModel() {
		ModelResourceLocation model = new ModelResourceLocation(this.getRegistryName(), "inventory");
		ModelLoader.registerItemVariants(this.itemBlock, model);
		ModelLoader.setCustomModelResourceLocation(this.itemBlock, 0, model);
	}

}
