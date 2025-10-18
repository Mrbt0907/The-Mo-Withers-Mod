package net.minecraft.MoWithers.render.shields;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.MoWithers.render.RenderHalloweenWither;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.EntityHalloweenWither;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerHalloweenWitherRightPumpkin implements LayerRenderer
{
    private final RenderHalloweenWither field_177152_a;
    private static final String __OBFID = "CL_00002411";

    public LayerHalloweenWitherRightPumpkin(RenderHalloweenWither p_i46110_1_)
    {
        this.field_177152_a = p_i46110_1_;
    }

    public void func_177151_a(EntityHalloweenWither p_177151_1_, float p_177151_2_, float p_177151_3_, float p_177151_4_, float p_177151_5_, float p_177151_6_, float p_177151_7_, float p_177151_8_)
    {
        if (!p_177151_1_.isInvisible())
        {
            GlStateManager.pushMatrix();
            this.field_177152_a.func_177123_g().headRight.postRender(0.0625F);
            float f7 = 0.425F;
            GlStateManager.translate(-0.05F, -0.05F, -0.05F);
            GlStateManager.scale(f7, -f7, -f7);
            if (p_177151_1_.hurtTime > 0)
            	Minecraft.getMinecraft().getItemRenderer().renderItem(p_177151_1_, new ItemStack(Items.skull, 1, 4), ItemCameraTransforms.TransformType.HEAD);
            else
            	Minecraft.getMinecraft().getItemRenderer().renderItem(p_177151_1_, new ItemStack(Items.skull, 1, 2), ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }

    public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
    {
        this.func_177151_a((EntityHalloweenWither)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
}