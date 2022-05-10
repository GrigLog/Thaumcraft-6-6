package griglog.thaumcraft.data;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.datafixers.util.Pair;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import static griglog.thaumcraft.blocks.ModBlocks.*;

public class BlockDropsProvider extends LootTableProvider {
    static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    final Map<Block, LootTable.Builder> lootTables = new HashMap<>();
    static Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();
    final DataGenerator generator;
    private static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(ItemPredicate.Builder.create()
        .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))));

    public BlockDropsProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        generator = dataGeneratorIn;
    }

    static Set<Block> special = ImmutableSet.of(silverLeaves, greatLeaves);
    @Override
    public void act(DirectoryCache cache){
        for (Block b : Utils.<Block>getFields(ModBlocks.class, Block.class, null)){
            if (!special.contains(b))
                tables.put(b.getRegistryName(), lootTables.getOrDefault(b, simple(b)));
        }
        for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
            Path path = getPath(generator.getOutputFolder(), e.getKey());
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(e.getValue().setParameterSet(LootParameterSets.BLOCK).build()), path);
            } catch (IOException exc){
                exc.printStackTrace();
            }
        }
    }

    protected static Path getPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return super.getTables();
    }

    LootTable.Builder simple(Block b){
        return LootTable.builder().addLootPool(
            LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                ItemLootEntry.builder(b).acceptCondition(SurvivesExplosion.builder())));
    }

    LootTable.Builder silk(IItemProvider normal, IItemProvider silk){
        LootEntry.Builder<?> cobbleDrop = ItemLootEntry.builder(normal).acceptCondition(SurvivesExplosion.builder());
        LootEntry.Builder<?> stoneDrop = ItemLootEntry.builder(silk).acceptCondition(SILK_TOUCH);
        return LootTable.builder().addLootPool(
            LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                stoneDrop.alternatively(cobbleDrop)));
    }

    void putSimple(Block b){
        lootTables.put(b, simple(b));
    }
}
