package net.minecraft.MoWithers.worldgen;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.wither.EntityEarthWither;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.DungeonHooks.DungeonMob;

public class WorldGenEarthWitherTower extends WorldGenerator
{
    private static final String[] SPAWNERTYPES = new String[] {"Skeleton", "Zombie", "Silverfish", "Spider", "Creeper"};
    private static final List CHESTCONTENT = Lists.newArrayList(new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10), new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10), new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10), new WeightedRandomChestContent(Items.string, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10), new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 4), new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 4), new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)});
    private static final Logger field_175918_a = LogManager.getLogger();
    public WorldGenEarthWitherTower()
    {
    	
    }

    public boolean generate(World worldIn, Random p_180709_2_, BlockPos p_180709_3_)
    {
        if (worldIn.isAirBlock(p_180709_3_) && worldIn.getBlockState(p_180709_3_.down()).getBlock().getMaterial().isSolid())
        {
            int i = p_180709_2_.nextInt(16) + 16;
            int j = 2;
            int k;
            int l;
            int i1;
            int j1;

            for (k = p_180709_3_.getX() - j; k <= p_180709_3_.getX() + j; ++k)
            {
                for (l = p_180709_3_.getZ() - j; l <= p_180709_3_.getZ() + j; ++l)
                {
                    i1 = k - p_180709_3_.getX();
                    j1 = l - p_180709_3_.getZ();

                    if (i1 * i1 + j1 * j1 <= j * j + 1 && worldIn.getBlockState(new BlockPos(k, p_180709_3_.getY() - 1, l)).getBlock().getMaterial().isSolid())
                    {
                        return false;
                    }
                }
            }

            for (k = p_180709_3_.getY(); k < p_180709_3_.getY() + i && k < 256; ++k)
            {
                for (l = p_180709_3_.getX() - j; l <= p_180709_3_.getX() + j; ++l)
                {
                    for (i1 = p_180709_3_.getZ() - j; i1 <= p_180709_3_.getZ() + j; ++i1)
                    {
                        j1 = l - p_180709_3_.getX();
                        int k1 = i1 - p_180709_3_.getZ();
                        
                        BlockPos pos = new BlockPos(l, k, i1);

                        if (j1 * j1 + k1 * k1 <= j * j + 1 && worldIn.getBlockState(pos).getBlock() != Blocks.chest && worldIn.getBlockState(pos).getBlock() != Blocks.mob_spawner)
                        {
                            worldIn.setBlockState(pos, Blocks.dirt.getDefaultState(), 2);
                        }
                    }
                }
            }

            EntityEarthWither entityendercrystal = new EntityEarthWither(worldIn);
            entityendercrystal.setLocationAndAngles((double)((float)p_180709_3_.getX() + 0.5F), (double)(p_180709_3_.getY() + i), (double)((float)p_180709_3_.getZ() + 0.5F), p_180709_2_.nextFloat() * 360.0F, 0.0F);
            worldIn.spawnEntityInWorld(entityendercrystal);
            worldIn.setBlockState(p_180709_3_.up(i), Blocks.bedrock.getDefaultState(), 2);
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean generateLootAndSpawner(World worldIn, Random p_180709_2_, BlockPos p_180709_3_)
    {
        boolean flag = true;
        int i = 8;
        int j = -i - 1;
        int k = i + 1;
        boolean flag1 = true;
        boolean flag2 = true;
        int l = 4;
        int i1 = -l - 1;
        int j1 = l + 1;
        int k1 = 0;
        int l1;
        int i2;
        int j2;
        BlockPos blockpos1;

        for (l1 = j; l1 <= k; ++l1)
        {
            for (i2 = -1; i2 <= 4; ++i2)
            {
                for (j2 = i1; j2 <= j1; ++j2)
                {
                    blockpos1 = p_180709_3_.add(l1, i2, j2);
                    Material material = worldIn.getBlockState(blockpos1).getBlock().getMaterial();
                    boolean flag3 = material.isSolid();

                    if (i2 == -1)
                    {
                        return false;
                    }

                    if (i2 == 4)
                    {
                        return false;
                    }

                    if ((l1 == j || l1 == k || j2 == i1 || j2 == j1) && i2 == 0 && worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.up()))
                    {
                        ++k1;
                    }
                }
            }
        }

        if (k1 >= 1 && k1 <= 5)
        {
            for (l1 = j; l1 <= k; ++l1)
            {
                for (i2 = 3; i2 >= -1; --i2)
                {
                    for (j2 = i1; j2 <= j1; ++j2)
                    {
                        blockpos1 = p_180709_3_.add(l1, i2, j2);

                        if (l1 != j && i2 != -1 && j2 != i1 && l1 != k && i2 != 4 && j2 != j1)
                        {
                            if (worldIn.getBlockState(blockpos1).getBlock() != Blocks.chest)
                            {
                                worldIn.setBlockToAir(blockpos1);
                            }
                        }
                        else if (blockpos1.getY() >= 0 && !worldIn.getBlockState(blockpos1.down()).getBlock().getMaterial().isSolid())
                        {
                            worldIn.setBlockToAir(blockpos1);
                        }
                        else if (worldIn.getBlockState(blockpos1).getBlock().getMaterial().isSolid() && worldIn.getBlockState(blockpos1).getBlock() != Blocks.chest)
                        {
                        	worldIn.setBlockState(blockpos1, Blocks.dirt.getDefaultState(), 2);
                        }
                    }
                }
            }

            l1 = 0;

            while (l1 < 2)
            {
                i2 = 0;

                while (true)
                {
                    if (i2 < 3)
                    {
                        label197:
                        {
                            j2 = p_180709_3_.getX() + p_180709_2_.nextInt(i * 2 + 1) - i;
                            int l2 = p_180709_3_.getY();
                            int i3 = p_180709_3_.getZ() + p_180709_2_.nextInt(l * 2 + 1) - l;
                            BlockPos blockpos2 = new BlockPos(j2, l2, i3);

                            if (worldIn.isAirBlock(blockpos2))
                            {
                                int k2 = 0;
                                Iterator iterator = EnumFacing.Plane.HORIZONTAL.iterator();

                                while (iterator.hasNext())
                                {
                                    EnumFacing enumfacing = (EnumFacing)iterator.next();

                                    if (worldIn.getBlockState(blockpos2.offset(enumfacing)).getBlock().getMaterial().isSolid())
                                    {
                                        ++k2;
                                    }
                                }

                                if (k2 == 1)
                                {
                                    worldIn.setBlockState(blockpos2, Blocks.chest.correctFacing(worldIn, blockpos2, Blocks.chest.getDefaultState()), 2);
                                    TileEntity tileentity1 = worldIn.getTileEntity(blockpos2);

                                    if (tileentity1 instanceof TileEntityChest)
                                    {
                                        WeightedRandomChestContent.generateChestContents(p_180709_2_, ChestGenHooks.getItems(DUNGEON_CHEST, p_180709_2_), (TileEntityChest)tileentity1, ChestGenHooks.getCount(DUNGEON_CHEST, p_180709_2_));
                                    }

                                    break label197;
                                }
                            }

                            ++i2;
                            continue;
                        }
                    }

                    ++l1;
                    break;
                }
            }

            worldIn.setBlockState(p_180709_3_, Blocks.mob_spawner.getDefaultState(), 2);
            TileEntity tileentity = worldIn.getTileEntity(p_180709_3_);

            if (tileentity instanceof TileEntityMobSpawner)
            {
                switch (p_180709_2_.nextInt(4))
                {
                    case 0:
                        ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Silverfish");
                    	break;
                    case 1:
                        ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Skeleton");
                    	break;
                    case 2:
                        ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Spider");
                    	break;
                    case 3:
                        ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Zombie");
                }
            }
            else
            {
                field_175918_a.error("Failed to fetch mob spawner entity at (" + p_180709_3_.getX() + ", " + p_180709_3_.getY() + ", " + p_180709_3_.getZ() + ")");
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}