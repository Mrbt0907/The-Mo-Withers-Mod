package net.endermanofdoom.mowithers.render;

import net.endermanofdoom.mca.client.render.RenderBaseWither;
import net.endermanofdoom.mca.entity.boss.EntityBaseWither;
import java.util.Random;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.entity.wither.EntityWitherDragon;
import net.endermanofdoom.mowithers.model.ModelWitherDragon;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderWitherDragon extends RenderBaseWither<EntityWitherDragon>
{
    public RenderWitherDragon(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
        this.addLayer(new LayerDeath());
        this.mainModel = new ModelWitherDragon(0.0F);
        this.auraModel = new ModelWitherDragon(0.5F);
    }

    protected ResourceLocation getEntityTexture(EntityWitherDragon entity)
    {
        int i = entity.getInvulTime();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? new ResourceLocation(MoWithers.MODID, "textures/entity/wither/superboss/wither_dragon_invulnerable.png") : new ResourceLocation(MoWithers.MODID, "textures/entity/wither/superboss/wither_dragon.png");
    }

    protected ResourceLocation getAuraTexture(EntityBaseWither entity)
    {
        int i = entity.deathTicks;
        return i > 100 && (i > 180 || i / 3 % 2 != 1) ? new ResourceLocation("textures/blocks/concrete_powder_white.png") : new ResourceLocation("textures/blocks/concrete_powder_purple.png");
    }
    
    public class LayerDeath implements LayerRenderer<EntityWitherDragon>
    {
        public void doRenderLayer(EntityWitherDragon entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
        {
            if (entitylivingbaseIn.deathTicks > 0)
            {
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder bufferbuilder = tessellator.getBuffer();
                RenderHelper.disableStandardItemLighting();
                float f = ((float)entitylivingbaseIn.deathTicks + partialTicks) / 200.0F;
                float f1 = 0.0F;

                if (f > 0.8F)
                {
                    f1 = (f - 0.8F) / 0.2F;
                }

                Random random = new Random(432L);
                GlStateManager.disableTexture2D();
                GlStateManager.shadeModel(7425);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                GlStateManager.disableAlpha();
                GlStateManager.enableCull();
                GlStateManager.depthMask(false);
                GlStateManager.pushMatrix();

                GlStateManager.translate(0, -(entitylivingbaseIn.getEyeHeight() / entitylivingbaseIn.getWitherScale() - 1.5F), 0);
                GlStateManager.scale(2F + f, 2F + f, 2F + f);
                for (int i = 0; (float)i < (f + f * f) / 2.0F * 200.0F; ++i)
                {
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0);
                    GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
                    float f2 = random.nextFloat() * 20.0F + 5.0F + f1 * 10.0F;
                    float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
                    bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
                    bufferbuilder.pos(0.0D, 0.0D, 0.0D).color(255, 0, 255, (int)(255.0F * (1.0F - f1))).endVertex();
                    bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 255, 255, 0).endVertex();
                    bufferbuilder.pos(0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 255, 255, 0).endVertex();
                    bufferbuilder.pos(0.0D, (double)f2, (double)(1.0F * f3)).color(255, 255, 255, 0).endVertex();
                    bufferbuilder.pos(-0.866D * (double)f3, (double)f2, (double)(-0.5F * f3)).color(255, 255, 255, 0).endVertex();
                    tessellator.draw();
                }

                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
                GlStateManager.disableCull();
                GlStateManager.disableBlend();
                GlStateManager.shadeModel(7424);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableTexture2D();
                GlStateManager.enableAlpha();
                RenderHelper.enableStandardItemLighting();
            }
        }

        public boolean shouldCombineTextures()
        {
            return false;
        }
    }
}