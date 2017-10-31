package me.refraction.alwayssharp;

import me.refraction.alwayssharp.particles.Particles;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = AlwaysSharp.MODID, version = AlwaysSharp.VERSION, name = AlwaysSharp.NAME)
public class AlwaysSharp
{
    public static final String MODID = "alwayssharp";
    public static final String VERSION = "1.0";
    public static final String NAME = "AlwaysSharp";
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new Particles());
    }
}
