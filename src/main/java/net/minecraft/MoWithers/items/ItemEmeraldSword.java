package net.minecraft.MoWithers.items;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.item.ItemSword;

public class ItemEmeraldSword extends ItemSword 
{
	public ItemEmeraldSword(String unlocalizedName, ToolMaterial material) 
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(MoWithers.mowithers);
	}
}