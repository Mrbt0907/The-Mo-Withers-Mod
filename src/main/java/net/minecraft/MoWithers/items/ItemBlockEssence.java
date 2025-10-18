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
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.wither.*;
import net.minecraft.entity.witherskulls.*;
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

public class ItemBlockEssence extends Item
{
	private Block spawnblock;
  public ItemBlockEssence(String unlocalizedName, Block block)
  {
	this.setUnlocalizedName(unlocalizedName);
	this.spawnblock = block;
    this.setCreativeTab(MoWithers.mowithers);
  }
  
  public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
  {
      if (worldIn.isRemote)
      {
          return true;
      }
      else
      {
          if (!playerIn.canPlayerEdit(pos, side, stack))
          {
              return false;
          }
          else
          {
              if (worldIn.isAirBlock(pos.up()) && worldIn.getBlockState(pos).getBlock() == this.spawnblock)
              {
            	  worldIn.newExplosion(playerIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 0F, false, false);
                  worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "item.fireCharge.use", 1.0F, (itemRand.nextFloat() - itemRand.nextFloat()) * 0.2F + 1.0F);
                  worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.2F);
                  
                  if (this.spawnblock == Blocks.sand)
                  {
                      EntitySandWither wither = new EntitySandWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.gravel)
                  {
                      EntityGravelWither wither = new EntityGravelWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.obsidian)
                  {
                      EntityObsidianWither wither = new EntityObsidianWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.bedrock)
                  {
                      EntityBedrockWither wither = new EntityBedrockWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 1.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.coal_block)
                  {
                      EntityCoalWither wither = new EntityCoalWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.redstone_block)
                  {
                      EntityRedstoneWither wither = new EntityRedstoneWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.lapis_block)
                  {
                      EntityLapisWither wither = new EntityLapisWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.iron_block)
                  {
                      EntityIronWither wither = new EntityIronWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.gold_block)
                  {
                      EntityGoldenWither wither = new EntityGoldenWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.diamond_block)
                  {
                      EntityDiamondWither wither = new EntityDiamondWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  if (this.spawnblock == Blocks.emerald_block)
                  {
                      EntityEmeraldWither wither = new EntityEmeraldWither(worldIn);
                      wither.setInvulTime(1);
                      wither.setAttackTarget(playerIn);
                      wither.setLocationAndAngles((double)pos.getX() + 0.5D, (double)pos.getY() - 2.5F, (double)pos.getZ() + 0.5D, 0F, 0F);
                      worldIn.spawnEntityInWorld(wither);
                  }
                  
                  for (int i = 0; i < 3; i++)
                  worldIn.playAuxSFX(2001, pos, Block.getStateId(worldIn.getBlockState(pos)));
                  if (worldIn.getBlockState(pos).getBlock().getBlockHardness(worldIn, pos) != -1F)
                  worldIn.setBlockState(pos, Blocks.air.getDefaultState(), 3);
                  
                  if (!playerIn.capabilities.isCreativeMode)
                  {
                      --stack.stackSize;
                  }
              }

              return true;
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
      tooltip.add("You sense it's power.");
      tooltip.add("All the good and evil this one item could do.");
  }
}
