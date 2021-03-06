package com.icemetalpunk.redplusplus.blocks;

import java.util.HashMap;

import com.icemetalpunk.redplusplus.blocks.BlockSmartPlate.PlateType;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class BlockRegistry {
	public static final HashMap<String, IRedPlusPlusBlock> registry = new HashMap<String, IRedPlusPlusBlock>();

	static {
		registry.put("REDSTONE_COUNTER", new BlockRedstoneCounter());
		registry.put("ANALOG_LAMP", new BlockAnalogLamp());
		registry.put("GOLDEN_BUTTON", new BlockGoldenButton());
		registry.put("DIAMOND_PRESSURE_PLATE", new BlockDiamondPressurePlate());
		registry.put("LIGHT_SMART_PLATE", new BlockSmartPlate("light_smart_plate", PlateType.LIGHT));
		registry.put("HEAVY_SMART_PLATE", new BlockSmartPlate("heavy_smart_plate", PlateType.HEAVY));
		registry.put("AUTO_CRAFTER", new BlockAutoCrafter());

		// Register trapped shulker boxes.
		for (EnumDyeColor col : EnumDyeColor.values()) {
			registry.put("TRAPPED_" + col.getName().toUpperCase() + "_SHULKER_BOX", new BlockTrappedShulkerBox(col));
		}
	}

	public BlockRegistry() {
	}

	public void registerAll(RegistryEvent.Register<Block> ev) {
		for (IRedPlusPlusBlock block : registry.values()) {
			block.getItemBlock().setRegistryName(((Block) block).getRegistryName());
			ev.getRegistry().register((Block) block);
		}
	}

	public void registerItemBlocks(RegistryEvent.Register<Item> ev) {
		for (IRedPlusPlusBlock block : registry.values()) {
			ev.getRegistry().register(block.getItemBlock());
		}
	}

	public static Block get(String name) {
		return (Block) registry.get(name);
	}

	public void registerModels() {
		for (IRedPlusPlusBlock block : registry.values()) {
			block.registerModel();
		}
	}
}
