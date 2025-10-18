package net.minecraft.MoWithers.render;

import net.minecraft.MoWithers.render.shields.LayerAirWitherAura;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.EntityWitherCultist;
import net.minecraft.entity.wither.EntityWitherCultistGreater;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCultistGreater extends RenderLiving
{
    private static final ResourceLocation cultistTextures = new ResourceLocation("mowithers", "textures/entities/cultist.png");

    public RenderCultistGreater(RenderManager p_i46191_1_)
    {
        super(p_i46191_1_, new ModelVillager(0F), 0.5F);
        addLayer(new RenderCultistGreaterHead(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityWitherCultistGreater entity)
    {
        return cultistTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityWitherCultistGreater)entity);
    }
    
    public ModelVillager func_177123_g()
    {
        return (ModelVillager)super.getMainModel();
    }

    public ModelBase getMainModel()
    {
        return this.func_177123_g();
    }
    
    protected void preRenderCallback(EntityWitherCultistGreater p_77041_1_, float p_77041_2_)
    {
        float f1 = 0.9375F;
        GlStateManager.scale(f1, f1, f1);
    }
    
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
        this.preRenderCallback((EntityWitherCultistGreater)p_77041_1_, p_77041_2_);
    }
}