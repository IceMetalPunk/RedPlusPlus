package com.icemetalpunk.redplusplus.items;

import java.util.HashMap;

import net.minecraft.item.Item;

public class ItemRegistry {
	public static final HashMap<String, IRedPlusPlusItem> registry = new HashMap<String, IRedPlusPlusItem>();

	static {
		registry.put("REDSTONE_WRENCH", new ItemRedstoneWrench());
	}

	public ItemRegistry(boolean isClient) {
		for (IRedPlusPlusItem item : registry.values()) {
			item.registerWithModel(isClient);
			item.registerRecipes();
		}
	}

	public static Item get(String name) {
		return (Item) registry.get(name);
	}

}
