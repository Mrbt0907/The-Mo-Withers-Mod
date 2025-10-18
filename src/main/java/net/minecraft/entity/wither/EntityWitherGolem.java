package net.minecraft.entity.wither;

import com.google.common.base.Predicate;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherGolem extends EntityGolem implements IMob, IRangedAttackMob
{
    private int attackTimer;
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
    private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 60, 32.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, 1.0D, true);

    public EntityWitherGolem(World worldIn)
    {
        super(worldIn);
        this.setSize(1.3F, 2.7F);
        this.isImmuneToFire = true;
        this.experienceValue = 25;
        this.tasks.addTask(1, new EntityAISwimming(this));
        ((PathNavigateGround)this.getNavigator()).func_179690_a(true);
        this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(1, new EntityAINearestWitherAttackTarget(this, EntityIronGolem.class, true));
        this.targetTasks.addTask(2, new EntityAINearestWitherAttackTarget(this, EntityLivingBase.class, 2, true, true, attackEntitySelector));
    }
    
    public boolean canAttackClass(Class p_70686_1_)
    {
        return MoWithers.isntACultist(p_70686_1_);
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
    }
    
    protected boolean canDespawn()
    {
        return true;
    }

    /**
     * Decrements the entity's air supply when underwater
     */
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
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (this.getAITarget() != null)
        {
        if ((this.getAITarget() instanceof EntityWitherCultist || this.getAITarget() instanceof EntityWitherCultistGreater || this.getAITarget() instanceof EntityWitherCultistLeader || this.getAITarget() instanceof EntityWither || this.getAITarget() instanceof EntityWitherBlaze || this.getAITarget() instanceof EntityWitherCreeper || this.getAITarget() instanceof EntityWitherGolem || this.getAITarget() instanceof EntityWitherZombie || this.getAITarget() instanceof EntityWitherSpider)) {
            this.setRevengeTarget(null);
          }
        else
        {
        	this.setAttackTarget(this.getAITarget());
        	this.setRevengeTarget(null);
        }
        }
        
    	if (this.ticksExisted % 20 == 0)
	        heal(1.0F);
        
        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }
        
        if (this.getAttackTarget() instanceof EntityIronGolem && ((EntityIronGolem)this.getAttackTarget()).getAttackTarget() != this)
        	((EntityIronGolem)this.getAttackTarget()).setAttackTarget(this);
        
        if (this.getAttackTarget() != null)
        {
            if (this.getDistanceSqToEntity(this.getAttackTarget()) > 144D || this.getAttackTarget() instanceof EntityFlying || this.getAttackTarget().posY > this.posY + 4D)
            {
                this.tasks.addTask(1, this.aiArrowAttack);
                this.tasks.removeTask(this.aiAttackOnCollide);
            }
            else
            {
                this.tasks.addTask(1, this.aiAttackOnCollide);
                this.tasks.removeTask(this.aiArrowAttack);
            }
        }
        
        if (this.attackTimer > 0)
        {
            --this.attackTimer;
        }

        if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0 && this.rand.nextInt(5) == 0)
        {
            int i = MathHelper.floor_double(this.posX);
            int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
            int k = MathHelper.floor_double(this.posZ);
            IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(i, j, k));
            Block block = iblockstate.getBlock();

            if (block.getMaterial() != Material.air)
            {
                this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, this.getEntityBoundingBox().minY + 0.1D, this.posZ + ((double)this.rand.nextFloat() - 0.5D) * (double)this.width, 4.0D * ((double)this.rand.nextFloat() - 0.5D), 0.5D, ((double)this.rand.nextFloat() - 0.5D) * 4.0D, new int[] {Block.getStateId(iblockstate)});
            }
        }
        
        this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
    }
    
    public void setInWeb() {}
    
    public int getTotalArmorValue()
    {
      return 4;
    }
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_)
    {
      return 15728880;
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
      return EnumCreatureAttribute.UNDEAD;
    }
    
    public void addPotionEffect(PotionEffect p_70690_1_) {}

    public boolean attackEntityAsMob(Entity p_70652_1_)
    {
        this.attackTimer = 10;
        this.worldObj.setEntityState(this, (byte)4);
        boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(20 + this.rand.nextInt(40)));
        if (p_70652_1_ instanceof EntityPlayer)
        p_70652_1_.attackEntityFrom(DamageSource.outOfWorld, 1F);
        p_70652_1_.motionY += 1D;
        this.func_174815_a(this, p_70652_1_);
        if (p_70652_1_ instanceof EntityLivingBase)
        {
            ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.wither.id, 200, 1));
        }
        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte p_70103_1_)
    {
        if (p_70103_1_ == 4)
        {
            this.attackTimer = 10;
            this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        }
        else
        {
            super.handleHealthUpdate(p_70103_1_);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getAttackTimer()
    {
        return this.attackTimer;
    }

    protected String getLivingSound()
    {
    	this.playSound("mob.wither.idle", 0.75F, this.getSoundPitch());
      return "mob.blaze.breathe";
    }
    
    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
    	this.playSound("mowithers:Ehh", 0.5F, this.getSoundPitch() - 0.2F);
    	this.playSound("mob.wither.hurt", 0.75F, this.getSoundPitch());
        return "mob.irongolem.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
    	this.playSound("mowithers:Ehh", 1F, 0.5F);
    	this.playSound("mob.wither.death", 1F, this.getSoundPitch());
        return "mob.irongolem.death";
    }

    protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
    {
        this.playSound("mob.irongolem.walk", 1.0F, 0.9F);
        this.playSound("mob.skeleton.step", 0.5F, 0.6F);
    }

    /**
     * Drop 0-2 items of this living's type
     */
    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
    {
        int j = this.rand.nextInt(3 + p_70628_2_);
        int k;

        for (k = 0; k < j; ++k)
        {
        	this.dropItem(Items.nether_wart, 1);
        }

        k = 3 + this.rand.nextInt(3 + p_70628_2_);

        for (int l = 0; l < k; ++l)
        {
            this.dropItem(Items.gold_ingot, 1);
        }
        
        for (int l = 0; l < k + 2; ++l)
        {
            this.dropItem(Items.gunpowder, 1);
        }
    }
    
    public float func_180484_a(BlockPos p_180484_1_)
    {
        return 0.5F - this.worldObj.getLightBrightness(p_180484_1_);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        BlockPos blockpos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);

        if (this.worldObj.getLightFor(EnumSkyBlock.SKY, blockpos) > this.rand.nextInt(32))
        {
            return false;
        }
        else
        {
            int i = this.worldObj.getLightFromNeighbors(blockpos);

            if (this.worldObj.isThundering())
            {
                int j = this.worldObj.getSkylightSubtracted();
                this.worldObj.setSkylightSubtracted(10);
                i = this.worldObj.getLightFromNeighbors(blockpos);
                this.worldObj.setSkylightSubtracted(j);
            }

            return i <= this.rand.nextInt(8);
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && this.isValidLightLevel() && super.getCanSpawnHere();
    }
    
    public void attackEntityWithRangedAttack(EntityLivingBase entitylivingbase, float p_82196_2_)
    {
    	this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, new BlockPos(this), 0);
    	this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
    	this.attackTimer = 10;
        this.worldObj.setEntityState(this, (byte)4);
        double d1 = 1.5D;
        Vec3 vec3 = this.getLook(1.0F);
        double d2 = entitylivingbase.posX - (this.posX + vec3.xCoord * d1);
        double d3 = entitylivingbase.posY + 0.5D - (this.posY + 2.5D);
        double d4 = entitylivingbase.posZ - (this.posZ + vec3.zCoord * d1);
        EntityWitherSkull entitylargefireball = new EntityWitherSkull(this.worldObj, this, d2, d3, d4);
        entitylargefireball.posX = this.posX + vec3.xCoord * d1;
        entitylargefireball.posY = this.posY + 2.5D;
        entitylargefireball.posZ = this.posZ + vec3.zCoord * d1;
        this.worldObj.spawnEntityInWorld(entitylargefireball);
    }
}