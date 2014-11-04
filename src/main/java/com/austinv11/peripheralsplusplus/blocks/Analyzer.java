package com.austinv11.peripheralsplusplus.blocks;

import com.austinv11.peripheralsplusplus.PeripheralsPlusPlus;
import com.austinv11.peripheralsplusplus.creativetab.PPPCreativeTab;
import com.austinv11.peripheralsplusplus.reference.Reference;
import com.austinv11.peripheralsplusplus.tiles.TileEntityAnalyzer;
import com.austinv11.peripheralsplusplus.tiles.TileEntityAnalyzerBee;
import com.austinv11.peripheralsplusplus.tiles.TileEntityAnalyzerButterfly;
import com.austinv11.peripheralsplusplus.tiles.TileEntityAnalyzerTree;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public abstract class Analyzer extends BlockContainer implements IPeripheralProvider{

	public Analyzer() {
		super(Material.rock);
		this.setCreativeTab(PPPCreativeTab.PPP_TAB);
		this.setHardness(4f);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> items = Lists.newArrayList();
		ItemStack stack = new ItemStack(this,1,metadata);
		items.add(stack);
		TileEntityAnalyzer analyzer = (TileEntityAnalyzer) world.getTileEntity(x,y,z);
		for (int i = 0; i < analyzer.getSizeInventory(); i++) {
			if (analyzer.getStackInSlot(i) != null)
				items.add(analyzer.getStackInSlot(i));
		}
		return items;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ){
		TileEntity te = world.getTileEntity(x,y,z);
		if (!world.isRemote) {
			if (te != null)
				player.openGui(PeripheralsPlusPlus.instance, Reference.GUIs.ANALYZER.ordinal(), world, x, y, z);
		}
		return true;
	}

	@Override
	public String getUnlocalizedName(){//Formats the name
		return String.format("tile.%s%s", Reference.MOD_ID.toLowerCase()+":", getUnwrappedUnlocalizedName(getUnwrappedUnlocalizedName(super.getUnlocalizedName())));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister){//Registers the block icon(s)
		blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
	}

	protected String getUnwrappedUnlocalizedName(String unlocalizedName){//Removes the "item." from the item name
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
	}

	@Override
	public abstract TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_);

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public IPeripheral getPeripheral(World world, int x, int y, int z, int side ) {
		return (IPeripheral) world.getTileEntity(x,y,z);
	}

	public class AnalyzerTree extends Analyzer{

		public AnalyzerTree() {
			super();
			this.setBlockName("treeAnalyzer");
		}

		@Override
		public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
			return new TileEntityAnalyzerTree();
		}
	}

	public class AnalyzerButterfly extends Analyzer{

		public AnalyzerButterfly() {
			super();
			this.setBlockName("butterflyAnalyzer");
		}

		@Override
		public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
			return new TileEntityAnalyzerButterfly();
		}
	}
}