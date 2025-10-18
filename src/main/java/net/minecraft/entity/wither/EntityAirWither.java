package net.minecraft.entity.wither;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.MoWithers.MoWithersAchievments;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.witherskulls.EntityAirSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
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

public class EntityAirWither
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
  private boolean water;
  private boolean fire;
  private boolean earth;
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
  private static final Predicate withersUnit = new Predicate()
  {
    public boolean func_180027_a(Entity p_180027_1_)
    {
      return (((p_180027_1_ instanceof EntityEarthWither)) && (((EntityEarthWither)p_180027_1_).canConjoin())) || (((p_180027_1_ instanceof EntityFireWither)) && (((EntityFireWither)p_180027_1_).canConjoin())) || (((p_180027_1_ instanceof EntityWaterWither)) && (((EntityWaterWither)p_180027_1_).canConjoin()));
    }
    
    public boolean apply(Object p_apply_1_)
    {
      return func_180027_a((Entity)p_apply_1_);
    }
  };
  
  public EntityAirWither(World worldIn)
  {
    super(worldIn);
    this.noClip = true;
    setHealth(getMaxHealth());
    setSize(0.9F, 3.5F);
    this.isImmuneToFire = true;
    ((PathNavigateGround)getNavigator()).func_179693_d(true);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 100.0F));
    this.moveHelper = new AirWitherMoveHelper();
    this.tasks.addTask(5, new AIRandomFly());
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
    this.experienceValue = 500;
  }
  
  protected void entityInit()
  {
    super.entityInit();
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(17, new Integer(0));
    this.dataWatcher.addObject(18, new Integer(0));
    this.dataWatcher.addObject(19, new Integer(0));
    this.dataWatcher.addObject(20, new Integer(0));
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Invul", getInvulTime());
    if (canConjoin()) {
      tagCompound.setBoolean("Unite", canConjoin());
    }
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setInvulTime(tagCompund.getInteger("Invul"));
    shouldConjoin(tagCompund.getBoolean("Unite"));
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
  
  public void shouldConjoin(boolean p_94061_1_)
  {
    this.dataWatcher.updateObject(16, Byte.valueOf((byte)(p_94061_1_ ? 1 : 0)));
  }
  
  public boolean canConjoin()
  {
    return this.dataWatcher.getWatchableObjectByte(16) != 0;
  }
  
  public boolean interact(EntityPlayer player)
  {
    ItemStack itemstack = player.getCurrentEquippedItem();
    if ((itemstack != null) && (itemstack.getItem() == Items.diamond_sword))
    {
      this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
      
      shouldConjoin(true);
      
      return true;
    }
    return false;
  }
  
  public void onLivingUpdate()
  {
    List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(64.0D, 64.0D, 64.0D));
    if ((list != null) && (!list.isEmpty()) && (canConjoin()))
    {
      setAttackTarget(null);
      for (int i1 = 0; i1 < list.size(); i1++)
      {
        Entity entity = (Entity)list.get(i1);
        if ((entity != null) && ((entity instanceof EntityEarthWither)) && (((EntityEarthWither)entity).canConjoin())) {
          ((EntityEarthWither)entity).setAttackTarget(this);
        }
        if ((entity != null) && ((entity instanceof EntityFireWither)) && (((EntityFireWither)entity).canConjoin())) {
          ((EntityFireWither)entity).setAttackTarget(this);
        }
        if ((entity != null) && ((entity instanceof EntityWaterWither)) && (((EntityWaterWither)entity).canConjoin())) {
          ((EntityWaterWither)entity).setAttackTarget(this);
        }
      }
    }
    List list11 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox(), Predicates.and(new Predicate[] { withersUnit }));
    if ((list11 != null) && (!list11.isEmpty()) && (canConjoin())) {
      for (int i3 = 0; i3 < list11.size(); i3++)
      {
        Entity entity = (Entity)list11.get(i3);
        if ((entity != null) && ((entity instanceof EntityEarthWither)) && (((EntityEarthWither)entity).canConjoin()))
        {
          this.earth = true;
          this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
          entity.setDead();
        }
        if ((entity != null) && ((entity instanceof EntityFireWither)) && (((EntityFireWither)entity).canConjoin()))
        {
          this.fire = true;
          this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
          entity.setDead();
        }
        if ((entity != null) && ((entity instanceof EntityWaterWither)) && (((EntityWaterWither)entity).canConjoin()))
        {
          this.water = true;
          this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
          entity.setDead();
        }
        if ((this.water) && (this.earth) && (this.fire))
        {
          EntityAvatarWither entityavatar = new EntityAvatarWither(this.worldObj);
          entityavatar.copyLocationAndAnglesFrom(this);
          if (!this.worldObj.isRemote)
          {
            this.worldObj.spawnEntityInWorld(entityavatar);
            setDead();
            entityavatar.func_82206_m();
            entityavatar.moveEntity(this.motionX, this.motionY + 0.5D, this.motionZ);
            
            Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)).iterator();
            EntityPlayer entityplayer;
            while (iterator.hasNext()) {
              entityplayer = (EntityPlayer)iterator.next();
            }
          }
        }
      }
    }
    if (this.isArmored()) 
    {
    	this.motionX *= 0.75F;
    	this.motionY *= 0.75F;
    	this.motionZ *= 0.75F;
    }
    if ((!this.worldObj.isRemote) && (getWatchedTargetId(0) > 0) && this.getHealth() > 0F)
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
          this.motionX += (d0 / d5 * 0.99D - this.motionX);
          this.motionY += (d1 / d5 * 0.99D - this.motionY);
          this.motionZ += (d2 / d5 * 0.99D - this.motionZ);
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
    if (canConjoin()) {
      for (int j = 0; j < 5; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    if (this.earth)
    {
      for (int j = 0; j < 15; j++)
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
        for (int j = 0; j < 15; j++) {
          this.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
        }
      }
    }
    if (this.fire)
    {
      for (int j = 0; j < 15; j++)
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
        for (int j = 0; j < 15; j++) {
          this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
        }
      }
    }
    if (this.water)
    {
      for (int j = 0; j < 15; j++)
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
        for (int j = 0; j < 15; j++) {
          this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
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
        this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 0.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
        this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
        
        List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8.0D, 8.0D, 8.0D));
        if ((list111 != null) && (!list111.isEmpty())) {
          for (int i111 = 0; i111 < list111.size(); i111++)
          {
            Entity entity = (Entity)list111.get(i111);
            if ((entity != null) && ((entity instanceof EntityLivingBase)))
            {
              entity.motionY = 1.0D;
              entity.attackEntityFrom(DamageSource.inWall, 10.0F);
            }
          }
        }
      }
      setInvulTime(i);
      if (this.ticksExisted % 10 == 0) {
        heal(30.0F);
      }
    }
    else
    {
      super.updateAITasks();
      List list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(16.0D, 16.0D, 16.0D));
      if ((list1 != null) && (!list1.isEmpty()) && (!canConjoin() && this.dataWatcher.getWatchableObjectByte(15) == 0)) {
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
          if ((entity != null) && ((entity instanceof EntityLivingBase)) && (!(entity instanceof IBossDisplayData)) && (!(entity instanceof EntityHurricaneWither)) && (!(entity instanceof EntityAirWither)) && (!(entity instanceof EntityWitherGirl)) && (!(entity instanceof EntityWitherGirlVoid)) && (!(entity instanceof EntityFriendlyWither)) && (!(entity instanceof EntityWitherGirlPink))) {
            if ((entity instanceof EntityPlayer))
            {
              if (!((EntityPlayer)entity).capabilities.disableDamage && !((EntityPlayer)entity).capabilities.allowFlying)
              {
                entity.attackEntityFrom(DamageSource.inWall, 1.0F);
                double d2 = this.posX - entity.posX;
                double d3 = (this.posY + 2) - entity.posY;
                double d4 = this.posZ - entity.posZ;
                double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                entity.addVelocity(d2 / d5 * 1.5D, d3 / d5 * 1.5D, d4 / d5 * 1.5D);
              }
            }
            else
            {
              if (((entity instanceof EntityDragon)) || ((entity instanceof EntityDragonPart))) {
                entity.attackEntityFrom(DamageSource.causePlayerDamage(this.worldObj.getClosestPlayerToEntity(this, -1.0D)), 5.0F);
              }
              entity.attackEntityFrom(DamageSource.inWall, 1.0F);
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
      for (int i = 1; i < 3; i++) {
        if (this.ticksExisted >= this.field_82223_h[(i - 1)])
        {
          this.field_82223_h[(i - 1)] = (this.ticksExisted + this.rand.nextInt(2));
          if ((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) || (this.worldObj.getDifficulty() == EnumDifficulty.HARD))
          {
            int k2 = i - 1;
            int l2 = this.field_82224_i[(i - 1)];
            this.field_82224_i[k2] = (this.field_82224_i[(i - 1)] + 1);
            if (l2 > 15)
            {
              float f = 20.0F;
              float f1 = 10.0F;
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
            if ((entity != null) && (entity.isEntityAlive()) && (getDistanceSqToEntity(entity) <= 10000.0D))
            {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.field_82223_h[(i - 1)] = (this.ticksExisted + 40);
              this.field_82224_i[(i - 1)] = 0;
            }
            else
            {
              func_82211_c(i, 0);
            }
          }
          else
          {
            List list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(100.0D, 100.0D, 100.0D), Predicates.and(attackEntitySelector, IEntitySelector.NOT_SPECTATING));
            for (int k1 = 0; (k1 < 64) && (!list.isEmpty()); k1++)
            {
              EntityLivingBase entitylivingbase = (EntityLivingBase)list.get(this.rand.nextInt(list.size()));
              if ((entitylivingbase != this) && (entitylivingbase.isEntityAlive()))
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
    setInvulTime(220);
    setHealth(getMaxHealth() / 3.0F);
  }
  
  public void setInWeb() {}
  
  public int getTotalArmorValue()
  {
    return 1;
  }
  
  private double func_82214_u(int p_82214_1_)
  {
    if (p_82214_1_ <= 0) {
      return this.posX;
    }
    float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 1.3D;
  }
  
  private double func_82208_v(int p_82208_1_)
  {
    return p_82208_1_ <= 0 ? this.posY + 3.0D : this.posY + 2.2D;
  }
  
  private double func_82213_w(int p_82213_1_)
  {
    if (p_82213_1_ <= 0) {
      return this.posZ;
    }
    float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 1.3D;
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
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight(), p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
  {
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityAirSkull entitywitherskull = new EntityAirSkull(this.worldObj, this, d6, d7, d8);
    entitywitherskull.posY = d4;
    entitywitherskull.posX = d3;
    entitywitherskull.posZ = d5;
    this.worldObj.spawnEntityInWorld(entitywitherskull);
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
  {
    if (!canConjoin()) {
      launchWitherSkullToEntity(0, p_82196_1_);
    }
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (isEntityInvulnerable(source)) {
      return false;
    }
    if ((source != DamageSource.anvil) && (source != DamageSource.fallingBlock) && (source != DamageSource.fall) && (!(source.getEntity() instanceof EntityAirWither)))
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
      int j = 4 + this.rand.nextInt(17 + p_70628_2_);

      for (int k = 0; k < j + 1 + p_70628_2_; ++k)
      {
    	  this.dropItem(Items.feather, 1);
      }
      for (int k = 0; k < j + 4 + p_70628_2_; ++k)
      {
          this.dropItem(Items.paper, 1);
      }
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
            entityplayer.triggerAchievement(MoWithersAchievments.achievementKillAirWither);
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
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.75D);
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

  protected void func_180433_a(double p_180433_1_, boolean p_180433_3_, Block p_180433_4_, BlockPos p_180433_5_) {}

  /**
   * Moves the entity based on the specified heading.  Args: strafe, forward
   */
  public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
  {
      if (this.isInWater())
      {
          this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
          this.moveEntity(this.motionX, this.motionY, this.motionZ);
          this.motionX *= 0.800000011920929D;
          this.motionY *= 0.800000011920929D;
          this.motionZ *= 0.800000011920929D;
      }
      else if (this.isInLava())
      {
          this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
          this.moveEntity(this.motionX, this.motionY, this.motionZ);
          this.motionX *= 0.5D;
          this.motionY *= 0.5D;
          this.motionZ *= 0.5D;
      }
      else
      {
          float f2 = 0.91F;

          if (this.onGround)
          {
              f2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
          }

          float f3 = 0.16277136F / (f2 * f2 * f2);
          this.moveFlying(p_70612_1_, p_70612_2_, this.onGround ? 0.1F * f3 : 0.02F);
          f2 = 0.91F;

          if (this.onGround)
          {
              f2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
          }

          this.moveEntity(this.motionX, this.motionY, this.motionZ);
          this.motionX *= (double)f2;
          this.motionY *= (double)f2;
          this.motionZ *= (double)f2;
          if (this.getHealth() <= 0F)
        	  this.motionY += 1.2F;
      }

      this.prevLimbSwingAmount = this.limbSwingAmount;
      double d1 = this.posX - this.prevPosX;
      double d0 = this.posZ - this.prevPosZ;
      float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

      if (f4 > 1.0F)
      {
          f4 = 1.0F;
      }

      this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
      this.limbSwing += this.limbSwingAmount;
  }

  /**
   * returns true if this entity is by a ladder, false otherwise
   */
  public boolean isOnLadder()
  {
      return false;
  }
  
  class AIRandomFly
    extends EntityAIBase
  {
    private EntityAirWither field_179454_a = EntityAirWither.this;
    
    public AIRandomFly()
    {
      setMutexBits(1);
    }
    
    public boolean shouldExecute()
    {
      EntityMoveHelper entitymovehelper = this.field_179454_a.getMoveHelper();
      if ((!entitymovehelper.isUpdating()) || (this.field_179454_a.getAttackTarget() == null)) {
        return true;
      }
      double d0 = entitymovehelper.func_179917_d() - this.field_179454_a.posX;
      double d1 = entitymovehelper.func_179919_e() - this.field_179454_a.posY;
      double d2 = entitymovehelper.func_179918_f() - this.field_179454_a.posZ;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return (d3 < 1.0D) || (d3 > 3600.0D);
    }
    
    public boolean continueExecuting()
    {
      return false;
    }
    
    public void startExecuting()
    {
        Random random = this.field_179454_a.getRNG();
        double d0 = this.field_179454_a.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d1 = this.field_179454_a.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = this.field_179454_a.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        this.field_179454_a.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
    }
  }
  
  class AirWitherMoveHelper extends EntityMoveHelper
  {
    private EntityAirWither airwither = EntityAirWither.this;
    private int field_179928_h;
    
    public AirWitherMoveHelper()
    {
      super(EntityAirWither.this);
    }
    
    public void onUpdateMoveHelper()
    {
      if (this.update)
      {
        double d0 = this.posX - this.airwither.posX;
        double d1 = this.posY - this.airwither.posY;
        double d2 = this.posZ - this.airwither.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (this.field_179928_h-- <= 0)
        {
          this.field_179928_h += this.airwither.getRNG().nextInt(10) + 30;
          d3 = MathHelper.sqrt_double(d3);
          if (func_179926_b(this.posX, this.posY, this.posZ, d3))
          {
            this.airwither.motionX += d0 / d3 * 0.75D;
            this.airwither.motionY += d0 / d3 * 0.75D;
            this.airwither.motionZ += d2 / d3 * 0.75D;
            this.airwither.getLookHelper().setLookPosition(this.posX, this.posY, this.posZ, 180F, 60F);
          }
          else
          {
            this.update = false;
          }
        }
      }
    }
    
    private boolean func_179926_b(double p_179926_1_, double p_179926_3_, double p_179926_5_, double p_179926_7_)
    {
      return this.airwither.getAttackTarget() == null;
    }
  }
}
