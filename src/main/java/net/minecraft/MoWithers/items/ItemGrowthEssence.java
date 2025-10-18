package net.minecraft.MoWithers.items;

import java.util.List;
import java.util.Random;

import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.MoWithers.MoWithers;
import net.minecraft.MoWithers.worldgen.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.wither.EntityPinkWither;
import net.minecraft.entity.wither.EntityWitherGirl;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.swing.event.Key;

public class ItemGrowthEssence extends Item
{
  public ItemGrowthEssence(String unlocalizedName)
  {
	this.setUnlocalizedName(unlocalizedName);
    this.setCreativeTab(MoWithers.mowithers);
  }
  
  public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target)
  {
      if (target instanceof EntityLivingBase)
      {
    	  playerIn.swingItem();

    	  if (target instanceof EntityPinkWither)
    	  {
    		  if (!((EntityPinkWither)target).worldObj.isRemote)
    	      {
    			  ((EntityPinkWither)target).isFed = true;
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
    	  if (target instanceof EntityPinkWither)
    	  {
    		  if (!((EntityPinkWither)target).worldObj.isRemote)
    	      {
    			  ((EntityPinkWither)target).isFed = true;
    	      }
    	  }

          return true;
      }
      else
      {
    	  return super.hitEntity(stack, target, attacker);
      }
  }
  
  public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
  {
      if (!playerIn.canPlayerEdit(pos, side, stack))
      {
          return false;
      }
      else
      {
    	  this.spreadBonemealEffect(pos.getX(), pos.getY() + 0.25D, pos.getZ(), worldIn);
    	  this.spreadBonemealEffect(pos.getX(), pos.getY(), pos.getZ(), worldIn);
    	  
    	  return true;
      }
  }
  
  public void spreadBonemealEffect(double posX, double posY, double posZ, World world)
  {
    BlockPos blockpos = new BlockPos(posX, posY, posZ);
    IBlockState iblockstate = world.getBlockState(blockpos);
    Block block = iblockstate.getBlock();
    if ((block != null) && (block.getMaterial() == Material.ground) && block != Blocks.farmland) {
      if ((world.getBiomeGenForCoords(new BlockPos(posX, posY, posZ)) == BiomeGenBase.mushroomIsland) || (world.getBiomeGenForCoords(new BlockPos(posX, posY, posZ)) == BiomeGenBase.mushroomIslandShore)) {
        world.setBlockState(blockpos, Blocks.mycelium.getDefaultState());
      } else {
        world.setBlockState(blockpos, Blocks.grass.getDefaultState());
      }
    }
    if ((block != null) && ((block instanceof IGrowable)))
    {
      IGrowable igrowable = (IGrowable)block;
      if (igrowable.canGrow(world, blockpos, iblockstate, world.isRemote)) {
        if (!world.isRemote) {
          if (((igrowable instanceof BlockCocoa)) || ((igrowable instanceof BlockCrops)) || ((igrowable instanceof BlockGrass)) || ((igrowable instanceof BlockMushroom)) || ((igrowable instanceof BlockSapling)) || ((igrowable instanceof BlockStem)) || ((igrowable instanceof BlockTallGrass))) {
            igrowable.grow(world, world.rand, blockpos, iblockstate);
          }
        }
      }
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
      tooltip.add("\u00A7dPlants twitch all around you, almost as if");
      tooltip.add("\u00A7dthey are urging you to help them.");
  }
}
