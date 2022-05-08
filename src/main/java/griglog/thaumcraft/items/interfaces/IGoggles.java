package griglog.thaumcraft.items.interfaces;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

/**
 * @author Azanor
 *
 * Equipped or held items that extend this class will be able to perform most functions that
 * goggles of revealing can.
 */
public interface IGoggles {

    /*
	 * If this method returns true things like block essentia contents will be shown.
	 */
    public boolean showIngamePopups(ItemStack itemstack, LivingEntity player);
}
