package net.minecraft.MoWithers;

import java.util.Random;

import net.minecraft.MoWithers.worldgen.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraftforge.fml.common.IWorldGenerator;

public class MoWithersStructures implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) 
	{
		int dimensionId = world.provider.getDimensionId();
		
		if (dimensionId == -1) // the Nether
		{
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new DudFortNether(), 256, 100);
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new MainNetherTower(), 256, 25);
		}
		else if (dimensionId != 1 && dimensionId != -1) // Every Dimension that isn't the Nether or the End (Overworld if no other mods are installed)
		{
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new Defaultwithereast(), 256, 1);
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new Defaultwithernorth(), 256, 1);
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new Defaultwithersouth(), 256, 1);
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new Defaultwitherwest(), 256, 1);
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new DudFort(), 256, 100);
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new WorldGenGreaterDungeon(), 256, 5);
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new MainTower(), 256, 25);
			generateStructure(chunkProvider, random, world, chunkX * 16, chunkZ * 16, new Fortress(), 256, 1);
		}
	}

	private void generateStructure(IChunkProvider chunkGenerator, Random rand, World world, int chunkX, int chunkZ, WorldGenerator worldgen, int maxY, int chance)
	{
		for (int k = 0; k < chance; ++k)
		{
	    	int x = chunkX * 16 + rand.nextInt(16);
	    	int z = chunkZ * 16 + rand.nextInt(16);
	    	int randPosY = rand.nextInt(maxY);
	    	worldgen.generate(world, rand, new BlockPos(x, randPosY, z));
		}
	}
}