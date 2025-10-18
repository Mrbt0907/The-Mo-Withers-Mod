package net.minecraft.MoWithers.render.shields;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.MoWithers.render.RenderSpawnerWither;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.wither.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSpawnerWitherSpawner implements LayerRenderer
{
    private final RenderSpawnerWither field_177152_a;

    public LayerSpawnerWitherSpawner(RenderSpawnerWither p_i46110_1_)
    {
        this.field_177152_a = p_i46110_1_;
    }

    public void func_177151_a(EntitySpawnerWither p_177151_1_, float p_177151_2_, float p_177151_3_, float p_177151_4_, float p_177151_5_, float p_177151_6_, float p_177151_7_, float p_177151_8_)
    {
        if (!p_177151_1_.isInvisible())
        {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, -0.75F, 0.0F);
            float f7 = (p_177151_1_.ticksExisted + p_177151_4_);
            float f8 = f7 * 0.1F;
            GlStateManager.translate(0.0F, MathHelper.cos(f7 * 0.3F) * 0.1F, 0.0F);
            GlStateManager.rotate(f8 * 45.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            Minecraft.getMinecraft().getItemRenderer().renderItem(p_177151_1_, new ItemStack(Blocks.mob_spawner, 1), null);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, MathHelper.cos(f7 * 0.3F + 5F) * 0.1F, 0.0F);
            renderMob(p_177151_1_, p_177151_1_.posX, p_177151_1_.posY, p_177151_1_.posZ, p_177151_4_);
            GlStateManager.popMatrix();
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }

    public void doRenderLayer(EntityLivingBase p_177141_1_, float p_177141_2_, float p_177141_3_, float p_177141_4_, float p_177141_5_, float p_177141_6_, float p_177141_7_, float p_177141_8_)
    {
        this.func_177151_a((EntitySpawnerWither)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
    
    public static void renderMob(EntitySpawnerWither mobSpawnerLogic, double posX, double posY, double posZ, float partialTicks)
    {
        Entity entity = EntityList.createEntityByName(EntityList.getStringFromID(mobSpawnerLogic.getHeldItem().getMetadata()), mobSpawnerLogic.worldObj);

        if (entity != null)
        {
            float f1 = 0.4375F;
            GlStateManager.translate(0.0F, -0.25F, 0.0F);
            GlStateManager.rotate((float)(mobSpawnerLogic.prevMobRotation + (mobSpawnerLogic.mobRotation - mobSpawnerLogic.prevMobRotation) * (double)partialTicks) * -10F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(f1, f1, f1);
            if (entity instanceof EntityDragon)
            	GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            entity.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks);
        }
    }
}