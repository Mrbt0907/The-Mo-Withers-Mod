package net.endermanofdoom.mowithers.registry;

import net.endermanofdoom.mac.registry.AbstractItemRegistry;
import net.endermanofdoom.mowithers.MoWithers;
import net.endermanofdoom.mowithers.items.*;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class MItems extends AbstractItemRegistry
{
	public static final MItems INSTANCE = new MItems(MoWithers.MODID);
	public static final ToolMaterial TIER_1_WITHER_TOOLS = EnumHelper.addToolMaterial("Tier1Wither", 8, 50362, 32.0F, 200.0F, 20);
	public static final ToolMaterial TIER_2_WITHER_TOOLS = EnumHelper.addToolMaterial("Tier2Wither", 9, 604350, 32.0F, 4000.0F, 22);
	public static final ToolMaterial TIER_3_WITHER_TOOLS = EnumHelper.addToolMaterial("Tier3Wither", 99, 7567500, 120.0F, 80000.0F, 26);
	
	public static final ArmorMaterial TIER_1_WITHER_ARMOR = EnumHelper.addArmorMaterial("Tier1Wither", MoWithers.MODID + ":atrophic", 324, new int[]{4, 9, 9, 4}, 16, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, 6.0F);
	public static final ArmorMaterial TIER_2_WITHER_ARMOR = EnumHelper.addArmorMaterial("Tier2Wither", MoWithers.MODID + ":necrotic", 14468, new int[]{5, 9, 9, 4}, 20, SoundEvents.BLOCK_SHULKER_BOX_OPEN, 8.0F);
	public static final ArmorMaterial TIER_3_WITHER_ARMOR = EnumHelper.addArmorMaterial("Tier3Wither", MoWithers.MODID + ":entropic", 120000, new int[]{5, 10, 10, 5}, 22, SoundEvents.BLOCK_END_PORTAL_FRAME_FILL, 10.0F);
	public static final ArmorMaterial TIER_4_WITHER_ARMOR = EnumHelper.addArmorMaterial("Tier4Wither", MoWithers.MODID + ":reaper", Integer.MAX_VALUE, new int[]{30, 30, 30, 30}, 30, SoundEvents.BLOCK_CLOTH_PLACE, 80.0F);

	public static final Item NETHER_STAR_ROD = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item NETHER_STAR_SHARD = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item NETHER_STAR_DUST = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ELEMENTS_ROD = new ItemElementsRod();
	public static final Item ATROPHIC_SHARD = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ATROPHIC_CRYSTAL = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ATROPHIC_SWORD = new ItemWitherSword(TIER_1_WITHER_TOOLS);
	public static final Item ATROPHIC_AXE = new ItemWitherAxe(TIER_1_WITHER_TOOLS, 400F, -3F);
	public static final Item ATROPHIC_PICKAXE = new ItemWitherPickaxe(TIER_1_WITHER_TOOLS);
	public static final Item ATROPHIC_SHOVEL = new ItemWitherShovel(TIER_1_WITHER_TOOLS);
	public static final Item ATROPHIC_HOE = new ItemWitherHoe(TIER_1_WITHER_TOOLS);
	public static final Item ATROPHIC_HELMET = new ItemWitherArmor(TIER_1_WITHER_ARMOR, 1, EntityEquipmentSlot.HEAD, 20D, 2D);
	public static final Item ATROPHIC_CHESTPLATE = new ItemWitherArmor(TIER_1_WITHER_ARMOR, 1, EntityEquipmentSlot.CHEST, 40D, 2D);
	public static final Item ATROPHIC_LEGGINGS = new ItemWitherArmor(TIER_1_WITHER_ARMOR, 2, EntityEquipmentSlot.LEGS, 30D, 2D);
	public static final Item ATROPHIC_BOOTS = new ItemWitherArmor(TIER_1_WITHER_ARMOR, 1, EntityEquipmentSlot.FEET, 10D, 2D);
	public static final Item NECROTIC_STAR = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item NECROTIC_SWORD = new ItemWitherSword(TIER_2_WITHER_TOOLS);
	public static final Item NECROTIC_AXE = new ItemWitherAxe(TIER_2_WITHER_TOOLS, 8000F, -2.75F);
	public static final Item NECROTIC_PICKAXE = new ItemWitherPickaxe(TIER_2_WITHER_TOOLS);
	public static final Item NECROTIC_SHOVEL = new ItemWitherShovel(TIER_2_WITHER_TOOLS);
	public static final Item NECROTIC_HOE = new ItemWitherHoe(TIER_2_WITHER_TOOLS);
	public static final Item NECROTIC_HELMET = new ItemWitherArmor(TIER_2_WITHER_ARMOR, 1, EntityEquipmentSlot.HEAD, 250D, 8D);
	public static final Item NECROTIC_CHESTPLATE = new ItemWitherArmor(TIER_2_WITHER_ARMOR, 1, EntityEquipmentSlot.CHEST, 400D, 8D);
	public static final Item NECROTIC_LEGGINGS = new ItemWitherArmor(TIER_2_WITHER_ARMOR, 2, EntityEquipmentSlot.LEGS, 300D, 8D);
	public static final Item NECROTIC_BOOTS = new ItemWitherArmor(TIER_2_WITHER_ARMOR, 1, EntityEquipmentSlot.FEET, 150D, 8D);
	public static final Item ENTROPIC_MATTER_UNSTABLE = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ENTROPIC_MATTER_STABLE = new ItemSimpleFoiled().setCreativeTab(MoWithers.MO_TAB);
	public static final Item ENTROPIC_SWORD = new ItemWitherSword(TIER_3_WITHER_TOOLS);
	public static final Item ENTROPIC_AXE = new ItemWitherAxe(TIER_3_WITHER_TOOLS, 160000F, -2F);
	public static final Item ENTROPIC_PICKAXE = new ItemWitherPickaxe(TIER_3_WITHER_TOOLS);
	public static final Item ENTROPIC_SHOVEL = new ItemWitherShovel(TIER_3_WITHER_TOOLS);
	public static final Item ENTROPIC_HOE = new ItemWitherHoe(TIER_3_WITHER_TOOLS);
	public static final Item ENTROPIC_HELMET = new ItemWitherArmor(TIER_3_WITHER_ARMOR, 1, EntityEquipmentSlot.HEAD, 5000D, 24D);
	public static final Item ENTROPIC_CHESTPLATE = new ItemWitherArmor(TIER_3_WITHER_ARMOR, 1, EntityEquipmentSlot.CHEST, 8000D, 24D);
	public static final Item ENTROPIC_LEGGINGS = new ItemWitherArmor(TIER_3_WITHER_ARMOR, 2, EntityEquipmentSlot.LEGS, 7000D, 24D);
	public static final Item ENTROPIC_BOOTS = new ItemWitherArmor(TIER_3_WITHER_ARMOR, 1, EntityEquipmentSlot.FEET, 4000D, 24D);
	public static final Item REAPER_HOOD = new ItemWitherArmor(TIER_4_WITHER_ARMOR, 1, EntityEquipmentSlot.HEAD, 10000000D, 5000D);
	public static final Item REAPER_CLOAK = new ItemWitherArmor(TIER_4_WITHER_ARMOR, 1, EntityEquipmentSlot.CHEST, 16000000D, 8000D);
	public static final Item REAPER_TROUSERS = new ItemWitherArmor(TIER_4_WITHER_ARMOR, 2, EntityEquipmentSlot.LEGS, 14000000D, 7000D);
	public static final Item REAPER_SHOES = new ItemWitherArmor(TIER_4_WITHER_ARMOR, 1, EntityEquipmentSlot.FEET, 8000000D, 4000D);

	private MItems(String modid)
	{
		super(modid);
	}

	@Override
	public void init()
	{
		Items.NETHER_STAR.setCreativeTab(MoWithers.MO_TAB);
		TIER_1_WITHER_TOOLS.setRepairItem(new ItemStack(ATROPHIC_SHARD));
		TIER_1_WITHER_ARMOR.setRepairItem(new ItemStack(ATROPHIC_SHARD));
		TIER_2_WITHER_TOOLS.setRepairItem(new ItemStack(ATROPHIC_CRYSTAL));
		TIER_2_WITHER_ARMOR.setRepairItem(new ItemStack(ATROPHIC_CRYSTAL));
		TIER_3_WITHER_TOOLS.setRepairItem(new ItemStack(NECROTIC_STAR));
		TIER_3_WITHER_ARMOR.setRepairItem(new ItemStack(NECROTIC_STAR));
	}
	
	@Override
	public void register()
	{

		addItem("nether_star_rod", NETHER_STAR_ROD, MoWithers.MO_TAB);
		addItem("nether_star_shard", NETHER_STAR_SHARD, MoWithers.MO_TAB);
		addItem("nether_star_dust", NETHER_STAR_DUST, MoWithers.MO_TAB);
		addItem("elements_rod", ELEMENTS_ROD, MoWithers.MO_TAB);
		addItem("atrophic_shard", ATROPHIC_SHARD, MoWithers.MO_TAB);
		addItem("atrophic_crystal", ATROPHIC_CRYSTAL, MoWithers.MO_TAB);
		addItem("atrophic_sword", ATROPHIC_SWORD, MoWithers.MO_TAB);
		addItem("atrophic_axe", ATROPHIC_AXE, MoWithers.MO_TAB);
		addItem("atrophic_pickaxe", ATROPHIC_PICKAXE, MoWithers.MO_TAB);
		addItem("atrophic_shovel", ATROPHIC_SHOVEL, MoWithers.MO_TAB);
		addItem("atrophic_hoe", ATROPHIC_HOE, MoWithers.MO_TAB);
		addItem("atrophic_helmet", ATROPHIC_HELMET, MoWithers.MO_TAB);
		addItem("atrophic_chestplate", ATROPHIC_CHESTPLATE, MoWithers.MO_TAB);
		addItem("atrophic_leggings", ATROPHIC_LEGGINGS, MoWithers.MO_TAB);
		addItem("atrophic_boots", ATROPHIC_BOOTS, MoWithers.MO_TAB);
		addItem("necrotic_star", NECROTIC_STAR, MoWithers.MO_TAB);
		addItem("necrotic_sword", NECROTIC_SWORD, MoWithers.MO_TAB);
		addItem("necrotic_axe", NECROTIC_AXE, MoWithers.MO_TAB);
		addItem("necrotic_pickaxe", NECROTIC_PICKAXE, MoWithers.MO_TAB);
		addItem("necrotic_shovel", NECROTIC_SHOVEL, MoWithers.MO_TAB);
		addItem("necrotic_hoe", NECROTIC_HOE, MoWithers.MO_TAB);
		addItem("necrotic_helmet", NECROTIC_HELMET, MoWithers.MO_TAB);
		addItem("necrotic_chestplate", NECROTIC_CHESTPLATE, MoWithers.MO_TAB);
		addItem("necrotic_leggings", NECROTIC_LEGGINGS, MoWithers.MO_TAB);
		addItem("necrotic_boots", NECROTIC_BOOTS, MoWithers.MO_TAB);
		addItem("entropic_matter_unstable", ENTROPIC_MATTER_UNSTABLE, MoWithers.MO_TAB);
		addItem("entropic_matter", ENTROPIC_MATTER_STABLE, MoWithers.MO_TAB);
		addItem("entropic_sword", ENTROPIC_SWORD, MoWithers.MO_TAB);
		addItem("entropic_axe", ENTROPIC_AXE, MoWithers.MO_TAB);
		addItem("entropic_pickaxe", ENTROPIC_PICKAXE, MoWithers.MO_TAB);
		addItem("entropic_shovel", ENTROPIC_SHOVEL, MoWithers.MO_TAB);
		addItem("entropic_hoe", ENTROPIC_HOE, MoWithers.MO_TAB);
		addItem("entropic_helmet", ENTROPIC_HELMET, MoWithers.MO_TAB);
		addItem("entropic_chestplate", ENTROPIC_CHESTPLATE, MoWithers.MO_TAB);
		addItem("entropic_leggings", ENTROPIC_LEGGINGS, MoWithers.MO_TAB);
		addItem("entropic_boots", ENTROPIC_BOOTS, MoWithers.MO_TAB);
		addItem("reaper_hood", REAPER_HOOD, MoWithers.MO_TAB);
		addItem("reaper_cloak", REAPER_CLOAK, MoWithers.MO_TAB);
		addItem("reaper_trousers", REAPER_TROUSERS, MoWithers.MO_TAB);
		addItem("reaper_shoes", REAPER_SHOES, MoWithers.MO_TAB);
	}
}
