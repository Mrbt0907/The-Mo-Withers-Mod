package net.minecraft.MoWithers.items;

import java.util.Calendar;
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
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.wither.*;
import net.minecraft.entity.witherskulls.EntityItemMoWithers;
import net.minecraft.entity.witherskulls.EntityMechaSkull;
import net.minecraft.entity.witherskulls.EntityAirVortex;
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
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.swing.event.Key;

public class ItemHolidayEssence extends Item
{
	  private int holidayID;
	
	  public ItemHolidayEssence(String unlocalizedName, int theHoliday)
	  {
		this.setUnlocalizedName(unlocalizedName);
	    this.setCreativeTab(MoWithers.mowithers);
	    this.holidayID = theHoliday;
	    if (theHoliday <= 0)
	    	this.holidayID = 0;
	  }
	  
	  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	  {
		  Calendar calendar = worldIn.getCurrentDate();
		  
          if (!worldIn.isRemote && worldIn.isAirBlock(playerIn.getPosition().up()) && worldIn.getDifficulty() != EnumDifficulty.PEACEFUL)
          {
              if (holidayID == 1 && calendar.get(2) + 1 == 12 && calendar.get(5) >= 20 && calendar.get(5) <= 28)
              {
            	  EntityChristmasWither wither = new EntityChristmasWither(worldIn);
                  wither.setInvulTime(1);
                  wither.setAttackTarget(playerIn);
                  wither.setLocationAndAngles((double)playerIn.getPosition().getX() + 0.5D, (double)playerIn.getPosition().getY() + 16F, (double)playerIn.getPosition().getZ() + 0.5D, 0F, 0F);
                  worldIn.spawnEntityInWorld(wither);
              }
              
              if (holidayID == 2 && calendar.get(2) + 1 == 3 && calendar.get(5) >= 12 && calendar.get(5) <= 26)
              {
            	  EntitySaintPatricksDayWither wither = new EntitySaintPatricksDayWither(worldIn);
                  wither.setInvulTime(1);
                  wither.setAttackTarget(playerIn);
                  wither.setLocationAndAngles((double)playerIn.getPosition().getX() + 0.5D, (double)playerIn.getPosition().getY() + 16F, (double)playerIn.getPosition().getZ() + 0.5D, 0F, 0F);
                  worldIn.spawnEntityInWorld(wither);
              }
              
              if (holidayID == 3 && calendar.get(2) + 1 == 2 && calendar.get(5) >= 8 && calendar.get(5) <= 26)
              {
            	  EntityValentinesDayWither wither = new EntityValentinesDayWither(worldIn);
                  wither.setInvulTime(1);
                  wither.setAttackTarget(playerIn);
                  wither.setLocationAndAngles((double)playerIn.getPosition().getX() + 0.5D, (double)playerIn.getPosition().getY() + 16F, (double)playerIn.getPosition().getZ() + 0.5D, 0F, 0F);
                  worldIn.spawnEntityInWorld(wither);
              }
              
              if (holidayID == 4 && calendar.get(2) + 1 == 10 && calendar.get(5) >= 11 && calendar.get(5) <= 31)
              {
            	  EntityHalloweenWither wither = new EntityHalloweenWither(worldIn);
                  wither.setInvulTime(1);
                  wither.setAttackTarget(playerIn);
                  wither.setLocationAndAngles((double)playerIn.getPosition().getX() + 0.5D, (double)playerIn.getPosition().getY() + 16F, (double)playerIn.getPosition().getZ() + 0.5D, 0F, 0F);
                  worldIn.spawnEntityInWorld(wither);
              }
          }

          return itemStackIn;
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
	      tooltip.add("The holiday in question eminates from this Item.");
	      tooltip.add("You realize that if you aren't close the right day,");
	      tooltip.add("absolutly nothing will happen.");
	  }
}
