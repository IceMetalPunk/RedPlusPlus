package com.icemetalpunk.redplusplus.blocks;

import java.util.HashMap;

public class BlockRegistry {
	public static final HashMap<String, BlockRedPlusPlus> registry = new HashMap<String, BlockRedPlusPlus>();

	static {
		registry.put("REDSTONE_COUNTER", new BlockRedstoneCounter());
		registry.put("ANALOG_LAMP", new BlockAnalogLamp());
	}

	public BlockRegistry(boolean isClient) {
		for (BlockRedPlusPlus block : registry.values()) {
			block.registerWithModel(isClient);
			block.registerRecipes();
		}
	}

	public static BlockRedPlusPlus get(String name) {
		return registry.get(name);
	}

}
