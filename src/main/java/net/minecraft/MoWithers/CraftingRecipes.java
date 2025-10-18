package net.minecraft.MoWithers;

import com.google.common.base.Predicate;

import net.minecraft.block.state.BlockWorldState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class CraftingRecipes 
{
	public static void initCrafting() 
	{		
	    GameRegistry.addShapedRecipe(new ItemStack(Items.nether_star, 1, 0), new Object[] { " S ", "SSS", " S ", Character.valueOf('S'), MoWitherItems.netherStarShard });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.sculpture, 1, 0), new Object[] { "WWW", "CCC", " C ", Character.valueOf('W'), new ItemStack(Items.skull, 1, 1), Character.valueOf('C'), Blocks.clay });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.wither_essence, 1, 0), new Object[] { "BWB", "WNW", "BWB", Character.valueOf('W'), new ItemStack(Items.skull, 1, 1), Character.valueOf('B'), Items.bone, Character.valueOf('N'), Items.nether_star });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.air_essence, 1, 0), new Object[] { "PFP", "FEF", "PFP", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), Items.paper, Character.valueOf('F'), Items.feather });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.earth_essence, 1, 0), new Object[] { "PFP", "FEF", "PFP", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), Blocks.dirt, Character.valueOf('F'), Items.poisonous_potato });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.fire_essence, 1, 0), new Object[] { "PFP", "FEF", "PFP", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), Items.blaze_powder, Character.valueOf('F'), Items.fire_charge });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.water_essence, 1, 0), new Object[] { "PFP", "FEF", "PFP", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), Items.water_bucket, Character.valueOf('F'), new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.getMetadata()) });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.lava_essence, 1, 0), new Object[] { "PBP", "FEF", "PFP", Character.valueOf('B'), MoWitherItems.fire_essence, Character.valueOf('E'), MoWitherItems.earth_essence, Character.valueOf('P'), Items.lava_bucket, Character.valueOf('F'), Blocks.netherrack });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.lightning_essence, 1, 0), new Object[] { "PBP", "FEF", "PFP", Character.valueOf('B'), MoWitherItems.fire_essence, Character.valueOf('E'), MoWitherItems.air_essence, Character.valueOf('P'), Items.redstone, Character.valueOf('F'), Items.blaze_rod });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.hurricane_essence, 1, 0), new Object[] { "PBP", "FEF", "PFP", Character.valueOf('B'), MoWitherItems.air_essence, Character.valueOf('E'), MoWitherItems.water_essence, Character.valueOf('P'), Blocks.waterlily, Character.valueOf('F'), Items.paper });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.ice_essence, 1, 0), new Object[] { "PBP", "FEF", "PFP", Character.valueOf('B'), MoWitherItems.water_essence, Character.valueOf('E'), MoWitherItems.earth_essence, Character.valueOf('P'), Blocks.ice, Character.valueOf('F'), Items.snowball });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.friendly_essence, 1, 0), new Object[] { "PFP", "FEF", "PFP", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), Items.apple, Character.valueOf('F'), Items.saddle });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.growth_essence, 1, 0), new Object[] { "PFP", "FEF", "PFP", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), Items.wheat_seeds, Character.valueOf('F'), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()) });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.demented_essence, 1, 0), new Object[] { "PEP", "EEE", "PEP", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), new ItemStack(Items.skull, 1, 1) });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.negative_essence, 1, 0), new Object[] { "PDP", "FEF", "PFP", Character.valueOf('D'), Items.record_11, Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), Items.coal, Character.valueOf('F'), Blocks.diamond_block });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.wither_dragon_essence, 1, 0), new Object[] { "PDP", "FEF", "PFP", Character.valueOf('D'), Blocks.dragon_egg, Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), Items.ender_eye, Character.valueOf('F'), Blocks.beacon });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.wither_girl_essence, 1, 0), new Object[] { "PFP", "FEF", "FPF", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('P'), Items.bed, Character.valueOf('F'), Items.golden_apple });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.pink_wither_girl_essence, 1, 0), new Object[] { "PFP", "FEF", "FPF", Character.valueOf('E'), MoWitherItems.wither_girl_essence, Character.valueOf('P'), MoWitherItems.growth_essence, Character.valueOf('F'), MoWitherItems.friendly_essence });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.void_wither_girl_essence, 1, 0), new Object[] { "FFF", "FEF", "FPF", Character.valueOf('E'), MoWitherItems.wither_girl_essence, Character.valueOf('P'), MoWitherItems.negative_essence, Character.valueOf('F'), MoWitherItems.fire_essence });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.sand_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.sand });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.gravel_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.gravel });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.glass_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.glass });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.obsidian_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.obsidian });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.bedrock_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.bedrock });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.coal_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.coal_block });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.redstone_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.redstone_block });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.lapis_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.lapis_block });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.iron_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.iron_block });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.gold_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.gold_block });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.diamond_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.diamond_block });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emerald_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.emerald_block });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.halloween_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.lit_pumpkin });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.valentine_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.red_flower });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.saint_patrick_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.tallgrass });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.christmas_essence, 1, 0), new Object[] { "SSS", "SES", "SSS", Character.valueOf('E'), MoWitherItems.wither_essence, Character.valueOf('S'), Blocks.chest });
	    
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emeraldHelmet, 1, 0), new Object[] { "XXX", "X X", Character.valueOf('X'), Items.emerald });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emeraldChestplate, 1, 0), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), Items.emerald });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emeraldLeggings, 1, 0), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), Items.emerald });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emeraldBoots, 1, 0), new Object[] { "X X", "X X", Character.valueOf('X'), Items.emerald });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.witherHelmet, 1, 0), new Object[] { "XXX", "X X", Character.valueOf('X'), Items.nether_star });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.witherChestplate, 1, 0), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), Items.nether_star });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.witherLeggings, 1, 0), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), Items.nether_star });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.witherBoots, 1, 0), new Object[] { "X X", "X X", Character.valueOf('X'), Items.nether_star });
	    
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emeraldAxe, 1, 0), new Object[] { "XX", "X#", " #", Character.valueOf('X'), Items.emerald, Character.valueOf('#'), Items.stick });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emeraldPickaxe, 1, 0), new Object[] { "XXX", " # ", " # ", Character.valueOf('X'), Items.emerald, Character.valueOf('#'), Items.stick });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emeraldHoe, 1, 0), new Object[] { "XX", " #", " #", Character.valueOf('X'), Items.emerald, Character.valueOf('#'), Items.stick });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emeraldSpade, 1, 0), new Object[] { "X", "#", "#", Character.valueOf('X'), Items.emerald, Character.valueOf('#'), Items.stick });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.emeraldSword, 1, 0), new Object[] { "X", "X", "#", Character.valueOf('X'), Items.emerald, Character.valueOf('#'), Items.stick });
	    
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.witherAxe, 1, 0), new Object[] { "XX", "X#", " #", Character.valueOf('X'), Blocks.beacon, Character.valueOf('#'), Items.nether_star });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.witherPickaxe, 1, 0), new Object[] { "XXX", " # ", " # ", Character.valueOf('X'), Blocks.beacon, Character.valueOf('#'), Items.nether_star });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.witherHoe, 1, 0), new Object[] { "XX", " #", " #", Character.valueOf('X'), Blocks.beacon, Character.valueOf('#'), Items.nether_star });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.witherSpade, 1, 0), new Object[] { "X", "#", "#", Character.valueOf('X'), Blocks.beacon, Character.valueOf('#'), Items.nether_star });
	    GameRegistry.addShapedRecipe(new ItemStack(MoWitherItems.witherSword, 1, 0), new Object[] { "X", "X", "#", Character.valueOf('X'), Blocks.beacon, Character.valueOf('#'), Items.nether_star });
	}
}