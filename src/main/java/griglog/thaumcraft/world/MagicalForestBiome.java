package griglog.thaumcraft.world;

import griglog.thaumcraft.Thaumcraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.registries.ForgeRegistries;

public class MagicalForestBiome {
    public static final ResourceLocation name = new ResourceLocation(Thaumcraft.id, "magical_forest");
    public static Biome BIOME = MagicalForestBiome.createBiomeBuilder(0.2f, 0.3f).build().setRegistryName(name);
    public static final RegistryKey<Biome> KEY = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, name);


    public static Biome.Builder createBiomeBuilder(float depth, float scale) {
        BiomeGenerationSettings.Builder builder = (new BiomeGenerationSettings.Builder())
            .withSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS)
            .withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(builder);
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
        DefaultBiomeFeatures.withMonsterRoom(builder);
        DefaultBiomeFeatures.withCommonOverworldBlocks(builder);
        DefaultBiomeFeatures.withOverworldOres(builder);
        DefaultBiomeFeatures.withDisks(builder);
        DefaultBiomeFeatures.withForestGrass(builder);
        MobSpawnInfo.Builder spawnInfoBuilder = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withPassiveMobs(spawnInfoBuilder);
        DefaultBiomeFeatures.withBatsAndHostiles(spawnInfoBuilder);
        DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(builder);
        DefaultBiomeFeatures.withFrozenTopLayer(builder);

        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_WATER);
        builder.withFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER);
        //builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_DEFAULT);
        //builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.BAMBOO);
        DefaultBiomeFeatures.withMushroomBiomeVegetation(builder);
        //DefaultBiomeFeatures.withNormalMushroomGeneration(builder);
        //Features.BROWN_MUSHROOM_NORMAL

        //builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.HUGE_RED_MUSHROOM);

        //spawnInfoBuilder.withSpawner(EntityClassification.MONSTER, EntityPech.spawner);

        //builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.silverTreeConf.chance(1));
        //builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModFeatures.bigTreeConf);

        return new Biome.Builder()
            .precipitation(Biome.RainType.RAIN)
            .category(Biome.Category.FOREST)
            .depth(depth)
            .scale(scale)
            .temperature(0.8F)
            .downfall(0.4F)
            .setEffects(new BiomeAmbience.Builder()
                .withSkyColor(getSkyColorWithTemperatureModifier(0.8F))
                .setFogColor(12638463)
                .setWaterColor(0x1038C9)
                .setWaterFogColor(329011)
                .withGrassColor(0x55FF81)
                .withFoliageColor(0x66FFC5)
                .setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build())
            .withMobSpawnSettings(spawnInfoBuilder.build())
            .withGenerationSettings(builder.build());
        }

    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
}