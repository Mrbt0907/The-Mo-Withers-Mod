package net.minecraft.MoWithers;

import java.util.Map;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.wither.*;
import net.minecraft.entity.witherskulls.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class RenderTheMobs
{
  public static void TheWithers() {}
  
  public static void registerEntity()
  {
	  createProjectile(EntityWitherSkullFriendly.class, "WitherSkullFriendly");
    createProjectile(EntityFireSkull.class, "FireSkull");
    createProjectile(EntityWitherSkullBlaze.class, "BlazeSkull");
    createProjectile(EntityWitherSkullCreeper.class, "CreeperSkull");
    createProjectile(EntityWitherSkullEnder.class, "EnderSkull");
    createProjectile(EntityLightningSkull.class, "LightningSkull");
    createProjectile(EntityEarthSkull.class, "EarthSkull");
    createProjectile(EntityWaterSkull.class, "WaterSkull");
    createProjectile(EntityIceSkull.class, "IceSkull");
    createProjectile(EntityAirSkull.class, "AirSkull");
    createProjectile(EntityPinkSkull.class, "PinkSkull");
    createProjectile(EntityMettalicSkull.class, "GoldSkull");
    createProjectile(EntityLoveSkull.class, "LoveSkull");
    createProjectile(EntityVoidSkull.class, "VoidSkull");
    createProjectile(EntityShotPresent.class, "ShotPresent");
    createProjectile(EntityWitherDragonProjectile.class, "EnderCharge");
    createProjectile(EntityMagmaSkull.class, "MagmaSkull");
    createProjectile(EntityMechaSkull.class, "MechaSkull");
    createProjectile(EntityHurricaneSkull.class, "HurricaneSkull");
    createProjectile(EntityWitherSkullZombie.class, "ZombieSkull");
    createProjectile(EntityBlockSkull.class, "BlockSkull");
    createProjectile(EntityIceHolder.class, "IceHolder");
    createProjectile(EntityBlackHole.class, "BlackHole");
    createEntity(EntityWitherCultistLeaderTransformation.class, "WitherBossTransform");
    
    createEntityWithEgg(Sculpture.class, "WitherSculpture", 0xAAAAAA, 0xAAAAAA);
    createEntityWithEgg(EntityWitherGirl.class, "WitherBossSexy", 0x141414, 0xFDECD0);
    createEntityWithEgg(EntityWitherGirlPink.class, "WitherBossSexyAndCute", 0xAE2885, 0xFFE4D6);
    createEntityWithEgg(EntityWitherGirlVoid.class, "WitherBossSexyAndScary", 0x030303, 0xFFD2BB);
    createEntityWithEgg(EntityFriendlyWither.class, "WitherBossPet", 0x141414, 0x1C1C1C);
    createEntityWithEgg(EntityPinkWither.class, "WitherBossPink", 0xFFC0CB, 0xFF69B4);
    createEntityWithEgg(EntityBabyWither.class, "WitherBossBaby", 0x141414, 0x141414);
    createEntityWithEgg(EntityFireWither.class, "WitherBossFire", 0xFF0000, 0xFF0000);
    createEntityWithEgg(EntityEarthWither.class, "WitherBossEarth", 0x8B4513, 0x8B4513);
    createEntityWithEgg(EntityWaterWither.class, "WitherBossWater", 0x0000FF, 0x0000FF);
    createEntityWithEgg(EntityAirWither.class, "WitherBossAir", 0xFFFFFF, 0xFFFFFF);
    createEntityWithEgg(EntityIceWither.class, "WitherBossIce", 0x8B4513, 0x0000FF);
    createEntityWithEgg(EntityLightningWither.class, "WitherBossLightning", 0xFFFFFF, 0xFF0000);
    createEntityWithEgg(EntityHurricaneWither.class, "WitherBossHurricane", 0x0000FF, 0xFFFFFF);
    createEntityWithEgg(EntityMagmaWither.class, "WitherBossLava", 0x8B4513, 0xFF0000);
    createEntityWithEgg(EntityBlazeWither.class, "WitherBossBlaze", 0xB5AA00, 0x8C0000);
    createEntityWithEgg(EntityCreeperWither.class, "WitherBossCreeper", 0x2AFF00, 0x225718);
    createEntityWithEgg(EntityEnderWither.class, "WitherBossEnder", 0x000000, 0x700071);
    createEntityWithEgg(EntityGhastWither.class, "WitherBossGhast", 0xf2f2f2, 0xfcfcfc);
    createEntityWithEgg(EntityZombieWither.class, "WitherBossZombie", 0x00afaf, 0x365a25);
    createEntityWithEgg(EntityWitherCultist.class, "WitherCultist", 0x1e1c1a, 0xbd8b72);
    createEntityWithEgg(EntityWitherCultistGreater.class, "WitherCultistGreater", 0x1e1c1a, 0xbd8b72);
    createEntityWithEgg(EntityWitherCultistLeader.class, "WitherCultistLeader", 0x1e1c1a, 0x959b9b);
    createEntityWithEgg(EntityLostSkull.class, "LostSkull", 0x141414, 0x141414);
    createEntityWithEgg(EntityWitherZombie.class, "WitherZombie", 0x141414, 0x478b84);
    createEntityWithEgg(EntityWitherSpider.class, "WitherSpider", 0x141414, 0x7e6c4c);
    createEntityWithEgg(EntityWitherCreeper.class, "WitherCreeper", 0x141414, 0x1d6f12);
    createEntityWithEgg(EntityWitherBlaze.class, "WitherBlaze", 0x141414, 0xc2ad00);
    createEntityWithEgg(EntityWitherGolem.class, "WitherGolem", 0x141414, 0x676767);
    createEntityWithEgg(EntityGlassWither.class, "WitherBossGlass", 0xfefefe, 0xc0f5fe);
    createEntityWithEgg(EntitySandWither.class, "WitherBossSand", 0xDED3A3, 0xBFB488);
    createEntityWithEgg(EntityGravelWither.class, "WitherBossGravel", 0x817f7f, 0x5a5854);
    createEntityWithEgg(EntitySpawnerWither.class, "WitherBossSpawner", 0x141414, 0x141414);
    createEntityWithEgg(EntityObsidianWither.class, "WitherBossObsidian", 0x101018, 0x3c3056);
    createEntityWithEgg(EntityBedrockWither.class, "WitherBossBedrock", 0x333333, 0x979797);
    createEntityWithEgg(EntityCoalWither.class, "WitherBossCoal", 0x0d0d0d, 0x232323);
    createEntityWithEgg(EntityRedstoneWither.class, "WitherBossRedstone", 0xe3260c, 0x740f06);
    createEntityWithEgg(EntityLapisWither.class, "WitherBossLapis", 0x254d9d, 0x1d2f9d);
    createEntityWithEgg(EntityIronWither.class, "WitherBossIron", 0xe6e6e6, 0xdcdcdc);
    createEntityWithEgg(EntityGoldenWither.class, "WitherBossGold", 0xf9fb49, 0xffff6f);
    createEntityWithEgg(EntityDiamondWither.class, "WitherBossDiamond", 0x8ce7e3, 0x00c5bc);
    createEntityWithEgg(EntityEmeraldWither.class, "WitherBossEmerald", 0x73eb95, 0x39cb5c);
    createEntityWithEgg(EntityRichWither.class, "WitherBossPolitician", 0x000000, 0xFFD700);
    createEntityWithEgg(EntityMechaWither.class, "WitherBossMecha", 0x5d5d5d, 0x191919);
    createEntityWithEgg(EntityEnchantedWither.class, "WitherBossEnchanted", 0x101018, 0x741e1d);
    createEntityWithEgg(EntityValentinesDayWither.class, "WitherBossValentine", 0xFF69B4, 0xFFC0CB);
    createEntityWithEgg(EntitySaintPatricksDayWither.class, "WitherBossLeprechaun", 0x009900, 0xFEFF99);
    createEntityWithEgg(EntityHalloweenWither.class, "WitherBossSpookey", 0x101010, 0xFFAE00);
    createEntityWithEgg(EntityChristmasWither.class, "WitherBossSanta", 0xFF0000, 0xFFFFFF);
    createEntityWithEgg(EntityDementedWither.class, "WitherBossDemented", 0x000000, 0x555555);
    createEntityWithEgg(EntityVoidWither.class, "WitherBossVoid", 0x000000, 0x000000);
    createEntityWithEgg(EntityWitherDragon.class, "WitherBossDragon", 0x000000, 0x000000);
    createEntityWithEgg(EntityAvatarWither.class, "WitherBossAvatar", 0x000000, 0xFFFFFF);
    
	EntityRegistry.addSpawn(EntityWitherCultist.class, 100, 2, 4, EnumCreatureType.MONSTER, BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.deepOcean, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.stoneBeach, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills);
	EntityRegistry.addSpawn(EntityLostSkull.class, 20, 2, 4, EnumCreatureType.MONSTER, BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.deepOcean, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.stoneBeach, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills);
	EntityRegistry.addSpawn(EntityWitherZombie.class, 20, 2, 4, EnumCreatureType.MONSTER, BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.deepOcean, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.stoneBeach, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills);
	EntityRegistry.addSpawn(EntityWitherSpider.class, 20, 2, 4, EnumCreatureType.MONSTER, BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.deepOcean, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.stoneBeach, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills);
	EntityRegistry.addSpawn(EntityWitherCreeper.class, 10, 2, 4, EnumCreatureType.MONSTER, BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.deepOcean, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.stoneBeach, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills);
	EntityRegistry.addSpawn(EntityWitherBlaze.class, 5, 1, 1, EnumCreatureType.MONSTER, BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.deepOcean, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.stoneBeach, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills);
	EntityRegistry.addSpawn(EntityWitherCultistGreater.class, 5, 1, 1, EnumCreatureType.MONSTER, BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.deepOcean, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.stoneBeach, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills);
	EntityRegistry.addSpawn(EntityWitherGolem.class, 1, 1, 1, EnumCreatureType.MONSTER, BiomeGenBase.beach, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.coldBeach, BiomeGenBase.coldTaiga, BiomeGenBase.coldTaigaHills, BiomeGenBase.deepOcean, BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.extremeHillsPlus, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver, BiomeGenBase.iceMountains, BiomeGenBase.icePlains, BiomeGenBase.mesa, BiomeGenBase.mesaPlateau, BiomeGenBase.mesaPlateau_F, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.roofedForest, BiomeGenBase.savanna, BiomeGenBase.savannaPlateau, BiomeGenBase.stoneBeach, BiomeGenBase.swampland, BiomeGenBase.taiga, BiomeGenBase.taigaHills);
	
	EntityRegistry.addSpawn(EntityLostSkull.class, 100, 4, 4, EnumCreatureType.MONSTER, BiomeGenBase.hell);
	EntityRegistry.addSpawn(EntityWitherCultist.class, 100, 4, 4, EnumCreatureType.MONSTER, BiomeGenBase.hell);
	EntityRegistry.addSpawn(EntityWitherZombie.class, 100, 4, 4, EnumCreatureType.MONSTER, BiomeGenBase.hell);
	EntityRegistry.addSpawn(EntityWitherSpider.class, 100, 4, 4, EnumCreatureType.MONSTER, BiomeGenBase.hell);
	EntityRegistry.addSpawn(EntityWitherCreeper.class, 50, 4, 4, EnumCreatureType.MONSTER, BiomeGenBase.hell);
	EntityRegistry.addSpawn(EntityWitherBlaze.class, 20, 2, 4, EnumCreatureType.MONSTER, BiomeGenBase.hell);
	EntityRegistry.addSpawn(EntityWitherCultistGreater.class, 20, 2, 4, EnumCreatureType.MONSTER, BiomeGenBase.hell);
	EntityRegistry.addSpawn(EntityWitherGolem.class, 5, 1, 2, EnumCreatureType.MONSTER, BiomeGenBase.hell);
	
	EntityRegistry.addSpawn(EntityPinkWither.class, 1, 1, 1, EnumCreatureType.CREATURE, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.taiga, BiomeGenBase.taigaHills, BiomeGenBase.megaTaiga, BiomeGenBase.megaTaigaHills, BiomeGenBase.roofedForest);
  }
  
  public static void createEntityWithEgg(Class entityClass, String entityName, int solidColor, int spotColor)
  {
    int randomId = EntityRegistry.findGlobalUniqueEntityId();
    EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomId);
    EntityRegistry.registerModEntity(entityClass, entityName, randomId, MoWithers.modInstance, 512, 1, false);
    createEgg(randomId, solidColor, spotColor);
  }
  
  public static void createEntity(Class entityClass, String entityName)
  {
    int randomId = EntityRegistry.findGlobalUniqueEntityId();
    EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomId);
    EntityRegistry.registerModEntity(entityClass, entityName, randomId, MoWithers.modInstance, 512, 1, false);
  }
  
  public static void createProjectile(Class entityClass, String entityName)
  {
    int randomId = EntityRegistry.findGlobalUniqueEntityId();
    EntityRegistry.registerGlobalEntityID(entityClass, entityName, randomId);
    EntityRegistry.registerModEntity(entityClass, entityName, randomId, MoWithers.modInstance, 512, 1, true);
  }
  
  private static void createEgg(int randomId, int solidColor, int spotColor)
  {
    EntityList.entityEggs.put(Integer.valueOf(randomId), new EntityList.EntityEggInfo(randomId, solidColor, spotColor));
  }
}