package griglog.thaumcraft.data;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModTagsProvider extends ItemTagsProvider {
    public ModTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, "forge", existingFileHelper);
    }

    @Override
    protected void registerTags() {
        getOrCreateBuilder(ModTags.ORES_COPPER);
        getOrCreateBuilder(ModTags.ORES_TIN);
        getOrCreateBuilder(ModTags.ORES_SILVER);
        getOrCreateBuilder(ModTags.ORES_LEAD);
    }
}
