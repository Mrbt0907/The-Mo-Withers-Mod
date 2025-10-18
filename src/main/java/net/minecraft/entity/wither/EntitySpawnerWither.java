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
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.witherskulls.EntityFireSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpawnerWither
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
  private String mobID = "WitherCultist";
  private int spawnDelay = 20;
  private int minSpawnDelay = 100;
  private int maxSpawnDelay = 200;
  private int spawnCount = 4;
  /** The rotation of the mob inside the mob spawner */
  public double mobRotation;
  /** the previous rotation of the mob inside the mob spawner */
  public double prevMobRotation;
  private Entity cachedEntity;
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
  
  public EntitySpawnerWither(World worldIn)
  {
    super(worldIn);
    this.setCurrentItemOrArmor(0, new ItemStack(Items.spawn_egg, 1 , EntityList.getIDFromString(mobID)));
    setHealth(getMaxHealth());
    setSize(0.9F, 3.5F);
    this.isImmuneToFire = true;
    ((PathNavigateGround)getNavigator()).func_179693_d(true);
    this.tasks.addTask(0, new EntityAISwimming(this));
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
  
  public String getEntityNameToSpawn()
  {
  	if (this.mobID == null)
    {
  	  	this.mobID = "WitherCultist";
    }
	  
      return this.mobID;
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Invul", getInvulTime());
    tagCompound.setString("EntityId", this.getEntityNameToSpawn());
    tagCompound.setShort("Delay", (short)this.spawnDelay);
    tagCompound.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
    tagCompound.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
    tagCompound.setShort("SpawnCount", (short)this.spawnCount);
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setInvulTime(tagCompund.getInteger("Invul"));
    this.mobID = tagCompund.getString("EntityId");
    this.spawnDelay = tagCompund.getShort("Delay");
    if (tagCompund.hasKey("MinSpawnDelay", 99))
    {
        this.minSpawnDelay = tagCompund.getShort("MinSpawnDelay");
        this.maxSpawnDelay = tagCompund.getShort("MaxSpawnDelay");
        this.spawnCount = tagCompund.getShort("SpawnCount");
    }
    if (this.worldObj != null)
    {
        this.cachedEntity = null;
    }
  }
  
  @SideOnly(Side.CLIENT)
  public Entity func_180612_a(World worldIn)
  {
      return EntityList.createEntityByName(EntityList.getStringFromID(this.getHeldItem().getItemDamage()), worldIn);
  }
  
  private Entity func_180613_a(Entity p_180613_1_, boolean p_180613_2_)
  {
	  if (p_180613_1_ instanceof EntityLivingBase && p_180613_1_.worldObj != null && p_180613_2_)
      {
          ((EntityLiving)p_180613_1_).func_180482_a(p_180613_1_.worldObj.getDifficultyForLocation(new BlockPos(p_180613_1_)), (IEntityLivingData)null);
          p_180613_1_.worldObj.spawnEntityInWorld(p_180613_1_);
      }

      return p_180613_1_;
  }
  
  protected String getLivingSound()
  {
    return "mob.wither.idle";
  }
  
  protected String getHurtSound()
  {
    return "mob.wither.hurt";
  }
  
  protected String getDeathSound()
  {
    return "mob.wither.death";
  }
  
  public void onLivingUpdate()
  {
      this.motionY *= 0.6000000238418579D;
      double d1;
      double d3;
      double d5;

      if (!this.worldObj.isRemote && this.getWatchedTargetId(0) > 0)
      {
          Entity entity = this.worldObj.getEntityByID(this.getWatchedTargetId(0));

          if (entity != null)
          {
              if (this.posY < entity.posY || !this.isArmored() && this.posY < entity.posY + 5.0D)
              {
                  if (this.motionY < 0.0D)
                  {
                      this.motionY = 0.0D;
                  }

                  this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
              }

              double d0 = entity.posX - this.posX;
              d1 = entity.posZ - this.posZ;
              d3 = d0 * d0 + d1 * d1;

              if (d3 > 9.0D)
              {
                  d5 = (double)MathHelper.sqrt_double(d3);
                  this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
                  this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
              }
          }
      }

      if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D)
      {
          this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * (180F / (float)Math.PI) - 90.0F;
      }

      super.onLivingUpdate();
      int i;

      for (i = 0; i < 2; ++i)
      {
          this.field_82218_g[i] = this.field_82221_e[i];
          this.field_82217_f[i] = this.field_82220_d[i];
      }

      int j;

      for (i = 0; i < 2; ++i)
      {
          j = this.getWatchedTargetId(i + 1);
          Entity entity1 = null;

          if (j > 0)
          {
              entity1 = this.worldObj.getEntityByID(j);
          }

          if (entity1 != null)
          {
              d1 = this.func_82214_u(i + 1);
              d3 = this.func_82208_v(i + 1);
              d5 = this.func_82213_w(i + 1);
              double d6 = entity1.posX - d1;
              double d7 = entity1.posY + (double)entity1.getEyeHeight() - d3;
              double d8 = entity1.posZ - d5;
              double d9 = (double)MathHelper.sqrt_double(d6 * d6 + d8 * d8);
              float f = (float)(Math.atan2(d8, d6) * 180.0D / Math.PI) - 90.0F;
              float f1 = (float)(-(Math.atan2(d7, d9) * 180.0D / Math.PI));
              this.field_82220_d[i] = this.func_82204_b(this.field_82220_d[i], f1, 40.0F);
              this.field_82221_e[i] = this.func_82204_b(this.field_82221_e[i], f, 10.0F);
          }
          else
          {
              this.field_82221_e[i] = this.func_82204_b(this.field_82221_e[i], this.renderYawOffset, 10.0F);
          }
      }
      
      boolean flag = this.isArmored();

      for (j = 0; j < 3; ++j)
      {
          double d10 = this.func_82214_u(j);
          double d2 = this.func_82208_v(j);
          double d4 = this.func_82213_w(j);
          this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);

          if (flag && this.worldObj.rand.nextInt(4) == 0)
          {
              this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
          }
      }

      if (this.getInvulTime() > 0)
      {
          for (j = 0; j < 3; ++j)
          {
              this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + (double)(this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
          }
      }
    
      if (this.spawnDelay > 0)
    --this.spawnDelay;
    
    BlockPos blockpos = this.getPosition().up(4);

    if (this.worldObj.isRemote)
    {
        double d0 = (double)(this.posX - 0.5F + this.rand.nextFloat());
        double d11 = (MathHelper.cos(this.ticksExisted * 0.02F) * 0.1F) + (double)(this.posY + 4F + this.rand.nextFloat());
        double d2 = (double)(this.posZ - 0.5F + this.rand.nextFloat());
        for (j = 0; j < 3; ++j)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d11, d2, 0.0D, 0.0D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.FLAME, d0, d11, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        this.prevMobRotation = this.mobRotation;
        ++this.mobRotation;
    }
    else
    {
        boolean flag1 = false;
        
        if (this.spawnDelay <= 0)
        {
        	if (this.mobID == null)
            {
          	  	this.mobID = "WitherCultist";
            }
        	
            for (i = 0; i < this.spawnCount; ++i)
            {
                Entity entity = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.worldObj);

                double d2 = (double)blockpos.getX() + this.rand.nextInt(12) - 6;
                double d31 = (double)(blockpos.getY() + this.rand.nextInt(12) - 6);
                double d4 = (double)blockpos.getZ() + this.rand.nextInt(12) - 6;
                EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving)entity : null;
                entity.setLocationAndAngles(d2, d31, d4, this.rand.nextFloat() * 360.0F, 0.0F);
                if (entityliving == null || entityliving.getCanSpawnHere() && entityliving.handleLavaMovement())
                {
              	  	entityliving.func_180482_a(this.worldObj.getDifficultyForLocation(getPosition()), (IEntityLivingData)null);
                    this.worldObj.playAuxSFX(2004, blockpos, 0);

                    if (entityliving != null)
                    {
                        entityliving.spawnExplosionParticle();
                        worldObj.spawnEntityInWorld(entityliving);
                    }

                    flag1 = true;
                }
            }

            if (flag1)
            {
            	this.spawnDelay = this.minSpawnDelay + rand.nextInt(this.maxSpawnDelay - this.minSpawnDelay);
            }
        }
    }
  }
  
  public boolean interact(EntityPlayer player)
  {
    ItemStack itemstack = player.getHeldItem();
    if ((itemstack != null) && (itemstack.getItem() == Items.spawn_egg))
    {
        if (!this.worldObj.isRemote)
        {
        	this.spawnDelay = 1;
        	mobID = EntityList.getStringFromID(itemstack.getMetadata());
            this.worldObj.newExplosion(this, this.posX, this.posY + 4.5D, this.posZ, 0.0F, false, false);
            this.setCurrentItemOrArmor(0, itemstack);
            
            if (!player.capabilities.isCreativeMode)
            {
                --itemstack.stackSize;
            }

            if (itemstack.stackSize <= 0)
            {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
        }
        player.swingItem();
        
        return true;
    }
    
    return super.interact(player);
  }
  
  private void resetTimer()
  {
      if (this.maxSpawnDelay <= this.minSpawnDelay)
      {
          this.spawnDelay = this.minSpawnDelay;
      }
      else
      {
          int i = this.maxSpawnDelay - this.minSpawnDelay;
          this.spawnDelay = this.minSpawnDelay + this.worldObj.rand.nextInt(i);
      }
  }
  
  protected void updateAITasks()
  {
      int i;

      if (this.getInvulTime() > 0)
      {
          i = this.getInvulTime() - 1;

          if (i <= 0)
          {
              this.worldObj.newExplosion(this, this.posX, this.posY + (double)this.getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
              this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
          }

          this.setInvulTime(i);

          if (this.ticksExisted % 10 == 0)
          {
              this.heal(10.0F);
          }
      }
      else
      {
          super.updateAITasks();
          int i1;

          for (i = 1; i < 3; ++i)
          {
              if (this.ticksExisted >= this.field_82223_h[i - 1])
              {
                  this.field_82223_h[i - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);

                  if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD)
                  {
                      int k2 = i - 1;
                      int l2 = this.field_82224_i[i - 1];
                      this.field_82224_i[k2] = this.field_82224_i[i - 1] + 1;

                      if (l2 > 15)
                      {
                          float f = 10.0F;
                          float f1 = 5.0F;
                          double d0 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - (double)f, this.posX + (double)f);
                          double d1 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - (double)f1, this.posY + (double)f1);
                          double d2 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - (double)f, this.posZ + (double)f);
                          this.launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
                          this.field_82224_i[i - 1] = 0;
                      }
                  }

                  i1 = this.getWatchedTargetId(i);

                  if (i1 > 0)
                  {
                      Entity entity = this.worldObj.getEntityByID(i1);

                      if (entity != null && entity.isEntityAlive() && this.getDistanceSqToEntity(entity) <= 900.0D && this.canEntityBeSeen(entity))
                      {
                          this.launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
                          this.field_82223_h[i - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
                          this.field_82224_i[i - 1] = 0;
                      }
                      else
                      {
                          this.func_82211_c(i, 0);
                      }
                  }
                  else
                  {
                      List list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().expand(20.0D, 8.0D, 20.0D), Predicates.and(attackEntitySelector, IEntitySelector.NOT_SPECTATING));

                      for (int k1 = 0; k1 < 10 && !list.isEmpty(); ++k1)
                      {
                          EntityLivingBase entitylivingbase = (EntityLivingBase)list.get(this.rand.nextInt(list.size()));

                          if (entitylivingbase != this && entitylivingbase.isEntityAlive() && this.canEntityBeSeen(entitylivingbase))
                          {
                              if (entitylivingbase instanceof EntityPlayer)
                              {
                                  if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage)
                                  {
                                      this.func_82211_c(i, entitylivingbase.getEntityId());
                                  }
                              }
                              else
                              {
                                  this.func_82211_c(i, entitylivingbase.getEntityId());
                              }

                              break;
                          }

                          list.remove(entitylivingbase);
                      }
                  }
              }
          }

          if (this.getAttackTarget() != null)
          {
              this.func_82211_c(0, this.getAttackTarget().getEntityId());
          }
          else
          {
              this.func_82211_c(0, 0);
          }

          if (this.blockBreakCounter > 0)
          {
              --this.blockBreakCounter;

              if (this.blockBreakCounter == 0 && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
              {
                  i = MathHelper.floor_double(this.posY);
                  i1 = MathHelper.floor_double(this.posX);
                  int j1 = MathHelper.floor_double(this.posZ);
                  boolean flag = false;

                  for (int l1 = -1; l1 <= 1; ++l1)
                  {
                      for (int i2 = -1; i2 <= 1; ++i2)
                      {
                          for (int j = 0; j <= 3; ++j)
                          {
                              int j2 = i1 + l1;
                              int k = i + j;
                              int l = j1 + i2;
                              Block block = this.worldObj.getBlockState(new BlockPos(j2, k, l)).getBlock();

                              if (!block.isAir(worldObj, new BlockPos(j2, k, l)) && this.canEntityDestroy(block, worldObj, new BlockPos(j2, k, l), this))
                              {
                                  flag = this.worldObj.destroyBlock(new BlockPos(j2, k, l), true) || flag;
                              }
                          }
                      }
                  }

                  if (flag)
                  {
                      this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1012, new BlockPos(this), 0);
                  }
              }
          }

          if (this.ticksExisted % 20 == 0)
          {
              this.heal(1.0F);
          }
      }
  }
  
  public boolean canEntityDestroy(Block block, IBlockAccess world, BlockPos pos, Entity entity)
  {
    return (block != Blocks.mob_spawner) && (block != Blocks.barrier) && (block != Blocks.bedrock) && (block != Blocks.end_portal) && (block != Blocks.end_portal_frame) && (block != Blocks.command_block);
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
      this.launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + (double)p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, p_82216_1_ == 0 && this.rand.nextFloat() < 0.001F);
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
  {
      this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, new BlockPos(this), 0);
      double d3 = this.func_82214_u(p_82209_1_);
      double d4 = this.func_82208_v(p_82209_1_);
      double d5 = this.func_82213_w(p_82209_1_);
      double d6 = p_82209_2_ - d3;
      double d7 = p_82209_4_ - d4;
      double d8 = p_82209_6_ - d5;
      EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.worldObj, this, d6, d7, d8);

      if (p_82209_8_)
      {
          entitywitherskull.setInvulnerable(true);
      }

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
      if (this.isEntityInvulnerable(source))
      {
          return false;
      }
      else if (source != DamageSource.drown && !(source.getEntity() instanceof EntitySpawnerWither))
      {
          if (this.getInvulTime() > 0 && source != DamageSource.outOfWorld)
          {
              return false;
          }
          else
          {
              Entity entity;

              if (this.isArmored())
              {
                  entity = source.getSourceOfDamage();

                  if (entity instanceof EntityArrow)
                  {
                      return false;
                  }
              }

              entity = source.getEntity();

              if (entity != null && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() == this.getCreatureAttribute())
              {
                  return false;
              }
              else
              {
                  if (this.blockBreakCounter <= 0)
                  {
                      this.blockBreakCounter = 20;
                  }

                  for (int i = 0; i < this.field_82224_i.length; ++i)
                  {
                      this.field_82224_i[i] += 3;
                  }

                  return super.attackEntityFrom(source, amount);
              }
          }
      }
      else
      {
          return false;
      }
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
	  this.entityDropItem(new ItemStack(Items.spawn_egg, 1 , EntityList.getIDFromString(mobID)), 0F);
	  
      EntityItem entityitem = this.dropItem(Items.nether_star, 1);

      if (entityitem != null)
      {
          entityitem.setNoDespawn();
      }

      if (!this.worldObj.isRemote)
      {
          Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)).iterator();

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
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(900.0D);
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
