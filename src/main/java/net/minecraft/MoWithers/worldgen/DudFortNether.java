//Schematic to java Structure by jajo_11 | inspired by "MITHION'S .SCHEMATIC TO JAVA CONVERTINGTOOL"

package net.minecraft.MoWithers.worldgen;

import static net.minecraft.MoWithers.worldgen.ChestGenHooksWither.DUD_FORT_NETHER;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

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

public class DudFortNether extends WorldGenerator
{
    private static final List CHESTCONTENT = Lists.newArrayList(new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.golden_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.golden_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.flint_and_steel, 0, 1, 1, 5), new WeightedRandomChestContent(Items.coal, 0, 2, 4, 20), new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20), new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 20) ,new WeightedRandomChestContent(Items.gold_nugget, 0, 1, 3, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 2, 10), new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10), new WeightedRandomChestContent(Items.ghast_tear, 0, 1, 1, 10), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)});

	public boolean generate(World world, Random rand, BlockPos pos)
	{
        if ((world.isAirBlock(pos) || world.getBlockState(pos).getBlock() == Blocks.snow_layer || world.getBlockState(pos).getBlock().getMaterial() == Material.plants) && world.getBlockState(pos.down()).getBlock().getMaterial().isSolid())
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
		
		this.setBlock(world, x + 1, y + 0, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 2, y + 0, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 0, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 0, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 0, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 6, y + 0, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 0, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 0, z + 2, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 2, y + 0, z + 2, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 0, z + 2, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 0, z + 2, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 0, z + 2, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 6, y + 0, z + 2, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 0, z + 2, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 0, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 2, y + 0, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 0, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 0, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 0, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 6, y + 0, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 0, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 0, z + 4, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 2, y + 0, z + 4, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 0, z + 4, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 0, z + 4, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 0, z + 4, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 6, y + 0, z + 4, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 0, z + 4, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 0, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 2, y + 0, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 0, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 0, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 0, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 6, y + 0, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 0, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 0, z + 6, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 2, y + 0, z + 6, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 0, z + 6, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 0, z + 6, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 0, z + 6, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 6, y + 0, z + 6, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 0, z + 6, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 0, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 2, y + 0, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 0, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 0, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 0, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 6, y + 0, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 0, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 1, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 3, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 4, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 4, Blocks.chest, 3, 3);
        TileEntity tileentity1 = world.getTileEntity(new BlockPos(x + 4, y + 1, z + 4));
        if (tileentity1 instanceof TileEntityChest)
        {
            WeightedRandomChestContent.generateChestContents(rand, ChestGenHooks.getItems(DUD_FORT_NETHER, rand), (TileEntityChest)tileentity1, ChestGenHooks.getCount(DUD_FORT_NETHER, rand));
        }
		this.setBlock(world, x + 5, y + 1, z + 4, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 1, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 5, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 1, y + 1, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 3, y + 1, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 5, y + 1, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 7, y + 1, z + 7, Blocks.nether_brick, 0, 3);
		this.setBlock(world, x + 4, y + 2, z + 4, Blocks.mob_spawner, 0, 3);
        TileEntity tileentity = world.getTileEntity(new BlockPos(x + 4, y + 2, z + 4));
        if (tileentity instanceof TileEntityMobSpawner)
        {
            ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityName("Blaze");
        }
		return true;

	}
	
	private void setBlock(World world, int i, int j, int k, Block block, int l, int m) 
	{
		world.setBlockState(new BlockPos(i, j, k), block.getStateFromMeta(l), m);
	}
	
    static
    {
        ChestGenHooks.init(DUD_FORT_NETHER, CHESTCONTENT, 8, 8);
        ChestGenHooks.addItem(DUD_FORT_NETHER, new WeightedRandomChestContent(new net.minecraft.item.ItemStack(Items.enchanted_book, 1, 0), 1, 1, 1));
    }

}