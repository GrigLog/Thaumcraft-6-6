package griglog.thaumcraft.items.infusions;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.items.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Field;
import java.util.*;

public class InfusionEnchantment {
    public static final Map<ResourceLocation, InfusionEnchantment> enchants = new HashMap<>();
    public static final InfusionEnchantment COLLECTOR = new InfusionEnchantment("collector", 1, "INFUSIONENCHANTMENT", "axe", "pickaxe", "shovel", "weapon");
    public static final InfusionEnchantment DESTRUCTIVE = new InfusionEnchantment("destructive", 1, "INFUSIONENCHANTMENT", "axe", "pickaxe", "shovel");
    public static final InfusionEnchantment BURROWING = new InfusionEnchantment("burrowing", 1, "INFUSIONENCHANTMENT", "axe", "pickaxe");
    public static final InfusionEnchantment SOUNDING = new InfusionEnchantment("sounding", 4, "INFUSIONENCHANTMENT", "pickaxe");
    public static final InfusionEnchantment REFINING = new InfusionEnchantment("refining", 4, "INFUSIONENCHANTMENT", "pickaxe");
    public static final InfusionEnchantment ARCING = new InfusionEnchantment("arcing", 4, "INFUSIONENCHANTMENT", "weapon");
    public static final InfusionEnchantment ESSENCE = new InfusionEnchantment("essence", 5, "INFUSIONENCHANTMENT", "weapon");
    public static final InfusionEnchantment VISBATTERY = new InfusionEnchantment("vis_battery", 3, "?", "chargable");
    public static final InfusionEnchantment VISCHARGE = new InfusionEnchantment("vis_charge", 1, "?", "chargable");
    public static final InfusionEnchantment SWIFT = new InfusionEnchantment("swift", 4, "IEARMOR", "boots");
    public static final InfusionEnchantment AGILE = new InfusionEnchantment("agile", 1, "IEARMOR", "legs");
    public static final InfusionEnchantment INFESTED = new InfusionEnchantment("infested", 1, "IETAINT", "chest");
    public static final InfusionEnchantment LAMPLIGHT = new InfusionEnchantment("lamplight", 1, "INFUSIONENCHANTMENT", "axe", "pickaxe", "shovel");


    public Set<String> toolClasses;
    public int maxLevel;
    public String research;
    public ResourceLocation id;

    InfusionEnchantment(ResourceLocation id, int ml, String research, String ... toolClasses) {
        this.toolClasses = ImmutableSet.copyOf(toolClasses);
        this.maxLevel = ml;
        this.research = research;
        this.id = id;
        enchants.put(id, this);
    }

    InfusionEnchantment(String name, int ml, String research, String ... toolClasses){
        this(new ResourceLocation(Thaumcraft.id, name), ml, research, toolClasses);
    }

    public static class Pair{
        public InfusionEnchantment type;
        public int level;

        public Pair(InfusionEnchantment type, int level) {
            this.type = type;
            this.level = level;
        }
    }

    public static ListNBT getTagList(ItemStack stack) {
        return (stack == null || stack.isEmpty() || stack.getTag() == null) ? null : stack.getTag().getList("infench", 10);
    }

    public static List<InfusionEnchantment> getList(ItemStack stack) {
        ListNBT tags = getTagList(stack);
        List<InfusionEnchantment> list = new ArrayList<InfusionEnchantment>();
        if (tags != null) {
            for (int j = 0; j < tags.size(); ++j) {
                ResourceLocation id = new ResourceLocation(tags.getCompound(j).getString("id"));
                list.add(enchants.get(id));
            }
        }
        return list;
    }

    public static List<Pair> getPairList(ItemStack stack) {
        ListNBT tags = getTagList(stack);
        List<Pair> list = new ArrayList<>();
        if (tags != null) {
            for (int j = 0; j < tags.size(); ++j) {
                ResourceLocation id = new ResourceLocation(tags.getCompound(j).getString("id"));
                int lvl = tags.getCompound(j).getShort("lvl");
                list.add(new Pair(enchants.get(id), lvl));
            }
        }
        return list;
    }

    public static int getLevel(ItemStack stack, InfusionEnchantment enchantment) {
        ListNBT tags = getTagList(stack);
        List<InfusionEnchantment> list = new ArrayList<InfusionEnchantment>();
        if (tags != null) {
            for (int j = 0; j < tags.size(); ++j) {
                ResourceLocation id = new ResourceLocation(tags.getCompound(j).getString("id"));
                int lvl = tags.getCompound(j).getShort("lvl");
                if (enchants.get(id) == enchantment) {
                    return lvl;
                }
            }
        }
        return 0;
    }

    public static void add(ItemStack stack, InfusionEnchantment ie, int level) {
        if (stack == null || stack.isEmpty() || level > ie.maxLevel) {
            return;
        }
        ListNBT list = getTagList(stack);
        if (list != null) {
            int j = 0;
            while (j < list.size()) {
                ResourceLocation id = new ResourceLocation(list.getCompound(j).getString("id"));
                int lvl = list.getCompound(j).getShort("lvl");
                if (id == ie.id) {
                    if (level <= lvl) {
                        return;
                    }
                    list.getCompound(j).putShort("lvl", (short) level);
                    stack.setTagInfo("infench", list);
                    return;
                } else {
                    ++j;
                }
            }
        } else {
            list = new ListNBT();
        }
        CompoundNBT tag = new CompoundNBT();
        tag.putString("id", ie.id.toString());
        tag.putShort("lvl", (short) level);
        list.add(tag);
        stack.setTagInfo("infench", list);
    }
}