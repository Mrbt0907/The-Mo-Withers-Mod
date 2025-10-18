package net.minecraft.MoWithers.items;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.item.ItemAxe;

public class ItemEmeraldAxe extends ItemAxe 
{
	public ItemEmeraldAxe(String unlocalizedName, ToolMaterial material) 
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(MoWithers.mowithers);
	}
}