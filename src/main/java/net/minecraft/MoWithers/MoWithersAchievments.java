package net.minecraft.MoWithers;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class MoWithersAchievments 
{
	public static Achievement achievementSculpture = new Achievement("achievement.michael", "michael", 0, 0, MoWitherItems.sculpture, (Achievement)null).setIndependent();
	public static Achievement achievementSpawnMoWither = new Achievement("achievement.buildWither", "buildWither", 2, 1, new ItemStack(Items.skull, 1, 1), achievementSculpture);
	public static Achievement achievementInvokeGodPower = new Achievement("achievement.invokeGodPower", "invokeGodPower", 3, -1, MoWitherItems.lightningstormpower, (Achievement)null).setSpecial().setIndependent();
	public static Achievement achievementIrony = new Achievement("achievement.irony", "irony", 1, -2, MoWitherItems.witherSword, (Achievement)null).setIndependent();
	public static Achievement achievementKillAirWither = new Achievement("achievement.killAirWither", "killAirWither", 0, 2, MoWitherItems.air_essence, achievementSpawnMoWither);
	public static Achievement achievementKillFireWither = new Achievement("achievement.killFireWither", "killFireWither", 1, 3, MoWitherItems.fire_essence, achievementSpawnMoWither);
	public static Achievement achievementKillEarthWither = new Achievement("achievement.killEarthWither", "killEarthWither", 3, 3, MoWitherItems.earth_essence, achievementSpawnMoWither);
	public static Achievement achievementKillWaterWither = new Achievement("achievement.killWaterWither", "killWaterWither", 4, 2, MoWitherItems.water_essence, achievementSpawnMoWither);
	public static Achievement achievementKillMagmaWither = new Achievement("achievement.killMagmaWither", "killMagmaWither", -1, 4, MoWitherItems.lava_essence, achievementSpawnMoWither);
	public static Achievement achievementKillMagmaWitherWaterDeath = new Achievement("achievement.killMagmaWitherWater", "killMagmaWitherWater", -3, 5, Items.water_bucket, achievementKillMagmaWither);
	public static Achievement achievementKillLightningWither = new Achievement("achievement.killLightningWither", "killLightningWither", 1, 5, MoWitherItems.lightning_essence, achievementSpawnMoWither);
	public static Achievement achievementKillHurricaneWither = new Achievement("achievement.killHurricaneWither", "killHurricaneWither", 3, 5, MoWitherItems.hurricane_essence, achievementSpawnMoWither);
	public static Achievement achievementKillIceWither = new Achievement("achievement.killIceWither", "killIceWither", 5, 4, MoWitherItems.ice_essence, achievementSpawnMoWither);
	public static Achievement achievementSpawnWitherGirl = new Achievement("achievement.witherGirl", "witherGirl", -3, -1, MoWitherItems.wither_girl_essence, achievementSpawnMoWither).setSpecial();
	public static Achievement achievementSpawnPinkWitherGirl = new Achievement("achievement.witherGirlPink", "witherGirlPink", -5, -2, MoWitherItems.pink_wither_girl_essence, achievementSpawnWitherGirl).setSpecial();
	public static Achievement achievementSpawnVoidWitherGirl = new Achievement("achievement.witherGirlVoid", "witherGirlVoid", -5, 0, MoWitherItems.void_wither_girl_essence, achievementSpawnWitherGirl).setSpecial();
	public static Achievement achievementWitherLove = new Achievement("achievement.witherGirlLove", "witherGirlLove", -3, -4, MoWitherItems.witherlove, achievementSpawnWitherGirl).setSpecial();
	public static Achievement achievementThreesome = new Achievement("achievement.witherGirlLove2", "witherGirlLove2", -5, -5, MoWitherItems.threesome, achievementWitherLove).setSpecial();
	public static Achievement achievementWitherFamily = new Achievement("achievement.witherFamily", "witherFamily", -1, -5, MoWitherItems.witherfamily, achievementWitherLove).setSpecial();
	public static Achievement achievementAllstarfather = new Achievement("achievement.witherAllStarFather", "witherAllStarFather", 1, -3, MoWitherItems.allstarfather, achievementWitherFamily).setSpecial();
	
	public static Achievement samanthaQuestlineStart = new Achievement("achievement.samanthaqueststart", "samanthaqueststart", 0, 0, Items.writable_book, (Achievement)null).setIndependent();
	public static Achievement samanthaQuestlinePart1 = new Achievement("achievement.samanthaquest1", "samanthaquest1", 1, 0, Items.writable_book, samanthaQuestlineStart);
	public static Achievement samanthaQuestlinePart2 = new Achievement("achievement.samanthaquest2", "samanthaquest2", 2, 0, Items.book, samanthaQuestlinePart1);
	public static Achievement samanthaQuestlinePart3 = new Achievement("achievement.samanthaquest3", "samanthaquest3", 3, 0, Items.book, samanthaQuestlinePart2);
	public static Achievement samanthaQuestlinePart4 = new Achievement("achievement.samanthaquest4", "samanthaquest4", 4, 0, Items.book, samanthaQuestlinePart3);
	public static Achievement samanthaQuestlinePart5 = new Achievement("achievement.samanthaquest5", "samanthaquest5", 5, 0, Items.enchanted_book, samanthaQuestlinePart4).setSpecial();
	public static Achievement samanthaQuestlinePart6 = new Achievement("achievement.samanthaquest6", "samanthaquest6", 6, 0, Items.enchanted_book, samanthaQuestlinePart5).setSpecial();
	public static Achievement samanthaQuestlinePart7 = new Achievement("achievement.samanthaquest7", "samanthaquest7", 7, 0, Items.enchanted_book, samanthaQuestlinePart6).setSpecial();
	public static Achievement samanthaQuestlineFinale = new Achievement("achievement.samanthaquestfinale", "samanthaquestfinale", 8, 0, Items.written_book, samanthaQuestlinePart7).setSpecial();
	public static Achievement achievementSamanthaLove = new Achievement("achievement.samanthaLove", "samanthaLove", 9, 0, MoWitherItems.witherlove, samanthaQuestlineFinale).setSpecial();
	public static Achievement achievementSamanthaFamily = new Achievement("achievement.samanthaFamily", "samanthaFamily", 10, 0, MoWitherItems.witherfamily, achievementSamanthaLove).setSpecial();
	
	public static void addAchievments()
	{
		registerAchievments();
		AchievementPage.registerAchievementPage(new AchievementPage("Mo' Withers", addAchievmentsToList()));
		AchievementPage.registerAchievementPage(new AchievementPage("The Samantha Quest Line", addSamanthaQuestLine()));
	}
	
	public static Achievement[] addSamanthaQuestLine()
	{
		return new Achievement[]
				{
						samanthaQuestlineStart,
						samanthaQuestlinePart1,
						samanthaQuestlinePart2,
						samanthaQuestlinePart3,
						samanthaQuestlinePart4,
						samanthaQuestlinePart5,
						samanthaQuestlinePart6,
						samanthaQuestlinePart7,
						samanthaQuestlineFinale,
						achievementSamanthaLove,
						achievementSamanthaFamily
				};
	}
	
	public static Achievement[] addAchievmentsToList()
	{
		return new Achievement[]
				{
						achievementSculpture, 
						achievementInvokeGodPower, 
						achievementIrony, 
						achievementSpawnMoWither, 
						achievementKillAirWither, 
						achievementKillFireWither, 
						achievementKillEarthWither, 
						achievementKillWaterWither, 
						achievementKillMagmaWither, 
						achievementKillMagmaWitherWaterDeath, 
						achievementKillLightningWither, 
						achievementKillHurricaneWither, 
						achievementKillIceWither, 
						achievementSpawnWitherGirl, 
						achievementSpawnPinkWitherGirl, 
						achievementSpawnVoidWitherGirl,
						achievementWitherLove, 
						achievementThreesome, 
						achievementWitherFamily, 
						achievementAllstarfather
				};
	}
	
	public static void registerAchievments()
	{
		achievementSculpture.registerStat();
		achievementSpawnMoWither.registerStat();
		achievementInvokeGodPower.registerStat();
		achievementIrony.registerStat();
		achievementSpawnWitherGirl.registerStat();
		achievementSpawnPinkWitherGirl.registerStat();
		achievementSpawnVoidWitherGirl.registerStat();
		achievementKillAirWither.registerStat();
		achievementKillFireWither.registerStat();
		achievementKillEarthWither.registerStat();
		achievementKillWaterWither.registerStat();
		achievementKillMagmaWither.registerStat();
		achievementKillMagmaWitherWaterDeath.registerStat();
		achievementKillLightningWither.registerStat();
		achievementKillHurricaneWither.registerStat();
		achievementKillIceWither.registerStat();
		achievementWitherLove.registerStat();
		achievementThreesome.registerStat();
		achievementWitherFamily.registerStat();
		achievementAllstarfather.registerStat();
		
		samanthaQuestlineStart.registerStat();
		samanthaQuestlinePart1.registerStat();
		samanthaQuestlinePart2.registerStat();
		samanthaQuestlinePart3.registerStat();
		samanthaQuestlinePart4.registerStat();
		samanthaQuestlinePart5.registerStat();
		samanthaQuestlinePart6.registerStat();
		samanthaQuestlinePart7.registerStat();
		samanthaQuestlineFinale.registerStat();
		achievementSamanthaLove.registerStat();
		achievementSamanthaFamily.registerStat();
	}
}
