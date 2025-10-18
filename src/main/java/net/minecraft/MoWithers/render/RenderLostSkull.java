package net.minecraft.MoWithers.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.EntityGhastWither;
import net.minecraft.entity.wither.EntityLostSkull;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderLostSkull extends RenderLiving
{
    public RenderLostSkull(RenderManager p_i46191_1_)
    {
    	super(p_i46191_1_, new ModelSkeletonHead(), 0.25F);
        addLayer(new RenderLostSkullSkull(this));
    }
    
    public ModelSkeletonHead func_177123_g()
    {
        return (ModelSkeletonHead)super.getMainModel();
    }

    public ModelBase getMainModel()
    {
        return this.func_177123_g();
    }
    
    protected ResourceLocation getEntityTexture(EntityLostSkull entity)
    {
        return null;
    }
    
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return this.getEntityTexture((EntityLostSkull)entity);
    }
    
    protected void func_180592_a(EntityLostSkull p_180592_1_, float p_180592_2_)
    {
      GlStateManager.translate(0F, 1.25F, 0F);
    }
    
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
      func_180592_a((EntityLostSkull)p_77041_1_, p_77041_2_);
    }
}