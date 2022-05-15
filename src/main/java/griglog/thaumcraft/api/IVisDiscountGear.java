package griglog.thaumcraft.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * @author Azanor
 * ItemArmor or Baubles with this interface will grant a discount to the vis cost of actions the wearer performs with casting wands.
 * The amount returned is the percentage by which the cost is discounted. There is a built-int max discount of 50%.
 */
public interface IVisDiscountGear {
    int getVisDiscount(ItemStack stack, PlayerEntity player);
}
