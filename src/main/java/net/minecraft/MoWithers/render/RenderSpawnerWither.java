package net.minecraft.MoWithers.render;

import net.minecraft.MoWithers.models.ModelSpawnerWither;
import net.minecraft.MoWithers.render.shields.LayerSpawnerWitherAura;
import net.minecraft.MoWithers.render.shields.LayerSpawnerWitherSpawner;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.wither.EntitySpawnerWither;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSpawnerWither
  extends RenderLiving
{
	  private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
	  private static final ResourceLocation witherTextures = new ResourceLocation("textures/entity/wither/wither.png");
  
  public RenderSpawnerWither(RenderManager p_i46130_1_)
  {
    super(p_i46130_1_, new ModelSpawnerWither(0.0F), 1.0F);
    addLayer(new LayerSpawnerWitherAura(this));
    addLayer(new LayerSpawnerWitherSpawner(this));
  }
  
  public ModelSpawnerWither func_177123_g()
  {
      return (ModelSpawnerWither)super.getMainModel();
  }

  public ModelBase getMainModel()
  {
      return this.func_177123_g();
  }
  
  public void func_180591_a(EntitySpawnerWither p_180591_1_, double p_180591_2_, double p_180591_4_, double p_180591_6_, float p_180591_8_, float p_180591_9_)
  {
    BossStatus.setBossStatus(p_180591_1_, true);
    super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
  }
  
  protected ResourceLocation getEntityTexture(EntitySpawnerWither entity)
  {
    int i = entity.getInvulTime();
    return (i > 0) && ((i > 80) || (i / 5 % 2 != 1)) ? invulnerableWitherTextures : witherTextures;
  }
  
  protected void func_180592_a(EntitySpawnerWither p_180592_1_, float p_180592_2_)
  {
    float f1 = 2.0F;
    int i = p_180592_1_.getInvulTime();
    if (i > 0) {
      f1 -= (i - p_180592_2_) / 220.0F * 0.5F;
    }
    GlStateManager.scale(f1, f1, f1);
  }
  
  public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntitySpawnerWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180592_a((EntitySpawnerWither)p_77041_1_, p_77041_2_);
  }
  
  public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntitySpawnerWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((EntitySpawnerWither)entity);
  }
  
  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntitySpawnerWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
}
