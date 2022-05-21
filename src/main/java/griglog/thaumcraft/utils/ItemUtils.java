package griglog.thaumcraft.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.items.ModTab;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.Optional;

public class ItemUtils {
    public static boolean consumePlayerItem(PlayerEntity player, Item item){
        for (int i = 0; i < player.inventory.getSizeInventory(); i++){
            ItemStack is = player.inventory.getStackInSlot(i);
            if (is.getItem() == item){
                is.setCount(is.getCount() - 1);
                return true;
            }
        }
        return false;
    }

    public static String slotName(EquipmentSlotType slot){
        switch (slot){
            default:
            case FEET:
                return "boots";
            case LEGS:
                return "legs";
            case CHEST:
                return "chest";
            case HEAD:
                return "helm";
        }
    }

    public static Item getItem(Block b){
        return new BlockItem(b, ModTab.props()).setRegistryName(b.getRegistryName());
    }

    //based on vanilla ItemStack.CODEC
    public static final Codec<ItemStack> CODEC = RecordCodecBuilder.create((builder) ->
        builder.group(
            Registry.ITEM.fieldOf("id").forGetter(ItemStack::getItem),
            Codec.INT.optionalFieldOf("count", 1).forGetter(ItemStack::getCount),
            CompoundNBT.CODEC.optionalFieldOf("tag").forGetter((is) -> Optional.ofNullable(is.getTag()))
        ).apply(builder, (id, count, oTag) -> new ItemStack(id, count, oTag.orElse(null))));

    public static ItemStack deserialize(JsonElement json){
        return CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(false, Thaumcraft.LOGGER::error);
    }

    public static JsonElement serialize(ItemStack is){
        return CODEC.encodeStart(JsonOps.INSTANCE, is).getOrThrow(false, Thaumcraft.LOGGER::error);
    }
}
