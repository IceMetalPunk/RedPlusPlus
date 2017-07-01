package com.icemetalpunk.redplusplus.sounds;

import java.util.HashMap;

import net.minecraft.util.SoundEvent;

public class SoundRegistry {
	public static final HashMap<String, RedPlusPlusSound> registry = new HashMap<String, RedPlusPlusSound>();

	static {
		registry.put("REDSTONE_SANDWICH", new SoundRedstoneSandwich("eat"));
	}

	public SoundRegistry() {
	}

	public void registerAll() {

		for (RedPlusPlusSound sound : registry.values()) {
			sound.register();
		}
	}

	public static SoundEvent get(String name) {
		return (SoundEvent) registry.get(name);
	}
}
