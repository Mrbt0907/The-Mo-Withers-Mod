package net.minecraft.entity.witherskulls;

import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityPinkWither;
import net.minecraft.entity.wither.EntityWitherGirlPink;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPinkSkull
  extends EntityWitherSkull
{
  public EntityPinkSkull(World worldIn)
  {
    super(worldIn);
    setSize(0.5F, 0.5F);
  }
  
  public EntityPinkSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(0.5F, 0.5F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityPinkSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
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
        movingObject.entityHit.hurtResistantTime = 1;
        if ((this.shootingEntity != null) && ((this.shootingEntity instanceof EntityPinkWither)))
        {
          ((EntityPinkWither)this.shootingEntity).spreadBonemealEffect(movingObject.entityHit.posX, movingObject.entityHit.posY, movingObject.entityHit.posZ, this.worldObj);
          ((EntityPinkWither)this.shootingEntity).spreadBonemealEffect(movingObject.entityHit.posX, movingObject.entityHit.posY - 0.2D, movingObject.entityHit.posZ, this.worldObj);
        }
        if ((this.shootingEntity != null) && ((this.shootingEntity instanceof EntityWitherGirlPink)))
        {
          ((EntityWitherGirlPink)this.shootingEntity).spreadBonemealEffect(movingObject.entityHit.posX, movingObject.entityHit.posY, movingObject.entityHit.posZ, this.worldObj);
          ((EntityWitherGirlPink)this.shootingEntity).spreadBonemealEffect(movingObject.entityHit.posX, movingObject.entityHit.posY - 0.2D, movingObject.entityHit.posZ, this.worldObj);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase)) {
          if (((EntityLivingBase)movingObject.entityHit).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
            ((EntityLivingBase)movingObject.entityHit).attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.shootingEntity, this.shootingEntity), 8.0F);
          } else {
            ((EntityLivingBase)movingObject.entityHit).heal(8.0F);
          }
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          byte b0 = 30;
          int i = 0;
          if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
            b0 = 50;
            i = 2;
          } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            b0 = 80;
            i = 4;
          }
          if (b0 > 0) {
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.regeneration.id, 20 * b0, i));
          }
        }
      }
      else if ((this.shootingEntity != null) && ((this.shootingEntity instanceof EntityPinkWither)))
      {
        ((EntityPinkWither)this.shootingEntity).spreadBonemealEffect(this.posX, this.posY, this.posZ, this.worldObj);
        ((EntityPinkWither)this.shootingEntity).spreadBonemealEffect(this.posX, this.posY - 0.2D, this.posZ, this.worldObj);
      }
      this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      
      setDead();
    }
  }
}
