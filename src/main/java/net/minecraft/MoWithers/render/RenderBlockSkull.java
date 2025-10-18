package net.minecraft.MoWithers.render;

import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.witherskulls.EntityBlockSkull;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderBlockSkull
  extends RenderWitherSkull
{
	  private static final ResourceLocation bedrock = new ResourceLocation("mowithers", "textures/entities/adminium_wither.png");
	  private static final ResourceLocation obsidian = new ResourceLocation("mowithers", "textures/entities/obsidian_wither.png");
	  private static final ResourceLocation glass = new ResourceLocation("mowithers", "textures/entities/glass_wither.png");
	  private static final ResourceLocation gravel = new ResourceLocation("mowithers", "textures/entities/gravel_wither.png");
	  private static final ResourceLocation sand = new ResourceLocation("mowithers", "textures/entities/desert_wither.png");
	  private final ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();
	  
	  public RenderBlockSkull(RenderManager p_i46129_1_)
	  {
	    super(p_i46129_1_);
	  }
  
	  public void doRender(EntityBlockSkull entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	  {
	      GlStateManager.pushMatrix();
	      GlStateManager.disableCull();
	      GlStateManager.translate((float)x, (float)y, (float)z);
	      float f4 = 0.0625F;
	      GlStateManager.enableRescaleNormal();
	      GlStateManager.scale(1.0F, -1.0F, 1.0F);
	      GlStateManager.scale(2.0F, 2.0F, 2.0F);
	      GlStateManager.enableAlpha();
	      this.bindEntityTexture(entity);
	      this.skeletonHeadModel.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f4);
	      GlStateManager.popMatrix();
	      this.renderName(entity, x, y, z);
	  }
  
	  protected ResourceLocation func_180564_a(EntityBlockSkull p_180564_1_)
	  {
	      switch (p_180564_1_.getSkullType())
	      {
	          case 0:
	              return sand;
	          case 1:
	              return gravel;
	          case 2:
	              return glass;
	          case 3:
	              return obsidian;
	          case 4:
	              return bedrock;
	          default:
	              return sand;
	      }
	  }
  
	  protected ResourceLocation getEntityTexture(Entity entity)
	  {
	    return func_180564_a((EntityBlockSkull)entity);
	  }
  
	  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
	  {
	      this.doRender((EntityBlockSkull)entity, x, y, z, p_76986_8_, partialTicks);
	  }
}
