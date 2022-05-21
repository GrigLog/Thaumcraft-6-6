package griglog.thaumcraft.data.providers;

import griglog.thaumcraft.data.ModTags;
import griglog.thaumcraft.utils.Utils;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModTagsProvider extends ItemTagsProvider {
    public ModTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, "forge", existingFileHelper);
    }

    @Override
    protected void registerTags() {
        for (Tags.IOptionalNamedTag<Item> tag : Utils.<Tags.IOptionalNamedTag<Item>>getFields(ModTags.class, Tags.IOptionalNamedTag.class, null)){
            getOrCreateBuilder(tag);
        }
        //getOrCreateBuilder(ModTags.ORES_COPPER);
        //getOrCreateBuilder(ModTags.ORES_TIN);
        //getOrCreateBuilder(ModTags.ORES_SILVER);
        //getOrCreateBuilder(ModTags.ORES_LEAD);
    }
}
