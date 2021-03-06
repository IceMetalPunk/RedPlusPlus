package com.icemetalpunk.redplusplus.blocks;

import java.util.List;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class BlockDiamondPressurePlate extends BlockPressurePlate implements IRedPlusPlusBlock {

	private ItemBlock itemBlock = new ItemBlock(this);

	protected BlockDiamondPressurePlate() {
		super(Material.IRON, null);
		this.setRegistryName(RedPlusPlus.MODID, "diamond_pressure_plate").setUnlocalizedName("diamond_pressure_plate")
				.setCreativeTab(RedPlusPlus.tab);
	}

	@Override
	public Item getItemBlock() {
		return itemBlock;
	}

	@Override
	protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
		AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);
		List<? extends Entity> list;
		list = worldIn.<Entity> getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

		if (!list.isEmpty()) {
			for (Entity entity : list) {
				if (!entity.doesEntityNotTriggerPressurePlate()) {
					return 15;
				}
			}
		}
		return 0;
	}

	@Override
	public void registerModel() {
		ModelResourceLocation model = new ModelResourceLocation(this.getRegistryName(), "inventory");
		ModelLoader.registerItemVariants(this.itemBlock, model);
		ModelLoader.setCustomModelResourceLocation(this.itemBlock, 0, model);
	}

}
