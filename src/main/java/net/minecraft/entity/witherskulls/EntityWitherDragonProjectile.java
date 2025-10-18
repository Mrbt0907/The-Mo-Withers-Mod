package net.minecraft.entity.witherskulls;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityBabyWither;
import net.minecraft.entity.wither.EntityWitherCultist;
import net.minecraft.entity.wither.EntityWitherCultistGreater;
import net.minecraft.entity.wither.EntityWitherDragon;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherDragonProjectile
  extends EntityFireball
{
  public EntityWitherDragonProjectile(World worldIn)
  {
    super(worldIn);
    setSize(1.5F, 1.5F);
  }
  
  public EntityWitherDragonProjectile(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(1.5F, 1.5F);
  }
  
  protected void entityInit()
  {
      this.dataWatcher.addObject(10, Byte.valueOf((byte)0));
  }

  /**
   * Return whether this skull comes from an invulnerable (aura) wither boss.
   */
  public boolean isInvulnerable()
  {
      return this.dataWatcher.getWatchableObjectByte(10) == 1;
  }

  /**
   * Set whether this skull comes from an invulnerable (aura) wither boss.
   */
  public void setInvulnerable(boolean p_82343_1_)
  {
      this.dataWatcher.updateObject(10, Byte.valueOf((byte)(p_82343_1_ ? 1 : 0)));
  }
  
  public boolean isBurning()
  {
      return false;
  }
  
  protected float getMotionFactor()
  {
    return 1F;
  }
  
  @SideOnly(Side.CLIENT)
  public EntityWitherDragonProjectile(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(1.5F, 1.5F);
  }
  
  public float getExplosionResistance(Explosion p_180428_1_, World worldIn, BlockPos p_180428_3_, IBlockState p_180428_4_)
  {
      float f = super.getExplosionResistance(p_180428_1_, worldIn, p_180428_3_, p_180428_4_);

      if (this.isInvulnerable() && p_180428_4_.getBlock() != Blocks.bedrock && p_180428_4_.getBlock() != Blocks.end_portal && p_180428_4_.getBlock() != Blocks.end_portal_frame && p_180428_4_.getBlock() != Blocks.command_block)
      {
          f = Math.min(0.8F, f);
      }

      return f;
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if (movingObject.entityHit != null)
      {
          double d0 = movingObject.entityHit.posX - this.posX;
          double d1 = movingObject.entityHit.posZ - this.posZ;
          double d3 = d0 * d0 + d1 * d1;
          double d5 = MathHelper.sqrt_double(d3);
          this.motionX += (d0 / d5 * 0.1D - this.motionX);
          this.motionZ += (d1 / d5 * 0.1D - this.motionZ);
          this.motionX = 0F;
          this.motionY = 0F;
          this.motionZ = 0F;
          this.accelerationX = 0F;
          this.accelerationY = -0.1F;
          this.accelerationZ = 0F;
          
          if (movingObject.entityHit instanceof EntityWitherDragonProjectile)
          {
        	  this.shootingEntity = null;
          }
    	  
        if (this.shootingEntity != null)
        {
            this.worldObj.newExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 1F, false, false);
            this.worldObj.newExplosion(this.shootingEntity, movingObject.entityHit.posX, movingObject.entityHit.posY, movingObject.entityHit.posZ, 1F, false, false);
        	
            if (!this.worldObj.isRemote && this.rand.nextInt(2) == 0)
            	this.setDead();
            
          if (movingObject.entityHit.attackEntityFrom(DamageSource.outOfWorld, 42.0F))
          {
            movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 50.0F);
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(15.0F);
              if (movingObject.entityHit instanceof EntityVillager || movingObject.entityHit instanceof EntityWitherCultist || movingObject.entityHit instanceof EntityWitherCultistGreater)
              {
            	  EntityWitherCultistGreater wither = new EntityWitherCultistGreater(worldObj);
                  wither.copyLocationAndAnglesFrom(movingObject.entityHit);
                  movingObject.entityHit.setDead();
                  worldObj.spawnEntityInWorld(wither);
              }
              if (movingObject.entityHit instanceof EntityBabyWither)
              {
            	  EntityBabyWither wither = new EntityBabyWither(worldObj);
                  wither.copyLocationAndAnglesFrom(movingObject.entityHit);
                  wither.setGrowthPoints(((EntityBabyWither)movingObject.entityHit).getGrowthPoints());
                  movingObject.entityHit.setDead();
                  worldObj.spawnEntityInWorld(wither);
                  this.motionY = 0.5F + this.rand.nextFloat();
              }
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSource.outOfWorld, 42.0F);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          byte b0 = 20;
          if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
            b0 = 40;
          } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            b0 = 80;
          }
          if (b0 > 0) {
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 3));
          }
        }
      }
      else
      {
          this.motionX = this.rand.nextFloat() - 0.5F;
          this.motionY = 0.25F + this.rand.nextFloat();
          this.motionZ = this.rand.nextFloat() - 0.5F;
          this.accelerationX = 0F;
          this.accelerationY = -0.1F;
          this.accelerationZ = 0F;
      }
    }
  }
  
  public void onUpdate()
  {
	  super.onUpdate();
	  
      if (this.posY <= 0D) 
    	  this.posY = 0D;
      
      this.noClip = true;
      
      List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(6.0D, 6.0D, 6.0D));
      if (this.rand.nextInt(2) == 0 && (list111 != null) && (!list111.isEmpty())) {
        for (int i111 = 0; i111 < list111.size(); i111++)
        {
          Entity entity = (Entity)list111.get(i111);
          if (entity != null && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isEntityAlive() && !(entity instanceof EntityWitherDragon) && !(entity instanceof EntityBabyWither) && !(entity instanceof EntityWither)) 
          {
              double d0 = entity.posX - this.posX;
              double d1 = entity.posZ - this.posZ;
              double d2 = entity.posZ - this.posZ;
              double d3 = d0 * d0 + d1 * d1 + d2 * d2;
              
              double d5 = MathHelper.sqrt_double(d3);
              this.motionX += (d0 / d5 * 0.75D - this.motionX);
              this.motionY += (d1 / d5 * 0.75D - this.motionX);
              this.motionZ += (d2 / d5 * 0.75D - this.motionX);
              break;
          }
        }
      }

      if (!this.worldObj.isRemote && (this.shootingEntity == null || (this.ticksExisted > 200 && this.rand.nextInt(10) == 0) || (this.shootingEntity != null && this.getDistanceSqToEntity(this.shootingEntity) > 14400D))) 
      {
          this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 3F, false, false);
          this.setDead();
          if (this.rand.nextInt(3) == 0)
          {
              EntityBabyWither wither = new EntityBabyWither(worldObj);
              wither.copyLocationAndAnglesFrom(this);
              worldObj.spawnEntityInWorld(wither);
          }
      }
  }
}

