package net.minecraft.MoWithers.worldgen;

import net.minecraftforge.common.ChestGenHooks;

public class ChestGenHooksWither extends ChestGenHooks
{
    public static final String WITHER_STATUE_BASE       = "witherStatueBase";
    public static final String WITHER_STATUE_HEAD_1     = "witherStatueHead1";
    public static final String WITHER_STATUE_HEAD_2     = "witherStatueHead2";
    public static final String DUD_FORT          		= "dudFort";
    public static final String DUD_FORT_NETHER          = "dudFortNether";
    public static final String FORTRESS_MAIN            = "fortressMain";
    public static final String BARRACKS                 = "barracks";
    public static final String FORGE                    = "forge";
    public static final String DISPENSERS               = "dispensers";
    public static final String RATHOLE                  = "ratHole";
    public static final String SPECIALTREASURE          = "specialTreasure";
	
	public ChestGenHooksWither(String category) 
	{
		super(category);
	}

}
