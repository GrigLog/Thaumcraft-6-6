package griglog.thaumcraft.aspect;

import griglog.thaumcraft.api.aspect.IAspectHolderItem;
import griglog.thaumcraft.blocks.tiles.Jar;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ItemAspects {
    public static Map<Item, AspectList> items;
    public static Map<ResourceLocation, AspectList> tags;
    public static Map<Item, AspectList> ignoreTags;
    public static Map<Item, AspectList> addToGenerated;
    public static AspectList getAspects(ItemStack is){
        Item item = is.getItem();
        if (item instanceof IAspectHolderItem)
            return ((IAspectHolderItem)item).readList(is);
        if (item instanceof BlockItem){
            if (((BlockItem)item).getBlock() instanceof Jar && is.hasTag()){
                return new AspectList().add(new AspectEntry(is.getTag()));
            }
        }
        return items.getOrDefault(item, AspectList.EMPTY).copy().multiply(is.getCount());
    }

    public static void generateFromCrafts(){

    }
}
