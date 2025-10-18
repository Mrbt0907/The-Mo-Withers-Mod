package net.minecraft.MoWithers.items;

import java.util.List;
import java.util.Random;

import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.MoWithers.MoWithers;
import net.minecraft.MoWithers.worldgen.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.wither.EntityWitherGirlPink;
import net.minecraft.entity.witherskulls.EntityItemMoWithers;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.swing.event.Key;

public class ItemPinkWitherGirlEssence extends Item
{
  public ItemPinkWitherGirlEssence(String unlocalizedName)
  {
	this.setUnlocalizedName(unlocalizedName);
    this.setCreativeTab(MoWithers.mowithers);
  }
  
  public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target)
  {
	  if (target instanceof EntityLiving)
	  {
		  if (target instanceof EntityWitherGirlPink)
		  {
			  target.playSound("mowithers:WitherGirlPinkIdle", 1F, 1F + (target.getRNG().nextFloat() * 0.2F));
	    	  playerIn.swingItem();
	    	  ((EntityWitherGirlPink)target).setInLove(true);
			  ((EntityWitherGirlPink)target).sugerRush = true;
	      }
		  
		  if (target instanceof EntityAnimal)
		  {
			  target.playSound("mowithers:WitherGirlPinkIdle", 1F, 1F + (target.getRNG().nextFloat() * 0.2F));
			  playerIn.swingItem();
	    	  if (((EntityAnimal)target).isChild())
	            {
	    		  ((EntityAnimal)target).func_175501_a((int)((float)(-((EntityAnimal)target).getGrowingAge()) * 0.1F), true);
	            }
	    	  else
	    	  {
		    	  ((EntityAnimal)target).setInLove(playerIn);
		    	  if (((EntityAnimal)target).getGrowingAge() > 0)
		    		  ((EntityAnimal)target).setGrowingAge(0);
	    	  }
	      }
		  
          return true;
      }
      else
      {
    	  return super.itemInteractionForEntity(stack, playerIn, target);
      }
  }
  
  public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
  {
      if (target instanceof EntityLiving && attacker instanceof EntityPlayer)
      {
    	  target.heal(target.getMaxHealth());
    	  
		  if (target instanceof EntityWitherGirlPink)
		  {
			  target.playSound("mowithers:WitherGirlPinkIdle", 1F, 1F + (target.getRNG().nextFloat() * 0.2F));
	    	  ((EntityWitherGirlPink)target).setInLove(true);
			  ((EntityWitherGirlPink)target).sugerRush = true;
	      }
		  
		  if (target instanceof EntityAnimal)
		  {
			  target.playSound("mowithers:WitherGirlPinkIdle", 1F, 1F + (target.getRNG().nextFloat() * 0.2F));
	    	  if (((EntityAnimal)target).isChild())
	            {
	    		  ((EntityAnimal)target).func_175501_a((int)((float)(-((EntityAnimal)target).getGrowingAge()) * 0.1F), true);
	            }
	    	  else
	    	  {
		    	  ((EntityAnimal)target).setInLove((EntityPlayer)attacker);
		    	  if (((EntityAnimal)target).getGrowingAge() > 0)
		    		  ((EntityAnimal)target).setGrowingAge(0);
	    	  }
	      }

          return true;
      }
      else
      {
    	  return super.hitEntity(stack, target, attacker);
      }
  }
  
  public EnumRarity getRarity(ItemStack stack)
  {
    return EnumRarity.EPIC;
  }
  
  @Override
  public boolean hasCustomEntity(ItemStack stack)
  {
      return true;
  }

  @Override
  public Entity createEntity(World world, Entity location, ItemStack itemstack)
  {
	  EntityItemMoWithers newItem = new EntityItemMoWithers(world, true, true, true, true);
	  newItem.copyLocationAndAnglesFrom(location);
	  newItem.motionX = location.motionX;
	  newItem.motionY = location.motionY;
	  newItem.motionZ = location.motionZ;
	  newItem.setEntityItemStack(itemstack);
      return newItem;
  }
  
  @Override
  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) 
  {
      tooltip.add("\u00A7dAbsolutly and positivly gleams with happiness");
      tooltip.add("\u00A7dand bliss. You feel it's kindness.");
  }
}
