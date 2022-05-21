package griglog.thaumcraft.data.providers;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.blocks.ModBlocks;
import griglog.thaumcraft.items.ModItems;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import static griglog.thaumcraft.items.ModItems.*;
import static griglog.thaumcraft.blocks.ModBlocks.*;

import java.util.Set;

public class ItemModelsProvider extends ItemModelProvider {

    public ItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Thaumcraft.id, existingFileHelper);
    }

    public static Set<Item> colored = ImmutableSet.of(clothChest, clothLegs, clothBoots, voidRobeHelm, voidRobeChest, voidRobeLegs);
    public static Set<Block> blockItems = ImmutableSet.of(silverLog, greatLog, silverLeaves, greatLeaves, crucible);
    public static Set<Item> tool = ImmutableSet.of(
        thaumiumSword, thaumiumShovel, thaumiumPickaxe, thaumiumHoe, thaumiumAxe,
        elementalSword, elementalShovel, elementalPickaxe, elementalHoe, elementalAxe,
        voidSword, voidShovel, voidPickaxe, voidHoe, voidAxe, primalCrusher);
    public static Set<Item> custom = ImmutableSet.of(phialEmpty, phialFull);
    public static Set<Item> usual = ImmutableSet.of(thaumiumIngot, brassIngot, voidIngot, quicksilver,
        ironCluster, goldCluster, copperCluster, tinCluster, silverCluster, leadCluster, cinnabarCluster, quartzCluster,
        quicksilverNugget, voidNugget, thaumiumNugget, brassNugget, quartzNugget, rareEarth, fabric,
        goggles, travellerBoots, thaumiumHelmet, thaumiumChestplate, thaumiumLeggings, thaumiumBoots, voidHelmet, voidChestplate, voidLeggings, voidBoots, cultHelm, cultChest, cultLegs, cultBoots, cultRobeHelm, cultRobeChest, cultRobeLegs, fortressHelm, fortressChest, fortressLegs);
    @Override
    protected void registerModels() {
        for (Item item : usual){
            if (!tool.contains(item) && !colored.contains(item) && !custom.contains(item))
                makeItemModel(item, "item/generated");
        }
        for (Block block : blockItems){
            String name = block.getRegistryName().getPath();
            withExistingParent(name, Thaumcraft.id + ":block/" + name);
        }
        for (Item item: tool)
            makeItemModel(item, "item/handheld");
        makeItemModel(greatSapling.asItem(), "item/generated", "block/");
        makeItemModel(silverSapling.asItem(), "item/generated", "block/");
        for (Item item : colored){
            makeColoredModel(item);
        }
    }


    private void makeColoredModel(Item item){
        String name = item.getRegistryName().getPath();
        withExistingParent(name, "item/generated")
            .texture("layer0", new ResourceLocation(Thaumcraft.id, "items/" + name))
            .texture("layer1", new ResourceLocation(Thaumcraft.id, "items/" + name + "_over"));
    }

    private void makeItemModel(Item item, String parent) {
        String name = item.getRegistryName().getPath();
        singleTexture(name, mcLoc(parent), "layer0", new ResourceLocation(Thaumcraft.id, "items/" + name));
    }

    private void makeItemModel(Item item, String parent, String texturePath) {
        String name = item.getRegistryName().getPath();
        singleTexture(name, mcLoc(parent), "layer0", new ResourceLocation(Thaumcraft.id, texturePath + name));
    }
}
