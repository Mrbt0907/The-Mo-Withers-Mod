package net.minecraft.MoWithers.render.shields;

import net.minecraft.MoWithers.render.RenderWitherGirlPink;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.wither.EntityWitherGirlPink;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerWitherGirlPinkEyes implements LayerRenderer
{
    private static final ResourceLocation witherGirlLoveEyesBlinking = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_overlay_6.png");
    private static final ResourceLocation witherGirlLoveEyes = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_overlay_5.png");
    private static final ResourceLocation witherGirlEyesFrightning = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_overlay_4.png");
    private static final ResourceLocation witherGirlEyesConcerned = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_overlay_3.png");
    private static final ResourceLocation witherGirlEyesBlinking = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_overlay_2.png");
    private static final ResourceLocation witherGirlEyes = new ResourceLocation("mowithers", "textures/entities/wither_girl_pink_overlay_1.png");
    private final RenderWitherGirlPink field_177202_b;

    public LayerWitherGirlPinkEyes(RenderWitherGirlPink p_i46117_1_)
    {
        this.field_177202_b = p_i46117_1_;
    }

    public void func_177201_a(EntityWitherGirlPink p_177201_1_, float p_177201_2_, float p_177201_3_, float p_177201_4_, float p_177201_5_, float p_177201_6_, float p_177201_7_, float p_177201_8_)
    {
    	if (p_177201_1_.isBlinking())
			if (p_177201_1_.isInLove())
				this.field_177202_b.bindTexture(witherGirlLoveEyesBlinking);
			else
				this.field_177202_b.bindTexture(witherGirlEyesBlinking);
    	else
    	{
    		if (p_177201_1_.isConcerned)
				this.field_177202_b.bindTexture(witherGirlEyesConcerned);
    		else if (p_177201_1_.isInLove())
				this.field_177202_b.bindTexture(witherGirlLoveEyes);
			else if (p_177201_1_.isFrightened)
				this.field_177202_b.bindTexture(witherGirlEyesFrightning);
			else
				this.field_177202_b.bindTexture(witherGirlEyes);
    	}
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.blendFunc(1, 1);
        GlStateManager.disableLighting();

        if (p_177201_1_.isInvisible())
        {
            GlStateManager.depthMask(false);
        }
        else
        {
            GlStateManager.depthMask(true);
        }

        int k = 15728880;
        int i = k % 65536;
        int j = k / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)i / 1.0F, (float)j / 1.0F);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.field_177202_b.getMainModel().render(p_177201_1_, p_177201_2_, p_177201_3_, p_177201_5_, p_177201_6_, p_177201_7_, p_177201_8_);
        this.field_177202_b.func_177105_a(p_177201_1_, p_177201_4_);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }

    public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
    {
        this.func_177201_a((EntityWitherGirlPink)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}