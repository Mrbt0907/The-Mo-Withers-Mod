package net.minecraft.MoWithers.items;

import java.util.Random;

import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.MoWithers.MoWithers;
import net.minecraft.MoWithers.worldgen.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.witherskulls.EntityItemMoWithers;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import scala.swing.event.Key;

public class ItemThrowableWitherSkull extends Item
{
  public ItemThrowableWitherSkull(String unlocalizedName)
  {
	this.setUnlocalizedName(unlocalizedName);
    this.setCreativeTab(MoWithers.mowithers);
  }
  
  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
  {
      boolean flag = playerIn.fallDistance > 0.0F && playerIn.isSneaking() && !playerIn.onGround && !playerIn.isOnLadder() && !playerIn.isInWater() && !playerIn.isPotionActive(Potion.blindness) && playerIn.ridingEntity == null;
	  
	  if (flag)
	  {
		  worldIn.playAuxSFXAtEntity(playerIn, 1014, new BlockPos(playerIn), 0);
          for (int k = 0; k < 10; ++k)
          {
    		  playerIn.swingItem();
    	      double d1 = 1.5D;
    	      double d22 = 64D;
    	      Vec3 vec3 = playerIn.getLook(1.0F);
    	      double d2 = (playerIn.posX + vec3.xCoord * d22) - (playerIn.posX + (vec3.xCoord - ((-5 + k) * 2)) * d1);
    	      double d3 = (playerIn.posY + vec3.yCoord * d22) - (playerIn.posY + vec3.xCoord * d1);
    	      double d4 = (playerIn.posZ + vec3.zCoord * d22) - (playerIn.posZ + (vec3.zCoord - ((-5 + k) * 2)) * d1);
    	      EntityWitherSkull entitywitherskull = new EntityWitherSkull(worldIn, playerIn, d2, d3, d4);
    	      entitywitherskull.posX = playerIn.posX + vec3.xCoord;
    	      entitywitherskull.posY = playerIn.posY + vec3.yCoord + 1.5D;
    	      entitywitherskull.posZ = playerIn.posZ + vec3.zCoord;
    	      if (!worldIn.isRemote)
    	      {
    	          worldIn.spawnEntityInWorld(entitywitherskull);
    	      }
          }
	  }
	  else
	  {
		  playerIn.swingItem();
	      double d1 = 1.5D;
	      double d22 = 64D;
	      Vec3 vec3 = playerIn.getLook(1.0F);
	      double d2 = (playerIn.posX + vec3.xCoord * d22) - (playerIn.posX + vec3.xCoord * d1);
	      double d3 = (playerIn.posY + vec3.yCoord * d22) - (playerIn.posY + vec3.xCoord * d1);
	      double d4 = (playerIn.posZ + vec3.zCoord * d22) - (playerIn.posZ + vec3.zCoord * d1);
	      worldIn.playAuxSFXAtEntity(playerIn, 1014, new BlockPos(playerIn), 0);
	      EntityWitherSkull entitywitherskull = new EntityWitherSkull(worldIn, playerIn, d2, d3, d4);
	      if (playerIn.getRNG().nextFloat() < 0.01F || playerIn.isSneaking())
	    	  entitywitherskull.setInvulnerable(true);
	      entitywitherskull.posX = playerIn.posX + vec3.xCoord;
	      entitywitherskull.posY = playerIn.posY + vec3.yCoord + 1.5D;
	      entitywitherskull.posZ = playerIn.posZ + vec3.zCoord;
	      if (!worldIn.isRemote)
	      {
	          worldIn.spawnEntityInWorld(entitywitherskull);
	      }
	  }
	  
      if (!worldIn.isRemote)
      {
          if (!playerIn.capabilities.isCreativeMode)
          {
              --itemStackIn.stackSize;
          }
      }

      playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
      return itemStackIn;
  }
  
  public EnumRarity getRarity(ItemStack stack)
  {
    return EnumRarity.UNCOMMON;
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
