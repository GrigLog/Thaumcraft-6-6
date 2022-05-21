package griglog.thaumcraft.data;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.AspectHelper;
import griglog.thaumcraft.aspect.AspectList;
import griglog.thaumcraft.aspect.Aspects;
import griglog.thaumcraft.aspect.ItemAspects;
import griglog.thaumcraft.utils.FileUtils;
import net.minecraft.block.Block;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.*;
import java.util.*;

public class CustomReloadListener extends ReloadListener<Void> {
    public static final String ITEM = "items.txt";
    public static final String ITEM_TAG = "item_tags.txt";
    public static final String BLOCK_TAG = "block_tags.txt";
    List<FileUtils.AspectHolderParsed> itemAspects = new ArrayList<>();
    List<FileUtils.AspectHolderParsed> itemTagAspects = new ArrayList<>();
    List<FileUtils.AspectHolderParsed> blockTagAspects = new ArrayList<>();
    Set<Item> generating = new HashSet<>();

    @Override
    protected Void prepare(IResourceManager resourceManager, IProfiler profilerIn) {
        itemAspects.clear();
        itemTagAspects.clear();
        blockTagAspects.clear();
        try {
            for (ResourceLocation rl : resourceManager.getAllResourceLocations("aspects/" + ITEM, (s) -> true)) {
                for (IResource resource : resourceManager.getAllResources(rl))
                    itemAspects.addAll(FileUtils.readAspects(resource));
            }
            for (ResourceLocation rl : resourceManager.getAllResourceLocations("aspects/" + ITEM_TAG, (s) -> true)) {
                for (IResource resource : resourceManager.getAllResources(rl))
                    itemTagAspects.addAll(FileUtils.readAspects(resource));
            }
            for (ResourceLocation rl : resourceManager.getAllResourceLocations("aspects/" + BLOCK_TAG, (s) -> true)) {
                for (IResource resource : resourceManager.getAllResources(rl))
                    blockTagAspects.addAll(FileUtils.readAspects(resource));
            }
        } catch (IOException exc){
            exc.printStackTrace();
        }
        return null;
    }

    @Override
    protected void apply(Void param, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        ItemAspects.tags = new HashMap<>();
        ItemAspects.items = new HashMap<>();
        ItemAspects.ignoreTags = new HashMap<>();
        ItemAspects.addToGenerated = new HashMap<>();
        for (FileUtils.AspectHolderParsed line : itemAspects){
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(line.id));
            if (item == Items.AIR) {
                Thaumcraft.LOGGER.error("Error while parsing item aspects: item " + line.id + " does not exist");
                continue;
            }
            switch (line.mode){
                case DEFAULT:
                    ItemAspects.items.put(item, line.aspects);
                    break;
                case OVERRIDE:
                    ItemAspects.ignoreTags.put(item, line.aspects);
                    break;
                case ADD_TO_GENERATED:
                    ItemAspects.addToGenerated.put(item, line.aspects);
                    break;
            }
        }
        tryFinish();
    }

    public void tryFinish(){
        for (FileUtils.AspectHolderParsed line : itemTagAspects){
            ITag<Item> tag = ItemTags.createOptional(new ResourceLocation(line.id));
            try {
                for (Item item : tag.getAllElements()) {
                    AspectList list = ItemAspects.items.getOrDefault(item, AspectList.EMPTY);
                    if (list == AspectList.EMPTY) {
                        ItemAspects.items.put(item, line.aspects.copy());
                    } else if (!ItemAspects.ignoreTags.containsKey(item))
                        list.merge(line.aspects);
                }
            } catch (IllegalStateException ecx){
                return;
            }
        }
        for (FileUtils.AspectHolderParsed line : blockTagAspects){
            ITag<Block> tag = BlockTags.createOptional(new ResourceLocation(line.id));
            try {
                for (Block block : tag.getAllElements()) {
                    Item item = block.asItem();
                    AspectList list = ItemAspects.items.getOrDefault(item, AspectList.EMPTY);
                    if (list == AspectList.EMPTY) {
                        ItemAspects.items.put(item, line.aspects.copy());
                    } else if (!ItemAspects.ignoreTags.containsKey(item))
                        list.merge(line.aspects);
                }
            } catch (IllegalStateException ecx){
                return;
            }
        }
        ItemAspects.items.putAll(ItemAspects.ignoreTags);
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null)
            return;
        for (Item item : ForgeRegistries.ITEMS.getValues()){
            if (!ItemAspects.items.containsKey(item)){
                generating.clear();
                genFromCraftsRecursive(item, server.getRecipeManager());
            }
        }
    }

    private AspectList genFromCraftsRecursive(Item item, RecipeManager recipes){
        generating.add(item);
        AspectList res = ItemAspects.items.getOrDefault(item, null);
        if (res != null) {
            generating.remove(item);
            return res;
        }
        int visValue = Integer.MAX_VALUE;
        for(IRecipe<?> recipe : recipes.getRecipes()) {
            if (recipe.getRecipeOutput().getItem() != item)
                continue;
            AspectList recipeAspects = new AspectList();
            for (Ingredient ing : recipe.getIngredients()) {
                if (ing.getMatchingStacks().length == 0)
                    continue;
                Item test = ing.getMatchingStacks()[0].getItem();
                if (generating.contains(test)) {
                    recipeAspects = AspectList.EMPTY;
                    break;
                }
                AspectList ingAspects = genFromCraftsRecursive(test, recipes);
                if (ingAspects == null) {
                    recipeAspects = AspectList.EMPTY;
                    break;
                }
                recipeAspects.add(ingAspects);
            }
            if (recipeAspects.size() == 0)
                continue;
            recipeAspects.multiply(0.75f / recipe.getRecipeOutput().getCount());
            if (recipe.getType() == IRecipeType.SMELTING) {
                recipeAspects.add(Aspects.FIRE, 1);
            }
            if (recipeAspects.visSize() < visValue)
                res = recipeAspects;
        }
        if (res == null)
            return null;
        if (ItemAspects.addToGenerated.containsKey(item))
            res.merge(ItemAspects.addToGenerated.get(item));
        AspectHelper.keepMostValuable(res, 5);
        ItemAspects.items.put(item, res);
        generating.remove(item);
        return res;
    }
}
