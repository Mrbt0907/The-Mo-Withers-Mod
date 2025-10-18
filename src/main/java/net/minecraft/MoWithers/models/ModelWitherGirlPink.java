package net.minecraft.MoWithers.models;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.EntityWitherGirl;
import net.minecraft.entity.wither.EntityWitherGirlPink;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelWitherGirlPink
  extends ModelBiped
{
  public ModelRenderer supporter;
  public ModelRenderer[] extraHeads;
  public ModelRenderer bipedBigBoobs;
  public ModelRenderer bipedBigPreg;
  public ModelRenderer bipedHair1;
  public ModelRenderer bipedHair2;
  public boolean isFlying;
  public boolean beingInnocent;
  public boolean isTreadingWater;
  
  public ModelWitherGirlPink()
  {
    this(0.0F);
  }
  
  public ModelWitherGirlPink(float p_i1148_1_)
  {
    this(p_i1148_1_, 0.0F, 64, 64);
  }
  
  public ModelWitherGirlPink(float p_i1149_1_, float p_i1149_2_, int p_i1149_3_, int p_i1149_4_)
  {
    super(p_i1149_1_, 0.0F, 64, 64);
    this.supporter = new ModelRenderer(this, 0, 32);
    this.supporter.addBox(-14.0F, -1.0F, 1.0F, 28, 2, 2, p_i1149_1_);
    this.supporter.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.extraHeads = new ModelRenderer[2];
    this.extraHeads[0] = new ModelRenderer(this, 0, 36);
    this.extraHeads[0].addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, p_i1149_1_);
    this.extraHeads[0].rotationPointX = -13.0F;
    this.extraHeads[0].rotationPointZ = 2.0F;
    this.extraHeads[1] = new ModelRenderer(this, 0, 36);
    this.extraHeads[1].addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6, p_i1149_1_);
    this.extraHeads[1].rotationPointX = 14.0F;
    this.extraHeads[1].rotationPointZ = 2.0F;
    this.bipedBigBoobs = new ModelRenderer(this, 0, 48);
    this.bipedBigBoobs.addBox(-4.0F, 2.0F, -4.8F, 8, 4, 3, p_i1149_1_);
    this.bipedBigBoobs.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.bipedBigPreg = new ModelRenderer(this, 20, 54);
    this.bipedBigPreg.addBox(-4.0F, 6.5F, -6.0F, 8, 6, 4, p_i1149_1_);
    this.bipedBigPreg.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.bipedHair1 = new ModelRenderer(this, 40, 36);
    this.bipedHair1.addBox(-4.0F, -2.0F, 2.0F, 8, 10, 1, p_i1149_1_);
    this.bipedHair1.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.bipedHair2 = new ModelRenderer(this, 40, 36);
    this.bipedHair2.addBox(-4.0F, -1.0F, 3.0F, 8, 10, 1, p_i1149_1_);
    this.bipedHair2.setRotationPoint(0.0F, 0.0F, 0.0F);
    this.bipedLeftArm.setRotationPoint(5.0F, 1.0F + MathHelper.cos(this.bipedBody.rotateAngleX) * 1.0F, 0.0F + MathHelper.sin(this.bipedBody.rotateAngleX) * 2.0F);
    this.bipedRightArm.setRotationPoint(-5.0F, 1.0F + MathHelper.cos(this.bipedBody.rotateAngleX) * 1.0F, 0.0F + MathHelper.sin(this.bipedBody.rotateAngleX) * 2.0F);
    this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + MathHelper.cos(this.bipedBody.rotateAngleX) * 12.0F, 0.0F + MathHelper.sin(this.bipedBody.rotateAngleX) * 12.0F);
    this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + MathHelper.cos(this.bipedBody.rotateAngleX) * 12.0F, 0.0F + MathHelper.sin(this.bipedBody.rotateAngleX) * 12.0F);
  }
  
  public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_)
  {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
    
    GlStateManager.pushMatrix();
    if (this.isChild)
    {
      float f6 = 3.0F;
      GlStateManager.scale(2.25F / f6, 2.25F / f6, 2.25F / f6);
      GlStateManager.translate(0.0F, 22.0F * p_78088_7_, 0.0F);
      this.bipedHead.render(p_78088_7_);
      this.bipedHeadwear.render(p_78088_7_);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      GlStateManager.scale(1.0F / f6, 1.0F / f6, 1.0F / f6);
      GlStateManager.translate(0.0F, 48.0F * p_78088_7_, 0.0F);
      this.bipedBody.render(p_78088_7_);
      this.bipedRightArm.render(p_78088_7_);
      this.bipedLeftArm.render(p_78088_7_);
      this.bipedRightLeg.render(p_78088_7_);
      this.bipedLeftLeg.render(p_78088_7_);
    }
    else
    {
      if (p_78088_1_.isSneaking()) {
        GlStateManager.translate(0.0F, 0.2F, 0.0F);
      }
      this.bipedHead.render(p_78088_7_);
      this.bipedBody.render(p_78088_7_);
      this.bipedRightArm.render(p_78088_7_);
      this.bipedLeftArm.render(p_78088_7_);
      this.bipedRightLeg.render(p_78088_7_);
      this.bipedLeftLeg.render(p_78088_7_);
      this.bipedBigBoobs.render(p_78088_7_);
      this.bipedHair1.render(p_78088_7_);
      this.bipedHair2.render(p_78088_7_);
      this.bipedHeadwear.render(p_78088_7_);
      this.supporter.render(p_78088_7_);
      
      ModelRenderer[] amodelrenderer = this.extraHeads;
      int i = amodelrenderer.length;
      for (int j = 0; j < i; j++)
      {
        ModelRenderer modelrenderer = amodelrenderer[j];
        modelrenderer.render(p_78088_7_);
      }
      
      if (this.isSneak)
    	  this.bipedBigPreg.render(p_78088_7_);
    }
    GlStateManager.popMatrix();
  }
  
  public void setLivingAnimations(EntityLivingBase p_78086_1_, float p_78086_2_, float p_78086_3_, float p_78086_4_)
  {
    EntityWitherGirlPink entitywither = (EntityWitherGirlPink)p_78086_1_;
    Block block1 = entitywither.worldObj.getBlockState(new BlockPos(entitywither.posX, entitywither.posY - 0.25D, entitywither.posZ)).getBlock();
    Block block2 = entitywither.worldObj.getBlockState(new BlockPos(entitywither.posX, entitywither.posY - 0.75D, entitywither.posZ)).getBlock();
    if (!entitywither.onGround && !block1.getMaterial().isLiquid() && !block2.getMaterial().isLiquid() && (entitywither.ridingEntity == null)) {
      this.isFlying = true;
    } else {
      this.isFlying = false;
    }
    this.isRiding = entitywither.isRiding();
    this.isSneak = entitywither.isSneaking();
    this.aimedBow = block1.getMaterial().isLiquid() && block2.getMaterial().isLiquid();
    this.isTreadingWater = (double)entitywither.limbSwingAmount < 0.001D;
    this.swingProgress = entitywither.getSwingProgress(p_78086_4_);
    
    this.beingInnocent = (!entitywither.isPregnant && (entitywither.onGround) && (entitywither.worldObj.getClosestPlayerToEntity(entitywither, 6.0D) != null));
    this.extraHeads[0].rotateAngleY = ((entitywither.func_82207_a(0) - p_78086_1_.renderYawOffset) / 57.295776F);
    this.extraHeads[0].rotateAngleX = (entitywither.func_82210_r(0) / 57.295776F);
    this.extraHeads[1].rotateAngleY = ((entitywither.func_82207_a(1) - p_78086_1_.renderYawOffset) / 57.295776F);
    this.extraHeads[1].rotateAngleX = (entitywither.func_82210_r(1) / 57.295776F);
  }
  
  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_)
  {
    super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);
    this.copyModelAngles(bipedHead, bipedHeadwear);
    this.copyModelAngles(bipedBody, bipedBigPreg);
    if (this.isRiding)
    {
      this.bipedRightArm.rotateAngleX += -0.62831855F;
      this.bipedLeftArm.rotateAngleX += -0.62831855F;
      this.bipedRightLeg.rotateAngleX = -1.2566371F;
      this.bipedLeftLeg.rotateAngleX = -1.2566371F;
      this.bipedRightLeg.rotateAngleY = 0.31415927F;
      this.bipedLeftLeg.rotateAngleY = -0.31415927F;
    }
    float f6 = MathHelper.cos(p_78087_3_ * 0.122857146F);
    this.bipedHair1.rotateAngleY = this.bipedHead.rotateAngleY;
    this.bipedHair1.rotateAngleX = (this.bipedHead.rotateAngleX / 3.0F);
    this.bipedHair2.rotateAngleY = this.bipedHead.rotateAngleY;
    this.bipedHair2.rotateAngleX = (this.bipedHead.rotateAngleX / 2.0F);
    this.bipedHair1.rotateAngleZ = this.bipedHead.rotateAngleZ;
    this.bipedHair2.rotateAngleZ = this.bipedHead.rotateAngleZ;
    this.bipedBigBoobs.rotateAngleX = this.bipedBody.rotateAngleX;
    this.bipedBigPreg.rotateAngleX = this.bipedBody.rotateAngleX;
    this.bipedBigBoobs.rotateAngleX = (MathHelper.cos(p_78087_1_ * 1.5F) * -0.15F * p_78087_2_);
    this.bipedBigBoobs.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.09F) * 0.025F;
    if (this.isFlying)
    {
    	this.bipedBigBoobs.rotateAngleX = this.bipedBody.rotateAngleX;
        this.bipedBigPreg.rotateAngleX = this.bipedBody.rotateAngleX;
      this.bipedLeftLeg.rotateAngleX = ((0.15F + 0.15F * f6) * 3.1415927F);
      this.bipedRightLeg.rotateAngleX = ((0.15F + 0.15F * f6) * 3.1415927F);
      this.bipedLeftLeg.rotateAngleZ = ((-0.05F + 0.05F * f6) * 3.1415927F);
      this.bipedRightLeg.rotateAngleZ = ((0.05F + -0.05F * f6) * 3.1415927F);
      this.bipedLeftLeg.rotateAngleY = ((-0.05F + 0.05F * f6) * 3.1415927F);
      this.bipedRightLeg.rotateAngleY = ((0.05F + -0.05F * f6) * 3.1415927F);
      this.bipedRightArm.rotateAngleX = ((0.1F + 0.2F * f6) * 3.1415927F);
      this.bipedLeftArm.rotateAngleX = ((0.1F + 0.2F * f6) * 3.1415927F);
      this.bipedRightArm.rotateAngleY = ((0.1F + -0.15F * f6) * 3.1415927F);
      this.bipedLeftArm.rotateAngleY = ((-0.1F + 0.15F * f6) * 3.1415927F);
      this.bipedLeftArm.rotateAngleZ = ((-0.075F + 0.05F * f6) * 3.1415927F);
      this.bipedRightArm.rotateAngleZ = ((0.075F + -0.05F * f6) * 3.1415927F);
    }
    else
    {
      this.bipedRightArm.rotateAngleX = (MathHelper.cos(p_78087_1_ * 1.5F + 3.1415927F) * 2.0F * p_78087_2_ * 0.5F);
      this.bipedLeftArm.rotateAngleX = (MathHelper.cos(p_78087_1_ * 1.5F) * 2.0F * p_78087_2_ * 0.5F);
      this.bipedRightArm.rotateAngleZ = 0.0F;
      this.bipedLeftArm.rotateAngleZ = 0.0F;
      this.bipedRightLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 1.5F) * 1.4F * p_78087_2_);
      this.bipedLeftLeg.rotateAngleX = (MathHelper.cos(p_78087_1_ * 1.5F + 3.1415927F) * 1.4F * p_78087_2_);
      this.bipedRightLeg.rotateAngleZ = 0.0F;
      this.bipedLeftLeg.rotateAngleZ = 0.0F;
      this.bipedRightArm.rotateAngleY = 0.0F;
      this.bipedLeftArm.rotateAngleY = 0.0F;
      this.bipedRightLeg.rotateAngleY = 0.0F;
      this.bipedLeftLeg.rotateAngleY = 0.0F;
      if (this.swingProgress > -9990.0F)
      {
        float f61 = this.swingProgress;
        this.bipedBody.rotateAngleY = (MathHelper.sin(MathHelper.sqrt_float(f61) * 3.1415927F * 2.0F) * 0.2F);
        this.bipedRightArm.rotationPointZ = (MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F);
        this.bipedRightArm.rotationPointX = (-MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F);
        this.bipedLeftArm.rotationPointZ = (-MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F);
        this.bipedLeftArm.rotationPointX = (MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F);
        this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
        this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
        this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
        f61 = 1.0F - this.swingProgress;
        f61 *= f61;
        f61 *= f61;
        f61 = 1.0F - f61;
        float f7 = MathHelper.sin(f61 * 3.1415927F);
        float f8 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
        this.bipedRightArm.rotateAngleX = ((float)(this.bipedRightArm.rotateAngleX - (f7 * 1.2D + f8)));
        this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
        this.bipedRightArm.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
      }
      if (this.isRiding)
      {
          this.bipedHead.rotateAngleX -= 0.75F;
          this.bipedHeadwear.rotateAngleX -= 0.75F;
          this.bipedRightArm.rotateAngleX = -((float)Math.PI / 3F);
          this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 3F);
          this.bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 3.5F);
          this.bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 3.5F);
          this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
          this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
      }
      if (this.beingInnocent)
      {
        this.bipedHead.rotateAngleZ = 0.3F;
        this.bipedHead.rotateAngleX = (p_78087_5_ / 57.295776F - 0.5F);
        this.bipedHeadwear.rotateAngleX = (p_78087_5_ / 57.295776F - 0.5F);
      }
      else
      {
        this.bipedHead.rotateAngleZ = 0.0F;
        this.bipedHead.rotateAngleX = (p_78087_5_ / 57.295776F);
        this.bipedHeadwear.rotateAngleX = (p_78087_5_ / 57.295776F);
      }
      if (this.aimedBow)
      {
          this.bipedHead.rotateAngleX -= 0.5F + MathHelper.cos(p_78087_3_ * 0.2F) * 0.15F;
          this.bipedHeadwear.rotateAngleX -= 0.5F + MathHelper.cos(p_78087_3_ * 0.2F) * 0.15F;
          this.bipedRightArm.rotateAngleX -= 1.75F;
          this.bipedLeftArm.rotateAngleX -= 1.75F;
          this.bipedRightLeg.rotateAngleX += 0.75F;
          this.bipedLeftLeg.rotateAngleX += 0.75F;
          this.bipedRightLeg.rotateAngleX *= 0.5F;
          this.bipedLeftLeg.rotateAngleX *= 0.5F;
          this.bipedRightArm.rotateAngleZ -= 0.5F;
          this.bipedLeftArm.rotateAngleZ += 0.5F;
          this.bipedRightLeg.rotateAngleZ += 0.5F;
          this.bipedLeftLeg.rotateAngleZ -= 0.5F;
          if (!this.isTreadingWater)
          {
              this.bipedHead.rotateAngleX -= 0.5F;
              this.bipedHeadwear.rotateAngleX -= 0.5F;
              this.bipedRightArm.rotateAngleX -= 1.25F;
              this.bipedLeftArm.rotateAngleX -= 1.25F;
              this.bipedRightLeg.rotateAngleX -= 0.25F;
              this.bipedLeftLeg.rotateAngleX -= 0.25F;
          }
          else
          {
              this.bipedRightLeg.rotateAngleX += MathHelper.cos(p_78087_3_ * 0.3F) * 0.75F;
              this.bipedLeftLeg.rotateAngleX -= MathHelper.cos(p_78087_3_ * 0.3F) * 0.75F;
              this.bipedRightArm.rotateAngleY += MathHelper.cos(p_78087_3_ * 0.2F) * 0.5F;
              this.bipedLeftArm.rotateAngleY -= MathHelper.cos(p_78087_3_ * 0.2F) * 0.5F;
          }
      }
      if (this.isRiding)
      {
          this.bipedHead.rotateAngleX -= 0.75F;
          this.bipedHeadwear.rotateAngleX -= 0.75F;
    	  this.bipedHair1.rotateAngleX -= 0.75F;
    	  this.bipedHair2.rotateAngleX -= 0.75F;

          if (this.isSneak)
          {
        	  this.bipedBigBoobs.rotateAngleX -= 0.25F;
        	  this.bipedHead.rotateAngleX += 0.25F;
        	  this.bipedHeadwear.rotateAngleX += 0.25F;
        	  this.bipedHair1.rotateAngleX += 0.75F;
        	  this.bipedHair2.rotateAngleX += 0.75F;
              this.bipedRightArm.rotateAngleX = -1.75F;
              this.bipedLeftArm.rotateAngleX = -1.75F;
              this.bipedRightLeg.rotateAngleX = -1.75F;
              this.bipedLeftLeg.rotateAngleX = -1.75F;
              this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 8F);
              this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 8F);
              this.bipedRightArm.rotateAngleY -= 0.1F;
              this.bipedLeftArm.rotateAngleY += 0.1F;
              this.bipedBody.rotateAngleX = -0.6F;
              this.bipedBigPreg.rotateAngleX = -0.6F;
              this.bipedRightLeg.rotationPointZ = -6.0F;
              this.bipedLeftLeg.rotationPointZ = -6.0F;
              this.bipedRightLeg.rotationPointY = 10F;
              this.bipedLeftLeg.rotationPointY = 10F;
              this.bipedBigBoobs.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.13F) * 0.05F;
              this.bipedHead.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.13F) * 0.05F;
              this.bipedHeadwear.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.13F) * 0.05F;
          }
          else
          {
              this.bipedRightArm.rotateAngleX += -0.62831855F;
              this.bipedLeftArm.rotateAngleX += -0.62831855F;
              this.bipedRightLeg.rotateAngleX = -1.2566371F;
              this.bipedLeftLeg.rotateAngleX = -1.2566371F;
              this.bipedRightLeg.rotateAngleY = 0.31415927F;
              this.bipedLeftLeg.rotateAngleY = -0.31415927F;
              
              if (this.isChild)
              {
            	  this.bipedHead.rotateAngleX += 0.5F;
            	  this.bipedHeadwear.rotateAngleX += 0.5F;
                  this.bipedRightArm.rotateAngleX = -1.75F;
                  this.bipedLeftArm.rotateAngleX = -1.75F;
                  this.bipedRightLeg.rotateAngleX = -1.75F;
                  this.bipedLeftLeg.rotateAngleX = -1.75F;
                  this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 8F);
                  this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 8F);
                  this.bipedRightArm.rotateAngleY += 0.2F;
                  this.bipedLeftArm.rotateAngleY -= 0.2F;
                  this.bipedBody.rotateAngleX = -0.6F;
                  this.bipedRightLeg.rotationPointZ = -6.0F;
                  this.bipedLeftLeg.rotationPointZ = -6.0F;
                  this.bipedRightLeg.rotationPointY = 10F;
                  this.bipedLeftLeg.rotationPointY = 10F;
              }
          }
      }
      else
      {
          if (this.isSneak)
          {
              this.bipedBody.rotateAngleX = -0.6F;
              this.bipedBigPreg.rotateAngleX = -0.6F;
              this.bipedBigBoobs.rotateAngleX -= 0.5F;
              this.bipedRightArm.rotateAngleX += 0.2F;
              this.bipedLeftArm.rotateAngleX += 0.2F;
              this.bipedRightArm.rotateAngleY += 0.15F;
              this.bipedLeftArm.rotateAngleY -= 0.15F;
              this.bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 4F);
              this.bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 4F);
              this.bipedRightLeg.rotateAngleZ -= 0.2F;
              this.bipedLeftLeg.rotateAngleZ += 0.2F;
              this.bipedRightLeg.rotateAngleY += 0.3F;
              this.bipedLeftLeg.rotateAngleY -= 0.3F;
              this.bipedRightLeg.rotationPointZ = -6.0F;
              this.bipedLeftLeg.rotationPointZ = -6.0F;
              this.bipedRightLeg.rotationPointY = 10F;
              this.bipedLeftLeg.rotationPointY = 10F;
              this.bipedBody.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.13F) * 0.05F;
              this.bipedBigPreg.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.13F) * 0.05F;
              this.bipedBigBoobs.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.13F) * 0.05F;
              this.bipedHead.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.13F) * 0.05F;
              this.bipedHeadwear.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.13F) * 0.05F;
          }
      }
      if (this.beingInnocent)
      {
        f6 = 0.0F;
        float f7 = 0.0F;
        if (this.isSneak)
        {
            this.bipedRightArm.rotateAngleY = (-(0.1F - f6 * 0.6F) + this.bipedHead.rotateAngleY - 0.3F);
            this.bipedLeftArm.rotateAngleY = (0.1F - f6 * 0.6F + this.bipedHead.rotateAngleY + 0.3F);
        }
        else
        {
            this.bipedRightArm.rotateAngleY = (-(0.1F - f6 * 0.6F) + this.bipedHead.rotateAngleY - 0.5F);
            this.bipedLeftArm.rotateAngleY = (0.1F - f6 * 0.6F + this.bipedHead.rotateAngleY + 0.5F);
            this.bipedRightArm.rotateAngleX = (-0.6F + this.bipedHead.rotateAngleX) - MathHelper.sin(p_78087_3_ * 0.1F) * 0.1F;
            this.bipedLeftArm.rotateAngleX = (-0.6F + this.bipedHead.rotateAngleX) - MathHelper.sin(p_78087_3_ * 0.1F) * 0.1F;
        }
        this.bipedRightArm.rotateAngleZ = 0.2F;
        this.bipedLeftArm.rotateAngleZ = -0.2F;
        this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
        this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
      }
      else
      {
        this.bipedHead.rotateAngleZ = 0.0F;
      }
    }
    if (!this.isSneak)
    {
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.05F + 0.05F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.05F;
    }
  }
  
  public void setModelAttributes(ModelBase p_178686_1_)
  {
    super.setModelAttributes(p_178686_1_);
    if ((p_178686_1_ instanceof ModelWitherGirlPink))
    {
      ModelWitherGirlPink modelbiped = (ModelWitherGirlPink)p_178686_1_;
      this.isFlying = modelbiped.isFlying;
      this.beingInnocent = modelbiped.beingInnocent;
      this.isTreadingWater = modelbiped.isTreadingWater;
    }
  }
}
