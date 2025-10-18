package net.minecraft.entity.wither;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
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
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.witherskulls.EntityAirSkull;
import net.minecraft.entity.witherskulls.EntityEarthSkull;
import net.minecraft.entity.witherskulls.EntityFireSkull;
import net.minecraft.entity.witherskulls.EntityIceSkull;
import net.minecraft.entity.witherskulls.EntityLightningSkull;
import net.minecraft.entity.witherskulls.EntityMagmaSkull;
import net.minecraft.entity.witherskulls.EntityWaterSkull;
import net.minecraft.entity.witherskulls.EntityWitherDragonProjectile;
import net.minecraft.entity.witherskulls.EntityWitherSkullAvatar;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAvatarWither
  extends EntityGolem
  implements IBossDisplayData, IRangedAttackMob, IMob
{
  private float[] field_82220_d = new float[2];
  private float[] field_82221_e = new float[2];
  private float[] field_82217_f = new float[2];
  private float[] field_82218_g = new float[2];
  private int[] field_82223_h = new int[2];
  private int[] field_82224_i = new int[2];
  private int blockBreakCounter;
private int deathTicks;
  private static final Predicate attackEntitySelector = new Predicate()
  {
    public boolean func_180027_a(Entity p_180027_1_)
    {
      return ((p_180027_1_ instanceof EntityLivingBase)) && (((EntityLivingBase)p_180027_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD);
    }
    
    public boolean apply(Object p_apply_1_)
    {
      return func_180027_a((Entity)p_apply_1_);
    }
  };
  
  public EntityAvatarWither(World worldIn)
  {
    super(worldIn);
    setHealth(getMaxHealth());
    setSize(3.0F, 10.55655F);
    this.isImmuneToFire = true;
    this.ignoreFrustumCheck = true;
    this.renderDistanceWeight = 16.0D;
    this.noClip = true;
    ((PathNavigateGround)getNavigator()).func_179693_d(true);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 1, 100.0F));
    this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
    this.experienceValue = 500000;
    this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
  }
  
  protected void entityInit()
  {
    super.entityInit();
    this.dataWatcher.addObject(17, new Integer(0));
    this.dataWatcher.addObject(18, new Integer(0));
    this.dataWatcher.addObject(19, new Integer(0));
    this.dataWatcher.addObject(20, new Integer(0));
  }
  
  public void onKillCommand()
  {
    setDead();
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
    playSound("mowithers:AirWitherIdle", getSoundVolume(), getSoundPitch());
    playSound("mowithers:EarthWitherIdle", getSoundVolume(), getSoundPitch());
    playSound("mob.wither.idle", getSoundVolume(), getSoundPitch() - 0.25F);
    playSound("mowithers:WaterWitherIdle", getSoundVolume(), getSoundPitch());
    playSound("mowithers:WaterWitherIdle", getSoundVolume(), getSoundPitch() - 0.3F);
    playSound("mowithers:AirWitherIdle", getSoundVolume(), getSoundPitch() - 0.1F);
    playSound("mob.wither.idle", getSoundVolume(), getSoundPitch() - 0.4F);
    playSound("mowithers:LightningWitherIdle", getSoundVolume(), getSoundPitch());
    return "mowithers:VoidWitherIdle";
  }
  
  protected String getHurtSound()
  {
    playSound("mowithers:AirWitherHurt", getSoundVolume(), getSoundPitch());
    playSound("mowithers:EarthWitherHurt", getSoundVolume(), getSoundPitch());
    playSound("mob.wither.hurt", getSoundVolume(), getSoundPitch() - 0.25F);
    playSound("mowithers:WaterWitherHurt", getSoundVolume(), getSoundPitch());
    playSound("mowithers:WaterWitherHurt", getSoundVolume(), getSoundPitch() - 0.3F);
    playSound("mowithers:AirWitherHurt", getSoundVolume(), getSoundPitch() - 0.1F);
    playSound("mob.wither.hurt", getSoundVolume(), getSoundPitch() - 0.4F);
    playSound("mowithers:LightningWitherHurt", getSoundVolume(), getSoundPitch());
    return "mowithers:VoidWitherHurt";
  }
  
  protected String getDeathSound()
  {
    playSound("mowithers:AirWitherDeath", getSoundVolume(), getSoundPitch());
    playSound("mowithers:EarthWitherDeath", getSoundVolume(), getSoundPitch());
    playSound("mob.wither.death", getSoundVolume(), getSoundPitch() - 0.25F);
    playSound("mowithers:WaterWitherDeath", getSoundVolume(), getSoundPitch());
    playSound("mowithers:WaterWitherDeath", getSoundVolume(), getSoundPitch() - 0.3F);
    playSound("mowithers:AirWitherDeath", getSoundVolume(), getSoundPitch() - 0.1F);
    playSound("mob.wither.death", getSoundVolume(), getSoundPitch() - 0.4F);
    playSound("mowithers:LightningWitherDeath", getSoundVolume(), getSoundPitch());
    return "mowithers:VoidWitherDeath";
  }
  
  public boolean isBurning()
  {
    return true;
  }
  
  public void onLivingUpdate()
  {
    if ((!this.worldObj.isRemote) && (this.worldObj.isThundering()) && (this.worldObj.canBlockSeeSky(new BlockPos(this))) && (this.rand.nextInt(40) == 0)) {
      this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX - 0.5D, this.posY + 10.5D, this.posZ - 0.5D));
    }
    if (getAttackTarget() == null) 
    	this.motionY *= 0.0D;
    if (this.isEntityAlive() && (!this.worldObj.isRemote) && (getWatchedTargetId(0) > 0))
    {
      Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
      if (entity != null)
      {
    	  this.faceEntity(entity, 180F, 180F);
        double d0 = entity.posX - this.posX;
        double d1 = entity.posY - this.posY;
        double d2 = entity.posZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (d3 > 9.0D)
        {
          double d5 = MathHelper.sqrt_double(d3);
          this.motionX += (d0 / d5 * 0.9D - this.motionX);
          this.motionY += (d1 / d5 * 0.9D - this.motionY);
          this.motionZ += (d2 / d5 * 0.9D - this.motionZ);
        }
      }
    }
    if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.0D) {
        for (int i = 0; i < 4; ++i)
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() * 6D - 3D), this.posY + 6 + (this.rand.nextDouble() * 12D - 6D), this.posZ + (this.rand.nextDouble() * 6D - 3D), MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F), -this.motionY * 3.1415927F / 180.0F, -MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F), new int[0]);
      this.rotationYaw = ((float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F);
    }
    super.onLivingUpdate();
    
    int i;
    for (i = 0; i < 2; i++)
    {
      this.field_82218_g[i] = this.field_82221_e[i];
      this.field_82217_f[i] = this.field_82220_d[i];
    }
    for (i = 0; i < 2; i++)
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
        if (this.getHealth() <= 0F)
        {
        	this.field_82221_e[i] = func_82204_b(this.field_82221_e[i], this.renderYawOffset + (rand.nextFloat() * 60F - 30F), 60.0F);
            this.field_82220_d[i] = func_82204_b(this.field_82220_d[i], rand.nextFloat() * 60F - 30F, 60.0F);
        }
      }
    }
    for (int y = 0; y < 5; y++)
    {
    boolean flag = isArmored();
    for (int j = 0; j < 3; j++)
    {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d10 + this.rand.nextGaussian(), d2 + this.rand.nextGaussian(), d4 + this.rand.nextGaussian(), 0.0D, 0.0D, 0.0D, new int[0]);
      if (this.worldObj.rand.nextInt(10) == 0) {
        this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
      if ((flag) && (this.worldObj.rand.nextInt(4) == 0)) {
        this.worldObj.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    
    int j;
    if (getInvulTime() > 0) {
      for (j = 0; j < 3; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + 4F + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
        this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + 4F + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, (this.rand.nextDouble() - 0.5D) * 2D, 0.25D, (this.rand.nextDouble() - 0.5D) * 2D, new int[0]);
        this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + 4F + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, (this.rand.nextDouble() - 0.5D) * 2D, 0.25D, (this.rand.nextDouble() - 0.5D) * 2D, new int[] { 3 });
        this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + 4F + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, (this.rand.nextDouble() - 0.5D) * 2D, 0.25D, (this.rand.nextDouble() - 0.5D) * 2D, new int[0]);
        this.worldObj.spawnParticle(EnumParticleTypes.DRIP_WATER, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + 4F + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, (this.rand.nextDouble() - 0.5D) * 2D, 0.25D, (this.rand.nextDouble() - 0.5D) * 2D, new int[0]);
      }
    }
    for (j = 0; j < 3; j++)
    {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d10 + this.rand.nextGaussian(), d2 + this.rand.nextGaussian(), d4 + this.rand.nextGaussian(), 0.0D, 0.0D, 0.0D, new int[] { 3 });
      
      this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[] { 3 });
      if ((flag) || (this.worldObj.rand.nextInt(20) == 0))
      {
        this.worldObj.spawnParticle(EnumParticleTypes.DRIP_LAVA, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
        this.worldObj.spawnParticle(EnumParticleTypes.DRIP_LAVA, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    if (getInvulTime() > 0) {
      for (j = 0; j < 3; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    for (j = 0; j < 3; j++)
    {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d10 + this.rand.nextGaussian(), d2 + this.rand.nextGaussian(), d4 + this.rand.nextGaussian(), 0.0D, 0.0D, 0.0D, new int[0]);
      
      this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
      if ((flag) || (this.worldObj.rand.nextInt(10) == 0))
      {
        this.worldObj.spawnParticle(EnumParticleTypes.FLAME, d10 + this.rand.nextGaussian(), d2 + this.rand.nextGaussian(), d4 + this.rand.nextGaussian(), 0.0D, 0.0D, 0.0D, new int[0]);
        this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    if (getInvulTime() > 0) {
      for (j = 0; j < 3; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    for (j = 0; j < 3; j++)
    {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, d10 + this.rand.nextGaussian(), d2 + this.rand.nextGaussian(), d4 + this.rand.nextGaussian(), 0.0D, 0.0D, 0.0D, new int[0]);
      
      this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
      if ((flag) || (this.worldObj.rand.nextInt(20) == 0))
      {
        this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
        this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    if (getInvulTime() > 0) {
      for (j = 0; j < 3; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    }
    if (getInvulTime() > 0)
    {
      setInvisible(true);
      this.worldObj.setWorldTime(this.worldObj.getWorldTime() + 1000L);
      
      List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(64.0D, 64.0D, 64.0D));
      if ((list != null) && (!list.isEmpty())) {
        for (int i111 = 0; i111 < list.size(); i111++)
        {
          Entity entity = (Entity)list.get(i111);
          if ((entity != null) && ((entity instanceof EntityLivingBase)))
          {
            entity.motionX += (float)(Math.random() * 0.1D - 0.05D);
            entity.motionY += (float)(Math.random() * 0.1D - 0.05D);
            entity.motionZ += (float)(Math.random() * 0.1D - 0.05D);
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20, 3));
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.confusion.id, 20, 3)); Entity 
            
              tmp2783_2781 = entity;tmp2783_2781.rotationPitch = ((float)(tmp2783_2781.rotationPitch + (float)(Math.random() * 4.0D - 2.0D))); Entity 
              tmp2808_2806 = entity;tmp2808_2806.rotationYaw = ((float)(tmp2808_2806.rotationYaw + (float)(Math.random() * 4.0D - 2.0D)));
            if ((entity.onGround) && (this.rand.nextInt(5) == 0))
            {
              entity.motionY = 0.5D;
              entity.attackEntityFrom(DamageSource.fall, 1.0F);
            }
          }
        }
      }
    }
  }
  
  protected void updateAITasks()
  {
    if (getInvulTime() > 0)
    {
      int i = getInvulTime() - 1;
      if (i <= 0)
      {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 1, 100.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
        setInvisible(false);
        this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 64.0F, false, false);
        this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
        
        List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(64.0D, 64.0D, 64.0D));
        if ((list111 != null) && (!list111.isEmpty()) && (this.rand.nextInt(20) == 0)) {
          for (int i111 = 0; i111 < list111.size(); i111++)
          {
            Entity entity = (Entity)list111.get(i111);
            if ((entity != null) && ((entity instanceof EntityLivingBase)))
            {
              entity.setFire(300);
            }
          }
        }
      }
      setInvulTime(i);
      if (this.ticksExisted % 1 == 0) {
    	  this.setAttackTarget(null);
        heal(100.0F);
      }
    }
    else
    {
      super.updateAITasks();
      int i;
      List list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(16.0D, 16.0D, 16.0D));
      if ((list1 != null) && (!list1.isEmpty())) {
        for (int i2 = 0; i2 < list1.size(); i2++)
        {
          Entity entity = (Entity)list1.get(i2);
          if ((entity != null) && (entity instanceof EntityItem || entity instanceof EntityFishHook || entity instanceof EntityArrow || entity instanceof EntityXPOrb || entity instanceof EntityTNTPrimed || entity instanceof EntityMinecart || entity instanceof EntityBoat)) {
              double d2 = this.posX - entity.posX;
              double d3 = (this.posY + 2) - entity.posY;
              double d4 = this.posZ - entity.posZ;
              double d5 = d2 * d2 + d3 * d3 + d4 * d4;
              entity.addVelocity(d2 / d5 * 3D, d3 / d5 * 3D, d4 / d5 * 3D);
            }
          if ((entity != null) && ((entity instanceof EntityLivingBase) || (entity instanceof EntityDragonPart)) && (!(entity instanceof EntityAvatarWither)) && (!(entity instanceof EntityWitherGirl)) && (!(entity instanceof EntityWitherGirlVoid)) && (!(entity instanceof EntityFriendlyWither)) && (!(entity instanceof EntityWitherGirlPink))) {
            if ((entity instanceof EntityPlayer))
            {
              if (!((EntityPlayer)entity).capabilities.disableDamage)
              {
                entity.attackEntityFrom(DamageSource.setExplosionSource(null), 4.0F);
                double d2 = this.posX - entity.posX;
                double d3 = (this.posY + 2) - entity.posY;
                double d4 = this.posZ - entity.posZ;
                double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                entity.addVelocity(d2 / d5 * 1.5D, d3 / d5 * 1.5D, d4 / d5 * 1.5D);
              }
            }
            else
            {
              entity.attackEntityFrom(DamageSource.setExplosionSource(null), 4.0F);
              double d2 = this.posX - entity.posX;
              double d3 = (this.posY + 3) - entity.posY;
              double d4 = this.posZ - entity.posZ;
              double d5 = d2 * d2 + d3 * d3 + d4 * d4;
              entity.addVelocity(d2 / d5 * 2.5D, d3 / d5 * 2.5D, d4 / d5 * 2.5D);
              if (this.getDistanceSqToEntity(entity) <= 36D)
            	  this.attackEntityAsMob(entity);
            }
          }
        }
      }
      if (this.worldObj.rand.nextInt(120) == 0) {
        this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, !this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      }
      if (this.worldObj.rand.nextInt(300) == 0) {
          this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 16.0F, false, false);
          for (i = 0; i < 2 + rand.nextInt(14); i++)
          this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
        }
      for (i = 1; i < 3; i++) {
        if (this.ticksExisted >= this.field_82223_h[(i - 1)])
        {
          this.field_82223_h[(i - 1)] = (this.ticksExisted + this.rand.nextInt(10));
          if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)
          {
            int k2 = i - 1;
            int l2 = this.field_82224_i[(i - 1)];
            this.field_82224_i[k2] = (this.field_82224_i[(i - 1)] + 1);
            if (l2 > 15)
            {
              float f = 16.0F;
              float f1 = 2.0F;
              double d0 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - f, this.posX + f);
              double d1 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - f1, this.posY + f1);
              double d2 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - f, this.posZ + f);
              launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
              launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
              launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
              launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
              this.field_82224_i[(i - 1)] = 0;
              this.worldObj.newExplosion(this, func_82214_u(i + 1), func_82208_v(i + 1) + 1.0D, func_82213_w(i + 1), 2.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            }
          }
          int i1 = getWatchedTargetId(i);
          if (i1 > 0)
          {
            Entity entity = this.worldObj.getEntityByID(i1);
            if ((entity != null) && (entity.isEntityAlive()) && (getDistanceSqToEntity(entity) <= 22500.0D))
            {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.field_82223_h[(i - 1)] = (this.ticksExisted + 1);
              this.field_82224_i[(i - 1)] = 0;
            }
            else
            {
              func_82211_c(i, 0);
            }
          }
          else
          {
            List list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(150.0D, 150.0D, 150.0D), Predicates.and(attackEntitySelector, IEntitySelector.NOT_SPECTATING));
            for (int k1 = 0; (k1 < 2) && (!list.isEmpty()); k1++)
            {
              EntityLivingBase entitylivingbase = (EntityLivingBase)list.get(this.rand.nextInt(list.size()));
              if ((entitylivingbase != this) && (entitylivingbase.isEntityAlive())) {
                if ((entitylivingbase instanceof EntityPlayer))
                {
                  if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage) {
                    func_82211_c(i, entitylivingbase.getEntityId());
                  }
                }
                else {
                  func_82211_c(i, entitylivingbase.getEntityId());
                }
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
          i = MathHelper.floor_double(this.posY);
          int i1 = MathHelper.floor_double(this.posX);
          int j1 = MathHelper.floor_double(this.posZ);
          boolean flag = false;
          for (int l1 = -15; l1 <= 15; l1++) {
            for (int i2 = -15; i2 <= 15; i2++) {
              for (int j = -15; j <= 15; j++)
              {
                int j2 = i1 + l1;
                int k = i + j;
                int l = j1 + i2;
                Block block = this.worldObj.getBlockState(new BlockPos(j2, k, l)).getBlock();
                if ((!block.isAir(this.worldObj, new BlockPos(j2, k, l))) && (canEntityDestroy(block, this.worldObj, new BlockPos(j2, k, l), this)))
                {
                  boolean should = true;
                  if ((block.getMaterial() == Material.ground) || (block.getMaterial() == Material.grass) || (block == Blocks.gravel) || (block == Blocks.netherrack)) {
                    should = false;
                  }
                  if ((block == Blocks.stone) || (block == Blocks.cobblestone)) {
                    should = this.rand.nextInt(20) == 0;
                  }
                  this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1012, new BlockPos(this), 0);
                  this.worldObj.destroyBlock(new BlockPos(j2, k, l), should);
                }
              }
            }
          }
        }
      }
      if ((this.blockBreakCounter <= 0) && (getAttackTarget() != null)) {
        this.blockBreakCounter = 6;
      }
      if (this.ticksExisted % 1 == 0) {
        heal(1.0F);
      }
    }
  }
  
  public boolean canEntityDestroy(Block block, IBlockAccess world, BlockPos pos, Entity entity)
  {
    return (block != Blocks.barrier) && (block != Blocks.bedrock) && (block != Blocks.end_portal) && (block != Blocks.end_portal_frame) && (block != Blocks.command_block);
  }
  
  public void func_82206_m()
  {
    setInvulTime(1440);
    setHealth(getMaxHealth() / 50.0F);
  }
  
  public void setInWeb() {}
  
  public int getTotalArmorValue()
  {
    return 24;
  }
  
  protected void onDeathUpdate()
  {
      List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(128.0D, 128.0D, 128.0D));
      if ((list111 != null) && (!list111.isEmpty())) {
        for (int i111 = 0; i111 < list111.size(); i111++)
        {
          Entity entity = (Entity)list111.get(i111);
          if (entity != null && entity instanceof EntityWitherDragonProjectile) {
              double d1 = entity.posX - this.posX;
              double d2 = entity.posZ - this.posZ;
              double d3 = entity.posZ - this.posZ;
              double d4 = d1 * d1 + d2 * d2 + d3 * d3;
              double d5 = MathHelper.sqrt_double(d3);
              entity.motionX += (d1 / d5 * 0.5D - entity.motionX) * 0.5D;
              entity.motionY += (d2 / d5 * 0.5D - entity.motionX) * 0.5D;
              entity.motionZ += (d3 / d5 * 0.5D - entity.motionZ) * 0.5D;
          }
        }
      }
      this.renderYawOffset = this.rotationYawHead + (rand.nextFloat() * 16F - 8F);
      
      this.rotationYawHead = this.rotationYaw = 0F;
      
      this.rotationPitch = (this.rand.nextFloat() * 90F - 45F);
      
      EntityPlayer player = worldObj.getClosestPlayerToEntity(this, -1D);
      
      if (player != null)
    	  player.setLocationAndAngles(this.posX, this.posY, this.posZ + 15D, this.rotationYaw - 180F, -15F);
      
    ++this.deathTicks;
    if (this.motionY < 0.0D) {
      this.motionY = 0.0D;
    }
    if ((this.deathTicks >= 240) && (this.deathTicks <= 300))
    {
      float f = (this.rand.nextFloat() - 0.5F) * 32.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 32.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 32.0F;
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + this.getEyeHeight(), this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY + this.getEyeHeight() + f1, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY + this.getEyeHeight(), this.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
    }
    if (!this.worldObj.isRemote)
    {
        if (this.deathTicks == 1) {
        	playSound(getDeathSound(), 10000.0F, 1.0F);
        	playSound(getDeathSound(), 10000.0F, 1.0F);
        	playSound(getDeathSound(), 10000.0F, 1.0F);
        	playSound(getDeathSound(), 10000.0F, 1.0F);
        	playSound(getDeathSound(), 10000.0F, 1.0F);
        	playSound(getDeathSound(), 10000.0F, 1.0F);
        	playSound(getDeathSound(), 10000.0F, 1.0F);
            playSound(getDeathSound(), 10000.0F, 1.0F);
            this.worldObj.playBroadcastSound(1018, new BlockPos(this), 0);
          }
        
        if (this.deathTicks % 10 == 0 && this.deathTicks < 280) 
        {
        	this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX - 0.5D, this.posY + 10.5D, this.posZ - 0.5D));
        }
    }
    moveEntity(0.0D, 0.2D, 0.0D);
    if ((this.deathTicks == 300) && (!this.worldObj.isRemote))
    {
      int i = 1000000;
      while (i > 0)
      {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 8.0D, this.posZ, j));
      }
      this.entityDropItem(new ItemStack(Blocks.dragon_egg, 1), 12F);
      this.entityDropItem(new ItemStack(Blocks.beacon, 1), 12F);
      Item it = null;
      Block bl = null;
      Iterator ilist = Item.itemRegistry.iterator();
      int icount = 0;
      while (ilist.hasNext())
      {
        it = (Item)ilist.next();
        if (it != null) {
          icount++;
        }
      }
      for (int j = 0; j < 128;)
      {
        int k = 1 + this.rand.nextInt(icount);
        ilist = Item.itemRegistry.iterator();
        while ((k > 0) && (ilist.hasNext()))
        {
          it = (Item)ilist.next();
          k--;
        }
        if (it != null)
        {
          j++;
          EntityItem var3 = new EntityItem(this.worldObj, this.posX + ((this.rand.nextDouble() - 0.5D) * 12D), this.posY + 12.0D + ((this.rand.nextDouble() - 0.5D) * 12D), this.posZ + ((this.rand.nextDouble() - 0.5D) * 12D), new ItemStack(it, 1, 0));
          this.worldObj.spawnEntityInWorld(var3);
        }
      }
      Iterator blist = Block.blockRegistry.iterator();
      int bcount = 0;
      while (blist.hasNext())
      {
        bl = (Block)blist.next();
        if (bl != null) {
          bcount++;
        }
      }
      for (int j = 0; j < 128;)
      {
        int k = 1 + this.rand.nextInt(bcount);
        blist = Block.blockRegistry.iterator();
        while ((k > 0) && (blist.hasNext()))
        {
          bl = (Block)blist.next();
          k--;
        }
        if (bl != null)
        {
          j++;
          EntityItem var3 = new EntityItem(this.worldObj, this.posX + ((this.rand.nextDouble() - 0.5D) * 12D), this.posY + 12.0D + ((this.rand.nextDouble() - 0.5D) * 12D), this.posZ + ((this.rand.nextDouble() - 0.5D) * 12D), new ItemStack(Item.getItemFromBlock(bl), 1, 0));
          this.worldObj.spawnEntityInWorld(var3);
        }
      }
      setDead();
    }
  }
  
  private double func_82214_u(int p_82214_1_)
  {
    if (p_82214_1_ <= 0) {
      return this.posX;
    }
    float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 3.25D;
  }
  
  private double func_82208_v(int p_82208_1_)
  {
    return p_82208_1_ <= 0 ? this.posY + 9.0D : this.posY + 7.5D;
  }
  
  private double func_82213_w(int p_82213_1_)
  {
    if (p_82213_1_ <= 0) {
      return this.posZ;
    }
    float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 3.25D;
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
	    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.1F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
  {
    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, new BlockPos(this), 0);
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3 + this.rand.nextGaussian() * 3.0D;
    double d7 = p_82209_4_ - d4 + this.rand.nextGaussian() * 3.0D;
    double d8 = p_82209_6_ - d5 + this.rand.nextGaussian() * 3.0D;
    EntityFireball entitywitherskull;
    if (this.rand.nextInt(50) == 0)
    {
        d6 = p_82209_2_ - d3;
        d7 = p_82209_4_ - d4;
        d8 = p_82209_6_ - d5;
        EntityWitherSkullAvatar skull = new EntityWitherSkullAvatar(this.worldObj, this, d6, d7, d8);
        skull.posY = d4;
        skull.posX = d3;
        skull.posZ = d5;
        this.worldObj.spawnEntityInWorld(skull);
    }
    else
    {
        switch (this.rand.nextInt(7))
        {
        case 0: 
          entitywitherskull = new EntityAirSkull(this.worldObj, this, d6, d7, d8);
          entitywitherskull.posY = d4;
          entitywitherskull.posX = d3;
          entitywitherskull.posZ = d5;
          this.worldObj.spawnEntityInWorld(entitywitherskull);
          break;
        case 1: 
          entitywitherskull = new EntityEarthSkull(this.worldObj, this, d6, d7, d8);
          if (p_82209_8_) {
            ((EntityEarthSkull)entitywitherskull).setInvulnerable(true);
          }
          entitywitherskull.posY = d4;
          entitywitherskull.posX = d3;
          entitywitherskull.posZ = d5;
          this.worldObj.spawnEntityInWorld(entitywitherskull);
          break;
        case 2: 
          entitywitherskull = new EntityFireSkull(this.worldObj, this, d6, d7, d8);
          if (p_82209_8_) {
            ((EntityFireSkull)entitywitherskull).setInvulnerable(true);
          }
          entitywitherskull.posY = d4;
          entitywitherskull.posX = d3;
          entitywitherskull.posZ = d5;
          this.worldObj.spawnEntityInWorld(entitywitherskull);
          break;
        case 3: 
          entitywitherskull = new EntityWaterSkull(this.worldObj, this, d6, d7, d8);
          if (p_82209_8_) {
            ((EntityWaterSkull)entitywitherskull).setInvulnerable(true);
          }
          entitywitherskull.posY = d4;
          entitywitherskull.posX = d3;
          entitywitherskull.posZ = d5;
          this.worldObj.spawnEntityInWorld(entitywitherskull);
          break;
        case 4: 
            entitywitherskull = new EntityIceSkull(this.worldObj, this, d6, d7, d8);
            if (p_82209_8_) {
              ((EntityIceSkull)entitywitherskull).setInvulnerable(true);
            }
            entitywitherskull.posY = d4;
            entitywitherskull.posX = d3;
            entitywitherskull.posZ = d5;
            this.worldObj.spawnEntityInWorld(entitywitherskull);
            break;
        case 5: 
            playSound("ambient.cave.cave", 100.0F, 2.0F);
            playSound("ambient.weather.thunder", 5.0F, 2.0F);
            entitywitherskull = new EntityLightningSkull(this.worldObj, this, d6, d7, d8);
            if (p_82209_8_) {
              ((EntityLightningSkull)entitywitherskull).setInvulnerable(true);
            }
            entitywitherskull.posY = d4;
            entitywitherskull.posX = d3;
            entitywitherskull.posZ = d5;
            this.worldObj.spawnEntityInWorld(entitywitherskull);
            break;
        case 6: 
            entitywitherskull = new EntityMagmaSkull(this.worldObj, this, d6, d7, d8);
            if (p_82209_8_) {
              ((EntityMagmaSkull)entitywitherskull).setInvulnerable(true);
            }
            entitywitherskull.posY = d4;
            entitywitherskull.posX = d3;
            entitywitherskull.posZ = d5;
            this.worldObj.spawnEntityInWorld(entitywitherskull);
            break;
        }
    }
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
  {
	    launchWitherSkullToEntity(0, p_82196_1_);
	    launchWitherSkullToEntity(0, p_82196_1_);
	    launchWitherSkullToEntity(0, p_82196_1_);
	    launchWitherSkullToEntity(0, p_82196_1_);
	    p_82196_1_.hurtResistantTime = 0;
	    p_82196_1_.attackEntityFrom(DamageSource.setExplosionSource(null), 1F);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (isEntityInvulnerable(source)) {
      return false;
    }
    if (source == DamageSource.lightningBolt)
    {
      heal(amount);
      if (getAbsorptionAmount() < 6000.0F) {
        setAbsorptionAmount(getAbsorptionAmount() + amount);
      }
      return false;
    }
    if ((source != DamageSource.outOfWorld) && (source != DamageSource.drown) && (source != DamageSource.inWall) && (source != DamageSource.starve) && (source != DamageSource.anvil) && (source != DamageSource.fallingBlock) && (source != DamageSource.inFire) && (source != DamageSource.onFire) && (source != DamageSource.lava) && (source != DamageSource.wither) && (source != DamageSource.cactus) && (source != DamageSource.fall) && (!(source.getEntity() instanceof EntityAvatarWither)))
    {
      if (getInvulTime() > 0) {
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
      this.blockBreakCounter = 1;
      for (int i = 0; i < this.field_82224_i.length; i++) {
        this.field_82224_i[i] += 20;
      }
      if (this.worldObj.rand.nextInt(20) == 0) {
        this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      }
      return super.attackEntityFrom(source, amount);
    }
    return false;
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    EntityItem entityitem = dropItem(Items.nether_star, 64);
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
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender(float p_70070_1_)
  {
    return 15728880;
  }
  
  public float getBrightness(float p_70013_1_)
  {
    return 1.0F;
  }
  
  public void fall(float distance, float damageMultiplier) {}
  
  public void addPotionEffect(PotionEffect p_70690_1_) {}
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(250000.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0D);
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
    return true;
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
    return 10000.0F;
  }
}
