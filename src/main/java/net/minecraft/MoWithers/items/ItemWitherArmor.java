package net.minecraft.MoWithers.items;

import java.util.Random;

import net.minecraft.MoWithers.MoWitherItems;
import net.minecraft.MoWithers.MoWithers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.witherskulls.EntityItemMoWithers;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemWitherArmor extends ItemArmor 
{
	public ItemWitherArmor(String unlocalizedName, ArmorMaterial material, int renderIndex, int armorType) 
	{
		super(material, renderIndex, armorType);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(MoWithers.mowithers);
		this.getArmorMaterial().customCraftingMaterial = Items.nether_star;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) 
	{
		if (player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == MoWitherItems.witherHelmet) 
		{
			this.effectPlayer(player, Potion.nightVision, 0);
			this.effectPlayer(player, Potion.waterBreathing, 0);
		}
		if (player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() == MoWitherItems.witherChestplate) 
		{
			this.effectPlayer(player, Potion.digSpeed, 3);
			this.effectPlayer(player, Potion.damageBoost, 2);
		}
		if (player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem() == MoWitherItems.witherLeggings) 
		{
			this.effectPlayer(player, Potion.fireResistance, 0);
			this.effectPlayer(player, Potion.resistance, 1);
		}
		if (player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem() == MoWitherItems.witherBoots) 
		{
			this.effectPlayer(player, Potion.jump, 2);
			this.effectPlayer(player, Potion.moveSpeed, 2);
		}

		if (this.isWearingFullSet(player, MoWitherItems.witherHelmet, MoWitherItems.witherChestplate, MoWitherItems.witherLeggings, MoWitherItems.witherBoots)) 
		{
					this.effectPlayer(player, Potion.saturation, 0);
					player.capabilities.allowFlying = true;
					player.fallDistance = 0F;
					if (!player.capabilities.isFlying && player.motionY < 0)
						player.motionY *= 0.9F;
					player.removePotionEffect(20);
					player.removePotionEffect(19);
					player.removePotionEffect(18);
					player.removePotionEffect(17);
					player.removePotionEffect(15);
					if (player.ticksExisted % 20 == 0)
						player.heal(0.5F);
					player.extinguish();
		}
		else
		{
				if (!player.capabilities.disableDamage)
					player.capabilities.allowFlying = false;
				else
					player.capabilities.allowFlying = true;
		}
	}

	private void effectPlayer(EntityPlayer player, Potion potion, int amplifier) 
	{
		if (player.getActivePotionEffect(potion) == null || player.getActivePotionEffect(potion).getDuration() <= 1)
		{
			player.addPotionEffect(new PotionEffect(potion.id, 2, amplifier, false, false));
		}
	}

	private boolean isWearingFullSet(EntityPlayer player, Item helmet, Item chestplate, Item leggings, Item boots) 
	{
		return player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == helmet
			&& player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() == chestplate
			&& player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem() == leggings
			&& player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem() == boots;
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