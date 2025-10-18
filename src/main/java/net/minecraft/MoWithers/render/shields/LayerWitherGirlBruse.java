package net.minecraft.MoWithers.render.shields;

import net.minecraft.MoWithers.models.ModelWitherGirl;
import net.minecraft.MoWithers.render.RenderWitherGirl;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.EntityWitherGirl;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerWitherGirlBruse
  implements LayerRenderer
{
  private static final ResourceLocation sheildTextures = new ResourceLocation("mowithers", "textures/entities/bruse.png");
  private final RenderWitherGirl field_177215_b;
  private final ModelWitherGirl field_177216_c = new ModelWitherGirl(0.001F);
  
  public LayerWitherGirlBruse(RenderWitherGirl renderWitherGirl)
  {
    this.field_177215_b = renderWitherGirl;
  }
  
  public void func_177214_a(EntityWitherGirl p_177214_1_, float p_177214_2_, float p_177214_3_, float p_177214_4_, float p_177214_5_, float p_177214_6_, float p_177214_7_, float p_177214_8_)
  {
    if (p_177214_1_.hasBeenRecentlySlapped)
    {
      GlStateManager.depthMask(!p_177214_1_.isInvisible());
      this.field_177215_b.bindTexture(sheildTextures);
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      GlStateManager.enableBlend();
      float f7 = p_177214_1_.hasBeenRecentlySlappedTimer * 0.002F;
      GlStateManager.color(f7, f7, f7, 1.0F);
      GlStateManager.disableLighting();
      GlStateManager.blendFunc(1, 1);
      this.field_177216_c.setLivingAnimations(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_4_);
      this.field_177216_c.setModelAttributes(this.field_177215_b.getMainModel());
      this.field_177216_c.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      GlStateManager.enableLighting();
      GlStateManager.disableBlend();
    }
  }
  
  public boolean shouldCombineTextures()
  {
    return true;
  }
  
  public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
  {
    func_177214_a((EntityWitherGirl)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
