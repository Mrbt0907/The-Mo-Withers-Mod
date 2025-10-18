package net.minecraft.MoWithers;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.wither.EntityWitherBlaze;
import net.minecraft.entity.wither.EntityWitherCreeper;
import net.minecraft.entity.wither.EntityWitherCultist;
import net.minecraft.entity.wither.EntityWitherCultistGreater;
import net.minecraft.entity.wither.EntityWitherCultistLeader;
import net.minecraft.entity.wither.EntityWitherGolem;
import net.minecraft.entity.wither.EntityWitherSpider;
import net.minecraft.entity.wither.EntityWitherZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenHell;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = MoWithers.MODID, name= MoWithers.MODNAME, version = MoWithers.VERSION)
public class MoWithers
{
	public static final String MODNAME = "Mo' Withers Mod";
	public static final String MODID = "mowithers";
	public static final String VERSION = "0.41";
	@SidedProxy(clientSide="net.minecraft.MoWithers.ClientProxy", serverSide="net.minecraft.MoWithers.CommonProxy")
	public static CommonProxy proxy;
    
    @Metadata
    public static ModMetadata meta;

    @Instance(MoWithers.MODID)
    public static MoWithers modInstance;
    public static final CreativeTabs mowithers = new CreativeTabs("mowithers") 
    {
        @Override public Item getTabIconItem() 
        {
            return Items.bone;
        }
    };
    
    /**
     * Detects for non-cultist, non-wither mobs
     */
    public static boolean isntACultist(Class p_70686_1_)
    {
        return p_70686_1_ != EntityWitherCultist.class && p_70686_1_ != EntityWitherCultistGreater.class && p_70686_1_ != EntityWitherCultistLeader.class && p_70686_1_ != EntityWither.class && p_70686_1_ != EntityWitherBlaze.class && p_70686_1_ != EntityWitherCreeper.class && p_70686_1_ != EntityWitherGolem.class && p_70686_1_ != EntityWitherZombie.class && p_70686_1_ != EntityWitherSpider.class;
    }
    
    private Logger logger;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) 
	{
        logger = e.getModLog();
		proxy.preInit(e);
        logger.info("Loading The Mo' Withers Mod...");
        logger.debug("Pre Initialization started!");
        logger.debug("Finished pre-init for The Mo' Withers Mod!");
	}

	@EventHandler
	public void init(FMLInitializationEvent e) 
	{
		proxy.init(e);
        logger.debug("Initialization started!");
        MoWithersAchievments.addAchievments();
        GameRegistry.registerWorldGenerator(new MoWithersStructures(), 5);
        logger.debug("Finished init for The Mo' Withers Mod!");
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) 
	{
		proxy.postInit(e);
        logger.debug("Post Initialization started!");
		proxy.registerRenderThings();
        logger.debug("Finished post-init for The Mo' Withers Mod!");
        logger.info("Finished The Mo' Withers Mod!");
	}
}
