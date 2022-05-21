package griglog.thaumcraft.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.JsonOps;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.AspectEntry;
import griglog.thaumcraft.aspect.AspectList;
import griglog.thaumcraft.aspect.Aspects;
import griglog.thaumcraft.utils.ItemUtils;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Arrays;

public class CrucibleRecipe implements IRecipe<IInventory>, IFinishedRecipe {
    public static IRecipeType<CrucibleRecipe> type = IRecipeType.register("crucible");
    ResourceLocation id;
    public AspectList aspects;
    public Ingredient catalyst;
    public ItemStack res;

    public CrucibleRecipe(ResourceLocation id, AspectList aspects, Ingredient catalyst, ItemStack res) {
        this.id = id;
        this.aspects = aspects;
        this.catalyst = catalyst;
        this.res = res;
    }

    public boolean matches(AspectList asp, ItemStack cat){
        return catalyst.test(cat) && aspects.entries.stream().allMatch(ae -> asp.getAmount(ae.type) >= ae.amount);
    }

    @Override
    public ItemStack getRecipeOutput() {return res.copy();}

    @Override
    public ResourceLocation getId() {return id;}

    @Override
    public void serialize(JsonObject json) {
        Serializer.instance.write(json, this);
    }

    @Override
    public ResourceLocation getID() {return id;}

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return Serializer.instance;
    }

    @Override
    public IRecipeType<?> getType() {return type;}

    public static CrucibleRecipe check(RecipeManager recipes, AspectList aspects, ItemStack catalyst){
        for (IRecipe<?> r : recipes.getRecipesForType(type)){
            CrucibleRecipe recipe = (CrucibleRecipe) r;
            if (recipe.matches(aspects, catalyst))
                return recipe;
        }
        return null;
    }


    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrucibleRecipe> {
        public static Serializer instance = (Serializer) new Serializer().setRegistryName("crucible");
        public void write(JsonObject json, CrucibleRecipe recipe){
            json.add("catalyst", recipe.catalyst.serialize());
            json.add("result", ItemUtils.serialize(recipe.res));
            json.add("aspects", new JsonPrimitive(recipe.aspects.toString()));
        }
        @Override
        public CrucibleRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient catalyst = Ingredient.deserialize(json.get("catalyst"));
            ItemStack res = ItemUtils.deserialize(json.get("result"));
            AspectList aspects = new AspectList(JSONUtils.getString(json, "aspects"));
            return new CrucibleRecipe(recipeId, aspects, catalyst, res);
        }

        @Override
        public CrucibleRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient catalyst = Ingredient.read(buffer);
            ItemStack res = buffer.readItemStack();
            int len = buffer.readVarInt();
            AspectList aspects = new AspectList();
            for (int i = 0; i < len; i++){
                aspects.add(Aspects.get(buffer.readString()), buffer.readInt());
            }
            return new CrucibleRecipe(recipeId, aspects, catalyst, res);
        }

        @Override
        public void write(PacketBuffer buffer, CrucibleRecipe recipe) {
            recipe.catalyst.write(buffer);
            buffer.writeItemStack(recipe.res);
            buffer.writeVarInt(recipe.aspects.size());
            for (AspectEntry ae : recipe.aspects.entries){
                buffer.writeString(ae.type.tag);
                buffer.writeInt(ae.amount);
            }
        }
    }

    @Override
    public JsonObject getAdvancementJson() {return null;}
    @Override
    public ResourceLocation getAdvancementID() {return null;}
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }
    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return null;
    }
    @Override
    public boolean canFit(int width, int height) {
        return true;
    }
}
