package net.minecraft.MoWithers.render;

import net.minecraft.MoWithers.models.ModelWitherGirl;
import net.minecraft.MoWithers.models.ModelWitherGirlPink;
import net.minecraft.MoWithers.render.shields.LayerWitherGirlPinkAura;
import net.minecraft.MoWithers.render.shields.LayerWitherGirlPinkEyes;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.wither.EntityWitherGirl;
import net.minecraft.entity.wither.EntityWitherGirlPink;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherGirlPink
  extends RenderLiving
{
	  private static final ResourceLocation frightenedWitherGirlTextures = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_frightened.png");
	  private static final ResourceLocation concernedWitherGirlTextures = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_cautious.png");
	  private static final ResourceLocation witherGirlLoveBlinkingTextures = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_love_blinking.png");
	  private static final ResourceLocation witherGirlLoveTextures = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_love.png");
  private static final ResourceLocation witherGirlBlinkingTextures = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_blinking.png");
  private static final ResourceLocation witherGirlTextures = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink.png");
  
  public RenderWitherGirlPink(RenderManager rendermanager)
  {
    super(rendermanager, new ModelWitherGirlPink(), 0.4F);
    addLayer(new LayerWitherGirlPinkAura(this));
    addLayer(new LayerBipedArmor(this));
    addLayer(new LayerHeldItem(this));
    addLayer(new LayerArrow(this));
    addLayer(new LayerWitherGirlPinkEyes(this));
  }
  
  public void func_180591_a(EntityWitherGirlPink p_180591_1_, double p_180591_2_, double p_180591_4_, double p_180591_6_, float p_180591_8_, float p_180591_9_)
  {
    BossStatus.setBossStatus(p_180591_1_, false);
    super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
  }
  
  protected ResourceLocation getEntityTexture(EntityWitherGirlPink entity)
  {
		if (entity.isBlinking())
			if (entity.isInLove())
				return witherGirlLoveBlinkingTextures;
			else
				return witherGirlBlinkingTextures;
		else
		{
			if (entity.isConcerned)
				return concernedWitherGirlTextures;
			else if (entity.isInLove())
				return witherGirlLoveTextures;
			else if (entity.isFrightened)
	    		return frightenedWitherGirlTextures;
			else
	    		return witherGirlTextures;
		}
  }
  
  public ModelWitherGirlPink func_177123_g()
  {
      return (ModelWitherGirlPink)super.getMainModel();
  }

  public ModelBase getMainModel()
  {
      return this.func_177123_g();
  }
  
  protected void func_180588_a(EntityWitherGirlPink p_180588_1_, float p_180588_2_, float p_180588_3_, float p_180588_4_)
  {
      super.rotateCorpse(p_180588_1_, p_180588_2_, p_180588_3_, p_180588_4_);
      
      if (p_180588_1_.sugerRush)
      {
    	  p_180588_3_ += (float)(Math.cos((double)p_180588_1_.ticksExisted * 3.25D) * Math.PI * 0.25D);
      }

	    Block block1 = p_180588_1_.worldObj.getBlockState(new BlockPos(p_180588_1_.posX, p_180588_1_.posY - 0.25D, p_180588_1_.posZ)).getBlock();
	    Block block2 = p_180588_1_.worldObj.getBlockState(new BlockPos(p_180588_1_.posX, p_180588_1_.posY - 0.75D, p_180588_1_.posZ)).getBlock();
if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
{
	GlStateManager.rotate((float) (p_180588_1_.motionY * 90F), -2F, 0F, 0F);
    if ((double)p_180588_1_.limbSwingAmount >= 0.001D)
    {
        float f3 = 6.5F;
        float f4 = p_180588_1_.limbSwing - p_180588_1_.limbSwingAmount * (1.0F - p_180588_4_) + 6.0F;
        float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
        GlStateManager.rotate(15F * f5, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(p_180588_1_.swimSwurllySwurl * 5F, 0.0F, 0.0F, 1.0F);
    }
    else
    {
    	GlStateManager.rotate(60F, 1F, 0F, 0F);
    	GlStateManager.translate(0.0F, 0.25F, 0.5F);
    	GlStateManager.translate(0.0F, -0.25F, 0.0F);
    }
}
if (p_180588_1_.isRiding())
{
	if (p_180588_1_.isDoingIt)
    {
    	GlStateManager.translate(0.0F, MathHelper.cos(p_180588_1_.ticksExisted * 0.3F) * 0.1F, 0.0F);
    }
    GlStateManager.rotate(-25F, 1F, 0F, 0F);
    if (p_180588_1_.isSneaking())
    {
    	GlStateManager.rotate(-5F, 1F, 0F, 0F);
    }
	if (p_180588_1_.isChild())
	{
		GlStateManager.rotate(-25F, 1F, 0F, 0F);
		GlStateManager.rotate(p_180588_1_.ridingEntity.rotationPitch, -1F, 0F, 0F);
	}
    if (p_180588_1_.ridingEntity instanceof EntityIronGolem)
    {
        if (((EntityIronGolem)p_180588_1_.ridingEntity).limbSwingAmount >= 0.01D)
        {
            float f3 = 13.0F;
            float f4 = ((EntityIronGolem)p_180588_1_.ridingEntity).limbSwing - ((EntityIronGolem)p_180588_1_.ridingEntity).limbSwingAmount * (1.0F - p_180588_2_) + 6.0F;
            float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
            GlStateManager.rotate(15F * f5, 0.0F, 0.0F, 1.0F);
        }
    }
}
  }
  
  protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
  {
      this.func_180588_a((EntityWitherGirlPink)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
  }
  
  protected void func_180592_a(EntityWitherGirlPink p_180592_1_, float p_180592_2_)
  {
    float f1 = 0.925F;
    int i = p_180592_1_.getInvulTime();
    if (i > 0) {
      f1 -= (i - p_180592_2_) / 220.0F * 0.5F;
    }
    GlStateManager.scale(f1, f1, f1);
    
    Block block1 = p_180592_1_.worldObj.getBlockState(new BlockPos(p_180592_1_.posX, p_180592_1_.posY - 0.25D, p_180592_1_.posZ)).getBlock();
    Block block2 = p_180592_1_.worldObj.getBlockState(new BlockPos(p_180592_1_.posX, p_180592_1_.posY - 0.75D, p_180592_1_.posZ)).getBlock();
if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
{
	GlStateManager.rotate(90F, 1F, 0F, 0F);
	GlStateManager.translate(0.0F, 0.95F, 0.0F);
  GlStateManager.translate(0.0F, 0.0F, MathHelper.cos(p_180592_1_.ticksExisted * 0.245714292F - 1F) * 0.08F);
}
if (p_180592_1_.isSneaking())
{
	GlStateManager.translate(0.0F, 0.55F, 0.35F);
}

if (p_180592_1_.isRiding())
{
	GlStateManager.translate(0.0F, 0.0F, 0.75F);
    if (p_180592_1_.isSneaking())
    {
    	GlStateManager.translate(0.0F, -0.25F, -0.1F);
    	if (p_180592_1_.ridingEntity.isSneaking())
        {
        	GlStateManager.translate(0.0F, 0.2F, 0.1F);
        }
    }
	if (p_180592_1_.isChild())
	{
		GlStateManager.translate(0.0F, 0.4F, -0.4F);
	}
}
    if (!p_180592_1_.onGround && !this.func_177123_g().isRiding && !block1.getMaterial().isLiquid() && !block2.getMaterial().isLiquid())
    GlStateManager.translate(0.0F, MathHelper.cos(p_180592_1_.ticksExisted * 0.245714292F - 1F) * 0.08F, 0.0F);
  }
  
  public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityWitherGirlPink)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180592_a((EntityWitherGirlPink)p_77041_1_, p_77041_2_);
  }
  
  public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityWitherGirlPink)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((EntityWitherGirlPink)entity);
  }
  
  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityWitherGirlPink)entity, x, y, z, p_76986_8_, partialTicks);
  }
}
