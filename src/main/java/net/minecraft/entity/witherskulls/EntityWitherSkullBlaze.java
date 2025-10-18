package net.minecraft.entity.witherskulls;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityBlazeWither;
import net.minecraft.entity.wither.EntityEnderWither;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherSkullBlaze
  extends EntityWitherSkull
{
  public EntityWitherSkullBlaze(World worldIn)
  {
    super(worldIn);
    setSize(0.5F, 0.5F);
  }
  
  public EntityWitherSkullBlaze(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(0.5F, 0.5F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityWitherSkullBlaze(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(0.5F, 0.5F);
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if (movingObject.entityHit != null && !(movingObject.entityHit instanceof EntityBlaze) && !(movingObject.entityHit instanceof EntityBlazeWither))
      {
        if (this.shootingEntity != null)
        {
          if (movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 18.0F))
          {
            movingObject.entityHit.setFire(25);
            
            movingObject.entityHit.attackEntityFrom(DamageSource.onFire, 24.0F);
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(5.0F);
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSource.onFire, 8.0F);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          byte b0 = 0;
          if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
            b0 = 10;
          } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            b0 = 40;
          }
          if (b0 > 0) {
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 1));
          }
        }
      }
      if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
        this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.5F, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
        this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 2.0F, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      } else {
        this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, true, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      }
      setDead();
    }
  }
}
