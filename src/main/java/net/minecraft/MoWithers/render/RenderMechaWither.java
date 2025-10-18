package net.minecraft.MoWithers.render;

import net.minecraft.MoWithers.models.ModelMechaWither;
import net.minecraft.MoWithers.render.shields.LayerMechaWitherAura;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.wither.EntityMechaWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMechaWither
  extends RenderLiving
{
  private static final ResourceLocation witherTextures = new ResourceLocation("mowithers", "textures/entities/mecha_wither.png");
  
  public RenderMechaWither(RenderManager p_i46130_1_)
  {
    super(p_i46130_1_, new ModelMechaWither(0.0F), 1.0F);
    addLayer(new LayerMechaWitherAura(this));
  }
  
  public void func_180591_a(EntityMechaWither p_180591_1_, double p_180591_2_, double p_180591_4_, double p_180591_6_, float p_180591_8_, float p_180591_9_)
  {
    BossStatus.setBossStatus(p_180591_1_, true);
    super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
  }
  
  protected ResourceLocation getEntityTexture(EntityMechaWither entity)
  {
    return witherTextures;
  }
  
  protected void func_180592_a(EntityMechaWither p_180592_1_, float p_180592_2_)
  {
    float f1 = 2.0F;
    int i = p_180592_1_.getInvulTime();
    if (i > 0) {
      f1 -= (i - p_180592_2_) / 220.0F * 0.5F;
    }
    GlStateManager.scale(f1, f1, f1);
    if (p_180592_1_.isWet())
    	GlStateManager.scale(1F + (MathHelper.cos(p_180592_1_.ticksExisted * 2F) * 0.05F), 1F + (-MathHelper.cos(p_180592_1_.ticksExisted * 2F) * 0.05F), 1F + (MathHelper.sin(p_180592_1_.ticksExisted * 2F) * 0.05F));
    else
    	GlStateManager.scale(1F + (MathHelper.cos(p_180592_1_.ticksExisted * 0.125F) * 0.01F), 1F + (-MathHelper.cos(p_180592_1_.ticksExisted * 0.125F) * 0.0125F), 1F + (MathHelper.sin(p_180592_1_.ticksExisted * 0.125F) * 0.01F));
    GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
  }
  
  public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityMechaWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180592_a((EntityMechaWither)p_77041_1_, p_77041_2_);
  }
  
  public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityMechaWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((EntityMechaWither)entity);
  }
  
  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityMechaWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
}
