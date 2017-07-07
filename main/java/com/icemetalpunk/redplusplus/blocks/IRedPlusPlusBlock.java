package com.icemetalpunk.redplusplus.blocks;

import net.minecraft.item.Item;

public interface IRedPlusPlusBlock {

	public Item getItemBlock();

	public void registerModel();

	/** Registers the recipes for this block, if any. */
	public abstract void registerRecipes();
}
