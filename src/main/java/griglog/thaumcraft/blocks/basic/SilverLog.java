package griglog.thaumcraft.blocks.basic;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class SilverLog extends RotatedPillarBlock {
    public SilverLog() {
        super(AbstractBlock.Properties.create(Material.WOOD,
                (state) -> (state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y) ? MaterialColor.SAND: MaterialColor.QUARTZ)
            .hardnessAndResistance(2, 5)
            .sound(SoundType.WOOD)
            .harvestTool(ToolType.AXE));
        setRegistryName("log_silverwood");
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return 5;
    }
}
