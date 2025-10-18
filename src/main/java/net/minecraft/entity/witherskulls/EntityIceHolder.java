package net.minecraft.entity.witherskulls;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.wither.EntityAvatarWither;
import net.minecraft.entity.wither.EntityIceWither;
import net.minecraft.entity.wither.EntityLightningWither;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityIceHolder extends Entity
{
	private Entity entityToHold;
	private float entityToHoldBodyRotation;
	private float entityToHoldHeadRotation;
    public EntityIceHolder(World worldIn)
    {
        super(worldIn);
        this.setSize(0.2F, 0.2F);
    }

    public EntityIceHolder(World worldIn, double p_i1699_2_, double p_i1699_4_, double p_i1699_6_, Entity entity, float rotationBody, float rotationHead)
    {
        this(worldIn);
        this.setPosition(p_i1699_2_, p_i1699_4_, p_i1699_6_);
        this.entityToHold = entity;
        this.entityToHoldBodyRotation = rotationBody;
        this.entityToHoldHeadRotation = rotationHead;
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
    	super.onUpdate();
    	
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        
        if ((this.ticksExisted >= 300 && this.entityToHold != null && this.entityToHold instanceof IBossDisplayData) || this.ticksExisted >= 600 || this.entityToHold == null || (this.entityToHold != null && !this.entityToHold.isEntityAlive()))
        {
        	if (this.entityToHold != null)
        	{
                int i = MathHelper.floor_double(this.posY);
                int i1 = MathHelper.floor_double(this.posX);
                int j1 = MathHelper.floor_double(this.posZ);
                for (int l1 = -(int)(this.entityToHold.width / 2); l1 <= (int)(this.entityToHold.width / 2); l1++) {
                  for (int i2 = -(int)(this.entityToHold.width / 2); i2 <= (int)(this.entityToHold.width / 2); i2++) {
                    for (int j = 0; j <= (int)(this.entityToHold.height); j++)
                    {
                      int j2 = i1 + l1;
                      int k = i + j;
                      int l = j1 + i2;
                      BlockPos blockpos = new BlockPos(j2, k, l);
                      Block block = this.worldObj.getBlockState(new BlockPos(blockpos)).getBlock();
                      if (block.getBlockHardness(this.worldObj, blockpos) != -1F && !block.isOpaqueCube() && block == Blocks.ice) {
                    	  this.worldObj.setBlockState(blockpos, Blocks.air.getDefaultState());
                      }
                    }
                  }
                }
        	}

        	this.setDead();
        }
        else
        {
        	if (this.entityToHold != null)
        	{
        		if (this.entityToHold instanceof EntityLivingBase)
            	{
        			((EntityLivingBase)this.entityToHold).rotationYaw = this.entityToHoldBodyRotation;
        			((EntityLivingBase)this.entityToHold).rotationYawHead = this.entityToHoldHeadRotation;
        			if (this.entityToHold instanceof EntityGuardian)
        			((EntityLivingBase)this.entityToHold).rotationPitch = 0F;
        			else
        			((EntityLivingBase)this.entityToHold).rotationPitch = -30F;
        			((EntityLivingBase)this.entityToHold).hurtResistantTime = 40;
            	}
        		if (this.entityToHold instanceof EntityEnderman || this.entityToHold instanceof EntityIceWither || this.entityToHold instanceof EntityAvatarWither)
            	{
        			this.ticksExisted = 600;
            	}
        		if (this.entityToHold.isEntityAlive() && !this.worldObj.isRemote)
        		{
        			this.entityToHold.motionX = 0F;
        			this.entityToHold.motionY = 0F;
        			this.entityToHold.motionZ = 0F;
                	this.entityToHold.setPosition(posX, posY, posZ);
                    int i = MathHelper.floor_double(this.posY);
                    int i1 = MathHelper.floor_double(this.posX);
                    int j1 = MathHelper.floor_double(this.posZ);
                    for (int l1 = -(int)(this.entityToHold.width / 2); l1 <= (int)(this.entityToHold.width / 2); l1++) {
                      for (int i2 = -(int)(this.entityToHold.width / 2); i2 <= (int)(this.entityToHold.width / 2); i2++) {
                        for (int j = 0; j <= (int)(this.entityToHold.height); j++)
                        {
                          int j2 = i1 + l1;
                          int k = i + j;
                          int l = j1 + i2;
                          BlockPos blockpos = new BlockPos(j2, k, l);
                          Block block = this.worldObj.getBlockState(new BlockPos(blockpos)).getBlock();
                          if (block.getBlockHardness(this.worldObj, blockpos) != -1F && !block.isOpaqueCube() && block != Blocks.ice) {
                        	  this.worldObj.setBlockState(blockpos, Blocks.ice.getDefaultState());
                          }
                        }
                      }
                    }
        		}
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