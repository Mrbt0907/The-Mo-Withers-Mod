package net.minecraft.MoWithers.render;

import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.RenderWitherSkull;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.entity.witherskulls.EntityMettalicSkull;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderMettalicSkull
  extends Render
{
  private static final ResourceLocation iron = new ResourceLocation("mowithers", "textures/entities/iron_wither.png");
  private static final ResourceLocation gold = new ResourceLocation("mowithers", "textures/entities/gold_wither.png");
  private static final ResourceLocation diamond = new ResourceLocation("mowithers", "textures/entities/diamond_wither.png");
  private static final ResourceLocation emerald = new ResourceLocation("mowithers", "textures/entities/emerald_wither.png");
  private static final ResourceLocation coal = new ResourceLocation("mowithers", "textures/entities/coal_wither.png");
  private static final ResourceLocation redstone = new ResourceLocation("mowithers", "textures/entities/redstone_wither.png");
  private static final ResourceLocation lapis = new ResourceLocation("mowithers", "textures/entities/lapis_wither.png");
  private final ModelSkeletonHead skeletonHeadModel = new ModelSkeletonHead();
  
  public RenderMettalicSkull(RenderManager p_i46129_1_)
  {
    super(p_i46129_1_);
  }
  
  public void doRender(EntityMettalicSkull entity, double x, double y, double z, float p_76986_8_, float partialTicks)
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
      super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
  }
  
  protected ResourceLocation func_180564_a(EntityMettalicSkull p_180564_1_)
  {
      switch (p_180564_1_.getSkullType())
      {
          case 0:
              return iron;
          case 1:
              return gold;
          case 2:
              return diamond;
          case 3:
              return emerald;
          case 4:
              return coal;
          case 5:
              return redstone;
          case 6:
              return lapis;
          default:
              return iron;
      }
  }
  
  protected ResourceLocation getEntityTexture(Entity entity)
  {
    return func_180564_a((EntityMettalicSkull)entity);
  }
  
  public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float partialTicks)
  {
      this.doRender((EntityMettalicSkull)entity, x, y, z, p_76986_8_, partialTicks);
  }
}
