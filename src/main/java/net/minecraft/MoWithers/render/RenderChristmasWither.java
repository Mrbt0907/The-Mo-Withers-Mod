package net.minecraft.MoWithers.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.MoWithers.models.ModelChristmasWither;
import net.minecraft.MoWithers.render.shields.LayerChristmasWitherAura;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.wither.EntityChristmasWither;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderChristmasWither
  extends RenderLiving
{
  private static final ResourceLocation witherTextures = new ResourceLocation("mowithers", "textures/entities/christmas_wither.png");
  
  public RenderChristmasWither(RenderManager p_i46130_1_)
  {
    super(p_i46130_1_, new ModelChristmasWither(0.0F), 1.0F);
    addLayer(new LayerChristmasWitherAura(this));
  }
  
  public void func_180591_a(EntityChristmasWither p_180591_1_, double p_180591_2_, double p_180591_4_, double p_180591_6_, float p_180591_8_, float p_180591_9_)
  {
    BossStatus.setBossStatus(p_180591_1_, true);
    super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
  }
  
  protected ResourceLocation getEntityTexture(EntityChristmasWither entity)
  {
    return witherTextures;
  }
  
  protected void func_180588_a(EntityChristmasWither p_180588_1_, float p_180588_2_, float p_180588_3_, float p_180588_4_)
  {
      super.rotateCorpse(p_180588_1_, p_180588_2_, p_180588_3_, p_180588_4_);

      if (!p_180588_1_.onGround && p_180588_1_.getAITarget() == null)
      {
    	  GlStateManager.rotate((float)p_180588_1_.fallDistance * 5F, -1.0F, 0.0F, -1.0F);
      }
  }

  protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_)
  {
      this.func_180588_a((EntityChristmasWither)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
  }
  
  protected void func_180592_a(EntityChristmasWither p_180592_1_, float p_180592_2_)
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
    func_180591_a((EntityChristmasWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180592_a((EntityChristmasWither)p_77041_1_, p_77041_2_);
  }
  
  public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityChristmasWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((EntityChristmasWither)entity);
  }
  
  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
    func_180591_a((EntityChristmasWither)entity, x, y, z, p_76986_8_, partialTicks);
  }
}
