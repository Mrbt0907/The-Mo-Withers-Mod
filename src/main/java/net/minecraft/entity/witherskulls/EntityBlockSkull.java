package net.minecraft.entity.witherskulls;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlockSkull
  extends EntityWitherSkull
{
  public EntityBlockSkull(World worldIn)
  {
    super(worldIn);
    setSize(1F, 1F);
  }
  
  public EntityBlockSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(1F, 1F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityBlockSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(1F, 1F);
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if (movingObject.entityHit != null)
      {
        if (this.shootingEntity != null)
        {
        	movingObject.entityHit.hurtResistantTime = 0;
            BlockPos blockpos = new BlockPos(movingObject.entityHit.posX, movingObject.entityHit.posY + movingObject.entityHit.getEyeHeight(), movingObject.entityHit.posZ);
            Block block = this.worldObj.getBlockState(new BlockPos(blockpos)).getBlock();
            if (block.getBlockHardness(worldObj, blockpos) != -1F && !block.isOpaqueCube()) {
          	  this.setDownBlock(worldObj, blockpos);
            }
            
            float damage = 2500F;
            
            switch (this.getSkullType())
            {
    	          case 0:
    	        	damage = 12.0F;
    	        	break;
    	          case 1:
    	        	damage = 16.0F;
    	        	break;
    	          case 2:
    	        	damage = 60.0F;
    	        	break;
    	          case 3:
    	        	damage = 200.0F;
    	        	break;
    	          case 4:
    	        	damage = 2500.0F;
    	        	break;
    	          default:
    	        	damage = 12.0F;
            }
        	
          if (movingObject.entityHit.attackEntityFrom(this.rand.nextFloat() > 0.6F ? DamageSource.generic : DamageSource.causeMobDamage(this.shootingEntity), damage))
          {
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(5.0F);
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSource.magic, 20.0F);
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
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.poison.id, 20 * b0, 1));
          }
        }
      }
      else
      {
          BlockPos blockpos = movingObject.getBlockPos().offset(movingObject.sideHit);
          Block block = this.worldObj.getBlockState(new BlockPos(blockpos)).getBlock();
          if (block.getBlockHardness(worldObj, blockpos) != -1F && !block.isOpaqueCube()) {
          	  this.setDownBlock(worldObj, blockpos);
          }
      }
      this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      
      setDead();
    }
  }
  
  protected void entityInit()
  {
	  super.entityInit();
	    this.dataWatcher.addObject(20, new Integer(0));
  }

  public int getSkullType()
  {
      return Math.max(this.dataWatcher.getWatchableObjectInt(20) % 5, 0);
  }
  
  public void setSkullType(int p_82215_1_)
  {
    this.dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
  }
  
  protected int setDownBlock(World world, BlockPos pos)
  {
      switch (this.getSkullType())
      {
          case 0:
        	  world.setBlockState(pos, Blocks.sand.getDefaultState());
              return 0;
          case 1:
        	  world.setBlockState(pos, Blocks.gravel.getDefaultState());
              return 1;
          case 2:
        	  world.setBlockState(pos, Blocks.glass.getDefaultState());
              return 2;
          case 3:
        	  world.setBlockState(pos, Blocks.obsidian.getDefaultState());
              return 3;
          case 4:
        	  world.setBlockState(pos, Blocks.bedrock.getDefaultState());
              return 4;
          default:
        	  world.setBlockState(pos, Blocks.sand.getDefaultState());
              return 0;
      }
  }
}
