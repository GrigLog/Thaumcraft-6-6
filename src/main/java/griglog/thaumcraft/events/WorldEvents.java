package griglog.thaumcraft.events;

import griglog.thaumcraft.aura.Aura;
import griglog.thaumcraft.aura.AuraHandler;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldEvents {
    @SubscribeEvent
    static void tick(TickEvent.WorldTickEvent event){
        //Thaumcraft.LOGGER.info("tick " + event.world.getDimensionKey().getLocation());
    }

    /*
    @SubscribeEvent
    static void loadChunk(ChunkEvent.Load event){
        if (!(event.getChunk() instanceof Chunk))
            return;
        if (event.getWorld().isRemote() || !(event.getChunk() instanceof Chunk))
            return;
        Chunk chunk = (Chunk) event.getChunk();

        IWorld world = chunk.getWorld();
        chunk.getCapability(Aura.Provider.AURA_CAP).ifPresent(aura -> {
            if (AuraHandler.auras.containsKey(world)){
                AuraHandler.auras.get(world).aura.putIfAbsent(chunk.getPos(), aura.ac);
            }
        });
    }

    @SubscribeEvent
    static void unloadChunk(ChunkEvent.Unload event){
        if (event.getWorld().isRemote() || !(event.getChunk() instanceof Chunk))
            return;
        Chunk chunk = (Chunk) event.getChunk();
        IWorld world = chunk.getWorld();
        chunk.getCapability(Aura.Provider.AURA_CAP).ifPresent(aura -> {
            if (AuraHandler.auras.containsKey(world)){
                aura.ac = AuraHandler.auras.get(world).aura.remove(chunk.getPos());
            }
        });
    }*/

    /*@SubscribeEvent
    static void readData(ChunkDataEvent.Load event){
        if (!AuraHandler.auras.containsKey(event.getWorld()))
            return;
        CompoundNBT tag = event.getData();
        AuraChunk ac = new AuraChunk(1, 1, 1);
        if (tag.contains(Thaumcraft.id)){
            tag = tag.getCompound(Thaumcraft.id);

        }
        AuraWorld aw = AuraHandler.auras.get(event.getWorld());
        IChunk chunk = event.getChunk();
        aw.chunks.put(chunk.getPos(), ac);
    }

    @SubscribeEvent
    static void writeData(ChunkDataEvent.Save event){
        if (!AuraHandler.auras.containsKey(event.getWorld()))
            return;
        IChunk chunk = event.getChunk();
        AuraChunk ac = AuraHandler.auras.get(event.getWorld()).chunks.get(chunk.getPos());
        if (ac == null)
            return;
        CompoundNBT tag = new CompoundNBT();

        event.getData().put(Thaumcraft.id, tag);
    }*/
}
