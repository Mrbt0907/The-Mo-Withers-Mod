package net.minecraft.MoWithers.render.shields;

import net.minecraft.MoWithers.models.ModelMagmaWither;
import net.minecraft.MoWithers.render.RenderMagmaWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.EntityMagmaWither;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerMagmaWitherAura
  implements LayerRenderer
{
  private static final ResourceLocation field_177217_a = new ResourceLocation("mowithers", "textures/entities/lava_wither_lava.png");
  private final RenderMagmaWither field_177215_b;
  private final ModelMagmaWither field_177216_c = new ModelMagmaWither(0.5F);
  
  public LayerMagmaWitherAura(RenderMagmaWither p_i46105_1_)
  {
    this.field_177215_b = p_i46105_1_;
  }
  
  public void func_177214_a(EntityMagmaWither p_177214_1_, float p_177214_2_, float p_177214_3_, float p_177214_4_, float p_177214_5_, float p_177214_6_, float p_177214_7_, float p_177214_8_)
  {
    if (p_177214_1_.isArmored())
    {
      GlStateManager.depthMask(!p_177214_1_.isInvisible());
      this.field_177215_b.bindTexture(field_177217_a);
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      float f7 = p_177214_1_.ticksExisted + p_177214_4_;
      float f9 = f7 * -0.0025F;
      GlStateManager.translate(0.0F, f9, 0.0F);
      GlStateManager.matrixMode(5888);
  	  GlStateManager.enableAlpha();
      GlStateManager.enableNormalize();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.color(1.0F, 0.0F, 0.0F, 0.75F);
      this.field_177216_c.setLivingAnimations(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_4_);
      this.field_177216_c.setModelAttributes(this.field_177215_b.getMainModel());
      this.field_177216_c.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
      GlStateManager.disableBlend();
      GlStateManager.disableNormalize();
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
    }
  }
  
  public boolean shouldCombineTextures()
  {
    return false;
  }
  
  public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
  {
    func_177214_a((EntityMagmaWither)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
