package net.minecraft.MoWithers.items;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.witherskulls.EntityItemMoWithers;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemNetherStarShard extends Item 
{
	public ItemNetherStarShard(String unlocalizedName) 
	{
		super();
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(MoWithers.mowithers);
	}
	
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
	  
	  @Override
	  public boolean hasCustomEntity(ItemStack stack)
	  {
	      return true;
	  }

	  @Override
	  public Entity createEntity(World world, Entity location, ItemStack itemstack)
	  {
		  EntityItemMoWithers newItem = new EntityItemMoWithers(world, false, true, false, false);
		  newItem.copyLocationAndAnglesFrom(location);
		  newItem.motionX = location.motionX;
		  newItem.motionY = location.motionY;
		  newItem.motionZ = location.motionZ;
		  newItem.setEntityItemStack(itemstack);
	      return newItem;
	  }
}