package net.minecraft.entity.witherskulls;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityAvatarWither;
import net.minecraft.entity.wither.EntityDementedWither;
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

public class EntityWitherSkullAvatar
  extends EntityWitherSkull
{
  public EntityWitherSkullAvatar(World worldIn)
  {
    super(worldIn);
    setSize(1F, 1F);
  }
  
  public EntityWitherSkullAvatar(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(1F, 1F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityWitherSkullAvatar(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(1F, 1F);
  }
  
  protected float getMotionFactor()
  {
      return 0.9F;
  }
  
  public float getExplosionResistance(Explosion p_180428_1_, World worldIn, BlockPos p_180428_3_, IBlockState p_180428_4_)
  {
      float f = super.getExplosionResistance(p_180428_1_, worldIn, p_180428_3_, p_180428_4_);

      if (!p_180428_4_.getBlock().getMaterial().isLiquid() && p_180428_4_.getBlock() != Blocks.bedrock && p_180428_4_.getBlock() != Blocks.end_portal && p_180428_4_.getBlock() != Blocks.end_portal_frame && p_180428_4_.getBlock() != Blocks.command_block)
      {
          f = Math.min(0.1F, f);
      }

      return f;
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if (movingObject.entityHit != null && !(movingObject.entityHit instanceof EntityAvatarWither))
      {
        if (this.shootingEntity != null)
        {
            if (movingObject.entityHit instanceof EntityLivingBase && movingObject.entityHit instanceof IAnimals && !(movingObject.entityHit instanceof INpc) && !(movingObject.entityHit instanceof IBossDisplayData))
            {
            	movingObject.entityHit.getDataWatcher().updateObject(6, Float.valueOf(0.0F));
                this.shootingEntity.heal(500.0F);
            }
        	
            if (movingObject.entityHit instanceof EntityLivingBase && ((EntityLivingBase)movingObject.entityHit).isEntityUndead())
            {
                if (movingObject.entityHit.attackEntityFrom(DamageSource.outOfWorld, 1000.0F)) {

                	if (!movingObject.entityHit.isEntityAlive())
                    {
                      movingObject.entityHit.motionY = 3.0D;
                      this.shootingEntity.heal(500.0F);
                    }
                    else
                    {
                      movingObject.entityHit.hurtResistantTime = 0;
                      func_174815_a(this.shootingEntity, movingObject.entityHit);
                    }
                  }
            }
            else
            {
                if (movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 10000.0F)) {

                	if (!movingObject.entityHit.isEntityAlive())
                    {
                      movingObject.entityHit.motionY = 3.0D;
                      this.shootingEntity.heal(500.0F);
                    }
                    else
                    {
                      movingObject.entityHit.hurtResistantTime = 0;
                      func_174815_a(this.shootingEntity, movingObject.entityHit);
                    }
                  }
            }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
        }
        setDead();
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
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.poison.id, 20 * b0, 3));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.confusion.id, 20 * b0, 3));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20 * b0, 9));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 20 * b0, 9));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.blindness.id, 20 * b0, 3));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.hunger.id, 20 * b0, 99));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.weakness.id, 20 * b0, 9));
          }
        }
      }
      this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1F + (this.rand.nextFloat() * 5F), false, true);
      
      this.motionX = this.rand.nextFloat() - 0.5F;
      this.motionY = 0.25F + this.rand.nextFloat();
      this.motionZ = this.rand.nextFloat() - 0.5F;
    }
  }
  
  public void onUpdate()
  {
	  super.onUpdate();
	  
      if (this.posY <= 0D) 
    	  this.posY = 0D;
      
      this.noClip = true;
      if ((this.motionX * this.motionX + this.motionZ * this.motionZ == 0.0D) && (!this.worldObj.isRemote)) {
    	    this.playSound("random.explode", 3.0F, 0.6F);
        setDead();
      }
      if (this.shootingEntity == null || (this.shootingEntity != null && this.getDistanceSqToEntity(this.shootingEntity) > 14400D)) {
          this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 14F, false, true);
          this.setDead();
        }
  }
}
