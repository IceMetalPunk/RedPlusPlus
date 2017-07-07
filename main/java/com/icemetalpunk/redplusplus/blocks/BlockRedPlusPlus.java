package com.icemetalpunk.redplusplus.blocks;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;

public abstract class BlockRedPlusPlus extends Block implements IRedPlusPlusBlock {

	private ItemBlock itemBlock = new ItemBlock(this);

	public BlockRedPlusPlus(String name, Material material) {
		super(material);
		this.setRegistryName(RedPlusPlus.MODID, name).setUnlocalizedName(name).setCreativeTab(RedPlusPlus.tab);
	}

	public BlockRedPlusPlus(String name, Material material, MapColor color) {
		super(material, color);
		this.setRegistryName(RedPlusPlus.MODID, name).setUnlocalizedName(name).setCreativeTab(RedPlusPlus.tab);
	}

	public Item getItemBlock() {
		return this.itemBlock;
	}

	public void registerModel() {
		ModelResourceLocation model = new ModelResourceLocation(this.getRegistryName(), "inventory");
		ModelLoader.registerItemVariants(this.itemBlock, model);
		ModelLoader.setCustomModelResourceLocation(this.itemBlock, 0, model);
	}

	/** Registers the recipes for this block, if any. */
	public abstract void registerRecipes();

}
