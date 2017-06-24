package com.icemetalpunk.redplusplus.blocks;

import java.util.HashMap;

public class BlockRegistry {
	public static final HashMap<String, BlockRedPlusPlus> registry = new HashMap<String, BlockRedPlusPlus>();

	static {
		registry.put("REDSTONE_COUNTER", new BlockRedstoneCounter());
	}

	public BlockRegistry(boolean isClient) {
		for (BlockRedPlusPlus block : registry.values()) {
			block.register(isClient);
		}
	}

	public static BlockRedPlusPlus get(String name) {
		return registry.get(name);
	}

}
