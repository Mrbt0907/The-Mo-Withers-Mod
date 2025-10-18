package net.minecraft.MoWithers.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.EntityDiamondWither;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDiamondWither
  extends ModelBase
{
  private ModelRenderer[] spine;
  private ModelRenderer[] heads;
  
  public ModelDiamondWither(float p_i46302_1_)
  {
    this.textureWidth = 64;
    this.textureHeight = 64;
    this.spine = new ModelRenderer[3];
    this.spine[0] = new ModelRenderer(this, 0, 16);
    this.spine[0].addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3, p_i46302_1_);
    this.spine[1] = new ModelRenderer(this).setTextureSize(this.textureWidth, this.textureHeight);
    this.spine[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
    this.spine[1].setTextureOffset(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, p_i46302_1_);
    this.spine[1].setTextureOffset(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2, p_i46302_1_);
    this.spine[1].setTextureOffset(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2, p_i46302_1_);
    this.spine[1].setTextureOffset(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2, p_i46302_1_);
    this.spine[2] = new ModelRenderer(this, 12, 22);
    this.spine[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, p_i46302_1_);
    this.heads = new ModelRenderer[3];
    this.heads[0] = new ModelRenderer(this, 0, 0);
    this.heads[0].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, p_i46302_1_);
    this.heads[1] = new ModelRenderer(this, 32, 0);
    this.heads[1].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
    this.heads[1].rotationPointX = -8.0F;
    this.heads[1].rotationPointY = 4.0F;
    this.heads[2] = new ModelRenderer(this, 32, 0);
    this.heads[2].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
    this.heads[2].rotationPointX = 10.0F;
    this.heads[2].rotationPointY = 4.0F;
  }
  
  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    ModelRenderer[] amodelrenderer = this.heads;
    int i = amodelrenderer.length;
    for (int j = 0; j < i; j++)
    {
      ModelRenderer modelrenderer = amodelrenderer[j];
      modelrenderer.render(p_78088_7_);
    }
    amodelrenderer = this.spine;
    i = amodelrenderer.length;
    for (int j = 0; j < i; j++)
    {
      ModelRenderer modelrenderer = amodelrenderer[j];
      modelrenderer.render(p_78088_7_);
    }
  }
  
  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    float f6 = MathHelper.cos(p_78087_3_ * 0.1F);
    this.spine[1].rotateAngleX = ((0.065F + 0.05F * f6) * 3.1415927F);
    this.spine[2].setRotationPoint(-2.0F, 6.9F + MathHelper.cos(this.spine[1].rotateAngleX) * 10.0F, -0.5F + MathHelper.sin(this.spine[1].rotateAngleX) * 10.0F);
    this.spine[2].rotateAngleX = ((0.265F + 0.1F * f6) * 3.1415927F);
    this.heads[0].rotateAngleY = (p_78087_4_ / 57.295776F);
    this.heads[0].rotateAngleX = (p_78087_5_ / 57.295776F);
  }
  
  public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
  {
	  EntityDiamondWither entitywither = (EntityDiamondWither)p_78086_1_;
    for (int i = 1; i < 3; i++)
    {
      this.heads[i].rotateAngleY = ((entitywither.func_82207_a(i - 1) - p_78086_1_.renderYawOffset) / 57.295776F);
      this.heads[i].rotateAngleX = (entitywither.func_82210_r(i - 1) / 57.295776F);
    }
  }
}
