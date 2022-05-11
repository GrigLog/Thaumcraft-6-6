package griglog.thaumcraft.events;

import griglog.thaumcraft.aura.AuraHandler;
import griglog.thaumcraft.aura.AuraWorld;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber
public class ServerEvents {
    @SubscribeEvent
    static void startup(FMLServerStartingEvent event){
        for (ServerWorld sw : event.getServer().getWorlds()){
            AuraHandler.auras.put(sw, new AuraWorld(sw));
        }
    }

    @SubscribeEvent
    static void tick(TickEvent.ServerTickEvent event){
        if (event.phase == TickEvent.Phase.END){
            AuraHandler.tick();
        }
    }

    @SubscribeEvent
    static void stop(FMLServerStoppingEvent event){
        AuraHandler.auras.clear();
    }
}
