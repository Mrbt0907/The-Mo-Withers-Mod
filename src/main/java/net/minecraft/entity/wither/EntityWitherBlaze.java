package net.minecraft.entity.wither;

import com.google.common.base.Predicate;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherBlaze extends EntityMob
{
    /** Random offset used in floating behaviour */
    private float heightOffset = 4.0F;
    /** ticks until heightOffset is randomized */
    private int heightOffsetUpdateTime;
    private static final Predicate attackEntitySelector = new Predicate()
    {
      public boolean func_180027_a(Entity p_180027_1_)
      {
        return (((p_180027_1_ instanceof EntityLivingBase) && !(p_180027_1_ instanceof EntityArmorStand)) && (((EntityLivingBase)p_180027_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD));
      }
      
      public boolean apply(Object p_apply_1_)
      {
        return func_180027_a((Entity)p_apply_1_);
      }
    };

    public EntityWitherBlaze(World worldIn)
    {
        super(worldIn);
        this.setSize(0.75F, 1.75F);
        this.isImmuneToFire = true;
        this.experienceValue = 15;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityWitherBlaze.AIFireballAttack());
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 2, true, true, attackEntitySelector));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0D);
    }
    
    public boolean canAttackClass(Class p_70686_1_)
    {
        return MoWithers.isntACultist(p_70686_1_);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    protected int decreaseAirSupply(int p_70682_1_)
    {
        return p_70682_1_;
    }

    protected void collideWithEntity(Entity p_82167_1_)
    {
        if (p_82167_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_82167_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && this.getRNG().nextInt(10) == 0)
        {
            this.setAttackTarget((EntityLivingBase)p_82167_1_);
        }

        super.collideWithEntity(p_82167_1_);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
    	this.playSound("mob.wither.idle", 0.6F, this.getSoundPitch() + 0.1F);
        return "mob.blaze.breathe";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
    	this.playSound("mowithers:Ehh", 0.6F, this.getSoundPitch() - 0.1F);
    	this.playSound("mob.wither.hurt", 0.6F, this.getSoundPitch() + 0.1F);
        return "mob.blaze.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
    	this.playSound("mowithers:Ehh", 0.6F, 0.6F);
    	this.playSound("mob.wither.death", 0.6F, this.getSoundPitch() + 0.1F);
        return "mob.blaze.death";
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float p_70013_1_)
    {
        return 1.0F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
    	if (this.ticksExisted % 30 == 0)
	        heal(1.0F);
    	
        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        for (int i = 0; i < (this.func_70845_n() ? 10 : 1); ++i)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        super.onLivingUpdate();
    }

    protected void updateAITasks()
    {
        --this.heightOffsetUpdateTime;

        if (this.heightOffsetUpdateTime <= 0)
        {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 4.0F + (float)this.rand.nextGaussian() * 2.0F;
        }

        EntityLivingBase entitylivingbase = this.getAttackTarget();

        if (entitylivingbase != null && this.canEntityBeSeen(entitylivingbase) && this.posY < entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() + 4D)
        {
            this.motionY += (0.4D - this.motionY) * 0.4D;
            this.isAirBorne = true;
        }

        super.updateAITasks();
    }

    public void fall(float distance, float damageMultiplier) {}
    
    public void setInWeb() {}
    
    public int getTotalArmorValue()
    {
      return 4;
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
      return EnumCreatureAttribute.UNDEAD;
    }
    
    public void addPotionEffect(PotionEffect p_70690_1_) {}

    protected Item getDropItem()
    {
        return Items.blaze_rod;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        int j = 1 + this.rand.nextInt(3 + p_70628_2_);
        int k;

        for (k = 0; k < j; ++k)
        {
            this.dropItem(Items.blaze_rod, 1);
        }
        
        j = this.rand.nextInt(3 + p_70628_2_);

        for (k = 0; k < j; ++k)
        {
        	this.dropItem(Items.nether_wart, 1);
        }
    }

    public boolean func_70845_n()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void func_70844_e(boolean p_70844_1_)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (p_70844_1_)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 &= -2;
        }

        this.dataWatcher.updateObject(16, Byte.valueOf(b0));
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return super.isValidLightLevel();
    }

    class AIFireballAttack extends EntityAIBase
    {
        private EntityWitherBlaze field_179469_a = EntityWitherBlaze.this;
        private int field_179467_b;
        private int field_179468_c;
        private static final String __OBFID = "CL_00002225";

        public AIFireballAttack()
        {
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            EntityLivingBase entitylivingbase = this.field_179469_a.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            this.field_179467_b = 0;
        }

        /**
         * Resets the task
         */
        public void resetTask()
        {
            this.field_179469_a.func_70844_e(false);
        }

        /**
         * Updates the task
         */
        public void updateTask()
        {
            --this.field_179468_c;
            EntityLivingBase entitylivingbase = this.field_179469_a.getAttackTarget();
            double d0 = this.field_179469_a.getDistanceSqToEntity(entitylivingbase);
            
            
            if (d0 < 9.0D)
            {
                if (this.field_179468_c <= 0)
                {
                    this.field_179468_c = 20;
                    this.field_179469_a.attackEntityAsMob(entitylivingbase);
                }

                this.field_179469_a.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }
            else if (d0 < 256.0D)
            {
                double d1 = entitylivingbase.posX - this.field_179469_a.posX;
                double d2 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (this.field_179469_a.posY + (double)(this.field_179469_a.height / 2.0F));
                double d3 = entitylivingbase.posZ - this.field_179469_a.posZ;

                if (this.field_179468_c <= 0)
                {
                    ++this.field_179467_b;

                    if (this.field_179467_b == 1)
                    {
                        this.field_179468_c = 40;
                        this.field_179469_a.func_70844_e(true);
                    }
                    else if (this.field_179467_b <= 4)
                    {
                        this.field_179468_c = 6;
                    }
                    else
                    {
                        this.field_179468_c = 60;
                        this.field_179467_b = 0;
                        this.field_179469_a.func_70844_e(false);
                    }

                    if (this.field_179467_b > 1)
                    {
                        float f = MathHelper.sqrt_float(MathHelper.sqrt_double(d0)) * 0.2F;
                        this.field_179469_a.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, new BlockPos((int)this.field_179469_a.posX, (int)this.field_179469_a.posY, (int)this.field_179469_a.posZ), 0);

                        for (int i = 0; i < 1; ++i)
                        {
                        	EntityWitherSkull entitysmallfireball = new EntityWitherSkull(this.field_179469_a.worldObj, this.field_179469_a, d1 + this.field_179469_a.getRNG().nextGaussian() * (double)f, d2, d3 + this.field_179469_a.getRNG().nextGaussian() * (double)f);
                            entitysmallfireball.posY = this.field_179469_a.posY + (double)(this.field_179469_a.height / 2.0F) + 0.5D;
                            this.field_179469_a.worldObj.spawnEntityInWorld(entitysmallfireball);
                        }
                    }
                }

                this.field_179469_a.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
                this.field_179469_a.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }
            else
            {
                if (!this.field_179469_a.canEntityBeSeen(entitylivingbase))
                	this.field_179469_a.getNavigator().setPath(this.field_179469_a.getNavigator().getPathToEntityLiving(entitylivingbase), 1D);
                else
                	this.field_179469_a.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 5.0D);
            }

            super.updateTask();
        }
    }
}