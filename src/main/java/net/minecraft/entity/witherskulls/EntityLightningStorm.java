package net.minecraft.entity.witherskulls;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.wither.EntityLightningWither;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLightningStorm extends Entity
{
    public EntityLightningStorm(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.playSound("mowithers:Invoke", Float.MAX_VALUE, 1F);
        this.playSound("weather.thunder", Float.MAX_VALUE, 0.75F);
    }

    public EntityLightningStorm(World worldIn, double p_i1699_2_, double p_i1699_4_, double p_i1699_6_)
    {
        this(worldIn);
        this.setPosition(p_i1699_2_, p_i1699_4_, p_i1699_6_);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        if (this.ticksExisted > 900)
        	this.setDead();
        
        if (this.ticksExisted > 60)
        {
            if (this.rand.nextInt(2) == 0)
            {
                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(128.0D, 256.0D, 128.0D));
                if ((list != null) && (!list.isEmpty())) {
                  for (int i = 0; i < list.size(); i++)
                  {
                    Entity entity = (Entity)list.get(i);
                    if (entity != null && entity instanceof EntityLiving && !(entity instanceof INpc) && this.rand.nextInt(1 + (entity instanceof IBossDisplayData ? 1 : (int)(this.ticksExisted * 0.05F))) == 0 && this.worldObj.canSeeSky(new BlockPos(entity))) 
                    {
                    	if (entity instanceof EntityLightningWither)
                    	{
                    		entity.hurtResistantTime = 100;
                    		((EntityLightningWither)entity).heal(5F);
                    	}
                    	entity.motionX = 0F;
                    	entity.motionY = 0F;
                    	entity.motionZ = 0F;
                    	this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, entity.posX - 0.5D, entity.posY, entity.posZ - 0.5D));
                    	if (entity instanceof IBossDisplayData)
                    		entity.hurtResistantTime = 0;
                    	if (entity instanceof EntityDragon)
                    		this.worldObj.newExplosion(null, entity.posX, entity.posY + 1.5D, entity.posZ, 1.75F, false, false);
                    }
                  }
                }
            }
            else
            {
                int i = MathHelper.floor_double(this.posX);
                int j = MathHelper.floor_double(this.posY);
                int k = MathHelper.floor_double(this.posZ);
                int i1 = i + (rand.nextInt() * 128 - 64);
                int j1 = j + (rand.nextInt() * 256 - 128);
                int k1 = k + (rand.nextInt() * 128 - 64);
                if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(i1, j1, k1)))
                	this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, i1 - 0.5D, j1, k1 - 0.5D));
            }
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
    }
}