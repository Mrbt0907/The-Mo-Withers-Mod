package net.minecraft.entity.witherskulls;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityRichWither;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
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

public class EntityMettalicSkull
  extends EntityWitherSkull
{
  public EntityMettalicSkull(World worldIn)
  {
    super(worldIn);
    setSize(1F, 1F);
  }
  
  public EntityMettalicSkull(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(1F, 1F);
  }
  
  protected float getMotionFactor()
  {
    return 0.95F;
  }
  
  @SideOnly(Side.CLIENT)
  public EntityMettalicSkull(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(1F, 1F);
  }
  
  protected void entityInit()
  {
	    this.dataWatcher.addObject(20, new Integer(0));
  }

  public int getSkullType()
  {
      return Math.max(this.dataWatcher.getWatchableObjectInt(20) % 7, 0);
  }
  
  public void setSkullType(int p_82215_1_)
  {
    this.dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
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
            if (block.getBlockHardness(worldObj, blockpos) != -1F && !block.isOpaqueCube() && block != Blocks.iron_block && block != Blocks.gold_block && block != Blocks.emerald_block && block != Blocks.diamond_block) {
          	  this.setMettalic(worldObj, blockpos);
            }
            
            float damage = 60F;
            
            switch (this.getSkullType())
            {
    	          case 0:
    	        	damage = 20.0F;
    	        	break;
    	          case 1:
    	        	damage = 30.0F;
    	        	break;
    	          case 2:
    	        	damage = 45.0F;
    	        	break;
    	          case 3:
    	        	damage = 60.0F;
    	        	break;
    	          case 4:
    	        	damage = 10.0F;
    	        	break;
    	          case 5:
    	        	damage = 15.0F;
    	        	break;
    	          case 6:
    	        	damage = 25.0F;
    	        	break;
    	          default:
    	        	damage = 20.0F;
            }
    	      
            
          if (movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), this.shootingEntity instanceof EntityRichWither ? 30F : damage))
          {
        	  
            if (!movingObject.entityHit.isEntityAlive())
            {
              this.shootingEntity.heal(10.0F);
              for (int j = 0; j < 9 + movingObject.entityHit.height + movingObject.entityHit.width; ++j)
              {
            	  if (this.shootingEntity instanceof EntityRichWither)
            	  {
            		  ((EntityRichWither)this.shootingEntity).earnMoney(1);
            	  }

            	  this.setMettalicItem(worldObj, movingObject.entityHit);
              }
              
              if (this.shootingEntity instanceof EntityRichWither)
              this.worldObj.playSoundEffect(this.shootingEntity.posX, this.shootingEntity.posY, this.shootingEntity.posZ, "mowithers:WeHaveLosers", 4.0F, 1.0F);
            }
            else
            {
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
          if (block.getBlockHardness(worldObj, blockpos) != -1F && !block.isOpaqueCube() && block != Blocks.iron_block && block != Blocks.gold_block && block != Blocks.emerald_block && block != Blocks.diamond_block) {
        	  this.setMettalic(worldObj, blockpos);
          }
      }
      this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
      setDead();
    }
  }
  
  protected int setMettalicItem(World world, Entity entity)
  {
      switch (this.getSkullType())
      {
          case 0:
        	  entity.dropItem(Items.iron_ingot, 1);
              return 0;
          case 1:
        	  entity.dropItem(Items.gold_ingot, 1);
              return 1;
          case 2:
        	  entity.dropItem(Items.diamond, 1);
              return 2;
          case 3:
        	  entity.dropItem(Items.emerald, 1);
              return 3;
          case 4:
        	  entity.dropItem(Items.coal, 1);
              return 4;
          case 5:
        	  entity.dropItem(Items.redstone, 1);
              return 5;
          case 6:
        	  entity.entityDropItem(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), 0.0F);
              return 6;
          default:
        	  entity.dropItem(Items.iron_ingot, 1);
              return 0;
      }
  }
  
  protected int setMettalic(World world, BlockPos pos)
  {
      switch (this.getSkullType())
      {
          case 0:
        	  world.setBlockState(pos, Blocks.iron_block.getDefaultState());
              return 0;
          case 1:
        	  world.setBlockState(pos, Blocks.gold_block.getDefaultState());
              return 1;
          case 2:
        	  world.setBlockState(pos, Blocks.diamond_block.getDefaultState());
              return 2;
          case 3:
        	  world.setBlockState(pos, Blocks.emerald_block.getDefaultState());
              return 3;
          case 4:
        	  world.setBlockState(pos, Blocks.coal_block.getDefaultState());
              return 4;
          case 5:
        	  world.setBlockState(pos, Blocks.redstone_block.getDefaultState());
              return 5;
          case 6:
        	  world.setBlockState(pos, Blocks.lapis_block.getDefaultState());
              return 6;
          default:
        	  world.setBlockState(pos, Blocks.iron_block.getDefaultState());
              return 0;
      }
  }
}
