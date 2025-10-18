package net.minecraft.MoWithers.render;

import net.minecraft.MoWithers.models.ModelBedrockWither;
import net.minecraft.MoWithers.render.shields.LayerBedrockWitherAura;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.wither.EntityBedrockWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBedrockWither
  extends RenderLiving
{
  private static final ResourceLocation witherTextures = new ResourceLocation("mowithers", "textures/entities/adminium_wither.png");
  
  public RenderBedrockWither(RenderManager p_i46130_1_)
  {
    super(p_i46130_1_, new ModelBedrockWither(0.0F), 0.0F);
    addLayer(new LayerBedrockWitherAura(this));
  }
  
  public void func_180591_a(EntityBedrockWither p_180591_1_, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    BossStatus.setBossStatus(p_180591_1_, true);
    
    double d3 = 0.002F + ((p_180591_1_.getHealth() - p_180591_1_.getMaxHealth()) * 0.000001F);
    x += p_180591_1_.getRNG().nextGaussian() * d3;
    z += p_180591_1_.getRNG().nextGaussian() * d3;
    super.doRender(p_180591_1_, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(EntityBedrockWither entity)
  {
    return witherTextures;
  }
  
  protected void func_180592_a(EntityBedrockWither p_180592_1_, float p_180592_2_)
  {
    float f1 = 2.0F;
    int i = p_180592_1_.getInvulTime();
    if (i > 0) {
      f1 -= (i - p_180592_2_) / 220.0F * 0.5F;
    }
    GlStateManager.scale(f1, f1, f1);
    GlStateManager.translate(0F, 0.425F, 0F);
  }
  
  public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityBedrockWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180592_a((EntityBedrockWither)p_77041_1_, p_77041_2_);
  }
  
  public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityBedrockWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((EntityBedrockWither)entity);
  }
  
  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityBedrockWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
}
