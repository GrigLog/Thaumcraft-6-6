package griglog.thaumcraft.aura;

import griglog.thaumcraft.Thaumcraft;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ChunkHolder;
import net.minecraft.world.server.ServerWorld;

import java.util.*;

public class AuraWorld {
    ServerWorld world;
    List<BlockPos> riftTrigger = new ArrayList<>();
    float phaseVis, phaseMax, phaseFlux;
    public AuraWorld(ServerWorld world){
        this.world = world;
    }

    public void process(){
        int loaded = 0, processed = 0;
        phaseVis = AuraHandler.phaseTable[world.getMoonPhase()];
        phaseMax = 1.0f + AuraHandler.maxTable[world.getMoonPhase()];
        phaseFlux = 0.25f - phaseVis;
        for (ChunkHolder ch : world.getChunkProvider().chunkManager.getLoadedChunksIterable()){
            Chunk chunk = ch.getChunkIfComplete();
            if (chunk != null) {
                loaded++;
                ChunkPos pos = chunk.getPos();
                AuraChunk ac = AuraHandler.getAura(world, pos);
                if (ac != null){
                    processChunk(pos, ac);
                    processed++;
                }
            }
        }
        Thaumcraft.LOGGER.info(world.getDimensionKey().getLocation() + " loaded " + loaded + " processed " + processed);
    }

    void processChunk(ChunkPos pos, AuraChunk chunk){
        List<Integer> directions = Arrays.asList(0, 1, 2, 3);
        Collections.shuffle(directions, world.rand);
        int x = pos.x;
        int z = pos.z;
        float base = chunk.base * phaseMax;
        float currentVis = chunk.vis;
        float currentFlux = chunk.flux;
        AuraChunk spreadVis = null;
        AuraChunk spreadFlux = null;
        float lowestVis = Float.MAX_VALUE;
        float lowestFlux = Float.MAX_VALUE;
        for (Integer a : directions) {
            Direction dir = Direction.byHorizontalIndex(a);
            ChunkPos nPos = new ChunkPos(x + dir.getXOffset(), z + dir.getZOffset());
            AuraChunk n = AuraHandler.getAura(world, nPos);
            if (n != null) {
                if ((spreadVis == null || lowestVis > n.vis) && n.vis + n.flux < n.base * phaseMax) {
                    spreadVis = n;
                    lowestVis = n.vis;
                }
                if (spreadFlux == null || lowestFlux > n.flux) {
                    spreadFlux = n;
                    lowestFlux = n.flux;
                }
            }
        }
        if (spreadVis != null && lowestVis < currentVis && lowestVis / currentVis < 0.75) {
            float inc = Math.min(currentVis - lowestVis, 1.0f);
            currentVis -= inc;
            spreadVis.vis += inc;
        }
        if (spreadFlux != null && currentFlux > Math.max(5.0f, chunk.base / 10.0f) && lowestFlux < currentFlux / 1.75) {
            float inc = Math.min(currentFlux - lowestFlux, 1.0f);
            currentFlux -= inc;
            spreadFlux.flux += inc;
        }
        if (currentVis + currentFlux < base) {
            float inc = Math.min(base - (currentVis + currentFlux), phaseVis);
            currentVis += inc;
        }
        else if (currentVis > base * 1.25 && world.rand.nextFloat() < 0.1) {
            currentFlux += phaseFlux;
            currentVis -= phaseFlux;
        }
        else if (currentVis <= base * 0.1 && currentVis >= currentFlux && world.rand.nextFloat() < 0.1) {
            currentFlux += phaseFlux;
        }
        chunk.vis = currentVis;
        chunk.flux = currentFlux;
        if (currentFlux > base * 0.75 && world.rand.nextFloat() < currentFlux / 5000f) {
            riftTrigger.add(new BlockPos(pos.x << 4, 0, pos.z << 4));
        }
    }
}
