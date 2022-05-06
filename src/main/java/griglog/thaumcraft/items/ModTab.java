package griglog.thaumcraft.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModTab extends ItemGroup {
    public static ModTab instance = new ModTab();
    public ModTab() {
        super("thaumcraft");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(Items.BOOK, 6);
    }

    public static Item.Properties props(){
        return new Item.Properties().group(instance);
    }

    public static Item defaultItem(String id){
        return new Item(props()).setRegistryName(id);
    }
}
