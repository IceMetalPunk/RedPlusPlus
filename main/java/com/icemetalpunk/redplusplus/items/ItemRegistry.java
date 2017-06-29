package com.icemetalpunk.redplusplus.items;

import java.util.HashMap;

import net.minecraft.item.Item;

public class ItemRegistry {
	public static final HashMap<String, IRedPlusPlusItem> registry = new HashMap<String, IRedPlusPlusItem>();

	static {
		registry.put("REDSTONE_WRENCH", new ItemRedstoneWrench());
		registry.put("REDSTONE_METER", new ItemRedstoneMeter()); // Depends on REDSTONE_WRENCH being registered first!
	}

	public ItemRegistry() {
		for (IRedPlusPlusItem item : registry.values()) {
			item.register();
			item.registerRecipes();
		}
	}

	public static Item get(String name) {
		return (Item) registry.get(name);
	}

	public void registerModels() {
		for (IRedPlusPlusItem item : registry.values()) {
			item.registerModel();
		}
	}

}
