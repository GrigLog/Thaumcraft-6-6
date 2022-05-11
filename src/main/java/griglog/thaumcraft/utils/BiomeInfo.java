package griglog.thaumcraft.utils;

import griglog.thaumcraft.aspect.Aspect;
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
        map.put(BiomeDictionary.Type.WATER, new BiomeInfo(0.33f, Aspect.WATER, 0.0f));
        map.put(BiomeDictionary.Type.OCEAN, new BiomeInfo(0.33f, Aspect.WATER, 0.0f));
        map.put(BiomeDictionary.Type.RIVER, new BiomeInfo(0.4f, Aspect.WATER, 0.0f));
        map.put(BiomeDictionary.Type.WET, new BiomeInfo(0.4f, Aspect.WATER, 0.0f));
        map.put(BiomeDictionary.Type.LUSH, new BiomeInfo(0.5f, Aspect.WATER, 0.5f));
        map.put(BiomeDictionary.Type.HOT, new BiomeInfo(0.33f, Aspect.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.DRY, new BiomeInfo(0.25f, Aspect.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.NETHER, new BiomeInfo(0.125f, Aspect.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.MESA, new BiomeInfo(0.33f, Aspect.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.SPOOKY, new BiomeInfo(0.5f, Aspect.FIRE, 0.0f));
        map.put(BiomeDictionary.Type.DENSE, new BiomeInfo(0.4f, Aspect.ORDER, 0.0f));
        map.put(BiomeDictionary.Type.SNOWY, new BiomeInfo(0.25f, Aspect.ORDER, 0.0f));
        map.put(BiomeDictionary.Type.COLD, new BiomeInfo(0.25f, Aspect.ORDER, 0.0f));
        map.put(BiomeDictionary.Type.MUSHROOM, new BiomeInfo(0.75f, Aspect.ORDER, 0.0f));
        map.put(BiomeDictionary.Type.MAGICAL, new BiomeInfo(0.75f, Aspect.ORDER, 1.0f));
        map.put(BiomeDictionary.Type.CONIFEROUS, new BiomeInfo(0.33f, Aspect.EARTH, 0.2f));
        map.put(BiomeDictionary.Type.FOREST, new BiomeInfo(0.5f, Aspect.EARTH, 1.0f));
        map.put(BiomeDictionary.Type.SANDY, new BiomeInfo(0.25f, Aspect.EARTH, 0.0f));
        map.put(BiomeDictionary.Type.BEACH, new BiomeInfo(0.3f, Aspect.EARTH, 0.0f));
        map.put(BiomeDictionary.Type.JUNGLE, new BiomeInfo(0.6f, Aspect.EARTH, 0.0f));
        map.put(BiomeDictionary.Type.SAVANNA, new BiomeInfo(0.25f, Aspect.AIR, 0.2f));
        map.put(BiomeDictionary.Type.MOUNTAIN, new BiomeInfo(0.3f, Aspect.AIR, 0.0f));
        map.put(BiomeDictionary.Type.HILLS, new BiomeInfo(0.33f, Aspect.AIR, 0.0f));
        map.put(BiomeDictionary.Type.PLAINS, new BiomeInfo(0.3f, Aspect.AIR, 0.2f));
        map.put(BiomeDictionary.Type.END, new BiomeInfo(0.125f, Aspect.AIR, 0.0f));
        map.put(BiomeDictionary.Type.SPARSE, new BiomeInfo(0.2f, Aspect.ENTROPY, 0.0f));
        map.put(BiomeDictionary.Type.SWAMP, new BiomeInfo(0.5f, Aspect.ENTROPY, 0.2f));
        map.put(BiomeDictionary.Type.WASTELAND, new BiomeInfo(0.125f, Aspect.ENTROPY, 0.0f));
        map.put(BiomeDictionary.Type.DEAD,new BiomeInfo( 0.1f, Aspect.ENTROPY, 0.0f));
    }
}
