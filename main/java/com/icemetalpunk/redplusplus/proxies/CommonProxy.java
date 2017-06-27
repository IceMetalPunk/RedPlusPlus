package com.icemetalpunk.redplusplus.proxies;

import com.icemetalpunk.redplusplus.blocks.BlockRegistry;
import com.icemetalpunk.redplusplus.dispensebehaviors.BehaviorFallblockDispense;
import com.icemetalpunk.redplusplus.dispensebehaviors.BehaviorPlantableDispense;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockFalling;
import net.minecraft.item.Item;
import net.minecraftforge.common.IPlantable;
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
		// Item-only things
		for (Item item : Item.REGISTRY) {
			if (Block.getBlockFromItem(item) instanceof BlockFalling) {
				BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, new BehaviorFallblockDispense());
			} else if (item instanceof IPlantable) {
				BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, new BehaviorPlantableDispense());
			}
		}

		// Block-only things
		for (Block block : Block.REGISTRY) {
			if (block instanceof IPlantable) {
				BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Item.getItemFromBlock(block),
						new BehaviorPlantableDispense());
			}
		}
	}
}
