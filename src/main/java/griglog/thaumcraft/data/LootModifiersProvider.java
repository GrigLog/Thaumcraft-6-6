package griglog.thaumcraft.data;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;


public class LootModifiersProvider extends GlobalLootModifierProvider {
    public LootModifiersProvider(DataGenerator gen) {
        super(gen, Thaumcraft.id);
    }

    @Override
    protected void start() {
        add("refining", new RefiningModifier.Serializer(), new RefiningModifier(0.125f, 0.125f, Arrays.asList(
            new RefiningModifier.Entry(Ingredient.fromTag(Tags.Items.ORES_IRON), new ItemStack(ModItems.ironCluster), 1f),
            new RefiningModifier.Entry(Ingredient.fromTag(Tags.Items.ORES_GOLD), new ItemStack(ModItems.goldCluster), 1f),
            new RefiningModifier.Entry(Ingredient.fromTag(Tags.Items.ORES_QUARTZ), new ItemStack(ModItems.quartzCluster), 1f),
            //cinnabar
            new RefiningModifier.Entry(Ingredient.fromTag(ModTags.ORES_COPPER), new ItemStack(ModItems.copperCluster), 1f),
            new RefiningModifier.Entry(Ingredient.fromTag(ModTags.ORES_TIN), new ItemStack(ModItems.tinCluster), 1f),
            new RefiningModifier.Entry(Ingredient.fromTag(ModTags.ORES_SILVER), new ItemStack(ModItems.silverCluster), 1f),
            new RefiningModifier.Entry(Ingredient.fromTag(ModTags.ORES_LEAD), new ItemStack(ModItems.leadCluster), 1f)
        )));
    }
}
