package com.icemetalpunk.redplusplus.sounds;

import java.util.HashMap;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;

public class SoundRegistry {
	public static final HashMap<String, RedPlusPlusSound> registry = new HashMap<String, RedPlusPlusSound>();

	static {
		registry.put("REDSTONE_SANDWICH", new SoundRedstoneSandwich("eat"));
		registry.put("AUTO_CRAFTER_BEEP", new SoundAutoCrafter("beep"));
		registry.put("AUTO_CRAFTER_INITIATE", new SoundAutoCrafter("initiate"));
	}

	public SoundRegistry() {
	}

	public void registerAll(RegistryEvent.Register<SoundEvent> ev) {

		for (RedPlusPlusSound sound : registry.values()) {
			ev.getRegistry().register((SoundEvent) sound);
		}
	}

	public static SoundEvent get(String name) {
		return (SoundEvent) registry.get(name);
	}
}
