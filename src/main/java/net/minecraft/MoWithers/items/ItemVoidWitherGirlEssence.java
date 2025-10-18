package net.minecraft.MoWithers.items;

import java.util.List;
import java.util.Random;

import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.MoWithers.MoWithers;
import net.minecraft.MoWithers.worldgen.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.wither.EntityWitherGirlVoid;
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

public class ItemVoidWitherGirlEssence extends Item
{
  public ItemVoidWitherGirlEssence(String unlocalizedName)
  {
	this.setUnlocalizedName(unlocalizedName);
    this.setCreativeTab(MoWithers.mowithers);
  }
  
  public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target)
  {
      if (target instanceof EntityLivingBase)
      {
    	  playerIn.swingItem();

    	  if (target instanceof EntityWitherGirlVoid)
    	  {
    		  ((EntityWitherGirlVoid)target).setInLove(true);
    	  }
    	  
    	  if (target instanceof EntityLiving)
    	  {
    		  ((EntityLiving)target).spawnExplosionParticle();
    		  playerIn.worldObj.playSound(target.posX + 0.5D, target.posY + 0.5D, target.posZ + 0.5D, "mob.zombie.remedy", 1F, 1F, false);
    		  ((EntityLiving)target).addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1000, 0));
      		  if (target instanceof EntityCreature)
    		  {
      			  if (!(target instanceof EntitySkeleton) && !(target instanceof EntityGuardian) && !(target instanceof EntityBlaze))
    			  ((EntityCreature)target).tasks.addTask(2, new EntityAIAttackOnCollide((EntityCreature)target, 1D, true));
    			  ((EntityCreature)target).targetTasks.addTask(0, new EntityAIHurtByTarget((EntityCreature)target, false, new Class[0]));
    			  ((EntityLiving)target).targetTasks.addTask(1, new EntityAINearestAttackableTarget((EntityCreature)target, EntityLivingBase.class, true));
    		  }
      		  else
      		  {
      			((EntityLiving)target).targetTasks.addTask(1, new EntityAIFindEntityNearest((EntityLiving)target, EntityLivingBase.class));
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
      if (target instanceof EntityLivingBase && attacker instanceof EntityPlayer)
      {
    	  if (target instanceof EntityWitherGirlVoid)
    	  {
    		  ((EntityWitherGirlVoid)target).setInLove(true);
    	  }
    	  
    	  if (target instanceof EntityLiving)
    	  {
    		  ((EntityLiving)target).spawnExplosionParticle();
    		  attacker.worldObj.playSound(target.posX + 0.5D, target.posY + 0.5D, target.posZ + 0.5D, "mob.zombie.remedy", 1F, 1F, false);
    		  ((EntityLiving)target).addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1000, 0));
      		  if (target instanceof EntityCreature)
    		  {
      			  if (!(target instanceof EntitySkeleton) && !(target instanceof EntityGuardian) && !(target instanceof EntityBlaze))
    			  ((EntityCreature)target).tasks.addTask(2, new EntityAIAttackOnCollide((EntityCreature)target, 1D, true));
    			  ((EntityCreature)target).targetTasks.addTask(0, new EntityAIHurtByTarget((EntityCreature)target, false, new Class[0]));
    			  ((EntityLiving)target).targetTasks.addTask(1, new EntityAINearestAttackableTarget((EntityCreature)target, EntityLivingBase.class, true));
    		  }
      		  else
      		  {
      			((EntityLiving)target).targetTasks.addTask(1, new EntityAIFindEntityNearest((EntityLiving)target, EntityLivingBase.class));
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
      tooltip.add("\u00A78Hatred, darkness and lucid swearing eminate from");
      tooltip.add("\u00A78this object. You feel like punching something.");
  }
}
