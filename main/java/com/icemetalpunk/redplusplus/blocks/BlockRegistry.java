package com.icemetalpunk.redplusplus.blocks;

import java.util.HashMap;

import net.minecraft.block.Block;

public class BlockRegistry {
	public static final HashMap<String, IRedPlusPlusBlock> registry = new HashMap<String, IRedPlusPlusBlock>();

	static {
		registry.put("REDSTONE_COUNTER", new BlockRedstoneCounter());
		registry.put("ANALOG_LAMP", new BlockAnalogLamp());
	}

	public BlockRegistry(boolean isClient) {
		for (IRedPlusPlusBlock block : registry.values()) {
			block.registerWithModel(isClient);
			block.registerRecipes();
		}
	}

	public static Block get(String name) {
		return (Block) registry.get(name);
	}

}
