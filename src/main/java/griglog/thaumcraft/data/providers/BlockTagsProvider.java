package griglog.thaumcraft.data.providers;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.blocks.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class BlockTagsProvider extends net.minecraft.data.BlockTagsProvider {
    public BlockTagsProvider(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, Thaumcraft.id, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        this.getOrCreateBuilder(BlockTags.LOGS).add(ModBlocks.greatLog, ModBlocks.silverLog);
    }
}
