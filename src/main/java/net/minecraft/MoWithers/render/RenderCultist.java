package net.minecraft.MoWithers.render;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.wither.EntityWitherCultist;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCultist extends RenderLiving
{
    private static final ResourceLocation cultistTextures = new ResourceLocation("mowithers", "textures/entities/cultist.png");

    public RenderCultist(RenderManager p_i46191_1_)
    {
        super(p_i46191_1_, new ModelVillager(0F), 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityWitherCultist entity)
    {
        return cultistTextures;
    }

    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityWitherCultist)entity);
    }
    
    protected void preRenderCallback(EntityWitherCultist p_77041_1_, float p_77041_2_)
    {
        float f1 = 0.9375F;
        GlStateManager.scale(f1, f1, f1);
    }
    
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
        this.preRenderCallback((EntityWitherCultist)p_77041_1_, p_77041_2_);
    }
}