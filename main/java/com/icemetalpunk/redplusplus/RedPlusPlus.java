/* TODO: Next
 * 1. Easter Egg: Redstone Meat Sandwich, which doubles all potion effects on you when eaten.
 * 2. Configurable Cactus (damage frequency proportional to signal strength)
 * 
 * TODO: Future mod ideas:
 * 1. MtG magic mod
 * -> Instants: spell items that have durability but can be used whenever
 * -> Sorceries: spell items that have no durability, but have long cooldowns
 * -> Summons: spell items that act like spawn eggs
 * -> Artifacts: tools
 * -> Enchantments: spell items that are single-use, but leave an AoE behind for awhile
 * -> Auras: spell items that are used on non-player mobs to create effects for that mob specifically
 * -> Curses: like Auras, but applicable to players only
 * -> Planeswalkers: if at all possible, similar to Aether companions; only one planeswalker active per player at a time
 * -> Mana generated from Mana Taps placed in different biomes; colored, of course, by biome type, and linked to a Mana Pool.
 * -> Spells crafted in a Library, recycled for mana and ingredients in a Spell Graveyard.
 */

package com.icemetalpunk.redplusplus;

import com.icemetalpunk.redplusplus.blocks.BlockRegistry;
import com.icemetalpunk.redplusplus.events.RedPlusPlusHandler;
import com.icemetalpunk.redplusplus.events.RedPlusPlusRegistryEvents;
import com.icemetalpunk.redplusplus.proxies.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = RedPlusPlus.MODID, version = RedPlusPlus.VERSION)
public class RedPlusPlus {
	public static final String MODID = "redplusplus";
	public static final String VERSION = "0.1";

	@SidedProxy(clientSide = "com.icemetalpunk.redplusplus.proxies.ClientProxy", serverSide = "com.icemetalpunk.redplusplus.proxies.ServerProxy")
	public static CommonProxy proxy;

	// Registries
	public static final IForgeRegistry FMLBlockRegistry = GameRegistry.findRegistry(Block.class);
	public static final IForgeRegistry FMLItemRegistry = GameRegistry.findRegistry(Item.class);

	// Tab
	public static final CreativeTabs tab = new CreativeTabs("redplusplus") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(BlockRegistry.get("REDSTONE_COUNTER")));
		}

	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		MinecraftForge.EVENT_BUS.register(new RedPlusPlusRegistryEvents());
		MinecraftForge.EVENT_BUS.register(new RedPlusPlusHandler());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
