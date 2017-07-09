package com.icemetalpunk.redplusplus.blocks;

import java.util.List;

import com.icemetalpunk.redplusplus.RedPlusPlus;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPressurePlateWeighted;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class BlockSmartPlate extends BlockPressurePlateWeighted implements IRedPlusPlusBlock {

	public final ItemBlock itemBlock = new ItemBlock(this);

	// Different plate types in a nice enum, possibly for later expansion.
	public static enum PlateType {
		HEAVY(Material.IRON, 150, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE), LIGHT(Material.IRON, 15,
				Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE);
		private Material material;
		private int weight;
		private ItemStack ingredient;

		private PlateType(Material material, int maxW, Item ing) {
			this.weight = maxW;
			this.material = material;
			this.ingredient = new ItemStack(ing, 1);
		}

		private PlateType(Material material, int maxW, Block ing) {
			this(material, maxW, Item.getItemFromBlock(ing));
		}

		public int getWeight() {
			return this.weight;
		}

		public ItemStack getIngredient() {
			return this.ingredient;
		}

		public Material getMaterial() {
			return this.material;
		}
	}

	// END ENUM

	public PlateType type;

	protected BlockSmartPlate(String name, PlateType type) {
		super(type.getMaterial(), type.getWeight());
		this.type = type;
		this.setRegistryName(RedPlusPlus.MODID, name).setUnlocalizedName(name).setCreativeTab(RedPlusPlus.tab);
	}

	@Override
	protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
		int count = 0;
		List<Entity> ents = worldIn.getEntitiesWithinAABB(Entity.class, PRESSURE_AABB.offset(pos));

		for (int p = 0; p < ents.size(); ++p) {
			if (ents.get(p) instanceof EntityItem) {
				ItemStack stack = ((EntityItem) (ents.get(p))).getItem();
				count += stack.getCount();
			} else {
				++count;
			}
		}

		int maxW = ReflectionHelper.getPrivateValue(BlockPressurePlateWeighted.class, this, "maxWeight");
		int l = Math.min(count, maxW);
		if (l <= 0) {
			return 0;
		} else {
			float f = (float) l / (float) maxW;
			return MathHelper.ceil(f * 15.0F);
		}
	}

	@Override
	public Item getItemBlock() {
		return this.itemBlock;
	}

	@Override
	public void registerModel() {
		ModelResourceLocation model = new ModelResourceLocation(this.getRegistryName(), "inventory");
		ModelLoader.registerItemVariants(this.itemBlock, model);
		ModelLoader.setCustomModelResourceLocation(this.itemBlock, 0, model);
	}
}