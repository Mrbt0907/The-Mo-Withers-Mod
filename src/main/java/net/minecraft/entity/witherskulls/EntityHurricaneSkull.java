package net.minecraft.entity.witherskulls;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityAirWither;
import net.minecraft.entity.wither.EntityFriendlyWither;
import net.minecraft.entity.wither.EntityWitherGirl;
import net.minecraft.entity.wither.EntityWitherGirlPink;
import net.minecraft.entity.wither.EntityWitherGirlVoid;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHurricaneSkull
  extends EntityFireball
{
  public EntityHurricaneSkull(World worldIn)
  {
    super(worldIn);
    setSize(0.5F, 0.5F);
  }
  
  public EntityHurricaneSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(0.5F, 0.5F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityHurricaneSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(0.5F, 0.5F);
  }
  
  protected float getMotionFactor()
  {
      return 0.95F;
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote) {
      if (movingObject.entityHit != null)
      {
        if (this.shootingEntity != null)
        {
          if (movingObject.entityHit.attackEntityFrom(DamageSource.setExplosionSource(null), 8.0F))
          {
            setDead();
            
            movingObject.entityHit.hurtResistantTime = 0;
            movingObject.entityHit.motionY += 1F;
            movingObject.entityHit.motionX += this.rand.nextGaussian() - 0.5D;
            movingObject.entityHit.motionZ += this.rand.nextGaussian() - 0.5D;
            
            movingObject.entityHit.attackEntityFrom(DamageSource.inWall, 8.0F);
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(5.0F);
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else
        {
          movingObject.entityHit.attackEntityFrom(DamageSource.inWall, 8.0F);
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
    }
  }
  
  public boolean canBeCollidedWith()
  {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    return false;
  }
  
  public void onUpdate()
  {
    onEntityUpdate();
    
    Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
    Vec3 vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
    vec3 = new Vec3(this.posX, this.posY, this.posZ);
    vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
    if (movingobjectposition != null) {
      vec31 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
    }
    Entity entity = null;
    List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
    double d0 = 0.0D;
    for (int i = 0; i < list.size(); i++)
    {
      Entity entity1 = (Entity)list.get(i);
      if ((entity1.canBeCollidedWith()) && (!entity1.isEntityEqual(this.shootingEntity)))
      {
        float f = 0.3F;
        AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f, f, f);
        MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);
        if (movingobjectposition1 != null)
        {
          double d1 = vec3.distanceTo(movingobjectposition1.hitVec);
          if ((d1 < d0) || (d0 == 0.0D))
          {
            entity = entity1;
            d0 = d1;
          }
        }
      }
    }
    if (entity != null) {
      movingobjectposition = new MovingObjectPosition(entity);
    }
    if (movingobjectposition != null) {
      onImpact(movingobjectposition);
    }
    this.posX += this.motionX;
    this.posY += this.motionY;
    this.posZ += this.motionZ;
    float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
    this.rotationYaw = ((float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) + 90.0F);
    for (this.rotationPitch = ((float)(Math.atan2(f1, this.motionY) * 180.0D / 3.141592653589793D) - 90.0F); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {}
    while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
      this.prevRotationPitch += 360.0F;
    }
    while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
      this.prevRotationYaw -= 360.0F;
    }
    while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
      this.prevRotationYaw += 360.0F;
    }
    this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
    this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
    float f2 = 0.99F;
    if (isInWater())
    {
      for (int j = 0; j < 4; j++)
      {
        float f3 = 0.25F;
        this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * f3, this.posY - this.motionY * f3, this.posZ - this.motionZ * f3, this.motionX, this.motionY, this.motionZ, new int[0]);
      }
      f2 = 0.8F;
    }
    this.motionX += this.accelerationX;
    this.motionY += this.accelerationY;
    this.motionZ += this.accelerationZ;
    this.motionX *= f2;
    this.motionY *= f2;
    this.motionZ *= f2;
    for (int i = 0; i < 4; ++i)
    this.worldObj.spawnParticle(EnumParticleTypes.WATER_DROP, this.posX + (this.rand.nextDouble() * 4D - 2D), this.posY + (this.rand.nextDouble() * 4D - 2D), this.posZ + (this.rand.nextDouble() * 4D - 2D), MathHelper.sin(this.prevRotationYaw * 3.1415927F / 180.0F), -this.motionY * 3.1415927F / 180.0F, -MathHelper.cos(this.prevRotationYaw * 3.1415927F / 180.0F), new int[0]);
    this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY + 0.25D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
    setPosition(this.posX, this.posY, this.posZ);
    this.noClip = true;
    if ((this.motionX * this.motionX + this.motionZ * this.motionZ == 0.0D || this.shootingEntity == null) && (!this.worldObj.isRemote)) {
      setDead();
    }
    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D) {
      this.rotationYaw = ((float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F);
    }
    List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(6.0D, 6.0D, 6.0D));
    if ((list111 != null) && (!list111.isEmpty())) {
      for (int i111 = 0; i111 < list111.size(); i111++)
      {
        Entity entity1 = (Entity)list111.get(i111);
        if ((entity1 != null) && ((entity1 instanceof EntityLivingBase)) && (this.shootingEntity != null) && (entity1 != this.shootingEntity) && (!(entity1 instanceof EntityAirWither)) && (!(entity1 instanceof EntityWitherGirl)) && (!(entity1 instanceof EntityWitherGirlVoid)) && (!(entity1 instanceof EntityFriendlyWither)) && (!(entity1 instanceof EntityWitherGirlPink))) 
        {
        	entity1.attackEntityFrom(DamageSource.setExplosionSource(null), 1F);
            double d2 = entity1.posX - this.posX;
            double d3 = entity1.posY - this.posY;
            double d4 = entity1.posZ - this.posZ;
            double d5 = d2 * d2 + d3 * d3 + d4 * d4;
            entity1.addVelocity(d2 / d5 * 0.25D, d3 / d5 * 0.25D, d4 / d5 * 0.25D);
        }
      }
    }
  }
}
