package net.minecraft.MoWithers.render;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.wither.EntityAirWither;
import net.minecraft.entity.wither.EntityWitherCultist;
import net.minecraft.entity.wither.EntityWitherCultistGreater;
import net.minecraft.entity.wither.EntityWitherCultistLeader;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCultistLeader extends RenderLiving
{
    private static final ResourceLocation cultistTextures = new ResourceLocation("mowithers", "textures/entities/cultist_leader.png");

    public RenderCultistLeader(RenderManager p_i46191_1_)
    {
        super(p_i46191_1_, new ModelVillager(0F), 0.5F);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityWitherCultistLeader entity)
    {
        return cultistTextures;
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityWitherCultistLeader)entity);
    }
    
    protected void preRenderCallback(EntityWitherCultistLeader p_77041_1_, float p_77041_2_)
    {
        float f1 = 0.9375F;
        GlStateManager.scale(f1, f1, f1);
    }
    
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
        this.preRenderCallback((EntityWitherCultistLeader)p_77041_1_, p_77041_2_);
    }
    
    public void func_180591_a(EntityWitherCultistLeader p_180591_1_, double p_180591_2_, double p_180591_4_, double p_180591_6_, float p_180591_8_, float p_180591_9_)
    {
      BossStatus.setBossStatus(p_180591_1_, true);
      super.doRender(p_180591_1_, p_180591_2_, p_180591_4_, p_180591_6_, p_180591_8_, p_180591_9_);
    }
    
    public void doRender(EntityLiving entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
      func_180591_a((EntityWitherCultistLeader)entity, x, y, z, p_76986_8_, partialTicks);
    }
    
    public void doRender(EntityLivingBase entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
      func_180591_a((EntityWitherCultistLeader)entity, x, y, z, p_76986_8_, partialTicks);
    }
    
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
    {
      func_180591_a((EntityWitherCultistLeader)entity, x, y, z, p_76986_8_, partialTicks);
    }
}