package griglog.thaumcraft.aura;

import griglog.thaumcraft.utils.BiomeInfo;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Aura {
    @CapabilityInject(Aura.class)
    public static Capability<Aura> AURA_CAP;
    public AuraChunk ac;
    public Aura(){

    }
    public Aura(Chunk chunk){
        if (chunk.dirty) {
            World world = chunk.getWorld();
            BlockPos center = new BlockPos(chunk.getPos().getXStart() + 8, 0, chunk.getPos().getZStart() + 8);
            float auraMod = BiomeInfo.getAuraModifier(world.getBiome(center));
            auraMod += BiomeInfo.getAuraModifier(world.getBiome(center.add(8, 0, 8)));
            auraMod += BiomeInfo.getAuraModifier(world.getBiome(center.add(8, 0, -8)));
            auraMod += BiomeInfo.getAuraModifier(world.getBiome(center.add(-8, 0, 8)));
            auraMod += BiomeInfo.getAuraModifier(world.getBiome(center.add(-8, 0, -8)));
            auraMod /= 5;
            float noise = (float) (1.0 + world.rand.nextGaussian() * 0.1);
            int base = MathHelper.clamp((int) (auraMod * 500.0f * noise), 0, 500);
            ac = new AuraChunk(base, base, 0);
        } else {
            ac = new AuraChunk(0, 0, 0);
        }
    }
    public CompoundNBT getNbt(){
        CompoundNBT tag = new CompoundNBT();
        tag.putFloat("base", ac.base);
        tag.putFloat("flux", ac.flux);
        tag.putFloat("vis", ac.vis);
        return tag;
    }

    public Aura setNbt(CompoundNBT tag){
        ac.base = tag.getFloat("base");
        ac.flux = tag.getFloat("flux");
        ac.vis = tag.getFloat("vis");
        return this;
    }

    public static class Provider implements ICapabilitySerializable<INBT> {
        public Provider(Chunk chunk){
            instance = LazyOptional.of(() -> new Aura(chunk));
        }
        public Provider(){
            instance = LazyOptional.of(() -> new Aura());
        }
        private final LazyOptional<Aura> instance;

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == AURA_CAP ? instance.cast() : LazyOptional.empty();
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
            return cap == AURA_CAP ? instance.cast() : LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT() {
            return AURA_CAP.getStorage().writeNBT(AURA_CAP, instance.resolve().get(), null);
        }

        @Override
        public void deserializeNBT(INBT nbt) {
            AURA_CAP.getStorage().readNBT(AURA_CAP, instance.resolve().get(), null, nbt);
        }
    }

    public static class Storage implements Capability.IStorage<Aura> {
        @Override
        public INBT writeNBT(Capability<Aura> capability, Aura AuraCap, Direction side) {
            return AuraCap.getNbt();
        }

        @Override
        public void readNBT(Capability<Aura> capability, Aura AuraCap, Direction side, INBT nbt) {
            AuraCap.setNbt((CompoundNBT)nbt);
        }
    }
}
