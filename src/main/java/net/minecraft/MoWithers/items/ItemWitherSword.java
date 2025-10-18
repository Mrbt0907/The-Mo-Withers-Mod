package net.minecraft.MoWithers.items;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.MoWithers.MoWithersAchievments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.witherskulls.EntityItemMoWithers;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemWitherSword extends ItemSword 
{
	public ItemWitherSword(String unlocalizedName, ToolMaterial material) 
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(MoWithers.mowithers);
	}
	
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
	    if (target instanceof EntityLivingBase && attacker instanceof EntityPlayer)
	    {
	      target.addPotionEffect(new PotionEffect(Potion.wither.id, 120, 3));
	    }
	    
	    if (target instanceof EntityWither && attacker instanceof EntityPlayer && !target.isEntityAlive())
	    {
	    	((EntityPlayer)attacker).triggerAchievement(MoWithersAchievments.achievementIrony);
	    }

	    return super.hitEntity(stack, target, attacker);
	}
	
	  public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
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
	      if (playerIn.getRNG().nextFloat() < 0.01F)
	    	  entitywitherskull.setInvulnerable(true);
	      entitywitherskull.posX = playerIn.posX + vec3.xCoord;
	      entitywitherskull.posY = playerIn.posY + vec3.yCoord + 1.5D;
	      entitywitherskull.posZ = playerIn.posZ + vec3.zCoord;
	      if (!worldIn.isRemote)
	      {
	          worldIn.spawnEntityInWorld(entitywitherskull);
	          itemStackIn.damageItem(1, playerIn);
	      }
	      playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
	      playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
	      return itemStackIn;
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