package griglog.thaumcraft.items;

import griglog.thaumcraft.Thaumcraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModTab extends ItemGroup {
    public static ModTab instance = new ModTab();
    public ModTab() {
        super(Thaumcraft.id);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.BOOK, 6);
    }

    public static Item.Properties props(){
        return new Item.Properties().group(instance);
    }

}
