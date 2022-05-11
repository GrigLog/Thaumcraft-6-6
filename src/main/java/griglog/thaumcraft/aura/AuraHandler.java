package griglog.thaumcraft.aura;

import griglog.thaumcraft.Thaumcraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;

import java.time.Instant;
import java.util.*;

public class AuraHandler {
    public static Map<ServerWorld, AuraWorld> auras = new HashMap<>();
    static final float[] phaseTable = new float[] { 0.25f, 0.15f, 0.1f, 0.05f, 0.0f, 0.05f, 0.1f, 0.15f };
    static final float[] maxTable = new float[] { 0.15f, 0.05f, 0.0f, -0.05f, -0.15f, -0.05f, 0.0f, 0.05f };

    static int c;
    public static void tick() {
        if (c++ % 20 != 0)
            return;
        long start = Instant.now().toEpochMilli();
        for (AuraWorld aw : auras.values()) {
            aw.process();
        }
        Thaumcraft.LOGGER.info("took " + (Instant.now().toEpochMilli() - start) + " ms");
        /*
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }*/
    }

    public static AuraChunk getAura(ServerWorld world, BlockPos pos){
        return getAura(world, new ChunkPos(pos));
    }

    public static AuraChunk getAura(ServerWorld world, ChunkPos pos){
        AuraWorld aw = auras.get(world);
        return aw != null ? aw.getAura(pos) : null;
    }

    public static AuraChunk readAura(ServerWorld world, BlockPos pos){
        AuraChunk res = getAura(world, pos);
        return res != null ? res : new AuraChunk(0, 0, 0);
    }
}
