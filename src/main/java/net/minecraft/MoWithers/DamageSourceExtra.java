package net.minecraft.MoWithers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

public class DamageSourceExtra
  extends DamageSource
{
  public static DamageSource love = new DamageSource("love").setDamageBypassesArmor();
  public static DamageSource spooky = new DamageSource("spooky").setDamageBypassesArmor();
  public static DamageSource santa = new DamageSource("santa").setDamageBypassesArmor();
  public static DamageSource giving = new DamageSource("giving").setDamageBypassesArmor();
  
  public DamageSourceExtra(String damageTypeIn)
  {
    super(damageTypeIn);
  }
  
  public static DamageSource causePiercingMobDamage(EntityLivingBase mob)
  {
    return new EntityDamageSource("mob", mob).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode().setDifficultyScaled();
  }
  
  public static DamageSource causeWitherGirlDamage(EntityLivingBase mob)
  {
    return new EntityDamageSource("witherGirl", mob).setDamageBypassesArmor().setDamageIsAbsolute().setDamageAllowedInCreativeMode().setDifficultyScaled();
  }
}
