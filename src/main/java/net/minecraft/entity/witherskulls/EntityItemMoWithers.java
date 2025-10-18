package net.minecraft.entity.witherskulls;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityItemMoWithers extends EntityItem
{
	public boolean isImmuneToExplosions;
	public boolean isImmuneToVoid;
	public boolean isImmuneToEverything;
	
	public EntityItemMoWithers(World worldIn) 
	{
		super(worldIn);
        this.setPickupDelay(20);
	}

	public EntityItemMoWithers(World worldIn, boolean fire, boolean blowup, boolean thevoid, boolean all) 
	{
		this(worldIn);
		this.isImmuneToEverything = all;
		this.isImmuneToFire = fire;
		this.isImmuneToExplosions = blowup;
		this.isImmuneToVoid = thevoid;
	}
	
	@Override
	protected void dealFireDamage(int amount) {}
	
	public boolean isEntityInvulnerable(DamageSource p_180431_1_)
    {
        return this.isImmuneToEverything ? true : super.isEntityInvulnerable(p_180431_1_);
    }
	
	public void setDead()
    {
        if (this.getEntityItem().stackSize <= 0 || this.worldObj.isRemote)
        	super.setDead();
    }
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
        	amount = 0;
            return false;
        }
        else if (this.isImmuneToExplosions && source.isExplosion())
        {
        	amount = 0;
            return false;
        }
        else if (this.isImmuneToFire && (source.isFireDamage() || source == DamageSource.lightningBolt))
        {
        	amount = 0;
            return false;
        }
        else if (this.isImmuneToVoid && source.canHarmInCreative())
        {
        	amount = 0;
            return false;
        }
        else
        {
            return super.attackEntityFrom(source, amount);
        }
    }
	
	public void onUpdate()
    {
		super.onUpdate();
		
		if ((this.isImmuneToEverything || this.isImmuneToVoid) && this.posY < 0)
			this.motionY += 0.1F + (rand.nextFloat() * 0.1F);
		
		if (this.getEntityItem() != null)
		this.setEntityItemStack(this.getEntityItem());
			
		this.extinguish();
    }
}
