package net.minecraft.entity.wither;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherSpider extends EntityMob
{
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
	private int skullShotTime;
	
    public EntityWitherSpider(World worldIn)
    {
        super(worldIn);
        this.setSize(1.35F, 0.8F);
        this.isImmuneToFire = true;
        this.experienceValue = 10;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(0, new EntityAIAvoidEntity(this, attackEntitySelector, 3.0F, 1.0D, 2.0D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 2, false, false, attackEntitySelector));
    }
    
    public boolean canAttackClass(Class p_70686_1_)
    {
        return MoWithers.isntACultist(p_70686_1_);
    }

    protected PathNavigate func_175447_b(World worldIn)
    {
        return new PathNavigateClimber(this, worldIn);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.worldObj.isRemote)
        {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }
    
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	++this.skullShotTime;
    	
    	if (this.ticksExisted % 40 == 0)
	        heal(1.0F);
    	
    	if (this.riddenByEntity != null && this.getAttackTarget() != null && this.riddenByEntity instanceof EntitySkeleton)
    	{
    		((EntitySkeleton)this.riddenByEntity).faceEntity(this.getAttackTarget(), 10F, 30F);
    		((EntitySkeleton)this.riddenByEntity).setAttackTarget(this.getAttackTarget());
    		((EntitySkeleton)this.riddenByEntity).tasks.addTask(0, new EntityAIAttackOnCollide((EntitySkeleton)this.riddenByEntity, 1.2D, false));
    	}
    	
        if (this.worldObj.isRemote)
        {
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
        }
        
        if (this.skullShotTime >= 100 && this.getAttackTarget() != null && this.canEntityBeSeen(this.getAttackTarget()))
        {
        	this.attackEntityWithRangedAttack(this.getAttackTarget(), 0F);
        	this.skullShotTime = 0;
        }
        
        if (this.getAttackTarget() != null && this.getDistanceSqToEntity(this.getAttackTarget()) < 6D)
        {
        	this.getAttackTarget().hurtResistantTime = 0;
        	if (this.getAttackTarget() instanceof EntityLiving && ((EntityLiving)this.getAttackTarget()).getAttackTarget() == this)
        	((EntityLiving)this.getAttackTarget()).setAttackTarget(null);
        	this.setAttackTarget(null);
        }
        
        if (this.getAttackTarget() != null && this.worldObj.canBlockSeeSky(getPosition()) && this.onGround && this.ticksExisted % 20 == 0 && this.rand.nextInt(2) == 0 && this.getDistanceSqToEntity(this.getAttackTarget()) < 400D && this.getDistanceSqToEntity(this.getAttackTarget()) > 10D)
        {
            double d0 = this.getAttackTarget().posX - this.posX;
            double d1 = this.getAttackTarget().posZ - this.posZ;
            float f = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
            this.motionX += d0 / (double)f * 0.8D * 0.8D + this.motionX * 0.5D;
            this.motionZ += d1 / (double)f * 0.8D * 0.8D + this.motionZ * 0.5D;
            this.motionY += 1F;
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0D);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
    	this.playSound("mob.wither.idle", 0.4F, this.getSoundPitch() + 0.1F);
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
    	this.playSound("mowithers:Ehh", 0.4F, this.getSoundPitch() + 0.1F);
    	this.playSound("mob.wither.hurt", 0.4F, this.getSoundPitch() + 0.1F);
        return "mob.spider.say";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
    	this.playSound("mowithers:Ehh", 0.4F, 0.8F);
    	this.playSound("mob.wither.death", 0.4F, this.getSoundPitch() + 0.1F);
        return "mob.spider.death";
    }

    protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
    {
        this.playSound("mob.spider.step", 0.15F, 1.0F);
    }

    protected Item getDropItem()
    {
        return Items.string;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        super.dropFewItems(p_70628_1_, p_70628_2_);
        
        int j = this.rand.nextInt(3 + p_70628_2_);
        int k;

        for (k = 0; k < j; ++k)
        {
        	this.dropItem(Items.nether_wart, 1);
        }

        if (p_70628_1_ && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
        {
            this.dropItem(Items.spider_eye, 1);
        }
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean isOnLadder()
    {
        return this.isBesideClimbableBlock();
    }

    /**
     * Sets the Entity inside a web block.
     */
    public void setInWeb() {}
    
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

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
      return 15728880;
    }
    
    public void fall(float distance, float damageMultiplier) {}
    
    public int getTotalArmorValue()
    {
      return 4;
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
      return EnumCreatureAttribute.UNDEAD;
    }
    
    public boolean isPotionApplicable(PotionEffect p_70687_1_)
    {
    	int i = p_70687_1_.getPotionID();

        return i == 1 || i == 5 || i == 10 || i == 14;
    }

    /**
     * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
     * setBesideClimableBlock.
     */
    public boolean isBesideClimbableBlock()
    {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    /**
     * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
     * false.
     */
    public void setBesideClimbableBlock(boolean p_70839_1_)
    {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (p_70839_1_)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 &= -2;
        }

        this.dataWatcher.updateObject(16, Byte.valueOf(b0));
    }

    public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
    {
        Object p_180482_2_1 = super.func_180482_a(p_180482_1_, p_180482_2_);

        if (this.worldObj.rand.nextInt(20) == 0)
        {
            EntitySkeleton entityskeleton = new EntitySkeleton(this.worldObj);
            entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
            entityskeleton.func_180482_a(p_180482_1_, (IEntityLivingData)null);
            entityskeleton.setSkeletonType(1);
            entityskeleton.setCurrentItemOrArmor(0, new ItemStack(Items.stone_sword));
            entityskeleton.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
            this.worldObj.spawnEntityInWorld(entityskeleton);
            entityskeleton.mountEntity(this);
        }

        if (p_180482_2_1 == null)
        {
            p_180482_2_1 = new EntityWitherSpider.GroupData();

            if (this.worldObj.rand.nextFloat() < 0.2F * p_180482_1_.getClampedAdditionalDifficulty())
            {
                ((EntityWitherSpider.GroupData)p_180482_2_1).func_111104_a(this.worldObj.rand);
            }
        }

        if (p_180482_2_1 instanceof EntityWitherSpider.GroupData)
        {
            int i = ((EntityWitherSpider.GroupData)p_180482_2_1).field_111105_a;

            if (i > 0 && Potion.potionTypes[i] != null)
            {
                this.addPotionEffect(new PotionEffect(i, Integer.MAX_VALUE));
            }
        }

        return (IEntityLivingData)p_180482_2_1;
    }

    public float getEyeHeight()
    {
        return 0.55F;
    }
    
    public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        if (super.attackEntityAsMob(p_70652_1_))
        {
            if (p_70652_1_ instanceof EntityLivingBase)
            {
                ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float p_82196_2_)
    {
    	entitylivingbase.hurtResistantTime = 0;
    	this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, new BlockPos(this), 0);
        double d1 = 1.5D;
        Vec3 vec3 = this.getLook(1.0F);
        double d2 = entitylivingbase.posX - (this.posX + vec3.xCoord * d1);
        double d3 = (entitylivingbase.posY + 0.3D) - (this.posY + 0.6D);
        double d4 = entitylivingbase.posZ - (this.posZ + vec3.zCoord * d1);
        EntityWitherSkull entitylargefireball = new EntityWitherSkull(this.worldObj, this, d2, d3, d4);
        entitylargefireball.posX = this.posX + vec3.xCoord * d1;
        entitylargefireball.posY = this.posY + 0.6D;
        entitylargefireball.posZ = this.posZ + vec3.zCoord * d1;
        this.worldObj.spawnEntityInWorld(entitylargefireball);
    }

    public static class GroupData implements IEntityLivingData
        {
            public int field_111105_a;

            public void func_111104_a(Random p_111104_1_)
            {
                int i = p_111104_1_.nextInt(5);

                if (i <= 1)
                {
                    this.field_111105_a = Potion.moveSpeed.id;
                }
                else if (i <= 2)
                {
                    this.field_111105_a = Potion.damageBoost.id;
                }
                else if (i <= 3)
                {
                    this.field_111105_a = Potion.regeneration.id;
                }
                else if (i <= 4)
                {
                    this.field_111105_a = Potion.invisibility.id;
                }
            }
        }
}