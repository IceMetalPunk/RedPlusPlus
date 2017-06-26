package com.icemetalpunk.redplusplus.proxies;

import com.icemetalpunk.redplusplus.blocks.BlockRegistry;
import com.icemetalpunk.redplusplus.dispensebehaviors.BehaviorFallblockDispense;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFalling;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public BlockRegistry blocks;

	public void preInit(FMLPreInitializationEvent e) {

	}

	public void init(FMLInitializationEvent e) {

	}

	public void postInit(FMLPostInitializationEvent e) {
		registerDispenserBehavior();
	}

	private void registerDispenserBehavior() {
		for (Item item : Item.REGISTRY) {
			if (Block.getBlockFromItem(item) != null && Block.getBlockFromItem(item) instanceof BlockFalling) {
				BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, new BehaviorFallblockDispense());
			}
		}
	}
}
