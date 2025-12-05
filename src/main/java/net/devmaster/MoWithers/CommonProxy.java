package net.devmaster.MoWithers;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy 
{
	  public void registerRenders() {}
	  
	  public void preInit(FMLPreInitializationEvent e) 
	  {
		  //	RenderTheMobs.registerEntity();
		  //  MoWitherItems.MOWI();
		    registerRenderThings();
		 //   CraftingRecipes.initCrafting();
	  }
	  
	  public void init(FMLInitializationEvent e) {}
	  
	  public void postInit(FMLPostInitializationEvent e) {}
	  
	  public void registerRenderThings()
	  {

	  }
}
