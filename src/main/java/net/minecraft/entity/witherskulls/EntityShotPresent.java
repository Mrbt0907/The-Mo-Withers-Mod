package net.minecraft.entity.witherskulls;

import static net.minecraft.MoWithers.worldgen.ChestGenHooksWither.SPECIALTREASURE;

import java.util.Calendar;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.MoWithers.DamageSourceExtra;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.wither.EntityBabyWither;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityShotPresent
  extends EntityWitherSkull
{
    private static final List CHESTCONTENTPUZZEL = Lists.newArrayList(new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.diamond, 0, 2, 4, 10), new WeightedRandomChestContent(Items.iron_ingot, 0, 5, 10, 30), new WeightedRandomChestContent(Items.gold_ingot, 0, 3, 7, 20), new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 5), new WeightedRandomChestContent(Items.cooked_beef, 0, 5, 10, 30), new WeightedRandomChestContent(Items.baked_potato, 0, 8, 8, 10), new WeightedRandomChestContent(Items.golden_apple, 1, 1, 1, 1), new WeightedRandomChestContent(Items.cookie, 0, 16, 24, 50), new WeightedRandomChestContent(Items.cooked_chicken, 0, 8, 16, 20), new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10)});
	
  public EntityShotPresent(World worldIn)
  {
    super(worldIn);
    setSize(0.5F, 0.5F);
  }
  
  public EntityShotPresent(World worldIn, EntityLivingBase p_i1794_2_, double p_i1794_3_, double p_i1794_5_, double p_i1794_7_)
  {
    super(worldIn, p_i1794_2_, p_i1794_3_, p_i1794_5_, p_i1794_7_);
    setSize(0.5F, 0.5F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityShotPresent(World worldIn, double p_i1795_2_, double p_i1795_4_, double p_i1795_6_, double p_i1795_8_, double p_i1795_10_, double p_i1795_12_)
  {
    super(worldIn, p_i1795_2_, p_i1795_4_, p_i1795_6_, p_i1795_8_, p_i1795_10_, p_i1795_12_);
    setSize(0.5F, 0.5F);
  }
  
  protected void onImpact(MovingObjectPosition movingObject)
  {
    if (!this.worldObj.isRemote)
    {
      if (movingObject.entityHit != null && !(movingObject.entityHit instanceof EntitySnowman))
      {
    	  movingObject.entityHit.hurtResistantTime = 0;
        if (this.shootingEntity != null)
        {
        	if (movingObject.entityHit instanceof EntityPlayer) {
                this.shootingEntity.heal(50.0F);
                if (((EntityPlayer)movingObject.entityHit).capabilities.disableDamage) {
                	movingObject.entityHit.attackEntityFrom(DamageSourceExtra.outOfWorld, 50.0F * (1F + this.rand.nextFloat() * 5F));
                  }
              }
        	
          if (movingObject.entityHit.attackEntityFrom(DamageSourceExtra.santa, 50.0F * (1F + this.rand.nextFloat() * 5F)))
          {
            movingObject.entityHit.setFire(25);
            if (Calendar.getInstance().get(2) + 1 == 12 && Calendar.getInstance().get(5) >= 12 && Calendar.getInstance().get(5) <= 31)
            	movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 200.0F * (1F + this.rand.nextFloat() * 5F));
            else
            	movingObject.entityHit.attackEntityFrom(DamageSource.causeMobDamage(this.shootingEntity), 100.0F * (1F + this.rand.nextFloat() * 5F));
            
            if (!movingObject.entityHit.isEntityAlive()) {
              this.shootingEntity.heal(25.0F);
              if (!this.worldObj.isRemote && this.rand.nextInt(3) == 0)
              {
                  EntitySnowman wither = new EntitySnowman(worldObj);
                  wither.copyLocationAndAnglesFrom(this.shootingEntity);
                  worldObj.spawnEntityInWorld(wither);
              }
            } else {
              func_174815_a(this.shootingEntity, movingObject.entityHit);
            }
          }
        }
        else {
          movingObject.entityHit.attackEntityFrom(DamageSourceExtra.santa, 32.0F);
        }
        if ((movingObject.entityHit instanceof EntityLivingBase))
        {
          byte b0 = 5;
          if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL) {
            b0 = 20;
          } else if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            b0 = 60;
          }
          if (b0 > 0) {
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.wither.id, 20 * b0, 2));
            ((EntityLivingBase)movingObject.entityHit).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 20 * b0, 3));
          }
        }
      }
      this.worldObj.setBlockState(new BlockPos(this.posX, movingObject.entityHit != null ? movingObject.entityHit.posY + movingObject.entityHit.getEyeHeight() : this.posY, this.posZ), Blocks.chest.getDefaultState(), 3);
        TileEntity tileentity1 = this.worldObj.getTileEntity(new BlockPos(this.posX, movingObject.entityHit != null ? movingObject.entityHit.posY + movingObject.entityHit.getEyeHeight() : this.posY, this.posZ));
        if (tileentity1 instanceof TileEntityChest)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(SPECIALTREASURE, rand), (TileEntityChest)tileentity1, ChestGenHooks.getCount(SPECIALTREASURE, rand));
        }
      this.worldObj.newExplosion(this, this.posX, this.posY, this.posZ, 1.0F, false, false);
      setDead();
    }
  }
  
  static
  {
      ChestGenHooks.init(SPECIALTREASURE, CHESTCONTENTPUZZEL, 4, 4);
      ChestGenHooks.addItem(SPECIALTREASURE, new WeightedRandomChestContent(new net.minecraft.item.ItemStack(Items.enchanted_book, 1, 0), 1, 1, 1));
      ChestGenHooks.addItem(SPECIALTREASURE, new WeightedRandomChestContent(new net.minecraft.item.ItemStack(Items.enchanted_book, 1, 0), 1, 1, 1));
  }
}
