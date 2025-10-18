package net.minecraft.entity.wither;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
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
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.witherskulls.EntityFireSkull;
import net.minecraft.entity.witherskulls.EntityWitherSkullBlaze;
import net.minecraft.entity.witherskulls.EntityWitherSkullEnder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityEnderWither
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
      return (p_180027_1_ instanceof EntityLivingBase && !(p_180027_1_ instanceof EntityEnderman) && !(p_180027_1_ instanceof EntityDragon) && !(p_180027_1_ instanceof EntityEnderWither));
    }
    
    public boolean apply(Object p_apply_1_)
    {
      return func_180027_a((Entity)p_apply_1_);
    }
  };
  
  public EntityEnderWither(World worldIn)
  {
    super(worldIn);
    setHealth(getMaxHealth());
    setSize(1.5F, 5.25F);
    this.isImmuneToFire = true;
    ((PathNavigateGround)getNavigator()).func_179693_d(true);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 20.0F));
    this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
    this.experienceValue = 150;
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
	  this.playSound("mob.wither.idle", 1F, this.getSoundPitch() + 0.3F);
    return "mob.endermen.scream";
  }
  
  protected String getHurtSound()
  {
	  this.playSound("mob.wither.hurt", 1F, this.getSoundPitch() + 0.3F);
    return "mob.endermen.hit";
  }
  
  protected String getDeathSound()
  {
	  this.playSound("mob.wither.death", 1F, this.getSoundPitch() + 0.3F);
    return "mob.endermen.death";
  }
  
  protected boolean teleportRandomly()
  {
      double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 16.0D;
      double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
      double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 16.0D;
      return this.teleportTo(d0, d1, d2);
  }

  /**
   * Teleport the enderman to another entity
   */
  protected boolean teleportToEntity(Entity p_70816_1_)
  {
      Vec3 vec3 = new Vec3(this.posX - p_70816_1_.posX, this.getEntityBoundingBox().minY + (double)(this.height / 2.0F) - p_70816_1_.posY + (double)p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
      vec3 = vec3.normalize();
      double d0 = 16.0D;
      double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.xCoord * d0;
      double d2 = this.posY + (double)(this.rand.nextInt(16) - 8) - vec3.yCoord * d0;
      double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3.zCoord * d0;
      return this.teleportTo(d1, d2, d3);
  }

  /**
   * Teleport the enderman
   */
  protected boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_)
  {
      net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, p_70825_1_, p_70825_3_, p_70825_5_, 0);
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
      double d3 = this.posX;
      double d4 = this.posY;
      double d5 = this.posZ;
      this.posX = event.targetX;
      this.posY = event.targetY;
      this.posZ = event.targetZ;
      boolean flag = false;
      BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);

      if (this.worldObj.isBlockLoaded(blockpos))
      {
          boolean flag1 = false;

          while (!flag1 && blockpos.getY() > 0)
          {
              BlockPos blockpos1 = blockpos.down();
              Block block = this.worldObj.getBlockState(blockpos1).getBlock();

              if (block.getMaterial().blocksMovement())
              {
                  flag1 = true;
              }
              else
              {
                  --this.posY;
                  blockpos = blockpos1;
              }
          }

          if (flag1)
          {
              super.setPositionAndUpdate(this.posX, this.posY, this.posZ);

              if (this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox()))
              {
                  flag = true;
              }
          }
      }

      if (!flag)
      {
          this.setPosition(d3, d4, d5);
          return false;
      }
      else
      {
          short short1 = 128;

          for (int i = 0; i < short1; ++i)
          {
              double d9 = (double)i / ((double)short1 - 1.0D);
              float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
              float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
              float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
              double d6 = d3 + (this.posX - d3) * d9 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
              double d7 = d4 + (this.posY - d4) * d9 + this.rand.nextDouble() * (double)this.height;
              double d8 = d5 + (this.posZ - d5) * d9 + (this.rand.nextDouble() - 0.5D) * (double)this.width * 2.0D;
              this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, d6, d7, d8, (double)f, (double)f1, (double)f2, new int[0]);
          }

          this.worldObj.playSoundEffect(d3, d4, d5, "mob.endermen.portal", 2.0F, 0.75F);
          this.playSound("mob.endermen.portal", 2.0F, 0.75F);
          return true;
      }
  }
  
  public void onLivingUpdate()
  {
      if (this.worldObj.isRemote)
      {
          for (int i = 0; i < 2; ++i)
          {
              this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
          }
      }
    this.motionY *= 0.6D;
    if ((!this.worldObj.isRemote) && (getWatchedTargetId(0) > 0))
    {
      Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
      if (entity != null)
      {
        if ((this.posY < entity.posY) || (!isArmored() && (this.posY < entity.posY + 5.0D + entity.getEyeHeight())))
        {
          if (this.motionY < 0.0D) {
            this.motionY = 0.0D;
          }
          this.motionY += (0.6D - this.motionY) * 0.6D;
        }
        double d0 = entity.posX - this.posX;
        double d1 = entity.posZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1;
        if (d3 > 9.0D)
        {
          double d5 = MathHelper.sqrt_double(d3);
          this.motionX += (d0 / d5 * 0.5D - this.motionX);
          this.motionZ += (d1 / d5 * 0.5D - this.motionZ);
        }
        
        List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(64.0D, 64.0D, 64.0D));
        if ((list111 != null) && (!list111.isEmpty())) {
          for (int i111 = 0; i111 < list111.size(); i111++)
          {
            Entity entity11 = (Entity)list111.get(i111);
            if (entity11 != null && entity11 instanceof EntityEnderman && entity instanceof EntityLivingBase)
            {
              ((EntityEnderman)entity11).setAttackTarget((EntityLivingBase) entity);
              ((EntityEnderman)entity11).setScreaming(true);
            }
          }
        }
      }
    }
    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D) {
      this.rotationYaw = ((float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F);
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
      this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.3D, 0.0D, 0.0D, 0.0D, new int[0]);
      
      this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
      if ((flag) || (this.worldObj.rand.nextInt(10) == 0))
      {
        this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
        this.worldObj.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    if (getInvulTime() > 0) {
      for (int j = 0; j < 15; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.CRIT_MAGIC, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
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
        this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
        this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
      }
      setInvulTime(i);
      if (this.ticksExisted % 10 == 0) {
        heal(15.0F);
      }
    }
    else
    {
      super.updateAITasks();
      if (!this.worldObj.isRemote && this.rand.nextInt(15) == 0 && this.ticksExisted % 20 == 0)
      {
      	EntityEnderman blazeminion = new EntityEnderman(this.worldObj);
      	blazeminion.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(this)), (IEntityLivingData)null);
      	blazeminion.copyLocationAndAnglesFrom(this);
          double d1 = blazeminion.posX - this.posX;
          double d0;
          for (d0 = blazeminion.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
          {
              d1 = (Math.random() - Math.random()) * 0.01D;
          }
          blazeminion.knockBack(this, 2F, d1, d0);
      	this.worldObj.spawnEntityInWorld(blazeminion);
      }
      if (isWet())
        attackEntityFrom(DamageSource.drown, 5.0F);
      
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
            if ((entity != null) && (entity.isEntityAlive()) && (getDistanceSqToEntity(entity) <= 1024.0D) && (canEntityBeSeen(entity)))
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
      if (this.ticksExisted % 10 == 0)
      {
        heal(2.0F);
      }
    }
  }
  
  public boolean canEntityDestroy(Block block, IBlockAccess world, BlockPos pos, Entity entity)
  {
    return (block != Blocks.lava) && (block != Blocks.fire) && (block != Blocks.barrier) && (block != Blocks.bedrock) && (block != Blocks.end_portal) && (block != Blocks.end_portal_frame) && (block != Blocks.command_block);
  }
  
  public void func_82206_m()
  {
    setInvulTime(220);
    setHealth(getMaxHealth() / 3.0F);
  }
  
  public void setInWeb() {}
  
  public int getTotalArmorValue()
  {
    return 4;
  }
  
  private double func_82214_u(int p_82214_1_)
  {
    if (p_82214_1_ <= 0) {
      return this.posX;
    }
    float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 1.95D;
  }
  
  private double func_82208_v(int p_82208_1_)
  {
    return p_82208_1_ <= 0 ? this.posY + 4.5D : this.posY + 3.3D;
  }
  
  private double func_82213_w(int p_82213_1_)
  {
    if (p_82213_1_ <= 0) {
      return this.posZ;
    }
    float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 1.95D;
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
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX + (p_82216_2_.motionX * 5), p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ + (p_82216_2_.motionZ * 5), (p_82216_1_ == 1) && (this.rand.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
  {
	  this.playSound("mob.endermen.portal", 2F, 1F);
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityWitherSkullEnder entitywitherskull = new EntityWitherSkullEnder(this.worldObj, this, d6, d7, d8);
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
	  this.launchWitherSkullToEntity(0, p_82196_1_);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
      if (!this.worldObj.isRemote && this.rand.nextInt(10) == 0)
      {
      	EntityEnderman blazeminion = new EntityEnderman(this.worldObj);
      	blazeminion.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(this)), (IEntityLivingData)null);
      	blazeminion.copyLocationAndAnglesFrom(this);
          double d1 = blazeminion.posX - this.posX;
          double d0;
          for (d0 = blazeminion.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
          {
              d1 = (Math.random() - Math.random()) * 0.01D;
          }
          blazeminion.knockBack(this, 2F, d1, d0);
      	this.worldObj.spawnEntityInWorld(blazeminion);
      }
    if (isEntityInvulnerable(source)) {
      return false;
    }
    else if ((!(source.getEntity() instanceof EntityEnderWither)))
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
      if (source instanceof EntityDamageSourceIndirect && this.isServerWorld())
      {
          for (int i = 0; i < 64; ++i)
          {
              if (this.teleportRandomly())
              {
                  return true;
              }
          }
          return false;
      }
      Entity entity = source.getEntity();
      if ((entity != null) && (!(entity instanceof EntityPlayer)) && ((entity instanceof EntityDragon) ||(entity instanceof EntityEnderman) || (entity instanceof EntityEnderWither))) {
        return false;
      }
      if (this.blockBreakCounter <= 0) {
        this.blockBreakCounter = 20;
      }
      for (int i = 0; i < this.field_82224_i.length; i++) {
        this.field_82224_i[i] += 3;
      }
      if (source.isUnblockable() && this.rand.nextInt(60) != 0 && this.isServerWorld())
      {
          this.teleportRandomly();
      }
      return super.attackEntityFrom(source, amount);
    }
    return false;
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
      int j = 1 + this.rand.nextInt(3 + p_70628_2_);

      for (int k = 0; k < j + p_70628_2_; ++k)
      {
          this.dropItem(Items.ender_eye, 1);
      }
      for (int k = 0; k < j + 1 + p_70628_2_; ++k)
      {
          this.dropItem(Items.ender_pearl, 1);
      }
    EntityItem entityitem = dropItem(Items.nether_star, 1);
    if (entityitem != null) {
      entityitem.setNoDespawn();
    }
    if (!this.worldObj.isRemote)
    {
      Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)).iterator();
      EntityPlayer entityplayer;
      while (iterator.hasNext()) {
        entityplayer = (EntityPlayer)iterator.next();
      }
    }
  }
  
  protected float getSoundPitch()
  {
    return super.getSoundPitch() - 0.4F;
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
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(750.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
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
  
  public void mountEntity(Entity entityIn)
  {
    this.ridingEntity = null;
  }
  
  protected float getSoundVolume()
  {
    return 3.0F;
  }
}
