package griglog.thaumcraft.data.providers;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.AspectList;
import griglog.thaumcraft.aspect.Aspects;
import griglog.thaumcraft.data.CrucibleRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static griglog.thaumcraft.aspect.Aspects.*;

public class RecipesProvider extends RecipeProvider {
    Consumer<IFinishedRecipe> consumer;
    Map<String, Integer> usedNames = new HashMap<>();
    public RecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.consumer = consumer;
        addCrucible(new ItemStack(Items.LEATHER), Ingredient.fromItems(Items.ROTTEN_FLESH), new AspectList()
            .add(AIR, 3).add(BEAST, 3));
    }

    void addCrucible(ItemStack res, Ingredient catalyst, AspectList list){
        String name = res.getItem().getRegistryName().getPath();
        Integer count = usedNames.getOrDefault(name, 0);
        if (count > 0)
            name += count;
        usedNames.put(name, count+1);
        consumer.accept(new CrucibleRecipe(new ResourceLocation(Thaumcraft.id, "crucible/" + name), list, catalyst, res));
    }
}
