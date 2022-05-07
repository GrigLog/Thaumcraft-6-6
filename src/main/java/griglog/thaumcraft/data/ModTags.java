package griglog.thaumcraft.data;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class ModTags {
    public final static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> ORES_COPPER = oreTag("copper");
    public final static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> ORES_TIN = oreTag("tin");
    public final static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> ORES_SILVER = oreTag("silver");
    public final static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> ORES_LEAD = oreTag("lead");

    static net.minecraftforge.common.Tags.IOptionalNamedTag<Item> oreTag(String name){
        return ItemTags.createOptional(new ResourceLocation("forge", "ores/" + name));
    }
}
