package net.minecraft.entity.witherskulls;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityDementedWither;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherSkullSaint
  extends EntityWitherSkull
{
  public EntityWitherSkullSaint(World worldIn)
  {
    super(worldIn);
  }
  
  public EntityWitherSkullSaint(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityWitherSkullSaint(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if (movingObject.entityHit != null)
      {
        if (this.shootingEntity != null)
        {
          if ((isInvulnerable()) && ((this.shootingEntity instanceof EntityDementedWither)))
          {
            if (movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 9000.0F)) {
              if (!movingObject.entityHit.isEntityAlive())
              {
                movingObject.entityHit.motionY = 3.0D;
                this.shootingEntity.heal(5.0F);
              }
              else
              {
                movingObject.entityHit.hurtResistantTime = 0;
                func_174815_a(this.shootingEntity, movingObject.entityHit);
              }
            }
          }
          else if (movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 20.0F)) {
            if (!movingObject.entityHit.isEntityAlive())
            {
              this.shootingEntity.heal(5.0F);
            }
            else
            {
              movingObject.entityHit.hurtResistantTime = 1;
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          byte b0 = 5;
          if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
            b0 = 20;
          } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            b0 = 80;
          }
          if (b0 > 0) {
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 3));
          }
          if ((this.shootingEntity != null) && ((this.shootingEntity instanceof EntityDementedWither)))
          {
            movingObject.entityHit.motionY = 1.0D;
            if (b0 > 0) {
              ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.confusion.id, 10 * b0, 0));
            }
          }
        }
      }
      this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.5F, false, true);
      
      setDead();
    }
  }
}
