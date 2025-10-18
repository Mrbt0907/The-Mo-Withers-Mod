package net.minecraft.MoWithers.questline;

import net.minecraft.entity.EntityLivingBase;

public class Quest
{
  public int questId;
  public String questName;
  public EntityLivingBase questingEntity;
  
  public Quest(EntityLivingBase questingEntity, String questName, int questId)
  {
    this.questingEntity = questingEntity;
    this.questName = questName;
    this.questId = questId;
  }
  
  public void updateQuestTo(int id)
  {
	  if (this.questingEntity.worldObj.getClosestPlayerToEntity(this.questingEntity, -1D) != null)
	  {
		  this.questId = id;
		  
	  }
  }
}
