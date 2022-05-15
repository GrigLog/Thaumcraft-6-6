package griglog.thaumcraft.blocks.basic;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ToolType;

public class GreatLog extends RotatedPillarBlock {
    public GreatLog() {
        super(AbstractBlock.Properties.create(Material.WOOD,
                (state) -> (state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y) ? MaterialColor.BROWN: MaterialColor.BROWN)
            .hardnessAndResistance(2, 5)
            .sound(SoundType.WOOD)
            .harvestTool(ToolType.AXE));
        setRegistryName("log_greatwood");
    }
}
