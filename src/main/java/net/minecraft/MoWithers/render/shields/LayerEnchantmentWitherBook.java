package net.minecraft.MoWithers.render.shields;

import net.minecraft.MoWithers.models.ModelEnchantmentWither;
import net.minecraft.MoWithers.render.RenderEnchantmentWither;
import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.wither.EntityEnchantedWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerEnchantmentWitherBook
  implements LayerRenderer
{
    private static final ResourceLocation field_147540_b = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private ModelBook field_147541_c = new ModelBook();
    private final RenderEnchantmentWither field_177215_b;
    
    public LayerEnchantmentWitherBook(RenderEnchantmentWither p_i46105_1_)
    {
      this.field_177215_b = p_i46105_1_;
    }
    
  public void func_177214_a(EntityEnchantedWither p_180537_1_, float p_180537_2_, float p_180537_3_, float p_180537_4_, float p_180537_5_, float p_180537_6_, float p_180537_7_, float p_180537_8_)
  {
      GlStateManager.pushMatrix();
      GlStateManager.translate(0F, -0.8F, 0F);
      GlStateManager.scale(0.75F, 0.75F, 0.75F);
      float f1 = (float)p_180537_1_.tickCount + p_180537_8_;
      GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f1 * 0.15F) * 0.05F, 0.0F);
      float f2;

      for (f2 = p_180537_1_.bookRotation - p_180537_1_.bookRotationPrev; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F))
      {
          ;
      }

      while (f2 < -(float)Math.PI)
      {
          f2 += ((float)Math.PI * 2F);
      }

      float f3 = p_180537_1_.renderYawOffset;
      EntityPlayer entityplayer = p_180537_1_.worldObj.getClosestPlayer(p_180537_1_.posX, p_180537_1_.posY + 3D, p_180537_1_.posZ, 5.0D);

      if (entityplayer == null)
      {
    	  f3 = -p_180537_1_.bookRotationPrev + f2 * p_180537_8_;
      }
      GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(f3 * 180.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(130.0F, 0.0F, 0.0F, 1.0F);
      this.field_177215_b.bindTexture(field_147540_b);
      float f4 = p_180537_1_.pageFlipPrev + (p_180537_1_.pageFlip - p_180537_1_.pageFlipPrev) * p_180537_8_ + 0.25F;
      float f5 = p_180537_1_.pageFlipPrev + (p_180537_1_.pageFlip - p_180537_1_.pageFlipPrev) * p_180537_8_ + 0.75F;
      f4 = (f4 - (float)MathHelper.truncateDoubleToInt((double)f4)) * 1.6F - 0.3F;
      f5 = (f5 - (float)MathHelper.truncateDoubleToInt((double)f5)) * 1.6F - 0.3F;
      
      if (p_180537_1_.hurtTime > 0)
      {
          GlStateManager.depthFunc(514);
          GlStateManager.disableTexture2D();
          GlStateManager.enableBlend();
          GlStateManager.blendFunc(770, 771);
          GlStateManager.color(1.0F, 0.0F, 0.0F, 0.5F);
          GlStateManager.enableTexture2D();
          GlStateManager.disableBlend();
          GlStateManager.depthFunc(515);
      }

      if (f4 < 0.0F)
      {
          f4 = 0.0F;
      }

      if (f5 < 0.0F)
      {
          f5 = 0.0F;
      }

      if (f4 > 1.0F)
      {
          f4 = 1.0F;
      }

      if (f5 > 1.0F)
      {
          f5 = 1.0F;
      }

      float f6 = p_180537_1_.bookSpreadPrev + (p_180537_1_.bookSpread - p_180537_1_.bookSpreadPrev) * p_180537_8_;
      GlStateManager.enableCull();
      this.field_147541_c.render((Entity)null, f1, f4, f5, f6, 0.0F, 0.0625F);
      GlStateManager.popMatrix();
  }
  
  public boolean shouldCombineTextures()
  {
    return false;
  }
  
  public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
  {
    func_177214_a((EntityEnchantedWither)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
  }
}
