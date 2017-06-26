/* TODO: Resources
 * 1. Create ItemBlock model and texture for RedstoneCounter and get it registered and rendering properly.
 * 2. Create .lang file entries for RedstoneCounter block and item.
 * 3. More redstone plus plus stuff (maybe port over some Red+ objects?)
 */

package com.icemetalpunk.redplusplus;

import com.icemetalpunk.redplusplus.blocks.BlockRegistry;
import com.icemetalpunk.redplusplus.proxies.CommonProxy;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	public static final CreativeTabs tab = new CreativeTabs("RedPlusPlus") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(BlockRegistry.get("REDSTONE_COUNTER")));
		}

	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
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
