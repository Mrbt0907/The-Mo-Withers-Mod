package net.minecraft.MoWithers.render;

import net.minecraft.MoWithers.models.ModelWitherGirl;
import net.minecraft.MoWithers.models.ModelWitherGirlVoid;
import net.minecraft.MoWithers.render.shields.LayerWitherGirlVoidAura;
import net.minecraft.MoWithers.render.shields.LayerWitherGirlVoidEyes;
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
import net.minecraft.entity.wither.EntityWitherGirlVoid;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherGirlVoid
  extends RenderLiving
{
	private static final ResourceLocation witherGirlBlinkingTextures = new ResourceLocation("mowithers", "textures/entities/wither_girl_void_blinking.png");
	private static final ResourceLocation witherGirlAngryTextures = new ResourceLocation("mowithers", "textures/entities/wither_girl_void_big_mode.png");
	private static final ResourceLocation witherGirlTextures = new ResourceLocation("mowithers", "textures/entities/wither_girl_void.png");
  
  public RenderWitherGirlVoid(RenderManager rendermanager)
  {
    super(rendermanager, new ModelWitherGirlVoid(), 0.6F);
    addLayer(new LayerWitherGirlVoidAura(this));
    addLayer(new LayerBipedArmor(this));
    addLayer(new LayerHeldItem(this));
    addLayer(new LayerArrow(this));
    addLayer(new LayerWitherGirlVoidEyes(this));
  }
  
  public void func_180591_a(EntityWitherGirlVoid p_180591_1_, double p_180591_2_, double p_180591_4_, double p_180591_6_, float p_180591_8_, float p_180591_9_)
  {
    BossStatus.setBossStatus(p_180591_1_, true);
    super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
  }
  
  protected ResourceLocation getEntityTexture(EntityWitherGirlVoid entity)
  {
		if (entity.isBlinking())
			return witherGirlBlinkingTextures;
		else
		{
			if (entity.getGrowthTime() >= 150)
	    		return witherGirlAngryTextures;
			else
	    		return witherGirlTextures;
		}
  }
  
  public ModelWitherGirlVoid func_177123_g()
  {
      return (ModelWitherGirlVoid)super.getMainModel();
  }

  public ModelBase getMainModel()
  {
      return this.func_177123_g();
  }
  
  protected void func_180588_a(EntityWitherGirlVoid p_180588_1_, float p_180588_2_, float p_180588_3_, float p_180588_4_)
  {
      super.rotateCorpse(p_180588_1_, p_180588_2_, p_180588_3_, p_180588_4_);

	    Block block1 = p_180588_1_.worldObj.getBlockState(new BlockPos(p_180588_1_.posX, p_180588_1_.posY - 0.25D, p_180588_1_.posZ)).getBlock();
	    Block block2 = p_180588_1_.worldObj.getBlockState(new BlockPos(p_180588_1_.posX, p_180588_1_.posY - 0.75D, p_180588_1_.posZ)).getBlock();
if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
{
	GlStateManager.rotate((float) (p_180588_1_.motionY * 90F), -1F, 0F, 0F);
    if ((double)p_180588_1_.limbSwingAmount >= 0.001D)
    {
        float f3 = 13.0F;
        float f4 = p_180588_1_.limbSwing - p_180588_1_.limbSwingAmount * (1.0F - p_180588_4_) + 6.0F;
        float f5 = (Math.abs(f4 % f3 - f3 * 0.5F) - f3 * 0.25F) / (f3 * 0.25F);
        GlStateManager.rotate(30F * f5, 0.0F, 0.0F, 1.0F);
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
	if (p_180588_1_.isChild())
	{
		GlStateManager.rotate(5F, 1F, 0F, 0F);
	}
	else
	{
		GlStateManager.rotate(135F, 1F, 0F, 0F);
		GlStateManager.rotate(180F, 0F, 0F, 1F);
		if (p_180588_1_.ridingEntity.isSneaking())
			GlStateManager.rotate(15F, 1F, 0F, 0F);
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
  }
  
  protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
  {
      this.func_180588_a((EntityWitherGirlVoid)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
  }
  
  protected void func_180592_a(EntityWitherGirlVoid p_180592_1_, float p_180592_2_)
  {
    float f1 = 1.15F;
    float f2 = 0.184285719F;
    int i = p_180592_1_.getInvulTime();
    if (p_180592_1_.getGrowthTime() > 1) {
      f1 *= (p_180592_1_.getGrowthTime() * 0.01F);
      f2 *= 0.5F;
    }
    if (i > 0) {
      f1 -= (i - p_180592_2_) / 220.0F * 0.5F;
    }
    GlStateManager.scale(f1, f1, f1);
    Block block1 = p_180592_1_.worldObj.getBlockState(new BlockPos(p_180592_1_.posX, p_180592_1_.posY - 0.25D, p_180592_1_.posZ)).getBlock();
    Block block2 = p_180592_1_.worldObj.getBlockState(new BlockPos(p_180592_1_.posX, p_180592_1_.posY - 0.75D, p_180592_1_.posZ)).getBlock();
if (block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid())
{
	GlStateManager.rotate(90F, 1F, 0F, 0F);
	GlStateManager.translate(0.0F, 1.0F, 0.0F);
  GlStateManager.translate(0.0F, 0.0F, MathHelper.cos(p_180592_1_.ticksExisted * 0.184285719F - 1F) * 0.1F);
}
if (p_180592_1_.isRiding())
{
	if (p_180592_1_.isChild())
	{
		GlStateManager.translate(0.0F, 0.4F, 0.25F);
	}
	else
	{
		GlStateManager.translate(0.0F, 1.25F, 0.6F);
		GlStateManager.translate(0.0F, 0.0F, MathHelper.cos(p_180592_1_.ticksExisted * 0.6F) * 0.15F);
	}
}
if (!p_180592_1_.onGround && !this.func_177123_g().isRiding && !block1.getMaterial().isLiquid() && !block2.getMaterial().isLiquid())
    GlStateManager.translate(0.0F, MathHelper.cos(p_180592_1_.ticksExisted * f2 - 1F) * 0.08F, 0.0F);
  }
  
  public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityWitherGirlVoid)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180592_a((EntityWitherGirlVoid)p_77041_1_, p_77041_2_);
  }
  
  public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityWitherGirlVoid)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((EntityWitherGirlVoid)entity);
  }
  
  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityWitherGirlVoid)entity, x, y, z, p_76986_8_, partialTicks);
  }
}
