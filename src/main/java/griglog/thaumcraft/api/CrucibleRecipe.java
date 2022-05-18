package griglog.thaumcraft.api;

import griglog.thaumcraft.aspect.Aspect;
import griglog.thaumcraft.aspect.AspectList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class CrucibleRecipe {
    private ItemStack recipeOutput;
    public Ingredient catalyst;
    public AspectList aspects;
    public String research;
    public int hash;

    public CrucibleRecipe(ItemStack result, Ingredient catalyst, AspectList tags){
        this("", result, catalyst, tags);
    }

    public CrucibleRecipe(String researchKey, ItemStack result, Ingredient catalyst, AspectList tags) {
        recipeOutput = result;
        aspects = tags.copy();
        research = researchKey;
        this.catalyst = catalyst;
        generateHash();
    }

    private void generateHash() {
        String hc = research;
        hc += recipeOutput.toString();
        if (recipeOutput.hasTag()) {
            hc += recipeOutput.getTag().toString();
        }
        for (ItemStack is : catalyst.getMatchingStacks()) {
            hc += is.toString();
            if (is.hasTag()) {
                hc += is.getTag().toString();
            }
        }
        hash = hc.hashCode();
    }

    public ItemStack getRecipeOutput(){
        return recipeOutput.copy();
    }

    public boolean matches(AspectList test, ItemStack cat) {
        if (!catalyst.test(cat))
            return false;
        for (Aspect tag : aspects.getAspects()) {
            if (test.getAmount(tag) < aspects.getAmount(tag))
                return false;
        }
        return true;
    }
}
