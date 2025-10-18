package net.minecraft.MoWithers.items;

import net.minecraft.MoWithers.MoWithers;
import net.minecraft.item.ItemSpade;

public class ItemEmeraldShovel extends ItemSpade
{
	public ItemEmeraldShovel(String unlocalizedName, ToolMaterial material) 
	{
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(MoWithers.mowithers);
	}
}
