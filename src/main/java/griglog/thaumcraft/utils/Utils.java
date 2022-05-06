package griglog.thaumcraft.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class Utils {
    public static CompoundNBT safeTag(ItemStack is){
        if (is.hasTag())
            return is.getTag();
        CompoundNBT tag = new CompoundNBT();
        is.setTag(tag);
        return tag;
    }
}
