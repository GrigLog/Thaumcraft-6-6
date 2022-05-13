package griglog.thaumcraft.utils;

import griglog.thaumcraft.items.ModTab;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

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
}
