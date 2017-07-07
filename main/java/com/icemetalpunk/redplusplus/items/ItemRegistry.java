package com.icemetalpunk.redplusplus.items;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;

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

	public void registerAll(RegistryEvent.Register<Item> ev) {
		for (IRedPlusPlusItem item : registry.values()) {
			ev.getRegistry().register((Item) item);
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

	public void unlockRecipes(EntityPlayer player) {
		for (IRedPlusPlusItem item : registry.values()) {
			if (!(item instanceof Item)) {
				System.err.println("The IRedPlusPlusItem is not an instance of Item: " + item);
			} else {
				player.unlockRecipes(new ResourceLocation[] { ((Item) item).getRegistryName() });
			}
		}
	}

}
