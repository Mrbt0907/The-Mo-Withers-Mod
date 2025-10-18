package net.minecraft.MoWithers.render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.MoWithers.models.ModelWitherGirl;
import net.minecraft.MoWithers.models.ModelWitherSculpture;
import net.minecraft.MoWithers.render.shields.LayerArrowSmaller;
import net.minecraft.MoWithers.render.shields.LayerScupltureTransformation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.Sculpture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSculpture
  extends RenderLiving
{
  private static final ResourceLocation sculuptureStartedTextures = new ResourceLocation("mowithers", "textures/entities/wither_sculpture_03.png");
  private static final ResourceLocation sculuptureItemOneTextures = new ResourceLocation("mowithers", "textures/entities/wither_sculpture_13.png");
  private static final ResourceLocation sculuptureItemTwoTextures = new ResourceLocation("mowithers", "textures/entities/wither_sculpture_23.png");
  
  public RenderSculpture(RenderManager p_i46130_1_)
  {
    super(p_i46130_1_, new ModelWitherSculpture(0.0F), 1.0F);
    addLayer(new LayerArrowSmaller(this));
    addLayer(new LayerScupltureTransformation(this));
  }
  
  protected ResourceLocation getEntityTexture(Sculpture entity)
  {
    return !entity.getHasBeenEnchanted() && !entity.getHasEssence() ? sculuptureStartedTextures : (!entity.getHasBeenEnchanted() ? sculuptureItemOneTextures : (sculuptureItemTwoTextures));
  }
  
  protected void func_180592_a(Sculpture p_180592_1_, float p_180592_2_)
  {
    float f1 = 2.0F;
    Random rnd = new Random();
    
    GlStateManager.scale(f1, f1, f1);
  }
  
  protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
  {
    func_180592_a((Sculpture)p_77041_1_, p_77041_2_);
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return getEntityTexture((Sculpture)entity);
  }
}
