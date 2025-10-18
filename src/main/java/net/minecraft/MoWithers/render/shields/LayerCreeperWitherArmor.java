package net.minecraft.MoWithers.render.shields;

import net.minecraft.MoWithers.models.ModelCreeperWither;
import net.minecraft.MoWithers.render.RenderCreeperWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.EntityCreeperWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCreeperWitherArmor
  implements LayerRenderer
{
  private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("mowithers", "textures/entities/creeper_wither_armor.png");
  private final RenderCreeperWither field_177215_b;
  private final ModelCreeperWither field_177216_c = new ModelCreeperWither(0.75F);
  
  public LayerCreeperWitherArmor(RenderCreeperWither p_i46105_1_)
  {
    this.field_177215_b = p_i46105_1_;
  }
  
  public void func_177214_a(EntityCreeperWither p_177214_1_, float p_177214_2_, float p_177214_3_, float p_177214_4_, float p_177214_5_, float p_177214_6_, float p_177214_7_, float p_177214_8_)
  {
      if (p_177214_1_.getPowered())
      {
          GlStateManager.depthMask(!p_177214_1_.isInvisible());
          this.field_177215_b.bindTexture(LIGHTNING_TEXTURE);
          GlStateManager.matrixMode(5890);
          GlStateManager.loadIdentity();
          float f7 = (float)p_177214_1_.ticksExisted + p_177214_4_;
          GlStateManager.translate(f7 * 0.01F, f7 * 0.01F, 0.0F);
          GlStateManager.matrixMode(5888);
          GlStateManager.enableBlend();
          float f8 = 0.5F;
          GlStateManager.color(f8, f8, f8, 1.0F);
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
    return false;
  }
  
  public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
  {
    func_177214_a((EntityCreeperWither)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
