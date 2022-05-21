package griglog.thaumcraft.api.aspect;

import griglog.thaumcraft.aspect.AspectList;
import net.minecraft.item.ItemStack;

public interface IAspectHolderItem {
    AspectList readList(ItemStack is);
}
