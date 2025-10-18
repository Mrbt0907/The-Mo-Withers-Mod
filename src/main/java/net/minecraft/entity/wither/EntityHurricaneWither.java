package net.minecraft.entity.wither;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.MoWithers.MoWithersAchievments;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.INpc;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.witherskulls.EntityHurricaneSkull;
import net.minecraft.entity.witherskulls.EntityLoveSkull;
import net.minecraft.entity.witherskulls.EntityMettalicSkull;
import net.minecraft.entity.witherskulls.EntitySpookySkull;
import net.minecraft.entity.witherskulls.EntityWitherSkullFriendly;
import net.minecraft.entity.witherskulls.EntityWitherSkullPiercing;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHurricaneWither
  extends EntityMob
  implements IBossDisplayData, IRangedAttackMob
{
  private float[] field_82220_d = new float[2];
  private float[] field_82221_e = new float[2];
  private float[] field_82217_f = new float[2];
  private float[] field_82218_g = new float[2];
  private int[] field_82223_h = new int[2];
  private int[] field_82224_i = new int[2];
  private int blockBreakCounter;
  private static final Predicate attackEntitySelector = new Predicate()
  {
    public boolean func_180027_a(Entity p_180027_1_)
    {
      return (((p_180027_1_ instanceof EntityLivingBase)) && (((EntityLivingBase)p_180027_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD));
    }
    
    public boolean apply(Object p_apply_1_)
    {
      return func_180027_a((Entity)p_apply_1_);
    }
  };
  
  public EntityHurricaneWither(World worldIn)
  {
    super(worldIn);
    setHealth(getMaxHealth());
    setSize(0.9F, 3.5F);
    this.playSound("mowithers:Wind", 6F, 1F);
    this.isImmuneToFire = true;
    ((PathNavigateGround)getNavigator()).func_179693_d(true);
    this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
    this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
    this.experienceValue = 750;
  }
  
  protected void entityInit()
  {
    super.entityInit();
    this.dataWatcher.addObject(17, new Integer(0));
    this.dataWatcher.addObject(18, new Integer(0));
    this.dataWatcher.addObject(19, new Integer(0));
    this.dataWatcher.addObject(20, new Integer(0));
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Invul", getInvulTime());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setInvulTime(tagCompund.getInteger("Invul"));
  }
  
  protected String getLivingSound()
  {
    return "mowithers:AirWitherIdle";
  }
  
  protected String getHurtSound()
  {
    return "mowithers:AirWitherHurt";
  }
  
  protected String getDeathSound()
  {
    return "mowithers:AirWitherDeath";
  }
  
  protected float getSoundPitch()
  {
	  return super.getSoundPitch() - 0.1F;
  }
  
  public void onLivingUpdate()
  {
	  if (this.ticksExisted % 150 == 0)
	  {
		  this.playSound("mowithers:Tornado", 10F, 1F);
		  this.playSound("mowithers:Tornado", 10F, 1F);
		  this.playSound("mowithers:Wind", 10F, 1F);
		  this.playSound("mowithers:Wind", 10F, 1F);
		  this.playSound("mowithers:Wind", 10F, 1F);
		  this.playSound("mowithers:Wind", 10F, 1F);
		  this.playSound("mowithers:Wind", 10F, 1F);
		  this.playSound("mowithers:Wind", 10F, 1F);
	  }

    if ((!this.onGround) && (this.motionY < 0.0D)) {
      this.motionY *= 0.5D;
    }
    if ((!this.worldObj.isRemote) && (getWatchedTargetId(0) > 0) && this.getHealth() > 0F)
    {
      Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
      if (entity != null)
      {
    	  for (int i = 0; i < 8; i++)
          {
    		  int in = MathHelper.floor_double(this.posX - 1.5D + this.rand.nextDouble() * 3.0D);
    		  int j = MathHelper.floor_double(this.posY + 3.0D - this.rand.nextDouble() * (this.isArmored() ? 4D : 14D));
    		  int k = MathHelper.floor_double(this.posZ - 1.5D + this.rand.nextDouble() * 3.0D);
    		  BlockPos blockpos = new BlockPos(in, j, k);
    		  IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
    		  Block block = iblockstate.getBlock();
    
   	         if ((!this.worldObj.isRemote) && (block != Blocks.air))
   	         {
   	          if (this.motionY < 0.0D) {
   	            this.motionY = 0.0D;
   	          }
   	          this.motionY += (0.5D - this.motionY);
            }
          }
    	  this.faceEntity(entity, 180F, 180F);
        double d1 = entity.posX - this.posX;
        double d2 = entity.posZ - this.posZ;
        double d3 = d1 * d1 + d2 * d2;
        if (d3 > 1024.0D)
        {
          double d5 = MathHelper.sqrt_double(d3);
          this.motionX += (d1 / d5 * 0.5D - this.motionX);
          this.motionZ += (d2 / d5 * 0.5D - this.motionZ);
        }
      }
    }
    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D) {
        for (int i = 0; i < 2; ++i)
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D), this.posY + this.getEyeHeight() + (this.rand.nextDouble() - 0.5D), this.posZ + (this.rand.nextDouble() - 0.5D), MathHelper.sin(this.rotationYawHead * 3.1415927F / 180.0F), -this.motionY * 3.1415927F / 180.0F, -MathHelper.cos(this.rotationYawHead * 3.1415927F / 180.0F), new int[0]);
        for (int i = 0; i < 4; ++i)
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D), this.posY + 2 + (this.rand.nextDouble() * 4D - 2D), this.posZ + (this.rand.nextDouble() - 0.5D), MathHelper.sin(this.renderYawOffset * 3.1415927F / 180.0F), -this.motionY * 3.1415927F / 180.0F, -MathHelper.cos(this.renderYawOffset * 3.1415927F / 180.0F), new int[0]);
      this.renderYawOffset = (this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F);
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
    boolean flag = isArmored();
    for (int j = 0; j < 3; j++)
    {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.3D, 0.0D, 0.0D, 0.0D, new int[0]);
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d10 + this.rand.nextGaussian() * 0.1D / 2.0D, d2 + this.rand.nextGaussian() * 0.1D, d4 + this.rand.nextGaussian() * 0.1D / 2.0D, 0.0D, 0.0D, 0.0D, new int[0]);
      if (this.worldObj.rand.nextInt(10) == 0) {
        this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
      if ((flag) && (this.worldObj.rand.nextInt(4) == 0)) {
        this.worldObj.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    if (getInvulTime() > 0) {
      for (int j = 0; j < 15; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    for (int k = 0; k < 16; k++)
    {
    	this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + (this.rand.nextGaussian() * 4D - 2D), this.posY + 7D + this.rand.nextFloat(), this.posZ + (this.rand.nextGaussian() * 4D - 2D), 0.0D, 0.0D, 0.0D, new int[0]);
        for (int j = 2; j < (this.isArmored() ? 50 : 100); j++) {
            float f6 = this.ticksExisted * (float)Math.PI * (this.isArmored() ? 0.015F : 0.005F) + j;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextGaussian() * 2D - 1D) + (MathHelper.cos(f6) * j), this.posY + 6D + this.rand.nextFloat() * 2F - 1F, this.posZ + (this.rand.nextGaussian() * 2D - 1D) + (MathHelper.sin(f6) * j), 0.0D, 0.25D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextGaussian() * 2D - 1D) + (MathHelper.cos(f6) * j), this.posY + 6D + this.rand.nextFloat() * 2F - 1F, this.posZ + (this.rand.nextGaussian() * 2D - 1D) + (MathHelper.sin(f6) * j), 0.0D, 0.25D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextGaussian() * 2D - 1D) - (MathHelper.cos(f6) * j), this.posY + 6D + this.rand.nextFloat() * 2F - 1F, this.posZ + (this.rand.nextGaussian() * 2D - 1D) - (MathHelper.sin(f6) * j), 0.0D, 0.25D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextGaussian() * 2D - 1D) - (MathHelper.cos(f6) * j), this.posY + 6D + this.rand.nextFloat() * 2F - 1F, this.posZ + (this.rand.nextGaussian() * 2D - 1D) - (MathHelper.sin(f6) * j), 0.0D, 0.25D, 0.0D, new int[0]);
        }
    }
  }
  
  protected void updateAITasks()
  {
    if (getInvulTime() > 0)
    {
      int i = getInvulTime() - 1;
      if (i <= 0) {
          this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
          this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
          
          List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8.0D, 8.0D, 8.0D));
          if ((list111 != null) && (!list111.isEmpty())) {
            for (int i111 = 0; i111 < list111.size(); i111++)
            {
              Entity entity = (Entity)list111.get(i111);
              if ((entity != null) && ((entity instanceof EntityLivingBase)))
              {
                entity.motionY = 1.0D;
                entity.attackEntityFrom(DamageSource.inWall, 16.0F);
              }
            }
          }
      }
      setInvulTime(i);
      if (this.ticksExisted % 10 == 0) {
        heal(50.0F);
      }
    }
    else
    {
      super.updateAITasks();
      
      if (!this.worldObj.isRemote)
      {
          WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
          WorldInfo worldinfo = worldserver.getWorldInfo();
          worldinfo.setRainTime(100);
          worldinfo.setRaining(true);
    	  
    	  if (this.isArmored())
    	  {
              worldinfo.setThunderTime(100);
              worldinfo.setThundering(true);
              if (this.rand.nextInt(3) == 0)
              {
                  List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(128.0D, 256.0D, 128.0D));
                  if ((list != null) && (!list.isEmpty())) {
                    for (int i = 0; i < list.size(); i++)
                    {
                      Entity entity = (Entity)list.get(i);
                      if (entity != null && entity instanceof EntityLivingBase && !(entity instanceof INpc) && this.rand.nextInt(10) == 0 && this.worldObj.canSeeSky(new BlockPos(entity))) 
                      {
                      	if (entity instanceof EntityLightningWither)
                      	{
                      		entity.hurtResistantTime = 100;
                      		((EntityLightningWither)entity).heal(5F);
                      	}
                      	entity.motionX = 0F;
                      	entity.motionY = 0F;
                      	entity.motionZ = 0F;
                      	this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, entity.posX - 0.5D, entity.posY + 0.5D, entity.posZ - 0.5D));
                      	if (entity instanceof IBossDisplayData)
                      		entity.hurtResistantTime = 0;
                      	if (entity instanceof EntityDragon)
                      		this.worldObj.newExplosion(null, entity.posX, entity.posY + 1.5D, entity.posZ, 1.75F, false, false);
                      }
                    }
                  }
              }
              else
              {
                  int i = MathHelper.floor_double(this.posX);
                  int j = MathHelper.floor_double(this.posY);
                  int k = MathHelper.floor_double(this.posZ);
                  int i1 = i + (rand.nextInt() * 128 - 64);
                  int j1 = j + (rand.nextInt() * 256 - 128);
                  int k1 = k + (rand.nextInt() * 128 - 64);
                  if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(i1, j1, k1)))
                  	this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, i1 - 0.5D, j1, k1 - 0.5D));
              }
    	  }
      }
      for (int j = 2; j < 12; j++)
      {
      List list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(80.0D, 80.0D, 80.0D));
      if (list1 != null && !list1.isEmpty()) {
        for (int i2 = 0; i2 < list1.size(); i2++)
        {
        	float f6 = this.ticksExisted * (float)Math.PI * (this.isArmored() ? 0.045F : 0.015F) + i2;
          Entity entity = (Entity)list1.get(i2);
          if ((entity instanceof EntityDragonPart) && this.getDistanceSqToEntity(entity) <= 144D)
        	  entity.attackEntityFrom(DamageSource.setExplosionSource(null), 1.0F);
          if ((entity != null) && (entity instanceof EntityItem || (entity instanceof EntityFireball && !(entity instanceof EntityWitherSkullPiercing) && !(entity instanceof EntityWitherSkullFriendly) && !(entity instanceof EntityHurricaneSkull)) || entity instanceof EntityThrowable || entity instanceof EntityFishHook || entity instanceof EntityArrow || entity instanceof EntityXPOrb || entity instanceof EntityTNTPrimed || entity instanceof EntityMinecart || entity instanceof EntityBoat)) 
          {
              double d2 = (this.posX + (MathHelper.cos(f6) * 24.0F)) - entity.posX;
              double d3 = (this.posY + 6D) - entity.posY;
              double d4 = (this.posZ + (MathHelper.sin(f6) * 24.0F)) - entity.posZ;
              double d5 = d2 * d2 + d3 * d3 + d4 * d4;
              if (!this.worldObj.isRemote)
              {
            	  if (entity.ticksExisted >= 0)
            	  {
            		  entity.addVelocity(d2 / d5 * 2D, d3 / d5 * 2D, d4 / d5 * 2D);
            	  }
            	  if (entity.ticksExisted >= 200)
            		  entity.ticksExisted = -100;
              }
              if (entity.motionY <= -0.5F)
              	entity.motionY = -0.5F;
          }
          if ((entity != null) && ((entity instanceof EntityLivingBase)) && (!(entity instanceof EntityHurricaneWither)) && (!(entity instanceof EntityAirWither)) && (!(entity instanceof EntityWitherGirl)) && (!(entity instanceof EntityWitherGirlVoid)) && (!(entity instanceof EntityFriendlyWither)) && (!(entity instanceof EntityWitherGirlPink))) 
          {
            if ((entity instanceof EntityPlayer))
            {
              if (!((EntityPlayer)entity).capabilities.disableDamage && !((EntityPlayer)entity).capabilities.allowFlying)
              {
            	  if (this.getDistanceSqToEntity(entity) <= 144D)
            	  {
                entity.attackEntityFrom(DamageSource.setExplosionSource(null), 1.0F);
                entity.motionX += this.rand.nextGaussian() - 0.5D;
                entity.motionZ += this.rand.nextGaussian() - 0.5D;
            	  }
                double d2 = (this.posX + (MathHelper.cos(f6) * 24.0F)) - entity.posX;
                double d3 = (this.posY + 6D) - entity.posY;
                double d4 = (this.posZ + (MathHelper.sin(f6) * 24.0F)) - entity.posZ;
                double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                if (!this.worldObj.isRemote)
                {
              	  if (entity.ticksExisted >= 0)
              	  {
              		  entity.addVelocity(d2 / d5 * 1.5D, d3 / d5 * 1.5D, d4 / d5 * 1.5D);
              	  }
              	  if (entity.ticksExisted >= 200)
              		  entity.ticksExisted = -100;
                }
                entity.motionX = d2 / d5;
                entity.motionY = d3 / d5;
                entity.motionZ = d4 / d5;
                if (entity.motionY <= -0.5F)
                	entity.motionY = -0.5F;
              }
            }
            else
            {
          	  if (this.getDistanceSqToEntity(entity) <= 144D)
          	  {
              entity.attackEntityFrom(DamageSource.setExplosionSource(null), 1.0F);
              entity.motionX += this.rand.nextGaussian() - 0.5D;
              entity.motionZ += this.rand.nextGaussian() - 0.5D;
          	  }
              double d2 = (this.posX + (MathHelper.cos(f6) * 24.0F)) - entity.posX;
              double d3 = (this.posY + 6D) - entity.posY;
              double d4 = (this.posZ + (MathHelper.sin(f6) * 24.0F)) - entity.posZ;
              double d5 = d2 * d2 + d3 * d3 + d4 * d4;
              if (!this.worldObj.isRemote)
              {
            	  if (entity.ticksExisted >= 0)
            	  {
            		  entity.addVelocity(d2 / d5 * 1.5D, d3 / d5 * 1.5D, d4 / d5 * 1.5D);
            	  }
            	  if (entity.ticksExisted >= 200)
            		  entity.ticksExisted = -100;
              }
              if (entity.motionY <= -0.5F)
              	entity.motionY = -0.5F;
            }
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
              float f = 10.0F;
              float f1 = 5.0F;
              double d0 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - f, this.posX + f);
              double d1 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - f1, this.posY + f1);
              double d2 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - f, this.posZ + f);
              launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
              this.field_82224_i[(i - 1)] = 0;
            }
          }
          int i1 = getWatchedTargetId(i);
          if (i1 > 0)
          {
            Entity entity = this.worldObj.getEntityByID(i1);
            if ((entity != null) && (entity.isEntityAlive()) && (getDistanceSqToEntity(entity) <= 900.0D) && (canEntityBeSeen(entity)))
            {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.field_82223_h[(i - 1)] = (this.ticksExisted + 40 + this.rand.nextInt(20));
              this.field_82224_i[(i - 1)] = 0;
            }
            else
            {
              func_82211_c(i, 0);
            }
          }
          else
          {
            List list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(20.0D, 8.0D, 20.0D), Predicates.and(attackEntitySelector, IEntitySelector.NOT_SPECTATING));
            for (int k1 = 0; (k1 < 10) && (!list.isEmpty()); k1++)
            {
              EntityLivingBase entitylivingbase = (EntityLivingBase)list.get(this.rand.nextInt(list.size()));
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
              list.remove(entitylivingbase);
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
      if (this.ticksExisted % 20 == 0) {
    	  heal(2.0F);
      }
    }
  }
  
  public boolean canEntityDestroy(Block block, IBlockAccess world, BlockPos pos, Entity entity)
  {
    return (block != Blocks.barrier) && (block != Blocks.bedrock) && (block != Blocks.end_portal) && (block != Blocks.end_portal_frame) && (block != Blocks.command_block);
  }
  
  public void func_82206_m()
  {
    setInvulTime(220);
    setHealth(getMaxHealth() / 3.0F);
  }
  
  public void setInWeb() {}
  
  public int getTotalArmorValue()
  {
    return 12;
  }
  
  private double func_82214_u(int p_82214_1_)
  {
    if (p_82214_1_ <= 0) {
      return this.posX;
    }
    float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 1.25D;
  }
  
  private double func_82208_v(int p_82208_1_)
  {
    return p_82208_1_ <= 0 ? this.posY + 2.95D : this.posY + 2.4D;
  }
  
  private double func_82213_w(int p_82213_1_)
  {
    if (p_82213_1_ <= 0) {
      return this.posZ;
    }
    float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 1.25D;
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
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
  {
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityHurricaneSkull entitywitherskull = new EntityHurricaneSkull(this.worldObj, this, d6, d7, d8);
    entitywitherskull.posY = d4;
    entitywitherskull.posX = d3;
    entitywitherskull.posZ = d5;
    this.worldObj.spawnEntityInWorld(entitywitherskull);
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
    if ((source != DamageSource.fall) && (source != DamageSource.anvil) && (source != DamageSource.fallingBlock) && (source != DamageSource.drown) && (source != DamageSource.inWall) && (!(source.getEntity() instanceof EntityHurricaneWither)))
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
      if ((entity != null) && (!(entity instanceof EntityPlayer)) && ((entity instanceof EntityLivingBase)) && (((EntityLivingBase)entity).getCreatureAttribute() == getCreatureAttribute())) {
        return false;
      }
      if (this.blockBreakCounter <= 0) {
        this.blockBreakCounter = 20;
      }
      for (int i = 0; i < this.field_82224_i.length; i++) {
        this.field_82224_i[i] += 3;
      }

      return super.attackEntityFrom(source, amount);
    }
    return false;
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    EntityItem entityitem = dropItem(Items.nether_star, 1);
    if (entityitem != null) {
      entityitem.setNoDespawn();
    }
    if (!this.worldObj.isRemote)
    {
        Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)).iterator();

        while (iterator.hasNext())
        {
            EntityPlayer entityplayer = (EntityPlayer)iterator.next();
            entityplayer.triggerAchievement(AchievementList.killWither);
            entityplayer.triggerAchievement(MoWithersAchievments.achievementKillHurricaneWither);
        }
    }
  }
  
  protected void despawnEntity()
  {
    this.entityAge = 0;
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender(float p_70070_1_)
  {
	  return 15728880;
  }
  
  public void fall(float distance, float damageMultiplier) {}
  
  public void addPotionEffect(PotionEffect p_70690_1_) {}
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(750.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
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
  
  public EnumCreatureAttribute getCreatureAttribute()
  {
    return EnumCreatureAttribute.UNDEAD;
  }
  
  public void mountEntity(Entity entityIn)
  {
    this.ridingEntity = null;
  }
  
  protected float getSoundVolume()
  {
    return 3.0F;
  }
}
