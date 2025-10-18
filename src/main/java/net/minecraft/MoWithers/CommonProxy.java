package net.minecraft.MoWithers;

import net.minecraft.MoWithers.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.entity.wither.*;
import net.minecraft.entity.witherskulls.*;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy 
{
	  public void registerRenders() {}
	  
	  public void preInit(FMLPreInitializationEvent e) 
	  {
		  	RenderTheMobs.registerEntity();
		    MoWitherItems.MOWI();
		    registerRenderThings();
		    CraftingRecipes.initCrafting();
	  }
	  
	  public void init(FMLInitializationEvent e) {}
	  
	  public void postInit(FMLPostInitializationEvent e) {}
	  
	  public void registerRenderThings()
	  {

	  }
}
