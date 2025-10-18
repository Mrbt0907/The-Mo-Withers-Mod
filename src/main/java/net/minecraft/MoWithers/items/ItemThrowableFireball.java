package net.minecraft.MoWithers.items;

import java.util.List;
import java.util.Random;

import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.MoWithers.MoWithers;
import net.minecraft.MoWithers.worldgen.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.witherskulls.EntityItemMoWithers;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.swing.event.Key;

public class ItemThrowableFireball extends Item
{
  public ItemThrowableFireball(String unlocalizedName)
  {
	this.setUnlocalizedName(unlocalizedName);
    this.setCreativeTab(MoWithers.mowithers);
  }
  
  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
  {
	  playerIn.swingItem();
      double d1 = 1.5D;
      double d22 = 64D;
      Vec3 vec3 = playerIn.getLook(1.0F);
      double d2 = (playerIn.posX + vec3.xCoord * d22) - (playerIn.posX + vec3.xCoord * d1);
      double d3 = (playerIn.posY + vec3.yCoord * d22) - ((playerIn.posY + 1.5D) + vec3.xCoord * d1);
      double d4 = (playerIn.posZ + vec3.zCoord * d22) - (playerIn.posZ + vec3.zCoord * d1);
      worldIn.playAuxSFXAtEntity(playerIn, 1008, new BlockPos(playerIn), 0);
      EntityLargeFireball entitylargefireball = new EntityLargeFireball(worldIn, playerIn, d2, d3, d4);
      entitylargefireball.explosionPower = 1;
      entitylargefireball.posX = playerIn.posX + vec3.xCoord;
      entitylargefireball.posY = (playerIn.posY + 1.5D) + vec3.yCoord;
      entitylargefireball.posZ = playerIn.posZ + vec3.zCoord;

      if (!worldIn.isRemote)
      {
          worldIn.spawnEntityInWorld(entitylargefireball);
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
