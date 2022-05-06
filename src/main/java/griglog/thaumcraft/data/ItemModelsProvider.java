package griglog.thaumcraft.data;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.items.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModelsProvider extends ItemModelProvider {
    public ItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Thaumcraft.id, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Item item : ModItems.defaultModel)
            makeItemModel(item, "item/generated");
        for (Item item: ModItems.toolModel)
            makeItemModel(item, "item/handheld");
    }

    private void makeItemModel(Item item, String parent) {
        String name = item.getRegistryName().getPath();
        singleTexture(name, mcLoc(parent), "layer0", new ResourceLocation(Thaumcraft.id, "items/" + name));
    }
}
