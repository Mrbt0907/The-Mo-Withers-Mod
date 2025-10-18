package net.minecraft.entity.witherskulls;

import java.util.Random;
import net.minecraft.MoWithers.DamageSourceExtra;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityWitherGirl;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherSkullPiercing
  extends EntityWitherSkull
{
  public EntityWitherSkullPiercing(World worldIn)
  {
	    super(worldIn);
	    this.setSize(0.5F, 0.5F);
  }
  
  public EntityWitherSkullPiercing(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
	    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
	    this.setSize(0.5F, 0.5F);
  }
  
  public EntityWitherSkullPiercing(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
  {
	  this(worldIn);
      this.setSize(0.5F, 0.5F);
      this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
      this.setPosition(x, y, z);
      double d6 = (double)MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
      this.accelerationX = accelX / d6 * 0.1D;
      this.accelerationY = accelY / d6 * 0.1D;
      this.accelerationZ = accelZ / d6 * 0.1D;
      setSize(0.5F, 0.5F);
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if ((movingObject.entityHit != null) && movingObject.entityHit instanceof EntityLivingBase && ((this.shootingEntity instanceof EntityWitherGirl && !(movingObject.entityHit instanceof EntityPlayer) || this.shootingEntity == null || (this.shootingEntity instanceof EntityWitherGirl && ((EntityWitherGirl)this.shootingEntity).isMad))) && (!(movingObject.entityHit instanceof EntityVillager)) && (!(movingObject.entityHit instanceof EntityIronGolem)))
      {
        if (this.shootingEntity != null)
        {
          if (movingObject.entityHit.attackEntityFrom(DamageSourceExtra.causePiercingMobDamage(this.shootingEntity), 20.0F)) {
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(5.0F);
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
            movingObject.entityHit.hurtResistantTime = 0;
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSource.outOfWorld, 20.0F);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          byte b0 = 5;
          if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
            b0 = 20;
          } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            b0 = 40;
          }
          if (b0 > 0) {
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 1));
          }
        }
      }
      if (this.shootingEntity != null)
    	  this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      else
    	  this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      
      setDead();
    }
  }
}
