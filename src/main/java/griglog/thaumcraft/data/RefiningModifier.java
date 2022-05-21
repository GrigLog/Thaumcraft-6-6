package griglog.thaumcraft.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.items.infusions.InfusionEnchantment;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RefiningModifier extends LootModifier {
    float baseChance, perLevel;
    List<Entry> drops;
    public RefiningModifier(float base, float perLevel, List<Entry> drops) {
        super(new ILootCondition[0]);
        this.baseChance = base;
        this.perLevel = perLevel;
        this.drops = drops;
    }

    public static class Entry {
        Ingredient ing;
        ItemStack res;
        float chance;

        public Entry(Ingredient ing, ItemStack res, float chance) {
            this.ing = ing;
            this.res = res;
            this.chance = chance;
        }
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (!context.has(LootParameters.THIS_ENTITY) || !(context.get(LootParameters.THIS_ENTITY) instanceof ServerPlayerEntity))
            return generatedLoot;
        ServerPlayerEntity player = (ServerPlayerEntity) context.get(LootParameters.THIS_ENTITY);
        int level = InfusionEnchantment.getLevel(player.getHeldItem(player.getActiveHand()), InfusionEnchantment.REFINING);
        if (level == 0)
            return generatedLoot;
        for (int loot = 0; loot < generatedLoot.size(); loot++){
            for (Entry e : drops){
                if (e.ing.test(generatedLoot.get(loot))){
                    ItemStack clone = e.res.copy();
                    clone.setCount(0);
                    int totalCount = 0;
                    float chance = (baseChance + perLevel * level) * e.chance;
                    for (int i = 0; i < generatedLoot.get(loot).getCount(); i++){
                        if (context.getRandom().nextFloat() < chance)
                            totalCount += e.res.getCount();
                    }
                    if (totalCount > 0) {
                        clone.setCount(totalCount);
                        generatedLoot.set(loot, clone);
                    }
                }
            }
        }
        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<RefiningModifier> {
        public Serializer() {
            setRegistryName(Thaumcraft.id, "refining_serializer");
        }

        @Override
        public RefiningModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditions) {
            float base = object.get("baseChance").getAsFloat();
            float perLevel = object.get("chancePerLevel").getAsFloat();
            List<Entry> drops = new ArrayList<>();
            for (JsonElement el : object.get("drops").getAsJsonArray()){
                JsonObject e = el.getAsJsonObject();
                try {
                    drops.add(new Entry(
                        Ingredient.deserialize(e.get("ingredient")),
                        ItemStack.CODEC.parse(JsonOps.INSTANCE, e.get("result")).getOrThrow(false, (s) -> Thaumcraft.LOGGER.error(e)),
                        e.get("chance").getAsFloat()));
                } catch (Exception error){
                    Thaumcraft.LOGGER.error("Unable to parse entry " + e.toString());
                }
            }
            return new RefiningModifier(base, perLevel, drops);
        }

        @Override
        public JsonObject write(RefiningModifier m) {
            JsonObject json = new JsonObject();
            json.add("baseChance", new JsonPrimitive(m.baseChance));
            json.add("chancePerLevel", new JsonPrimitive(m.perLevel));
            JsonArray drops = new JsonArray();
            for (Entry e : m.drops){
                JsonObject obj = new JsonObject();
                obj.add("ingredient", e.ing.serialize());
                obj.add("result", ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, e.res).getOrThrow(false, (s) -> Thaumcraft.LOGGER.error(e)));
                obj.add("chance", new JsonPrimitive(e.chance));
                drops.add(obj);
            }
            json.add("drops", drops);
            return json;
        }
    }
}
