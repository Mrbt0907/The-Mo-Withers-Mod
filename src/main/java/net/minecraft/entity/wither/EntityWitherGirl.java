package net.minecraft.entity.wither;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.mojang.authlib.GameProfile;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.MoWithers.DamageSourceExtra;
import net.minecraft.MoWithers.MoWithersAchievments;
import net.minecraft.MoWithers.questline.Quest;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.INpc;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
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
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.witherskulls.EntityWitherSkullPiercing;
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
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherGirl
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
  Village villageObj;
  private int homeCheckTimer;
  private int slapCount;
  private int blinkTimer;
  public int hasBeenRecentlySlappedTimer;
  public boolean hasBeenRecentlySlapped;
  public boolean isMad;
  public boolean isDoingIt;
  public boolean isPregnant;
  public int timeSinceDoingIt;
  public int bangTimer;
  private EntityAINearestWitherAttackTarget aiAttackPlayers = new EntityAINearestWitherAttackTarget(this, EntityPlayer.class, false);
  public boolean isBeingOffered;
  private int musicTimer;
  private int numbOfOthers;
  private int questId;
  private Quest quest;
  private EntityAIWitherGirlFollowPlayer aiFollowLover = new EntityAIWitherGirlFollowPlayer(this, 0.6D, 12.0F, 4.0F);
  private EntityAIWitherGirlFollowFather aiFollowFather = new EntityAIWitherGirlFollowFather(this, 0.6D, 12.0F, 4.0F);
  private EntityAIWitherGirlFollowFather aiFollowDaddy = new EntityAIWitherGirlFollowFather(this, 1.0D, 8.0F, 3.0F);
  
  
  public EntityWitherGirl(World worldIn)
  {
    super(worldIn);
    this.quest = new Quest(this, getQuestName(), getQuestId());
    setSize(0.5F, 1.975F);
    setHealth(getMaxHealth());
    this.isImmuneToFire = true;
    this.ignoreFrustumCheck = true;
    ((PathNavigateGround)getNavigator()).func_179693_d(true);
    this.tasks.addTask(0, new EntityAIAvoidEntity(this, new Predicate()
    {
      public boolean func_179958_a(Entity p_179958_1_)
      {
        return EntityWitherGirl.this.isChild() && ((p_179958_1_ instanceof IMob));
      }
      
      public boolean apply(Object p_apply_1_)
      {
        return func_179958_a((Entity)p_apply_1_);
      }
    }, 8.0F, 1.0D, 1.0D));
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIOpenDoor(this, true));
    this.tasks.addTask(1, new EntityAIBreakDoor(this));
    this.tasks.addTask(1, new EntityAITempt(this, 1.0D, Items.golden_apple, false));
    this.tasks.addTask(2, new EntityAIPanic(this, 1.0D));
    this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 0.8D, true));
    this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
    this.tasks.addTask(5, new EntityAIWatchClosest2(this, EntityIronGolem.class, 4.0F, 0.1F));
    this.tasks.addTask(5, new EntityAIWatchClosest2(this, EntityVillager.class, 4.0F, 0.1F));
    this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 4.0F, 0.1F));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 0, false, false, IMob.mobSelector));
    this.experienceValue = 5000;
  }
  
  public boolean canAttackClass(Class p_70686_1_)
  {
      return p_70686_1_ != EntityWitherGirl.class && p_70686_1_ != EntityWitherGirlPink.class && p_70686_1_ != EntityWitherGirlVoid.class;
  }
  
  public float getRenderSizeModifier()
  {
    return this.isSneaking() ? 1.75F : 1.0F;
  }
  
  protected PathNavigate func_175447_b(World worldIn)
  {
	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
      return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? new PathNavigateSwimmer(this, worldIn) : new PathNavigateGround(this, worldIn);
  }
  
  public int getQuestId()
  {
    return this.questId;
  }
  
  public String getQuestName()
  {
    return "Samatha's Quest Line";
  }
  
  protected void collideWithEntity(Entity p_82167_1_)
  {
    if (((p_82167_1_ instanceof EntityPlayer)) && !this.isInLove() && (getRNG().nextInt(20) == 0) && (p_82167_1_.motionX != 0.0D) && (p_82167_1_.motionZ != 0.0D) && (!isChild())) {
      playSound("mowithers:StopWalkingIntoMe", getSoundVolume(), getSoundPitch());
    }
    
    super.collideWithEntity(p_82167_1_);
  }
  
  public float getEyeHeight()
  {
	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
	  if (this.isChild())
	  {
		  return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? 0.2F : (this.isRiding() ? 0.1F : 0.65F);
	  }
	  else
	  {
		  return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? 0.25F : (this.isSneaking() ? 0.925F : 1.785F);
	  }
  }
  
  public int getTalkInterval()
  {
    if (isChild()) {
      return 20;
    }
    if (this.hasBeenRecentlySlapped) {
      return 600;
    }
    return 100;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(3000.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.55D);
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0D);
    getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
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
    tagCompound.setInteger("Invul", getInvulTime());
    tagCompound.setBoolean("Samantha", isSamantha());
    tagCompound.setBoolean("IsBaby", isChild());
    tagCompound.setInteger("Slaps", this.slapCount);
    tagCompound.setBoolean("LastedSlapped", this.hasBeenRecentlySlapped);
    tagCompound.setInteger("LastedSlappedTime", this.hasBeenRecentlySlappedTimer);
    tagCompound.setBoolean("Angry", this.isMad);
    tagCompound.setBoolean("Interrested", this.isBeingOffered);
    tagCompound.setBoolean("InLove", isInLove());
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
    setInvulTime(tagCompund.getInteger("Invul"));
    setChild(tagCompund.getBoolean("IsBaby"));
    setSamantha(tagCompund.getBoolean("Samantha"));
    this.hasBeenRecentlySlapped = tagCompund.getBoolean("LastedSlapped");
    this.isMad = tagCompund.getBoolean("Angry");
    setInLove(tagCompund.getBoolean("InLove"));
    this.isBeingOffered = tagCompund.getBoolean("Interrested");
    if (tagCompund.hasKey("TimeBetweenBangs", 99)) {
        this.timeSinceDoingIt = tagCompund.getInteger("TimeBetweenBangs");
      }
    if (tagCompund.hasKey("Slaps", 99)) {
      this.slapCount = tagCompund.getInteger("Slaps");
    }
    if (tagCompund.hasKey("LastedSlappedTime", 99)) {
      this.hasBeenRecentlySlappedTimer = tagCompund.getInteger("LastedSlappedTime");
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
  
  public boolean isSamantha()
  {
    return getDataWatcher().getWatchableObjectByte(12) == 1;
  }
  
  public void setSamantha(boolean childZombie)
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
    return this.isPregnant ? null : "mowithers:WitherGirlIdle";
  }
  
  protected String getHurtSound()
  {
    return "mowithers:WitherGirlHurt";
  }
  
  protected String getDeathSound()
  {
    return "mowithers:WitherGirlDeath";
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
		    if (!this.worldObj.isRemote && this.getFather() != null)
		    {
		      List list1 = this.worldObj.getEntitiesWithinAABB(EntityWitherGirlPink.class, getEntityBoundingBox().expand(16.0D, 16.0D, 16.0D));
		      List list2 = this.worldObj.getEntitiesWithinAABB(EntityWitherGirlVoid.class, getEntityBoundingBox().expand(16.0D, 16.0D, 16.0D));

		      if (list1 != null && list2 != null && !list1.isEmpty() && !list2.isEmpty())
		      {
		    	  	for (int l = 0; l < list1.size(); ++l)
	                {
		    	  		for (int l1 = 0; l1 < list2.size(); ++l1)
		                {
		  		    	  EntityWitherGirlPink entity = (EntityWitherGirlPink)list1.get(l);
				    	  EntityWitherGirlVoid entity2 = (EntityWitherGirlVoid)list2.get(l1);
				    	  if (entity != null && entity2 != null && entity.isChild() && entity2.isChild() && entity.getFather() == this.getFather() && entity2.getFather() == this.getFather())
				    		  this.getFather().triggerAchievement(MoWithersAchievments.achievementAllstarfather);
		                }
	                }
		      }
		    }
	  }
	  
	  if (this.rotationYaw < (this.rotationYawHead - 30))
		  this.rotationYaw = (this.rotationYawHead - 30);
	  
	  if (this.rotationYaw > (this.rotationYawHead + 30))
		  this.rotationYaw = (this.rotationYawHead + 30);
	  
	  if (this.isInLove() && this.timeSinceDoingIt < 4000)
	  {
		  EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 100D);
		  if (player != null)
		  {
			    if (!this.isChild() && player.getDistanceSqToEntity(this) <= 9D && player.getSleepTimer() > 0 && (!this.worldObj.isRemote) && !this.worldObj.isDaytime() && !this.worldObj.canSeeSky(new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ)))
				{
                    this.bangTimer = player.getSleepTimer();
                    this.isDoingIt = true;
                    this.mountEntity(player);
                    this.limbSwingAmount = 1F;
                    if (this.ticksExisted % 20 == 0)
                    {
                        this.playSound("mowithers:OHGOD", 1F, 0.9F + (this.bangTimer * 0.005F));
                    }
				}
		  }
	  }
	  
	  if (this.timeSinceDoingIt < 4000 && !this.worldObj.isRemote)
	  {
		  this.isPregnant = false;
		  this.setSneaking(false);
	  }
	  
	  if (this.timeSinceDoingIt > 4000 && !(this.timeSinceDoingIt < 4000) && !this.worldObj.isRemote)
	  {
		  this.setInLove(true);
		  this.isPregnant = true;
		  this.setSneaking(true);
		  this.getNavigator().clearPathEntity();
		  if (this.ticksExisted % 62 == 0)
			  this.playSound("mowithers:JestBreathe", 0.1F, 1.1F);
	  }
	  if (this.timeSinceDoingIt == 4000 && !this.worldObj.isRemote)
	  {
		  this.setInLove(false);
		  this.isPregnant = false;
		  this.setSneaking(false);
		  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1.1F);
		  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1.1F);
		  this.playSound("game.player.hurt", 2F, 0.6F);
		  this.playSound("game.player.hurt", 2F, 0.6F);
		  this.playSound("game.player.hurt", 2F, 0.6F);
		  this.playSound("game.player.hurt", 2F, 0.6F);
		  EntityWitherGirl entityocelot = new EntityWitherGirl(this.worldObj);
		  this.worldObj.createExplosion(this, this.posX, this.posY + 0.6D, this.posZ, 0F, false);
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

	  if (this.bangTimer >= 99)
	  {
		  if (this.ridingEntity instanceof EntityPlayer)
		  {
			  	((EntityPlayer)this.ridingEntity).addExhaustion(2F);
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
		  }
		  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1F);
		  this.bangTimer = 0;
		  if (!this.worldObj.isRemote)
		  {
			  this.setInLove(false);
			  this.isDoingIt = false;
			  if (this.rand.nextInt(10) == 0)
			  {
				  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1F);
				  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1F);
				  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1F);
				  this.timeSinceDoingIt = 72000;
		            int i = 150000;

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
		            int i = 5000;

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
	  }
	  --this.timeSinceDoingIt;
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
			  this.renderYawOffset = this.rotationYaw = this.rotationYawHead = ((EntityLivingBase)this.ridingEntity).rotationYaw;
			  
			  if (this.ridingEntity instanceof EntityPlayer && this.isChild())
				  this.renderYawOffset = this.rotationYaw = this.rotationYawHead = ((EntityPlayer)this.ridingEntity).rotationYawHead;
			  else
				  this.renderYawOffset = this.rotationYaw = ((EntityLivingBase)this.ridingEntity).rotationYaw;
		  }
	  }
	  
	  ++this.blinkTimer;
      byte b1 = this.dataWatcher.getWatchableObjectByte(16);
      byte b0 = (byte)(this.blinkTimer > 0 ? 1 : 0);

      if (b1 != b0)
      {
          this.dataWatcher.updateObject(16, Byte.valueOf(b0));
      }
      
      if (this.blinkTimer == 3)
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
	  if (this.getAttackTarget() != null && this.isMad && this.ticksExisted % 60 == 0 && !this.worldObj.isRemote)
	  {
		  this.attackEntityWithRangedAttack(this.getAttackTarget(), 0F);
	  }
	  if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
	  {
		  if (this.getAttackTarget() != null)
			  this.getMoveHelper().setMoveTo(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ, 0.8D);
		    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D && !this.isRiding()) {
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
	  
    if (isChild()) {
    	if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
    		setSize(0.45F, 0.45F);
    	else
    		setSize(0.45F, 0.9F);
    } else {
    	if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
    		setSize(0.5F, 0.5F);
    	else if (this.isSneaking())
    		setSize(0.5F, 1.35F);
    	else
    		setSize(0.5F, 1.95F);
    }
    if ((this.musicTimer <= 0) && (isSamantha()))
    {
      this.musicTimer = 4000;
      playSound("mowithers:SamanthasTheme", 10000.0F, 1.0F);
    }
    this.musicTimer -= 1;
    
    if (this.hasBeenRecentlySlappedTimer >= 0) {
      this.hasBeenRecentlySlappedTimer -= 1;
    }
    if (this.hasBeenRecentlySlappedTimer < 0) {
      this.hasBeenRecentlySlapped = false;
    }
    if ((getVillage() != null) && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && this.getAttackTarget() != null && (this.rand.nextInt(400) == 0))
    {
      playSound("mowithers:DefendVillage", getSoundVolume(), getSoundPitch());
      this.rotationPitch -= 20.0F;
    }
    if (isSamantha())
    {
    	setCustomNameTag("\u00A75Samantha");
    	setCanPickUpLoot(true);
    }
    if ((!this.onGround) && (this.motionY < 0.0D) && !this.isPregnant) {
      this.motionY *= 0.5D;
    }
    List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8.0D, 8.0D, 8.0D));
    if ((list111 != null) && (!list111.isEmpty())) {
      for (int i111 = 0; i111 < list111.size(); i111++)
      {
        Entity entity = (Entity)list111.get(i111);
        if ((entity != null) && ((entity instanceof EntityPlayer)) && (((EntityPlayer)entity).getHeldItem() != null) && this.timeSinceDoingIt <= 0 && !this.isInLove() && (((EntityPlayer)entity).getHeldItem().getItem() == Items.golden_apple)) {
          this.isBeingOffered = true;
        } else {
          this.isBeingOffered = false;
        }
      }
    }
    if (this.rand.nextInt(120) == 0) {
      this.shouldStartFlying = false;
    }
    if ((!this.worldObj.isRemote) && (this.shouldStartFlying == true) && (getWatchedTargetId(0) > 0))
    {
      Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
      if ((entity != null) && (canEntityBeSeen(entity)))
      {
        if ((this.posY < entity.posY) || ((!isArmored()) && (this.posY < entity.posY + 7.0D)))
        {
          if (this.motionY < 0.0D) {
            this.motionY = 0.0D;
          }
          this.motionY += (0.5D - this.motionY) * 0.5D;
        }
        else
        {
          this.shouldStartFlying = true;
          setAttackTarget(null);
        }
        double d0 = entity.posX - this.posX;
        double d1 = entity.posZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1;
        if (d3 > 9.0D)
        {
          double d5 = MathHelper.sqrt_double(d3);
          this.motionX += (d0 / d5 * 0.8D - this.motionX) * 0.8D;
          this.motionZ += (d1 / d5 * 0.8D - this.motionZ) * 0.8D;
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
    if (this.isMad) {
      for (int j = 0; j < 15; j++)
      {
        double d10 = func_82214_u(j);
        double d2 = func_82208_v(j);
        double d4 = func_82213_w(j);
        this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    if (getInvulTime() > 0) {
      for (int j = 0; j < 3; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
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
              this.motionX *= 0.875D;
              this.motionY *= 0.875D;
              this.motionZ *= 0.875D;
              float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.25D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.25D) * 0.25F;

              if (f > 1.0F)
              {
                  f = 1.0F;
              }

              if (this.rand.nextInt(2) == 0)
              this.playSound(this.getSwimSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.6F);
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
    boolean flag = p_70652_1_.attackEntityFrom(DamageSourceExtra.causeWitherGirlDamage(this), (int)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
    
    swingItem();
    if (flag) {
      func_174815_a(this, p_70652_1_);
    }
    return flag;
  }
  
  protected void updateAITasks()
  {
    if (--this.homeCheckTimer <= 0)
    {
      this.homeCheckTimer = (70 + this.rand.nextInt(50));
      this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this), 32);
      if (this.villageObj == null)
      {
        detachHome();
      }
      else
      {
        BlockPos blockpos = this.villageObj.getCenter();
        func_175449_a(blockpos, (int)(this.villageObj.getVillageRadius() * 0.6F));
      }
    }
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
      
	  if (this.getAttackTarget() != null && this.getDistanceSqToEntity(this.getAttackTarget()) <= 9D && this.ticksExisted % 10 == 0)
		  this.attackEntityAsMob(this.getAttackTarget());
      
      List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(24.0D, 24.0D, 24.0D));
      if ((list111 != null) && (!list111.isEmpty())) {
        for (int i1 = 0; i1 < list111.size(); i1++)
        {
          Entity entity = (Entity)list111.get(i1);
          if ((entity != null) && (getAttackTarget() != null) && ((entity instanceof EntityIronGolem))) {
            if ((entity instanceof EntityPlayer))
            {
              if (!((EntityPlayer)entity).capabilities.disableDamage) {
                ((EntityIronGolem)entity).setAttackTarget(getAttackTarget());
              }
            }
            else {
              ((EntityIronGolem)entity).setAttackTarget(getAttackTarget());
            }
          }
        }
      }
      List list = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, getEntityBoundingBox().expand(100.0D, 100.0D, 100.0D), Predicates.and(IMob.mobSelector, IEntitySelector.NOT_SPECTATING));
      for (int k1 = 0; (k1 < 24) && (!list.isEmpty()); k1++)
      {
        EntityLiving entitylivingbase = (EntityLiving)list.get(this.rand.nextInt(list.size()));
        
        double d0 = entitylivingbase.getEntityAttribute(SharedMonsterAttributes.followRange).getBaseValue();
        if ((!this.isMad) && (entitylivingbase != this) && (entitylivingbase.isEntityAlive()) && (canEntityBeSeen(entitylivingbase)) && (entitylivingbase.getDistanceSqToEntity(this) <= d0 * d0))
        {
          entitylivingbase.setAttackTarget(this);
          if ((entitylivingbase instanceof EntitySlime))
          {
            int inte = ((EntitySlime)entitylivingbase).getSlimeSize();
            if ((entitylivingbase.canEntityBeSeen(this)) && (entitylivingbase.getDistanceSqToEntity(this) < 0.6D * inte * 0.6D * inte))
            {
              int i2 = inte;
              if ((entitylivingbase instanceof EntityMagmaCube)) {
                i2 = inte + 2;
              }
              attackEntityFrom(DamageSource.causeMobDamage(entitylivingbase), i2);
            }
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
            if (l2 > 15)
            {
              float f = 16.0F;
              float f1 = 1.0F;
              double d1 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - f, this.posX + f);
              double d2 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - f1, this.posY + f1);
              double d3 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - f, this.posZ + f);
              this.field_82224_i[(i - 1)] = 0;
            }
          }
          int i1 = getWatchedTargetId(i);
          if (i1 > 0)
          {
            Entity entity = this.worldObj.getEntityByID(i1);
            if ((!isChild()) && (entity != null) && (entity.isEntityAlive()) && (getDistanceSqToEntity(entity) <= 4096.0D) && (canEntityBeSeen(entity)))
            {
            	this.faceEntity(entity, 180F, 30F);
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.field_82223_h[(i - 1)] = (this.ticksExisted + 20);
              this.field_82224_i[(i - 1)] = 0;
            }
            else
            {
              func_82211_c(i, 0);
            }
          }
          else
          {
            List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(24.0D, 12.0D, 24.0D), Predicates.and(IMob.mobSelector, IEntitySelector.NOT_SPECTATING));
            for (int k1 = 0; (k1 < 24) && (!list1.isEmpty()); k1++)
            {
              EntityLivingBase entitylivingbase = (EntityLivingBase)list1.get(this.rand.nextInt(list1.size()));
              if ((entitylivingbase != this) && (entitylivingbase.isEntityAlive()) && (canEntityBeSeen(entitylivingbase)))
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
              list1.remove(entitylivingbase);
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
          for (int l1 = -1; l1 <= 1; l1++) {
            for (int i2 = -1; i2 <= 1; i2++) {
              for (int j = 0; j <= 3; j++)
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
      if (this.ticksExisted % 20 == 0)
      {
        heal(1.0F);
        if (this.isMad) {
            this.targetTasks.addTask(1, this.aiAttackPlayers);
          } else {
            this.targetTasks.removeTask(this.aiAttackPlayers);
          }
          
          if (this.getMate() != null) {
              this.targetTasks.addTask(1, this.aiFollowLover);
            } else {
              this.targetTasks.removeTask(this.aiFollowLover);
            }
          
          if (this.isChild())
          {
              if (this.getFather() != null) {
                  this.targetTasks.addTask(1, this.aiFollowDaddy);
                  this.targetTasks.removeTask(this.aiFollowFather);
                } else {
                  this.targetTasks.removeTask(this.aiFollowDaddy);
                }
          }
          else
          {
              if (this.getFather() != null && this.getMate() == null) {
                  this.targetTasks.addTask(1, this.aiFollowFather);
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
  
  public double getYOffset()
  {
      return this.isChild() ? super.getYOffset() + 0.25D : super.getYOffset() - 0.8D;
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
                EntityWitherGirl entityageable = new EntityWitherGirl(worldObj);

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
  
  private double func_82214_u(int p_82214_1_)
  {
    if (p_82214_1_ <= 0) {
      return this.posX;
    }
    float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 0.8D;
  }
  
  private double func_82208_v(int p_82208_1_)
  {
    return p_82208_1_ <= 0 ? this.posY + getEyeHeight() : this.posY + getEyeHeight() - 0.3D;
  }
  
  private double func_82213_w(int p_82213_1_)
  {
    if (p_82213_1_ <= 0) {
      return this.posZ;
    }
    float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 0.8D;
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
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.6D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
  {
    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, new BlockPos(this), 0);
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityWitherSkullPiercing entitywitherskull = new EntityWitherSkullPiercing(this.worldObj, this, d6, d7, d8);
    if (p_82209_8_) {
      entitywitherskull.setInvulnerable(true);
    }
    entitywitherskull.posY = d4;
    entitywitherskull.posX = d3;
    entitywitherskull.posZ = d5;
    if (!isChild()) {
      this.worldObj.spawnEntityInWorld(entitywitherskull);
    }
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
  {
	  launchWitherSkullToEntity(0, p_82196_1_);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (isEntityInvulnerable(source)) {
      return false;
    }
    if ((source != DamageSource.drown) && (!(source.getEntity() instanceof EntityWitherGirl)) && (!(source.getEntity() instanceof EntityWitherGirlVoid)) && (!(source.getEntity() instanceof EntityFriendlyWither)) && (!(source.getEntity() instanceof EntityWitherGirlPink)))
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
      if ((entity != null) && ((entity instanceof EntityPlayer)))
      {
        ItemStack itemstack = ((EntityPlayer)entity).getHeldItem();
        
        Entity entity1 = source.getSourceOfDamage();
        if ((itemstack == null) && this.getFather() == null && this.getMate() == null && (!(entity1 instanceof EntityArrow)) && (!(source instanceof EntityDamageSourceIndirect)) && (!isChild()))
        {
          playSound("mowithers:BitchSlap", getSoundVolume(), getSoundPitch());
          this.rotationYawHead += 35.0F;
          this.rotationPitch = 10.0F;
          this.slapCount += 1;
          this.hasBeenRecentlySlapped = true;
          this.hasBeenRecentlySlappedTimer = 200;
          if (this.slapCount >= 40)
          {
            setAttackTarget((EntityPlayer)entity);
            this.isMad = true;
            func_82211_c(1, ((EntityPlayer)entity).getEntityId());
            func_82211_c(2, ((EntityPlayer)entity).getEntityId());
          }
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
      if (this.blockBreakCounter <= 0) {
        this.blockBreakCounter = 5;
      }
      for (int i = 0; i < this.field_82224_i.length; i++) {
        this.field_82224_i[i] += 3;
      }
      this.shouldStartFlying = true;
      
      return super.attackEntityFrom(source, amount);
    }
    return false;
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
  
  public boolean isPotionApplicable(PotionEffect p_70687_1_)
  {
      int i = p_70687_1_.getPotionID();

      if (i == 2 || i == 4 || i == 9 || i == 15 || i == 17 || i == 18 || i == 19 || i == 20)
      {
          return false;
      }

      return true;
  }
  
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
    return 3.0F;
  }
  
  protected float getSoundPitch()
  {
    return isChild() ? (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F : (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.05F;
  }
}
