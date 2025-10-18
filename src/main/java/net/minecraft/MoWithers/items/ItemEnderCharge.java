package net.minecraft.MoWithers.items;

import java.util.Random;

import net.java.games.input.Component;
import net.java.games.input.Keyboard;
import net.minecraft.MoWithers.MoWithers;
import net.minecraft.MoWithers.worldgen.*;
import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import scala.swing.event.Key;

public class ItemEnderCharge extends Item
{
  public ItemEnderCharge(String unlocalizedName)
  {
	this.setUnlocalizedName(unlocalizedName);
    this.setCreativeTab(MoWithers.mowithers);
  }
  
  public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
  {
      pos = pos.offset(side);

      if (!playerIn.canPlayerEdit(pos, side, stack))
      {
          return false;
      }
      else
      {
    	  Fortress fortress = new Fortress();
    	  
    	  fortress.generate(worldIn, playerIn.getRNG(), pos);
    	  
          if (worldIn.isAirBlock(pos) && worldIn.getBlockState(pos.down()).getBlock().getMaterial().isSolid())
          {
        	  if (new Random().nextFloat() <= 0.5F)
        		  worldIn.setBlockState(pos.down(), Blocks.end_portal.getDefaultState());
        	  else
        	  {
        		  worldIn.setBlockState(pos, Blocks.portal.getDefaultState());
        	  }
        	  worldIn.newExplosion(playerIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, 1F, false, false);
              worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D - 1D, (double)pos.getZ() + 0.5D, "portal.travel", 3.0F, itemRand.nextFloat() * 0.2F + 1.0F);
          }

          stack.damageItem(1, playerIn);
          return true;
      }
  }
  
  public EnumRarity getRarity(ItemStack stack)
  {
    return EnumRarity.EPIC;
  }
}
