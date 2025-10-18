package net.minecraft.entity.witherskulls;

import net.minecraft.MoWithers.DamageSourceExtra;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMechaSkull
  extends EntityWitherSkull
{
	public Entity assginedEntity;
	public int lifetime;
	
  public EntityMechaSkull(World worldIn)
  {
    super(worldIn);
    setSize(0.5F, 0.5F);
  }
  
  public EntityMechaSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(0.5F, 0.5F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityMechaSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
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
        	if (movingObject.entityHit instanceof EntityDragonPart)
        		movingObject.entityHit.attackEntityFrom(DamageSource.setExplosionSource(null), 12.0F);
        	
          if (movingObject.entityHit.attackEntityFrom(DamageSource.magic, 8.0F))
          {
            movingObject.entityHit.setFire(25);
            
            movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 24.0F);
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(5.0F);
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSource.magic, 8.0F);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          byte b0 = 40;
          if (b0 > 0) {
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 1));
          }
        }
      }
      this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1F, false, false);
      setDead();
    }
  }
  
  public void onUpdate()
  {
	  super.onUpdate();
	  
      ++this.lifetime;
      
  	if (this.assginedEntity != null && this.assginedEntity.isEntityAlive() && this.lifetime > 10 + rand.nextInt(20))
  	{
          double d0 = this.assginedEntity.posX - this.posX;
          double d1 = (this.assginedEntity.posY + 0.5D) - this.posY;
          double d2 = this.assginedEntity.posZ - this.posZ;
          float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
          this.motionX = d0 / (double)f2 * (this.getMotionFactor() * 0.75F) * (this.getMotionFactor() * 0.75F) + this.motionX;
          this.motionY = d1 / (double)f2 * (this.getMotionFactor() * 0.75F) * (this.getMotionFactor() * 0.75F) + this.motionY;
          this.motionZ = d2 / (double)f2 * (this.getMotionFactor() * 0.75F) * (this.getMotionFactor() * 0.75F) + this.motionZ;
  	}
  	
  	if (this.lifetime >= 140)
  	{
        this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1F, false, false);
        setDead();
  	}
  }
}
