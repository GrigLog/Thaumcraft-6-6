package griglog.thaumcraft.aura;

import net.minecraft.util.math.ChunkPos;

public class AuraChunk {
    public float base, flux, vis;

    public AuraChunk(float base, float vis, float flux) {
        this.base = base;
        this.flux = flux;
        this.vis = vis;
    }
}
