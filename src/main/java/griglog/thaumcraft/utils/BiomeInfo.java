package griglog.thaumcraft.utils;


import griglog.thaumcraft.aspect.Aspect;
import griglog.thaumcraft.aspect.Aspects;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class BiomeInfo {
    float aura, greatwoodChance;
    Aspect aspect;

    public BiomeInfo(float aura, Aspect aspect, float greatwoodChance) {
        this.aura = aura;
        this.greatwoodChance = greatwoodChance;
        this.aspect = aspect;
    }

    public static float getAuraModifier(Biome biome) {
        float average = 0.0f;
        int count = 0;
        RegistryKey<Biome> key = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, biome.getRegistryName());
        for (BiomeDictionary.Type type : BiomeDictionary.getTypes(key)){
            average += getInfo(type).aura;
            count++;
        }
        return average / count;
    }

    public static BiomeInfo getInfo(BiomeDictionary.Type type){
        return map.getOrDefault(type, new BiomeInfo(0.25f, null, 0));
    }

    public static Map<BiomeDictionary.Type, BiomeInfo> map = new HashMap<>();
    static {
        map.put(BiomeDictionary.Type.WATER, new BiomeInfo(0.33f, Aspects.WATER, 0.0f));
        map.put(BiomeDictionary.Type.OCEAN, new BiomeInfo(0.33f, Aspects.WATER, 0.0f));
        map.put(BiomeDictionary.Type.RIVER, new BiomeInfo(0.4f, Aspects.WATER, 0.0f));
        map.put(BiomeDictionary.Type.WET, new BiomeInfo(0.4f, Aspects.WATER, 0.0f));
        map.put(BiomeDictionary.Type.LUSH, new BiomeInfo(0.5f, Aspects.WATER, 0.5f));
        map.put(BiomeDictionary.Type.HOT, new BiomeInfo(0.33f, Aspects.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.DRY, new BiomeInfo(0.25f, Aspects.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.NETHER, new BiomeInfo(0.125f, Aspects.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.MESA, new BiomeInfo(0.33f, Aspects.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.SPOOKY, new BiomeInfo(0.5f, Aspects.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.DENSE, new BiomeInfo(0.4f, Aspects.ORDER, 0.0f));
        map.put(BiomeDictionary.Type.SNOWY, new BiomeInfo(0.25f, Aspects.ORDER, 0.0f));
        map.put(BiomeDictionary.Type.COLD, new BiomeInfo(0.25f, Aspects.ORDER, 0.0f));
        map.put(BiomeDictionary.Type.MUSHROOM, new BiomeInfo(0.75f, Aspects.ORDER, 0.0f));
        map.put(BiomeDictionary.Type.MAGICAL, new BiomeInfo(0.75f, Aspects.ORDER, 1.0f));
        map.put(BiomeDictionary.Type.CONIFEROUS, new BiomeInfo(0.33f, Aspects.EARTH, 0.2f));
        map.put(BiomeDictionary.Type.FOREST, new BiomeInfo(0.5f, Aspects.EARTH, 1.0f));
        map.put(BiomeDictionary.Type.SANDY, new BiomeInfo(0.25f, Aspects.EARTH, 0.0f));
        map.put(BiomeDictionary.Type.BEACH, new BiomeInfo(0.3f, Aspects.EARTH, 0.0f));
        map.put(BiomeDictionary.Type.JUNGLE, new BiomeInfo(0.6f, Aspects.EARTH, 0.0f));
        map.put(BiomeDictionary.Type.SAVANNA, new BiomeInfo(0.25f, Aspects.AIR, 0.2f));
        map.put(BiomeDictionary.Type.MOUNTAIN, new BiomeInfo(0.3f, Aspects.AIR, 0.0f));
        map.put(BiomeDictionary.Type.HILLS, new BiomeInfo(0.33f, Aspects.AIR, 0.0f));
        map.put(BiomeDictionary.Type.PLAINS, new BiomeInfo(0.3f, Aspects.AIR, 0.2f));
        map.put(BiomeDictionary.Type.END, new BiomeInfo(0.125f, Aspects.AIR, 0.0f));
        map.put(BiomeDictionary.Type.SPARSE, new BiomeInfo(0.2f, Aspects.ENTROPY, 0.0f));
        map.put(BiomeDictionary.Type.SWAMP, new BiomeInfo(0.5f, Aspects.ENTROPY, 0.2f));
        map.put(BiomeDictionary.Type.WASTELAND, new BiomeInfo(0.125f, Aspects.ENTROPY, 0.0f));
        map.put(BiomeDictionary.Type.DEAD,new BiomeInfo( 0.1f, Aspects.ENTROPY, 0.0f));
    }
}
