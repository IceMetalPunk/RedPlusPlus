package com.icemetalpunk.redplusplus.items;

import java.util.Collection;

import com.icemetalpunk.redplusplus.RedPlusPlus;
import com.icemetalpunk.redplusplus.sounds.SoundRegistry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRedstoneSuperSandwich extends ItemFood implements IRedPlusPlusItem {
	public ItemRedstoneSuperSandwich() {
		super(10, 1.0f, false);
		this.setRegistryName(RedPlusPlus.MODID, "super_redstone_sandwich").setUnlocalizedName("super_redstone_sandwich")
				.setCreativeTab(RedPlusPlus.tab);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		Collection<PotionEffect> effects = player.getActivePotionEffects();
		boolean hadEffect = false;
		for (PotionEffect effect : effects) {
			if (effect.getPotion().isBeneficial()) {
				PotionEffect newDuration = new PotionEffect(effect.getPotion(), effect.getDuration() * 2,
						effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles());
				effect.combine(newDuration);
				hadEffect = true;
			} else if (effect.getPotion().isBadEffect()) {
				PotionEffect newDuration = new PotionEffect(effect.getPotion(), effect.getDuration() / 2,
						effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles());
				player.removeActivePotionEffect(effect.getPotion());
				player.addPotionEffect(newDuration);
				hadEffect = true;
			}
		}
		if (hadEffect) {
			worldIn.playSound(player, player.getPosition(), SoundRegistry.get("REDSTONE_SANDWICH"),
					SoundCategory.AMBIENT, 1.0f, 1.0f);
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public void register() {
		RedPlusPlus.FMLItemRegistry.register(this);
	}

	@Override
	public void registerModel() {
		ModelResourceLocation model = new ModelResourceLocation(this.getRegistryName(), "inventory");
		ModelLoader.registerItemVariants(this, model);
		ModelLoader.setCustomModelResourceLocation(this, 0, model);
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(this.getRegistryName(),
				new ResourceLocation("redplusplus:super_redstone_sandwich"), new ItemStack(this, 1), "RBR", "RSR",
				"RBR", 'R', new ItemStack(Blocks.REDSTONE_BLOCK, 1), 'B', new ItemStack(Items.BREAD, 1), 'S',
				new ItemStack(Items.COOKED_BEEF, 1));
	}
}
