package com.icemetalpunk.redplusplus.blocks;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;

public abstract class BlockRedPlusPlusDirectional extends BlockDirectional implements IRedPlusPlusBlock {

	private ItemBlock itemBlock = new ItemBlock(this);

	public BlockRedPlusPlusDirectional(String name, Material material) {
		super(material);
		this.setRegistryName(RedPlusPlus.MODID, name).setUnlocalizedName(name).setCreativeTab(RedPlusPlus.tab);
	}

	public BlockRedPlusPlusDirectional(String name, Material material, MapColor color) {
		super(material);
		this.setRegistryName(RedPlusPlus.MODID, name).setUnlocalizedName(name).setCreativeTab(RedPlusPlus.tab);
	}

	public Item getItemBlock() {
		return this.itemBlock;
	}

	public void register() {
		this.itemBlock.setRegistryName(this.getRegistryName());
		RedPlusPlus.FMLBlockRegistry.register(this);
		RedPlusPlus.FMLItemRegistry.register(this.itemBlock);
	}

	public void registerModel() {
		ModelResourceLocation model = new ModelResourceLocation(this.getRegistryName(), "inventory");
		ModelLoader.registerItemVariants(this.itemBlock, model);
		ModelLoader.setCustomModelResourceLocation(this.itemBlock, 0, model);
	}

	/** Registers the recipes for this block, if any. */
	public abstract void registerRecipes();

}