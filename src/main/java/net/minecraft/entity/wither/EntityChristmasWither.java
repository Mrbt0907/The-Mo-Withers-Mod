package net.minecraft.entity.wither;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.MoWithers.DamageSourceExtra;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
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
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.witherskulls.EntityLoveSkull;
import net.minecraft.entity.witherskulls.EntityShotPresent;
import net.minecraft.entity.witherskulls.EntitySpookySkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
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

public class EntityChristmasWither
  extends EntityGolem
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
      return ((p_180027_1_ instanceof EntityMob)) && (((EntityMob)p_180027_1_).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD);
    }
    
    public boolean apply(Object p_apply_1_)
    {
      return func_180027_a((Entity)p_apply_1_);
    }
  };
  
  public EntityChristmasWither(World worldIn)
  {
    super(worldIn);
    setHealth(getMaxHealth());
    setSize(0.9F, 3.5F);
    this.isImmuneToFire = true;
    ((PathNavigateGround)getNavigator()).func_179693_d(true);
    this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 40, 100.0F));
    this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(0, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
    this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
    this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 0, false, false, IMob.mobSelector));
    this.experienceValue = 45000;
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
    return "mowithers:JungleWitherIdle";
  }
  
  protected String getHurtSound()
  {
    return "mowithers:JungleWitherHurt";
  }
  
  protected String getDeathSound()
  {
    return "mowithers:JungleWitherDeath";
  }
  
  protected float getSoundPitch()
  {
    return (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.5F;
  }
  
  public void onLivingUpdate()
  {
      List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(64.0D, 64.0D, 64.0D));
      if ((list111 != null) && (!list111.isEmpty())) {
        for (int i111 = 0; i111 < list111.size(); i111++)
        {
          Entity entity11 = (Entity)list111.get(i111);
          if (entity11 != null && entity11 instanceof EntitySnowman && entity11.getDistanceSqToEntity(this) >= 512D)
          {
            ((EntitySnowman)entity11).getMoveHelper().setMoveTo(this.posX, this.posY, this.posZ, 2D);
            ((EntitySnowman)entity11).getLookHelper().setLookPositionWithEntity(this, 10F, 30F);
            ((EntitySnowman)entity11).setAttackTarget(null);
            
            if (entity11.isCollidedHorizontally && this.ticksExisted % 10 == 0)
            	((EntitySnowman)entity11).motionY = 0.42F;
          }
        }
      }
      
    if ((!this.worldObj.isRemote) && (getWatchedTargetId(0) > 0))
    {
      Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
      if (entity != null)
      {
    	  this.fallDistance = 0.0F;
    	  for (int i = 0; i < 8; i++)
          {
    		  if (this.rand.nextInt(entity instanceof IBossDisplayData ? 20 : 200) == 0)
    			  launchWitherSkullToEntity(0 + rand.nextInt(2), (EntityLivingBase)entity);
    		  int in = MathHelper.floor_double(this.posX - 1.5D + this.rand.nextDouble() * 3.0D);
    		  int j = MathHelper.floor_double(this.posY + 3.0D - this.rand.nextDouble() * 10.0D);
    		  int k = MathHelper.floor_double(this.posZ - 1.5D + this.rand.nextDouble() * 3.0D);
    		  BlockPos blockpos = new BlockPos(in, j, k);
    		  IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
    		  Block block = iblockstate.getBlock();
    
   	         if ((!this.worldObj.isRemote) && (block != Blocks.air))
   	         {
   	          this.motionY *= (this.posY - entity.posY) * 0.005D;
   	           
   	          if (this.motionY < 0.0D) {
   	            this.motionY = 0.0D;
   	          }
   	          this.motionY += (0.6D - this.motionY) * 0.6D;
            }
          }
    	  
        double d0 = entity.posX - this.posX;
        double d1 = entity.posZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1;
        if (d3 > 9.0D)
        {
          double d5 = MathHelper.sqrt_double(d3);
          this.motionX += (d0 / d5 * 0.6D - this.motionX) * 0.6D;
          this.motionZ += (d1 / d5 * 0.6D - this.motionZ) * 0.6D;
        }
      }
    }
    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0D) {
      this.renderYawOffset = this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * (180F / (float)Math.PI) - 90.0F;
    }
    super.onLivingUpdate();
    
    if (this.getHealth() < this.getMaxHealth() / 5 && this.rand.nextInt(40) == 0)
    {
    	this.setInvulTime(20);
        List list11 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(32.0D, 32.0D, 32.0D));
        if ((list11 != null) && (!list11.isEmpty())) {
          for (int i111 = 0; i111 < list11.size(); i111++)
          {
            Entity entity11 = (Entity)list11.get(i111);
            if (entity11 != null && entity11 instanceof EntityPlayer && this.getAttackTarget() != entity11)
            {
              this.setAttackTarget((EntityPlayer)entity11);
              this.func_82211_c(0, entity11.getEntityId());
            }
          }
        }
    }
    
	if (Calendar.getInstance().get(2) + 1 == 12 && Calendar.getInstance().get(5) >= 12 && Calendar.getInstance().get(5) <= 31)
	{
        WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
        WorldInfo worldinfo = worldserver.getWorldInfo();
        worldinfo.setRainTime(10);
        worldinfo.setRaining(true);
	}
    
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
      
      this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
      this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
      if ((flag) || (this.worldObj.rand.nextInt(5) == 0)) {
          this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.7D, 0.0D, new int[] { 80 });
        this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    if (getInvulTime() > 0) {
      for (int j = 0; j < 15; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_ANGRY, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
  }
  
  protected void updateAITasks()
  {
    if (getInvulTime() > 0)
    {
      int i = getInvulTime() - 1;
      if (i <= 0) {
          this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 20.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
          this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
          if (this.onGround)
        	  this.jump();
      }
      setInvulTime(i);
      if (this.ticksExisted % 10 == 0) {
        heal(1000.0F);
      }
    }
    else
    {
      super.updateAITasks();
      if (this.blockBreakCounter <= 0) {
          this.blockBreakCounter = 1;
        }
      if (!this.worldObj.isRemote && this.rand.nextInt(3) == 0 && this.ticksExisted % 20 == 0)
      {
      	if (Calendar.getInstance().get(2) + 1 == 12 && Calendar.getInstance().get(5) >= 12 && Calendar.getInstance().get(5) <= 31)
      	{
              WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
              WorldInfo worldinfo = worldserver.getWorldInfo();
              worldinfo.setRainTime(10);
              worldinfo.setRaining(true);
      	    for (int i = 0; i < 16; i++)
      	    {
      	    	EntitySnowman blazeminion = new EntitySnowman(this.worldObj);
      	    	blazeminion.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(this)), (IEntityLivingData)null);
      	    	blazeminion.copyLocationAndAnglesFrom(this);
      	    	blazeminion.posY = this.posY + 5D;
      	        double d1 = blazeminion.posX - this.posX;
      	        double d0;
      	        for (d0 = blazeminion.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()))
      	        {
      	            d1 = (Math.random() - Math.random());
      	        }
      	        blazeminion.knockBack(this, 2F, d1, d0);
      	        blazeminion.motionY = 0.6D;
      	    	this.worldObj.spawnEntityInWorld(blazeminion);
      	    }
      	}
      	else
      	{
      		EntitySnowman blazeminion = new EntitySnowman(this.worldObj);
          	blazeminion.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(this)), (IEntityLivingData)null);
          	blazeminion.copyLocationAndAnglesFrom(this);
          	blazeminion.posY = this.posY + 5D;
              double d1 = blazeminion.posX - this.posX;
              double d0;
              for (d0 = blazeminion.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
              {
                  d1 = (Math.random() - Math.random()) * 0.01D;
              }
              blazeminion.knockBack(this, 2F, d1, d0);
      		this.worldObj.spawnEntityInWorld(blazeminion);
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
            if ((entity != null) && (entity.isEntityAlive()) && (getDistanceSqToEntity(entity) <= 10000.0D) && (canEntityBeSeen(entity)))
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
            List list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(100.0D, 100.0D, 100.0D), Predicates.and(attackEntitySelector, IMob.mobSelector, IEntitySelector.NOT_SPECTATING));
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
          for (int l1 = -2; l1 <= 2; l1++) {
            for (int i2 = -2; i2 <= 2; i2++) {
              for (int j = 0; j <= 5; j++)
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
      if (this.ticksExisted % 1 == 0) {
    	  heal(10.0F);
      }
    }
  }
  
  public boolean canEntityDestroy(Block block, IBlockAccess world, BlockPos pos, Entity entity)
  {
    return (block != Blocks.farmland) && (block != Blocks.mycelium) && (block != Blocks.grass) && (block != Blocks.dirt) && (block != Blocks.barrier) && (block != Blocks.bedrock) && (block != Blocks.end_portal) && (block != Blocks.end_portal_frame) && (block != Blocks.command_block);
  }
  
  public void func_82206_m()
  {
    setInvulTime(220);
    setHealth(getMaxHealth() / 3.0F);
  }
  
  public void setInWeb() {}
  
  public int getTotalArmorValue()
  {
    return 24;
  }
  
  private double func_82214_u(int p_82214_1_)
  {
    if (p_82214_1_ <= 0) {
      return this.posX;
    }
    float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 1.2D;
  }
  
  private double func_82208_v(int p_82208_1_)
  {
    return p_82208_1_ <= 0 ? this.posY + 3.0D : this.posY + 2.5D;
  }
  
  private double func_82213_w(int p_82213_1_)
  {
    if (p_82213_1_ <= 0) {
      return this.posZ;
    }
    float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 1.2D;
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
	  p_82216_2_.hurtResistantTime = 0;
	  p_82216_2_.attackEntityFrom(DamageSourceExtra.outOfWorld, 1F + p_82216_2_.height + p_82216_2_.width);
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
  {
      switch (this.rand.nextInt(5))
      {
          case 0:
        	  this.playSound("note.harp", this.getSoundVolume(), 1.5F);
          case 1:
        	  this.playSound("note.bd", this.getSoundVolume(), 1.5F);
          case 2:
        	  this.playSound("note.snare", this.getSoundVolume(), 1.5F);
          case 3:
        	  this.playSound("note.hat", this.getSoundVolume(), 1.5F);
          case 4:
        	  this.playSound("note.bassattack", this.getSoundVolume(), 1.5F);
      }
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityShotPresent entitywitherskull = new EntityShotPresent(this.worldObj, this, d6, d7, d8);
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
    if ((source != DamageSource.fall) && (source != DamageSource.anvil) && (source != DamageSource.fallingBlock) && (source != DamageSource.drown) && (source != DamageSource.inWall) && (!(source.getEntity() instanceof EntityChristmasWither)))
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
      if ((entity != null) && ((entity instanceof EntityLivingBase)) && source.getSourceOfDamage() instanceof EntitySnowball) {
    	  this.heal(1F);
        return false;
      }
      if (this.blockBreakCounter <= 0) {
        this.blockBreakCounter = 1;
      }
      for (int i = 0; i < this.field_82224_i.length; i++) {
        this.field_82224_i[i] += 3;
      }
      
      if (!this.worldObj.isRemote && this.rand.nextInt(2) == 0)
      {
		EntitySnowman blazeminion = new EntitySnowman(this.worldObj);
    	blazeminion.func_180482_a(this.worldObj.getDifficultyForLocation(new BlockPos(this)), (IEntityLivingData)null);
    	blazeminion.copyLocationAndAnglesFrom(this);
    	blazeminion.posY = this.posY + 4D;
        double d1 = blazeminion.posX - this.posX;
        double d0;
        for (d0 = blazeminion.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
        {
            d1 = (Math.random() - Math.random()) * 0.01D;
        }
        blazeminion.knockBack(this, 2F, d1, d0);
		this.worldObj.spawnEntityInWorld(blazeminion);
    }
      return super.attackEntityFrom(source, amount);
    }
    return false;
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
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
      for (int j = 0; j < 16;)
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
          EntityItem var3 = new EntityItem(this.worldObj, this.posX + ((this.rand.nextDouble() - 0.5D) * 2D), this.posY + 6.0D + ((this.rand.nextDouble() - 0.5D) * 2D), this.posZ + ((this.rand.nextDouble() - 0.5D) * 2D), new ItemStack(it, 1, 0));
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
      for (int j = 0; j < 16;)
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
          EntityItem var3 = new EntityItem(this.worldObj, this.posX + ((this.rand.nextDouble() - 0.5D) * 2D), this.posY + 8.0D + ((this.rand.nextDouble() - 0.5D) * 2D), this.posZ + ((this.rand.nextDouble() - 0.5D) * 2D), new ItemStack(Item.getItemFromBlock(bl), 1, 0));
          this.worldObj.spawnEntityInWorld(var3);
        }
      }
    
    EntityItem entityitem = dropItem(Items.nether_star, 1);
    if (entityitem != null) {
      entityitem.setNoDespawn();
    }
    Calendar calendar = Calendar.getInstance();
    if (!this.worldObj.isRemote)
    {
      Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)).iterator();
      while (iterator.hasNext())
      {
        EntityPlayer entityplayer = (EntityPlayer)iterator.next();
        if ((calendar.get(2) + 1 == 12) && (calendar.get(5) == 26)) {
          entityplayer.heal(5.0F);
        }
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
  
  protected String getFallSoundString(int damageValue)
  {
      return damageValue > 8 ? "mowithers:PowerSmash" : "random.explode";
  }
  
  public void fall(float distance, float damageMultiplier) 
  {
      float[] ret = net.minecraftforge.common.ForgeHooks.onLivingFall(this, distance, damageMultiplier);
      if (ret == null) return;
      distance = ret[0]; damageMultiplier = ret[1];
      int i = MathHelper.ceiling_float_int((distance - 7.0F) * damageMultiplier);

      if (i > 0)
      {
          this.playSound(this.getFallSoundString(i), 10.0F, 1.0F);
          this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, i, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));

          if (i > 8)
          {
        	  this.motionY = 0.5F;
              List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(48.0D, 12.0D, 48.0D));
              if ((list111 != null) && (!list111.isEmpty())) {
                for (int i111 = 0; i111 < list111.size(); i111++)
                {
                  Entity entity = (Entity)list111.get(i111);
                  if ((entity != null) && ((entity instanceof EntityLivingBase))) {
                    entity.attackEntityFrom(DamageSource.generic, 100.0F);
                    entity.motionY += 2F;
                  }
                }
              }
          }
      }
  }
  
  public void addPotionEffect(PotionEffect p_70690_1_) {}
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15000.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
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
    return getHealth() <= getMaxHealth() / 2.0F;
  }
  
  public boolean isEntityUndead()
  {
      return true;
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
