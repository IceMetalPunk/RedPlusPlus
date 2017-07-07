package com.icemetalpunk.redplusplus.blocks;

import java.util.HashMap;

import com.icemetalpunk.redplusplus.blocks.BlockSmartPlate.PlateType;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
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

		// TODO: Remove this from the creative menu, leaving only trial-by-recipe to uncover it.
		registry.put("AUTO_CRAFTER", new BlockAutoCrafter());

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
			block.registerRecipes();
		}
	}

	public void registerItemBlocks(RegistryEvent.Register<Item> ev) {
		for (IRedPlusPlusBlock block : registry.values()) {
			ev.getRegistry().register(block.getItemBlock());
		}
	}

	public void unlockRecipes(EntityPlayer player) {
		for (IRedPlusPlusBlock block : registry.values()) {
			// GameRegistry.addShapedRecipe(name, group, output, params);
			if (!(block instanceof Block)) {
				System.err.println("The IRedPlusPlusBlock is not an instance of Block: " + block);
			} else {
				player.unlockRecipes(new ResourceLocation[] { ((Block) block).getRegistryName() });
			}
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
