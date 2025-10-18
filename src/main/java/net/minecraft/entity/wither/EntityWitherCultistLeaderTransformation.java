package net.minecraft.entity.wither;

import com.google.common.base.Predicate;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityWitherCultistLeaderTransformation extends EntityWither
{
	private int callFriendsTime;
    private static final Predicate attackEntitySelector = new Predicate()
    {
        public boolean func_180027_a(Entity p_180027_1_)
        {
            return p_180027_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_180027_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
        }
        public boolean apply(Object p_apply_1_)
        {
            return this.func_180027_a((Entity)p_apply_1_);
        }
    };
	
	public EntityWitherCultistLeaderTransformation(World worldIn)
	{
		super(worldIn);
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
	}
	
    public void playLivingSound()
    {
        String s = this.getLivingSound();

        if (s != null)
        {
            this.playSound(s, this.getSoundVolume(), this.getSoundPitch());
            this.playSound("mowithers:CultistIdle", this.getSoundVolume(), this.getSoundPitch() - 0.3F);
        }
    }
	
    public boolean canAttackClass(Class p_70686_1_)
    {
        return MoWithers.isntACultist(p_70686_1_);
    }
	
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	if (amount >= 1F && this.isArmored())
    		amount *= 0.35F;
    	
    	return super.attackEntityFrom(source, amount);
    }
    
    public void onLivingUpdate()
    {
    	super.onLivingUpdate();
    	
    	if (this.rand.nextInt(20) == 0)
    	{
    		EntityLostSkull wither = new EntityLostSkull(this.worldObj);
            if (!this.worldObj.isRemote)
            {
            	wither.copyLocationAndAnglesFrom(this);
            	wither.posY = this.posY + 3D;
            	wither.setSkullType(1);
                this.worldObj.spawnEntityInWorld(wither);
            }
    	}
    	
    	++this.callFriendsTime;
    	
    	if (this.worldObj.getCurrentMoonPhaseFactor() != 1F || this.worldObj.isDaytime())
        {
    		EntityWitherCultistLeader cardinal = new EntityWitherCultistLeader(this.worldObj);
            if (!this.worldObj.isRemote)
            {
            	cardinal.setHealth(this.getHealth() / 3F);
            	cardinal.copyLocationAndAnglesFrom(this);
            	this.setDead();
                this.worldObj.spawnEntityInWorld(cardinal);
            }
        }
        this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
        if (this.getAttackTarget() != null)
        {
            if (this.callFriendsTime >= 100)
            {
            	this.callFriendsTime = 0;
                this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "mowithers:CultistCall", 4.0F, 1.0F);
                if (this.rand.nextInt(2) == 0)
                {
                    for (int i = 0; i < 2 + this.rand.nextInt(6); ++i)
                    {
                    	EntityWitherCultistGreater entityocelot = new EntityWitherCultistGreater(this.worldObj);
                        entityocelot.setLocationAndAngles(this.getAttackTarget().posX + rand.nextDouble() * 12 - 6, this.getAttackTarget().posY, this.getAttackTarget().posZ + rand.nextDouble() * 12 - 6, this.rotationYaw, 0.0F);
                        entityocelot.setAttackTarget(this.getAttackTarget());
                        this.worldObj.spawnEntityInWorld(entityocelot);
                        entityocelot.targetTasks.addTask(2, new EntityAINearestAttackableTarget(entityocelot, EntityLiving.class, 0, false, false, attackEntitySelector));
                    }
                }
                else
                {
                    for (int i = 0; i < 8 + this.rand.nextInt(16); ++i)
                    {
                    	EntityWitherCultist entityocelot = new EntityWitherCultist(this.worldObj);
                        entityocelot.setLocationAndAngles(this.getAttackTarget().posX + rand.nextDouble() * 12 - 6, this.getAttackTarget().posY, this.getAttackTarget().posZ + rand.nextDouble() * 12 - 6, this.rotationYaw, 0.0F);
                        entityocelot.setAttackTarget(this.getAttackTarget());
                        this.worldObj.spawnEntityInWorld(entityocelot);
                        entityocelot.targetTasks.addTask(2, new EntityAINearestAttackableTarget(entityocelot, EntityLiving.class, 0, false, false, attackEntitySelector));
                    }
                }
            }
        }
    }
}
