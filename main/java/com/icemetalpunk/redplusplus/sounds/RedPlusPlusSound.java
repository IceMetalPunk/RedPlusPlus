package com.icemetalpunk.redplusplus.sounds;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class RedPlusPlusSound extends SoundEvent {
	public RedPlusPlusSound(String name) {
		super(new ResourceLocation(RedPlusPlus.MODID, name));
		this.setRegistryName(new ResourceLocation(RedPlusPlus.MODID, name));
	}

	public void register() {
		GameRegistry.findRegistry(SoundEvent.class).register(this);
	}
}
