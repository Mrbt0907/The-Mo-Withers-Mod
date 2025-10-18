package net.minecraft.entity.wither;

import java.util.Random;

import net.minecraft.MoWithers.MoWitherItems;
import net.minecraft.MoWithers.MoWithers;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityWitherCultistLeader extends EntityMob implements IBossDisplayData
{
	private int skullShotTime;
	private int callFriendsTime;
	
    public EntityWitherCultistLeader(World worldIn)
    {
        super(worldIn);
        this.experienceValue = 25;
        this.setSize(0.5F, 1.95F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.field_175455_a);
        this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.2D, true));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }
    
    public boolean canAttackClass(Class p_70686_1_)
    {
        return MoWithers.isntACultist(p_70686_1_);
    }
    
    protected void despawnEntity()
    {
      this.entityAge = 0;
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
        
        if (this.ticksExisted % 60 == 0)
        {
            if (this.getAttackTarget() != null)
            	this.navigator = new PathNavigateClimber(this, this.worldObj);
            else
            	this.navigator = new PathNavigateGround(this, this.worldObj);
        }
    }
    
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if (this.ticksExisted % 40 == 0)
	        heal(1.0F);
    	
    	if (this.rand.nextInt(60) == 0)
    	{
    		EntityLostSkull wither = new EntityLostSkull(this.worldObj);
            if (!this.worldObj.isRemote)
            {
            	wither.copyLocationAndAnglesFrom(this);
            	wither.posY = this.posY + 1.5D;
            	wither.setSkullType(1);
                this.worldObj.spawnEntityInWorld(wither);
            }
    	}
    	
    	++this.skullShotTime;
    	++this.callFriendsTime;
    	
        if ((!this.onGround) && (this.motionY < 0.0D)) {
            this.motionY *= 0.75D;
          }
        if (this.getAttackTarget() != null)
        {
        	this.faceEntity(this.getAttackTarget(), 180F, 30F);
        	
        	if (this.ridingEntity != null && this.ridingEntity instanceof EntityWitherGolem && ((EntityWitherGolem)this.ridingEntity).getAttackTarget() != this.getAttackTarget())
        		((EntityWitherGolem)this.ridingEntity).setAttackTarget(this.getAttackTarget());
        		
        	if (this.getHealth() < this.getMaxHealth() / 4)
            {
        		if (this.worldObj.getCurrentMoonPhaseFactor() == 1F && !this.worldObj.isDaytime())
        		{
            		this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
            		EntityWitherCultistLeaderTransformation wither = new EntityWitherCultistLeaderTransformation(this.worldObj);
                    if (!this.worldObj.isRemote)
                    {
                    	wither.copyLocationAndAnglesFrom(this);
                        this.setDead();
                        this.worldObj.spawnEntityInWorld(wither);
                    }
        		}
        		else if (this.rand.nextInt(60) == 0 && this.ridingEntity == null)
        		{
        			this.heal(20F);
            		EntityWitherGolem wither = new EntityWitherGolem(this.worldObj);
                    if (!this.worldObj.isRemote)
                    {
                    	wither.copyLocationAndAnglesFrom(this);
                        this.mountEntity(wither);
                        this.worldObj.spawnEntityInWorld(wither);
                    }
        		}
        		
            }
        	
            if (this.posY < this.getAttackTarget().posY + 3.0D + this.rand.nextDouble() && this.getHealth() >= this.getMaxHealth() / 2)
            {
            	this.faceEntity(this.getAttackTarget(), 180F, 30F);
              if (this.motionY < 0.0D) {
                this.motionY = 0.0D;
              }
              this.motionY += (0.5D - this.motionY) * 0.5D;
              this.moveStrafing = 1F;
              this.limbSwingAmount *= 0F;
            }
            else
            {
            	this.getAttackTarget().hurtResistantTime = 0;
            }
            if (this.skullShotTime >= 80)
            {
            	this.attackEntityWithRangedAttack(this.getAttackTarget(), 0F);
            	this.skullShotTime = 0;
            }
            
            if (this.callFriendsTime >= 200)
            {
            	this.callFriendsTime = 0;
                this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mowithers:CultistCall", 4.0F, 1.0F);
                if (this.rand.nextInt(3) == 0)
                {
                    for (int i = 0; i < 1 + this.rand.nextInt(3); ++i)
                    {
                    	EntityWitherCultistGreater entityocelot = new EntityWitherCultistGreater(this.worldObj);
                        entityocelot.setLocationAndAngles(this.getAttackTarget().posX + rand.nextDouble() * 12 - 6, this.getAttackTarget().posY, this.getAttackTarget().posZ + rand.nextDouble() * 12 - 6, this.rotationYaw, 0.0F);
                        entityocelot.setAttackTarget(this.getAttackTarget());
                        this.worldObj.spawnEntityInWorld(entityocelot);
                    }
                }
                else
                {
                    for (int i = 0; i < 4 + this.rand.nextInt(8); ++i)
                    {
                    	EntityWitherCultist entityocelot = new EntityWitherCultist(this.worldObj);
                        entityocelot.setLocationAndAngles(this.getAttackTarget().posX + rand.nextDouble() * 12 - 6, this.getAttackTarget().posY, this.getAttackTarget().posZ + rand.nextDouble() * 12 - 6, this.rotationYaw, 0.0F);
                        entityocelot.setAttackTarget(this.getAttackTarget());
                        this.worldObj.spawnEntityInWorld(entityocelot);
                    }
                }
            }
        }
        this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150.0D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }

    protected String getLivingSound()
    {
      return "mowithers:CultistIdle";
    }
    
    protected String getHurtSound()
    {
      return "mowithers:CultistHurt";
    }
    
    protected String getDeathSound()
    {
      return "mob.wither.death";
    }

    protected Item getDropItem()
    {
        return Items.baked_potato;
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        super.dropFewItems(p_70628_1_, p_70628_2_);

        Item item = this.getDropItem();

        if (item != null)
        {
            int j = this.rand.nextInt(3);
            ++j;
            ++j;

            if (p_70628_2_ > 0)
            {
                j += this.rand.nextInt(p_70628_2_ + 1);
            }

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(item, 2);
            }
        }
        
        int j = 2 + this.rand.nextInt(2 + p_70628_2_);

        for (int k = 0; k < j + 1 + p_70628_2_; ++k)
        {
      	  this.dropItem(MoWitherItems.skull, 1);
        }
        
        if ((this.rand.nextInt(2) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
        {
            this.dropItem(Items.emerald, 1);
        }
        
        if ((this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
        {
            this.dropItem(Items.emerald, 1);
        }
        
        if ((this.rand.nextInt(4) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
        {
            this.dropItem(Items.emerald, 1);
        }
        
        if ((this.rand.nextInt(10) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0))
        {
            this.dropItem(Items.nether_star, 1);
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
    
    public void fall(float distance, float damageMultiplier) {}

    public boolean isPotionApplicable(PotionEffect p_70687_1_)
    {
        return p_70687_1_.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(p_70687_1_);
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

    public float getEyeHeight()
    {
        return 1.62F;
    }
    
    private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
    {
      launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + (p_82216_2_.getEyeHeight() * 0.25D), p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
    }
    
    private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
    {
      this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mowithers:CultistSkullSummon", 4.0F, 1.0F);
      double d3 = this.posX;
      double d4 = this.posY + 4D;
      double d5 = this.posZ;
      double d6 = p_82209_2_ - d3;
      double d7 = p_82209_4_ - d4;
      double d8 = p_82209_6_ - d5;
      EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.worldObj, this, d6, d7, d8);
      if (p_82209_8_) {
        entitywitherskull.setInvulnerable(true);
      }
      entitywitherskull.posY = d4;
      entitywitherskull.posX = d3;
      entitywitherskull.posZ = d5;
      this.worldObj.spawnEntityInWorld(entitywitherskull);
      this.worldObj.createExplosion(this, this.posX, this.posY + 4.5D, this.posZ, 0F, false);
    }
    
    public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
    {
      launchWitherSkullToEntity(0, p_82196_1_);
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
      return EnumCreatureAttribute.UNDEAD;
    }
}