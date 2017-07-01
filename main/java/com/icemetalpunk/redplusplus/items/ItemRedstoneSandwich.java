package com.icemetalpunk.redplusplus.items;

import java.util.Collection;

import com.icemetalpunk.redplusplus.RedPlusPlus;
import com.icemetalpunk.redplusplus.sounds.SoundRegistry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRedstoneSandwich extends ItemFood implements IRedPlusPlusItem {
	public ItemRedstoneSandwich() {
		super(10, 1.0f, false);
		this.setRegistryName(RedPlusPlus.MODID, "redstone_sandwich").setUnlocalizedName("redstone_sandwich")
				.setCreativeTab(RedPlusPlus.tab);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
		super.onFoodEaten(stack, worldIn, player);
		Collection<PotionEffect> effects = player.getActivePotionEffects();
		for (PotionEffect effect : effects) {
			if (effect.getPotion().isBeneficial()) {
				PotionEffect newDuration = new PotionEffect(effect.getPotion(), effect.getDuration() * 2,
						effect.getAmplifier(), effect.getIsAmbient(), effect.doesShowParticles());
				effect.combine(newDuration);
			}
		}
		worldIn.playSound(player, player.getPosition(), SoundRegistry.get("REDSTONE_SANDWICH"), SoundCategory.AMBIENT,
				1.0f, 1.0f);
	}

	@Override
	public void register() {
		RedPlusPlus.FMLItemRegistry.register(this);
	}

	@Override
	// TODO: Add Redstone Sandwich model & texture.
	public void registerModel() {
		ModelResourceLocation model = new ModelResourceLocation(this.getRegistryName(), "inventory");
		ModelLoader.registerItemVariants(this, model);
		ModelLoader.setCustomModelResourceLocation(this, 0, model);
	}

	@Override
	public void registerRecipes() {
		GameRegistry.addShapedRecipe(this.getRegistryName(), new ResourceLocation("redplusplus:redstone_sandwich"),
				new ItemStack(this, 1), "RBR", "RPR", "RBR", 'R', new ItemStack(Items.REDSTONE, 1), 'B',
				new ItemStack(Items.BREAD, 1), 'P', new ItemStack(Items.COOKED_PORKCHOP, 1));
	}
}
