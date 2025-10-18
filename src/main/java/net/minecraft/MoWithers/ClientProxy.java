package net.minecraft.MoWithers;

import net.minecraft.MoWithers.render.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.wither.*;
import net.minecraft.entity.witherskulls.*;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


public class ClientProxy extends CommonProxy{
	@Override
	public void preInit(FMLPreInitializationEvent e) 
	{
		super.preInit(e);
	}

	@Override
	public void init(FMLInitializationEvent e) 
	{
		super.init(e);
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) 
	{
		super.postInit(e);
		MoWitherItems.registerRenders();
	}
	
	  public void registerRenderThings()
	  {
		RenderManager manager = Minecraft.getMinecraft().getRenderManager();
		  
	    RenderingRegistry.registerEntityRenderingHandler(Sculpture.class, new RenderSculpture(manager));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherSkullBlaze.class, new RenderBlazeSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherSkullCreeper.class, new RenderCreeperSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherSkullEnder.class, new RenderEnderSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityFireSkull.class, new RenderFireSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityLightningSkull.class, new RenderLightningSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityEarthSkull.class, new RenderEarthSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWaterSkull.class, new RenderWaterSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityIceSkull.class, new RenderIceSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityAirSkull.class, new RenderAirSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityVoidSkull.class, new RenderVoidSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityPinkSkull.class, new RenderPinkSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityMettalicSkull.class, new RenderMettalicSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherSkullFriendly.class, new RenderFriendlySkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityLoveSkull.class, new RenderLoveSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherDragonProjectile.class, new RenderWitherDragonProjectile(manager, 3F));
	    RenderingRegistry.registerEntityRenderingHandler(EntityMagmaSkull.class, new RenderMagmaSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBlockSkull.class, new RenderBlockSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityShotPresent.class, new RenderShotPresent(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityMechaSkull.class, new RenderMechaSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityHurricaneSkull.class, new RenderHurricaneSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherSkullZombie.class, new RenderZombieSkull(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBlockSkull.class, new RenderBlockSkull(manager));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityIceHolder.class, new RenderIceHolder(manager));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBlackHole.class, new RenderBlackHole(manager));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherGirl.class, new RenderWitherGirl(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherGirlPink.class, new RenderWitherGirlPink(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherGirlVoid.class, new RenderWitherGirlVoid(Minecraft.getMinecraft().getRenderManager()));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherCultist.class, new RenderCultist(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherCultistGreater.class, new RenderCultistGreater(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherCultistLeader.class, new RenderCultistLeader(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherGolem.class, new RenderWitherGolem(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherSpider.class, new RenderWitherSpider(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherBlaze.class, new RenderWitherBlaze(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherCreeper.class, new RenderWitherCreeper(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherZombie.class, new RenderWitherZombie(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityLostSkull.class, new RenderLostSkull(Minecraft.getMinecraft().getRenderManager()));
	    
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherCultistLeaderTransformation.class, new RenderWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityFireWither.class, new RenderFireWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityLightningWither.class, new RenderLightningWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityEarthWither.class, new RenderEarthWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWaterWither.class, new RenderWaterWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityIceWither.class, new RenderIceWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityAirWither.class, new RenderAirWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityVoidWither.class, new RenderVoidWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityPinkWither.class, new RenderPinkWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityRichWither.class, new RenderRichWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntitySandWither.class, new RenderSandWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityFriendlyWither.class, new RenderFriendlyWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityValentinesDayWither.class, new RenderValentinesDayWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityWitherDragon.class, new RenderWitherDragon(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityMagmaWither.class, new RenderMagmaWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBabyWither.class, new RenderBabyWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBlazeWither.class, new RenderBlazeWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityCreeperWither.class, new RenderCreeperWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityEnderWither.class, new RenderEnderWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntitySaintPatricksDayWither.class, new RenderSaintPatricksDayWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityDementedWither.class, new RenderDementedWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityHalloweenWither.class, new RenderHalloweenWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityGhastWither.class, new RenderGhastWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityChristmasWither.class, new RenderChristmasWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityEnchantedWither.class, new RenderEnchantmentWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityIronWither.class, new RenderIronWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityGoldenWither.class, new RenderGoldenWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityDiamondWither.class, new RenderDiamondWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityEmeraldWither.class, new RenderEmeraldWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityCoalWither.class, new RenderCoalWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityRedstoneWither.class, new RenderRedstoneWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityLapisWither.class, new RenderLapisWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityMechaWither.class, new RenderMechaWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityHurricaneWither.class, new RenderHurricaneWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityZombieWither.class, new RenderZombieWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityGravelWither.class, new RenderGravelWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityGlassWither.class, new RenderGlassWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntitySpawnerWither.class, new RenderSpawnerWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityObsidianWither.class, new RenderObsidianWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityBedrockWither.class, new RenderBedrockWither(Minecraft.getMinecraft().getRenderManager()));
	    RenderingRegistry.registerEntityRenderingHandler(EntityAvatarWither.class, new RenderAvatarWither(Minecraft.getMinecraft().getRenderManager()));
	  }

}
