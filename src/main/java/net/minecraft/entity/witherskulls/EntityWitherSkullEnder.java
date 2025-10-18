package net.minecraft.entity.witherskulls;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityEnderWither;
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
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherSkullEnder
  extends EntityWitherSkull
{
  public EntityWitherSkullEnder(World worldIn)
  {
    super(worldIn);
    setSize(0.5F, 0.5F);
  }
  
  public EntityWitherSkullEnder(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(0.5F, 0.5F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityWitherSkullEnder(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(0.5F, 0.5F);
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if (movingObject.entityHit != null && !(movingObject.entityHit instanceof EntityEnderman) && !(movingObject.entityHit instanceof EntityDragon) && !(movingObject.entityHit instanceof EntityEnderWither))
      {
        if (this.shootingEntity != null)
        {
          if (movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 6.0F))
          {
              if ((movingObject.entityHit instanceof EntityLivingBase))
              {
                  int i = MathHelper.floor_double(this.posX);
                  int j = MathHelper.floor_double(this.posY);
                  int k = MathHelper.floor_double(this.posZ);
                  int i1 = i + MathHelper.getRandomIntegerInRange(this.rand, 7, 16) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                  int j1 = j + MathHelper.getRandomIntegerInRange(this.rand, 7, 16) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);
                  int k1 = k + MathHelper.getRandomIntegerInRange(this.rand, 7, 16) * MathHelper.getRandomIntegerInRange(this.rand, -1, 1);

                  EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase) movingObject.entityHit, i1, j1, k1, 12.0F);
                  
                  if (this.worldObj.isAirBlock(new BlockPos(i1, j1, k1)) && World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(i1, j1 - 1, k1)) && this.worldObj.checkNoEntityCollision(movingObject.entityHit.getEntityBoundingBox(), movingObject.entityHit) && this.worldObj.getCollidingBoundingBoxes(movingObject.entityHit, movingObject.entityHit.getEntityBoundingBox()).isEmpty())
                  {
                      if (!net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event))
                      { 
                    	  for (int i11 = 0; i11 < 512; ++i11)
                    		  movingObject.entityHit.setPositionAndUpdate(event.targetX, event.targetY, event.targetZ);
                          movingObject.entityHit.fallDistance = 0.0F;
                          this.worldObj.playSoundEffect(i1, j1, k1, "mob.endermen.portal", 1.0F, 1.0F);
                          this.playSound("mob.endermen.portal", 1.0F, 1.0F);
                          movingObject.entityHit.playSound("mob.endermen.portal", 1.0F, 1.0F);
                    	  movingObject.entityHit.attackEntityFrom(DamageSource.fall, event.attackDamage);
                      }
                  }
                  else
                  {
                	  movingObject.entityHit.attackEntityFrom(DamageSource.fall, 12.0F);
                  }
              }
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(5.0F);
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSource.fall, 8.0F);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          byte b0 = 2;
          if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
            b0 = 10;
          } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            b0 = 40;
          }
          if (b0 > 0) {
              ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20 * b0, 1));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 1));
          }
        }
      }
      if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
        this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.5F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
        this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 2.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      } else {
        this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
      }
      setDead();
    }
  }
}
