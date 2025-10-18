package net.minecraft.MoWithers.items;

import java.util.Random;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.MoWithers.worldgen.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.witherskulls.EntityItemMoWithers;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemWitherAxe extends ItemAxe 
{
	public ItemWitherAxe(String unlocalizedName, ToolMaterial material) 
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(MoWithers.mowithers);
	}
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
	    if (target instanceof EntityLivingBase && attacker instanceof EntityPlayer)
	    {
	      target.addPotionEffect(new PotionEffect(Potion.wither.id, 100, 3));
	    }

		  new Fortress().generate(target.worldObj, new Random(), new BlockPos(target.posX, target.posY, target.posZ));
	    return super.hitEntity(stack, target, attacker);
	}
	
	  public EnumRarity getRarity(ItemStack stack)
	  {
	    return EnumRarity.RARE;
	  }
	  
	  @Override
	  public boolean hasCustomEntity(ItemStack stack)
	  {
	      return true;
	  }

	  @Override
	  public Entity createEntity(World world, Entity location, ItemStack itemstack)
	  {
		  EntityItemMoWithers newItem = new EntityItemMoWithers(world, true, true, false, false);
		  newItem.copyLocationAndAnglesFrom(location);
		  newItem.motionX = location.motionX;
		  newItem.motionY = location.motionY;
		  newItem.motionZ = location.motionZ;
		  newItem.setEntityItemStack(itemstack);
	      return newItem;
	  }
}