package com.icemetalpunk.redplusplus.blocks;

import java.util.Random;

import com.icemetalpunk.redplusplus.sounds.SoundRegistry;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAutoCrafter extends BlockRedPlusPlus {

	public enum CrafterState implements IStringSerializable {
		DEFAULT, GREEN, YELLOW, RED;

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
	}

	public static final PropertyEnum<CrafterState> state = PropertyEnum.create("state", CrafterState.class);

	public BlockAutoCrafter() {
		super("block_auto_crafter", Material.WOOD, MapColor.WOOD);
		this.setHardness(2.5F).setResistance(2.5F).setTickRandomly(true);
		this.setDefaultState(this.getDefaultState().withProperty(this.state, CrafterState.DEFAULT));
		this.setCreativeTab(null);
	}

	@Override
	public int tickRate(World worldIn) {
		return 20;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (state.getValue(this.state).equals(CrafterState.DEFAULT)) {
			worldIn.setBlockState(pos, state.withProperty(this.state, CrafterState.GREEN));
			worldIn.playSound(playerIn, pos, SoundRegistry.get("AUTO_CRAFTER_BEEP"), SoundCategory.BLOCKS, 1.0f, 1.0f);
			worldIn.markBlockRangeForRenderUpdate(pos, pos);
			worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
		}
		return true;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		if (!worldIn.isRemote) {
			if (state.getValue(this.state).equals(CrafterState.RED)) {
				worldIn.playSound(null, pos, SoundRegistry.get("AUTO_CRAFTER_INITIATE"), SoundCategory.BLOCKS, 1.0f,
						1.0f);
				worldIn.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 4.0f, true);
				worldIn.setBlockToAir(pos);
			} else {
				worldIn.playSound(null, pos, SoundRegistry.get("AUTO_CRAFTER_BEEP"), SoundCategory.BLOCKS, 1.0f, 1.0f);
				CrafterState newState = state.getValue(this.state);
				newState = CrafterState.values()[newState.ordinal() + 1];
				worldIn.setBlockState(pos, state.withProperty(this.state, newState));
				worldIn.markBlockRangeForRenderUpdate(pos, pos);
				worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(this.state, CrafterState.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(this.state).ordinal();
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { this.state });
	}

}
