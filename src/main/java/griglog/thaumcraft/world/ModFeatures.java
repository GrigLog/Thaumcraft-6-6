package griglog.thaumcraft.world;

import griglog.thaumcraft.blocks.ModBlocks;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;

public class ModFeatures {
    //this is actually ignored, but I can't just pass null
    static final BaseTreeFeatureConfig dummyConfig = new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(ModBlocks.silverLog.getDefaultState()), new SimpleBlockStateProvider(ModBlocks.silverLeaves.getDefaultState()), new SpruceFoliagePlacer(FeatureSpread.create(4), FeatureSpread.create(2), FeatureSpread.create(6)), new GiantTrunkPlacer(7, 2, 2), new TwoLayerFeature(1, 1, 2)).build();

    public static final Feature<BaseTreeFeatureConfig> silverTree = new SilverTree.Feature();
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> silverTreeConf = silverTree.withConfiguration(dummyConfig);
    public static final Feature<BaseTreeFeatureConfig> greatTree = new GreatTree.Feature();
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> greatTreeConf = greatTree.withConfiguration(dummyConfig);
    public static final Feature<BaseTreeFeatureConfig> bigTree = new BigTree.Feature();
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> bigTreeConf = bigTree.withConfiguration(dummyConfig);
}
