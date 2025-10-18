//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package net.minecraft.MoWithers.worldgen;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import static net.minecraftforge.common.ChestGenHooks.DUNGEON_CHEST;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ChestGenHooks;

public class MainTower extends WorldGenerator
{
    private static final List CHESTCONTENT = Lists.newArrayList(new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15), new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2), new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10), new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10), new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10), new WeightedRandomChestContent(Items.string, 0, 1, 4, 10), new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1), new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10), new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 4), new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 4), new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)});

	public boolean generate(World world, Random rand, BlockPos pos)
	{
        if ((world.isAirBlock(pos) || world.getBlockState(pos).getBlock() == Blocks.snow_layer || world.getBlockState(pos).getBlock().getMaterial() == Material.plants) && world.canSeeSky(pos.up()) && world.getBlockState(pos.down()).getBlock().getMaterial().isSolid())
        {
    	    generate_r0(world, rand, pos.getX(), pos.getY(), pos.getZ());

    	    return true;
        }
        else
        {
            return false;
        }
	}

	public boolean generate_r0(World world, Random rand, int x, int y, int z)
	{
		x -= 4;
		z -= 4;

		this.setBlock(world, x + 2, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 9, Blocks.stone_stairs, 3, 3);
		this.setBlock(world, x + 2, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 4, Blocks.cobblestone, 0, 3);
		this.generateChest(world, rand, x + 1, y + 1, z + 4, Blocks.chest, 5, 3);
		this.setBlock(world, x + 2, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 4, Blocks.mob_spawner, 0, 3);
        TileEntity tileentity = world.getTileEntity(new BlockPos(x + 4, y + 1, z + 4));
        if (tileentity instanceof TileEntityMobSpawner)
        {
            ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName(setRandomMob(rand));
        }
		this.setBlock(world, x + 5, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 4, Blocks.air, 0, 3);
		this.generateChest(world, rand, x + 7, y + 1, z + 4, Blocks.chest, 4, 3);
		this.setBlock(world, x + 8, y + 1, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 8, Blocks.cobblestone, 0, 3);

	    generate_r03(world, rand, x, y + 6, z);

		generate_r02_last(world, rand, x, y, z);
		
		int i1 = rand.nextInt(2);

		if(i1 == 0)
		{
		    generate_r03(world, rand, x, y + 11, z);
		    
			int i2 = rand.nextInt(2);

			if(i2 == 0)
			{
			    generate_r03(world, rand, x, y + 16, z);
			    
				int i3 = rand.nextInt(2);

				if(i3 == 0)
				{
				    generate_r03(world, rand, x, y + 21, z);
				    
					int i4 = rand.nextInt(2);

					if(i4 == 0)
					{
					    generate_r03(world, rand, x, y + 26, z);
					    
						int i5 = rand.nextInt(2);

						if(i5 == 0)
						{
						    generate_r03(world, rand, x, y + 31, z);
						    
							int i6 = rand.nextInt(2);

							if(i6 == 0)
							{
							    generate_r03(world, rand, x, y + 36, z);
							    
								int i7 = rand.nextInt(2);

								if(i7 == 0)
								{
								    generate_r03(world, rand, x, y + 41, z);
								    
									int i8 = rand.nextInt(2);

									if(i8 == 0)
									{
									    generate_r03(world, rand, x, y + 46, z);
									    
										int iboss = rand.nextInt(2);

										if(iboss == 0)
										{
										    generate_boss_room(world, rand, x, y + 51, z);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		else
		{
			int i2 = rand.nextInt(32);

			if(i2 == 0)
			{
			    generate_r03(world, rand, x, y + 11, z);
			    generate_r03(world, rand, x, y + 16, z);
			    generate_r03(world, rand, x, y + 21, z);
			    generate_r03(world, rand, x, y + 26, z);
			    generate_r03(world, rand, x, y + 31, z);
			    generate_r03(world, rand, x, y + 36, z);
			    generate_r03(world, rand, x, y + 41, z);
			    generate_r03(world, rand, x, y + 46, z);
			    generate_boss_room(world, rand, x, y + 51, z);
			}
		}
		
		return true;

	}
	public boolean generate_r02_last(World world, Random rand, int x, int y, int z)
	{

		this.setBlock(world, x + 4, y + 1, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 2, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 3, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 4, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 5, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 6, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 7, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 8, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 9, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 10, z + 1, Blocks.ladder, 3, 3);
		return true;

	}
	
	public boolean generate_r03(World world, Random rand, int x, int y, int z)
	{
		this.setBlock(world, x + 0, y, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 4, Blocks.cobblestone, 0, 3);
		this.generateChest(world, rand, x + 1, y, z + 4, Blocks.chest, 5, 3);
		this.setBlock(world, x + 2, y, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 4, Blocks.mob_spawner, 0, 3);
        TileEntity tileentity2 = world.getTileEntity(new BlockPos(x + 4, y, z + 4));
        if (tileentity2 instanceof TileEntityMobSpawner)
        {
            ((TileEntityMobSpawner)tileentity2).getSpawnerBaseLogic().setEntityName(setRandomMob(rand));
        }
		this.setBlock(world, x + 5, y, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 4, Blocks.air, 0, 3);
		this.generateChest(world, rand, x + 7, y, z + 4, Blocks.chest, 4, 3);
		this.setBlock(world, x + 8, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 7, Blocks.air, 0, 3);
		this.generateChest(world, rand, x + 4, y, z + 7, Blocks.chest, 2, 3);
		this.setBlock(world, x + 5, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 8, Blocks.glass, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 8, Blocks.glass, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 8, Blocks.air, 0, 3);

		generate_r03_last(world, rand, x, y, z);
		return true;
	}
	
	public boolean generate_r03_last(World world, Random rand, int x, int y, int z)
	{

		this.setBlock(world, x + 4, y, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 1, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 2, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 3, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 4, z + 1, Blocks.ladder, 3, 3);
		return true;

	}
	
	public boolean generate_boss_room(World world, Random rand, int x, int y, int z)
	{
		this.setBlock(world, x + 0, y, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 4, Blocks.cobblestone, 0, 3);
		this.generateChest(world, rand, x + 1, y, z + 4, Blocks.chest, 5, 3);
		this.setBlock(world, x + 2, y, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 4, Blocks.mob_spawner, 0, 3);
        TileEntity tileentity2 = world.getTileEntity(new BlockPos(x + 4, y, z + 4));
        if (tileentity2 instanceof TileEntityMobSpawner)
        {
            ((TileEntityMobSpawner)tileentity2).getSpawnerBaseLogic().setEntityName(setRandomBossMob(rand));
        }
		this.setBlock(world, x + 5, y, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 4, Blocks.air, 0, 3);
		this.generateChest(world, rand, x + 7, y, z + 4, Blocks.chest, 4, 3);
		this.setBlock(world, x + 8, y, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y, z + 7, Blocks.air, 0, 3);
		this.generateChest(world, rand, x + 4, y, z + 7, Blocks.chest, 2, 3);
		this.setBlock(world, x + 5, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 1, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 8, Blocks.glass, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 1, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 1, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 4, Blocks.glass, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 2, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 2, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 8, Blocks.glass, 0, 3);
		this.setBlock(world, x + 5, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 2, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 2, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 2, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 3, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 3, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 4, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 4, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 4, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 4, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 4, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 2, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 3, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 4, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 5, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 6, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 5, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 5, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 5, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 5, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 5, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 5, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 5, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 5, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 5, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 6, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 6, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 6, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 6, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 6, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 6, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 6, z + 0, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 6, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 6, z + 0, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 6, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 6, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 6, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 6, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 6, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 6, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 6, z + 1, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 6, z + 1, Blocks.air, 0, 3);
		this.setBlock(world, x + 0, y + 6, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 6, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 6, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 6, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 6, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 6, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 6, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 6, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 6, z + 2, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 6, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 6, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 6, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 6, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 6, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 6, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 6, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 6, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 6, z + 3, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 6, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 6, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 6, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 6, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 6, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 6, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 6, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 6, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 6, z + 4, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 6, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 6, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 6, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 6, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 6, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 6, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 6, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 6, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 6, z + 5, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 6, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 1, y + 6, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 6, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 6, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 6, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 6, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 6, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 6, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 6, z + 6, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 0, y + 6, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 6, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 2, y + 6, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 6, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 6, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 6, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 6, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 6, z + 7, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 8, y + 6, z + 7, Blocks.air, 0, 3);
		this.setBlock(world, x + 1, y + 6, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 2, y + 6, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 3, y + 6, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 4, y + 6, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 5, y + 6, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 6, y + 6, z + 8, Blocks.cobblestone, 0, 3);
		this.setBlock(world, x + 7, y + 6, z + 8, Blocks.air, 0, 3);
		this.setBlock(world, x + 8, y + 6, z + 8, Blocks.air, 0, 3);

		generate_r04_last(world, rand, x, y, z);
		return true;
	}
	
	public boolean generate_r04_last(World world, Random rand, int x, int y, int z)
	{

		this.setBlock(world, x + 4, y, z + 1, Blocks.ladder, 3, 3);
		this.setBlock(world, x + 4, y + 1, z + 1, Blocks.ladder, 3, 3);
		return true;

	}
	
	private void generateChest(World world, Random rand, int i, int j, int k, Block block, int l, int m) 
	{
		world.setBlockState(new BlockPos(i, j, k), block.getStateFromMeta(l), m);
        TileEntity chest = world.getTileEntity(new BlockPos(i, j, k));
        if (chest instanceof TileEntityChest)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUNGEON_CHEST, rand), (TileEntityChest)chest, ChestGenHooks.getCount(DUNGEON_CHEST, rand));
        }
	}
	
	private void setBlock(World world, int i, int j, int k, Block block, int l, int m) 
	{
		world.setBlockState(new BlockPos(i, j, k), block.getStateFromMeta(l), m);
	}
	
	private String setRandomMob(Random rand) 
	{
        int i = rand.nextInt(17);

        if (i == 0)
        {
            return "WitherCultistGreater";
        }
        else if (i == 1 || i == 2)
        {
            return "WitherCultist";
        }
        else if (i == 3 || i == 4)
        {
            return "CaveSpider";
        }
        else if (i == 5 || i == 6)
        {
            return "Creeper";
        }
        else if (i == 7 || i == 8 || i == 9)
        {
            return "Skeleton";
        }
        else if (i == 10 || i == 11 || i == 12)
        {
            return "Spider";
        }
        else
        {
            return "Zombie";
        }
	}
	
	private String setRandomBossMob(Random rand) 
	{
        int i = rand.nextInt(6);

        if (i == 0)
        {
            return "WitherBossEarth";
        }
        else if (i == 1)
        {
            return "WitherBossAir";
        }
        else if (i == 2)
        {
            return "WitherBossFire";
        }
        else if (i == 3)
        {
            return "WitherBossWater";
        }
        else
        {
            return "WitherBoss";
        }
	}
	
    static
    {
        ChestGenHooks.init(DUNGEON_CHEST, CHESTCONTENT, 8, 16);
        ChestGenHooks.addItem(DUNGEON_CHEST, new WeightedRandomChestContent(new net.minecraft.item.ItemStack(Items.enchanted_book, 1, 0), 1, 1, 1));
    }

}