package net.minecraft.entity.wither;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.MoWithers.DamageSourceExtra;
import net.minecraft.MoWithers.MoWithersAchievments;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.INpc;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.witherskulls.EntityPinkSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherGirlPink
  extends EntityGolem
  implements IBossDisplayData, IRangedAttackMob, INpc
{
	
    private static final UUID field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier field_110190_br = (new AttributeModifier(field_110189_bq, "Attacking speed boost", 3D, 0)).setSaved(false);
  private float[] field_82220_d = new float[2];
  private float[] field_82221_e = new float[2];
  private float[] field_82217_f = new float[2];
  private float[] field_82218_g = new float[2];
  private int[] field_82223_h = new int[2];
  private int[] field_82224_i = new int[2];
  private int blockBreakCounter;
  public boolean shouldStartFlying;
  Village villageObj;
  private int homeCheckTimer;
  private int musicTimer;
  private int blinkTimer;
  public float swimSwurllySwurl;
  public float swimSwurllySwurlCooldown;
  private boolean wasFed;
  public boolean isConcerned;
  public boolean isFrightened;
  public boolean sugerRush;
  public boolean isDoingIt;
  public boolean isPregnant;
  public int timeSinceDoingIt;
  public int bangTimer;
  private int numbOfOthers;
  private EntityAIPinkWitherGirlFollowPlayer aiFollowLover = new EntityAIPinkWitherGirlFollowPlayer(this, 0.5D, 12.0F, 4.0F);
  private EntityAIPinkWitherGirlFollowFather aiFollowFather = new EntityAIPinkWitherGirlFollowFather(this, 0.5D, 12.0F, 4.0F);
  private EntityAIPinkWitherGirlFollowFather aiFollowDaddy = new EntityAIPinkWitherGirlFollowFather(this, 1.0D, 8.0F, 3.0F);
  
  public EntityWitherGirlPink(World worldIn)
  {
    super(worldIn);
    setSize(0.45F, 1.850001F);
    setHealth(getMaxHealth());
    this.isImmuneToFire = true;
    this.ignoreFrustumCheck = true;
    ((PathNavigateGround)getNavigator()).func_179693_d(true);
    this.tasks.addTask(0, new EntityAIAvoidEntity(this, new Predicate()
    {
      public boolean func_179958_a(Entity p_179958_1_)
      {
        return EntityWitherGirlPink.this.isChild() && ((p_179958_1_ instanceof IMob));
      }
      
      public boolean apply(Object p_apply_1_)
      {
        return func_179958_a((Entity)p_apply_1_);
      }
    }, 8.0F, 1.0D, 1.0D));
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIOpenDoor(this, true));
    this.tasks.addTask(1, new EntityAIBreakDoor(this));
    this.tasks.addTask(1, new EntityAITempt(this, 1.0D, Items.sugar, false));
    this.tasks.addTask(1, new EntityAITempt(this, 1.0D, Items.golden_apple, false));
    this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 0.6D, true));
    this.tasks.addTask(2, new EntityAIPanic(this, 1.1D));
    this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
    this.tasks.addTask(5, new EntityAIWatchClosest2(this, EntityIronGolem.class, 6.0F, 0.1F));
    this.tasks.addTask(5, new EntityAIWatchClosest2(this, EntityVillager.class, 6.0F, 0.1F));
    this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 6.0F, 1.0F));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.experienceValue = 100;
  }
  
  public boolean canAttackClass(Class p_70686_1_)
  {
      return p_70686_1_ != EntityWitherGirl.class && p_70686_1_ != EntityWitherGirlPink.class && p_70686_1_ != EntityWitherGirlVoid.class;
  }
  
  public float getRenderSizeModifier()
  {
    return this.isSneaking() ? 1.75F : 1.0F;
  }
  
  public int getTalkInterval()
  {
    if (this.sugerRush)
    	return 2;
    if (isChild())
        return 20;
    if (isChild() && this.sugerRush)
        return 1;
    else
    	return 80;
  }
  
  protected PathNavigate func_175447_b(World worldIn)
  {
	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
      return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? new PathNavigateSwimmer(this, worldIn) : new PathNavigateGround(this, worldIn);
  }
  
  public float getEyeHeight()
  {
	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
	  if (this.isChild())
	  {
		  return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? 0.15F : (this.isRiding() ? 0.3F : 0.6F);
	  }
	  else
	  {
		  return block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid() ? 0.2F : (this.isSneaking() ? 0.875F : 1.5725F);
	  }
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.7D);
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(24.0D);
    getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
  }
  
  protected void entityInit()
  {
    super.entityInit();
    getDataWatcher().addObject(11, Byte.valueOf((byte)0));
    getDataWatcher().addObject(12, Byte.valueOf((byte)0));
    getDataWatcher().addObject(13, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
    this.dataWatcher.addObject(17, new Integer(0));
    this.dataWatcher.addObject(18, new Integer(0));
    this.dataWatcher.addObject(19, new Integer(0));
    this.dataWatcher.addObject(20, new Integer(0));
    this.dataWatcher.addObject(22, "");
    this.dataWatcher.addObject(23, "");
  }
  
  public String getFatherId()
  {
      return this.dataWatcher.getWatchableObjectString(22);
  }

  public void setFatherId(String ownerUuid)
  {
      this.dataWatcher.updateObject(22, ownerUuid);
  }

  public EntityPlayer getFather()
  {
      try
      {
          UUID uuid = UUID.fromString(this.getFatherId());
          return uuid == null ? null : this.worldObj.getPlayerEntityByUUID(uuid);
      }
      catch (IllegalArgumentException illegalargumentexception)
      {
          return null;
      }
  }

  public boolean isFather(EntityPlayer entityIn)
  {
      return entityIn == this.getFather();
  }
  
  public String getMateId()
  {
      return this.dataWatcher.getWatchableObjectString(23);
  }

  public void setMateId(String ownerUuid)
  {
      this.dataWatcher.updateObject(23, ownerUuid);
  }

  public EntityPlayer getMate()
  {
      try
      {
          UUID uuid = UUID.fromString(this.getMateId());
          return uuid == null ? null : this.worldObj.getPlayerEntityByUUID(uuid);
      }
      catch (IllegalArgumentException illegalargumentexception)
      {
          return null;
      }
  }

  public boolean isMate(EntityPlayer entityIn)
  {
      return entityIn == this.getMate();
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Invul", getInvulTime());
    tagCompound.setBoolean("Rheia", isRheia());
    tagCompound.setBoolean("IsBaby", isChild());
    tagCompound.setBoolean("InLove", isInLove());
    tagCompound.setInteger("TimeBetweenBangs", this.timeSinceDoingIt);
    if (this.getFather() == null)
    {
        tagCompound.setString("FatherUUID", "");
    }
    else
    {
        tagCompound.setString("FatherUUID", this.getFatherId());
    }
    if (this.getMate() == null)
    {
        tagCompound.setString("MateUUID", "");
    }
    else
    {
        tagCompound.setString("MateUUID", this.getMateId());
    }
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setInvulTime(tagCompund.getInteger("Invul"));
    setRheia(tagCompund.getBoolean("Rheia"));
    setChild(tagCompund.getBoolean("IsBaby"));
    setInLove(tagCompund.getBoolean("InLove"));
    if (tagCompund.hasKey("TimeBetweenBangs", 99)) {
        this.timeSinceDoingIt = tagCompund.getInteger("TimeBetweenBangs");
      }
    String s2 = "";

    if (tagCompund.hasKey("FatherUUID", 8))
    {
        s2 = tagCompund.getString("FatherUUID");
    }
    else
    {
        String s11 = tagCompund.getString("Father");
        s2 = PreYggdrasilConverter.func_152719_a(s11);
    }

    if (s2.length() > 0)
    {
        this.setFatherId(s2);
    }
    String s3 = "";

    if (tagCompund.hasKey("MateUUID", 8))
    {
        s3 = tagCompund.getString("MateUUID");
    }
    else
    {
        String s11 = tagCompund.getString("Mate");
        s3 = PreYggdrasilConverter.func_152719_a(s11);
    }

    if (s3.length() > 0)
    {
        this.setMateId(s3);
    }
  }
  
  public boolean isChild()
  {
    return getDataWatcher().getWatchableObjectByte(11) == 1;
  }
  
  protected int getExperiencePoints(EntityPlayer player)
  {
    if (isChild()) {
      this.experienceValue = ((int)(this.experienceValue * 2.5F));
    }
    return super.getExperiencePoints(player);
  }
  
  public void setChild(boolean childZombie)
  {
    getDataWatcher().updateObject(11, Byte.valueOf((byte)(childZombie ? 1 : 0)));
  }
  
  public boolean isRheia()
  {
    return getDataWatcher().getWatchableObjectByte(12) == 1;
  }
  
  public void setRheia(boolean childZombie)
  {
    getDataWatcher().updateObject(12, Byte.valueOf((byte)(childZombie ? 1 : 0)));
  }
  
  public boolean isInLove()
  {
    return getDataWatcher().getWatchableObjectByte(13) == 1;
  }
  
  public void setInLove(boolean childZombie)
  {
	  getDataWatcher().updateObject(13, Byte.valueOf((byte)(childZombie ? 1 : 0)));
  }
  
  protected String getLivingSound()
  {
    return this.isPregnant ? null : "mowithers:WitherGirlPinkIdle";
  }
  
  protected String getHurtSound()
  {
    return "mowithers:WitherGirlPinkHurt";
  }
  
  protected String getDeathSound()
  {
    return "mowithers:WitherGirlPinkDeath";
  }
  
  protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_)
  {
    Block.SoundType soundtype = p_180429_2_.stepSound;
    if (this.worldObj.getBlockState(p_180429_1_.up()).getBlock() == Blocks.snow_layer)
    {
      soundtype = Blocks.snow_layer.stepSound;
      playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.5F, soundtype.getFrequency());
    }
    else if (!p_180429_2_.getMaterial().isLiquid())
    {
      playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.5F, soundtype.getFrequency());
    }
  }
  
  public Village getVillage()
  {
    return this.villageObj;
  }
  
  public void spreadBonemealEffect(double posX, double posY, double posZ, World world)
  {
    if (this.blockBreakCounter <= 0) {
      this.blockBreakCounter = 20;
    }
    BlockPos blockpos = new BlockPos(posX, posY, posZ);
    IBlockState iblockstate = world.getBlockState(blockpos);
    Block block = iblockstate.getBlock();
    if ((block != null) && (block.getMaterial() == Material.ground) && block != Blocks.farmland && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
      if ((this.worldObj.getBiomeGenForCoords(new BlockPos(posX, posY, posZ)) == BiomeGenBase.mushroomIsland) || (this.worldObj.getBiomeGenForCoords(new BlockPos(posX, posY, posZ)) == BiomeGenBase.mushroomIslandShore)) {
        world.setBlockState(blockpos, Blocks.mycelium.getDefaultState());
      } else {
        world.setBlockState(blockpos, Blocks.grass.getDefaultState());
      }
    }
    if ((block != null) && ((block instanceof IGrowable)) && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))
    {
      IGrowable igrowable = (IGrowable)block;
      if (igrowable.canGrow(world, blockpos, iblockstate, this.worldObj.isRemote)) {
        if (!world.isRemote) {
          if (((igrowable instanceof BlockCocoa)) || ((igrowable instanceof BlockCrops)) || ((igrowable instanceof BlockGrass)) || ((igrowable instanceof BlockMushroom)) || ((igrowable instanceof BlockSapling)) || ((igrowable instanceof BlockStem)) || ((igrowable instanceof BlockTallGrass))) {
            igrowable.grow(world, world.rand, blockpos, iblockstate);
          }
        }
      }
    }
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isBlinking()
  {
      return this.dataWatcher.getWatchableObjectByte(16) != 0;
  }
  
  public void onLivingUpdate()
  {
	  if (this.sugerRush && this.rand.nextInt(40) == 0 && this.ticksExisted % 20 == 0)
		  this.sugerRush = false;
	  
	  if (this.isChild())
	  {
		  this.numbOfOthers = 0;
		  this.timeSinceDoingIt = 0;
		  this.isDoingIt = false;
		  this.isPregnant = false;
		  this.bangTimer = 0;
		  this.setInLove(false);
		  this.setAttackTarget(null);
	  }
	  
	  if (this.rotationYaw < (this.rotationYawHead - 30))
		  this.rotationYaw = (this.rotationYawHead - 30);
	  
	  if (this.rotationYaw > (this.rotationYawHead + 30))
		  this.rotationYaw = (this.rotationYawHead + 30);
	  
	  if (this.isInLove() && this.timeSinceDoingIt < 4000)
	  {
		  EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 100D);
		  if (player != null && (this.isInLove() || (this.getFather() != null && player == this.getFather())))
		  {
			    if (!this.isChild() && player.getDistanceSqToEntity(this) <= 9D && player.getSleepTimer() > 0 && (!this.worldObj.isRemote) && !this.worldObj.isDaytime() && !this.worldObj.canSeeSky(new BlockPos(this.posX, (double)Math.round(this.posY), this.posZ)))
				{
                    this.bangTimer = player.getSleepTimer();
                    this.isDoingIt = true;
                    this.mountEntity(player);
                    this.limbSwingAmount = 1F;
                    if (this.ticksExisted % 15 == 0)
                    {
                        this.attackEntityFrom(DamageSource.outOfWorld, 1F);
                        this.heal(1F);
                    }
				}
		  }
	  }
	  
	  if (this.timeSinceDoingIt < 4000 && !this.worldObj.isRemote)
	  {
		  this.isPregnant = false;
		  this.setSneaking(false);
	  }
	  
	  if (this.timeSinceDoingIt < 4000 && !this.worldObj.isRemote)
	  {
		  this.isPregnant = false;
		  this.setSneaking(false);
	  }
	  
	  if (this.timeSinceDoingIt > 4000 && !(this.timeSinceDoingIt < 4000) && !this.worldObj.isRemote)
	  {
		  this.setInLove(true);
		  this.isPregnant = true;
		  this.setSneaking(true);
		  this.getNavigator().clearPathEntity();
		  if (this.ticksExisted % 52 == 0)
			  this.playSound("mowithers:JestBreathe", 0.1F, 1.3F);
	  }
	  if (this.timeSinceDoingIt == 4000 && !this.worldObj.isRemote)
	  {
		  this.playLivingSound();
		  this.setInLove(false);
		  this.isPregnant = false;
		  this.setSneaking(false);
		  EntityWitherGirlPink entityocelot = new EntityWitherGirlPink(this.worldObj);
          entityocelot.setLocationAndAngles(this.posX, this.posY + 0.5D, this.posZ, this.rotationYaw, 0.0F);
          entityocelot.setChild(true);
          if (this.getMate() != null)
          {
          entityocelot.setFatherId(this.getMate().getUniqueID().toString());
          this.getMate().triggerAchievement(MoWithersAchievments.achievementWitherFamily);
          }
          this.worldObj.spawnEntityInWorld(entityocelot);
	  }
	  if (this.bangTimer >= 99)
	  {
		  if (this.ridingEntity instanceof EntityPlayer)
		  {
			  	((EntityPlayer)this.ridingEntity).addExhaustion(2F);
			  	((EntityPlayer)this.ridingEntity).triggerAchievement(MoWithersAchievments.achievementWitherLove);
			  	++this.numbOfOthers;
			    if (!this.worldObj.isRemote)
			    {
			      Iterator iterator1 = this.worldObj.getEntitiesWithinAABB(EntityWitherGirl.class, getEntityBoundingBox().expand(6.0D, 6.0D, 6.0D)).iterator();
			      Iterator iterator2 = this.worldObj.getEntitiesWithinAABB(EntityWitherGirlPink.class, getEntityBoundingBox().expand(6.0D, 6.0D, 6.0D)).iterator();
			      Iterator iterator3 = this.worldObj.getEntitiesWithinAABB(EntityWitherGirlVoid.class, getEntityBoundingBox().expand(6.0D, 6.0D, 6.0D)).iterator();
			      EntityWitherGirl withergirl;
			      while (iterator1.hasNext()) {
			    	  withergirl = (EntityWitherGirl)iterator1.next();
			    	  if (withergirl.isInLove())
			    	  ++this.numbOfOthers;
			      }
			      EntityWitherGirlPink pinkwithergirl;
			      while (iterator2.hasNext()) {
			    	  pinkwithergirl = (EntityWitherGirlPink)iterator2.next();
			    	  if (pinkwithergirl.isInLove())
			    	  ++this.numbOfOthers;
			      }
			      EntityWitherGirlVoid voidwithergirl;
			      while (iterator3.hasNext()) {
			    	  voidwithergirl = (EntityWitherGirlVoid)iterator3.next();
			    	  if (voidwithergirl.isInLove())
			    	  ++this.numbOfOthers;
			      }
			    }
			    if (this.numbOfOthers >= 2)
			    	((EntityPlayer)this.ridingEntity).triggerAchievement(MoWithersAchievments.achievementThreesome);
				this.setMateId(((EntityPlayer)this.ridingEntity).getUniqueID().toString());
				this.ridingEntity = null;
		  }
		  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1.35F);
		  this.bangTimer = 0;
		  if (!this.worldObj.isRemote)
		  {
			  this.setInLove(false);
			  this.isDoingIt = false;
			  if (this.rand.nextInt(10) == 0)
			  {
				  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1.34F);
				  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1.36F);
				  this.playSound("mowithers:SEX!", Float.MAX_VALUE, 1.37F);
				  this.timeSinceDoingIt = 72000;
		            int i = 150000;

		            while (i > 0)
		            {
		                int j = EntityXPOrb.getXPSplit(i);
		                i -= j;
		                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
		            }
			  }
			  else
			  {
				  this.timeSinceDoingIt = 1200;
		            int i = 5000;

		            while (i > 0)
		            {
		                int j = EntityXPOrb.getXPSplit(i);
		                i -= j;
		                this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, j));
		            }
			  }
		  }
		  
	        for (int k = (int)this.posX - 1; k < (int)this.posX + 1; ++k)
	        {
	            for (int l = (int)this.posY - 2; l < (int)this.posY + 2; ++l)
	            {
	                for (int i1 = (int)this.posZ - 1; i1 < (int)this.posZ + 1; ++i1)
	                {
	                	BlockPos pos = new BlockPos(k, l, i1);
	                	Block block = this.worldObj.getBlockState(pos).getBlock();

	                    if (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, pos))
	                    {
	                        this.worldObj.setBlockState(pos, Blocks.snow_layer.getDefaultState());
	                    }
	                }
	            }
	        }
	        
	        this.numbOfOthers = 0;
	  }
	  --this.timeSinceDoingIt;
	  
	  if (this.isConcerned)
	  {
		  if (this.isCollidedHorizontally)
			  this.jump();
		  EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 4.0D);
		  if (player != null)
		  {
			  this.getLookHelper().setLookPositionWithEntity(player, 30F, 30F);
		        double d0 = player.posX - this.posX;
		        double d1 = player.posZ - this.posZ;
		        double d3 = d0 * d0 + d1 * d1;
		          double d5 = MathHelper.sqrt_double(d3);
		          this.motionX -= (d0 / d5 * 0.8D - this.motionX) * 0.8D;
		          this.motionZ -= (d1 / d5 * 0.8D - this.motionZ) * 0.8D;
		  }
	  }
	  
	  if (this.rotationYaw < (this.rotationYawHead - 20))
		  this.rotationYaw = (this.rotationYawHead - 20);
	  
	  if (this.rotationYaw > (this.rotationYawHead + 20))
		  this.rotationYaw = (this.rotationYawHead + 20);
	  
	  if (this.swimSwurllySwurlCooldown <= 0F)
	  {
		  this.swimSwurllySwurlCooldown = 100F;
	  }
	  else
	  {
		  --this.swimSwurllySwurlCooldown;
		  if (this.swimSwurllySwurlCooldown > 60F)
		  {
			  ++this.swimSwurllySwurl;
		  }
		  if (this.swimSwurllySwurlCooldown > 50F)
		  {
			  ++this.swimSwurllySwurl;
		  }
		  if (this.swimSwurllySwurlCooldown > 40F)
		  {
			  ++this.swimSwurllySwurl;
		  }
		  if (this.swimSwurllySwurlCooldown > 30F)
		  {
			  ++this.swimSwurllySwurl;
		  }
		  if (this.swimSwurllySwurlCooldown > 20F)
		  {
			  ++this.swimSwurllySwurl;
		  }
	  }
	  
	  if ((double)this.limbSwingAmount < 0.001D)
		  this.swimSwurllySwurlCooldown = 1F;
	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
		  if (this.isRiding())
		  {
			  if (this.ridingEntity instanceof EntityMinecart || this.ridingEntity instanceof EntityBoat)
			  {
				  this.renderYawOffset = this.rotationYaw = this.ridingEntity.rotationYaw + 90F;
			  }
			  else
			  {
				  this.renderYawOffset = this.rotationYaw = this.rotationYawHead = this.ridingEntity.rotationYaw;
				  if (this.ridingEntity instanceof EntityLivingBase)
					  this.rotationYawHead = ((EntityLivingBase)this.ridingEntity).rotationYawHead;
			  }
		  }
	  ++this.blinkTimer;
      byte b1 = this.dataWatcher.getWatchableObjectByte(16);
      byte b0 = (byte)(this.blinkTimer > 0 ? 1 : 0);

      if (b1 != b0)
      {
          this.dataWatcher.updateObject(16, Byte.valueOf(b0));
      }
      
      if (this.blinkTimer == 2)
      {
    	  if (this.sugerRush)
    	  {
    	      	if (this.isChild())
    	      	{
    	              this.blinkTimer = -5 - this.rand.nextInt(5);
    	      	}
    	      	else
    	      	{
    	      		this.blinkTimer = -10 - this.rand.nextInt(10);
    	      	}
    	  }
    	  else
    	  {
    	      	if (this.isChild())
    	      	{
    	              this.blinkTimer = -10 - this.rand.nextInt(50);
    	      	}
    	      	else
    	      	{
    	      		this.blinkTimer = -20 - this.rand.nextInt(100);
    	      	}
    	  }
      }
      if (isChild()) {
      	if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
      		setSize(0.4F, 0.4F);
      	else
      		setSize(0.4F, 0.8F);
      } else {
      	if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
      		setSize(0.45F, 0.45F);
    	else if (this.isSneaking())
    		setSize(0.45F, 1.15F);
      	else
      		setSize(0.45F, 1.850001F);
      }
	  if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
	  {
		  EntityPlayer player = this.worldObj.getClosestPlayerToEntity(this, 10.0D);
		  if (player != null && player.getHeldItem() != null && player.getHeldItem().getItem() == Items.sugar)
		  {
			  this.getMoveHelper().setMoveTo(player.posX, player.posY, player.posZ, 1D);
			  this.getLookHelper().setLookPositionWithEntity(player, 10F, 30F);
		  }
		    if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D) {
		        this.renderYawOffset = this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F;
		      }
            float f3 = this.rotationYaw * (float)Math.PI / 180.0F;
            float f11 = MathHelper.sin(f3);
            float f4 = MathHelper.cos(f3);
          for (int i = 0; i < 2; ++i)
          {
              this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * 2D, this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ + 0.5D + (this.rand.nextDouble() - 0.5D) * 2D, 0.0D, 0.0D, 0.0D, new int[0]);
              this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width + (double)(f4 * 0.2F) - (double)(f11 * 0.75F), this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ + 0.25D + (this.rand.nextDouble() - 0.5D) * (double)this.width + (double)(f11 * 0.2F) + (double)(f4 * 0.75F), 0.0D, 0.0D, 0.0D, new int[0]);
              this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width + (double)(f4 * 0.2F) + (double)(f11 * 0.75F), this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ - 0.25D + (this.rand.nextDouble() - 0.5D) * (double)this.width + (double)(f11 * 0.2F) - (double)(f4 * 0.75F), 0.0D, 0.0D, 0.0D, new int[0]);
              this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width - (double)(f4 * 0.2F) - (double)(f11 * 0.75F), this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ + 0.25D + (this.rand.nextDouble() - 0.5D) * (double)this.width - (double)(f11 * 0.2F) + (double)(f4 * 0.75F), 0.0D, 0.0D, 0.0D, new int[0]);
              this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width - (double)(f4 * 0.2F) + (double)(f11 * 0.75F), this.posY + (this.rand.nextDouble() - 0.5D) * 2D, this.posZ - 0.25D + (this.rand.nextDouble() - 0.5D) * (double)this.width - (double)(f11 * 0.2F) - (double)(f4 * 0.75F), 0.0D, 0.0D, 0.0D, new int[0]);
          }
	  }
    if ((!this.worldObj.isRemote) && (isRheia()) && (getHeldItem() != null) && (this.worldObj.getClosestPlayerToEntity(this, 6.0D) != null) && (this.rand.nextInt(40) == 0))
    {
      EntityItem entityitem = entityDropItem(new ItemStack(getHeldItem().getItem(), 1, getHeldItem().getItemDamage()), 1.5F);
      
      entityitem.addVelocity(-MathHelper.sin(this.rotationYawHead * 3.1415927F / 180.0F) * (0.2F + this.rand.nextFloat() * 0.2F), 0.1D + this.rand.nextDouble() * 0.2D, MathHelper.cos(this.rotationYawHead * 3.1415927F / 180.0F) * (0.2F + this.rand.nextFloat() * 0.2F));
      if (entityitem != null)
      {
        entityitem.setDefaultPickupDelay();
        entityitem.setNoDespawn();
      }
      setCurrentItemOrArmor(0, null);
    }
    if ((this.musicTimer <= 0) && (isRheia()))
    {
      this.musicTimer = 2100;
      playSound("mowithers:RheiasTheme", 10000.0F, 1.0F);
    }
    this.musicTimer -= 1;
    
    if ((!this.onGround) && (this.motionY < 0.0D) && !this.isPregnant) {
        this.motionY *= 0.5D;
      }
    if (isRheia())
    {
      setCustomNameTag("\u00A7dRheia");
      setCanPickUpLoot(true);
    }
    if (this.rand.nextInt(120) == 0) {
      this.shouldStartFlying = false;
    }
    if (this.rand.nextInt(120) == 0)
    {
      spreadBonemealEffect(this.posX, this.posY, this.posZ, this.worldObj);
      spreadBonemealEffect(this.posX, this.posY - 0.25D, this.posZ, this.worldObj);
    }
    if ((!this.worldObj.isRemote) && (this.shouldStartFlying == true) && (getWatchedTargetId(0) > 0))
    {
      Entity entity = this.worldObj.getEntityByID(getWatchedTargetId(0));
      if ((entity != null) && (canEntityBeSeen(entity)))
      {
        if ((this.posY < entity.posY) || ((!isArmored()) && (this.posY < entity.posY + 7.0D)))
        {
          if (this.motionY < 0.0D) {
            this.motionY = 0.0D;
          }
          this.motionY += (0.5D - this.motionY) * 0.5D;
        }
        double d0 = entity.posX - this.posX;
        double d1 = entity.posZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1;
        if (d3 > 1.0D)
        {
          double d5 = MathHelper.sqrt_double(d3);
          this.motionX += (d0 / d5 * 0.8D - this.motionX) * 0.8D;
          this.motionZ += (d1 / d5 * 0.8D - this.motionZ) * 0.8D;
        }
      }
    }
    super.onLivingUpdate();
    for (int i = 0; i < 2; i++)
    {
      this.field_82218_g[i] = this.field_82221_e[i];
      this.field_82217_f[i] = this.field_82220_d[i];
    }
    for (int i = 0; i < 2; i++)
    {
      int j = getWatchedTargetId(i + 1);
      Entity entity1 = null;
      if (j > 0) {
        entity1 = this.worldObj.getEntityByID(j);
      }
      if (entity1 != null)
      {
        double d1 = func_82214_u(i + 1);
        double d3 = func_82208_v(i + 1);
        double d5 = func_82213_w(i + 1);
        double d6 = entity1.posX - d1;
        double d7 = entity1.posY + entity1.getEyeHeight() - d3;
        double d8 = entity1.posZ - d5;
        double d9 = MathHelper.sqrt_double(d6 * d6 + d8 * d8);
        float f = (float)(Math.atan2(d8, d6) * 180.0D / 3.141592653589793D) - 90.0F;
        float f1 = (float)-(Math.atan2(d7, d9) * 180.0D / 3.141592653589793D);
        this.field_82220_d[i] = func_82204_b(this.field_82220_d[i], f1, 40.0F);
        this.field_82221_e[i] = func_82204_b(this.field_82221_e[i], f, 10.0F);
      }
      else
      {
        this.field_82221_e[i] = func_82204_b(this.field_82221_e[i], this.renderYawOffset, 10.0F);
      }
    }
    
    if (this.isInLove() && this.worldObj.isRemote)
    {
    	for (int j = 0; j < 1; j++)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
        }
    }
    
    boolean flag = isArmored();
    for (int j = 0; j <= 3; j++)
    {
      double d10 = func_82214_u(j);
      double d2 = func_82208_v(j);
      double d4 = func_82213_w(j);
      if (this.sugerRush)
      this.worldObj.spawnParticle(EnumParticleTypes.ITEM_CRACK, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.3D, 0.0D, 0.0D, 0.0D, new int[] {353});
      if ((flag) || (this.worldObj.rand.nextInt(2) == 0)) {
          this.worldObj.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.3D, 0.0D, 0.0D, 0.0D, new int[0]);
        this.worldObj.spawnParticle(EnumParticleTypes.HEART, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
    if (getInvulTime() > 0) {
      for (int j = 0; j < 15; j++) {
        this.worldObj.spawnParticle(EnumParticleTypes.CRIT_MAGIC, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + this.rand.nextFloat() * 3.3F, this.posZ + this.rand.nextGaussian() * 0.6D, 0.0D, 0.0D, 0.0D, new int[0]);
      }
    }
  }
  
  /**
   * Moves the entity based on the specified heading.  Args: strafe, forward
   */
  public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
  {
      if (this.isServerWorld())
      {
  	    Block block1 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.25D, this.posZ)).getBlock();
  	    Block block2 = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.75D, this.posZ)).getBlock();
          if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
          {
              this.moveFlying(p_70612_1_, p_70612_2_, 0.1F);
              this.moveEntity(this.motionX, this.motionY, this.motionZ);
              this.motionX *= 0.9D;
              this.motionY *= 0.9D;
              this.motionZ *= 0.9D;
              float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.25D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.25D) * 0.25F;

              if (f > 1.0F)
              {
                  f = 1.0F;
              }

              if (this.rand.nextInt(2) == 0)
        		  if (this.swimSwurllySwurlCooldown > 60F)
        		  {
                      this.playSound(this.getSplashSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 1.9F);
        		  }
        		  if (this.swimSwurllySwurlCooldown > 40F)
        		  {
                      this.playSound(this.getSwimSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 1.9F);
        		  }
              this.playSound(this.getSwimSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 1.25F);
          }
          else
          {
              super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
          }
      }
      else
      {
          super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
      }
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_)
  {
    return p_70652_1_.attackEntityFrom(DamageSourceExtra.causeWitherGirlDamage(this), (int)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
  }
  
  protected void updateAITasks()
  {
    if (--this.homeCheckTimer <= 0)
    {
      this.homeCheckTimer = (70 + this.rand.nextInt(50));
      this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(new BlockPos(this), 32);
      if (this.villageObj == null)
      {
        detachHome();
      }
      else
      {
        BlockPos blockpos = this.villageObj.getCenter();
        func_175449_a(blockpos, (int)(this.villageObj.getVillageRadius() * 0.6F));
      }
    }
    if (getInvulTime() > 0)
    {
      int i = getInvulTime() - 1;
      if (i <= 0)
      {
        this.worldObj.newExplosion(this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
        this.worldObj.playBroadcastSound(1013, new BlockPos(this), 0);
      }
      setInvulTime(i);
      if (this.ticksExisted % 10 == 0) {
        heal(40.0F);
      }
    }
    else
    {
      super.updateAITasks();
      
      IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

      if (this.sugerRush)
      {
    	  if (!iattributeinstance.func_180374_a(field_110190_br))
    	  iattributeinstance.applyModifier(field_110190_br);
    	  double d0 = this.posX - this.prevPosX;
          double d1 = this.posZ - this.prevPosZ;
          float f6 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

          if (f6 > 1.0F)
          {
              f6 = 1.0F;
          }

          this.limbSwingAmount += (f6 - this.limbSwingAmount) * 0.8F;
      }
      else if (iattributeinstance.func_180374_a(field_110190_br))
      {
          iattributeinstance.removeModifier(field_110190_br);
      }
      for (int i = 1; i < 3; i++) {
        if (this.ticksExisted >= this.field_82223_h[(i - 1)])
        {
          this.field_82223_h[(i - 1)] = (this.ticksExisted + 10 + this.rand.nextInt(10));
          if (this.wasFed)
          {
            int k2 = i - 1;
            int l2 = this.field_82224_i[(i - 1)];
            this.field_82224_i[k2] = (this.field_82224_i[(i - 1)] + 1);
            if (l2 > 15)
            {
              float f = 16.0F;
              float f1 = 1.0F;
              double d1 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - f, this.posX + f);
              double d2 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - f1, this.posY + f1);
              double d3 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - f, this.posZ + f);
              launchWitherSkullToCoords(i + 1, d1, d2, d3, true);
              this.field_82224_i[(i - 1)] = 0;
            }
          }
          int i1 = getWatchedTargetId(i);
          if (i1 > 0)
          {
            Entity entity = this.worldObj.getEntityByID(i1);
            if ((!isChild()) && (entity != null) && (entity.isEntityAlive()) && (getDistanceSqToEntity(entity) <= 4096.0D) && (canEntityBeSeen(entity)))
            {
              launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
              this.field_82223_h[(i - 1)] = (this.ticksExisted + 20 + this.rand.nextInt(20));
              this.field_82224_i[(i - 1)] = 0;
            }
            else
            {
              func_82211_c(i, 0);
            }
          }
          else
          {
            List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(24.0D, 12.0D, 24.0D), Predicates.and(IMob.mobSelector, IEntitySelector.NOT_SPECTATING));
            for (int k1 = 0; (k1 < 24) && (!list1.isEmpty()); k1++)
            {
              EntityLivingBase entitylivingbase = (EntityLivingBase)list1.get(this.rand.nextInt(list1.size()));
              if ((entitylivingbase != this) && (entitylivingbase.isEntityAlive()) && (canEntityBeSeen(entitylivingbase)) && (entitylivingbase.isEntityUndead()))
              {
                if ((entitylivingbase instanceof EntityPlayer))
                {
                  if (((EntityPlayer)entitylivingbase).capabilities.disableDamage) {
                    break;
                  }
                  func_82211_c(i, entitylivingbase.getEntityId()); break;
                }
                func_82211_c(i, entitylivingbase.getEntityId());
                
                break;
              }
              list1.remove(entitylivingbase);
            }
          }
        }
      }
      if (getAttackTarget() != null) {
        func_82211_c(0, getAttackTarget().getEntityId());
      } else {
        func_82211_c(0, 0);
      }
      if (this.blockBreakCounter > 0)
      {
        this.blockBreakCounter -= 1;
        if ((this.blockBreakCounter == 0) && (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
        {
          int i = MathHelper.floor_double(this.posY);
          int i1 = MathHelper.floor_double(this.posX);
          int j1 = MathHelper.floor_double(this.posZ);
          boolean flag = false;
          for (int l1 = -1; l1 <= 1; l1++) {
            for (int i2 = -1; i2 <= 1; i2++) {
              for (int j = 0; j <= 2; j++)
              {
                int j2 = i1;
                int k = i + j;
                int l = j1;
                Block block = this.worldObj.getBlockState(new BlockPos(j2, k, l)).getBlock();
                if ((!block.isAir(this.worldObj, new BlockPos(j2, k, l))) && (canEntityDestroy(block, this.worldObj, new BlockPos(j2, k, l), this))) {
                  flag = (this.worldObj.destroyBlock(new BlockPos(j2, k, l), true)) || (flag);
                }
              }
            }
          }
        }
      }
      List list1111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8.0D, 8.0D, 8.0D));
      if ((list1111 != null) && (!list1111.isEmpty() && !this.worldObj.isRemote)) {
        for (int i111 = 0; i111 < list1111.size(); i111++)
        {
          Entity entity = (Entity)list1111.get(i111);
          if ((entity != null) && ((entity instanceof EntityLivingBase)) && (this.rand.nextInt(20) == 0)) {
            if ((entity instanceof IMob))
            {
              ((EntityLivingBase)entity).attackEntityFrom(DamageSource.causePlayerDamage(this.worldObj.getClosestPlayerToEntity(entity, -1.0D)), 1.0F);
              ((EntityLivingBase)entity).attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this), 8.0F);
            }
            else
            {
              ((EntityLivingBase)entity).heal(8.0F);
              if (entity instanceof EntityAgeable)
              {
		    	  if (((EntityAgeable)entity).getGrowingAge() != 0)
		    	  {
		    		  if (entity instanceof EntityAnimal && (!((EntityAnimal)entity).isInLove() || ((EntityAnimal)entity).isChild()))
		    			  ((EntityAnimal)entity).setGrowingAge(0);
		    	  }
            	  if (entity instanceof EntityAnimal && this.worldObj.getClosestPlayerToEntity(this, -1D) != null && !((EntityAnimal)entity).isChild() && getRNG().nextInt(10) == 0)
                  {
            		  ((EntityAnimal)entity).setInLove(this.worldObj.getClosestPlayerToEntity(this, -1D));
                  }
              }
            }
          }
        }
      }
      if (this.ticksExisted % 20 == 0) {
        heal(1.0F);
	    if (this.getMate() != null) {
	        this.targetTasks.addTask(1, this.aiFollowLover);
	      } else {
	        this.targetTasks.removeTask(this.aiFollowLover);
	      }
	    
	    if (this.isChild())
	    {
	        if (this.getFather() != null) {
	            this.targetTasks.addTask(1, this.aiFollowDaddy);
	            this.targetTasks.removeTask(this.aiFollowFather);
	          } else {
	            this.targetTasks.removeTask(this.aiFollowDaddy);
	          }
	    }
	    else
	    {
	        if (this.getFather() != null && this.getMate() == null) {
	            this.targetTasks.addTask(1, this.aiFollowFather);
	            this.targetTasks.removeTask(this.aiFollowDaddy);
	          } else {
	            this.targetTasks.removeTask(this.aiFollowFather);
	          }
	    }
      }
    }
  }
  
  public boolean canEntityDestroy(Block block, IBlockAccess world, BlockPos pos, Entity entity)
  {
    return (block != Blocks.water) && (block != Blocks.lava) && (block != Blocks.bed) && (block != Blocks.barrier) && (block != Blocks.bedrock) && (block != Blocks.end_portal) && (block != Blocks.end_portal_frame) && (block != Blocks.command_block);
  }
  
  public void func_82206_m()
  {
    setInvulTime(220);
    setHealth(getMaxHealth() / 3.0F);
  }
  
  public void setInWeb() {}
  
  public int getTotalArmorValue()
  {
    return 10;
  }
  
  public double getYOffset()
  {
      return this.isChild() ? super.getYOffset() + 0.25D : super.getYOffset() - 0.8D;
  }
  
  public boolean interact(EntityPlayer player)
  {
    ItemStack itemstack = player.getCurrentEquippedItem();
    if ((itemstack != null) && itemstack.getItem() == Items.golden_apple && player != this.getFather() && !this.isChild() && !this.isInLove() && this.timeSinceDoingIt <= 0)
    {
        this.playSound("random.eat", 2F, this.worldObj.rand.nextFloat() * 0.1F + 1.75F);
        this.playSound("random.burp", 2F, this.worldObj.rand.nextFloat() * 0.1F + 1.75F);
        this.setInLove(true);
        this.swingItem();
        this.rotationPitch = 75F;
        return true;
    }
    else if (itemstack != null && itemstack.getItem() == Items.sugar && !this.sugerRush)
    {
	      this.sugerRush = true;
	      this.playSound("random.eat", 2F, this.worldObj.rand.nextFloat() * 0.1F + 1.75F);
	      this.playSound("random.burp", 2F, this.worldObj.rand.nextFloat() * 0.1F + 1.75F);
	      player.swingItem();
	      if (!player.capabilities.isCreativeMode) {
	        itemstack.stackSize -= 1;
	      }
	      if (itemstack.stackSize <= 0) {
	        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
	      }
        return true;
    }
    else if (itemstack != null && itemstack.getItem() == Items.spawn_egg)
    {
        if (!this.worldObj.isRemote)
        {
            Class oclass = EntityList.getClassFromID(itemstack.getMetadata());

            if (oclass != null && this.getClass() == oclass)
            {
                EntityWitherGirlPink entityageable = new EntityWitherGirlPink(worldObj);

                if (entityageable != null)
                {
                    entityageable.setChild(true);
                    entityageable.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
                    this.worldObj.spawnEntityInWorld(entityageable);
                    
                    if (this.getMate() != null && player == this.getMate())
                    {
                        entityageable.setFatherId(player.getUniqueID().toString());
                    }
                    
                    if (this.getFather() != null && player == this.getFather())
                    {
                        entityageable.setFatherId(player.getUniqueID().toString());
                    }

                    if (itemstack.hasDisplayName())
                    {
                        entityageable.setCustomNameTag(itemstack.getDisplayName());
                    }

                    if (!player.capabilities.isCreativeMode)
                    {
                        --itemstack.stackSize;

                        if (itemstack.stackSize <= 0)
                        {
                            player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
                        }
                    }
                }
            }
        }

        return true;
    }
    else if (itemstack == null && (this.isInLove() || this.isChild()))
    {
    	if (this.ridingEntity == null)
    	{
    		this.mountEntity(player);
    		if (this.getFather() == null)
    		{
    			this.setFatherId(player.getUniqueID().toString());
    	    	for (int j = 0; j < 3; j++)
    	        {
    	            this.playLivingSound();
    	        }
    		}
    	}
    	else if (this.ridingEntity != null)
    		this.ridingEntity = null;
        this.swingItem();
        return true;
    }
    else if (itemstack == null && this.getMate() != null)
    {
    	if (this.ridingEntity == null && this.getMate().riddenByEntity != null)
    		this.getMate().riddenByEntity.mountEntity(this);
    	else if (this.riddenByEntity != null)
    		this.riddenByEntity = null;
        this.swingItem();
        return true;
    }
    else
    {
    	return super.interact(player);
    }
  }
  
  private double func_82214_u(int p_82214_1_)
  {
    if (p_82214_1_ <= 0) {
      return this.posX;
    }
    float f = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 0.8D;
  }
  
  private double func_82208_v(int p_82208_1_)
  {
    return p_82208_1_ <= 0 ? this.posY + getEyeHeight() : this.posY + getEyeHeight() - 0.3D;
  }
  
  private double func_82213_w(int p_82213_1_)
  {
    if (p_82213_1_ <= 0) {
      return this.posZ;
    }
    float f = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 0.8D;
  }
  
  private float func_82204_b(float p_82204_1_, float p_82204_2_, float p_82204_3_)
  {
    float f3 = MathHelper.wrapAngleTo180_float(p_82204_2_ - p_82204_1_);
    if (f3 > p_82204_3_) {
      f3 = p_82204_3_;
    }
    if (f3 < -p_82204_3_) {
      f3 = -p_82204_3_;
    }
    return p_82204_1_ + f3;
  }
  
  private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_)
  {
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0) && (this.rand.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_)
  {
    this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1014, new BlockPos(this), 0);
    double d3 = func_82214_u(p_82209_1_);
    double d4 = func_82208_v(p_82209_1_);
    double d5 = func_82213_w(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityPinkSkull entitywitherskull = new EntityPinkSkull(this.worldObj, this, d6, d7, d8);
    if (p_82209_8_) {
      entitywitherskull.setInvulnerable(true);
    }
    entitywitherskull.posY = d4;
    entitywitherskull.posX = d3;
    entitywitherskull.posZ = d5;
    this.worldObj.spawnEntityInWorld(entitywitherskull);
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
  {
    if (this.shouldStartFlying == true) {
      launchWitherSkullToEntity(0, p_82196_1_);
    }
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (this.blockBreakCounter <= 0) {
      this.blockBreakCounter = 5;
    }
    for (int i = 0; i < this.field_82224_i.length; i++) {
      this.field_82224_i[i] += 3;
    }
    if (isEntityInvulnerable(source)) {
      return false;
    }
    if ((source != DamageSource.drown) && (!(source.getEntity() instanceof EntityWitherGirl)) && (!(source.getEntity() instanceof EntityWitherGirlVoid)) && (!(source.getEntity() instanceof EntityFriendlyWither)) && (!(source.getEntity() instanceof EntityWitherGirlPink)))
    {
      if ((getInvulTime() > 0) && (source != DamageSource.outOfWorld)) {
        return false;
      }
      if (isArmored())
      {
        Entity entity = source.getSourceOfDamage();
        if ((entity instanceof EntityArrow)) {
          return false;
        }
      }
      Entity entity = source.getEntity();
      if ((entity != null) && ((entity instanceof EntityPlayer)))
      {
        ItemStack itemstack = ((EntityPlayer)entity).getHeldItem();
        
        Entity entity1 = source.getSourceOfDamage();
        if ((itemstack == null) && this.getFather() == null && this.getMate() == null && (!(entity1 instanceof EntityArrow)) && (!(source instanceof EntityDamageSourceIndirect)) && (!isChild()))
        {
          playSound("mowithers:BitchSlap", getSoundVolume(), getSoundPitch() + 0.4F);
          this.rotationYawHead += 35.0F;
          this.rotationPitch = 10.0F;
          this.isConcerned = true;
          entity.attackEntityFrom(DamageSourceExtra.causeWitherGirlDamage(this), (int)getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
          double d1 = 32.0D;
          Vec3 vec3 = ((EntityPlayer)entity1).getLook(1.0F);
      	this.getNavigator().setPath(this.getNavigator().getPathToXYZ(entity1.posX + vec3.xCoord * d1, entity1.posY, entity1.posZ + vec3.zCoord * d1), 1.1D);
          List list111 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(32.0D, 32.0D, 32.0D));
          if ((list111 != null) && (!list111.isEmpty())) {
            for (int i111 = 0; i111 < list111.size(); i111++)
            {
              Entity entity11 = (Entity)list111.get(i111);
              if ((entity11 != null) && ((entity11 instanceof EntityWitherGirl)))
              {
                ((EntityWitherGirl)entity11).isMad = true;
                ((EntityWitherGirl)entity11).setAttackTarget((EntityPlayer)entity1);
              }
              if ((entity11 != null) && ((entity11 instanceof EntityWitherGirlVoid)))
              {
                ((EntityWitherGirlVoid)entity11).setGrowthTime(2000);
                ((EntityWitherGirlVoid)entity11).setAttackTarget((EntityPlayer)entity1);
                ((EntityWitherGirlVoid)entity11).shouldStartFlying = true;
                ((EntityWitherGirlVoid)entity11).func_82211_c(1, entity.getEntityId());
                ((EntityWitherGirlVoid)entity11).func_82211_c(2, entity.getEntityId());
              }
            }
          }
        }
        return false;
      }
      return super.attackEntityFrom(source, amount);
    }
    return false;
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn)
  {
    super.onKillEntity(entityLivingIn);
    if ((getAttackTarget() != null) && (entityLivingIn == getAttackTarget()) && (this.shouldStartFlying == true)) {
      this.shouldStartFlying = false;
    }
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    EntityItem entityitem = dropItem(Items.nether_star, 1);
    if (entityitem != null) {
      entityitem.setNoDespawn();
    }
    if (!this.worldObj.isRemote)
    {
      Iterator iterator = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, getEntityBoundingBox().expand(50.0D, 100.0D, 50.0D)).iterator();
      while (iterator.hasNext())
      {
        EntityPlayer entityplayer = (EntityPlayer)iterator.next();
        entityplayer.triggerAchievement(AchievementList.killWither);
      }
    }
  }
  
  protected void despawnEntity()
  {
    this.entityAge = 0;
  }
  
  public void fall(float distance, float damageMultiplier) {}
  
  public void addPotionEffect(PotionEffect p_70690_1_) {}
  
  @SideOnly(Side.CLIENT)
  public float func_82207_a(int p_82207_1_)
  {
    return this.field_82221_e[p_82207_1_];
  }
  
  @SideOnly(Side.CLIENT)
  public float func_82210_r(int p_82210_1_)
  {
    return this.field_82220_d[p_82210_1_];
  }
  
  public int getInvulTime()
  {
    return this.dataWatcher.getWatchableObjectInt(20);
  }
  
  public void setInvulTime(int p_82215_1_)
  {
    this.dataWatcher.updateObject(20, Integer.valueOf(p_82215_1_));
  }
  
  public int getWatchedTargetId(int p_82203_1_)
  {
    return this.dataWatcher.getWatchableObjectInt(17 + p_82203_1_);
  }
  
  public void func_82211_c(int p_82211_1_, int p_82211_2_)
  {
    this.dataWatcher.updateObject(17 + p_82211_1_, Integer.valueOf(p_82211_2_));
  }
  
  public boolean isArmored()
  {
    return getHealth() <= getMaxHealth() / 2.0F;
  }
  
  protected float getSoundVolume()
  {
    return 3.0F;
  }
  
  protected float getSoundPitch()
  {
    return super.getSoundPitch() * 1.25F;
  }
}
