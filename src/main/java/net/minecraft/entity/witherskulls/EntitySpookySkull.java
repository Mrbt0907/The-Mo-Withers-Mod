package net.minecraft.entity.witherskulls;

import net.minecraft.MoWithers.DamageSourceExtra;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpookySkull
  extends EntityWitherSkull
{
  public EntitySpookySkull(World worldIn)
  {
    super(worldIn);
    setSize(0.5F, 0.5F);
  }
  
  public EntitySpookySkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(0.5F, 0.5F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntitySpookySkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(0.5F, 0.5F);
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if (movingObject.entityHit != null)
      {
        if (this.shootingEntity != null)
        {
          if (movingObject.entityHit.attackEntityFrom(DamageSourceExtra.spooky, 16.0F))
          {
            movingObject.entityHit.setFire(25);
            if (this.worldObj.getCurrentDate().get(2) + 1 == 10 && this.worldObj.getCurrentDate().get(5) >= 20 || this.worldObj.getCurrentDate().get(2) + 1 == 11 && this.worldObj.getCurrentDate().get(5) <= 3)
            	movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 36.0F);
            else
            	movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 24.0F);
            
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(5.0F);
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSourceExtra.spooky, 16.0F);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          byte b0 = 2;
          if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
            b0 = 15;
          } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            b0 = 50;
          }
          if (b0 > 0) {
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 2));
          }
        }
      }
      this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 0.0F, false, false);
      setDead();
    }
  }
}
