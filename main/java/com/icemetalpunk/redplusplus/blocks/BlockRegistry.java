package com.icemetalpunk.redplusplus.blocks;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;

public class BlockRegistry {
	public static final HashMap<String, IRedPlusPlusBlock> registry = new HashMap<String, IRedPlusPlusBlock>();

	static {
		registry.put("REDSTONE_COUNTER", new BlockRedstoneCounter());
		registry.put("ANALOG_LAMP", new BlockAnalogLamp());
		registry.put("GOLDEN_BUTTON", new BlockGoldenButton());
		registry.put("DIAMOND_PRESSURE_PLATE", new BlockDiamondPressurePlate());

		for (EnumDyeColor col : EnumDyeColor.values()) {
			registry.put("TRAPPED_" + col.getName().toUpperCase() + "_SHULKER_BOX", new BlockTrappedShulkerBox(col));
		}
	}

	public BlockRegistry() {
	}

	public void registerAll() {
		for (IRedPlusPlusBlock block : registry.values()) {
			block.register();
			block.registerRecipes();
		}
	}

	public static Block get(String name) {
		return (Block) registry.get(name);
	}

	public void registerModels() {
		for (IRedPlusPlusBlock block : registry.values()) {
			block.registerModel();
		}
	}
}
