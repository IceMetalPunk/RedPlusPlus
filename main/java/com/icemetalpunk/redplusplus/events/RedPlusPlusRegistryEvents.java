package com.icemetalpunk.redplusplus.events;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RedPlusPlusRegistryEvents {
	@SubscribeEvent
	public void modelHandler(ModelRegistryEvent ev) {
		RedPlusPlus.proxy.blocks.registerModels();
		RedPlusPlus.proxy.items.registerModels();
	}

	@SubscribeEvent
	public void itemHandler(RegistryEvent.Register<Item> ev) {
		RedPlusPlus.proxy.items.registerAll(ev);
		RedPlusPlus.proxy.blocks.registerItemBlocks(ev);
	}

	@SubscribeEvent
	public void blockHandler(RegistryEvent.Register<Block> ev) {
		RedPlusPlus.proxy.blocks.registerAll(ev);
	}

	@SubscribeEvent
	public void soundHandler(RegistryEvent.Register<SoundEvent> ev) {
		RedPlusPlus.proxy.sounds.registerAll(ev);
	}
}
