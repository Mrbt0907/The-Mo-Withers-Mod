package net.minecraft.entity.witherskulls;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityAirWither;
import net.minecraft.entity.wither.EntityAvatarWither;
import net.minecraft.entity.wither.EntityFriendlyWither;
import net.minecraft.entity.wither.EntityVoidWither;
import net.minecraft.entity.wither.EntityWitherGirl;
import net.minecraft.entity.wither.EntityWitherGirlPink;
import net.minecraft.entity.wither.EntityWitherGirlVoid;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlackHole extends Entity
{
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private Block inTile;
    private boolean inGround;
    public EntityLivingBase shootingEntity;
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;

    public EntityBlackHole(World worldIn)
    {
        super(worldIn);
        this.setSize(8.0F, 8.0F);
        this.playSound("mowithers:BlackHole", Float.MAX_VALUE, 1F);
        this.playSound("mowithers:Wind", 1F, 0.5F);
    }

    protected void entityInit() {}

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d1 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
        d1 *= 256.0D;
        return distance < d1 * d1;
    }

    public EntityBlackHole(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
    	this(worldIn);
        this.setSize(8.0F, 8.0F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        double d6 = (double)MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
        this.accelerationX = accelX / d6 * 0.1D;
        this.accelerationY = accelY / d6 * 0.1D;
        this.accelerationZ = accelZ / d6 * 0.1D;
    }

    public EntityBlackHole(World worldIn, EntityPlayer shooter, double accelX, double accelY, double accelZ)
    {
        this(worldIn);
        this.shootingEntity = shooter;
        this.setSize(8.0F, 8.0F);
        this.setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = this.motionY = this.motionZ = 0.0D;
        accelX += this.rand.nextGaussian() * 0.4D;
        accelY += this.rand.nextGaussian() * 0.4D;
        accelZ += this.rand.nextGaussian() * 0.4D;
        double d3 = (double)MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
        this.accelerationX = accelX / d3 * 0.1D;
        this.accelerationY = accelY / d3 * 0.1D;
        this.accelerationZ = accelZ / d3 * 0.1D;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (!this.worldObj.isRemote && (this.shootingEntity != null && this.shootingEntity.isDead || !this.worldObj.isBlockLoaded(new BlockPos(this))))
        {
            this.setDead();
        }
        else
        {
            super.onUpdate();

            if (this.inGround)
            {
                if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile)
                {
                    ++this.ticksAlive;

                    if (this.ticksAlive == 600)
                    {
                        this.setDead();
                    }

                    return;
                }

                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksAlive = 0;
                this.ticksInAir = 0;
            }
            else
            {
                ++this.ticksInAir;
            }

            Vec3 vec3 = new Vec3(this.posX, this.posY, this.posZ);
            Vec3 vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
            vec3 = new Vec3(this.posX, this.posY, this.posZ);
            vec31 = new Vec3(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null)
            {
                vec31 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }
            
            if (this.shootingEntity == null || movingobjectposition != null || (this.shootingEntity != null && this.getDistanceSqToEntity(this.shootingEntity) > 20000D))
            {
            	List list1111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(64.0D, 64.0D, 64.0D));
        	    if ((list1111 != null) && (!list1111.isEmpty())) {
        	      for (int i111 = 0; i111 < list1111.size(); i111++)
        	      {
        	        Entity entity1 = (Entity)list1111.get(i111);
        	        if ((entity1 != null) && ((entity1 instanceof EntityLiving))) 
        	        {
        	        	if (this.shootingEntity != null && this.shootingEntity instanceof EntityPlayer)
        	        		if (entity1 instanceof EntityEnderman)
        	        			entity1.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.shootingEntity), 10000F);
        	        		else
        	        			entity1.attackEntityFrom(DamageSource.causeIndirectMagicDamage((EntityPlayer)this.shootingEntity, this), 10000F);
        	        	else
        	        		entity1.attackEntityFrom(DamageSource.setExplosionSource(null), 10000F);
        	        }
        	      }
        	    }

        	    if (this.shootingEntity != null && !this.worldObj.isRemote)
                this.worldObj.newExplosion(this.shootingEntity, this.posX, this.posY, this.posZ, 100F, false, false);
        	    if (!this.worldObj.isRemote)
                this.setDead();
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.rotationYaw = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) + 90.0F;

            for (this.rotationPitch = (float)(Math.atan2((double)f1, this.motionY) * 180.0D / Math.PI) - 90.0F; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
            {
                ;
            }

            while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
            {
                this.prevRotationPitch += 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw < -180.0F)
            {
                this.prevRotationYaw -= 360.0F;
            }

            while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
            {
                this.prevRotationYaw += 360.0F;
            }

            this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
            this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
            float f2 = this.getMotionFactor();

            if (this.isInWater())
            {
                for (int j = 0; j < 4; ++j)
                {
                    float f3 = 0.25F;
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * (double)f3, this.posY - this.motionY * (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ, new int[0]);
                }
            }
            
      	  if (this.ticksExisted % 200 == 0)
      	  {
      		  this.playSound("mowithers:BlackHole", Float.MAX_VALUE, 1F);
      		this.playSound("mowithers:Wind", 1F, 0.75F);
      	  }
      	  
          List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(72.0D, 72.0D, 72.0D));
    	    if ((list111 != null) && !this.worldObj.isRemote && (!list111.isEmpty())) {
    	      for (int i111 = 0; i111 < list111.size(); i111++)
    	      {
    	        Entity entity = (Entity)list111.get(i111);
    	        if ((entity != null) && !entity.isEntityEqual(this.shootingEntity) && (entity instanceof EntityLivingBase) && !(entity instanceof EntityVoidWither) && !(entity instanceof EntityWitherGirl) && !(entity instanceof EntityWitherGirlPink) && !(entity instanceof EntityWitherGirlVoid)) 
    	        {
    	        	if (entity.posY < this.posY)
    	        		entity.motionY += 0.025F;
    	        	double d1 = 4D;
    	        	double d2 = (this.posX + (rand.nextGaussian() * 4D)) - entity.posX;
                    double d3 = (this.posY + (rand.nextGaussian() * 4D)) - entity.posY;
                    double d4 = (this.posZ + (rand.nextGaussian() * 4D)) - entity.posZ;
                    double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                    entity.addVelocity(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
                    if (entity.getDistanceSqToEntity(this) <= 36D)
                    {
                    	entity.attackEntityFrom(DamageSource.outOfWorld, 4.0F);
                    }
                    if (entity.getDistanceSqToEntity(this) <= 4D)
                    {
                  	  if (entity instanceof EntityAvatarWither)
          	        		this.shootingEntity = null;
                  	  else
                  	  {
                  		  entity.hurtResistantTime = 0;
                  		  entity.getDataWatcher().updateObject(6, Float.valueOf(MathHelper.clamp_float(((EntityLivingBase) entity).getHealth() - 50F, 0.0F, ((EntityLivingBase) entity).getMaxHealth())));
                     	  entity.attackEntityFrom(DamageSource.outOfWorld, 50F);
                      	  
                      	  if (!entity.isEntityAlive())
                      	  {
                      		  if (entity instanceof EntityLiving)
                      			  ((EntityLiving)entity).spawnExplosionParticle();
                      		  entity.setDead();
                      	  }
                  	  }
                    }
    	        }
    	        if ((entity != null) && (entity instanceof EntityItem || entity instanceof EntityThrowable || entity instanceof EntityFireball || entity instanceof EntityFallingBlock || entity instanceof EntityFishHook || entity instanceof EntityArrow || entity instanceof EntityXPOrb || entity instanceof EntityTNTPrimed || entity instanceof EntityMinecart || entity instanceof EntityBoat) && !(entity instanceof EntityVoidSkull)) 
    	        {
    	        	double d1 = 4D;
    	        	double d2 = (this.posX + (rand.nextGaussian() * 4D)) - entity.posX;
                    double d3 = (this.posY + (rand.nextGaussian() * 4D)) - entity.posY;
                    double d4 = (this.posZ + (rand.nextGaussian() * 4D)) - entity.posZ;
                    double d5 = d2 * d2 + d3 * d3 + d4 * d4;
                    entity.setPosition(entity.posX, entity.posY, entity.posZ);
                    entity.addVelocity(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
                    if (entity.getDistanceSqToEntity(this) <= 4D)
                    {
                  	  if (entity instanceof EntityWitherSkullAvatar)
                  		  this.shootingEntity = null;

                  	  entity.setDead();
                    }
    	        }
    	      }
    	    }
        	
        	int i = MathHelper.floor_double(this.posY);
            int i1 = MathHelper.floor_double(this.posX);
            int j1 = MathHelper.floor_double(this.posZ);
            for (int l1 = -12; l1 <= 12; l1++) {
              for (int i2 = -12; i2 <= 12; i2++) {
                for (int j = -12; j <= 12; j++)
                {
                  int j2 = i1 + l1;
                  int k = i + j;
                  int l = j1 + i2;
                  Block block = this.worldObj.getBlockState(new BlockPos(j2, k, l)).getBlock();
                  if ((block.getMaterial() != Material.air && (block.getMaterial().isLiquid() || this.rand.nextInt(10) == 0) && !this.worldObj.isRemote && this.worldObj.isAreaLoaded(this.getPosition().add(-32, -32, -32), this.getPosition().add(32, 32, 32)) && block.getBlockHardness(this.worldObj, new BlockPos(j2, k, l)) != -1))
                  {
                  	  if (block.getMaterial().isLiquid())
                  		  this.worldObj.setBlockToAir(new BlockPos(j2, k, l));
                  	  else
                  		  this.worldObj.spawnEntityInWorld(new EntityFallingBlock(worldObj, j2, k + 0.5D, l, block.getDefaultState()));
                  }
                }
              }
            }

            for (int l1 = -4; l1 <= 4; l1++) {
                for (int i2 = -4; i2 <= 4; i2++) {
                  for (int j = -4; j <= 4; j++)
                  {
                    int j2 = i1 + l1;
                    int k = i + j;
                    int l = j1 + i2;
                    Block block = this.worldObj.getBlockState(new BlockPos(j2, k, l)).getBlock();
                    if ((block.getMaterial() != Material.air && !this.worldObj.isRemote && this.worldObj.isAreaLoaded(this.getPosition().add(-32, -32, -32), this.getPosition().add(32, 32, 32)) && block.getBlockHardness(this.worldObj, new BlockPos(j2, k, l)) != -1))
                    {
                    	  if (block.getMaterial().isLiquid())
                    		  this.worldObj.setBlockToAir(new BlockPos(j2, k, l));
                    	  else
                    		  this.worldObj.spawnEntityInWorld(new EntityFallingBlock(worldObj, j2, k + 0.5D, l, block.getDefaultState()));
                    }
                  }
                }
              }

            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= (double)f2;
            this.motionY *= (double)f2;
            this.motionZ *= (double)f2;
            this.setPosition(this.posX, this.posY, this.posZ);
            for (int k = 0; k < 3; k++)
            for (int j = 6; j < (this.ticksExisted <= 256 ? (this.ticksExisted * 0.25F) : 64); j++) {
                float f6 = this.ticksExisted * (float)Math.PI * 0.00125F + j;
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextGaussian() - 0.5D) + (MathHelper.cos(f6) * j), this.posY + this.rand.nextFloat(), this.posZ + (this.rand.nextGaussian() - 0.5D) + (MathHelper.sin(f6) * j), this.motionX + this.accelerationX, this.motionY + this.accelerationY, this.motionZ + this.accelerationZ, new int[0]);
                this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextGaussian() - 0.5D) + (MathHelper.cos(f6) * j), this.posY + this.rand.nextFloat(), this.posZ + (this.rand.nextGaussian() - 0.5D) + (MathHelper.sin(f6) * j), this.motionX + this.accelerationX, this.motionY + this.accelerationY, this.motionZ + this.accelerationZ, new int[0]);
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextGaussian() - 0.5D) - (MathHelper.cos(f6) * j), this.posY + this.rand.nextFloat(), this.posZ + (this.rand.nextGaussian() - 0.5D) - (MathHelper.sin(f6) * j), this.motionX + this.accelerationX, this.motionY + this.accelerationY, this.motionZ + this.accelerationZ, new int[0]);
                this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextGaussian() - 0.5D) - (MathHelper.cos(f6) * j), this.posY + this.rand.nextFloat(), this.posZ + (this.rand.nextGaussian() - 0.5D) - (MathHelper.sin(f6) * j), this.motionX + this.accelerationX, this.motionY + this.accelerationY, this.motionZ + this.accelerationZ, new int[0]);
            }
        }
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */
    protected float getMotionFactor()
    {
        return 0.6F;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setShort("xTile", (short)this.xTile);
        tagCompound.setShort("yTile", (short)this.yTile);
        tagCompound.setShort("zTile", (short)this.zTile);
        ResourceLocation resourcelocation = (ResourceLocation)Block.blockRegistry.getNameForObject(this.inTile);
        tagCompound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        tagCompound.setTag("direction", this.newDoubleNBTList(new double[] {this.motionX, this.motionY, this.motionZ}));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        this.xTile = tagCompund.getShort("xTile");
        this.yTile = tagCompund.getShort("yTile");
        this.zTile = tagCompund.getShort("zTile");

        if (tagCompund.hasKey("inTile", 8))
        {
            this.inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
        }
        else
        {
            this.inTile = Block.getBlockById(tagCompund.getByte("inTile") & 255);
        }

        this.inGround = tagCompund.getByte("inGround") == 1;

        if (tagCompund.hasKey("direction", 9))
        {
            NBTTagList nbttaglist = tagCompund.getTagList("direction", 6);
            this.motionX = nbttaglist.getDouble(0);
            this.motionY = nbttaglist.getDouble(1);
            this.motionZ = nbttaglist.getDouble(2);
        }
        else
        {
            this.setDead();
        }
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    public float getCollisionBorderSize()
    {
        return 1.0F;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
    	return false;
    }
}