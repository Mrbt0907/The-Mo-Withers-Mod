package net.minecraft.MoWithers.render;

import net.minecraft.MoWithers.models.ModelGhastWither;
import net.minecraft.MoWithers.render.shields.LayerGhastWitherAura;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.wither.EntityGhastWither;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGhastWither
  extends RenderLiving
{
  private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("mowithers", "textures/entities/ghast_wither_invul.png");
  private static final ResourceLocation wither2Textures = new ResourceLocation("mowithers", "textures/entities/ghast_wither_shooting.png");
  private static final ResourceLocation witherTextures = new ResourceLocation("mowithers", "textures/entities/ghast_wither.png");
  
  public RenderGhastWither(RenderManager p_i46130_1_)
  {
    super(p_i46130_1_, new ModelGhastWither(0.0F), 2F);
    addLayer(new LayerGhastWitherAura(this));
  }
  
  public void func_180591_a(EntityGhastWither p_180591_1_, double p_180591_2_, double p_180591_4_, double p_180591_6_, float p_180591_8_, float p_180591_9_)
  {
    BossStatus.setBossStatus(p_180591_1_, true);
    super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
  }
  
  protected ResourceLocation getEntityTexture(EntityGhastWither entity)
  {
    int i = entity.getInvulTime();
    return (i > 0) && ((i > 80) || (i / 5 % 2 != 1)) ? invulnerableWitherTextures : (entity.func_110182_bF() ? wither2Textures :  witherTextures);
  }
  
  protected void func_180592_a(EntityGhastWither p_180592_1_, float p_180592_2_)
  {
    float f1 = 6.0F;
    int i = p_180592_1_.getInvulTime();
    if (i > 0) {
      f1 -= (i - p_180592_2_) / 220.0F * 0.5F;
    }
    GlStateManager.scale(f1, f1, f1);
    GlStateManager.translate(0F, 1.25F, 0F);
  }
  
  public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityGhastWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180592_a((EntityGhastWither)p_77041_1_, p_77041_2_);
  }
  
  public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityGhastWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((EntityGhastWither)entity);
  }
  
  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityGhastWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
}
