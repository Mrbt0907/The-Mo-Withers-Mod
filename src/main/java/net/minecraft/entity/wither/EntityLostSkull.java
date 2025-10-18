package net.minecraft.entity.wither;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLostSkull extends EntityMob
{
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
	  
    public EntityLostSkull(World worldIn)
    {
        super(worldIn);
        this.setSize(0.5F, 0.5F);
        this.moveHelper = new EntityLostSkull.LostSkullMoveHelper();
        this.tasks.addTask(3, new EntityLostSkull.AIRandomFly());
        this.tasks.addTask(4, new EntityLostSkull.AILookAround());
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 0, false, false, attackEntitySelector));
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(13, new Byte((byte)0));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
    }
    
    public String getName()
    {
        if (this.hasCustomName())
        {
            return this.getCustomNameTag();
        }
        else
        {
            switch (this.getSkullType())
            {
                case 0:
                	return "Skeleton Skull";
                case 1:
                	return "Wither Skeleton Skull";
                case 2:
                	return "Zombie Head";
                case 3:
                	return "Player Head";
                case 4:
                	return "Creeper Head";
                default:
                	return "Skeleton Skull";
            }
        }
    }
    
    protected void addRandomArmor()
    {
    	this.entityDropItem(new ItemStack(Items.skull, 1, (this.getSkullType() > 4 ? 4 : this.getSkullType())), 0.0F);
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
      return EnumCreatureAttribute.UNDEAD;
    }
    
    public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        if (super.attackEntityAsMob(p_70652_1_))
        {
            if (p_70652_1_ instanceof EntityLivingBase)
            {
            	if (p_70652_1_ instanceof EntityLiving && this.rand.nextInt(5) == 0)
            		((EntityLiving)p_70652_1_).setAttackTarget(null);
            	
            	if (this.getSkullType() == 1)
            		((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.wither.id, 80));
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    public int getSkullType()
    {
        return this.dataWatcher.getWatchableObjectByte(13);
    }

    public void setSkullType(int p_82201_1_)
    {
    	if (p_82201_1_ < 0)
    		p_82201_1_ = 0;
    	if (p_82201_1_ > 4)
    		p_82201_1_ = 4;	
        this.dataWatcher.updateObject(13, Byte.valueOf((byte)p_82201_1_));
        this.isImmuneToFire = p_82201_1_ == 1;
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        switch (this.getSkullType())
        {
            case 0:
            	return "mob.skeleton.say";
            case 1:
            	return "mob.skeleton.say";
            case 2:
            	return "mob.zombie.say";
            case 3:
            	return null;
            case 4:
            	return null;
            default:
            	return "mob.skeleton.say";
        }
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        switch (this.getSkullType())
        {
            case 0:
            	return "mob.skeleton.hurt";
            case 1:
            	return "mob.skeleton.hurt";
            case 2:
            	return "mob.zombie.hurt";
            case 3:
            	return super.getHurtSound();
            case 4:
            	return "mob.creeper.say";
            default:
            	return "mob.skeleton.hurt";
        }
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        switch (this.getSkullType())
        {
            case 0:
            	return "mob.skeleton.death";
            case 1:
            	return "mob.skeleton.death";
            case 2:
            	return "mob.zombie.death";
            case 3:
            	return super.getHurtSound();
            case 4:
            	return "mob.creeper.death";
            default:
            	return "mob.skeleton.death";
        }
    }

    protected Item getDropItem()
    {
        switch (this.getSkullType())
        {
            case 0:
            	return Items.bone;
            case 1:
            	return Items.coal;
            case 2:
            	return Items.rotten_flesh;
            case 3:
            	return Items.apple;
            case 4:
            	return Items.gunpowder;
            default:
            	return Items.bone;
        }
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        Item item = this.getDropItem();

        if (item != null)
        {
            int j = this.rand.nextInt(2 + p_70628_2_);

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(item, 1);
            }
        }
        
        if (this.getSkullType() == 1)
        {
            int j = this.rand.nextInt(2 + p_70628_2_);

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(Items.bone, 1);
            }
        }
        
        if (this.rand.nextInt(15) == 0)
        {
        	this.addRandomArmor();
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        super.writeEntityToNBT(tagCompound);
        tagCompound.setByte("SkullType", (byte)this.getSkullType());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        super.readEntityFromNBT(tagCompund);

        if (tagCompund.hasKey("SkullType", 99))
        {
            byte b0 = tagCompund.getByte("SkullType");
            this.setSkullType(b0);
        }
    }

    public float getEyeHeight()
    {
        switch (this.getSkullType())
        {
            case 4:
            	return 0.325F;
            default:
            	return 0.225F;
        }
    }
    
    public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
    {
        if (this.worldObj.provider instanceof WorldProviderHell)
        {
        	if (this.getRNG().nextInt(5) > 0)
        		this.setSkullType(1);
        	else
        		this.setSkullType(0);
        }
        else
        {
        	this.setSkullType(this.rand.nextInt(5));
        }
        
        return super.func_180482_a(p_180482_1_, p_180482_2_);
    }
    
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if (this.getSkullType() == 1)
    		this.setSize(0.6F, 0.6F);
    	else
    		this.setSize(0.5F, 0.5F);
    	
    	this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    	
        if (this.worldObj.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
                this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, new int[0]);
            }
        }
        
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote && (this.getSkullType() == 0 || this.getSkullType() == 2))
        {
            float f = this.getBrightness(1.0F);
            BlockPos blockpos = new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ);

            if (f > 0.5F && this.rand.nextFloat() * 10.0F < (f - 0.4F) * 2.0F && this.worldObj.canSeeSky(blockpos))
            {
            	this.setFire(8);
            }
        }
    }
    
    protected void updateAITasks()
    {
    	super.updateAITasks();
    	
  	  if (this.getAttackTarget() != null && this.getDistanceSqToCenter(new BlockPos(this.getAttackTarget().posX, this.getAttackTarget().posY + this.getAttackTarget().getEyeHeight(), this.getAttackTarget().posZ)) <= (this.getAttackTarget().width * this.getAttackTarget().width) + 7D)
  	  {
  		  if (this.ticksExisted % 10 == 0)
		  this.attackEntityAsMob(this.getAttackTarget());
  		  
  		  if (this.getSkullType() == 4)
  		  {
  			  if (!this.worldObj.isRemote)
              {
                  this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 2F, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
                  this.setDead();
              }
  		  }
  	  }
    }
    
    public void fall(float distance, float damageMultiplier) {}

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
            this.motionX *= 0.8D;
            this.motionY *= 0.8D;
            this.motionZ *= 0.8D;
        }
        else if (this.isInLava())
        {
            this.moveFlying(p_70612_1_, p_70612_2_, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.6D;
            this.motionY *= 0.6D;
            this.motionZ *= 0.6D;
        }
        else
        {
            float f2 = 0.99F;

            if (this.onGround)
            {
                f2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            this.moveFlying(p_70612_1_, p_70612_2_, this.onGround ? 0.2F * f3 : 0.1F);
            f2 = 0.99F;

            if (this.onGround)
            {
                f2 = this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock().slipperiness * 0.91F;
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= (double)f2;
            this.motionY *= (double)f2;
            this.motionZ *= (double)f2;
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

    class AILookAround extends EntityAIBase
    {
        private EntityLostSkull field_179472_a = EntityLostSkull.this;

        public AILookAround()
        {
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return true;
        }

        /**
         * Updates the task
         */
        public void updateTask()
        {
            if (this.field_179472_a.getAttackTarget() == null)
            {
            	this.field_179472_a.rotationPitch = -(float)(this.field_179472_a.motionY * 90F);
                this.field_179472_a.rotationYawHead = -((float)Math.atan2(this.field_179472_a.motionX, this.field_179472_a.motionZ)) * 180.0F / (float)Math.PI;
            }
            else
            {
                EntityLivingBase entitylivingbase = this.field_179472_a.getAttackTarget();
                double d0 = 32.0D;

                if (entitylivingbase.getDistanceSqToEntity(this.field_179472_a) < d0 * d0)
                {
                    this.field_179472_a.getLookHelper().setLookPositionWithEntity(entitylivingbase, 180F, 180F);
                }
            }
        }
    }

    class AIRandomFly extends EntityAIBase
    {
        private EntityLostSkull field_179454_a = EntityLostSkull.this;

        public AIRandomFly()
        {
            this.setMutexBits(1);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityMoveHelper entitymovehelper = this.field_179454_a.getMoveHelper();

            if (!entitymovehelper.isUpdating())
            {
                return true;
            }
            else
            {
                double d0 = entitymovehelper.func_179917_d() - this.field_179454_a.posX;
                double d1 = entitymovehelper.func_179919_e() - this.field_179454_a.posY;
                double d2 = entitymovehelper.func_179918_f() - this.field_179454_a.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean continueExecuting()
        {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            Random random = this.field_179454_a.getRNG();
            if (this.field_179454_a.getAttackTarget() != null)
            {
                double d0 = this.field_179454_a.getAttackTarget().posX + (double)((random.nextFloat() * 2.0F - 1.0F));
                double d1 = this.field_179454_a.getAttackTarget().posY + this.field_179454_a.getAttackTarget().getEyeHeight() + (double)((random.nextFloat() * 2.0F - 1.0F));
                double d2 = this.field_179454_a.getAttackTarget().posZ + (double)((random.nextFloat() * 2.0F - 1.0F));
                this.field_179454_a.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
            }
            else
            {
                double d0 = this.field_179454_a.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                double d1 = this.field_179454_a.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                double d2 = this.field_179454_a.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
                this.field_179454_a.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
            }
        }
    }

    class LostSkullMoveHelper extends EntityMoveHelper
    {
        private EntityLostSkull field_179927_g = EntityLostSkull.this;
        private int field_179928_h;

        public LostSkullMoveHelper()
        {
            super(EntityLostSkull.this);
        }

        public void onUpdateMoveHelper()
        {
            if (this.update)
            {
                double d0 = this.posX - this.field_179927_g.posX;
                double d1 = this.posY - this.field_179927_g.posY;
                double d2 = this.posZ - this.field_179927_g.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

                if (this.field_179928_h-- <= 0)
                {
                    this.field_179928_h += this.field_179927_g.getRNG().nextInt(2) + 4;
                    d3 = (double)MathHelper.sqrt_double(d3);

                    if (this.func_179926_b(this.posX, this.posY, this.posZ, d3))
                    {
                    	if (this.field_179927_g.getAttackTarget() != null && this.field_179927_g.getAttackTarget().getDistanceSqToEntity(this.field_179927_g) < 45D)
                    	{
                            this.field_179927_g.motionX += d0 / d3 * 0.3D;
                            this.field_179927_g.motionY += d1 / d3 * 0.3D;
                            this.field_179927_g.motionZ += d2 / d3 * 0.3D;
                    	}
                    	else
                    	{
                            this.field_179927_g.motionX += d0 / d3 * 0.1D;
                            this.field_179927_g.motionY += d1 / d3 * 0.1D;
                            this.field_179927_g.motionZ += d2 / d3 * 0.1D;
                    	}
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
            double d4 = (p_179926_1_ - this.field_179927_g.posX) / p_179926_7_;
            double d5 = (p_179926_3_ - this.field_179927_g.posY) / p_179926_7_;
            double d6 = (p_179926_5_ - this.field_179927_g.posZ) / p_179926_7_;
            AxisAlignedBB axisalignedbb = this.field_179927_g.getEntityBoundingBox();

            for (int i = 1; (double)i < p_179926_7_; ++i)
            {
                axisalignedbb = axisalignedbb.offset(d4, d5, d6);

                if (!this.field_179927_g.worldObj.getCollidingBoundingBoxes(this.field_179927_g, axisalignedbb).isEmpty())
                {
                    return false;
                }
            }

            return true;
        }
    }
}