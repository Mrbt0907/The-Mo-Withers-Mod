package net.minecraft.MoWithers.items;

import java.util.Random;

import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.MoWithers.MoWithers;
import net.minecraft.MoWithers.MoWithersAchievments;
import net.minecraft.MoWithers.worldgen.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.wither.Sculpture;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import scala.swing.event.Key;

public class ItemSculpture extends Item
{
  public ItemSculpture(String unlocalizedName)
  {
	this.setUnlocalizedName(unlocalizedName);
    this.setCreativeTab(MoWithers.mowithers);
    this.setMaxStackSize(1);
  }
  
  public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
  {
      if (worldIn.isRemote)
      {
          return true;
      }
      else if (!playerIn.canPlayerEdit(pos.offset(side), side, stack))
      {
          return false;
      }
      else
      {
          IBlockState iblockstate = worldIn.getBlockState(pos);

          pos = pos.offset(side);
          double d0 = 0.0D;

          if (side == EnumFacing.UP && iblockstate instanceof BlockFence)
          {
              d0 = 0.5D;
          }

    	  Sculpture sculpture = new Sculpture(worldIn);
    	  sculpture.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() + d0, (double)pos.getZ() + 0.5D, playerIn.rotationYaw - 180F, 0.0F);
    	  sculpture.rotationYawHead = sculpture.rotationYaw;
    	  sculpture.renderYawOffset = sculpture.rotationYaw;
    	  sculpture.func_180482_a(worldIn.getDifficultyForLocation(new BlockPos(sculpture)), (IEntityLivingData)null);
          worldIn.spawnEntityInWorld(sculpture);

          if (sculpture != null)
          {
        	  playerIn.triggerAchievement(MoWithersAchievments.achievementSculpture);
        	  worldIn.playAuxSFX(2001, new BlockPos(sculpture.posX, sculpture.posY, sculpture.posZ), Block.getStateId(Blocks.clay.getDefaultState()));
        	  worldIn.playAuxSFX(2001, new BlockPos(sculpture.posX, sculpture.posY + 1D, sculpture.posZ), Block.getStateId(Blocks.clay.getDefaultState()));
        	  worldIn.playAuxSFX(2001, new BlockPos(sculpture.posX, sculpture.posY + 2D, sculpture.posZ), Block.getStateId(Blocks.clay.getDefaultState()));
        	  worldIn.playAuxSFX(2001, new BlockPos(sculpture.posX, sculpture.posY + 3D, sculpture.posZ), Block.getStateId(Blocks.clay.getDefaultState()));
        	  
              if (sculpture instanceof EntityLivingBase && stack.hasDisplayName())
              {
            	  sculpture.setCustomNameTag(stack.getDisplayName());
              }

              if (!playerIn.capabilities.isCreativeMode)
              {
                  --stack.stackSize;
              }
          }

          return true;
      }
  }
  
  public EnumRarity getRarity(ItemStack stack)
  {
    return EnumRarity.UNCOMMON;
  }
}
