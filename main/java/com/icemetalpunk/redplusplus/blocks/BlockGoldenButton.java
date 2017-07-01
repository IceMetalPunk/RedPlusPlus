package com.icemetalpunk.redplusplus.blocks;

import javax.annotation.Nullable;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	public void register() {
		this.itemBlock.setRegistryName(this.getRegistryName());
		RedPlusPlus.FMLBlockRegistry.register(this);
		RedPlusPlus.FMLItemRegistry.register(this.itemBlock);
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapelessRecipe(this.getRegistryName(), new ResourceLocation("redplusplus:golden_button"),
				new ItemStack(this, 1), Ingredient.fromStacks(new ItemStack(Items.GOLD_NUGGET, 1)));
	}

	@Override
	public void registerModel() {
		ModelResourceLocation model = new ModelResourceLocation(this.getRegistryName(), "inventory");
		ModelLoader.registerItemVariants(this.itemBlock, model);
		ModelLoader.setCustomModelResourceLocation(this.itemBlock, 0, model);
	}

	public void unlockRecipe(EntityPlayer player) {
		player.unlockRecipes(new ResourceLocation[] { this.getRegistryName() });
	}

}
