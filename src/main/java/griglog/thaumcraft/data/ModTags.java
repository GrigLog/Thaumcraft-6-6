package griglog.thaumcraft.data;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import static net.minecraftforge.common.Tags.*;

public class ModTags {
    public final static IOptionalNamedTag<Item> ORES_COPPER = ore("copper");
    public final static IOptionalNamedTag<Item> ORES_TIN = ore("tin");
    public final static IOptionalNamedTag<Item> ORES_SILVER = ore("silver");
    public final static IOptionalNamedTag<Item> ORES_LEAD = ore("lead");
    public final static IOptionalNamedTag<Item> ORES_URANIUM = ore("uranium");
    public final static IOptionalNamedTag<Item> DUSTS_COPPER = dust("copper");
    public final static IOptionalNamedTag<Item> DUSTS_TIN = dust("tin");
    public final static IOptionalNamedTag<Item> DUSTS_SILVER = dust("silver");
    public final static IOptionalNamedTag<Item> DUSTS_LEAD = dust("lead");
    public final static IOptionalNamedTag<Item> DUSTS_BRASS = dust("brass");
    public final static IOptionalNamedTag<Item> DUSTS_BRONZE = dust("bronze");
    public final static IOptionalNamedTag<Item> CLUSTERS_COPPER = cluster("copper");
    public final static IOptionalNamedTag<Item> CLUSTERS_TIN = cluster("tin");
    public final static IOptionalNamedTag<Item> CLUSTERS_SILVER = cluster("silver");
    public final static IOptionalNamedTag<Item> CLUSTERS_LEAD = cluster("lead");
    public final static IOptionalNamedTag<Item> INGOTS_COPPER = ingot("copper");
    public final static IOptionalNamedTag<Item> INGOTS_TIN = ingot("tin");
    public final static IOptionalNamedTag<Item> INGOTS_SILVER = ingot("silver");
    public final static IOptionalNamedTag<Item> INGOTS_LEAD = ingot("lead");
    public final static IOptionalNamedTag<Item> INGOTS_BRASS = ingot("brass");
    public final static IOptionalNamedTag<Item> INGOTS_BRONZE = ingot("bronze");
    public final static IOptionalNamedTag<Item> INGOTS_URANIUM = ingot("uranium");
    public final static IOptionalNamedTag<Item> INGOTS_STEEL = ingot("steel");

    public final static IOptionalNamedTag<Item> GEMS_RUBY = gem("ruby");
    public final static IOptionalNamedTag<Item> GEMS_SAPPHIRE = gem("sapphire");


    public final static IOptionalNamedTag<Item> DUSTS_IRON = dust("iron");
    public final static IOptionalNamedTag<Item> DUSTS_GOLD = dust("gold");

    public final static IOptionalNamedTag<Item> GEAR = ItemTags.createOptional(new ResourceLocation("forge", "gear"));

    public final static IOptionalNamedTag<Item> CLUSTERS = ItemTags.createOptional(new ResourceLocation("thaumcraft", "clusters"));

    public static IOptionalNamedTag<Item> ore(String name){
        return ItemTags.createOptional(new ResourceLocation("forge", "ores/" + name));
    }

    public static IOptionalNamedTag<Item> dust(String name){
        return ItemTags.createOptional(new ResourceLocation("forge", "dusts/" + name));
    }

    public static IOptionalNamedTag<Item> ingot(String name){
        return ItemTags.createOptional(new ResourceLocation("forge", "ingots/" + name));
    }

    public static IOptionalNamedTag<Item> cluster(String name){
        return ItemTags.createOptional(new ResourceLocation("thaumcraft", "clusters/" + name));
    }

    public static IOptionalNamedTag<Item> gem(String name){
        return ItemTags.createOptional(new ResourceLocation("forge", "gems/" + name));
    }
}
