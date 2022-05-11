package griglog.thaumcraft.events;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aura.Aura;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CapabilityEvents {
    @SubscribeEvent
    static void chunks(AttachCapabilitiesEvent<Chunk> event){
        event.addCapability(new ResourceLocation(Thaumcraft.id, "aura"), new Aura.Provider(event.getObject()));
    }
}
