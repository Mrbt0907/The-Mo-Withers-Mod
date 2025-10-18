package net.minecraft.MoWithers;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class SoundHandler 
{
    public static void onEntityPlay(String name,World world,Entity entityName,float volume ,float pitch)
    {
        world.playSoundAtEntity(entityName,("mowithers:" + name), (float)volume,(float) pitch);
    }
}
