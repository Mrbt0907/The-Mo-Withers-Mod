package net.minecraft.entity.wither;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.MoWithers.MoWitherItems;
import net.minecraft.MoWithers.MoWithersAchievments;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Sculpture
  extends EntityLiving
{
  public static final Item[] essences = { null, MoWitherItems.air_essence, MoWitherItems.earth_essence, MoWitherItems.fire_essence, MoWitherItems.water_essence, MoWitherItems.ice_essence, MoWitherItems.lightning_essence, MoWitherItems.hurricane_essence, MoWitherItems.lava_essence, MoWitherItems.wither_girl_essence, MoWitherItems.pink_wither_girl_essence, MoWitherItems.void_wither_girl_essence, MoWitherItems.wither_essence, MoWitherItems.friendly_essence, MoWitherItems.growth_essence, MoWitherItems.demented_essence, MoWitherItems.negative_essence, MoWitherItems.wither_dragon_essence };
  
  public Sculpture(World worldIn)
  {
    super(worldIn);
    setSize(0.9F, 3.275F);
  }
  
  protected void entityInit()
  {
      super.entityInit();
      this.dataWatcher.addObject(16, new Byte((byte)0));
      this.dataWatcher.addObject(17, new Byte((byte)0));
      this.dataWatcher.addObject(18, new Integer(0));
  }
  
  public boolean getHasBeenEnchanted()
  {
      return this.dataWatcher.getWatchableObjectByte(16) == 1;
  }

  public void setHasBeenEnchanted(boolean b)
  {
	  if (b)
		  this.dataWatcher.updateObject(16, Byte.valueOf((byte)1));
	  else
		  this.dataWatcher.updateObject(16, Byte.valueOf((byte)0));
  }
  
  public boolean getHasEssence()
  {
      return this.dataWatcher.getWatchableObjectByte(17) == 1;
  }

  public void setHasEssence(boolean b)
  {
	  if (b)
		  this.dataWatcher.updateObject(17, Byte.valueOf((byte)1));
	  else
		  this.dataWatcher.updateObject(17, Byte.valueOf((byte)0));
  }
  
  public int getEssenceID()
  {
      return this.dataWatcher.getWatchableObjectInt(18);
  }

  public void setEssenceID(int p_82215_1_)
  {
      this.dataWatcher.updateObject(18, Integer.valueOf(p_82215_1_));
  }
  
  public EnumCreatureAttribute getCreatureAttribute()
  {
      return EnumCreatureAttribute.UNDEAD;
  }
  
  public boolean isEntityUndead()
  {
      return false;
  }
  
  protected void despawnEntity() {}
  
  public float getEyeHeight()
  {
    return 2.7F;
  }
  
  public boolean isBurning()
  {
    return false;
  }
  
  protected String getHurtSound()
  {
    return "dig.gravel";
  }
  
  protected String getDeathSound()
  {
    return "mob.zombie.woodbreak";
  }
  
  protected float getSoundPitch()
  {
    return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.8F;
  }
  
  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    
    if (this.worldObj.getClosestPlayerToEntity(this, 8D) != null && this.ticksExisted == 1)
    	this.faceEntity(this.worldObj.getClosestPlayerToEntity(this, 8D), 180, 0);
    
    this.maxHurtResistantTime = 8;
    this.hurtTime = 0;
    this.maxHurtTime = 0;
    
    this.renderYawOffset = (this.rotationYawHead = this.rotationYaw);
    
    if (this.getHasEssence())
    {
    	for (int i = 0; i <= 3; ++i)
        {
    		this.worldObj.spawnParticle(EnumParticleTypes.CRIT, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, rand.nextDouble() - 0.5D, 0.5D, rand.nextDouble() - 0.5D, new int[0]);
    		this.worldObj.spawnParticle(EnumParticleTypes.CRIT_MAGIC, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, rand.nextDouble() - 0.5D, 0.5D, rand.nextDouble() - 0.5D, new int[0]);
        }
    }
    
    if (this.getHasBeenEnchanted())
    {
    	for (int i = 0; i <= 3; ++i)
        {
    		this.worldObj.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, rand.nextDouble() - 0.5D, 0.5D, rand.nextDouble() - 0.5D, new int[0]);
        }
    }
    
    BlockPos pos = new BlockPos(this.posX, this.posY - (0.25D + this.motionY), this.posZ);
    IBlockState iblockstate = this.worldObj.getBlockState(pos);
    Block block = iblockstate.getBlock();
    if (block.getMaterial() == Material.glass || block.getMaterial() == Material.leaves || block.getMaterial() == Material.plants || block.getMaterial() == Material.gourd || block.getMaterial() == Material.vine || block.getMaterial() == Material.cactus || block.getMaterial() == Material.cake || block.getMaterial() == Material.circuits || block.getMaterial() == Material.fire || block.getMaterial() == Material.ice || block.getMaterial() == Material.web)
    {
    	this.worldObj.destroyBlock(pos, true);
    }
  }
  
  protected int getExperiencePoints(EntityPlayer player)
  {
    return 0;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("HasEssence", this.getHasEssence());
    tagCompound.setBoolean("HasBeenEnchanted", this.getHasBeenEnchanted());
    tagCompound.setInteger("EssenceID", this.getEssenceID());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    this.setHasEssence(tagCompund.getBoolean("HasEssence"));
    this.setHasBeenEnchanted(tagCompund.getBoolean("HasBeenEnchanted"));
    this.setEssenceID(tagCompund.getInteger("EssenceID"));
  }
  
  protected void collideWithNearbyEntities() {}
  
  public void knockBack(Entity p_70653_1_, float p_70653_2_, double p_70653_3_, double p_70653_5_) {}
  
  public void addVelocity(double x, double y, double z) {super.addVelocity(x, y, z);}
  
  public void moveEntity(double x, double y, double z) {super.moveEntity(x, y, z);}
  
  protected int decreaseAirSupply(int p_70682_1_)
  {
    return p_70682_1_;
  }
  
  public AxisAlignedBB getCollisionBox(Entity entityIn)
  {
    return entityIn.getEntityBoundingBox();
  }
  
  public AxisAlignedBB getBoundingBox()
  {
    return getEntityBoundingBox();
  }
  
  public boolean canBeCollidedWith()
  {
    return true;
  }
  
  public boolean canBePushed()
  {
    return false;
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    dropItem(MoWitherItems.sculpture, 1);
    if (this.getHasEssence()) {
      dropItem(essences[this.getEssenceID()], 1);
    }
    if (this.getHasBeenEnchanted()) {
        for (int i = 0; i < this.equipmentDropChances.length; ++i)
        {
            this.equipmentDropChances[i] = 1F;
        }
    }
  }
  
  protected void onDeathUpdate()
  {
    for (int i = 0; i < 150; i++)
    {
      double d2 = this.rand.nextGaussian() * 0.02D;
      double d0 = this.rand.nextGaussian() * 0.02D;
      double d1 = this.rand.nextGaussian() * 0.02D;
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d2, d0, d1, new int[0]);
    }
    setDead();
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    Entity entity = source.getEntity();
    if (((entity instanceof EntityPlayer)) && (((EntityPlayer)entity).getHeldItem() != null) && ((((EntityPlayer)entity).getHeldItem().getItem() instanceof ItemSpade)))
    {
      ((EntityPlayer)entity).swingItem();
      amount *= 10.0F;
    }
    if (isEntityInvulnerable(source)) {
      return false;
    }
    if ((source != DamageSource.drown) && (source != DamageSource.inFire) && (source != DamageSource.onFire) && (source != DamageSource.inWall) && (source != DamageSource.cactus) && (source != DamageSource.starve))
    {
      if ((this.worldObj instanceof WorldServer)) {
        ((WorldServer)this.worldObj).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + this.height / 1.5D, this.posZ, 50, this.width / 4.0F, this.height / 4.0F, this.width / 4.0F, 0.05D, new int[] { Block.getStateId(Blocks.clay.getDefaultState()) });
      }
      return super.attackEntityFrom(source, amount);
    }
    return false;
  }
  
  public boolean interact(EntityPlayer player)
  {
    ItemStack itemstack = player.getCurrentEquippedItem();
    
    if (itemstack == null && player.isSneaking() && this.getEssenceID() != 0 && !this.worldObj.isRemote)
    {
    	if (this.getHasBeenEnchanted()) 
        {
    		this.playSound("mowithers:EssenceTransfer", 5F, 1.5F);
    		if (!player.capabilities.disableDamage)
      	  	dropItem(this.getHeldItem().getItem(), 1);
      	  	this.setCurrentItemOrArmor(0, null);
      	  	this.setHasBeenEnchanted(false);
      	  	return true;
        }
    	else if (this.getHasEssence()) 
        {
    		this.playSound("mowithers:EssenceTransfer", 5F, 1.5F);
    		if (!player.capabilities.disableDamage)
        	dropItem(essences[this.getEssenceID()], 1);
        	this.setEssenceID(0);
        	this.setHasEssence(false);
        	return true;
        }
    }
    
    if ((this.getHasBeenEnchanted()) && this.getHasEssence() && (itemstack != null) && this.getEssenceID() != 0 && (itemstack.getItem() == Items.nether_star) && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)
    {
    	player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
    	this.worldObj.newExplosion(player, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, false);
    	this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
    	player.triggerAchievement(MoWithersAchievments.achievementSpawnMoWither);

      switch (this.getEssenceID())
      {
          case 1:
              EntityAirWither airwither = new EntityAirWither(this.worldObj);
              airwither.copyLocationAndAnglesFrom(this);
              airwither.renderYawOffset = airwither.rotationYaw = airwither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(airwither);
              }
              break;
          case 2:
              EntityEarthWither earthwither = new EntityEarthWither(this.worldObj);
              earthwither.copyLocationAndAnglesFrom(this);
              earthwither.renderYawOffset = earthwither.rotationYaw = earthwither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(earthwither);
              }
              break;
          case 3:
              EntityFireWither firewither = new EntityFireWither(this.worldObj);
              firewither.copyLocationAndAnglesFrom(this);
              firewither.renderYawOffset = firewither.rotationYaw = firewither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(firewither);
              }
              break;
          case 4:
              EntityWaterWither waterwither = new EntityWaterWither(this.worldObj);
              waterwither.copyLocationAndAnglesFrom(this);
              waterwither.renderYawOffset = waterwither.rotationYaw = waterwither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(waterwither);
              }
              break;
          case 5:
              EntityIceWither icewither = new EntityIceWither(this.worldObj);
              icewither.copyLocationAndAnglesFrom(this);
              icewither.renderYawOffset = icewither.rotationYaw = icewither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(icewither);
              }
              break;
          case 6:
              EntityLightningWither lightningwither = new EntityLightningWither(this.worldObj);
              lightningwither.copyLocationAndAnglesFrom(this);
              lightningwither.renderYawOffset = lightningwither.rotationYaw = lightningwither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(lightningwither);
              }
              break;
          case 7:
              EntityHurricaneWither hurricanewither = new EntityHurricaneWither(this.worldObj);
              hurricanewither.copyLocationAndAnglesFrom(this);
              hurricanewither.renderYawOffset = hurricanewither.rotationYaw = hurricanewither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(hurricanewither);
              }
              break;
          case 8:
              EntityMagmaWither magmawither = new EntityMagmaWither(this.worldObj);
              magmawither.copyLocationAndAnglesFrom(this);
              magmawither.renderYawOffset = magmawither.rotationYaw = magmawither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(magmawither);
              }
              break;
          case 9:
              EntityWitherGirl withergirl = new EntityWitherGirl(this.worldObj);
              withergirl.copyLocationAndAnglesFrom(this);
              withergirl.renderYawOffset = withergirl.rotationYaw = withergirl.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
            	  player.triggerAchievement(MoWithersAchievments.achievementSpawnWitherGirl);
                this.worldObj.spawnEntityInWorld(withergirl);
              }
              break;
          case 10:
              EntityWitherGirlPink pinkwithergirl = new EntityWitherGirlPink(this.worldObj);
              pinkwithergirl.copyLocationAndAnglesFrom(this);
              pinkwithergirl.renderYawOffset = pinkwithergirl.rotationYaw = pinkwithergirl.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
            	  player.triggerAchievement(MoWithersAchievments.achievementSpawnPinkWitherGirl);
                this.worldObj.spawnEntityInWorld(pinkwithergirl);
              }
              break;
          case 11:
              EntityWitherGirlVoid voidwithergirl = new EntityWitherGirlVoid(this.worldObj);
              voidwithergirl.copyLocationAndAnglesFrom(this);
              voidwithergirl.renderYawOffset = voidwithergirl.rotationYaw = voidwithergirl.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
            	  player.triggerAchievement(MoWithersAchievments.achievementSpawnVoidWitherGirl);
                this.worldObj.spawnEntityInWorld(voidwithergirl);
              }
              break;
          case 12:
        	  for (int i = 0; i <= 2; ++i)
        	  {
        		  this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
              EntityWither wither = new EntityWither(this.worldObj);
              wither.copyLocationAndAnglesFrom(this);
              wither.motionY = i;
              wither.renderYawOffset = wither.rotationYaw = wither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(wither);
              }
        	  }
              break;
          case 13:
        	  EntityFriendlyWither friendlywither = new EntityFriendlyWither(this.worldObj);
              friendlywither.copyLocationAndAnglesFrom(this);
              friendlywither.renderYawOffset = friendlywither.rotationYaw = friendlywither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(friendlywither);
              }
              break;
          case 14:
        	  EntityPinkWither pinkwither = new EntityPinkWither(this.worldObj);
        	  pinkwither.copyLocationAndAnglesFrom(this);
        	  pinkwither.renderYawOffset = pinkwither.rotationYaw = pinkwither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(pinkwither);
              }
              break;
          case 15:
        	  EntityDementedWither dementedwither = new EntityDementedWither(this.worldObj);
              dementedwither.copyLocationAndAnglesFrom(this);
              dementedwither.renderYawOffset = dementedwither.rotationYaw = dementedwither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(dementedwither);
              }
              break;
          case 16:
        	  EntityVoidWither voidwither = new EntityVoidWither(this.worldObj);
              voidwither.copyLocationAndAnglesFrom(this);
              voidwither.renderYawOffset = voidwither.rotationYaw = voidwither.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(voidwither);
              }
              break;
          case 17:
        	  EntityWitherDragon witherdragon = new EntityWitherDragon(this.worldObj);
              witherdragon.copyLocationAndAnglesFrom(this);
              witherdragon.renderYawOffset = witherdragon.rotationYaw = witherdragon.rotationYawHead = this.rotationYawHead;
              if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(witherdragon);
              }
              break;
          default:
      }
      
      if (!this.worldObj.isRemote)
      {
        Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)).iterator();
        EntityPlayer entityplayer;
        while (iterator.hasNext()) {
          entityplayer = (EntityPlayer)iterator.next();
        }
      }
      setDead();
      player.swingItem();
      
      return true;
    }
    if ((!this.getHasEssence()) && (itemstack != null))
    {
    	if (itemstack.getItem() == MoWitherItems.air_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(1);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.earth_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(2);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.fire_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(3);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.water_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(4);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.ice_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(5);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.lightning_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(6);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.hurricane_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(7);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.lava_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(8);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.wither_girl_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(9);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.pink_wither_girl_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(10);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.void_wither_girl_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(11);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.wither_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(12);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.friendly_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(13);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.growth_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(14);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.demented_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(15);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.negative_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(16);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	else if (itemstack.getItem() == MoWitherItems.wither_dragon_essence)
    	{
    		player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
            this.setHasEssence(true);
            this.setEssenceID(17);
    		this.playSound("mowithers:EssenceTransfer", 5F, 1F);
            if (!player.capabilities.isCreativeMode) {
              itemstack.stackSize -= 1;
            }
            if (itemstack.stackSize <= 0) {
              player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    	}
    	
        player.swingItem();
    	
        return true;
    }
    if ((!this.getHasBeenEnchanted()) && this.getEssenceID() != 0 && (itemstack != null))
    {
      if (itemstack.getItem() == Items.enchanted_book)
      {
    	  player.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(itemstack.getItem())]);
        this.setHasBeenEnchanted(true);
        this.setCurrentItemOrArmor(0, new ItemStack(itemstack.getItem()));
        this.playSound("mowithers:Augment", 5F, 1F);
        if (!player.capabilities.isCreativeMode) {
          itemstack.stackSize -= 1;
        }
        if (itemstack.stackSize <= 0) {
          player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
        }
      }
      player.swingItem();
      
      return true;
    }
    return false;
  }
}