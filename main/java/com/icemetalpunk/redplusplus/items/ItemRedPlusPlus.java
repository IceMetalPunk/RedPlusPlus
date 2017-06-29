package com.icemetalpunk.redplusplus.items;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public abstract class ItemRedPlusPlus extends Item implements IRedPlusPlusItem {

	public ItemRedPlusPlus(String name) {
		super();
		this.setRegistryName(RedPlusPlus.MODID, name).setUnlocalizedName(name).setCreativeTab(RedPlusPlus.tab);
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
	public abstract void registerRecipes();

}
