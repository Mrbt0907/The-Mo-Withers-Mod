package net.minecraft.entity.witherskulls;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityBabyWither;
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

public class EntityVoidSkull
  extends EntityWitherSkull
{
  public EntityVoidSkull(World worldIn)
  {
    super(worldIn);
    setSize(1.0F, 1.0F);
  }
  
  public EntityVoidSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(1.0F, 1.0F);
  }
  
  protected float getMotionFactor()
  {
    return isInvulnerable() ? 0.6F : 0.9F;
  }
  
  @SideOnly(Side.CLIENT)
  public EntityVoidSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(1.0F, 1.0F);
  }
  
  public float getExplosionResistance(Explosion p_180428_1_, World worldIn, BlockPos p_180428_3_, IBlockState p_180428_4_)
  {
    float f = super.getExplosionResistance(p_180428_1_, worldIn, p_180428_3_, p_180428_4_);
    if ((p_180428_4_.getBlock() != Blocks.bedrock) && (p_180428_4_.getBlock() != Blocks.end_portal) && (p_180428_4_.getBlock() != Blocks.end_portal_frame) && (p_180428_4_.getBlock() != Blocks.command_block)) {
      f = Math.min(0.25F, f);
    }
    return f;
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if ((movingObject.entityHit != null) && (movingObject.entityHit != this.shootingEntity))
      {
        if (this.shootingEntity != null)
        {
          if (movingObject.entityHit.attackEntityFrom(DamageSource.outOfWorld, 6.0F))
          {
            movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 46.0F);
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(15.0F);
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSource.outOfWorld, 16.0F);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.blindness.id, 400, 0));
          
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
      this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 3.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      setDead();
    }
  }
  
  public void onUpdate()
  {
	  super.onUpdate();
	  
      if (this.posY <= 0D) 
    	  this.posY = 0D;
      
      this.rotationPitch = (float) this.accelerationY * 90F;
      this.rotationYaw = ((float)Math.atan2(this.accelerationZ, this.accelerationX) * 180.0F / (float)Math.PI);
  }
}
