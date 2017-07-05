package com.icemetalpunk.redplusplus.items;

import java.util.HashMap;

import net.minecraft.item.Item;

public class ItemRegistry {
	public static final HashMap<String, IRedPlusPlusItem> registry = new HashMap<String, IRedPlusPlusItem>();

	static {
		registry.put("REDSTONE_WRENCH", new ItemRedstoneWrench());
		registry.put("REDSTONE_METER", new ItemRedstoneMeter()); // Depends on REDSTONE_WRENCH being registered first!
		
		// TODO: Remove these two from the creative menu, leaving only trial-by-recipe to uncover them.
		registry.put("REDSTONE_SANDWICH", new ItemRedstoneSandwich());
		registry.put("SUPER_REDSTONE_SANDWICH", new ItemRedstoneSuperSandwich());
	}

	public ItemRegistry() {
	}

	public void registerAll() {
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
