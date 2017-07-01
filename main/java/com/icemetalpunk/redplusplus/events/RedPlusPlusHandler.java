package com.icemetalpunk.redplusplus.events;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class RedPlusPlusHandler {
	@SubscribeEvent
	// Lava pushes mobs
	public void onWorldTick(TickEvent.WorldTickEvent ev) {
		World world = ev.world;
		List<Entity> ents = world.getLoadedEntityList();
		for (Entity ent : ents) {
			world.handleMaterialAcceleration(
					ent.getEntityBoundingBox().grow(0.0D, -0.4000000059604645D, 0.0D).shrink(0.001D), Material.LAVA,
					ent);
		}
	}
}
