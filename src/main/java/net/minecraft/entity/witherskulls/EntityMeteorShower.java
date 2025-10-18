package net.minecraft.entity.witherskulls;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMeteorShower extends Entity
{
    public EntityMeteorShower(World worldIn)
    {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.playSound("mowithers:Invoke", Float.MAX_VALUE, 1F);
    }

    public EntityMeteorShower(World worldIn, double p_i1699_2_, double p_i1699_4_, double p_i1699_6_)
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
        
        if (this.ticksExisted > 600)
        	this.setDead();
        
        if (this.rand.nextInt(12) == 0)
        {
            if (this.rand.nextInt(3) == 0)
            {
                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(64.0D, 128.0D, 64.0D));
                if ((list != null) && (!list.isEmpty())) {
                  for (int i = 0; i < list.size(); i++)
                  {
                    Entity entity = (Entity)list.get(i);
                    if (entity != null && entity instanceof EntityLiving && !(entity instanceof INpc) && this.rand.nextInt(10) == 0) 
                    {
                        double d2 = entity.posX + (this.rand.nextDouble() * 6D - 3D);
                        double d3 = entity.posY + 100D;
                        double d4 = entity.posZ + (this.rand.nextDouble() * 6D - 3D);
                        EntityGhastWitherFireball entitylargefireball = new EntityGhastWitherFireball(this.worldObj, d2, d3, d4, this.rand.nextDouble() - 0.5D, -entity.posY, this.rand.nextDouble() - 0.5D);
                        entitylargefireball.explosionPower = 10;
                        if (!this.worldObj.isRemote)
	                        this.worldObj.spawnEntityInWorld(entitylargefireball);
                    }
                  }
                }
            }
            else
            {
                double d2 = this.posX + (this.rand.nextDouble() * 64D - 32D);
                double d3 = this.posY + 100D;
                double d4 = this.posZ + (this.rand.nextDouble() * 64D - 32D);
                EntityGhastWitherFireball entitylargefireball = new EntityGhastWitherFireball(this.worldObj, d2, d3, d4, this.rand.nextDouble() - 0.5D, -this.posY, this.rand.nextDouble() - 0.5D);
                entitylargefireball.explosionPower = 10;
                if (!this.worldObj.isRemote)
                    this.worldObj.spawnEntityInWorld(entitylargefireball);
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