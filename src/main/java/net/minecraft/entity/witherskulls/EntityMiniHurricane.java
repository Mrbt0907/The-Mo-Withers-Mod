package net.minecraft.entity.witherskulls;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.wither.EntityAirWither;
import net.minecraft.entity.wither.EntityFriendlyWither;
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

public class EntityMiniHurricane extends Entity
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

    public EntityMiniHurricane(World worldIn)
    {
        super(worldIn);
        this.setSize(2.0F, 2.0F);
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
        d1 *= 64.0D;
        return distance < d1 * d1;
    }

    public EntityMiniHurricane(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
    	this(worldIn);
        this.setSize(2.0F, 2.0F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        double d6 = (double)MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
        this.accelerationX = accelX / d6 * 0.1D;
        this.accelerationY = accelY / d6 * 0.1D;
        this.accelerationZ = accelZ / d6 * 0.1D;
    }

    public EntityMiniHurricane(World worldIn, EntityPlayer shooter, double accelX, double accelY, double accelZ)
    {
        this(worldIn);
        this.shootingEntity = shooter;
        this.setSize(2.0F, 2.0F);
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
            
            List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(48.0D, 24.0D, 48.0D));
    	    if ((list111 != null) && (!list111.isEmpty())) {
    	      for (int i111 = 0; i111 < list111.size(); i111++)
    	      {
    	        Entity entity = (Entity)list111.get(i111);
    	        if ((entity != null) && ((entity instanceof EntityLiving))) 
    	        {
    	        	double d1 = 2.5D;
    	        	double d2 = this.posX - entity.posX;
	                double d3 = (this.posY + 3D) - entity.posY;
	                double d4 = this.posZ - entity.posZ;
	                double d5 = d2 * d2 + d3 * d3 + d4 * d4;
	                if (this.isAboveOcean())
	                	d1 *= 1.75D;
	                if (entity instanceof IBossDisplayData)
	                	d1 = 1.5D;
	                entity.addVelocity(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
	                if (entity.getDistanceSqToCenter(new BlockPos(this.posX, this.posY + 6D, this.posZ)) <= 64D)
	                {
        	        	if (this.shootingEntity != null)
        	        		if (entity instanceof EntityEnderman)
        	        			entity.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.shootingEntity), 2F);
        	        		else
        	        			entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage((EntityPlayer)this.shootingEntity, this), 2F);
        	        	else
        	        		entity.attackEntityFrom(DamageSource.setExplosionSource(null), 2F);
	                }
    	        }
    	      }
    	    }

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
            
            if (this.isAboveOcean())
            	this.ticksInAir = 0;
            
            if (!this.isAboveOcean() && this.ticksInAir > 600 && !this.worldObj.isRemote)
            	this.setDead();

            Vec3 vec3 = new Vec3(this.posX, this.posY + 2D, this.posZ);
            Vec3 vec31 = new Vec3(this.posX + this.motionX, this.posY + 2D + this.motionY, this.posZ + this.motionZ);
            MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
            vec3 = new Vec3(this.posX, this.posY + 2D, this.posZ);
            vec31 = new Vec3(this.posX + this.motionX, this.posY + 2D + this.motionY, this.posZ + this.motionZ);

            if (movingobjectposition != null)
            {
                vec31 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }
            
            if (this.shootingEntity == null || movingobjectposition != null || (this.shootingEntity != null && this.getDistanceSqToEntity(this.shootingEntity) > 14400D))
            {
            	List list1111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(32.0D, 16.0D, 32.0D));
        	    if ((list1111 != null) && (!list1111.isEmpty())) {
        	      for (int i111 = 0; i111 < list1111.size(); i111++)
        	      {
        	        Entity entity1 = (Entity)list1111.get(i111);
        	        if ((entity1 != null) && ((entity1 instanceof EntityLiving))) 
        	        {
        	        	if (this.shootingEntity != null)
        	        		if (entity1 instanceof EntityEnderman)
        	        			entity1.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)this.shootingEntity), 18F);
        	        		else
        	        			entity1.attackEntityFrom(DamageSource.causeIndirectMagicDamage((EntityPlayer)this.shootingEntity, this), 18F);
        	        	else
        	        		entity1.attackEntityFrom(DamageSource.setExplosionSource(null), 18F);
        	        }
        	      }
        	    }

        	    if (this.shootingEntity != null)
                this.worldObj.newExplosion(this.shootingEntity, this.posX, this.posY + 3D, this.posZ, 16.0F, false, false);
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

            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= (double)f2;
            this.motionY *= (double)f2;
            this.motionZ *= (double)f2;
            if (this.rand.nextInt((this.isAboveOcean() ? 5 : 20)) == 0 && !this.worldObj.isRemote)
            	this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX + this.accelerationX, this.posY + 3D + this.accelerationY, this.posZ + this.accelerationZ));
            this.setPosition(this.posX, this.posY, this.posZ);
            if (!this.worldObj.isRemote)
            {
                for (int j = 1; j < (this.isAboveOcean() ? 48 : 24); j++) {
                    float f6 = this.ticksExisted * (float)Math.PI * 0.0075F + j;
                    if (this.shootingEntity != null)
                    {
                    	this.worldObj.newExplosion(this.shootingEntity, this.posX + (MathHelper.cos(f6) * (j * 0.5D)), this.posY + 3D, this.posZ + (MathHelper.sin(f6) * (j * 0.5D)), 1.0F, false, false);
                    	this.worldObj.newExplosion(this.shootingEntity, this.posX - (MathHelper.cos(f6) * (j * 0.5D)), this.posY + 3D, this.posZ - (MathHelper.sin(f6) * (j * 0.5D)), 1.0F, false, false);
                    }
                    else
                    {
                    	this.worldObj.newExplosion(this, this.posX + (MathHelper.cos(f6) * (j * 0.5D)), this.posY + 3D, this.posZ + (MathHelper.sin(f6) * (j * 0.2D)), 1.0F, false, false);
                    	this.worldObj.newExplosion(this, this.posX - (MathHelper.cos(f6) * (j * 0.5D)), this.posY + 3D, this.posZ - (MathHelper.sin(f6) * (j * 0.2D)), 1.0F, false, false);
                    }
                }
            }
        }
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */
    protected float getMotionFactor()
    {
        return this.isAboveOcean() ? 0.9F : 0.8F;
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
    
    private boolean isAboveOcean()
    {
    	BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
    	
    	if (biomegenbase == BiomeGenBase.ocean || biomegenbase == BiomeGenBase.deepOcean || biomegenbase == BiomeGenBase.river || biomegenbase == BiomeGenBase.beach || biomegenbase == BiomeGenBase.stoneBeach || biomegenbase == BiomeGenBase.mushroomIslandShore)
        {
            return true;
        }
    	else
    	{
    		return false;
    	}
    }
}