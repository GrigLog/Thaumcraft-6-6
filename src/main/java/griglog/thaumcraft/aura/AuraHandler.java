package griglog.thaumcraft.aura;

import griglog.thaumcraft.Thaumcraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.server.ServerWorld;

import java.time.Instant;
import java.util.*;

public class AuraHandler{
    public static Map<ServerWorld, AuraWorld> auras = new HashMap<>();
    static final float[] phaseTable = new float[] { 0.25f, 0.15f, 0.1f, 0.05f, 0.0f, 0.05f, 0.1f, 0.15f };
    static final float[] maxTable = new float[] { 0.15f, 0.05f, 0.0f, -0.05f, -0.15f, -0.05f, 0.0f, 0.05f };
    static final int period = 1;

    static int c = 0;
    public static void tick() {
        if (c++ % period == 0) {
            long start = Instant.now().toEpochMilli();
            for (AuraWorld aw : auras.values()) {
                aw.process();
            }
            Thaumcraft.LOGGER.info("took " + (Instant.now().toEpochMilli() - start) + " ms");
        }
    }

    public static AuraChunk getAura(ServerWorld world, BlockPos pos){
        return Aura.getAuraChunk(world.getChunkAt(pos));
    }

    public static AuraChunk getAura(ServerWorld world, ChunkPos pos){
        return Aura.getAuraChunk(world.getChunk(pos.x, pos.z));
    }

    public static AuraChunk readAura(ServerWorld world, BlockPos pos){
        AuraChunk res = getAura(world, pos);
        return res != null ? res : new AuraChunk(0, 0, 0);
    }
}
