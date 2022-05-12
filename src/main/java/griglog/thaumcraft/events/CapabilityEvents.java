package griglog.thaumcraft.events;

import griglog.thaumcraft.aura.Aura;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityEvents {
    @SubscribeEvent
    static void chunks(AttachCapabilitiesEvent<Chunk> event){
        event.addCapability(Aura.id, new Aura.Provider(event.getObject()));
    }

    /*
    @SubscribeEvent
    static void tiles(AttachCapabilitiesEvent<TileEntity> event){
        if (event.getObject() instanceof TileJar){
            event.addCapability(EssentiaHolder.id, new EssentiaHolder.Provider(event.getObject()));
        }
    }*/
}
