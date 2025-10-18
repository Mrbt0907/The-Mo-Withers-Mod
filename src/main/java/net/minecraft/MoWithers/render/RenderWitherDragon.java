package net.minecraft.MoWithers.render;

import net.minecraft.MoWithers.models.ModelWitherDragon;
import net.minecraft.MoWithers.render.shields.LayerWitherDragonAura;
import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.wither.EntityWitherDragon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherDragon
  extends RenderLiving
{
  private static final ResourceLocation invulnerableWitherTextures = new ResourceLocation("mowithers", "textures/entities/wither_dragon_invul.png");
  private static final ResourceLocation witherTextures = new ResourceLocation("mowithers", "textures/entities/wither_dragon.png");
  private static final ResourceLocation enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
  protected ModelWitherDragon modelWitherDragon;
  
  public RenderWitherDragon(RenderManager p_i46130_1_)
  {
    super(p_i46130_1_, new ModelWitherDragon(0.0F), 2.0F);
    this.modelWitherDragon = (ModelWitherDragon)this.mainModel;
    addLayer(new LayerWitherDragonAura(this));
  }
  
  protected void renderModel(EntityWitherDragon p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_)
  {
      super.renderModel(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
  }
  
  protected void renderModel(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_)
  {
      this.renderModel((EntityWitherDragon)p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
  }
  
  protected void rotateCorpse(EntityWitherDragon p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
  {
    GlStateManager.translate(0.0F, MathHelper.cos(p_77043_2_ * 0.15F) * 0.2F, 0.0F);
    
    super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
  }
  
  protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
  {
    rotateCorpse((EntityWitherDragon)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
  }
  
  public void func_180591_a(EntityWitherDragon p_180591_1_, double p_180591_2_, double p_180591_4_, double p_180591_6_, float p_180591_8_, float p_180591_9_)
  {
    BossStatus.setBossStatus(p_180591_1_, true);
    super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
  }
  
  protected ResourceLocation getEntityTexture(EntityWitherDragon entity)
  {
    int i = entity.getInvulTime();
    return (i > 0) && ((i > 80) || (i / 5 % 2 != 1)) ? invulnerableWitherTextures : witherTextures;
  }
  
  protected void func_180592_a(EntityWitherDragon p_180592_1_, float p_180592_2_)
  {
    float f1 = 1.5F;
    int i = p_180592_1_.getInvulTime();
    if (i > 0) {
      f1 -= (i - p_180592_2_) / 220.0F * 0.5F;
    }
    GlStateManager.scale(f1, f1, f1);
  }
  
  public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityWitherDragon)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180592_a((EntityWitherDragon)p_77041_1_, p_77041_2_);
  }
  
  public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityWitherDragon)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((EntityWitherDragon)entity);
  }
  
  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityWitherDragon)entity, x, y, z, p_76986_8_, partialTicks);
  }
}
