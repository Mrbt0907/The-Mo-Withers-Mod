package net.minecraft.entity.wither;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.MoWithers.DamageSourceExtra;
import net.minecraft.MoWithers.MoWithersAchievments;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.witherskulls.EntityVoidSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherGirlVoid
  extends EntityGolem
  implements IBossDisplayData, IRangedAttackMob, INpc
{
  private float[] field_82220_d = new float[2];
  private float[] field_82221_e = new float[2];
  private float[] field_82217_f = new float[2];
  private float[] field_82218_g = new float[2];
  private int[] field_82223_h = new int[2];
  private int[] field_82224_i = new int[2];
  private int blockBreakCounter;
  public boolean shouldStartFlying;
  public boolean isDoingIt;
  public boolean isPregnant;
  public int timeSinceDoingIt;
  public int bangTimer;
  Village villageObj;
  private int homeCheckTimer;
  private int musicTimer;
  private int blinkTimer;
  private int numbOfOthers;
  private EntityAIVoidWitherGirlFollowPlayer aiFollowLover = new EntityAIVoidWitherGirlFollowPlayer(this, 0.5D, 12.0F, 4.0F);
  private EntityAIVoidWitherGirlFollowFather aiFollowFather = new EntityAIVoidWitherGirlFollowFather(this, 0.5D, 12.0F, 4.0F);
  private EntityAIVoidWitherGirlFollowFather aiFollowDaddy = new EntityAIVoidWitherGirlFollowFather(this, 1.0D, 8.0F, 4.0F);
  
  public EntityWitherGirlVoid(World worldIn)
  {
    super(worldIn);
    setSize(0.5F, 1.95F);
    setHealth(getMaxHealth());
    this.isImmuneToFire = true;
    this.ignoreFrustumCheck = true;
    ((PathNavigateGround)getNavigator()).func_179693_d(true);
    this.tasks.addTask(0, new EntityAIAvoidEntity(this, new Predicate()
    {
      public boolean func_179958_a(Entity p_179958_1_)
      {
        return EntityWitherGirlVoid.this.isChild() && ((p_179958_1_ instanceof IMob));
      }
      
      public boolean apply(Object p_apply_1_)
      {
        return func_179958_a((Entity)p_apply_1_);
      }
    }, 8.0F, 1.0D, 1.0D));
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIOpenDoor(this, true));
    this.tasks.addTask(1, new EntityAIBreakDoor(this));
    this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 0.9D, true));
    this.tasks.addTask(5, new EntityAIWander(this, 0.5D));
    this.tasks.addTask(5, new EntityAIWatchClosest2(this, EntityIronGolem.class, 4.0F, 0.1F));
    this.tasks.addTask(5, new EntityAIWatchClosest2(this, EntityVillager.class, 4.0F, 0.1F));
    this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 4.0F, 0.1F));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 0, false, false, IMob.mobSelector));
    this.experienceValue = 1500000;
  }
  
  public boolean canAttackClass(Class p_70686_1_)
  {
      return p_70686_1_ != EntityWitherGirl.class && p_70686_1_ != EntityWitherGirlPink.class && p_70686_1_ != EntityWitherGirlVoid.class;
  }
  
  protected PathNavigate func_175447_b(World worldIn)
  {
	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
      return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? new PathNavigateSwimmer(this, worldIn) : new PathNavigateGround(this, worldIn);
  }
  
  public float getRenderSizeModifier()
  {
    return getGrowthTime() > 1 ? 1.0F * (this.getGrowthTime() * 0.01F) : 1.0F;
  }
  
  public float getEyeHeight()
  {
	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
		  if (this.isChild())
		  {
			  if (this.getGrowthTime() > 1)
			  {
				  return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? 0.2F * (this.getGrowthTime() * 0.01F) : (this.isRiding() ? 0.3F * (this.getGrowthTime() * 0.01F) : 0.725F * (this.getGrowthTime() * 0.01F));
			  }
			  else
			  {
				  return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? 0.2F : (this.isRiding() ? 0.3F : 0.725F);
			  }
		  }
		  else
		  {
			  if (this.getGrowthTime() > 1)
			  {
				  return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? 0.3F * (this.getGrowthTime() * 0.01F) : 1.95F * (this.getGrowthTime() * 0.01F);
			  }
			  else
			  {
				  return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? 0.3F : 1.9F;
			  }
		  }
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50000.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.45D);
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0D);
    getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(200.0D);
  }
  
  protected void entityInit()
  {
    super.entityInit();
    getDataWatcher().addObject(11, Byte.valueOf((byte)0));
    getDataWatcher().addObject(12, Byte.valueOf((byte)0));
    getDataWatcher().addObject(13, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(17, new Integer(0));
    this.dataWatcher.addObject(18, new Integer(0));
    this.dataWatcher.addObject(19, new Integer(0));
    this.dataWatcher.addObject(20, new Integer(0));
    this.dataWatcher.addObject(21, new Integer(0));
    this.dataWatcher.addObject(22, "");
    this.dataWatcher.addObject(23, "");
  }
  
  public String getFatherId()
  {
      return this.dataWatcher.getWatchableObjectString(22);
  }

  public void setFatherId(String ownerUuid)
  {
      this.dataWatcher.updateObject(22, ownerUuid);
  }

  public EntityPlayer getFather()
  {
      try
      {
          UUID uuid = UUID.fromString(this.getFatherId());
          return uuid == null ? null : this.worldObj.getPlayerEntityByUUID(uuid);
      }
      catch (IllegalArgumentException illegalargumentexception)
      {
          return null;
      }
  }

  public boolean isFather(EntityPlayer entityIn)
  {
      return entityIn == this.getFather();
  }
  
  public String getMateId()
  {
      return this.dataWatcher.getWatchableObjectString(23);
  }

  public void setMateId(String ownerUuid)
  {
      this.dataWatcher.updateObject(23, ownerUuid);
  }

  public EntityPlayer getMate()
  {
      try
      {
          UUID uuid = UUID.fromString(this.getMateId());
          return uuid == null ? null : this.worldObj.getPlayerEntityByUUID(uuid);
      }
      catch (IllegalArgumentException illegalargumentexception)
      {
          return null;
      }
  }

  public boolean isMate(EntityPlayer entityIn)
  {
      return entityIn == this.getMate();
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Growth", getGrowthTime());
    tagCompound.setInteger("Invul", getInvulTime());
    tagCompound.setBoolean("Theia", isTheia());
    tagCompound.setBoolean("IsBaby", isChild());
    tagCompound.setInteger("TimeBetweenBangs", this.timeSinceDoingIt);
    if (this.getFather() == null)
    {
        tagCompound.setString("FatherUUID", "");
    }
    else
    {
        tagCompound.setString("FatherUUID", this.getFatherId());
    }
    if (this.getMate() == null)
    {
        tagCompound.setString("MateUUID", "");
    }
    else
    {
        tagCompound.setString("MateUUID", this.getMateId());
    }
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setGrowthTime(tagCompund.getInteger("Growth"));
    setInvulTime(tagCompund.getInteger("Invul"));
    setTheia(tagCompund.getBoolean("Theia"));
    setChild(tagCompund.getBoolean("IsBaby"));
    if (tagCompund.hasKey("TimeBetweenBangs", 99)) {
        this.timeSinceDoingIt = tagCompund.getInteger("TimeBetweenBangs");
      }
    String s2 = "";

    if (tagCompund.hasKey("FatherUUID", 8))
    {
        s2 = tagCompund.getString("FatherUUID");
    }
    else
    {
        String s11 = tagCompund.getString("Father");
        s2 = PreYggdrasilConverter.func_152719_a(s11);
    }

    if (s2.length() > 0)
    {
        this.setFatherId(s2);
    }
    String s3 = "";

    if (tagCompund.hasKey("MateUUID", 8))
    {
        s3 = tagCompund.getString("MateUUID");
    }
    else
    {
        String s11 = tagCompund.getString("Mate");
        s3 = PreYggdrasilConverter.func_152719_a(s11);
    }

    if (s3.length() > 0)
    {
        this.setMateId(s3);
    }
  }
  
  public boolean isChild()
  {
    return getDataWatcher().getWatchableObjectByte(11) == 1;
  }
  
  protected int getExperiencePoints(EntityPlayer player)
  {
    if (isChild()) {
      this.experienceValue = ((int)(this.experienceValue * 2.5F));
    }
    return super.getExperiencePoints(player);
  }
  
  public void setChild(boolean childZombie)
  {
    getDataWatcher().updateObject(11, Byte.valueOf((byte)(childZombie ? 1 : 0)));
  }
  
  public boolean isTheia()
  {
    return getDataWatcher().getWatchableObjectByte(12) == 1;
  }
  
  public void setTheia(boolean childZombie)
  {
    getDataWatcher().updateObject(12, Byte.valueOf((byte)(childZombie ? 1 : 0)));
  }
  
  public boolean isInLove()
  {
    return getDataWatcher().getWatchableObjectByte(13) == 1;
  }
  
  public void setInLove(boolean childZombie)
  {
	  getDataWatcher().updateObject(13, Byte.valueOf((byte)(childZombie ? 1 : 0)));
  }
  
  protected String getLivingSound()
  {
    return this.isChild() ? "mowithers:WitherGirlIdle" : (this.isPregnant ? null : "mowithers:WitherGirlVoidIdle");
  }
  
  protected String getHurtSound()
  {
    return this.isChild() ? "mowithers:WitherGirlHurt" : "mowithers:WitherGirlVoidHurt";
  }
  
  protected String getDeathSound()
  {
    return this.isChild() ? "mowithers:WitherGirlDeath" : "mowithers:WitherGirlVoidDeath";
  }
  
  protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
  {
    Block.SoundType soundtype = p_180429_2_.stepSound;
    if (this.worldObj.getBlockState(p_180429_1_.up()).getBlock() == Blocks.snow_layer)
    {
      soundtype = Blocks.snow_layer.stepSound;
      playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.5F, soundtype.getFrequency());
    }
    else if (!p_180429_2_.getMaterial().isLiquid())
    {
      playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.5F, soundtype.getFrequency());
    }
  }
  
  public Village getVillage()
  {
    return this.villageObj;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isBlinking()
  {
      return this.dataWatcher.getWatchableObjectByte(16) != 0;
  }
  
  public void onLivingUpdate()
  {
	  if (this.isChild())
	  {
		  this.numbOfOthers = 0;
		  this.timeSinceDoingIt = 0;
		  this.isDoingIt = false;
		  this.isPregnant = false;
		  this.bangTimer = 0;
		  this.setInLove(false);
		  this.setAttackTarget(null);
	  }
	  
	  if (this.rotationYaw < (this.rotationYawHead - 30))
		  this.rotationYaw = (this.rotationYawHead - 30);
	  
	  if (this.rotationYaw > (this.rotationYawHead + 30))
		  this.rotationYaw = (this.rotationYawHead + 30);
	  
	  if (this.getGrowthTime() <= 0)
		  this.setGrowthTime(0);
	  else
		  this.setGrowthTime(this.getGrowthTime() - 1);
	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
	  if (this.isRiding())
	  {
		  if (this.ridingEntity instanceof EntityMinecart || this.ridingEntity instanceof EntityBoat)
		  {
			  this.renderYawOffset = this.rotationYaw = this.ridingEntity.rotationYaw + 90F;
		  }
		  else
		  {
			  this.renderYawOffset = this.rotationYaw = this.rotationYawHead = this.ridingEntity.rotationYaw;
			  if (this.ridingEntity instanceof EntityLivingBase)
			  {
				  if (this.isChild())
					  this.rotationYawHead = ((EntityLivingBase)this.ridingEntity).rotationYaw;
				  else
					  this.rotationYawHead = ((EntityLivingBase)this.ridingEntity).rotationYawHead;
			  }
		  }
	  }
	  
	  ++this.blinkTimer;
      byte b1 = this.dataWatcher.getWatchableObjectByte(16);
      byte b0 = (byte)(this.blinkTimer > 0 ? 1 : 0);

      if (b1 != b0)
      {
          this.dataWatcher.updateObject(16, Byte.valueOf(b0));
      }
      
      if (this.blinkTimer == 4)
      {
      	if (this.isChild())
      	{
              this.blinkTimer = -10 - this.rand.nextInt(50);
      	}
      	else
      	{
      		this.blinkTimer = -20 - this.rand.nextInt(100);
      	}
      }
	  
	  if (this.timeSinceDoingIt < 4000 && !this.worldObj.isRemote)
	  {
		  this.isPregnant = false;
		  this.setSprinting(false);
	  }
      
	  if (this.ridingEntity != null && this.ridingEntity instanceof EntityLivingBase && !this.isChild())
	  {
		  this.renderYawOffset = this.rotationYaw = this.rotationYawHead = this.ridingEntity.rotationYaw;
		  if (this.ridingEntity instanceof EntityLivingBase)
			  this.rotationYawHead = ((EntityLivingBase)this.ridingEntity).rotationYaw;
		  this.faceEntity(this.ridingEntity, 180F, 30F);
		  this.limbSwingAmount = 1.5F;
		  this.hurtResistantTime = 40;
		  if (this.ridingEntity instanceof EntityPlayer)
		  ((EntityPlayer)this.ridingEntity).addExhaustion(0.5F);
		  ((EntityLivingBase)this.ridingEntity).limbSwingAmount = 1.5F;
		  if (this.ridingEntity.hurtResistantTime > 20)
			  this.ridingEntity.hurtResistantTime = 20;
		  ++this.bangTimer;
		  this.rotationPitch = -15F - this.rand.nextFloat() * 30F;
          if ((this.ridingEntity.onGround) && (this.rand.nextInt(5) == 0))
          {
        	  this.ridingEntity.motionY = 0.5D;
        	  this.ridingEntity.attackEntityFrom(DamageSource.fall, 1.0F);
          }
          if (this.ticksExisted % 10 == 0)
          {
        	  this.blinkTimer = 0;
        	  ((EntityLivingBase)this.ridingEntity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30, 9));
        	  this.ridingEntity.motionX = 0F;
        	  this.ridingEntity.motionZ = 0F;
              this.playSound("mowithers:VoidWitherHurt", getSoundVolume(), getSoundPitch());
              this.playSound("mowithers:WitherGirlVoidHurtAlternate", getSoundVolume(), getSoundPitch());
              this.playSound("mowithers:OHGOD", getSoundVolume(), getSoundPitch());
        	  this.setGrowthTime(0);
        	  this.ridingEntity.attackEntityFrom(DamageSource.outOfWorld, 1F);
          }
	  }
	  if (this.timeSinceDoingIt < 4000 && !this.worldObj.isRemote)
	  {
		  this.isPregnant = false;
		  this.setSprinting(false);
	  }
	  
	  if (this.timeSinceDoingIt > 4000 && !(this.timeSinceDoingIt < 4000) && !this.worldObj.isRemote)
	  {
		  this.isPregnant = true;
		  this.setSprinting(true);
		  if (this.ticksExisted % 98 == 0)
			  this.playSound("mowithers:JestBreathe", 0.1F, 0.85F);
	  }
	  if (this.timeSinceDoingIt == 4000 && !this.worldObj.isRemote)
	  {
		  this.setInLove(false);
		  this.isPregnant = false;
		  this.setSneaking(false);
		  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 0.8F);
		  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 0.8F);
		  this.playSound("game.player.hurt", 2F, 0.6F);
		  this.playSound("game.player.hurt", 2F, 0.6F);
		  this.playSound("game.player.hurt", 2F, 0.6F);
		  this.playSound("game.player.hurt", 2F, 0.6F);
		  EntityWitherGirlVoid entityocelot = new EntityWitherGirlVoid(this.worldObj);
		  this.worldObj.createExplosion(this, this.posX, this.posY + 0.6D, this.posZ, 2F, false);
          entityocelot.setLocationAndAngles(this.posX, this.posY + 0.6D, this.posZ, this.rotationYaw, 0.0F);
          entityocelot.rotationPitch = 90F;
          entityocelot.setChild(true);
          if (this.getMate() != null)
          {
          entityocelot.setFatherId(this.getMate().getUniqueID().toString());
          this.getMate().triggerAchievement(MoWithersAchievments.achievementWitherFamily);
          }
          this.worldObj.spawnEntityInWorld(entityocelot);
	  }
	    if (this.timeSinceDoingIt == 4000 && this.worldObj.isRemote)
	    {
	    	for (int j = 0; j < 30; j++)
	        {
	            double d0 = this.rand.nextGaussian() * 0.02D;
	            double d1 = this.rand.nextGaussian() * 0.02D;
	            double d2 = this.rand.nextGaussian() * 0.02D;
	            this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + (double)(this.rand.nextFloat() - 0.5F), this.posY + 0.5D + (double)(this.rand.nextFloat() - 0.5F), this.posZ + (double)(this.rand.nextFloat() - 0.5F), d0, d1, d2, new int[0]);
	        }
	    }
	  if (this.timeSinceDoingIt == 0)
	  {
		  this.bangTimer = 0;
	  }

	  if (this.bangTimer >= 200)
	  {
		  if (this.ridingEntity instanceof EntityPlayer)
		  {
			  this.ridingEntity.motionY += 1.5F;
			  this.func_174815_a(this, this.ridingEntity);
			  	((EntityPlayer)this.ridingEntity).triggerAchievement(MoWithersAchievments.achievementWitherLove);
			  	++this.numbOfOthers;
			    if (!this.worldObj.isRemote)
			    {
			      Iterator iterator1 = this.worldObj.getEntitiesWithinAABB(EntityWitherGirl.class, getEntityBoundingBox().expand(6.0D, 6.0D, 6.0D)).iterator();
			      Iterator iterator2 = this.worldObj.getEntitiesWithinAABB(EntityWitherGirlPink.class, getEntityBoundingBox().expand(6.0D, 6.0D, 6.0D)).iterator();
			      Iterator iterator3 = this.worldObj.getEntitiesWithinAABB(EntityWitherGirlVoid.class, getEntityBoundingBox().expand(6.0D, 6.0D, 6.0D)).iterator();
			      EntityWitherGirl withergirl;
			      while (iterator1.hasNext()) {
			    	  withergirl = (EntityWitherGirl)iterator1.next();
			    	  if (withergirl.isInLove())
			    	  ++this.numbOfOthers;
			      }
			      EntityWitherGirlPink pinkwithergirl;
			      while (iterator2.hasNext()) {
			    	  pinkwithergirl = (EntityWitherGirlPink)iterator2.next();
			    	  if (pinkwithergirl.isInLove())
			    	  ++this.numbOfOthers;
			      }
			      EntityWitherGirlVoid voidwithergirl;
			      while (iterator3.hasNext()) {
			    	  voidwithergirl = (EntityWitherGirlVoid)iterator3.next();
			    	  if (voidwithergirl.isInLove())
			    	  ++this.numbOfOthers;
			      }
			    }
			    if (this.numbOfOthers >= 2)
			    	((EntityPlayer)this.ridingEntity).triggerAchievement(MoWithersAchievments.achievementThreesome);
				this.setMateId(((EntityPlayer)this.ridingEntity).getUniqueID().toString());
				this.ridingEntity = null;
	            if (!this.worldObj.isDaytime() && this.worldObj.getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
	            {
	                long i = this.worldObj.getWorldTime() + 24000L;
	                this.worldObj.setWorldTime(i - i % 24000L);
	            }
		  }
		  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 0.85F);
		  if (!this.worldObj.isRemote)
		  {
			  this.setInLove(false);
			  this.isDoingIt = false;
			  this.bangTimer = 0;
			  if (this.rand.nextInt(10) == 0)
			  {
				  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 0.85F);
				  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 0.85F);
				  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 0.85F);
				  this.timeSinceDoingIt = 72000;
		            int i = 1000000;

		            while (i > 0)
		            {
		                int j = EntityXPOrb.getXPSplit(i);
		                i -= j;
		                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
		            }
			  }
			  else
			  {
				  this.timeSinceDoingIt = 1200;
		            int i = 20000;

		            while (i > 0)
		            {
		                int j = EntityXPOrb.getXPSplit(i);
		                i -= j;
		                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
		            }
			  }
		  }
		  
	        for (int k = (int)this.posX - 1; k < (int)this.posX + 1; ++k)
	        {
	            for (int l = (int)this.posY - 2; l < (int)this.posY + 2; ++l)
	            {
	                for (int i1 = (int)this.posZ - 1; i1 < (int)this.posZ + 1; ++i1)
	                {
	                	BlockPos pos = new BlockPos(k, l, i1);
	                	Block block = this.worldObj.getBlockState(pos).getBlock();

	                    if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, pos))
	                    {
	                        this.worldObj.setBlockState(pos, Blocks.snow_layer.getDefaultState());
	                    }
	                }
	            }
	        }
	        this.bangTimer = 0;
	        this.numbOfOthers = 0;
	  }
	  --this.timeSinceDoingIt;
	  
    if ((this.musicTimer <= 0) && (isTheia()))
    {
      this.musicTimer = 1400;
      playSound("mowithers:TheiasTheme", 10000.0F, 1.0F);
    }
    this.musicTimer -= 1;
    if (isChild()) {
        if (getGrowthTime() > 1)
        {
        	this.worldObj.setWorldTime(18000L);
      	if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
    		setSize(0.5F * (this.getGrowthTime() * 0.01F), 0.5F * (this.getGrowthTime() * 0.01F));
      	else if (this.isRiding())
      		setSize(0.2F * (this.getGrowthTime() * 0.01F), 0.1F * (this.getGrowthTime() * 0.01F));
    	else
    		setSize(0.5F * (this.getGrowthTime() * 0.01F), 0.95F * (this.getGrowthTime() * 0.01F));
      	this.stepHeight = 0.75F * (this.getGrowthTime() * 0.01F);
        }
        else
        {
        	if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
        		setSize(0.5F, 0.5F);
        	else if (this.isRiding())
          		setSize(0.2F, 0.1F);
        	else
        		setSize(0.5F, 0.95F);
        	this.stepHeight = 0.75F;
        }
    } else {
        if (getGrowthTime() > 1)
        {
        	this.worldObj.setWorldTime(18000L);
        	if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
        		setSize(0.6F * (this.getGrowthTime() * 0.01F), 0.6F * (this.getGrowthTime() * 0.01F));
        	else
        		setSize(0.6F * (this.getGrowthTime() * 0.01F), 2.35F * (this.getGrowthTime() * 0.01F));
          this.stepHeight = 0.75F * (this.getGrowthTime() * 0.01F);
        }
        else
        {
        	if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
        		setSize(0.6F, 0.6F);
        	else
        		setSize(0.6F, 1.95F);
          this.stepHeight = 0.75F;
        }
    }
	  if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
	  {
		  if (this.getAttackTarget() != null)
			  this.getMoveHelper().setMoveTo(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ, 0.8D);
		    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D) {
		        this.renderYawOffset = this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F;
		      }
          float f3 = this.rotationYaw * (float)Math.PI / 180.0F;
          float f11 = MathHelper.sin(f3);
          float f4 = MathHelper.cos(f3);
        for (int i = 0; i < 2; ++i)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * 2D, this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ + 0.5D + (this.rand.nextDouble() - 0.5D) * 2D, 0.0D, 0.0D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width + (double)(f4 * 0.5F) - (double)(f11 * 1.0F), this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ + 0.5D + (this.rand.nextDouble() - 0.5D) * (double)this.width + (double)(f11 * 0.5F) + (double)(f4 * 1.0F), 0.0D, 0.0D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width + (double)(f4 * 0.5F) + (double)(f11 * 1.0F), this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ - 0.5D + (this.rand.nextDouble() - 0.5D) * (double)this.width + (double)(f11 * 0.5F) - (double)(f4 * 1.0F), 0.0D, 0.0D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width - (double)(f4 * 0.5F) - (double)(f11 * 1.0F), this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ + 0.5D + (this.rand.nextDouble() - 0.5D) * (double)this.width - (double)(f11 * 0.5F) + (double)(f4 * 1.0F), 0.0D, 0.0D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width - (double)(f4 * 0.5F) + (double)(f11 * 1.0F), this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ - 0.5D + (this.rand.nextDouble() - 0.5D) * (double)this.width - (double)(f11 * 0.5F) - (double)(f4 * 1.0F), 0.0D, 0.0D, 0.0D, new int[0]);
        }
	  }
    if (isTheia())
    {
      setCustomNameTag("\u00A70Theia");
      setCanPickUpLoot(true);
    }
    if ((!this.onGround) && (this.motionY < 0.0D)) {
      this.motionY *= 0.5D;
    }
    if (this.rand.nextInt(120) == 0) {
      this.shouldStartFlying = false;
    }
    
	if (this.blockBreakCounter <= 0) {
        this.blockBreakCounter = 20;
      }
    
    if ((!this.worldObj.isRemote) && (this.shouldStartFlying == true) && (getWatchedTargetId(0) > 0))
    {
      Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
      if ((entity != null) && (canEntityBeSeen(entity)))
      {
    	  this.rotationPitch = -30F;
        if ((this.posY < entity.posY) || ((!isArmored()) && (this.posY < entity.posY + 1.0D)))
        {
          if (this.motionY < 0.0D) {
            this.motionY = 0.0D;
          }
          this.motionY += (0.9D - this.motionY);
        }
        double d0 = entity.posX - this.posX;
        double d1 = entity.posZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1;
        if (d3 > 16.0D)
        {
          double d5 = MathHelper.sqrt_double(d3);
          this.motionX += (d0 / d5 * 0.9D - this.motionX);
          this.motionZ += (d1 / d5 * 0.9D - this.motionZ);
        }
      }
    }
    super.onLivingUpdate();
    for (int i = 0; i < 2; i++)
    {
      this.field_82218_g[i] = this.field_82221_e[i];
      this.field_82217_f[i] = this.field_82220_d[i];
    }
    for (int i = 0; i < 2; i++)
    {
      int j = getWatchedTargetId(i + 1);
      Entity entity1 = null;
      if (j > 0) {
        entity1 = this.worldObj.getEntityByID(j);
      }
      if (entity1 != null)
      {
        double d1 = func_82214_u(i + 1);
        double d3 = func_82208_v(i + 1);
        double d5 = func_82213_w(i + 1);
        double d6 = entity1.posX - d1;
        double d7 = entity1.posY + entity1.getEyeHeight() - d3;
        double d8 = entity1.posZ - d5;
        double d9 = MathHelper.sqrt_double(d6 * d6 + d8 * d8);
        float f = (float)(Math.atan2(d8, d6) * 180.0D / 3.141592653589793D) - 90.0F;
        float f1 = (float)-(Math.atan2(d7, d9) * 180.0D / 3.141592653589793D);
        this.field_82220_d[i] = func_82204_b(this.field_82220_d[i], f1, 40.0F);
        this.field_82221_e[i] = func_82204_b(this.field_82221_e[i], f, 10.0F);
      }
      else
      {
        this.field_82221_e[i] = func_82204_b(this.field_82221_e[i], this.renderYawOffset, 10.0F);
      }
    }
    if (this.isInLove() && this.worldObj.isRemote)
    {
    	for (int j = 0; j < 1; j++)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
        }
    }
    
    boolean flag = isArmored();
    for (int j = 0; j < 3; j++)
    {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
      if ((flag) && (this.worldObj.rand.nextInt(4) == 0)) {
        this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
      }
    }
    int j;
    for (j = 0; j < 3; j++)
    {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
      if ((flag) && (this.worldObj.rand.nextInt(4) == 0)) {
        this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
      }
    }
  }
  
  /**
   * Moves the entity based on the specified heading.  Args: strafe, forward
   */
  public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
  {
      if (this.isServerWorld())
      {
  	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
  	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
          if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
          {
              this.moveFlying(p_70612_1_, p_70612_2_, 0.1F);
              this.moveEntity(this.motionX, this.motionY, this.motionZ);
              this.motionX *= 0.825D;
              this.motionY *= 0.825D;
              this.motionZ *= 0.825D;
              if (this.getGrowthTime() > 1)
              {
                  this.motionX *= 0.75D;
                  this.motionY *= 0.75D;
                  this.motionZ *= 0.75D;
              }
              float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.25D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.25D) * 0.25F;

              if (f > 2.0F)
              {
                  f = 2.0F;
              }

              if (this.rand.nextInt(2) == 0)
              this.playSound(this.getSwimSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.5F);
          }
          else
          {
              super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
          }
      }
      else
      {
          super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
      }
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_)
  {
    boolean flag = p_70652_1_.attackEntityFrom(DamageSourceExtra.causeWitherGirlDamage(this), (int)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() + (this.getGrowthTime() > 0 ? (this.getGrowthTime()) : 0F)) ;
    swingItem();
    p_70652_1_.hurtResistantTime = 0;
    if (flag && this.getDistanceSqToEntity(p_70652_1_) > 4D) {
      func_174815_a(this, p_70652_1_);
      double d2 = p_70652_1_.posX - this.posX;
      double d3 = p_70652_1_.posZ - this.posZ;
      double d4 = d2 * d2 + d3 * d3;
      p_70652_1_.addVelocity(d2 / d4 * 6.0D, 0.75D, d3 / d4 * 6.0D);
    }
    return flag;
  }
  
  protected void updateAITasks()
  {
    if (getInvulTime() > 0)
    {
      int i = getInvulTime() - 1;
      if (i <= 0)
      {
        this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
        this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
      }
      setInvulTime(i);
      if (this.ticksExisted % 10 == 0) {
        heal(40.0F);
      }
    }
    else
    {
      super.updateAITasks();
      
      List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(2.0D, 2.0D, 2.0D), Predicates.and(IMob.mobSelector, IEntitySelector.NOT_SPECTATING));
      if ((list1 != null) && (!list1.isEmpty())) 
      {
          for (int i111 = 0; i111 < list1.size(); i111++)
          {
              EntityLivingBase entitylivingbase = (EntityLivingBase)list1.get(i111);
              if ((entitylivingbase != this) && entitylivingbase.hurtTime <= 0 && (entitylivingbase.isEntityAlive()) && (canEntityBeSeen(entitylivingbase)) && (!isChild()))
              {
                  if ((entitylivingbase instanceof EntityPlayer))
                  {
                    if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage) 
                    {
                      this.attackEntityAsMob(entitylivingbase);
                      this.setAttackTarget(entitylivingbase);
                    }
                  }
                  else if (!(entitylivingbase instanceof EntityWitherGirl) && !(entitylivingbase instanceof EntityWitherGirlPink) && !(entitylivingbase instanceof EntityWitherGirlVoid))
                  {
                  	this.attackEntityAsMob(entitylivingbase);
                      this.setAttackTarget(entitylivingbase);
                  }
            	}
          }
      }
      
	    if ((this.getAttackTarget() != null) && (!this.getAttackTarget().isEntityAlive()))
	    	this.setAttackTarget(null);
	    
	    if (this.getAttackTarget() != null && (this.getAttackTarget() instanceof EntityWitherGirl || this.getAttackTarget() instanceof EntityWitherGirlPink || this.getAttackTarget() instanceof EntityWitherGirlVoid))
	    	this.setAttackTarget(null);
	    
        if (this.getMate() != null && this.getMate().getAITarget() != null)
        	this.setAttackTarget(this.getMate().getAITarget());
        
        if (this.getMate() != null && this.getMate().getLastAttacker() != null) {
            setAttackTarget(getMate().getLastAttacker());
          }
      
	  if (this.getAttackTarget() != null && this.getDistanceSqToEntity(this.getAttackTarget()) <= (this.height * this.width + 36D) && this.ticksExisted % 10 == 0)
		  this.attackEntityAsMob(this.getAttackTarget());
	  
	  if (this.isInLove())
	  {
		  EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 100D);
		  if (player != null)
		  {
			  if (!this.isRiding() && this.ticksExisted % 10 == 0)
			  {
				  this.getNavigator().tryMoveToEntityLiving(player, 0.8D);
				  this.getLookHelper().setLookPositionWithEntity(player, 10F, 30F);
			  }
			  if (player.getDistanceSqToEntity(this) > 9D)
			  {
			  if (this.isCollidedHorizontally)
			  {
				  this.shouldStartFlying = true;
				  if (this.ticksExisted % 20 == 0)
				  {
			          int i = MathHelper.floor_double(this.posY);
			          int i1 = MathHelper.floor_double(this.posX);
			          int j1 = MathHelper.floor_double(this.posZ);
			          for (int l1 = -2; l1 <= 2; l1++) {
			            for (int i2 = -2; i2 <= 2; i2++) {
			              for (int j = 0; j <= 7; j++)
			              {
			                int j2 = i1 + l1;
			                int k = i + j;
			                int l = j1 + i2;
			                Block block = this.worldObj.getBlockState(new BlockPos(j2, k, l)).getBlock();
			                if ((!block.isAir(this.worldObj, new BlockPos(j2, k, l))) && (canEntityDestroy(block, this.worldObj, new BlockPos(j2, k, l), this))) {
			                  this.worldObj.destroyBlock(new BlockPos(j2, k, l), true);
			                }
			              }
			            }
			          }
				  }
			  }
			  }
			  else
			  {
				  if (this.ridingEntity == null && this.ticksExisted % 10 == 0)
				  this.mountEntity(player);
			  }
		  }
	  }
      
      for (int i = 1; i < 3; i++) {
        if (this.ticksExisted >= this.field_82223_h[(i - 1)])
        {
          this.field_82223_h[(i - 1)] = (this.ticksExisted + 10 + this.rand.nextInt(10));
          if ((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) || (this.worldObj.getDifficulty() == EnumDifficulty.HARD))
          {
            int k2 = i - 1;
            int l2 = this.field_82224_i[(i - 1)];
            this.field_82224_i[k2] = (this.field_82224_i[(i - 1)] + 1);
            if ((l2 > 15) && (!isChild()))
            {
              float f = 16.0F;
              float f1 = 1.0F;
              double d1 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - f, this.posX + f);
              double d2 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - f1, this.posY + f1);
              double d3 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - f, this.posZ + f);
              launchWitherSkullToCoords(i + 1, d1, d2, d3, true);
              this.field_82224_i[(i - 1)] = 0;
            }
          }
          int i1 = getWatchedTargetId(i);
          if (i1 > 0)
          {
            Entity entity = this.worldObj.getEntityByID(i1);
            if ((!isChild()) && (entity != null) && (entity.isEntityAlive()) && (getDistanceSqToEntity(entity) <= 10000.0D) && (canEntityBeSeen(entity)))
            {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.field_82223_h[(i - 1)] = (this.ticksExisted + this.rand.nextInt(20));
              this.field_82224_i[(i - 1)] = 0;
            }
            else
            {
              func_82211_c(i, 0);
            }
          }
          else
          {
            List list11 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(100.0D, 100.0D, 100.0D), Predicates.and(IMob.mobSelector, IEntitySelector.NOT_SPECTATING));
            for (int k1 = 0; (k1 < 2) && (!list11.isEmpty()); k1++)
            {
              EntityLivingBase entitylivingbase = (EntityLivingBase)list11.get(this.rand.nextInt(list11.size()));
              if ((entitylivingbase != this) && (entitylivingbase.isEntityAlive()) && (canEntityBeSeen(entitylivingbase)) && (!isChild()))
              {
                if ((entitylivingbase instanceof EntityPlayer))
                {
                  if (((EntityPlayer)entitylivingbase).capabilities.disableDamage) {
                    break;
                  }
                  func_82211_c(i, entitylivingbase.getEntityId()); break;
                }
                func_82211_c(i, entitylivingbase.getEntityId());
                
                break;
              }
              list11.remove(entitylivingbase);
            }
          }
        }
      }
      if (getAttackTarget() != null) {
        func_82211_c(0, getAttackTarget().getEntityId());
      } else {
        func_82211_c(0, 0);
      }
      if (this.blockBreakCounter > 0)
      {
        this.blockBreakCounter -= 1;
        if ((this.blockBreakCounter == 0) && (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
        {
          int i = MathHelper.floor_double(this.posY);
          int i1 = MathHelper.floor_double(this.posX);
          int j1 = MathHelper.floor_double(this.posZ);
          boolean flag = false;
          for (int l1 = -1 -(int)(this.getGrowthTime() * 0.005F); l1 <= 1 +(int)(this.getGrowthTime() * 0.005F); l1++) {
            for (int i2 = -1 -(int)(this.getGrowthTime() * 0.005F); i2 <= 1 +(int)(this.getGrowthTime() * 0.005F); i2++) {
              for (int j = 0; j <= 2 +(int)(this.getGrowthTime() * 0.05F); j++)
              {
                int j2 = i1 + l1;
                int k = i + j;
                int l = j1 + i2;
                Block block = this.worldObj.getBlockState(new BlockPos(j2, k, l)).getBlock();
                if ((!block.isAir(this.worldObj, new BlockPos(j2, k, l))) && (canEntityDestroy(block, this.worldObj, new BlockPos(j2, k, l), this))) {
                  flag = (this.worldObj.destroyBlock(new BlockPos(j2, k, l), true)) || (flag);
                }
              }
            }
          }
          if (flag) {
            this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1012, new BlockPos(this), 0);
          }
        }
      }
      if (this.ticksExisted % 20 == 0) {
        heal(5.0F);
	    if (this.getMate() != null) {
	        this.targetTasks.addTask(0, this.aiFollowLover);
	      } else {
	        this.targetTasks.removeTask(this.aiFollowLover);
	      }
	    
	    if (this.isChild())
	    {
	        if (this.getFather() != null) {
	            this.targetTasks.addTask(0, this.aiFollowDaddy);
	            this.targetTasks.removeTask(this.aiFollowFather);
	          } else {
	            this.targetTasks.removeTask(this.aiFollowDaddy);
	          }
	    }
	    else
	    {
	        if (this.getFather() != null && this.getMate() == null) {
	            this.targetTasks.addTask(0, this.aiFollowFather);
	            this.targetTasks.removeTask(this.aiFollowDaddy);
	          } else {
	            this.targetTasks.removeTask(this.aiFollowFather);
	          }
	    }
      }
    }
  }
  
  public boolean canEntityDestroy(Block block, IBlockAccess world, BlockPos pos, Entity entity)
  {
    return (block != Blocks.water) && (block != Blocks.lava) && (block != Blocks.bed) && (block != Blocks.barrier) && (block != Blocks.bedrock) && (block != Blocks.end_portal) && (block != Blocks.end_portal_frame) && (block != Blocks.command_block);
  }
  
  public void func_82206_m()
  {
    setInvulTime(220);
    setHealth(getMaxHealth() / 3.0F);
  }
  
  public void setInWeb() {}
  
  public int getTotalArmorValue()
  {
    return 10;
  }
  
  public boolean interact(EntityPlayer player)
  {
    ItemStack itemstack = player.getHeldItem();
    if ((itemstack != null) && itemstack.getItem() == Items.golden_apple && player != this.getFather() && !this.isChild() && !this.isInLove() && this.timeSinceDoingIt <= 0)
    {
        this.playSound("random.eat", 2F, this.worldObj.rand.nextFloat() * 0.1F + 1.2F);
        this.playSound("random.burp", 2F, this.worldObj.rand.nextFloat() * 0.1F + 1.2F);
        this.setInLove(true);
        this.swingItem();
        this.rotationPitch = 75F;
        return true;
    }
    else if (itemstack != null && itemstack.getItem() == Items.spawn_egg)
    {
        if (!this.worldObj.isRemote)
        {
            Class oclass = EntityList.getClassFromID(itemstack.getMetadata());

            if (oclass != null && this.getClass() == oclass)
            {
                EntityWitherGirlVoid entityageable = new EntityWitherGirlVoid(worldObj);

                if (entityageable != null)
                {
                    entityageable.setChild(true);
                    entityageable.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                    this.worldObj.spawnEntityInWorld(entityageable);

                    if (itemstack.hasDisplayName())
                    {
                        entityageable.setCustomNameTag(itemstack.getDisplayName());
                    }
                    
                    if (this.getMate() != null && player == this.getMate())
                    {
                        entityageable.setFatherId(player.getUniqueID().toString());
                    }
                    
                    if (this.getFather() != null && player == this.getFather())
                    {
                        entityageable.setFatherId(player.getUniqueID().toString());
                    }

                    if (!player.capabilities.isCreativeMode)
                    {
                        --itemstack.stackSize;

                        if (itemstack.stackSize <= 0)
                        {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                        }
                    }
                }
            }
        }

        return true;
    }
    else if (itemstack == null && (this.isInLove() || this.isChild()))
    {
    	if (this.ridingEntity == null)
    	{
    		this.mountEntity(player);
    		if (this.getFather() == null)
    		{
    			this.setFatherId(player.getUniqueID().toString());
    	    	for (int j = 0; j < 3; j++)
    	        {
    	            this.playLivingSound();
    	        }
    		}
    	}
    	else if (this.ridingEntity != null)
    		this.ridingEntity = null;
        this.swingItem();
        return true;
    }
    else if (itemstack == null && this.getMate() != null)
    {
    	if (this.ridingEntity == null && this.getMate().riddenByEntity != null)
    		this.getMate().riddenByEntity.mountEntity(this);
    	else if (this.riddenByEntity != null)
    		this.riddenByEntity = null;
        this.swingItem();
        return true;
    }
    else
    {
    	return super.interact(player);
    }
  }
  
  @Override
  public void updateRiderPosition()
  {
	    float f = (this.renderYawOffset + 180 * (2 - 1)) / 180.0F * 3.1415927F;
	    float f1 = MathHelper.cos(f);
	    float f2 = MathHelper.sin(f);
      if (this.riddenByEntity != null)
      {
    	  if (this.riddenByEntity instanceof EntityWitherGirlVoid)
    		  this.riddenByEntity.setPosition(getGrowthTime() > 1 ? this.posX + f1 * (0.65D * (this.getGrowthTime() * 0.01F)) : this.posX + f1 * 0.65D, this.posY + getEyeHeight() * 0.85D, getGrowthTime() > 1 ? this.posZ + f2 * (0.65D * (this.getGrowthTime() * 0.01F)) : this.posZ + f2 * 0.65D);
    	  else
    		  this.riddenByEntity.setPosition(this.posX, this.posY + 1D, this.posZ);
      }
  }
  
  private double func_82214_u(int p_82214_1_)
  {
    if (p_82214_1_ <= 0) {
      return this.posX;
    }
    float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return getGrowthTime() > 1 ? this.posX + f1 * (0.8D * (this.getGrowthTime() * 0.01F)) : this.posX + f1 * 0.8D;
  }
  
  private double func_82208_v(int p_82208_1_)
  {
    return p_82208_1_ <= 0 ? this.posY + getEyeHeight() : this.posY + getEyeHeight() * 0.85D;
  }
  
  private double func_82213_w(int p_82213_1_)
  {
    if (p_82213_1_ <= 0) {
      return this.posZ;
    }
    float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return getGrowthTime() > 1 ? this.posZ + f1 * (0.8D * (this.getGrowthTime() * 0.01F)) : this.posZ + f1 * 0.8D;
  }
  
  private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_)
  {
    float f3 = MathHelper.wrapAngleTo180_float(p_82204_2_ - p_82204_1_);
    if (f3 > p_82204_3_) {
      f3 = p_82204_3_;
    }
    if (f3 < -p_82204_3_) {
      f3 = -p_82204_3_;
    }
    return p_82204_1_ + f3;
  }
  
  private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
  {
	launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.3D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.3D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
  {
	this.playSound("mob.wither.shoot", 1.5F, 0.6F + (this.rand.nextFloat() * 0.15F));
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4 + this.rand.nextGaussian() * 2.0D;
    double d8 = p_82209_6_ - d5;
    EntityVoidSkull entitywitherskull = new EntityVoidSkull(this.worldObj, this, d6, d7, d8);
    if (p_82209_8_) {
      entitywitherskull.setInvulnerable(true);
    }
    entitywitherskull.posY = d4;
    entitywitherskull.posX = d3;
    entitywitherskull.posZ = d5;
    this.worldObj.spawnEntityInWorld(entitywitherskull);
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
  {
    if (this.shouldStartFlying == true) {
      launchWitherSkullToEntity(0, p_82196_1_);
    }
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (isEntityInvulnerable(source)) {
      return false;
    }
    else if ((source != DamageSource.drown) && (!(source.getEntity() instanceof EntityWitherGirl)) && (!(source.getEntity() instanceof EntityWitherGirlVoid)) && (!(source.getEntity() instanceof EntityFriendlyWither)) && (!(source.getEntity() instanceof EntityWitherGirlPink)))
    {
      if ((getInvulTime() > 0) && (source != DamageSource.outOfWorld)) {
        return false;
      }
      if (isArmored())
      {
        Entity entity = source.getSourceOfDamage();
        if ((entity instanceof EntityArrow)) {
          return false;
        }
      }
      Entity entity = source.getEntity();

      if ((entity != null) && ((entity instanceof EntityWither))) {
        return false;
      }
      if (this.blockBreakCounter <= 0) {
        this.blockBreakCounter = 1;
      }
      for (int i = 0; i < this.field_82224_i.length; i++) {
        this.field_82224_i[i] += 3;
      }

      if ((entity != null) && ((entity instanceof EntityPlayer)))
      {
        ItemStack itemstack = ((EntityPlayer)entity).getHeldItem();
        
        Entity entity1 = source.getSourceOfDamage();
        if ((itemstack == null) && this.getFather() == null && this.getMate() == null && (!(entity1 instanceof EntityArrow)) && (!(source instanceof EntityDamageSourceIndirect)) && (!isChild()))
        {
          this.rotationYawHead += 35.0F;
          this.rotationPitch = 10.0F;
        }
        else if ((itemstack != null) && (getRNG().nextInt(10) == 0) && (itemstack.getItem() != Items.stick) && (itemstack.getItem() != Items.blaze_rod) && (!isChild()))
        {
          playSound("mowithers:StopHittingMe", getSoundVolume(), getSoundPitch());
        }
        else if ((itemstack != null) && ((itemstack.getItem() == Items.stick) || (itemstack.getItem() == Items.blaze_rod)) && (getRNG().nextInt(20) == 0) && (!isChild()))
        {
          playSound("mowithers:StopPokingMe", getSoundVolume(), getSoundPitch());
        }
        return false;
      }
      if (super.attackEntityFrom(source, amount))
      {
    	  this.setGrowthTime(1200);
          this.shouldStartFlying = true;
    	  return true;
      }
      else
      {
    	  return false;
      }
    }
    else
    {
     return false;
    }
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn)
  {
    super.onKillEntity(entityLivingIn);
    if ((getAttackTarget() != null) && (entityLivingIn == getAttackTarget()) && (this.shouldStartFlying == true)) {
      this.shouldStartFlying = false;
    }
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    EntityItem entityitem = dropItem(Items.nether_star, 1);
    if (entityitem != null) {
      entityitem.setNoDespawn();
    }
    if (!this.worldObj.isRemote)
    {
      Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)).iterator();
      while (iterator.hasNext())
      {
        EntityPlayer entityplayer = (EntityPlayer)iterator.next();
        entityplayer.triggerAchievement(AchievementList.killWither);
      }
    }
  }
  
  protected void despawnEntity()
  {
    this.entityAge = 0;
  }
  
  public void fall(float distance, float damageMultiplier) {}
  
  public void addPotionEffect(PotionEffect p_70690_1_) {}
  
  @SideOnly(Side.CLIENT)
  public float func_82207_a(int p_82207_1_)
  {
    return this.field_82221_e[p_82207_1_];
  }
  
  @SideOnly(Side.CLIENT)
  public float func_82210_r(int p_82210_1_)
  {
    return this.field_82220_d[p_82210_1_];
  }
  
  public int getGrowthTime()
  {
	  if (this.dataWatcher.getWatchableObjectInt(21) <= 0)
		  return 0;
	  else
		  return this.dataWatcher.getWatchableObjectInt(21);
  }
  
  public void setGrowthTime(int p_82215_1_)
  {
	  if (p_82215_1_ <= 100)
		  this.dataWatcher.updateObject(21, Integer.valueOf(0));
	  else
		  this.dataWatcher.updateObject(21, Integer.valueOf(p_82215_1_));
  }
  
  public int getInvulTime()
  {
    return this.dataWatcher.getWatchableObjectInt(20);
  }
  
  public void setInvulTime(int p_82215_1_)
  {
    this.dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
  }
  
  public int getWatchedTargetId(int p_82203_1_)
  {
    return this.dataWatcher.getWatchableObjectInt(17 + p_82203_1_);
  }
  
  public void func_82211_c(int p_82211_1_, int p_82211_2_)
  {
    this.dataWatcher.updateObject(17 + p_82211_1_, Integer.valueOf(p_82211_2_));
  }
  
  public boolean isArmored()
  {
    return getHealth() <= getMaxHealth() / 2.0F;
  }
  
  protected float getSoundVolume()
  {
    return 3.0F + (this.getGrowthTime() * 0.01F);
  }
  
  protected float getSoundPitch()
  {
    return this.isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.45F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F + 1.05F;
  }
}
