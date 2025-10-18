package net.minecraft.MoWithers.render.shields;

import net.minecraft.MoWithers.models.*;
import net.minecraft.MoWithers.render.RenderGhastWither;
import net.minecraft.MoWithers.render.RenderSculpture;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.wither.EntityRichWither;
import net.minecraft.entity.wither.Sculpture;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerScupltureTransformation implements LayerRenderer 
{
	private final RenderSculpture field_177215_b;
	
	  public LayerScupltureTransformation(RenderSculpture p_i46105_1_)
	  {
	    this.field_177215_b = p_i46105_1_;
	  }
	
	  public void func_177214_a(Sculpture p_177214_1_, float p_177214_2_, float p_177214_3_, float p_177214_4_, float p_177214_5_, float p_177214_6_, float p_177214_7_, float p_177214_8_)
	  {
		  	ModelWitherGirl regularWitherGirl = new ModelWitherGirl(0.0F);
		  	ModelFiveHeadedWither dementedWither = new ModelFiveHeadedWither(0.0F);
			ModelWither regularWither = new ModelWither(0.0F);
			ModelWitherDragon witherdragon = new ModelWitherDragon(0.0F);
			ModelWither regularWitherSlight = new ModelWither(0.001F);
			ModelWither sheild = new ModelWither(0.5F);
		
	      	GlStateManager.depthMask(!p_177214_1_.isInvisible());
	      	GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
	      	GlStateManager.scale(0.1F, 0.1F, 0.1F);
	      	GlStateManager.translate(0F, 6F, -4F);
	      	GlStateManager.translate(0F, MathHelper.cos(p_177214_1_.ticksExisted * 0.1F) * 0.2F, 0F);
	      	GlStateManager.rotate(p_177214_1_.ticksExisted * 2F, 0F, 1F, 0F);
		  
		    if (p_177214_1_.getEssenceID() == 1)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/air_wither.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
	      	
		    if (p_177214_1_.getEssenceID() == 2)
		    {
		    	GlStateManager.scale(3F, 3F, 3F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/earth_wither.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
	      	
		    if (p_177214_1_.getEssenceID() == 3)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/fire_wither.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
	      	
		    if (p_177214_1_.getEssenceID() == 4)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/water_wither.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
	      	
		    if (p_177214_1_.getEssenceID() == 5)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/ice_wither.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
	      	
		    if (p_177214_1_.getEssenceID() == 6)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/lightning_wither.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    	this.field_177215_b.bindTexture(new ResourceLocation("textures/entity/creeper/creeper_armor.png"));
		    	GlStateManager.color(1.0F, 0.75F, 0.0F, 1.0F);
		    	sheild.setModelAttributes(this.field_177215_b.getMainModel());
		    	sheild.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    } 
	      	
		    if (p_177214_1_.getEssenceID() == 7)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/air_wither.png"));
		    	regularWitherSlight.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWitherSlight.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/hurricane_wave.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }  
	      	
		    if (p_177214_1_.getEssenceID() == 8)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/air_wither.png"));
		    	regularWitherSlight.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWitherSlight.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/lava_wither_lava.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }  
	    
		    if (p_177214_1_.getEssenceID() == 9)
		    {
		    	GlStateManager.scale(1.05F, 1.05F, 1.05F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/wither_girl.png"));
		    	regularWitherGirl.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWitherGirl.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
	    
		    if (p_177214_1_.getEssenceID() == 10)
		    {
		    	GlStateManager.scale(0.925F, 0.925F, 0.925F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/wither_girl_pink.png"));
		      	regularWitherGirl.setModelAttributes(this.field_177215_b.getMainModel());
		      	regularWitherGirl.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
	    
		    if (p_177214_1_.getEssenceID() == 11)
		    {
		    	GlStateManager.scale(1.15F, 1.15F, 1.15F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/wither_girl_void.png"));
		    	regularWitherGirl.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWitherGirl.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
	    
		    if (p_177214_1_.getEssenceID() == 12)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("textures/entity/wither/wither.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
		    
		    if (p_177214_1_.getEssenceID() == 13)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/friendly_wither.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
		    
		    if (p_177214_1_.getEssenceID() == 14)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/wither_pink.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
		    
		    if (p_177214_1_.getEssenceID() == 15)
		    {
		    	GlStateManager.scale(2F, 2F, 2F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("textures/entity/wither/wither.png"));
		    	dementedWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	dementedWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
		    
		    if (p_177214_1_.getEssenceID() == 16)
		    {
		    	GlStateManager.scale(4F, 4F, 4F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/void_wither.png"));
		    	regularWither.setModelAttributes(this.field_177215_b.getMainModel());
		    	regularWither.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
		    
		    if (p_177214_1_.getEssenceID() == 17)
		    {
		    	GlStateManager.scale(1.5F, 1.5F, 1.5F);
		    	GlStateManager.translate(0F, 3F, 0F);
		    	this.field_177215_b.bindTexture(new ResourceLocation("mowithers", "textures/entities/wither_dragon.png"));
		    	witherdragon.setModelAttributes(this.field_177215_b.getMainModel());
		    	witherdragon.render(p_177214_1_, p_177214_2_, p_177214_3_, p_177214_5_, p_177214_6_, p_177214_7_, p_177214_8_);
		    }
	  }
	  
	  public boolean shouldCombineTextures()
	  {
	    return false;
	  }
	  
	  public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
	  {
	    func_177214_a((Sculpture)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
	  }

}
