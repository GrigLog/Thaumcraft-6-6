package griglog.thaumcraft.utils;

import net.minecraft.entity.player.PlayerEntity;
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
}
