package com.icemetalpunk.redplusplus.events;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RedPlusPlusRegistryEvents {
	@SubscribeEvent
	public void modelHandler(ModelRegistryEvent ev) {
		RedPlusPlus.proxy.blocks.registerModels();
		RedPlusPlus.proxy.items.registerModels();
	}
}
