package net.minecraft.MoWithers.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRecordMoWithers extends ItemRecord {

	public String music;
	private static final Map RECORDS = new HashMap();
	public ItemRecordMoWithers(String name, String music) 
	{
		super(music);
		this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setUnlocalizedName(name);
        this.music = music;
        RECORDS.put("records." + music, this);
	}
	
	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() == Blocks.jukebox && !((Boolean)iblockstate.getValue(BlockJukebox.HAS_RECORD)).booleanValue()) {
            if (worldIn.isRemote) { return true; }
            else {
            	((BlockJukebox)Blocks.jukebox).insertRecord(worldIn, pos, iblockstate, stack);
                worldIn.playAuxSFXAtEntity((EntityPlayer)null, 1005, pos, Item.getIdFromItem(this));
                --stack.stackSize;
                return true;
            }
        } else { return false; }
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        tooltip.add(this.getRecordNameLocal());
    }

	@Override
    @SideOnly(Side.CLIENT)
    public String getRecordNameLocal() {
        return StatCollector.translateToLocal("item.record." + this.music + ".desc");
    }

	@Override
	public ResourceLocation getRecordResource(String record) 
	{
		ResourceLocation location = super.getRecordResource("mowithers:" + this.music);
		return location;
	}
	
	 @Override
	 public EnumRarity getRarity(ItemStack itemStack) 
	 {
		 return EnumRarity.RARE;
	 }
	 
	 @SideOnly(Side.CLIENT)
	 public static ItemRecordMoWithers getRecord(String name) 
	 {
		 return (ItemRecordMoWithers)RECORDS.get(name);
     }
}