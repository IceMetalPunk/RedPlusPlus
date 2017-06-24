package com.icemetalpunk.redplusplus.blocks;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockRedPlusPlus extends Block {

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

	public void register(boolean isClient) {
		this.itemBlock.setRegistryName(this.getRegistryName());
		RedPlusPlus.FMLBlockRegistry.register(this);
		RedPlusPlus.FMLItemRegistry.register(this.itemBlock);

		if (isClient) {
			ModelBakery.registerItemVariants(this.itemBlock, this.getRegistryName());
		}

	}

}
