package griglog.thaumcraft.items.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

/**
 * @author Azanor
 *
 * Items with this interface can be recharged in wand pedestals and similar devices.
 * All values are automatically stored in the items nbt data.
 *
 * See RechargableItemHelper for methods to handle actualy recharging of the item.
 */
public interface IRechargable {

    /**
     * @param stack
     * @param player passed player may be null so check first
     * @return how much vis this item can hold
     */
    public int getMaxCharge(ItemStack stack, LivingEntity player);

    /**
     * @param stack
     * @param player
     * @return when the charge will be displayed in the built-in hud display for chargable items
     */
    public EnumChargeDisplay showInHud(ItemStack stack, LivingEntity player);

    enum EnumChargeDisplay {

        NEVER, NORMAL, PERIODIC
    }
    /*
     * NEVER = never
     * NORMAL = whenever the charge changes
     * PERIODIC = whenever charge changes to 0%, 25%, 50%, 75% or 100%
     *
     */
}
