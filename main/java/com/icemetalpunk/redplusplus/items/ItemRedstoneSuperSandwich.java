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
import net.minecraft.util.EnumParticleTypes;
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
		// Particles and sounds!
		if (hadEffect) {
			double d0 = worldIn.rand.nextGaussian() * 0.02D;
			double d1 = worldIn.rand.nextGaussian() * 0.02D;
			double d2 = worldIn.rand.nextGaussian() * 0.02D;
			for (int i = 0; i < 13; ++i) {
				worldIn.spawnParticle(EnumParticleTypes.HEART,
						player.posX + (double) (worldIn.rand.nextFloat() * player.width * 2.0F) - (double) player.width,
						player.posY + 0.5D + (double) (worldIn.rand.nextFloat() * player.height),
						player.posZ + (double) (worldIn.rand.nextFloat() * player.width * 2.0F) - (double) player.width,
						d0, d1, d2);
				worldIn.spawnParticle(EnumParticleTypes.CRIT,
						player.posX + (double) (worldIn.rand.nextFloat() * player.width * 2.0F) - (double) player.width,
						player.posY + 0.5D + (double) (worldIn.rand.nextFloat() * player.height),
						player.posZ + (double) (worldIn.rand.nextFloat() * player.width * 2.0F) - (double) player.width,
						d0, d1, d2);
			}
			worldIn.playSound(player, player.getPosition(), SoundRegistry.get("REDSTONE_SANDWICH"),
					SoundCategory.AMBIENT, 1.0f, 1.0f);
		}
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
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
				new ResourceLocation("redplusplus:super_redstone_sandwich"), new ItemStack(this, 1), "RBR", "DSD",
				"RBR", 'R', new ItemStack(Blocks.REDSTONE_BLOCK, 1), 'B', new ItemStack(Items.BREAD, 1), 'S',
				new ItemStack(Items.COOKED_BEEF, 1), 'D', new ItemStack(Blocks.DIAMOND_BLOCK, 1));
	}
}
