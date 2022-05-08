package griglog.thaumcraft.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockTC extends Block {
    public BlockTC(AbstractBlock.Properties props) {
        super(props.hardnessAndResistance(2, 1.5f));
    }

    public BlockTC(AbstractBlock.Properties props, SoundType st) {
        this(props.sound(st));
    }
}
