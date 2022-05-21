package griglog.thaumcraft.data.providers;

import com.google.common.collect.ImmutableSet;
import griglog.thaumcraft.Thaumcraft;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Set;

import static griglog.thaumcraft.blocks.ModBlocks.*;

public class BlockStatesProvider extends BlockStateProvider {
    public BlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Thaumcraft.id, exFileHelper);
    }

    static final Set<Block> cube = ImmutableSet.of(silverLeaves);

    @Override
    protected void registerStatesAndModels() {
        logBlock((RotatedPillarBlock) silverLog);
        logBlock((RotatedPillarBlock) greatLog);
        cross(silverSapling);
        cross(greatSapling);
        cube.forEach(this::simpleBlock);
        byParent(greatLeaves, "block/leaves", "all");
    }

    void cross(Block b){
        String name = b.getRegistryName().getPath();
        simpleBlock(b, models().cross(name, new ResourceLocation(Thaumcraft.id, "block/" + name)));
    }

    void byParent(Block b, String parent, String textureKey){
        String name = b.getRegistryName().getPath();
        simpleBlock(b, models().singleTexture(name, mcLoc(parent), textureKey, new ResourceLocation(Thaumcraft.id, "block/" + name)));
    }

}
