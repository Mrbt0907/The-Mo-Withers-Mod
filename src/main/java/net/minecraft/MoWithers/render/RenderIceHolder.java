package net.minecraft.MoWithers.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.witherskulls.EntityIceHolder;
import net.minecraft.util.ResourceLocation;

public class RenderIceHolder extends Render
{
    public RenderIceHolder(RenderManager p_i46184_1_)
    {
        super(p_i46184_1_);
        this.shadowSize = 0.0F;
    }

    public void doRender(EntityIceHolder entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
    }

    protected ResourceLocation getTextures(EntityIceHolder p_180554_1_)
    {
        return null;
    }

    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getTextures((EntityIceHolder)entity);
    }

    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
        this.doRender((EntityIceHolder)entity, x, y, z, p_76986_8_, partialTicks);
    }
}