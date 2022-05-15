package griglog.thaumcraft.utils;

import griglog.thaumcraft.Thaumcraft;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class EntityWrapper<T extends Entity> {
    String regName;
    EntityType.Builder<T> builder;

    public EntityWrapper(EntityType.IFactory<T> factory, EntityClassification cls, String registryName) {
        regName = registryName;
        builder = EntityType.Builder.<T>create(factory, cls);
    }
    @SuppressWarnings("unchecked")
    public EntityType<T> get(){
        return (EntityType<T>) builder.build("").setRegistryName(Thaumcraft.id, regName);
    }

    public EntityWrapper<T> sized(float a, float b){
        builder.size(a, b);
        return this;
    }

    public EntityWrapper<T> noSummon() {
        builder.disableSummoning();
        return this;
    }

    public EntityWrapper<T> noSave() {
        builder.disableSerialization();
        return this;
    }

    public EntityWrapper<T> fireImmune() {
        builder.immuneToFire();
        return this;
    }

    public EntityWrapper<T> immuneTo(Block... blocks) {
        builder.func_233607_a_(blocks);
        return this;
    }

    public EntityWrapper<T> canSpawnFarFromPlayer() {
        builder.func_225435_d();
        return this;
    }

    public EntityWrapper<T> clientTrackingRange(int pRange) {
        builder.trackingRange(pRange);
        return this;
    }

    public EntityWrapper<T> updateInterval(int interval) {
        builder.updateInterval(interval);
        return this;
    }

    public EntityWrapper<T> setUpdateInterval(int interval) {
        builder.setUpdateInterval(interval);
        return this;
    }

    public EntityWrapper<T> setTrackingRange(int range) {
        builder.setTrackingRange(range);
        return this;
    }

    public EntityWrapper<T> setShouldReceiveVelocityUpdates(boolean value) {
        builder.setShouldReceiveVelocityUpdates(value);
        return this;
    }

    public EntityWrapper<T> setCustomClientFactory(java.util.function.BiFunction<net.minecraftforge.fml.network.FMLPlayMessages.SpawnEntity, World, T> customClientFactory) {
        builder.setCustomClientFactory(customClientFactory);
        return this;
    }
}
